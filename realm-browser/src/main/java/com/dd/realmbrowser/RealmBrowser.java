package com.dd.realmbrowser;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.realm.RealmConfiguration;

public final class RealmBrowser {

    public static final int NOTIFICATION_ID = 1000;

    private static final RealmBrowser sInstance = new RealmBrowser();
    private Set<RealmConfiguration> mRealmConfSet;

    private RealmBrowser() {
        mRealmConfSet = new HashSet<>();
    }

    public List<RealmConfiguration> getRealmConfList() {
        return new ArrayList<>(mRealmConfSet);
    }

    public final void addRealmConf(RealmConfiguration... arr) {
        mRealmConfSet.addAll(Arrays.asList(arr));
    }

    public final void removeRealmConf(RealmConfiguration... arr) {
        mRealmConfSet.removeAll(Arrays.asList(arr));
    }

    public static RealmBrowser getInstance() {
        return sInstance;
    }

    public static void startRealmFilesActivity(@NonNull Activity activity) {
        RealmFilesActivity.start(activity);
    }

    public static void startRealmModelsActivity(@NonNull Activity activity, @NonNull RealmConfiguration realmConf) {
        RealmModelsActivity.start(activity, realmConf);
    }

    public static void showRealmFilesNotification(@NonNull Activity activity) {
        showRealmNotification(activity, RealmFilesActivity.class);
    }

    RealmConfiguration findRealmConfByHashCode(int hashCode) {
        for ( RealmConfiguration realmConf : mRealmConfSet ) {
            if ( realmConf.hashCode() == hashCode ) {
                return realmConf;
            }
        }
        return null;
    }

    private static void showRealmNotification(@NonNull Activity activity, @NonNull Class activityClass) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(activity)
                .setSmallIcon(R.drawable.ic_rb)
                .setContentTitle(activity.getString(R.string.rb_title))
                .setContentText(activity.getString(R.string.rb_click_to_launch))
                .setAutoCancel(false);
        Intent notifyIntent = new Intent(activity, activityClass);
        notifyIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent notifyPendingIntent =
                PendingIntent.getActivity(activity, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(notifyPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
