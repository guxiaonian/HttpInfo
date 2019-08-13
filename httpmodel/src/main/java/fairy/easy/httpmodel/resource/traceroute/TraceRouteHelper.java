package fairy.easy.httpmodel.resource.traceroute;


import android.text.TextUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import fairy.easy.httpmodel.resource.HttpType;
import fairy.easy.httpmodel.resource.Input;
import fairy.easy.httpmodel.resource.base.BaseData;
import fairy.easy.httpmodel.util.Base;
import fairy.easy.httpmodel.util.HttpLog;
import fairy.easy.httpmodel.util.LogTime;
import fairy.easy.httpmodel.util.Output;
import fairy.easy.httpmodel.util.TraceRoute;

public class TraceRouteHelper {

    public static void getTraceRouteParam() throws Exception {
        long startTime = LogTime.getLogTime();
        final TraceRouteBean routeBean = new TraceRouteBean();
        final List<JSONObject> list = new ArrayList<>();
        TraceRoute.getInstance().startTraceRoute(Base.getUrlHost(), new TraceRoute.TraceRouteListener() {
            @Override
            public void onNetTraceUpdated(TraceRouteBean.TraceRouteDataBean traceRouteBean) {
                try {
                    checkIpCountry(traceRouteBean);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                list.add(traceRouteBean.toJSONObject());
            }

            @Override
            public void onNetTraceFinished(boolean status) {
                routeBean.setStatus(status ? BaseData.HTTP_SUCCESS : BaseData.HTTP_ERROR);
                routeBean.setList(list);
            }
        });
        routeBean.setTotalTime(LogTime.getElapsedMillis(startTime));
        HttpLog.i("TraceRoute is end");
        Input.onSuccess(HttpType.TRACE_ROUTE, routeBean.toJSONObject());

    }

    private static void checkIpCountry(final TraceRouteBean.TraceRouteDataBean traceRouteBean) throws Exception {
        String ip = traceRouteBean.getIp();
        if (TextUtils.isEmpty(ip) || "*".equals(ip)) {
            traceRouteBean.setCountry("未知");
        } else if (ip.startsWith("192.168")) {
            traceRouteBean.setCountry("私网地址");
        } else {
            if (ip.contains("(") && ip.contains(")")) {
                ip = ip.substring(ip.indexOf("(") + 1, ip.indexOf(")"));
            }
            Output.getOutPutIpCountry(new Output.OutPutListener<String>() {
                @Override
                public void onSuccess(String s) {
                    traceRouteBean.setCountry(s);
                }

                @Override
                public void onFail(Exception e) {
                    traceRouteBean.setCountry("未知");
                }
            }, ip);

        }

    }
}
