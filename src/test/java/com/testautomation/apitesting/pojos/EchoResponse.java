package com.testautomation.apitesting.pojos;

import java.util.Map;

/**
 * POJO for Postman Echo GET/POST/PUT/PATCH/DELETE response.
 */
public class EchoResponse {
    private Map<String, String> args;
    private Map<String, String> headers;
    private String url;
    private Object data;
    private Map<String, String> form;
    private Object json;

    public EchoResponse() {
    }

    public Map<String, String> getArgs() {
        return args;
    }

    public void setArgs(Map<String, String> args) {
        this.args = args;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Map<String, String> getForm() {
        return form;
    }

    public void setForm(Map<String, String> form) {
        this.form = form;
    }

    public Object getJson() {
        return json;
    }

    public void setJson(Object json) {
        this.json = json;
    }
}
