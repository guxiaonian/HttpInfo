package fairy.easy.httpmodel.resource.traceroute;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import fairy.easy.httpmodel.resource.base.BaseBean;

public class TraceRouteBean extends BaseBean {
    private int status;
    private int totalTime;
    private List<JSONObject> list;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public List<JSONObject> getList() {
        return list;
    }

    public void setList(List<JSONObject> list) {
        this.list = list;
    }

    @Override
    protected JSONObject toJSONObject() {
        try {
            jsonObject.put(isChina() ? TraceRouteData.STATUS_CN :TraceRouteData.STATUS, status);
            jsonObject.put(isChina() ? TraceRouteData.TOTALTIME_CN :TraceRouteData.TOTALTIME, totalTime + "ms");
            jsonObject.put(isChina() ? TraceRouteData.TRACEROUTE_DATA_CN :TraceRouteData.TRACEROUTE_DATA, new JSONArray(list));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return super.toJSONObject();
    }

    public static class TraceRouteData {
        public static final String STATUS = "status";
        public static final String STATUS_CN = "执行结果";
        public static final String TOTALTIME = "totalTime";
        public static final String TOTALTIME_CN = "总消耗时间";
        public static final String TRACEROUTE_DATA = "traceRoute";
        public static final String TRACEROUTE_DATA_CN = "扫描结果";

        public static final String HOP = "ttl";
        public static final String HOP_CN = "生存时间";
        public static final String IP = "ip";
        public static final String IP_CN = "IP地址";
        public static final String TIME = "time";
        public static final String TIME_CN = "扫描时间";
        public static final String COUNTRY = "address";
        public static final String COUNTRY_CN = "IP归属地";
    }

    public static class TraceRouteDataBean extends BaseBean {
        private int hop;
        private String ip = "*";
        private String time = "*";
        private String country = "*";

        public int getHop() {
            return hop;
        }

        public void setHop(int hop) {
            this.hop = hop;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        @Override
        protected JSONObject toJSONObject() {
            try {
                jsonObject.put(isChina() ? TraceRouteData.HOP_CN :TraceRouteData.HOP, hop);
                jsonObject.put(isChina() ? TraceRouteData.IP_CN :TraceRouteData.IP, ip);
                jsonObject.put(isChina() ? TraceRouteData.TIME_CN :TraceRouteData.TIME, time.replace(" ",""));
                jsonObject.put(isChina() ? TraceRouteData.COUNTRY_CN :TraceRouteData.COUNTRY, country);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return super.toJSONObject();
        }
    }
}
