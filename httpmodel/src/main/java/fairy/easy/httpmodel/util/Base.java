package fairy.easy.httpmodel.util;

import java.net.MalformedURLException;
import java.net.URL;

import fairy.easy.httpmodel.HttpModelHelper;

public class Base {

    public static boolean checkUrl(String address) {
        try {
            URL url = new URL(address);
            return true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static String getUrlHost() {
        String address = HttpModelHelper.getInstance().getAddress();
        try {
            URL urlStr = new URL(address);
            return urlStr.getHost();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return address;
    }
}
