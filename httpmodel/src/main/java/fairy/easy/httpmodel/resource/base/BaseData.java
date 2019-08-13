package fairy.easy.httpmodel.resource.base;

public class BaseData {

    public static final String BASE = ":";

    /**
     * 使用腾讯云相关接口 当腾讯云接口不行当时候 也可以使用网易接口
     */
    public static final String BASE_URL = "https://huatuo.qq.com/";
    public static final String OUTPUT_IP_URL = "Report/GetUserIp";
    public static final String OUTPUT_IP_COUNTRY_URL = "Report/GetIsp";
    public static final String RESPONSE_SERVER_URL = "Report/GetUrlResponseServer";


    /**
     * 使用网易接口
     */
    public static final String OUTPUT_DNS_URL = "https://nstool.netease.com/";


    public static final int HTTP_ERROR = -1;
    public static final int HTTP_SUCCESS = 200;
}
