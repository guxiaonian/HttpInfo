package fairy.easy.httpmodel.resource;

import android.os.Handler;
import android.os.Looper;

import org.json.JSONException;
import org.json.JSONObject;

import fairy.easy.httpmodel.HttpModelHelper;
import fairy.easy.httpmodel.util.HttpLog;
import fairy.easy.httpmodel.util.LogTime;

public class Input {
    public static boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    public static Handler mainHandler = new Handler(Looper.getMainLooper());

    private static final String ALL_TIME = "totalTime";

    private static JSONObject totalJson;
    private static int index;

    public static void onSuccess(final HttpType httpType, final JSONObject result) {
        if (isMainThread()) {
            HttpModelHelper.getInstance().getHttpListener().onSuccess(httpType, result);
            addJson(httpType, result);
        } else {
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    HttpModelHelper.getInstance().getHttpListener().onSuccess(httpType, result);
                    addJson(httpType, result);
                }
            });
        }
    }

    private static void addJson(HttpType httpType, JSONObject result) {
        if (totalJson == null) {
            totalJson = new JSONObject();
        }
        index++;
        try {
            totalJson.put(httpType.getName(), result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (index == HttpModelHelper.getInstance().getHttpTypeSize()) {
            try {
                totalJson.put(ALL_TIME, LogTime.getElapsedMillis(HttpModelHelper.getInstance().getInitTime())+ "ms");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpModelHelper.getInstance().getHttpListener().onFinish(totalJson);
            totalJson = null;
            index = 0;
        }
    }

    public static void onFail(final String error) {
        HttpLog.e(error);
        if (isMainThread()) {
            HttpModelHelper.getInstance().getHttpListener().onFail(error);
        } else {
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    HttpModelHelper.getInstance().getHttpListener().onFail(error);
                }
            });
        }
    }
}
