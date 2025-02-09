package com.trisnasejati.qrcode.api;

public class ApiResponse {
    private boolean status;
    private String message;

    // Getter and Setter for 'status'
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    // Getter and Setter for 'message'
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
