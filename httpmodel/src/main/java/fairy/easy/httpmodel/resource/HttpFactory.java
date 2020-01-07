package fairy.easy.httpmodel.resource;



import java.util.LinkedList;
import java.util.List;

import fairy.easy.httpmodel.HttpModelHelper;
import fairy.easy.httpmodel.resource.dns.DnsHelper;
import fairy.easy.httpmodel.resource.host.HostHelper;
import fairy.easy.httpmodel.resource.http.HttpHelper;
import fairy.easy.httpmodel.resource.index.IndexHelper;
import fairy.easy.httpmodel.resource.mtu.MtuHelper;
import fairy.easy.httpmodel.resource.net.NetHelper;
import fairy.easy.httpmodel.resource.ping.PingHelper;
import fairy.easy.httpmodel.resource.port.PortHelper;
import fairy.easy.httpmodel.resource.traceroute.TraceRouteHelper;
import fairy.easy.httpmodel.util.HttpLog;

public class HttpFactory implements Factory {

    public HttpFactory() {
    }

    private List<HttpType> httpTypes = new LinkedList<>();


    @Override
    public void getData() {
        if (HttpModelHelper.getInstance().getAddress() == null) {
            HttpLog.e("Please init HttpModelHelper first");
            return;
        }
        for (HttpType httpType : httpTypes) {
            switch (httpType) {
                case INDEX:
                    HttpLog.i("Index is start");
                    try {
                        IndexHelper.getIndexParam();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case NET:
                    HttpLog.i("Net is start");
                    try {
                        NetHelper.getNetParam();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case PING:
                    HttpLog.i("Ping is start");
                    try {
                        PingHelper.getPingParam();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case HTTP:
                    HttpLog.i("Http is start");
                    try {
                        HttpHelper.getHttpParam();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case HOST:
                    HttpLog.i("Host is start");
                    try {
                        HostHelper.getHostParam();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case PORT_SCAN:
                    HttpLog.i("PortScan is start");
                    try {
                        PortHelper.getPortParam();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case MTU_SCAN:
                    HttpLog.i("MtuScan is start");
                    try {
                        MtuHelper.getMtuParam();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case TRACE_ROUTE:
                    HttpLog.i("TraceRoute is start");
                    try {
                        TraceRouteHelper.getTraceRouteParam();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case NSLOOKUP:
                    HttpLog.i("NsLookup is start");
                    try {
                        DnsHelper.getDnsParam();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
        httpTypes.clear();

    }

    public int getTypeSize() {
        return size;
    }

    public HttpFactory addAll() {
        httpTypes.add(HttpType.INDEX);
        httpTypes.add(HttpType.NET);
        httpTypes.add(HttpType.PING);
        httpTypes.add(HttpType.HTTP);
        httpTypes.add(HttpType.HOST);
        httpTypes.add(HttpType.MTU_SCAN);
        httpTypes.add(HttpType.PORT_SCAN);
        httpTypes.add(HttpType.TRACE_ROUTE);
        httpTypes.add(HttpType.NSLOOKUP);
        return this;
    }


    public HttpFactory addType( HttpType httpType) {
        httpTypes.add(httpType);
        return this;
    }

    private int size;

    public HttpModelHelper build() {
        size=httpTypes.size();
        return HttpModelHelper.getInstance();
    }
}