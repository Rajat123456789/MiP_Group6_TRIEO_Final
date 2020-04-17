package com.example.notesandreminders;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.notesandreminders.model.DbManager;
import com.example.notesandreminders.model.Reminder;

import java.util.List;

public class AlarmService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startService();
        return START_STICKY;
    }

    private void startService()
    {
        Intent intent = new Intent(this, MyBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this.getApplicationContext(), 234, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        List<Reminder> reminders = DbManager.getInstance().appDao().getReminders();
        assert alarmManager != null;
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+1000, pendingIntent);

        for(int i=0;i<reminders.size();i++)
        {
            Bundle b = new Bundle();
            b.putSerializable("reminder",reminders.get(i));
            intent.putExtra("reminder",b);
        }
    }
}
