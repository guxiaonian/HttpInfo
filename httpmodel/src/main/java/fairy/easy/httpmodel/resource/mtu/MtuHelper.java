package fairy.easy.httpmodel.resource.mtu;


import java.util.Collections;
import java.util.List;

import fairy.easy.httpmodel.resource.HttpType;
import fairy.easy.httpmodel.resource.Input;
import fairy.easy.httpmodel.resource.base.BaseData;
import fairy.easy.httpmodel.util.Base;
import fairy.easy.httpmodel.util.HttpLog;
import fairy.easy.httpmodel.util.LogTime;
import fairy.easy.httpmodel.util.MtuScan;

public class MtuHelper {

    public static void getMtuParam() throws Exception {
        long startTime = LogTime.getLogTime();
        MtuScan mtuScan = new MtuScan(Base.getUrlHost());
        List<Integer> mtuList = mtuScan.startReturnValue();
        MtuBean mtuBean = new MtuBean();
        if (mtuList.size() == 0) {
            mtuBean.setStatus(BaseData.HTTP_ERROR);
        } else {
            mtuBean.setStatus(BaseData.HTTP_SUCCESS);
            Collections.sort(mtuList);
            mtuBean.setMtu(mtuList.get(mtuList.size() - 1) + 28);
        }
        mtuScan.cancelMtuScan();
        mtuBean.setTotalTime(LogTime.getElapsedMillis(startTime));
        HttpLog.i("MtuScan is end");
        Input.onSuccess(HttpType.MTU_SCAN, mtuBean.toJSONObject());
    }

}
