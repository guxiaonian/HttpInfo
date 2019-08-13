package fairy.easy.httpmodel.resource.base;


import org.json.JSONObject;

import java.io.Serializable;

import fairy.easy.httpmodel.HttpModelHelper;
import fairy.easy.httpmodel.util.HttpLog;


public class BaseBean implements Serializable {

    protected JSONObject jsonObject = new JSONObject();


    protected JSONObject toJSONObject() {
        HttpLog.i(jsonObject.toString());
        return jsonObject;
    }

    protected BaseBean() {

    }

    public boolean isChina() {
        return HttpModelHelper.getInstance().isChina();
    }

}
