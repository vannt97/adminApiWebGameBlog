package com.example.adminApiWebGameBlog.payload.reponse;

public class ResponseData {
    private int status;
    private Object data;
    private boolean isSuccess;

    public ResponseData() {

    }

    public ResponseData(int status, Object data, boolean isSucces) {
        this.status = status;
        this.data = data;
        this.isSuccess = isSucces;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isSucces() {
        return isSuccess;
    }

    public void setSucces(boolean success) {
        isSuccess = success;
    }
}
