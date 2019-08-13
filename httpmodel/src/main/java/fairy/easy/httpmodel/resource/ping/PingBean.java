package fairy.easy.httpmodel.resource.ping;

import org.json.JSONException;
import org.json.JSONObject;

import fairy.easy.httpmodel.resource.base.BaseBean;

/**
 * Created by 谷闹年 on 2018/8/15.
 */

public class PingBean extends BaseBean {

    private String address="*";
    private String ip="*";
    private float lossRate;
    private int receive;
    private float rttAvg;
    private float rttMDev;
    private float rttMax;
    private float rttMin;
    private int ttl;
    private int error;
    private int transmitted;
    private int allTime;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAllTime() {
        return allTime;
    }

    public void setAllTime(int allTime) {
        this.allTime = allTime;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public int getTransmitted() {
        return transmitted;
    }

    public void setTransmitted(int count) {
        this.transmitted = count;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setLossRate(float lossRate) {
        this.lossRate = lossRate;
    }

    public void setReceive(int receive) {
        this.receive = receive;
    }

    public void setRttAvg(float rttAvg) {
        this.rttAvg = rttAvg;
    }

    public void setRttMDev(float rttMDev) {
        this.rttMDev = rttMDev;
    }

    public void setRttMax(float rttMax) {
        this.rttMax = rttMax;
    }

    public void setRttMin(float rttMin) {
        this.rttMin = rttMin;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    public String getIp() {
        return this.ip;
    }

    public float getLossRate() {
        return this.lossRate;
    }

    public int getReceive() {
        return this.receive;
    }

    public float getRttAvg() {
        return this.rttAvg;
    }

    public float getRttMDev() {
        return this.rttMDev;
    }

    public float getRttMax() {
        return this.rttMax;
    }

    public float getRttMin() {
        return this.rttMin;
    }


    public int getTtl() {
        return this.ttl;
    }

    public PingBean() {
    }

    @Override
    protected JSONObject toJSONObject() {
        try {
            jsonObject.put(isChina() ? PingData.ADDRESS_CN :PingData.ADDRESS, address);
            jsonObject.put(isChina() ? PingData.ERROR_CN :PingData.ERROR, error);
            jsonObject.put(isChina() ? PingData.IP_CN :PingData.IP, ip);
            jsonObject.put(isChina() ? PingData.TTL_CN :PingData.TTL, ttl);
            jsonObject.put(isChina() ? PingData.TRANSMITTED_CN :PingData.TRANSMITTED, transmitted);
            jsonObject.put(isChina() ? PingData.RECEIVE_CN :PingData.RECEIVE, receive);
            jsonObject.put(isChina() ? PingData.LOSSRATE_CN :PingData.LOSSRATE, lossRate+"%");
            jsonObject.put(isChina() ? PingData.RTTMIN_CN :PingData.RTTMIN, rttMin+"ms");
            jsonObject.put(isChina() ? PingData.RTTAVG_CN :PingData.RTTAVG, rttAvg+"ms");
            jsonObject.put(isChina() ? PingData.RTTMAX_CN :PingData.RTTMAX, rttMax+"ms");
            jsonObject.put(isChina() ? PingData.RTTMDEV_CN :PingData.RTTMDEV, rttMDev+"ms");
            jsonObject.put(isChina() ? PingData.ALLTIME_CN :PingData.ALLTIME, allTime+"ms");
        } catch (JSONException e) {
            //ignore
        }
        return super.toJSONObject();
    }

    public static class PingData {
        public static final String IP = "ip";
        public static final String IP_CN = "IP地址";
        public static final String ADDRESS = "address";
        public static final String ADDRESS_CN = "网址";
        public static final String TTL = "ttl";
        public static final String TTL_CN = "生存时间";
        public static final String TRANSMITTED = "transmitted";
        public static final String TRANSMITTED_CN = "发送包";
        public static final String RECEIVE = "receive";
        public static final String RECEIVE_CN = "接收包";
        public static final String LOSSRATE = "lossRate";
        public static final String LOSSRATE_CN = "丢包率";
        public static final String RTTMIN = "rttMin";
        public static final String RTTMIN_CN = "最小RTT";
        public static final String RTTAVG = "rttAvg";
        public static final String RTTAVG_CN = "平均RTT";
        public static final String RTTMAX = "rttMax";
        public static final String RTTMAX_CN = "最大RTT";
        public static final String RTTMDEV = "rttMDev";
        public static final String RTTMDEV_CN = "算术平均偏差RTT";
        public static final String ERROR = "status";
        public static final String ERROR_CN = "执行结果";
        public static final String ALLTIME = "allTime";
        public static final String ALLTIME_CN = "总消耗时间";
    }
}
