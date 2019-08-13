package fairy.easy.httpmodel.model;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Map;

import fairy.easy.httpmodel.util.Preconditions;

public class HttpModel implements Key {
    private static final String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%;$";
    private final Headers headers;
    @Nullable
    private final URL url;
    private final RequestMethod requestMethod;
    private final PostParam postParam;

    @Nullable
    private final String stringUrl;

    @Nullable
    private String safeStringUrl;
    @Nullable
    private URL safeUrl;
    @Nullable
    private volatile byte[] cacheKeyBytes;

    private int hashCode;

    public HttpModel(URL url, RequestMethod requestMethod, PostParam postParam) {
        this(url, Headers.DEFAULT, requestMethod, postParam);
    }

    public HttpModel(String url, RequestMethod requestMethod, PostParam postParam) {
        this(url, Headers.DEFAULT, requestMethod, postParam);
    }

    public HttpModel(URL url, Headers headers, RequestMethod requestMethod, PostParam postParam) {
        this.url = Preconditions.checkNotNull(url);
        stringUrl = null;
        this.requestMethod = requestMethod;
        this.headers = Preconditions.checkNotNull(headers);
        this.postParam = postParam;
    }

    public HttpModel(String url, Headers headers, RequestMethod requestMethod, PostParam postParam) {
        this.url = null;
        this.requestMethod = requestMethod;
        this.stringUrl = Preconditions.checkNotEmpty(url);
        this.headers = Preconditions.checkNotNull(headers);
        this.postParam = postParam;
    }

    public PostParam getPostParam() {
        return postParam;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public URL toURL() throws MalformedURLException {
        return getSafeUrl();
    }

    private URL getSafeUrl() throws MalformedURLException {
        if (safeUrl == null) {
            safeUrl = new URL(getSafeStringUrl());
        }
        return safeUrl;
    }


    public String toStringUrl() {
        return getSafeStringUrl();
    }

    private String getSafeStringUrl() {
        if (TextUtils.isEmpty(safeStringUrl)) {
            String unsafeStringUrl = stringUrl;
            if (TextUtils.isEmpty(unsafeStringUrl)) {
                unsafeStringUrl = Preconditions.checkNotNull(url).toString();
            }
            safeStringUrl = Uri.encode(unsafeStringUrl, ALLOWED_URI_CHARS);
        }
        return safeStringUrl;
    }

    public Map<String, String> getHeaders() {
        return headers.getHeaders();
    }


    @SuppressWarnings("WeakerAccess")
    public String getCacheKey() {
        return stringUrl != null ? stringUrl : Preconditions.checkNotNull(url).toString();
    }

    @Override
    public String toString() {
        return getCacheKey();
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(getCacheKeyBytes());
    }

    private byte[] getCacheKeyBytes() {
        if (cacheKeyBytes == null) {
            cacheKeyBytes = getCacheKey().getBytes(CHARSET);
        }
        return cacheKeyBytes;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof HttpModel) {
            HttpModel other = (HttpModel) o;
            return getCacheKey().equals(other.getCacheKey())
                    && headers.equals(other.headers);
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (hashCode == 0) {
            hashCode = getCacheKey().hashCode();
            hashCode = 2 * hashCode + headers.hashCode();
        }
        return hashCode;
    }
}
