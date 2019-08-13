package fairy.easy.httpmodel.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fairy.easy.httpmodel.resource.traceroute.TraceRouteBean;


public class TraceRoute {

    private static TraceRoute instance;

    private TraceRoute() {
    }


    private static class TraceRouteLoader {
        private static final TraceRoute INSTANCE = new TraceRoute();
    }

    public static TraceRoute getInstance() {
        return TraceRouteLoader.INSTANCE;
    }


    public interface TraceRouteListener {
        void onNetTraceUpdated(TraceRouteBean.TraceRouteDataBean traceRouteBean);

        void onNetTraceFinished(boolean status);
    }


    public void startTraceRoute(String host, TraceRouteListener traceRouteListener) {
        TraceTask trace = new TraceTask(host, 1);
        execTrace(trace, traceRouteListener);

    }

    public void resetInstance() {
        if (instance != null) {
            instance = null;
        }
    }

    private static final String MATCH_PING_IP = "(?<=from ).*(?=: icmp_seq=1 ttl=)";
    private static final String MATCH_PING_TIME = "(?<=time=).*?ms";


    private String execPing(PingTask ping) {
        Process process = null;
        String str = "";
        BufferedReader reader = null;
        try {
            process = Runtime.getRuntime().exec("ping -c 1 " + ping.getHost());
            reader = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                str += line;
            }
            reader.close();
            process.waitFor();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                process.destroy();
            } catch (Exception e) {
            }
        }
        return str;
    }


    private void execTrace(TraceTask trace, TraceRouteListener traceRouteListener) {
        Pattern patternIp = Pattern.compile(MATCH_PING_IP);
        Pattern patternTime = Pattern.compile(MATCH_PING_TIME);
        boolean statusType = false;
        Process process = null;
        BufferedReader reader = null;
        boolean finish = false;
        try {
            while (!finish && trace.getHop() < 30) {
                StringBuilder str = new StringBuilder();
                String command = "ping -c 1 -t " + trace.getHop() + " "
                        + trace.getHost();

                process = Runtime.getRuntime().exec(command);
                reader = new BufferedReader(new InputStreamReader(
                        process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    str.append(line);
                }
                reader.close();
                process.waitFor();
                TraceRouteBean.TraceRouteDataBean traceRouteBean = new TraceRouteBean.TraceRouteDataBean();
                if (str.toString().contains("From") && str.toString().contains("icmp_seq=1")) {
                    String from = str.substring(str.indexOf("From"));
                    String ipStr = from.substring(0, from.indexOf("icmp_seq=1"));
                    if (ipStr.contains("(") && ipStr.contains(")")) {
                        ipStr = ipStr.substring(ipStr.indexOf("(") + 1, ipStr.indexOf(")"));
                    } else {
                        ipStr = ipStr.substring(ipStr.indexOf("From") + 5, ipStr.indexOf(":"));
                    }
                    String pingIp = ipStr;
                    PingTask pingTask = new PingTask(pingIp);

                    String status = execPing(pingTask);
                    if (status.length() == 0) {
                        finish = true;
                    } else {
                        Matcher matcherTime = patternTime.matcher(status);
                        if (matcherTime.find()) {
                            String time = matcherTime.group();
                            traceRouteBean.setHop(trace.getHop());
                            traceRouteBean.setIp(pingIp);
                            traceRouteBean.setTime(time);
                        } else {
                            traceRouteBean.setHop(trace.getHop());
                            traceRouteBean.setIp(pingIp);
                        }
                        statusType = true;
                        traceRouteListener.onNetTraceUpdated(traceRouteBean);
                        trace.setHop(trace.getHop() + 1);
                    }

                } else {
                    Matcher matchPingIp = patternIp.matcher(str.toString());
                    if (matchPingIp.find()) {
                        String pingIp = matchPingIp.group();
                        Matcher matcherTime = patternTime.matcher(str.toString());
                        if (matcherTime.find()) {
                            String time = matcherTime.group();
                            traceRouteBean.setHop(trace.getHop());
                            traceRouteBean.setIp(pingIp);
                            traceRouteBean.setTime(time);
                            statusType = true;
                            traceRouteListener.onNetTraceUpdated(traceRouteBean);
                        }
                        finish = true;
                    } else {
                        if (str.length() == 0) {
                            finish = true;
                        } else {
                            traceRouteBean.setHop(trace.getHop());
                            trace.setHop(trace.getHop() + 1);
                            statusType = true;
                        }
                        traceRouteListener.onNetTraceUpdated(traceRouteBean);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                process.destroy();
            } catch (Exception e) {
            }
        }

        traceRouteListener.onNetTraceFinished(statusType);
    }

    private class PingTask {

        private String host;
        private static final String MATCH_PING_HOST_IP = "(?<=\\().*?(?=\\))";

        public String getHost() {
            return host;
        }

        public PingTask(String host) {
            super();
            this.host = host;
            Pattern p = Pattern.compile(MATCH_PING_HOST_IP);
            Matcher m = p.matcher(host);
            if (m.find()) {
                this.host = m.group();
            }

        }
    }

    private class TraceTask {
        private final String host;
        private int hop;

        public TraceTask(String host, int hop) {
            super();
            this.host = host;
            this.hop = hop;
        }

        public String getHost() {
            return host;
        }

        public int getHop() {
            return hop;
        }

        public void setHop(int hop) {
            this.hop = hop;
        }
    }
}
