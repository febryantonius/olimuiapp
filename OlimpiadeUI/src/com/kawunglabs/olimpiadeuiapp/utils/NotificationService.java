package com.kawunglabs.olimpiadeuiapp.utils;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class NotificationService extends Service {
	Thread t = new ThreadNotification();
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.d("Notif", "cek gan");
		t.start();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d("Notif", "bikin gan");
		if (!t.isAlive()) {
			t.start();
			Log.d("Notif", "jadi gan");
		}
		
		return Service.START_REDELIVER_INTENT;
	}
}
