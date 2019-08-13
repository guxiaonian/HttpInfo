package fairy.easy.httpmodel.model;

import org.json.JSONObject;

import java.util.Map;

public class PostParam {

    private final String param;
    private JSONObject jsonObject;
    private Map<String, Object> map;

    public PostParam(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
        this.param = jsonObject == null ? null : jsonObject.toString();
    }

    public PostParam(Map<String, Object> map) {
        this.map = map;
        this.param = getMapData(map);
    }

    public PostParam(String param) {
        this.param = param;
    }

    public String getStringParam() {
        return param;
    }

    public JSONObject getJSONObjectParam() {
        return jsonObject;
    }

    public Map<String, Object> getMapParam() {
        return map;
    }

    private String getMapData(Map<String, Object> map) {

        StringBuilder stringBuilder = new StringBuilder();

        try {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                stringBuilder.append(entry.getKey())
                        .append("=")
                        .append(entry.getKey())
                        .append("&");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        } catch (Exception e) {
            //ignore

        }
        return stringBuilder.toString();
    }
}
