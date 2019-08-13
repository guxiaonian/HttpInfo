package fairy.easy.httpmodel.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Date {

    /**
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String time) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date = simpleDateFormat.parse(time);
        long ts = date.getTime();
        return String.valueOf(ts);
    }

    /**
     * 将时间戳转换为时间
     */
    public static String stampToDate(long timeMillis) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date = new java.util.Date(timeMillis);
        return simpleDateFormat.format(date);
    }
}
