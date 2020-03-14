package com.akdev.covid19tracker;

import android.content.Context;

import com.onesignal.OneSignal;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

public class MainApplication extends MultiDexApplication {

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
    }

}
