package com.yellowmessenger.urlshortner.urlshortner.dto;

import java.io.Serializable;

/**
 * @author karthikjoshi
 * UI response class pojo.
 * @param <T>
 */
public class UIResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    private transient T data;
    private Integer code;
    private String message;
    private String status;

    public UIResponse(T data)
    {
        this.data = data;
    }

    public UIResponse()
    {
        super();
    }

    public static long getSerialVersionUID() {

        return serialVersionUID;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
