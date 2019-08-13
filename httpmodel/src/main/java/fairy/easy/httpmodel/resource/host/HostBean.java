package fairy.easy.httpmodel.resource.host;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import fairy.easy.httpmodel.resource.base.BaseBean;

public class HostBean extends BaseBean {

    private int status;

    private List<String> param;

    public int getStatus() {
        return status;
    }

    private int totalTime;

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<String> getParam() {
        return param;
    }

    public void setParam(List<String> param) {
        this.param = param;
    }

    public HostBean() {
    }

    @Override
    protected JSONObject toJSONObject() {
        try {
            jsonObject.put(isChina() ? HostData.STATUS_CN : HostData.STATUS, status);
            jsonObject.put(isChina() ? HostData.PARAM_CN : HostData.PARAM, new JSONArray(param));
            jsonObject.put(isChina() ? HostData.TOTALTIME_CN : HostData.TOTALTIME, totalTime + "ms");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return super.toJSONObject();
    }

    public static class HostData {
        public static final String TOTALTIME = "totalTime";
        public static final String TOTALTIME_CN = "总消耗时间";
        public static final String STATUS = "status";
        public static final String STATUS_CN = "执行结果";
        public static final String PARAM = "param";
        public static final String PARAM_CN = "详细信息";
    }
}
