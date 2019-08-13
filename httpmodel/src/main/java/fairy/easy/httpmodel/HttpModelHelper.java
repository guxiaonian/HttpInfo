package fairy.easy.httpmodel;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.AnyThread;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;

import java.util.UUID;

import fairy.easy.httpmodel.model.HttpNormalUrlLoader;
import fairy.easy.httpmodel.model.ModelLoader;
import fairy.easy.httpmodel.resource.HttpFactory;
import fairy.easy.httpmodel.resource.HttpListener;
import fairy.easy.httpmodel.resource.Input;
import fairy.easy.httpmodel.util.Base;
import fairy.easy.httpmodel.util.HttpLog;
import fairy.easy.httpmodel.util.LogTime;
import fairy.easy.httpmodel.util.NetWork;
import fairy.easy.httpmodel.util.Preconditions;

public class HttpModelHelper {

    private Context mContext;
    private String mAddress;
    private HttpListener httpListener;
    private ModelLoader modelLoader;
    private HttpFactory httpFactory;
    private long initTime;

    public HttpModelHelper() {
    }

    private static class HttpModelLoader {
        private static final HttpModelHelper INSTANCE = new HttpModelHelper();
    }

    public static HttpModelHelper getInstance() {
        return HttpModelLoader.INSTANCE;
    }

    public HttpModelHelper init(@NonNull Context context) {
        this.mContext = context.getApplicationContext();
        return this;
    }

    private boolean isChina;

    public boolean isChina() {
        return isChina;
    }

    public HttpModelHelper setChina(boolean china) {
        isChina = china;
        return this;
    }

    public ModelLoader getModelLoader() {
        if (modelLoader == null) {
            modelLoader = new HttpNormalUrlLoader();
        }
        return modelLoader;
    }

    public HttpModelHelper setModelLoader(@NonNull ModelLoader modelLoader) {
        this.modelLoader = modelLoader;
        return this;
    }

    public Context getContext() {
        return mContext;
    }


    public String getAddress() {
        return mAddress;
    }

    @MainThread
    public HttpListener getHttpListener() {
        return httpListener;
    }

    public long getInitTime() {
        return initTime;
    }

    @AnyThread
    public void startAsync(@NonNull final String address, @NonNull HttpListener httpListener) {
        Preconditions.checkNotNull(httpListener, "HttpListener must not be null");
        HttpLog.i("Welcome to use HttpModel");
        initTime = LogTime.getLogTime();
        this.httpListener = httpListener;
        if (Input.isMainThread()) {
            HandlerThread handlerThread = new HandlerThread("http" + UUID.randomUUID().toString().substring(0, 8));
            handlerThread.start();
            Handler handler = new Handler(handlerThread.getLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    startNetWork(address);
                }
            });
        } else {
            startNetWork(address);
        }
    }

    private boolean checkNetWork(String address) {
        if (mContext == null) {
            Input.onFail("Context must not be null");
            return false;
        }
        if (!Preconditions.checkNotEmptyBoolean(address)) {
            Input.onFail("The input address must not be null");
            return false;
        }
        if (!NetWork.isNetworkAvailable(mContext)) {
            Input.onFail("The network is not available");
            return false;
        }
        if (!address.startsWith("http")) {
            address = "http://" + address;
        }
        if (address.startsWith("https")) {
            address = "http://" + address.substring(8);
        }
        if (!Base.checkUrl(address)) {
            Input.onFail("The input address is not legitimate");
            return false;
        }
        this.mAddress = address;
        HttpLog.i("this address is:" + mAddress);
        return true;
    }

    private void startNetWork(String address) {
        if (checkNetWork(address)) {
            if (httpFactory != null) {
                httpFactory.getData();
            } else {
                httpFactory = new HttpFactory();
                httpFactory.addAll().build();
                httpFactory.getData();
            }
        }
    }

    public HttpFactory setFactory() {
        this.httpFactory = new HttpFactory();
        return httpFactory;
    }

    public HttpFactory setFactory(@NonNull HttpFactory httpFactory) {
        this.httpFactory = httpFactory;
        return httpFactory;
    }

    public int getHttpTypeSize() {
        return httpFactory == null ? 0 : httpFactory.getTypeSize();
    }


}
