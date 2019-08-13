package fairy.easy.httpmodel.resource.index;


import fairy.easy.httpmodel.HttpModelHelper;
import fairy.easy.httpmodel.resource.HttpType;
import fairy.easy.httpmodel.resource.Input;
import fairy.easy.httpmodel.util.Date;
import fairy.easy.httpmodel.util.HttpLog;

public class IndexHelper {

    public static void getIndexParam() throws Exception {
        IndexBean indexBean = new IndexBean();
        indexBean.setAddress(HttpModelHelper.getInstance().getAddress());
        indexBean.setTime(Date.stampToDate(System.currentTimeMillis()));
        HttpLog.i("Index is end");
        Input.onSuccess(HttpType.INDEX, indexBean.toJSONObject());
    }

}
