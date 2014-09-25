package com.example.olimpiadeui.utils;

import java.util.ArrayList;
import java.util.List;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.olimpiadeui.R;
import com.example.olimpiadeui.Model.Match;

public class ThreadNotification extends Thread {
	@Override
	public void run() {
		while (true) {
			try {
				//to do
				Log.d("Thread", "masuk gan");
				Thread.sleep(1000*60*5);
				Log.d("Thread", "kelar gan");
				DataManager dm = DataManager.getDataManager();
				dm.open();
				
				long timeNow = System.currentTimeMillis() / 1000;
				long timeQuery = (timeNow + 60 * 10) * 1000;
				List<Match> listMatch = dm.getAllMatchesByTime(timeQuery, false);
				List<Match> listNotifMatch = new ArrayList<Match>();
				
				String contentText = "";
				
				for (Match m : listMatch) {
					long matchTime = m.getMillisTime();
					int FID1 = m.getFID1();
					int FID2 = m.getFID2();
					int SID = m.getSID();
					
					String sportName = dm.getSportBySID(SID).getName();
					String facultyName1 = "";
					String facultyName2 = "";
					
					if (FID1 != -1)
						facultyName1 = dm.getFacultyByFID(FID1).getInitialName();
					
					if (FID2 != -1)
						facultyName2 = dm.getFacultyByFID(FID2).getInitialName();
					
					if ((matchTime - timeNow) > (60*15))
						continue;
					
					if (isMatchIncluded(sportName, facultyName1, facultyName2))
						listNotifMatch.add(m);
				}
				
				for (int i = 0; i < listNotifMatch.size(); ++i) {
					Match m = listNotifMatch.get(i);
					String sports = dm.getSportBySID(m.getSID()).getName();
					String category = dm.getSportCategoryName(m.getSCID());
					String p1 = m.getpName1();
					String p2 = m.getpName2();
					String matchTime = m.getStartTime();
					String lokasi = m.getLokasi();
					
					if (i != 0)
						contentText = contentText + "\n";
					
					contentText += sports + " - " + category + "\n";
					contentText += p1 + " vs " + p2 + "\n";
					contentText += matchTime + " @ " + lokasi;
				}
				
				if (listNotifMatch.size() != 0) {
					Context context = DataManager.getContext();
					Intent intent = new Intent();
					PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, 0);
					
					Log.d("Thread", "bikin notif gan");
					Notification notification = new Notification.Builder(context)
					.setTicker("Notification")
					.setContentTitle("You have " + listNotifMatch.size() + " coming soon match(es)")
					.setContentText(contentText)
					.setSmallIcon(R.drawable.ic_launcher)
					.setContentIntent(pIntent).getNotification();
					
					notification.flags = Notification.FLAG_AUTO_CANCEL;
					NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
					notificationManager.notify(0, notification);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private boolean isMatchIncluded(String sportName, String facultyName1,
			String facultyName2) {
		// TODO Auto-generated method stub
		SharedPreferences pref = DataManager.getContext().getSharedPreferences("NotificationSetting", 0);
		
		return (pref.getBoolean(sportName, false) || pref.getBoolean(facultyName1, false) ||
				pref.getBoolean(facultyName2, false));
	}
}
