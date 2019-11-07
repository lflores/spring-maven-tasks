package com.triadsoft.exceptions;

/**
 * @author triad <flores.leonardo@gmail.com>
 * Created 4/11/19 15:14
 */
public class ErrorResponse {
    private int status;
    private String error;
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
