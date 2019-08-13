package fairy.easy.httpmodel.resource.index;

import org.json.JSONException;
import org.json.JSONObject;

import fairy.easy.httpmodel.resource.base.BaseBean;

public class IndexBean extends BaseBean {

    private String address="*";
    private String time="*";

    public IndexBean() {
    }

    public String getAddress() {
        return address;
    }

    public String getTime() {
        return time;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    protected JSONObject toJSONObject() {
        try {
            jsonObject.put(isChina() ? IndexData.ADDRESS_CN : IndexData.ADDRESS, address);
            jsonObject.put(isChina() ? IndexData.TIME_CN : IndexData.TIME, time);
        } catch (JSONException e) {
            //ignore
        }
        return super.toJSONObject();
    }

    public static class IndexData {
        public static final String ADDRESS = "address";
        public static final String ADDRESS_CN = "测速地址";
        public static final String TIME = "time";
        public static final String TIME_CN = "请求时间";
    }
}
