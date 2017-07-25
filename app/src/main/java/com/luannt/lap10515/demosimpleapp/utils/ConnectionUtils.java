package com.luannt.lap10515.demosimpleapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by lap10515 on 20/07/2017.
 */

public class ConnectionUtils {
    public static boolean hasInternetConnection(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return info !=null && info.isConnected();
    }
}
