package com.akdev.covid19tracker.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

import com.akdev.covid19tracker.R;

public class AppRater {
    private final static String APP_PNAME = "com.akdev.covid19tracker";// Package Name

    public static void appLaunched(Context mContext) {
        SharedPreferences prefs = CommonUtils.getSharedPreferences(mContext);
        boolean kClickOk = prefs.getBoolean(Constants.kClickOk, false);
        SharedPreferences.Editor editor = prefs.edit();

        // Increment launch counter
        long launchCount = prefs.getLong(Constants.kLaunchCount, 0) + 1;
        editor.putLong(Constants.kLaunchCount, launchCount);

        // Wait at least n days before opening
        if (!kClickOk) {
            if (launchCount % 7 == 1) {
                showRateDialog(mContext, editor);
                editor.putLong(Constants.kLastTimeShow, System.currentTimeMillis());
            }
        } else {
            if (launchCount % 17 == 1) {
                showRateDialog(mContext, editor);
                editor.putLong(Constants.kLastTimeShow, System.currentTimeMillis());
            }
        }

        editor.apply();
    }

    private static void showRateDialog(final Context mContext, final SharedPreferences.Editor editor) {
        String appTitle = mContext.getString(R.string.app_name);

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(appTitle);
        builder.setMessage("Rate the app so that we have more developmental update the latest information!");
        builder.setCancelable(false);

        builder.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + APP_PNAME)));
                        editor.putBoolean(Constants.kClickOk, true);
                        if (editor != null) {
                            editor.putBoolean(Constants.kClickOk, true);
                            editor.commit();
                        }
                        dialog.cancel();
                    }
                });

        builder.setNegativeButton(
                "No, Thanks",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}