package fairy.easy.httpmodel.resource.ping;

import fairy.easy.httpmodel.HttpModelHelper;
import fairy.easy.httpmodel.resource.HttpType;
import fairy.easy.httpmodel.resource.Input;
import fairy.easy.httpmodel.util.HttpLog;
import fairy.easy.httpmodel.util.Ping;

public class PingHelper {

    public static void getPingParam() throws Exception {
        Ping ping = new Ping(HttpModelHelper.getInstance().getAddress());
        PingBean pingBean = ping.getPingInfo();
        HttpLog.i("Ping is end");
        Input.onSuccess(HttpType.PING, pingBean.toJSONObject());
    }
}
