package com.example.peter.a0906learning;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.peter.a0906learning.alarm_manager.AlarmManagerActivity;
import com.example.peter.a0906learning.async_query_loader.AsyncQueryHandlerActivity;
import com.example.peter.a0906learning.cursor_loader.CursorLoaderActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setListener();
    }

    private void setListener() {
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.button1:
                intent = new Intent(MainActivity.this, AlarmManagerActivity.class);
                break;
            case R.id.button2:
                intent = new Intent(MainActivity.this, AsyncQueryHandlerActivity.class);
                break;
            case R.id.button3:
                intent = new Intent(MainActivity.this, CursorLoaderActivity.class);
                break;
            case R.id.button4:
                intent = new Intent(MainActivity.this, GpsActivity.class);
                break;
        }
        startActivity(intent);
    }
}
