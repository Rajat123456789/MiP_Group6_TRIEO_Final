package com.example.notesandreminders;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.notesandreminders.model.DbManager;
import com.example.notesandreminders.model.Reminder;
import com.example.notesandreminders.ui.MainActivity;

public class MyBroadcastReceiver extends BroadcastReceiver {
    MediaPlayer mp;
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("ALARM CAs");
        mp=MediaPlayer.create(context, R.raw.alarm);
        mp.start();
        if(intent.getBundleExtra("bundle")!=null) {

            Bundle b = intent.getBundleExtra("bundle");
            Reminder reminder = (Reminder) b.getSerializable("reminder");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                makeNotificationChannel("CHANNEL_1", "Example channel", NotificationManager.IMPORTANCE_DEFAULT,context);
            }
            Reminder reminder1 = new Reminder();
            reminder1.setReminderSuccess(true);
            reminder1.setDescription(reminder1.getDescription());
            reminder1.setTitle(reminder1.getTitle());
            reminder1.setCreatedOn(reminder1.getCreatedOn());
            reminder1.setRemindOn(reminder1.getRemindOn());
            DbManager.getInstance().appDao().updateReminder(reminder1);
            System.out.println("REMINDERSSS LIST IS"+DbManager.getInstance().appDao().getReminders());
            NotificationCompat.Builder notification =
                    new NotificationCompat.Builder(context, "CHANNEL_1");
            Intent myIntent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(
                    context,
                    0,
                    myIntent,
                    PendingIntent.FLAG_ONE_SHOT);
            notification
                    .setSmallIcon(R.mipmap.ic_launcher) // can use any other icon
                    .setContentTitle(reminder.getTitle())
                    .setContentIntent(pendingIntent)
                    .setContentText(reminder.getDescription())
                    .setNumber(3); // this shows a number in the notification dots

            NotificationManager notificationManager =
                    (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

            assert notificationManager != null;
            notificationManager.notify(1, notification.build());
        }
        Toast.makeText(context, "Alarm", Toast.LENGTH_LONG).show();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    void makeNotificationChannel(String id, String name, int importance,Context context)
    {
        NotificationChannel channel = new NotificationChannel(id, name, importance);
        channel.setShowBadge(true); // set false to disable badges, Oreo exclusive

        NotificationManager notificationManager =
                (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        assert notificationManager != null;
        notificationManager.createNotificationChannel(channel);
    }

    }
