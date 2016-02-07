package com.example.administrator.handler;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tvStatus;
    Button  showDate, setValue, getValue;
    ProgressBar progressBar;
    Handler handlerForServer;
    boolean bounded;
    Server server;

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, Server.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (bounded) {
            unbindService(serviceConnection);
            bounded = false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStatus = (TextView) findViewById(R.id.tvStatus);
        (showDate = (Button) findViewById(R.id.btnShowDate)).setOnClickListener(this);
        (setValue = (Button) findViewById(R.id.btnSetValue)).setOnClickListener(this);
        (getValue = (Button) findViewById(R.id.btnGetValue)).setOnClickListener(this);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnShowDate:
                tvStatus.setText(server.getTime());
                break;
            case R.id.btnSetValue:
                handlerForServer.sendEmptyMessage(1);
                break;
            case R.id.btnGetValue:
                tvStatus.setText("value - " + server.getValue());
                break;
        }
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Toast.makeText(MainActivity.this, "Service connected", Toast.LENGTH_SHORT).show();
            bounded = true;
            Server.LocalBinder localBinder = (Server.LocalBinder) service;
            server = localBinder.getServerInsance();
            handlerForServer = server.getHandler();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(MainActivity.this, "Service disconnected", Toast.LENGTH_SHORT).show();
            bounded = false;
            server = null;
            handlerForServer = null;
        }
    };
}
