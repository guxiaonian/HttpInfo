package fairy.easy.httpmodel.util;


import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import fairy.easy.httpmodel.resource.base.BaseData;
import fairy.easy.httpmodel.resource.ping.PingBean;


public class Ping {

    private int time;
    private int size;
    private String url;

    private PingBean pingBean;

    public Ping(String url) {
        this(32, 10, url);
    }

    public Ping(int size, String url) {
        this(32, size, url);
    }

    public Ping(int time, int size, String url) {
        this.time = time;
        this.size = size;
        this.url = url;
        this.pingBean = new PingBean();
        pingBean.setAddress(url);
    }

    public PingBean getPingInfo() {
        return getIPFromUrl();
    }

    private PingBean getIPFromUrl() {
        String domain = getDomain(url);
        if (TextUtils.isEmpty(domain)) {
            pingBean.setError(BaseData.HTTP_ERROR);
            return pingBean;
        }
        String pingString = ping(createSimplePingCommand(size, time, domain));
        if (null != pingString) {
            try {
                pingBean.setError(BaseData.HTTP_SUCCESS);
                pingBean.setIp(parseIpFromPing(pingString));
                parseLossFromPing(pingString, pingBean);
                parseDelayFromPing(pingString, pingBean);
                parseTtlFromPing(pingString, pingBean);
            } catch (Exception e) {
                pingBean.setError(BaseData.HTTP_ERROR);
                e.printStackTrace();
            }
        }
        return pingBean;
    }

    private String parseIpFromPing(String paramString) {
        try {
            boolean bool = paramString.contains("ping");
            String localObject = null;
            if (bool) {
                int i = paramString.indexOf("(");
                int j = paramString.indexOf(")");
                localObject = paramString.substring(i + 1, j);
            }
            return localObject;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return null;
    }

    private void parseDelayFromPing(String paramString, PingBean pingBean) {
        try {
            if (paramString.contains("rtt")) {
                String str = paramString.substring(paramString.indexOf("rtt"));
                String[] arrayOfString = str.substring(2 + str.indexOf("=")).split("/");
                pingBean.setRttMin(Float.parseFloat(arrayOfString[0]));
                pingBean.setRttAvg(Float.parseFloat(arrayOfString[1]));
                pingBean.setRttMax(Float.parseFloat(arrayOfString[2]));
                pingBean.setRttMDev(Float.parseFloat(arrayOfString[3].substring(0, arrayOfString[3].indexOf(" ms"))));
            }
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    private void parseTtlFromPing(String paramString, PingBean pingBean) {
        try {
            if (paramString.contains("ttl")) {
                String str1 = paramString.substring(paramString.indexOf("ttl"), paramString.indexOf("\n", paramString.indexOf("ttl")));
                pingBean.setTtl(Integer.parseInt(str1.substring(4, str1.indexOf(" "))));

            }
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    private void parseLossFromPing(String paramString, PingBean pingBean) {
        try {
            if (paramString.contains("statistics")) {
                String str1 = paramString.substring(1 + paramString.indexOf("\n", paramString.indexOf("statistics")));
                String[] arrayOfString = str1.substring(0, str1.indexOf("\n")).split(", ");
                for (String str : arrayOfString) {
                    if (str.contains(" packets transmitted")) {
                        pingBean.setTransmitted(Integer.parseInt(str.substring(0, str.indexOf(" packets transmitted"))));
                    }
                    if (str.contains(" received")) {
                        pingBean.setReceive(Integer.parseInt(str.substring(0, str.indexOf(" received"))));
                    }
                    if (str.contains(" errors")) {
                        pingBean.setError(Integer.parseInt(str.substring(0, str.indexOf(" errors"))));
                    }
                    if (str.contains(" packet loss")) {
                        pingBean.setLossRate(Float.parseFloat(str.substring(0, str.indexOf("%"))));
                    }
                    if (str.contains("time")) {
                        pingBean.setAllTime(Integer.parseInt(str.substring(str.lastIndexOf("e") + 2, str.indexOf("ms"))));
                    }
                }
            }
        } catch (Exception localException) {
            localException.printStackTrace();
        }

    }

    private String getDomain(String url) {
        try {
            URL urlStr = new URL(url);
            return urlStr.getHost();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    private String ping(String command) {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(command);
            InputStream is = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            while (null != (line = reader.readLine())) {
                sb.append(line);
                sb.append("\n");
            }
            reader.close();
            is.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != process) {
                process.destroy();
            }
        }
        return null;
    }

    @SuppressLint("DefaultLocale")
    private String createSimplePingCommand(int count, int timeout, String domain) {
        Object[] arrayOfObject = new Object[3];
        arrayOfObject[0] = count;
        arrayOfObject[1] = timeout;
        arrayOfObject[2] = domain;
        return String.format("/system/bin/ping -c %d -w %d  %s", arrayOfObject);
    }


}
