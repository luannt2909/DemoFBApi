package com.luannt.lap10515.demosimpleapp.broadcast_receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.luannt.lap10515.demosimpleapp.eventmessage.ConnectionMessage;
import com.luannt.lap10515.demosimpleapp.utils.ConnectionUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by lap10515 on 20/07/2017.
 */

public class NetworkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectionMessage message = new ConnectionMessage(false);
        if(ConnectionUtils.hasInternetConnection(context)){
            message.setEnable(true);
        }
        EventBus.getDefault().post(message);
    }
}
