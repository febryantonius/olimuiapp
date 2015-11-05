package com.kawunglabs.olimpiadeuiapp;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

import com.kawunglabs.olimpiadeuiapp.utils.DataManager;
import com.kawunglabs.olimpiadeuiapp.utils.DataUtility;
import com.kawunglabs.olimpiadeuiapp.utils.NotificationService;

public class MenuUtama extends TabActivity implements OnTabChangeListener {
	TabHost tabHost;
	private MenuUtama host = this;
	private DownloadData asyncTask;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_menu_utama);
//        Log.d("main", "bikin service");
//        Intent i = new Intent(this, NotificationService.class);
//        this.startService(i);
//        Log.d("main", "selesai bikin");
        
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
        
        DataManager.createDataManager(getApplicationContext());
		
        tabHost = getTabHost();
        
        tabHost.setOnTabChangedListener(this);
         
        // Tab for Schedule
        TabSpec scheduleTab = tabHost.newTabSpec("Schedule");
        // setting Title and Icon for the Tab
        scheduleTab.setIndicator("", getResources().getDrawable(R.drawable.schedule));
        Intent scheduleIntent = new Intent(this, MenuUtamaJadwal.class);
        scheduleTab.setContent(scheduleIntent);
        
        TabSpec matchResultTab = tabHost.newTabSpec("MatchResult");
        matchResultTab.setIndicator("", getResources().getDrawable(R.drawable.match_result));
        Intent matchResultIntent = new Intent(this, MenuUtamaBagan.class);
        matchResultTab.setContent(matchResultIntent);
        
        TabSpec medalsTab = tabHost.newTabSpec("Medals");
        medalsTab.setIndicator("", getResources().getDrawable(R.drawable.medals));
        Intent medalsIntent = new Intent(this, MenuUtamaMedali.class);
        medalsTab.setContent(medalsIntent);
        
        TabSpec filterTab = tabHost.newTabSpec("Filter");
        filterTab.setIndicator("", getResources().getDrawable(R.drawable.filter));
        Intent filterIntent = new Intent(this, MenuUtamaFilter.class);
        filterTab.setContent(filterIntent);
        
        // Adding all TabSpec to TabHost
        tabHost.addTab(scheduleTab);
        tabHost.addTab(matchResultTab);
        tabHost.addTab(medalsTab);
        tabHost.addTab(filterTab);
        
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		onOptionsItemSelected(menu.findItem(R.id.action_refresh));
		
		return true;
	}

	@Override
	public void onTabChanged(String arg0) {
		// TODO Auto-generated method stub
		
		int currentTab = tabHost.getCurrentTab();
		
		for (int i = 0; i < tabHost.getTabWidget().getChildCount(); ++i)
			tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#26B99A"));
		
		tabHost.getTabWidget().getChildAt(currentTab).setBackgroundColor(Color.parseColor("#0F7A65"));
		
		if (currentTab == 0)
			setTitle(R.string.title_activity_menu_utama_jadwal);
		else if (currentTab == 1)
			setTitle(R.string.title_activity_menu_utama_bagan);
		else if (currentTab == 2)
			setTitle(R.string.title_activity_menu_utama_medali);
		else
			setTitle(R.string.title_activity_menu_utama_filter);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
//		switch(item.getItemId()) {
//			case R.id.action_refresh: {
//				if (!DataUtility.isDownloading()) {
//					item.setActionView(R.layout.progressbar);
//					item.expandActionView();
//					
//					asyncTask = new DownloadData(host, item);
//					asyncTask.execute();
//				}
//				
//				JSONObject val = new JSONObject();
//				try{
//					val.put("UID", MainActivity.uid);
//				}catch(JSONException e) {
//					e.printStackTrace();
//				}
//				Mixpanel.track("refresh", val);
//				
//				break;
//			}
//			case R.id.action_about: {
//				Intent intent = new Intent(MenuUtama.this, AboutActivity.class);
//				startActivity(intent);
//				
//				JSONObject val = new JSONObject();
//				try{
//					val.put("UID", MainActivity.uid);
//				}catch(JSONException e) {
//					e.printStackTrace();
//				}
//				Mixpanel.track("halaman tentang", val);
//				
//				break;
//			}
//			case R.id.action_help: {
//				Intent intent = new Intent(MenuUtama.this, HelpActivity.class);
//				startActivity(intent);
//				
//				JSONObject val = new JSONObject();
//				try{
//					val.put("UID", MainActivity.uid);
//				}catch(JSONException e) {
//					e.printStackTrace();
//				}
//				Mixpanel.track("halaman bantuan", val);
//				
//				break;
//			}
////			case R.id.action_settings: {
////				Intent intent = new Intent(MenuUtama.this, NotificationActivity.class);
////				startActivity(intent);
////				break;
////			}
//		}

		// R.id is no longer available in switch. must be using if-else
		if(item.getItemId() == R.id.action_refresh) {
			if (!DataUtility.isDownloading()) {
				item.setActionView(R.layout.progressbar);
				item.expandActionView();
				
				asyncTask = new DownloadData(host, item);
				asyncTask.execute();
			}
			
			JSONObject val = new JSONObject();
			try{
				val.put("UID", MainActivity.uid);
			}catch(JSONException e) {
				e.printStackTrace();
			}
			Mixpanel.track("refresh", val);
		}else if(item.getItemId() == R.id.action_about){
			Intent intent = new Intent(MenuUtama.this, AboutActivity.class);
			startActivity(intent);
			
			JSONObject val = new JSONObject();
			try{
				val.put("UID", MainActivity.uid);
			}catch(JSONException e) {
				e.printStackTrace();
			}
			Mixpanel.track("halaman tentang", val);
		}else if(item.getItemId() == R.id.action_help) {
			Intent intent = new Intent(MenuUtama.this, HelpActivity.class);
			startActivity(intent);
			
			JSONObject val = new JSONObject();
			try{
				val.put("UID", MainActivity.uid);
			}catch(JSONException e) {
				e.printStackTrace();
			}
			Mixpanel.track("halaman bantuan", val);
		}
		
		return true;
	}
	
	public void updateIcon(MenuItem item) {
		item.collapseActionView();
		item.setActionView(null);
	}
	
	class DownloadData extends AsyncTask<Void, Void, Void> {
		private MenuItem item;
		private int statusCode;
		private DataManager dm;
		
		public DownloadData(Context context, MenuItem item) {
			this.item = item;
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			DataUtility.startDownload();
			
			dm = DataManager.getDataManager();
			dm.open();
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			statusCode = dm.refreshDatabase();
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			dm.close();
			
			if (statusCode != -1)
				DataUtility.inisialisasiData();
			
			String text = (statusCode != -1) ? "Data telah diperbarui" :
				"Anda tidak terhubung dengan internet";
			
			int duration = Toast.LENGTH_LONG;
			
			Toast toast = Toast.makeText(MenuUtama.this, text, duration);
			toast.show();
			int tabNow = tabHost.getCurrentTab();
			tabHost.setCurrentTab(0);
			tabHost.setCurrentTab(tabNow);
			DataUtility.finishDownload();
			updateIcon(item);
		}
	}
}
