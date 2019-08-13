package fairy.easy.httpmodel.resource;

public enum HttpType {
    INDEX("Index"),
    PING("Ping"),
    HTTP("Http"),
    HOST("Host"),
    PORT_SCAN("PortScan"),
    MTU_SCAN("MtuScan"),
    TRACE_ROUTE("TraceRoute"),
    NSLOOKUP("NsLookup"),
    NET("Net");

    private String name;

    HttpType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
