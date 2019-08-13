package fairy.easy.httpmodel.model;

public enum RequestMethod {
    GET("GET"),
    POST("POST");


    String name;

    RequestMethod(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
