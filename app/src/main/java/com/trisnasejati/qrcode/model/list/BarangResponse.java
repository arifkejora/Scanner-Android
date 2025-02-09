package com.trisnasejati.qrcode.model.list;

import com.google.gson.annotations.SerializedName;
import java.util.List; // Importing the correct List class

public class BarangResponse<S> {

    @SerializedName("status")
    private boolean status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<ListData> data; // Correct usage of List

    // Getter and Setter methods

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ListData> getData() {
        return data;
    }

    public void setData(List<ListData> data) {
        this.data = data;
    }
}
