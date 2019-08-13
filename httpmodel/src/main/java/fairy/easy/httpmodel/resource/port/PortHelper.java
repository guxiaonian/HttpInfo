package fairy.easy.httpmodel.resource.port;

import org.json.JSONObject;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import fairy.easy.httpmodel.HttpModelHelper;
import fairy.easy.httpmodel.resource.HttpType;
import fairy.easy.httpmodel.resource.Input;
import fairy.easy.httpmodel.resource.base.BaseData;
import fairy.easy.httpmodel.util.Base;
import fairy.easy.httpmodel.util.HttpLog;
import fairy.easy.httpmodel.util.LogTime;
import fairy.easy.httpmodel.util.PortScan;

public class PortHelper {

    public static void getPortParam() throws Exception {
        long startTime = LogTime.getLogTime();
        final PortBean portBean = new PortBean();
        portBean.setAddress(HttpModelHelper.getInstance().getAddress());
        final List<JSONObject> list = new ArrayList<>();
        try {

            PortScan.onAddress(Base.getUrlHost())
                    .setPortsPrivileged()
                    .setMethodTCP()
                    .doScan(new PortScan.PortListener() {
                        @Override
                        public void onResult(PortBean.PortNetBean portNetBean) {
                            if (portNetBean.isConnected()) {
                                list.add(portNetBean.toJSONObject());
                            }
                        }


                        @Override
                        public void onFinished(ArrayList<Integer> openPorts) {
                            portBean.setStatus(BaseData.HTTP_SUCCESS);
                            portBean.setPortNetBeans(list);

                        }
                    });
        } catch (UnknownHostException e) {
            portBean.setStatus(BaseData.HTTP_ERROR);
            e.printStackTrace();
        }
        portBean.setTotalTime(LogTime.getElapsedMillis(startTime));
        HttpLog.i("PortScan is end");
        Input.onSuccess(HttpType.PORT_SCAN, portBean.toJSONObject());

    }
}
