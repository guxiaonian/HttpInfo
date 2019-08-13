package fairy.easy.httpmodel.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


@SuppressLint("MissingPermission")
public class Dns {

    private static String[] readDnsServersFromConnectionManager(Context context) {
        LinkedList<String> dnsServers = new LinkedList<>();
        if (Build.VERSION.SDK_INT >= 21 && context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                for (Network network : connectivityManager.getAllNetworks()) {
                    NetworkInfo networkInfo = connectivityManager.getNetworkInfo(network);
                    if (networkInfo.getType() == activeNetworkInfo.getType()) {
                        LinkProperties lp = connectivityManager.getLinkProperties(network);
                        for (InetAddress addr : lp.getDnsServers()) {
                            dnsServers.add(addr.getHostAddress());
                        }
                    }
                }
            }
        }
        return dnsServers.isEmpty() ? new String[0] : dnsServers.toArray(new String[dnsServers.size()]);
    }

    private static String[] readDnsServersFromWifiManager(Context context) {
        LinkedList<String> dnsServers = new LinkedList<>();
        try {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            if (wifiManager == null || !wifiManager.isWifiEnabled()) {
                return new String[0];
            }
            DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
            if (dhcpInfo != null) {
                if (dhcpInfo.dns1 != 0) {
                    dnsServers.add(intToIp(dhcpInfo.dns1));
                }
                if (dhcpInfo.dns2 != 0) {
                    dnsServers.add(intToIp(dhcpInfo.dns2));
                }
            }
        } catch (Exception e) {
        }
        return dnsServers.isEmpty() ? new String[0] : dnsServers.toArray(new String[dnsServers.size()]);
    }

    private static String intToIp(int paramInt) {
        return (paramInt & 0xFF) + "." + (0xFF & paramInt >> 8) + "." + (0xFF & paramInt >> 16) + "."
                + (0xFF & paramInt >> 24);
    }


    private static String[] readDnsServersFromCommand() {
        LinkedList<String> dnsServers = new LinkedList<>();
        try {
            Process process = Runtime.getRuntime().exec("getprop");
            InputStream inputStream = process.getInputStream();
            LineNumberReader lnr = new LineNumberReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = lnr.readLine()) != null) {
                int split = line.indexOf("]: [");
                if (split == -1) {
                    continue;
                }
                String property = line.substring(1, split);
                String value = line.substring(split + 4, line.length() - 1);
                if (property.endsWith(".dns")
                        || property.endsWith(".dns1")
                        || property.endsWith(".dns2")
                        || property.endsWith(".dns3")
                        || property.endsWith(".dns4")) {
                    InetAddress ip = InetAddress.getByName(value);
                    if (ip == null) {
                        continue;
                    }
                    value = ip.getHostAddress();
                    if (value == null) {
                        continue;
                    }
                    if (value.length() == 0) {
                        continue;
                    }
                    dnsServers.add(value);
                }
            }
        } catch (IOException e) {
        }
        return dnsServers.isEmpty() ? new String[0] : dnsServers.toArray(new String[dnsServers.size()]);
    }


    private static final String[] DNS_SERVER_PROPERTIES = new String[]{"net.dns1",
            "net.dns2", "net.dns3", "net.dns4"};

    private static String[] readDnsServersFromSystemProperties() {
        SystemProperties.init();
        LinkedList<String> dnsServers = new LinkedList<>();
        for (String property : DNS_SERVER_PROPERTIES) {
            String server = SystemProperties.get(property, "");
            if (server != null && !server.isEmpty()) {
                try {
                    InetAddress ip = InetAddress.getByName(server);
                    if (ip == null) {
                        continue;
                    }
                    server = ip.getHostAddress();
                    if (server == null || server.isEmpty()) {
                        continue;
                    }
                } catch (Throwable throwable) {
                    continue;
                }
                dnsServers.add(server);
            }
        }
        return dnsServers.toArray(new String[dnsServers.size()]);
    }

    private static class SystemProperties {
        private static boolean isReflectInited = false;

        public static void init() {
            if (!isReflectInited) {
                isReflectInited = true;
                try {
                    Class<?> cls = Class.forName("android.os.SystemProperties");
                    setPropertyMethod = cls.getDeclaredMethod("set", new Class<?>[]{String.class, String.class});
                    setPropertyMethod.setAccessible(true);
                    getPropertyMethod = cls.getDeclaredMethod("get", new Class<?>[]{String.class, String.class});
                    getPropertyMethod.setAccessible(true);
                } catch (Throwable throwable) {
                }
            }
        }

        private static Method getPropertyMethod = null;

        public static String get(String property, String defaultValue) {
            String propertyValue = defaultValue;
            if (getPropertyMethod != null) {
                try {
                    propertyValue = (String) getPropertyMethod.invoke(null, property, defaultValue);
                } catch (Throwable throwable) {
                }
            }
            return propertyValue;
        }

        private static Method setPropertyMethod = null;

        public static void set(String property, String value) {
            if (setPropertyMethod != null) {
                try {
                    setPropertyMethod.invoke(null, property, value);
                } catch (Throwable throwable) {
                }
            }
        }
    }

    /**
     * 拿到本地的两个DNS服务器
     *
     * @param context
     * @return
     */
    public static String[] readDnsServers(Context context) {
        String[] dnsServers = readDnsServersFromConnectionManager(context);
        if (dnsServers.length == 0) {
            dnsServers = readDnsServersFromSystemProperties();
            if (dnsServers.length == 0) {
                dnsServers = readDnsServersFromCommand();
            }
        }
        return dnsServers;
    }


    /**
     * 默认策略解析的ip
     *
     * @param host
     * @return
     */
    public static List<String> mobDNS(String host) {
        List<String> list = new ArrayList<>();
        InetAddress[] addresses = new InetAddress[0];
        try {
            addresses = InetAddress.getAllByName(host);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (InetAddress inetAddress : addresses) {
            if (inetAddress instanceof Inet4Address) {
                list.add(inetAddress.getHostAddress());
            }
        }
        return list;

    }
}
