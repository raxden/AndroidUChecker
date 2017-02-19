package com.raxdenstudios.uchecker;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import org.jsoup.Jsoup;

import java.io.IOException;

/**
 * Created by Ángel Gómez on 19/02/2017.
 */

public class UCheckerRequest {

    private static final String TAG = UCheckerRequest.class.getSimpleName();

    private String mPackageName;
    private String mVersionName;
    private int mTimeout;

    private UCheckerRequest(Builder builder) {
        mPackageName = builder.packageName;
        mVersionName = builder.versionName;
        mTimeout = builder.timeout;

        if (mPackageName == null) {
            mPackageName = getPackageName(builder.context);
        }
        if (mVersionName == null) {
            mVersionName = getVersionName(builder.context);
        }
        if (mTimeout <= 0) {
            mTimeout = 30000;
        }
    }

    public String retrieveLastVersion() {
        String lastVersion = "";
        try {
            lastVersion = Jsoup
                    .connect("https://play.google.com/store/apps/details?id=" + mPackageName + "&hl=en")
                    .timeout(mTimeout)
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com")
                    .get()
                    .select("div[itemprop=softwareVersion]")
                    .first()
                    .ownText();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return lastVersion;
    }

    public boolean checkVersion() {
        return mVersionName.equals(retrieveLastVersion());
    }

    private String getPackageName(Context context) {
        String packageName = "";
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            packageName = pInfo.packageName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return packageName;
    }

    private String getVersionName(Context context) {
        String versionName = "";
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionName = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return versionName;
    }

    public static class Builder {

        Context context;
        String packageName;
        String versionName;
        int timeout;

        public Builder (Context context) {
            this.context = context;
        }

        public Builder setPackageName(String packageName) {
            this.packageName = packageName;
            return this;
        }

        public Builder setVersionName(String versionName) {
            this.versionName = versionName;
            return this;
        }

        public Builder setTimeout(int timeout) {
            this.timeout = timeout;
            return this;
        }

        public UCheckerRequest create() {
            return new UCheckerRequest(this);
        }

    }

}
