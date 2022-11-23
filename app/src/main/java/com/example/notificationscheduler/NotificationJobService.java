package com.example.notificationscheduler;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

public class NotificationJobService extends JobService {
    private static final int JOB_ID = 0;
    NotificationManager mNotifyManager;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        createNotificationChannel();
        PendingIntent contentPendingIntent = PendingIntent.getActivity(this,
                JOB_ID, new Intent(this, MainActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);
        Notification.Builder builder = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            builder = new Notification.Builder(this, PRIMARY_CHANNEL_ID)
                    .setContentTitle("Download")
                    .setContentIntent(contentPendingIntent)
                    .setSmallIcon(R.drawable.ic_job_running)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true);
        }
        mNotifyManager.notify(JOB_ID, builder.build());
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }

    public void createNotificationChannel() {
        mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,
                    "Download notification",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from Download");
            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }
}
