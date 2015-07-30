package com.example.xtremepos;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StartXtremePOSatBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Intent serviceIntent = new Intent(context, Orders.class);
            context.startService(serviceIntent);
        }
    }
}