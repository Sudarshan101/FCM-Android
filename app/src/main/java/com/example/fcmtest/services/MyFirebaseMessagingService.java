package com.example.fcmtest.services;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.fcmtest.MainActivity;
import com.example.fcmtest.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.List;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String CHANNEL_ID = "Bestmarts";
    private static final String CHANNEL_NAME = "Bestmarts";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            sendNotification1(remoteMessage);
        }else{
            sendNotification(remoteMessage);
        }
    }

    @SuppressLint("LongLogTag")
    private void sendNotification(RemoteMessage remoteMessage) {
        if (!isAppIsInBackground(getApplicationContext())) {
            //foreground app
            Log.e("remoteMessage foreground", remoteMessage.getData().toString());
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();
            Intent resultIntent = new Intent(getApplicationContext() , MainActivity.class);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                    0 /* Request code */, resultIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
            notificationBuilder.setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                    .setNumber(10)
                    .setTicker("Bestmarts")
                    .setContentTitle(title)
                    .setContentText(body)
                    .setContentInfo("Info");
            notificationManager.notify(1, notificationBuilder.build());
        }else{
            Log.e("remoteMessage background", remoteMessage.getData().toString());
            Map<String, String> data = remoteMessage.getData();
            String title = data.get("title");
            String body = data.get("body");
            Intent resultIntent = new Intent(getApplicationContext() , MainActivity.class);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                    0 /* Request code */, resultIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
            notificationBuilder.setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                    .setNumber(10)
                    .setTicker("Bestmarts")
                    .setContentTitle(title)
                    .setContentText(body)
                    .setContentInfo("Info");
            notificationManager.notify(1, notificationBuilder.build());
        }
    }

    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }

    @SuppressLint("NewApi")
    private void sendNotification1(RemoteMessage remoteMessage) {
        if (!isAppIsInBackground(getApplicationContext())) {
            //foreground app
            Log.e("remoteMessage", remoteMessage.getData().toString());
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();
            Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                    0 /* Request code */, resultIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            Uri defaultsound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            OreoNotification oreoNotification = new OreoNotification(this);
            Notification.Builder builder = oreoNotification.getOreoNotification(title, body, pendingIntent, defaultsound, String.valueOf(R.drawable.ic_launcher_background));

            int i = 0;
            oreoNotification.getManager().notify(i, builder.build());
        }else{
            Log.e("remoteMessage", remoteMessage.getData().toString());
            String title = remoteMessage.getData().get("title");
            String body = remoteMessage.getData().get("body");
            Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                    0 /* Request code */, resultIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            Uri defaultsound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            OreoNotification oreoNotification = new OreoNotification(this);
            Notification.Builder builder = oreoNotification.getOreoNotification(title, body, pendingIntent, defaultsound, String.valueOf(R.drawable.ic_launcher_background));

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int i = 0;
            oreoNotification.getManager().notify(i, builder.build());
        }

    }
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("NEW_TOKEN = = == = = =",s);
    }

}