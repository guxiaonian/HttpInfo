package fairy.easy.httpmodel.resource.http;

import android.net.TrafficStats;

import fairy.easy.httpmodel.HttpModelHelper;
import fairy.easy.httpmodel.resource.HttpType;
import fairy.easy.httpmodel.resource.Input;
import fairy.easy.httpmodel.util.Http;
import fairy.easy.httpmodel.util.HttpLog;
import fairy.easy.httpmodel.util.LogTime;

public class HttpHelper {

    public static void getHttpParam() throws Exception {
        long startTime = LogTime.getLogTime();
        Http http = new Http(HttpModelHelper.getInstance().getAddress());
        long totalTraffic=TrafficStats.getTotalRxBytes();
        HttpBean httpBean = http.getHttpInfo();
        int  time=LogTime.getElapsedMillis(startTime);
        //网速单位为Bytes B/ms 转变成bpms==kbps
        httpBean.setSpeed((int) (TrafficStats.getTotalRxBytes()-totalTraffic)/time*8);
        httpBean.setTime(time);
        http.getResponseServer();
        httpBean.setTotalTime(LogTime.getElapsedMillis(startTime));
        HttpLog.i("Http is end");
        Input.onSuccess(HttpType.HTTP, httpBean.toJSONObject());
    }
}
