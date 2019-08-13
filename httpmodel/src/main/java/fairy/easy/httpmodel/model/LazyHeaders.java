package fairy.easy.httpmodel.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class LazyHeaders implements Headers {
    private final Map<String, List<LazyHeaderFactory>> headers;
    private volatile Map<String, String> combinedHeaders;

    LazyHeaders(Map<String, List<LazyHeaderFactory>> headers) {
        this.headers = Collections.unmodifiableMap(headers);
    }

    @Override
    public Map<String, String> getHeaders() {
        if (combinedHeaders == null) {
            synchronized (this) {
                if (combinedHeaders == null) {
                    this.combinedHeaders = Collections.unmodifiableMap(generateHeaders());
                }
            }
        }

        return combinedHeaders;
    }

    private Map<String, String> generateHeaders() {
        Map<String, String> combinedHeaders = new HashMap<>();

        for (Map.Entry<String, List<LazyHeaderFactory>> entry : headers.entrySet()) {
            String values = buildHeaderValue(entry.getValue());
            if (!TextUtils.isEmpty(values)) {
                combinedHeaders.put(entry.getKey(), values);
            }
        }

        return combinedHeaders;
    }

    @NonNull
    private String buildHeaderValue(@NonNull List<LazyHeaderFactory> factories) {
        StringBuilder sb = new StringBuilder();
        int size = factories.size();
        for (int i = 0; i < size; i++) {
            LazyHeaderFactory factory = factories.get(i);
            String header = factory.buildHeader();
            if (!TextUtils.isEmpty(header)) {
                sb.append(header);
                if (i != factories.size() - 1) {
                    sb.append(',');
                }
            }
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "LazyHeaders{"
                + "headers=" + headers
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof LazyHeaders) {
            LazyHeaders other = (LazyHeaders) o;
            return headers.equals(other.headers);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return headers.hashCode();
    }

    public static final class Builder {
        private static final String USER_AGENT_HEADER = "User-Agent";
        private static final String DEFAULT_USER_AGENT = getSanitizedUserAgent();
        private static final Map<String, List<LazyHeaderFactory>> DEFAULT_HEADERS;

        static {
            Map<String, List<LazyHeaderFactory>> temp
                    = new HashMap<>(2);
            if (!TextUtils.isEmpty(DEFAULT_USER_AGENT)) {
                temp.put(USER_AGENT_HEADER,
                        Collections.<LazyHeaderFactory>singletonList(
                                new StringHeaderFactory(DEFAULT_USER_AGENT)));
            }
            DEFAULT_HEADERS = Collections.unmodifiableMap(temp);
        }

        private boolean copyOnModify = true;
        private Map<String, List<LazyHeaderFactory>> headers = DEFAULT_HEADERS;
        private boolean isUserAgentDefault = true;

        public Builder addHeader(@NonNull String key, @NonNull String value) {
            return addHeader(key, new StringHeaderFactory(value));
        }


        public Builder addHeader(@NonNull String key, @NonNull LazyHeaderFactory factory) {
            if (isUserAgentDefault && USER_AGENT_HEADER.equalsIgnoreCase(key)) {
                return setHeader(key, factory);
            }

            copyIfNecessary();
            getFactories(key).add(factory);
            return this;
        }


        @SuppressWarnings({"UnusedReturnValue", "WeakerAccess"})
        public Builder setHeader(@NonNull String key, @Nullable String value) {
            return setHeader(key, value == null ? null : new StringHeaderFactory(value));
        }

        public Builder setHeader(@NonNull String key, @Nullable LazyHeaderFactory factory) {
            copyIfNecessary();
            if (factory == null) {
                headers.remove(key);
            } else {
                List<LazyHeaderFactory> factories = getFactories(key);
                factories.clear();
                factories.add(factory);
            }

            if (isUserAgentDefault && USER_AGENT_HEADER.equalsIgnoreCase(key)) {
                isUserAgentDefault = false;
            }

            return this;
        }

        private List<LazyHeaderFactory> getFactories(String key) {
            List<LazyHeaderFactory> factories = headers.get(key);
            if (factories == null) {
                factories = new ArrayList<>();
                headers.put(key, factories);
            }
            return factories;
        }

        private void copyIfNecessary() {
            if (copyOnModify) {
                copyOnModify = false;
                headers = copyHeaders();
            }
        }

        public LazyHeaders build() {
            copyOnModify = true;
            return new LazyHeaders(headers);
        }

        private Map<String, List<LazyHeaderFactory>> copyHeaders() {
            Map<String, List<LazyHeaderFactory>> result = new HashMap<>(headers.size());
            for (Map.Entry<String, List<LazyHeaderFactory>> entry : headers.entrySet()) {
                @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
                List<LazyHeaderFactory> valueCopy = new ArrayList<>(entry.getValue());
                result.put(entry.getKey(), valueCopy);
            }
            return result;
        }

        @VisibleForTesting
        static String getSanitizedUserAgent() {
            String defaultUserAgent = System.getProperty("http.agent");
            if (TextUtils.isEmpty(defaultUserAgent)) {
                return defaultUserAgent;
            }

            int length = defaultUserAgent.length();
            StringBuilder sb = new StringBuilder(defaultUserAgent.length());
            for (int i = 0; i < length; i++) {
                char c = defaultUserAgent.charAt(i);
                if ((c > '\u001f' || c == '\t') && c < '\u007f') {
                    sb.append(c);
                } else {
                    sb.append('?');
                }
            }
            return sb.toString();
        }
    }

    static final class StringHeaderFactory implements LazyHeaderFactory {

        @NonNull
        private final String value;

        StringHeaderFactory(@NonNull String value) {
            this.value = value;
        }

        @Override
        public String buildHeader() {
            return value;
        }

        @Override
        public String toString() {
            return "StringHeaderFactory{"
                    + "value='" + value + '\''
                    + '}';
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof StringHeaderFactory) {
                StringHeaderFactory other = (StringHeaderFactory) o;
                return value.equals(other.value);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return value.hashCode();
        }
    }
}
