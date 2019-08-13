package fairy.easy.httpmodel.resource.net;

import android.content.Context;

import fairy.easy.httpmodel.HttpModelHelper;
import fairy.easy.httpmodel.resource.HttpType;
import fairy.easy.httpmodel.resource.Input;
import fairy.easy.httpmodel.util.Dns;
import fairy.easy.httpmodel.util.HttpLog;
import fairy.easy.httpmodel.util.LogTime;
import fairy.easy.httpmodel.util.Net;

import static fairy.easy.httpmodel.util.NetWork.isNetworkAvailable;

public class NetHelper {

    public static void getNetParam() throws Exception {
        long startTime = LogTime.getLogTime();
        final NetBean netBean = new NetBean();
        Context context = HttpModelHelper.getInstance().getContext();
        netBean.setNetworkAvailable(isNetworkAvailable(context));
        netBean.setNetWorkType(Net.networkType(context));
        netBean.setMobileType(Net.networkTypeMobile(context));
        netBean.setWifiRssi(Net.getWifiRssi(context));
        netBean.setWifiLevel(Net.calculateSignalLevel(netBean.getWifiRssi()));
        netBean.setWifiLevelValue(Net.checkSignalRssi(netBean.getWifiLevel()));
        netBean.setIp(Net.getClientIp());
        netBean.setDns(Dns.readDnsServers(context).length>0?Dns.readDnsServers(context)[0]:"*");
        Net.getOutPutDns(netBean);
        netBean.setRoaming(Net.checkIsRoaming(context));
        Net.getMobileDbm(context, netBean);
        netBean.setMobLevelValue(Net.checkSignalRssi(netBean.getMobLevel()));
        netBean.setTotalName(LogTime.getElapsedMillis(startTime));
        HttpLog.i("Net is end");
        Input.onSuccess(HttpType.NET, netBean.toJSONObject());
    }


}
