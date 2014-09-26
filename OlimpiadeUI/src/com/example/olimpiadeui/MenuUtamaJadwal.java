package com.example.olimpiadeui;

import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.olimpiadeui.Model.Sport;
import com.example.olimpiadeui.utils.DataUtility;
import com.example.olimpiadeui.utils.SportAdapter;

public class MenuUtamaJadwal extends Activity {
//	ArrayAdapter sportItemArrayAdapter;
	private static ArrayAdapter<Sport> sportItemArrayAdapter;
	private static List<Sport> listSport;
//	private static DataManager dataManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_menu_utama_jadwal);
		
		setupActionBar();
		
		Typeface tf = Typeface.createFromAsset(this.getAssets(), "fonts/edelsans_regular.otf");
		((TextView) findViewById(R.id.schedule_title)).setTypeface(tf, Typeface.BOLD);
		
//		dataManager = DataManager.getDataManager();
//		dataManager.open();
		
		listSport = DataUtility.getAllSports();
		
		sportItemArrayAdapter = new SportAdapter(this, listSport);
		((ListView) findViewById(R.id.listSportSchedule)).setAdapter(sportItemArrayAdapter);
		((ListView) findViewById(R.id.listSportSchedule)).setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
				int position, long id) {
				
				Intent intent = new Intent(MenuUtamaJadwal.this, SchedulePage.class);
				intent.putExtra("SID", listSport.get(position).getSID());
				startActivity(intent);
			}
		});
		
//		MainActivity.setScheduleAdapter(sportItemArrayAdapter);
//		MainActivity.setListSport(listSport);
	}
	
	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_utama_jadwal, menu);
		return true;
	}
	
	@Override
	protected void onResume() {
		listSport = DataUtility.getAllSports();
		sportItemArrayAdapter.clear();
		sportItemArrayAdapter.addAll(listSport);
		sportItemArrayAdapter.notifyDataSetChanged();
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	public static void updateGan() {
//		dataManager.open();
		listSport = DataUtility.getAllSports();
		sportItemArrayAdapter.clear();
		sportItemArrayAdapter.addAll(listSport);
		sportItemArrayAdapter.notifyDataSetChanged();
	}
}
