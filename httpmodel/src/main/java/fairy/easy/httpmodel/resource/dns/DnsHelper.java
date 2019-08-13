package fairy.easy.httpmodel.resource.dns;


import android.text.TextUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fairy.easy.httpmodel.HttpModelHelper;
import fairy.easy.httpmodel.resource.HttpType;
import fairy.easy.httpmodel.resource.Input;
import fairy.easy.httpmodel.resource.base.BaseData;
import fairy.easy.httpmodel.server.Lookup;
import fairy.easy.httpmodel.server.Record;
import fairy.easy.httpmodel.server.SimpleResolver;
import fairy.easy.httpmodel.util.Base;
import fairy.easy.httpmodel.util.Dns;
import fairy.easy.httpmodel.util.HttpLog;
import fairy.easy.httpmodel.util.LogTime;
import fairy.easy.httpmodel.util.Output;

public class DnsHelper {

    public static final String NORMAL_STRATEGY = "默认策略";
    public static final String DNS_STRATEGY = "指定DNS";


    public static void getDnsParam() throws Exception {
        long startTime = LogTime.getLogTime();
        DnsBean dnsBean = new DnsBean();
        List<String> listDns = Arrays.asList(Dns.readDnsServers(HttpModelHelper.getInstance().getContext()));
        dnsBean.setStatus(listDns.size() == 0 ? BaseData.HTTP_ERROR : BaseData.HTTP_SUCCESS);
        dnsBean.setDnsServer(getServerBean(listDns));
        dnsBean.setMethod(getStrategyMethod(listDns));
        dnsBean.setTotalTime(LogTime.getElapsedMillis(startTime));
        HttpLog.i("NsLookup is end");
        Input.onSuccess(HttpType.NSLOOKUP, dnsBean.toJSONObject());
    }

    private static List<JSONObject> getStrategyMethod(List<String> listDns) {
        List<JSONObject> strategyMethod = new ArrayList<>();
        strategyMethod.add(strategyParam(NORMAL_STRATEGY, Dns.mobDNS(Base.getUrlHost())));
        for (String str : listDns) {
            List<String> list = new ArrayList<>();
            try {
                Lookup lookup = new Lookup(Base.getUrlHost());
                SimpleResolver simpleResolver = new SimpleResolver(str);
                lookup.setResolver(simpleResolver);
                lookup.run();
                if (lookup.getResult() == Lookup.SUCCESSFUL) {
                    Record[] records = lookup.getAnswers();
                    for (Record record : records) {
//                        HttpLog.i(record.rdataToString());
                        list.add(record.rdataToString());
                    }
                } else {
                    HttpLog.e(lookup.getErrorString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            strategyMethod.add(strategyParam(DNS_STRATEGY + str, list));
        }
        return strategyMethod;

    }

    private static JSONObject strategyParam(String method, List<String> list) {
        long startTime = LogTime.getLogTime();
        DnsBean.DnsMethodBean dnsMethodBean = new DnsBean.DnsMethodBean();
        dnsMethodBean.setStatus(list.size() == 0 ? BaseData.HTTP_ERROR : BaseData.HTTP_SUCCESS);
        dnsMethodBean.setAddress(Base.getUrlHost());
        dnsMethodBean.setDnsMethod(method);
        dnsMethodBean.setDnsIp(getServerBean(list));
        dnsMethodBean.setTime(LogTime.getElapsedMillis(startTime));
        return dnsMethodBean.toJSONObject();

    }

    private static List<JSONObject> getServerBean(List<String> list) {
        final List<JSONObject> listServer = new ArrayList<>();
        for (String ip : list) {
            DnsBean.DnsServerBean dnsServerBean = new DnsBean.DnsServerBean();
            if (TextUtils.isEmpty(ip) || "*".equals(ip)) {
                dnsServerBean.setIp("*");
                dnsServerBean.setParam("未知");
            } else if (ip.startsWith("192.168")) {
                dnsServerBean.setIp(ip);
                dnsServerBean.setParam("私网地址");
            } else {
                if (ip.contains("(") && ip.contains(")")) {
                    ip = ip.substring(ip.indexOf("(") + 1, ip.indexOf(")"));
                }
                dnsServerBean.setIp(ip);
                Output.getOutPutIpCountry(new Output.OutPutListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        dnsServerBean.setParam(s);
                    }

                    @Override
                    public void onFail(Exception e) {
                        dnsServerBean.setParam("未知");
                    }
                }, ip);

            }
            listServer.add(dnsServerBean.toJSONObject());
        }
        return listServer;
    }
}
