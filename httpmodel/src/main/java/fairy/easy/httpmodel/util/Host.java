package fairy.easy.httpmodel.util;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import fairy.easy.httpmodel.resource.base.BaseData;
import fairy.easy.httpmodel.resource.host.HostBean;


public class Host {

    public static HostBean getLocalHost() {
        HostBean hostBean = new HostBean();
        List<String> list = new ArrayList<>();
        String mapsFilename = "/system/etc/hosts";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(mapsFilename));
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
            reader.close();
            hostBean.setStatus(BaseData.HTTP_SUCCESS);
        } catch (Exception e) {
            hostBean.setStatus(BaseData.HTTP_ERROR);
            e.printStackTrace();
        }
        hostBean.setParam(list);
        return hostBean;
    }
}
