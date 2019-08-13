package fairy.easy.httpmodel.resource.http;

import android.annotation.SuppressLint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import fairy.easy.httpmodel.resource.base.BaseBean;


public class HttpBean extends BaseBean {
    private String address;
    private int totalTime;
    private int speed;
    private int responseCode;
    private int time;
    private double size;
    private String headerServer = "*";
    private String checkHeaderServer = "*";
    private boolean isJump;
    private List<Map<Object, String>> header;
    private int error;

    public HttpBean() {
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public String getHeaderServer() {
        return headerServer;
    }

    public void setHeaderServer(String headerServer) {
        this.headerServer = headerServer;
    }

    public String getCheckHeaderServer() {
        return checkHeaderServer;
    }

    public void setCheckHeaderServer(String checkHeaderServer) {
        this.checkHeaderServer = checkHeaderServer;
    }

    public boolean isJump() {
        return isJump;
    }

    public void setJump(boolean jump) {
        isJump = jump;
    }

    public List<Map<Object, String>> getHeader() {
        return header;
    }

    public void setHeader(List<Map<Object, String>> header) {
        this.header = header;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    @SuppressLint("DefaultLocale")
    @Override
    protected JSONObject toJSONObject() {
        try {
            jsonObject.put(isChina() ? HttpData.ERROR_CN : HttpData.ERROR, error);
            jsonObject.put(isChina() ? HttpData.ADDRESS_CN : HttpData.ADDRESS, address);
            jsonObject.put(isChina() ? HttpData.TIME_CN : HttpData.TIME, time + "ms");
            jsonObject.put(isChina() ? HttpData.TOTALTIME_CN : HttpData.TOTALTIME, totalTime + "ms");
            jsonObject.put(isChina() ? HttpData.SPEED_CN : HttpData.SPEED, speed + "kbps");
            jsonObject.put(isChina() ? HttpData.RESPONSECODE_CN : HttpData.RESPONSECODE, responseCode);
            jsonObject.put(isChina() ? HttpData.SIZE_CN : HttpData.SIZE, String.format("%.1fKB", new BigDecimal(size)));
            jsonObject.put(isChina() ? HttpData.HEADER_SERVER_CN : HttpData.HEADER_SERVER, headerServer);
            jsonObject.put(isChina() ? HttpData.CHECK_HEADER_SERVER_CN : HttpData.CHECK_HEADER_SERVER, checkHeaderServer);
            jsonObject.put(isChina() ? HttpData.ISJUMP_CN : HttpData.ISJUMP, isJump);
            jsonObject.put(isChina() ? HttpData.HEADER_CN : HttpData.HEADER, new JSONArray(header));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return super.toJSONObject();
    }

    public static class HttpData {
        public static final String ERROR = "status";
        public static final String ERROR_CN = "执行结果";
        public static final String ADDRESS = "address";
        public static final String ADDRESS_CN = "网址";
        public static final String TIME = "time";
        public static final String TIME_CN = "用时";
        public static final String TOTALTIME_CN = "总消耗时间";
        public static final String TOTALTIME = "totalTime";
        public static final String SPEED = "speed";
        public static final String SPEED_CN = "速度";
        public static final String RESPONSECODE = "responseCode";
        public static final String RESPONSECODE_CN = "请求状态";
        public static final String SIZE = "size";
        public static final String SIZE_CN = "下载大小";
        public static final String HEADER_SERVER = "headerServer";
        public static final String HEADER_SERVER_CN = "服务器";
        public static final String CHECK_HEADER_SERVER = "checkHeaderServer";
        public static final String CHECK_HEADER_SERVER_CN = "校验服务器";
        public static final String ISJUMP = "isJump";
        public static final String ISJUMP_CN = "跳转";
        public static final String HEADER = "header";
        public static final String HEADER_CN = "返回header";
    }
}
