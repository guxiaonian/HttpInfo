package fairy.easy.httpmodel.model;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

import fairy.easy.httpmodel.load.HttpException;
import fairy.easy.httpmodel.util.HttpLog;
import fairy.easy.httpmodel.util.LogTime;
import fairy.easy.httpmodel.util.Preconditions;

/**
 * @author gunaonian
 * 普通的加载类
 */
public class HttpNormalUrlLoader implements ModelLoader<String> {

    private static final int MAXIMUM_REDIRECTS = 1;
    private static final int INVALID_STATUS_CODE = -1;

    private HttpModel httpModel = null;
    private int timeout = 5 * 1000;
    private HttpUrlConnectionFactory connectionFactory = DEFAULT_CONNECTION_FACTORY;

    private HttpURLConnection urlConnection;
    private InputStream stream;
    private volatile boolean isCancelled;

    private static final HttpUrlConnectionFactory DEFAULT_CONNECTION_FACTORY =
            new DefaultHttpUrlConnectionFactory();

    @Override
    public void loadData(@NonNull DataCallback<? super String> callback) {
        if (httpModel == null || timeout < 1 || connectionFactory == null) {
            HttpLog.e("Failed to load data");
            callback.onLoadFailed(new IllegalArgumentException("Failed to load data"));
        }
        long startTime = LogTime.getLogTime();
        try {
            String result = loadDataWithRedirects(httpModel.toURL(), 0, null, httpModel.getHeaders());
            callback.onDataReady(result);
        } catch (IOException e) {
            HttpLog.e("Failed to load data for url" + e.toString());
            callback.onLoadFailed(e);
        } finally {
            HttpLog.e("Finished http url fetcher fetch in " + LogTime.getElapsedMillis(startTime)+"ms");
        }
    }

    private String loadDataWithRedirects(URL url, int redirects, URL lastUrl,
                                         Map<String, String> headers) throws IOException {
        if (redirects >= MAXIMUM_REDIRECTS) {
            throw new HttpException("Too many (> " + MAXIMUM_REDIRECTS + ") redirects!");
        } else {
            try {
                if (lastUrl != null && url.toURI().equals(lastUrl.toURI())) {
                    throw new HttpException("In re-direct loop");

                }
            } catch (URISyntaxException e) {
                //ignore
            }
        }

        urlConnection = connectionFactory.build(url);
        for (Map.Entry<String, String> headerEntry : headers.entrySet()) {
            urlConnection.addRequestProperty(headerEntry.getKey(), headerEntry.getValue());
        }
        urlConnection.setConnectTimeout(timeout);
        urlConnection.setReadTimeout(timeout);
        urlConnection.setUseCaches(false);
        urlConnection.setRequestMethod(httpModel.getRequestMethod().getName());
        urlConnection.setDoInput(true);

        urlConnection.setInstanceFollowRedirects(false);
        urlConnection.connect();
        if (RequestMethod.POST == httpModel.getRequestMethod() && Preconditions.checkNotEmptyBoolean(httpModel.getPostParam().getStringParam())) {
            OutputStream outputStream = urlConnection.getOutputStream();
            outputStream.write(httpModel.getPostParam().getStringParam().getBytes());
            outputStream.flush();
            outputStream.close();

        }
        stream = urlConnection.getInputStream();
        if (isCancelled) {
            return null;
        }
        final int statusCode = urlConnection.getResponseCode();
        if (isHttpOk(statusCode)) {
            return getStreamForSuccessfulRequest();
        } else if (isHttpRedirect(statusCode)) {
            String redirectUrlString = urlConnection.getHeaderField("Location");
            if (TextUtils.isEmpty(redirectUrlString)) {
                throw new HttpException("Received empty or null redirect url");
            }
            URL redirectUrl = new URL(url, redirectUrlString);
            cleanup();
            return loadDataWithRedirects(redirectUrl, redirects + 1, url, headers);
        } else if (statusCode == INVALID_STATUS_CODE) {
            throw new HttpException(statusCode);
        } else {
            throw new HttpException(urlConnection.getResponseMessage(), statusCode);
        }
    }

    private static boolean isHttpOk(int statusCode) {
        return statusCode / 100 == 2;
    }

    private static boolean isHttpRedirect(int statusCode) {
        return statusCode / 100 == 3;
    }

    private String getStreamForSuccessfulRequest()
            throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        byte[] buf = new byte[1024];
        for (int n; (n = stream.read(buf)) != -1; ) {
            stringBuilder.append(new String(buf, 0, n, HttpModel.GBK_CHARSET_NAME));
        }
        cleanup();
        return stringBuilder.toString();

    }

    @Override
    public void cleanup() {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                // Ignore
            }
        }
        if (urlConnection != null) {
            urlConnection.disconnect();
        }
        urlConnection = null;
    }

    @Override
    public void cancel() {
        isCancelled = true;
    }

    @Override
    public ModelLoader<String> setHttpModel(HttpModel httpModel) {
        this.httpModel = httpModel;
        return this;
    }

    @Override
    public ModelLoader<String> setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }


    @NonNull
    @Override
    public Class<String> getDataClass() {
        return String.class;
    }

    interface HttpUrlConnectionFactory {
        HttpURLConnection build(URL url) throws IOException;
    }

    private static class DefaultHttpUrlConnectionFactory implements HttpUrlConnectionFactory {

        DefaultHttpUrlConnectionFactory() {
        }

        @Override
        public HttpURLConnection build(URL url) throws IOException {
            return (HttpURLConnection) url.openConnection();
        }
    }
}
