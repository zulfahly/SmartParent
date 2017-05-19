package pnj.ti.b2013.smartparent.service;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import pnj.ti.b2013.smartparent.R;
import pnj.ti.b2013.smartparent.controller.Config;
import pnj.ti.b2013.smartparent.util.Preferences;
import pnj.ti.b2013.smartparent.view.LoginActivity;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    Context context;
    JSONObject data;

    NotificationCompat.Builder builder;
    NotificationCompat.BigTextStyle bigTextStyle;
    final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
            context);

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        try {
            data = new JSONObject(remoteMessage.getData().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e(TAG,"data notif"+data.toString());
        String title = "";
        String body = "";
        String timestamp = "";
        if (remoteMessage.getData().size() > 0){
            try {
                JSONObject data2 = data.getJSONObject("data");
                title = data2.getString("title");
                body = data2.getString("message");
                timestamp = data2.getString("timestamp");
                this.sendNotification(title, body, timestamp);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void sendNotification(String title, String body, String timestamp) {
        if (!isAppIsInBackground(getApplicationContext())) {
            setNotificationBigStyle(title, body, timestamp);
        } else {
           setNotificationBigStyle(title, body, timestamp);
        }
    }

    public void setNotificationBigStyle(String title, String body, String timestamp) {
        Preferences.getInstance(getApplicationContext()).storeNotifId(Config.PUSH_NOTIFICATION_ID);

        builder = new NotificationCompat.Builder(this);
        bigTextStyle = new NotificationCompat.BigTextStyle();
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Bitmap icon = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.ic_smartparent);
        bigTextStyle.bigText(body).setBigContentTitle(title);
        Bundle extras = new Bundle();
//        extras.putString("type", type);

        Intent redirectIntent = new Intent(this, LoginActivity.class);
        redirectIntent.putExtras(extras);

        builder.setContentTitle(title)
                .setSound(defaultSoundUri)
                .setAutoCancel(true)
                .setLargeIcon(icon)
                .setSmallIcon(R.drawable.ic_notification)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(body))
                .setContentText(body);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, redirectIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(Preferences.getInstance(getApplicationContext()).getNotifId(Config.PUSH_NOTIFICATION_ID), builder.build());

    }


    public void sendBroadcast(Bundle data) {
        Intent broadcastIntent = new Intent("iGrow-Surveyor");
        broadcastIntent.putExtra("data", data);
        Log.d("broadcasted =", data.toString());
        sendBroadcast(broadcastIntent);
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

}
