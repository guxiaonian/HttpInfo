package fairy.easy.httpmodel.resource.port;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import fairy.easy.httpmodel.resource.base.BaseBean;


public class PortBean extends BaseBean {

    private String address="*";
    private List<JSONObject> portNetBeans;
    private int status;
    private int totalTime;

    public PortBean() {
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<JSONObject> getPortNetBeans() {
        return portNetBeans;
    }

    public void setPortNetBeans(List<JSONObject> portNetBeans) {
        this.portNetBeans = portNetBeans;
    }

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

    @Override
    protected JSONObject toJSONObject() {
        try {
            jsonObject.put(isChina() ? PortData.ADDRESS_CN :PortData.ADDRESS,address);
            jsonObject.put(isChina() ? PortData.STATUS_CN :PortData.STATUS,status);
            jsonObject.put(isChina() ? PortData.TOTALTIME_CN :PortData.TOTALTIME,totalTime+"ms");
            jsonObject.put(isChina() ? PortData.PORT_NET_CN :PortData.PORT_NET,new JSONArray(portNetBeans));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return super.toJSONObject();
    }

    public static class PortData{
        public static final String ADDRESS = "address";
        public static final String ADDRESS_CN = "网址";
        public static final String STATUS="status";
        public static final String STATUS_CN="执行结果";
        public static final String TOTALTIME="totalTime";
        public static final String TOTALTIME_CN="总消耗时间";
        public static final String PORT_NET="portNet";
        public static final String PORT_NET_CN="具体信息";

        public static final String DELAY="delay";
        public static final String DELAY_CN="扫描时间";
        public static final String ISCONNECTED="isConnected";
        public static final String ISCONNECTED_CN="是否开放";
        public static final String PORT="port";
        public static final String PORT_CN="端口号";
    }

    public static class PortNetBean extends BaseBean {
        private long delay;
        private boolean isConnected;
        private int port;

        public PortNetBean() {

        }

        public long getDelay() {
            return delay;
        }

        public void setDelay(long delay) {
            this.delay = delay;
        }

        public boolean isConnected() {
            return isConnected;
        }

        public void setConnected(boolean connected) {
            isConnected = connected;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        @Override
        protected JSONObject toJSONObject() {
            try {
                jsonObject.put(isChina() ? PortData.DELAY_CN :PortData.DELAY,delay+"ms");
                jsonObject.put(isChina() ? PortData.ISCONNECTED_CN :PortData.ISCONNECTED,isConnected);
                jsonObject.put(isChina() ? PortData.PORT_CN :PortData.PORT,port);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return super.toJSONObject();
        }
    }
}
