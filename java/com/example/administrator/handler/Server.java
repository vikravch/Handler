package com.example.administrator.handler;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Server extends Service {
    public Server() {
    }

    Handler handler;
    IBinder binder = new LocalBinder();
    int a = 0;

    @Override
    public IBinder onBind(Intent intent) {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        a = 12;
                }
            }
        };
        return binder;
    }

    public class LocalBinder extends Binder {
        public Server getServerInsance() {
            return Server.this;
        }
    }

    public String getTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date());
    }

    public int getValue() {
        return a;
    }

    public Handler getHandler() {
        return handler;
    }
}
