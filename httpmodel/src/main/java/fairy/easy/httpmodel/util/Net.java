package fairy.easy.httpmodel.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellSignalStrengthCdma;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.CellSignalStrengthLte;
import android.telephony.CellSignalStrengthWcdma;
import android.telephony.TelephonyManager;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.List;

import fairy.easy.httpmodel.HttpModelHelper;
import fairy.easy.httpmodel.model.HttpModel;
import fairy.easy.httpmodel.model.ModelLoader;
import fairy.easy.httpmodel.model.RequestMethod;
import fairy.easy.httpmodel.resource.base.BaseData;
import fairy.easy.httpmodel.resource.net.NetBean;

@SuppressLint("MissingPermission")
public class Net {

    public static String networkType(Context context) {
        ConnectivityManager manager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager != null) {
            NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
                return "WIFI";
            }
        }
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context
                .TELEPHONY_SERVICE);
        if (telephonyManager == null) {
            return "Other";
        }
        int networkType = telephonyManager.getNetworkType();
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return "2G";
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return "3G";
            case TelephonyManager.NETWORK_TYPE_LTE:
                return "4G";
            default:
                return "Other";

        }
    }

    public static String networkTypeMobile(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context
                .TELEPHONY_SERVICE);
        if (telephonyManager == null || !hasSimCard(context)) {
            return "Other";
        }
        int networkType = telephonyManager.getNetworkType();
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return "2G_GPRS";
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return "2G_EDGE";
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return "2G_CDMA";
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return "2G_1xRTT";
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return "2G_IDEN";
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return "3G_UMTS";
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return "3G_EVDO_0";
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return "3G_EVDO_A";
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return "3G_HSDPA";
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return "3G_HSUPA";
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return "3G_HSPA";
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                return "3G_EVDO_B";
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                return "3G_EHRPD";
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return "3G_SHPAP";
            case TelephonyManager.NETWORK_TYPE_LTE:
                return "4G_LTE";
            default:
                return "Other";

        }
    }

    public static boolean hasSimCard(Context context) {
        boolean result = true;
        try {
            TelephonyManager telMgr = (TelephonyManager)
                    context.getSystemService(Context.TELEPHONY_SERVICE);
            int simState = telMgr.getSimState();
            switch (simState) {
                case TelephonyManager.SIM_STATE_ABSENT:
                    result = false;
                    break;
                case TelephonyManager.SIM_STATE_UNKNOWN:
                    result = false;
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            //ignore
        }
        return result;
    }

    @SuppressLint("MissingPermission")
    public static int getWifiRssi(Context context) {
        try {
            WifiManager mWifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            if (mWifiManager != null) {
                WifiInfo mWifiInfo = mWifiManager.getConnectionInfo();
                return mWifiInfo.getRssi();
            }
        } catch (Exception e) {
            //ignore
        }
        return 0;
    }

    private static final int MIN_RSSI = -100;
    private static final int MAX_RSSI = -55;

    public static int calculateSignalLevel(int rssi) {
        if (rssi <= MIN_RSSI) {
            return 0;
        } else if (rssi >= MAX_RSSI) {
            return 4;
        } else {
            float inputRange = (MAX_RSSI - MIN_RSSI);
            float outputRange = (4);
            return (int) ((float) (rssi - MIN_RSSI) * outputRange / inputRange);
        }
    }


    public static String checkSignalRssi(int level) {
        String levelStr = "无信号";
        if (level > 4) {
            level = 4;
        }
        switch (level) {
            case 0:
                levelStr = "无信号";
                break;
            case 1:
                levelStr = "信号差";
                break;
            case 2:
                levelStr = "信号中";
                break;
            case 3:
                levelStr = "信号良";
                break;
            case 4:
                levelStr = "信号优";
                break;
            default:
                break;
        }
        return levelStr;
    }

    public static String getClientIp() {
        String localIp = "";
        try {
            Enumeration localEnumeration = NetworkInterface.getNetworkInterfaces();
            if (localEnumeration != null) {
                while (localEnumeration.hasMoreElements()) {
                    Enumeration localEnumerationNew = ((NetworkInterface) localEnumeration.nextElement()).getInetAddresses();
                    while (localEnumerationNew.hasMoreElements()) {
                        InetAddress inetAddress = (InetAddress) localEnumerationNew.nextElement();
                        if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address && !inetAddress.isLinkLocalAddress()) {
                            return inetAddress.getHostAddress();
                        }
                    }
                }
            }
        } catch (Exception e) {
            //ignore
        }
        return localIp;
    }

    public static void getOutPutDns(final NetBean netBean) {
        final ModelLoader modelLoader = HttpModelHelper.getInstance().getModelLoader();
        modelLoader.setHttpModel(new HttpModel(BaseData.OUTPUT_DNS_URL, RequestMethod.GET, null));
        modelLoader.loadData(new ModelLoader.DataCallback<String>() {
            @Override
            public void onDataReady(@Nullable String data) {
                HttpLog.i("outputDns html info success:" + data);
                String url = data.substring(data.indexOf("src=") + 4, data.lastIndexOf("frameborder")).replaceAll("'", "").replaceAll(" ", "");
                modelLoader.setHttpModel(new HttpModel(url, RequestMethod.GET, null));
                modelLoader.loadData(new ModelLoader.DataCallback<String>() {
                    @Override
                    public void onDataReady(@Nullable String data) {
                        HttpLog.i("outputDns info success:" + data);
                        String dataIp = data.substring(data.indexOf("您的IP地址信息") + 10);
                        String dataAddress = dataIp.substring(0, dataIp.indexOf("<br>"));
                        String[] outputIp = dataAddress.split(" ");
                        netBean.setOutputIp(outputIp[0]);
                        netBean.setOutputIpCountry(outputIp[1]);

                        String dnsIp = data.substring(data.indexOf("您的DNS地址信息") + 11);
                        String dnsAddress = dnsIp.substring(0, dnsIp.indexOf("<br>"));
                        String[] outputDns = dnsAddress.split(" ");
                        netBean.setOutputDns(outputDns[0]);
                        netBean.setOutputDnsCountry(outputDns[1]);


                    }

                    @Override
                    public void onLoadFailed(@NonNull Exception e) {
                        HttpLog.e("outputDns info fail:" + e.toString());
                    }
                });
            }

            @Override
            public void onLoadFailed(@NonNull Exception e) {
                HttpLog.e("outputDns html info fail:" + e.toString());
            }
        });
    }

    public static boolean checkIsRoaming(Context context) {
        ConnectivityManager manager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager != null) {
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (networkInfo != null) {
                return networkInfo.isRoaming();
            }
        }
        return false;
    }

    public static void getMobileDbm(Context context, NetBean netBean) {
        int dbm = 0;
        int level = 0;
        int asu = 0;
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            List<CellInfo> cellInfoList;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                if (tm == null) {
                    return;
                }
                cellInfoList = tm.getAllCellInfo();
                if (null != cellInfoList) {
                    for (CellInfo cellInfo : cellInfoList) {
                        if (cellInfo instanceof CellInfoGsm) {
                            CellSignalStrengthGsm cellSignalStrengthGsm = ((CellInfoGsm) cellInfo).getCellSignalStrength();
                            dbm = cellSignalStrengthGsm.getDbm();
                            level = cellSignalStrengthGsm.getLevel();
                            asu = cellSignalStrengthGsm.getAsuLevel();
                        } else if (cellInfo instanceof CellInfoCdma) {
                            CellSignalStrengthCdma cellSignalStrengthCdma =
                                    ((CellInfoCdma) cellInfo).getCellSignalStrength();
                            dbm = cellSignalStrengthCdma.getDbm();
                            level = cellSignalStrengthCdma.getLevel();
                            asu = cellSignalStrengthCdma.getAsuLevel();
                        } else if (cellInfo instanceof CellInfoLte) {
                            CellSignalStrengthLte cellSignalStrengthLte = ((CellInfoLte) cellInfo).getCellSignalStrength();
                            dbm = cellSignalStrengthLte.getDbm();
                            level = cellSignalStrengthLte.getLevel();
                            asu = cellSignalStrengthLte.getAsuLevel();
                        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                            if (cellInfo instanceof CellInfoWcdma) {
                                CellSignalStrengthWcdma cellSignalStrengthWcdma =
                                        ((CellInfoWcdma) cellInfo).getCellSignalStrength();
                                dbm = cellSignalStrengthWcdma.getDbm();
                                level = cellSignalStrengthWcdma.getLevel();
                                asu = cellSignalStrengthWcdma.getAsuLevel();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            HttpLog.e("signal info:" + e.toString());
        }
        netBean.setMobAsu(asu);
        netBean.setMobDbm(dbm);
        netBean.setMobLevel(level);
    }

}
