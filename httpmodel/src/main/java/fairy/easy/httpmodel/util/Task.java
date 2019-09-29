package fairy.easy.httpmodel.util;

import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

public class Task implements Callable<Integer> {
    Integer i;
    String host;
    int size;

    public Task(Integer i, String host) {
        super();
        this.i = i;
        this.host = host;
    }

    @Override
    public Integer call() throws Exception {
        size = i;
        if (checkMtuFromPing(size, host)) {
            return size;
        } else {
            do {
                this.size = size - 28;
                HttpLog.e("当前size:"+size);
                if (checkMtuFromPing(size, host)) {
                    return size;
                }
            } while (this.size > 100);
        }
        return size;
    }

    private boolean checkMtuFromPing(int size, String host) {
        String param = ping(createSimplePingCommand(size, host));
        if (TextUtils.isEmpty(param)) {
            return false;
        }
        if (param.toLowerCase().contains("icmp_seq")) {
            if (param.toLowerCase().contains("df")) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    private String createSimplePingCommand(int size, String host) {
        Object[] arrayOfObject = new Object[3];
        arrayOfObject[0] = size;
        arrayOfObject[1] = host;
        return String.format("/system/bin/ping -M do -c 1 -w 1 -s %d %s", arrayOfObject);
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
}
