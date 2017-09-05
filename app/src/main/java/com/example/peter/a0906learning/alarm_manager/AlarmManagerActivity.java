package com.example.peter.a0906learning.alarm_manager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.peter.a0906learning.R;

import java.util.Calendar;

public class AlarmManagerActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int RECEIVED_TIME = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_manager);

        findViewById(R.id.alarm_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // 時間をセットする
        Calendar calendar = Calendar.getInstance();

        // 現在の時間を取得
        calendar.setTimeInMillis(System.currentTimeMillis());

        // n秒後に設定
        calendar.add(Calendar.SECOND, RECEIVED_TIME);

        Intent intent = new Intent(AlarmManagerActivity.this, AlarmBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        // Alarmセット(APIレベル19以降と以前で異なる。)
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }

        Toast.makeText(this, "Set Alarm", Toast.LENGTH_SHORT).show();
    }
}
