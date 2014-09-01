package com.example.olimpiadeui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

import com.example.olimpiadeui.utils.DataManager;
import com.example.olimpiadeui.utils.NotificationService;

public class MainActivity extends TabActivity implements OnTabChangeListener {
	TabHost tabHost;
	private Activity activity = this;
	private boolean downloading = false;
	private ProgressDialog progressDialog;
	private int statusCode = 400;
//	private static ArrayAdapter sportAdapter;
//	private static List<Sport> listSport;
//	private DataManager dataManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
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
		switch(item.getItemId()) {
			case R.id.action_refresh: {
				final ProgressDialog progressDialog = ProgressDialog.show(MainActivity.this, "Please Wait...", "Update data", true);
				progressDialog.setCancelable(false);

				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							refreshData();
						} catch (Exception e) {
							e.printStackTrace();
						}
		
						progressDialog.dismiss();
					}
				}).start();
				
//				Log.d("download", "download gan");
//				new DownloadData().execute();
//				Log.d("download", "kelar download");
//				
//				new Thread(new Runnable() {
//					public void run() {
//						progressDialog = new ProgressDialog(activity);
//						progressDialog.setTitle("Please Wait...");
//						progressDialog.setMessage("Update data");
//						progressDialog.setCancelable(false);
//						progressDialog.show();
//						downloading = true;
//					}
//				}).start();
				
//				activity.runOnUiThread(new Runnable() {
//					public void run() {
//						while (downloading);
//						Log.d("download", "lhoooo");
//						if ((statusCode != 200) && (statusCode != 201)) {
//							Log.d("A", "Masuk Lhooo");
//							AlertDialog.Builder builder = new AlertDialog.Builder(this);
//							AlertDialog alertDialog = builder.create();
//							alertDialog.setTitle("Error");
//							alertDialog.setMessage("No Internet Connection");
//							alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
//								
//								@Override
//								public void onClick(DialogInterface dialog, int which) {
//									// TODO Auto-generated method stub
//									
//								}
//							});
//							alertDialog.show();
//						}
//					}
//				});
				
				break;
			}
			case R.id.action_about: {
				Intent intent = new Intent(MainActivity.this, AboutActivity.class);
				startActivity(intent);
				break;
			}
			case R.id.action_help: {
				Intent intent = new Intent(MainActivity.this, HelpActivity.class);
				startActivity(intent);
				break;
			}
//			case R.id.action_settings: {
//				Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
//				startActivity(intent);
//				break;
//			}
		}
		
		return true;
	}
	
	public int refreshData() {
		DataManager dm = DataManager.getDataManager();
		
		dm.open();
		int statusCode = dm.refreshDatabase();
		
		
//		listSport = dm.getAllSports();
//		sportAdapter.clear();
//		sportAdapter.addAll(listSport);
//		sportAdapter.notifyDataSetChanged();
//		MenuUtamaJadwal.updateGan();
		dm.close();
		
		return statusCode;
	}
	
	class DownloadData extends AsyncTask <Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			statusCode = refreshData();
			Log.d("Refresh", "Selesai Refresh");
			progressDialog.dismiss();
			downloading = false;
			
			return null;
		}
	}
	
//	public static void setScheduleAdapter(ArrayAdapter aa) {
//		sportAdapter = aa;
//	}
//	
//	public static void setListSport(List<Sport> ls) {
//		listSport = ls;
//	} 
}
