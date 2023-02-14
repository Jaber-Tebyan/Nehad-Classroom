package com.tebyan.nehadclassroom.utility;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;


public class ActivityManager {
    public static void startActivityDelayed(Activity from,Class<?> to,long delayTime){
        startActivityDelayed(from,to,delayTime,true);
    }
    public static void startActivityDelayed(Activity from,Class<?> to,long delayTime,boolean finishSelf) {
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(from, to);
            from.startActivity(intent);

            if(finishSelf)
                from.finish();
        }, delayTime);
    }
}
