package fairy.easy.httpmodel.resource.host;

import fairy.easy.httpmodel.resource.HttpType;
import fairy.easy.httpmodel.resource.Input;
import fairy.easy.httpmodel.util.Host;
import fairy.easy.httpmodel.util.HttpLog;
import fairy.easy.httpmodel.util.LogTime;

public class HostHelper {

    public static void getHostParam() throws Exception {
        long startTime = LogTime.getLogTime();
        HostBean hostBean = Host.getLocalHost();
        hostBean.setTotalTime(LogTime.getElapsedMillis(startTime));
        HttpLog.i("Host is end");
        Input.onSuccess(HttpType.HOST, hostBean.toJSONObject());
    }
}
