package fairy.easy.httpmodel.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import fairy.easy.httpmodel.HttpModelHelper;
import fairy.easy.httpmodel.model.HttpModel;
import fairy.easy.httpmodel.model.ModelLoader;
import fairy.easy.httpmodel.model.PostParam;
import fairy.easy.httpmodel.model.RequestMethod;
import fairy.easy.httpmodel.resource.base.BaseData;

public class Output {

    public interface OutPutListener<T>{
        void onSuccess(T t);

        void onFail(Exception e);
    }

    public static void getOutPutIp(final OutPutListener<String> outPutListener) {
        ModelLoader modelLoader = HttpModelHelper.getInstance().getModelLoader();
        modelLoader.setHttpModel(new HttpModel(BaseData.BASE_URL + BaseData.OUTPUT_IP_URL, RequestMethod.GET, null));
        modelLoader.loadData(new ModelLoader.DataCallback<String>() {
            @Override
            public void onDataReady(@Nullable String data) {
                HttpLog.i("outputIp success:" + data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    outPutListener.onSuccess(jsonObject.getString("ip"));
                } catch (JSONException e) {
                    outPutListener.onFail(e);
                }
            }

            @Override
            public void onLoadFailed(@NonNull Exception e) {
                HttpLog.e("outputIp fail:" + e.toString());
                outPutListener.onFail(e);
            }
        });
    }


    public static void getOutPutIpCountry(final OutPutListener<String> outPutListener,String ip) {
        ModelLoader modelLoader = HttpModelHelper.getInstance().getModelLoader();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ip", ip);
            jsonObject.put("ver", 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        PostParam postParam = new PostParam(jsonObject);
        modelLoader.setHttpModel(new HttpModel(BaseData.BASE_URL + BaseData.OUTPUT_IP_COUNTRY_URL, RequestMethod.POST, postParam));
        modelLoader.loadData(new ModelLoader.DataCallback<String>() {
            @Override
            public void onDataReady(@Nullable String data) {
                HttpLog.i("outputIp info success:" + data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    outPutListener.onSuccess(jsonObject.getString("country") + jsonObject.getString("province") + jsonObject.getString("isp"));
                } catch (JSONException e) {
                    outPutListener.onFail(e);
                }
            }

            @Override
            public void onLoadFailed(@NonNull Exception e) {
                HttpLog.e("outputIp info fail:" + e.toString());
                outPutListener.onFail(e);
            }
        });
    }
}
