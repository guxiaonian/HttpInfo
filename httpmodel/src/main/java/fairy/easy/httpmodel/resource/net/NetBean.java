package fairy.easy.httpmodel.resource.net;

import org.json.JSONException;
import org.json.JSONObject;

import fairy.easy.httpmodel.resource.base.BaseBean;

public class NetBean extends BaseBean {

    private boolean isNetworkAvailable;
    private String netWorkType;
    private int wifiRssi;
    private String ip="*";
    private String outputIp="*";
    private String outputIpCountry="*";
    private String dns="*";
    private String outputDns = "*";
    private String outputDnsCountry="*";
    private boolean isRoaming;
    private int mobDbm;
    private int mobAsu;
    private int mobLevel;
    private String mobLevelValue;
    private int wifiLevel;
    private String wifiLevelValue;
    private String mobileType;
    private int totalName;

    public int getTotalName() {
        return totalName;
    }

    public void setTotalName(int totalName) {
        this.totalName = totalName;
    }

    public void setOutputDnsCountry(String outputDnsCountry) {
        this.outputDnsCountry = outputDnsCountry;
    }

    public void setMobileType(String mobileType) {
        this.mobileType = mobileType;
    }

    public void setOutputIpCountry(String outputIpCountry) {
        this.outputIpCountry = outputIpCountry;
    }

    public void setMobLevelValue(String mobLevelValue) {
        this.mobLevelValue = mobLevelValue;
    }

    public void setWifiLevel(int wifiLevel) {
        this.wifiLevel = wifiLevel;
    }

    public void setWifiLevelValue(String wifiLevelValue) {
        this.wifiLevelValue = wifiLevelValue;
    }

    public void setNetworkAvailable(boolean networkAvailable) {
        isNetworkAvailable = networkAvailable;
    }

    public void setNetWorkType(String netWorkType) {
        this.netWorkType = netWorkType;
    }

    public void setWifiRssi(int wifiRssi) {
        this.wifiRssi = wifiRssi;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setOutputIp(String outputIp) {
        this.outputIp = outputIp;
    }

    public void setDns(String dns) {
        this.dns = dns;
    }

    public void setOutputDns(String outputDns) {
        this.outputDns = outputDns;
    }


    public void setRoaming(boolean roaming) {
        isRoaming = roaming;
    }

    public void setMobDbm(int mobDbm) {
        this.mobDbm = mobDbm;
    }

    public void setMobAsu(int mobAsu) {
        this.mobAsu = mobAsu;
    }

    public void setMobLevel(int mobLevel) {
        this.mobLevel = mobLevel;
    }

    public boolean isNetworkAvailable() {
        return isNetworkAvailable;
    }

    public String getNetWorkType() {
        return netWorkType;
    }

    public int getWifiRssi() {
        return wifiRssi;
    }

    public String getIp() {
        return ip;
    }

    public String getOutputIp() {
        return outputIp;
    }

    public String getOutputIpCountry() {
        return outputIpCountry;
    }

    public String getDns() {
        return dns;
    }

    public String getOutputDns() {
        return outputDns;
    }

    public String getOutputDnsCountry() {
        return outputDnsCountry;
    }


    public boolean isRoaming() {
        return isRoaming;
    }

    public int getMobDbm() {
        return mobDbm;
    }

    public int getMobAsu() {
        return mobAsu;
    }

    public int getMobLevel() {
        return mobLevel;
    }

    public String getMobLevelValue() {
        return mobLevelValue;
    }

    public int getWifiLevel() {
        return wifiLevel;
    }

    public String getWifiLevelValue() {
        return wifiLevelValue;
    }

    public String getMobileType() {
        return mobileType;
    }

    public NetBean() {
    }

    @Override
    protected JSONObject toJSONObject() {
        try {
            jsonObject.put(isChina() ? NetData.NETWORK_AVAILABLE_CN : NetData.NETWORK_AVAILABLE, isNetworkAvailable);
            jsonObject.put(isChina() ? NetData.NETWORK_TYPE_CN : NetData.NETWORK_TYPE, netWorkType);
            jsonObject.put(isChina() ? NetData.MOBILE_TYPE_CN : NetData.MOBILE_TYPE, mobileType);
            jsonObject.put(isChina() ? NetData.WIFI_TYPE_CN : NetData.WIFI_TYPE, "WIFI".equals(netWorkType));
            jsonObject.put(isChina() ? NetData.WIFI_RSSI_CN : NetData.WIFI_RSSI,wifiRssi);
            jsonObject.put(isChina() ? NetData.WIFI_LEVEL_CN : NetData.WIFI_LEVEL,wifiLevel);
            jsonObject.put(isChina() ? NetData.WIFI_LEVEL_VALUE_CN : NetData.WIFI_LEVEL_VALUE,wifiLevelValue);
            jsonObject.put(isChina() ? NetData.IP_CN : NetData.IP,ip);
            jsonObject.put(isChina() ? NetData.OUTPUT_IP_CN : NetData.OUTPUT_IP,outputIp);
            jsonObject.put(isChina() ? NetData.OUTPUT_IP_COUNTRY_CN : NetData.OUTPUT_IP_COUNTRY,outputIpCountry);
            jsonObject.put(isChina() ? NetData.DNS_CN : NetData.DNS,dns);
            jsonObject.put(isChina() ? NetData.OUTPUT_DNS_CN : NetData.OUTPUT_DNS,outputDns);
            jsonObject.put(isChina() ? NetData.OUTPUT_DNS_COUNTRY_CN : NetData.OUTPUT_DNS_COUNTRY,outputDnsCountry);
            jsonObject.put(isChina() ? NetData.IS_ROAMING_CN : NetData.IS_ROAMING,isRoaming);
            jsonObject.put(isChina() ? NetData.MOB_ASU_CN : NetData.MOB_ASU,mobAsu);
            jsonObject.put(isChina() ? NetData.MOB_DBM_CN : NetData.MOB_DBM,mobDbm);
            jsonObject.put(isChina() ? NetData.MOB_LEVEL_CN : NetData.MOB_LEVEL,mobLevel);
            jsonObject.put(isChina() ? NetData.MOB_LEVEL_VALUE_CN : NetData.MOB_LEVEL_VALUE,mobLevelValue);
            jsonObject.put(isChina() ? NetData.TOTALTIME_CN : NetData.TOTALTIME,totalName+"ms");

        } catch (JSONException e) {
            //ignore
        }
        return super.toJSONObject();
    }

    public static class NetData {

        public static final String NETWORK_AVAILABLE = "isNetworkAvailable";
        public static final String NETWORK_AVAILABLE_CN = "网络状态";
        public static final String NETWORK_TYPE = "netWorkType";
        public static final String NETWORK_TYPE_CN = "网络类型";
        public static final String WIFI_TYPE = "isWifiOpen";
        public static final String WIFI_TYPE_CN = "WIFI状态";
        public static final String WIFI_RSSI = "wifiRssi";
        public static final String WIFI_RSSI_CN = "WIFI信号强度";
        public static final String WIFI_LEVEL = "wifiLevel";
        public static final String WIFI_LEVEL_CN = "WIFI信号等级";
        public static final String WIFI_LEVEL_VALUE = "wifiLevelValue";
        public static final String WIFI_LEVEL_VALUE_CN = "WIFI信号评定";
        public static final String IP = "ip";
        public static final String IP_CN = "本地IP";
        public static final String OUTPUT_IP = "outputIp";
        public static final String OUTPUT_IP_CN = "出口IP";
        public static final String OUTPUT_IP_COUNTRY = "outputIpCountry";
        public static final String OUTPUT_IP_COUNTRY_CN = "出口IP归属地";
        public static final String DNS = "dns";
        public static final String DNS_CN = "本地DNS";
        public static final String OUTPUT_DNS = "outputDns";
        public static final String OUTPUT_DNS_CN = "出口DNS";
        public static final String OUTPUT_DNS_COUNTRY = "outputDnsCountry";
        public static final String OUTPUT_DNS_COUNTRY_CN = "出口DNS归属地";
        public static final String MOBILE_TYPE = "mobileType";
        public static final String MOBILE_TYPE_CN = "网络制式";
        public static final String IS_ROAMING = "isRoaming";
        public static final String IS_ROAMING_CN = "漫游状态";
        public static final String MOB_DBM = "mobDbm";
        public static final String MOB_DBM_CN = "手机信号强度";
        public static final String MOB_ASU = "mobAsu";
        public static final String MOB_ASU_CN = "手机信号电平值";
        public static final String MOB_LEVEL = "mobLevel";
        public static final String MOB_LEVEL_CN = "手机信号等级";
        public static final String MOB_LEVEL_VALUE = "mobLevelValue";
        public static final String MOB_LEVEL_VALUE_CN = "手机信号评定";
        public static final String TOTALTIME_CN = "总消耗时间";
        public static final String TOTALTIME = "totalTime";
    }
}
