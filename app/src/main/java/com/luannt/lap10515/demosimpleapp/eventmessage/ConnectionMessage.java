package com.luannt.lap10515.demosimpleapp.eventmessage;

/**
 * Created by lap10515 on 20/07/2017.
 */

public class ConnectionMessage {
    private String message;
    private boolean isEnable;

    public ConnectionMessage(boolean isEnable) {
        this.isEnable = isEnable;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }
}
