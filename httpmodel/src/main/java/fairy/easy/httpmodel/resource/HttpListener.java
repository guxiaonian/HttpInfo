package fairy.easy.httpmodel.resource;

import org.json.JSONObject;

public interface HttpListener {

    void onSuccess(HttpType httpType, JSONObject result);

    void onFail(String data);

    void onFinish(JSONObject result);
}
