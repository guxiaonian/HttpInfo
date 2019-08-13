package com.example.httpinfo;

import java.io.Serializable;


public class ResultBean implements Serializable {
    private String title;
    private Object param;

    public ResultBean(String title, Object param) {
        this.title = title;
        this.param = param;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getParam() {
        return param;
    }

    public void setParam(Object param) {
        this.param = param;
    }

}
