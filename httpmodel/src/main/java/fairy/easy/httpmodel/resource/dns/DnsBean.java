package fairy.easy.httpmodel.resource.dns;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.List;

import fairy.easy.httpmodel.resource.base.BaseBean;


public class DnsBean extends BaseBean {

    private List<JSONObject> dnsServer;

    private List<JSONObject> method;

    public List<JSONObject> getMethod() {
        return method;
    }

    public void setMethod(List<JSONObject> method) {
        this.method = method;
    }

    public List<JSONObject> getDnsServer() {
        return dnsServer;
    }

    public void setDnsServer(List<JSONObject> dnsServer) {
        this.dnsServer = dnsServer;
    }

    private int totalTime;

    private int status;

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

    @Override
    protected JSONObject toJSONObject() {
        try {
            jsonObject.put(isChina() ? DnsData.TOTALTIME_CN : DnsData.TOTALTIME, totalTime + "ms");
            jsonObject.put(isChina() ? DnsData.STATUS_CN : DnsData.STATUS, status);
            jsonObject.put(isChina() ? DnsData.LOCAL_SERVER_DNS_CN : DnsData.LOCAL_SERVER_DNS, new JSONArray(dnsServer));
            jsonObject.put(isChina() ? DnsData.DNS_STRATEGY_CN : DnsData.DNS_STRATEGY, new JSONArray(method));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return super.toJSONObject();
    }

    public static class DnsData extends BaseBean {
        public static final String TOTALTIME = "totalTime";
        public static final String TOTALTIME_CN = "总消耗时间";
        public static final String STATUS = "status";
        public static final String STATUS_CN = "执行结果";
        public static final String LOCAL_SERVER_DNS = "localDns";
        public static final String LOCAL_SERVER_DNS_CN = "本地DNS服务器";
        public static final String DNS_STRATEGY = "strategy";
        public static final String DNS_STRATEGY_CN = "解析策略";

        public static final String IP = "ip";
        public static final String IP_CN = "具体IP";

        public static final String PARAM = "param";
        public static final String PARAM_CN = "归属地";

        public static final String STRATEGY_PARAM = "strategyParam";
        public static final String STRATEGY_PARAM_CN = "策略内容";
        public static final String STRATEGY_ADDRESS = "strategyAddress";
        public static final String STRATEGY_ADDRESS_CN = "域名";
        public static final String STRATEGY_STATUS = "strategyStatus";
        public static final String STRATEGY_STATUS_CN = "结果";
        public static final String STRATEGY_TIME = "strategyTime";
        public static final String STRATEGY_TIME_CN = "用时";
        public static final String STRATEGY_IP = "strategyIp";
        public static final String STRATEGY_IP_CN = "IP地址";

    }

    public static class DnsServerBean extends BaseBean {
        private String ip;
        private String param;

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getParam() {
            return param;
        }

        public void setParam(String param) {
            this.param = param;
        }

        @Override
        protected JSONObject toJSONObject() {
            try {
                jsonObject.put(isChina() ? DnsData.IP_CN : DnsData.IP, ip);
                jsonObject.put(isChina() ? DnsData.PARAM_CN : DnsData.PARAM, param);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return super.toJSONObject();
        }
    }

    public static class DnsMethodBean extends BaseBean {
        private String dnsMethod = "*";
        private String address = "*";
        private int status;
        private int time;
        private List<JSONObject> dnsIp;

        public String getDnsMethod() {
            return dnsMethod;
        }

        public void setDnsMethod(String dnsMethod) {
            this.dnsMethod = dnsMethod;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public List<JSONObject> getDnsIp() {
            return dnsIp;
        }

        public void setDnsIp(List<JSONObject> dnsIp) {
            this.dnsIp = dnsIp;
        }

        @Override
        protected JSONObject toJSONObject() {
            try {
                jsonObject.put(isChina() ? DnsData.STRATEGY_PARAM_CN : DnsData.STRATEGY_PARAM, dnsMethod);
                jsonObject.put(isChina() ? DnsData.STRATEGY_ADDRESS_CN : DnsData.STRATEGY_ADDRESS, address);
                jsonObject.put(isChina() ? DnsData.STRATEGY_STATUS_CN : DnsData.STRATEGY_STATUS, status);
                jsonObject.put(isChina() ? DnsData.STRATEGY_TIME_CN : DnsData.STRATEGY_TIME, time + "ms");
                jsonObject.put(isChina() ? DnsData.STRATEGY_IP_CN : DnsData.STRATEGY_IP, new JSONArray(dnsIp));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return super.toJSONObject();
        }
    }


}
