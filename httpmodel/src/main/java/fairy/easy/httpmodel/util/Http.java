package fairy.easy.httpmodel.util;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fairy.easy.httpmodel.HttpModelHelper;
import fairy.easy.httpmodel.load.HttpException;
import fairy.easy.httpmodel.model.HttpModel;
import fairy.easy.httpmodel.model.ModelLoader;
import fairy.easy.httpmodel.model.PostParam;
import fairy.easy.httpmodel.model.RequestMethod;
import fairy.easy.httpmodel.resource.base.BaseData;
import fairy.easy.httpmodel.resource.http.HttpBean;


public class Http {
    private static final int DEFAULT_TIMEOUT = 5 * 1000;

    private final String address;
    private final HttpBean httpBean;
    private final int timeOut;
    private List<Map<Object, String>> list;

    public Http(String address) {
        this(address, DEFAULT_TIMEOUT);
    }

    public Http(String address, int timeout) {
        this.address = address;
        this.timeOut = timeout;
        this.httpBean = new HttpBean();
        list = new ArrayList<>();
    }


    public HttpBean getHttpInfo() {
        httpBean.setAddress(address);
        try {
            loadDataWithRedirects(new URL(address), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        httpBean.setHeader(list);
        return httpBean;

    }

    private boolean isHttpOk(int statusCode) {
        return statusCode / 100 == 2;
    }

    private boolean isHttpRedirect(int statusCode) {
        return statusCode / 100 == 3;
    }

    private void loadDataWithRedirects(URL url, URL lastUrl) throws IOException {
        try {
            if (lastUrl != null && url.toURI().equals(lastUrl.toURI())) {
                throw new HttpException("In re-direct loop");

            }
        } catch (URISyntaxException e) {
            //ignore
        }
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
//            urlConnection.setDoOutput(true);
//            urlConnection.setRequestMethod(RequestMethod.POST.getName());
            urlConnection.setConnectTimeout(timeOut);
            urlConnection.setReadTimeout(timeOut);
//            urlConnection.setRequestProperty("Accept-Encoding", "identity");
            Map<String, List<String>> map = urlConnection.getHeaderFields();
            Map<Object, String> stringMap = new HashMap<>();
            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                if (!TextUtils.isEmpty(entry.getKey()) && ("Server").equals(entry.getKey())) {
                    httpBean.setHeaderServer(entry.getValue().toString().substring(1, entry.getValue().toString().length() - 1));
                }
                stringMap.put(entry.getKey() + "", entry.getValue().toString().substring(1, entry.getValue().toString().length() - 1));
            }
            list.add(stringMap);

            urlConnection.connect();

            int statusCode = urlConnection.getResponseCode();
            httpBean.setResponseCode(statusCode);
            if (isHttpOk(statusCode)) {
                httpBean.setError(BaseData.HTTP_SUCCESS);
                getStreamForSuccessfulRequest(urlConnection, true);
            } else if (isHttpRedirect(statusCode)) {
                httpBean.setJump(true);
                String redirectUrlString = urlConnection.getHeaderField("Location");
                if (TextUtils.isEmpty(redirectUrlString)) {
                    throw new HttpException("Received empty or null redirect url");
                }
                URL redirectUrl = new URL(url, redirectUrlString);
                try {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                } catch (Exception e) {
                    //ignore
                }
                loadDataWithRedirects(redirectUrl, url);
            } else {
                getStreamForSuccessfulRequest(urlConnection, false);
                httpBean.setError(BaseData.HTTP_SUCCESS);
            }

        } catch (Exception e) {
            e.printStackTrace();
            httpBean.setError(BaseData.HTTP_ERROR);
        } finally {
            try {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                //ignore
            }

        }

    }

    private void getStreamForSuccessfulRequest(HttpURLConnection httpURLConnection, boolean isSuccess)
            throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        byte[] buf = new byte[1024];
        InputStream inputStream = isSuccess ? httpURLConnection.getInputStream() : httpURLConnection.getErrorStream();
        for (int n; (n = inputStream.read(buf)) != -1; ) {
            stringBuilder.append(new String(buf, 0, n));
        }
        try {
            inputStream.close();
        } catch (Exception e) {
            //ignore
        }
        HttpLog.i("http size " + stringBuilder.toString().getBytes().length / 1024.0 + "KB");
        httpBean.setSize(stringBuilder.toString().getBytes().length / 1024.0);

    }


    public void getResponseServer() {
        ModelLoader modelLoader = HttpModelHelper.getInstance().getModelLoader();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("url", httpBean.getAddress());
            jsonObject.put("ver", 1);
            jsonObject.put("action", "get_server");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        PostParam postParam = new PostParam(jsonObject);
        modelLoader.setHttpModel(new HttpModel(BaseData.BASE_URL + BaseData.RESPONSE_SERVER_URL, RequestMethod.POST, postParam));
        modelLoader.loadData(new ModelLoader.DataCallback<String>() {
            @Override
            public void onDataReady(@Nullable String data) {
                HttpLog.i("ResponseServer info success:" + data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    httpBean.setCheckHeaderServer(jsonObject.getString("server"));
                } catch (JSONException e) {

                }
            }

            @Override
            public void onLoadFailed(@NonNull Exception e) {
                HttpLog.e("ResponseServer info fail:" + e.toString());
            }
        });

    }
}
