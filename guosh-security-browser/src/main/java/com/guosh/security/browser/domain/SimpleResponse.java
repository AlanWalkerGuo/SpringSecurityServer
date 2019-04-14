package com.guosh.security.browser.domain;


public class SimpleResponse {
    private Object object;

    public SimpleResponse() {
    }

    public SimpleResponse(Object object) {
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
