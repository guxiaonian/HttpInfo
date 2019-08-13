package fairy.easy.httpmodel.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import fairy.easy.httpmodel.resource.port.PortBean;


public class PortScanTCP {

    private PortScanTCP() {
    }


    public static PortBean.PortNetBean scanAddress(InetAddress ia, int portNo, int timeoutMillis) {
        PortBean.PortNetBean portNetBean = new PortBean.PortNetBean();
        long time = System.currentTimeMillis();
        Socket s = null;
        try {
            s = new Socket();
            s.connect(new InetSocketAddress(ia, portNo), timeoutMillis);
            portNetBean.setConnected(true);
        } catch (IOException e) {
            //ignore
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        portNetBean.setPort(portNo);
        portNetBean.setDelay(System.currentTimeMillis() - time);
        return portNetBean;
    }

}
