package fairy.easy.httpmodel.util;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

import fairy.easy.httpmodel.resource.port.PortBean;


public class PortScanUDP {

    private PortScanUDP() {
    }


    public static PortBean.PortNetBean scanAddress(InetAddress ia, int portNo, int timeoutMillis) {
        PortBean.PortNetBean portNetBean = new PortBean.PortNetBean();
        Long time = System.currentTimeMillis();
        try {
            byte[] bytes = new byte[128];
            DatagramPacket dp = new DatagramPacket(bytes, bytes.length);

            DatagramSocket ds = new DatagramSocket();
            ds.setSoTimeout(timeoutMillis);
            ds.connect(ia, portNo);
            ds.send(dp);
            ds.isConnected();
            ds.receive(dp);
            ds.close();
            portNetBean.setConnected(true);
        } catch (SocketTimeoutException e) {

        } catch (Exception ignore) {

        }
        portNetBean.setPort(portNo);
        portNetBean.setDelay(System.currentTimeMillis() - time);
        return portNetBean;
    }

}
