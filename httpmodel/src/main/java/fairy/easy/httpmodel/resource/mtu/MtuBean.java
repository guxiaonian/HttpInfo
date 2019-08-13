package fairy.easy.httpmodel.resource.mtu;

import org.json.JSONException;
import org.json.JSONObject;

import fairy.easy.httpmodel.resource.base.BaseBean;

public class MtuBean extends BaseBean {

    private int status;
    private int mtu;
    private int totalTime;

    public MtuBean() {
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getMtu() {
        return mtu;
    }

    public void setMtu(int mtu) {
        this.mtu = mtu;
    }

    @Override
    protected JSONObject toJSONObject() {
        try {
            jsonObject.put(isChina() ? MtuData.STATUS_CN : MtuData.STATUS, status);
            jsonObject.put(isChina() ? MtuData.MTU_CN : MtuData.MTU, mtu + "bytes");
            jsonObject.put(isChina() ? MtuData.TOTALTIME_CN : MtuData.TOTALTIME, totalTime + "ms");
        } catch (JSONException e) {
            //ignore
        }
        return super.toJSONObject();
    }

    public static class MtuData {
        public static final String STATUS = "status";
        public static final String STATUS_CN = "执行结果";
        public static final String MTU = "mtu";
        public static final String MTU_CN = "传输单元";
        public static final String TOTALTIME_CN = "总消耗时间";
        public static final String TOTALTIME = "totalTime";
    }
}
