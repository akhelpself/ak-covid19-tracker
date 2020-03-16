package com.akdev.covid19tracker.utils;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.akdev.covid19tracker.BuildConfig;
import com.akdev.covid19tracker.R;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;


public class CommonUtils {

    public static String getCountryCode(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String countryCode = tm.getSimCountryIso();
        return countryCode;
    }

    public static String deviceLang() {
        return Locale.getDefault().getLanguage();
    }

    public static String versionCode() {
        return String.valueOf(BuildConfig.VERSION_CODE);
    }

    public static boolean checkNetworkWithAlert(final Context context) {
        if (!checkNetwork(context)) {
            Toast.makeText(context, context.getString(R.string.cannot_connect_network), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static boolean checkNetwork(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null) {
            if (networkInfo.isConnectedOrConnecting()) {
                return true;
            }
        }
        return false;
    }

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    public static int[] ScreenSize(Context context) {
        int[] size = new int[2];
        DisplayMetrics displaymetrics = context.getResources()
                .getDisplayMetrics();
        size[0] = displaymetrics.widthPixels;
        size[1] = displaymetrics.heightPixels;

        return size;
    }

    public static void dialogConfirm(Context context, int resTitleId, int resMessageId, DialogInterface.OnClickListener onClickListener, DialogInterface.OnClickListener onClickListenerCancel) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(context);
        if (onClickListenerCancel == null) {
            builder.setTitle(context.getString(resTitleId))
                    .setMessage(context.getString(resMessageId))
                    .setPositiveButton(android.R.string.yes, onClickListener)
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else {
            builder.setTitle(context.getString(resTitleId))
                    .setMessage(context.getString(resMessageId))
                    .setPositiveButton(android.R.string.yes, onClickListener)
                    .setNegativeButton(android.R.string.no,  onClickListenerCancel)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }


    }

    public static void dialogConfirm(Context context, int resTitleId, int resMessageId, DialogInterface.OnClickListener onClickListener) {
        dialogConfirm(context, resTitleId, resMessageId, onClickListener, null);
    }

    public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            return convertByteToHex1(messageDigest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String convertByteToHex1(byte[] data) {
        BigInteger number = new BigInteger(1, data);
        String hashText = number.toString(16);
        // Now we need to zero pad it if you actually want the full 32 chars.
        while (hashText.length() < 32) {
            hashText = "0" + hashText;
        }
        return hashText;
    }

    public static void shareApp(Context context, String title, String body) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, title);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, body);
        context.startActivity(Intent.createChooser(sharingIntent, title));
    }

    public static void showGooglePlay(Context context) {
        final String appPackageName = context.getPackageName();
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(html);
        }
    }

    /**
     * Show animation of circle progress bar
     * @param progressBar
     * @return for cancel animation
     */
    public static ObjectAnimator showLoadingBar(ProgressBar progressBar) {
        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", 0, 500); // see this max value coming back here, we animate towards that value
        animation.setDuration(5000); // in milliseconds
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
        return animation;
    }

    public static void saveUserPref(String key, String value, SharedPreferences.Editor editor) {
        editor.putString(key, value);
        editor.commit();
    }

    public static String getUserPref(String key, Context context) {
        return getSharedPreferences(context).getString(key, "");
    }

    public static String getUserPref(String key, String defaultVal, Context context) {
        return getSharedPreferences(context).getString(key, defaultVal);
    }

}
