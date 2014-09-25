package com.example.olimpiadeui;

import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.olimpiadeui.Model.Sport;
import com.example.olimpiadeui.utils.DataManager;
import com.example.olimpiadeui.utils.SportAdapter;

public class MenuUtamaBagan extends Activity {
	ArrayAdapter<Sport> sportItemArrayAdapter;
	private List<Sport> listSport;
	private DataManager dataManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_menu_utama_bagan);
		setupActionBar();
		
		Typeface tf = Typeface.createFromAsset(this.getAssets(), "fonts/edelsans_regular.otf");
		((TextView) findViewById(R.id.result_title)).setTypeface(tf, Typeface.BOLD);
		
		dataManager = DataManager.getDataManager();
		dataManager.open();
		
		listSport = dataManager.getAllSports();
		
		sportItemArrayAdapter = new SportAdapter(this, listSport);
		((ListView) findViewById(R.id.listSportResult)).setAdapter(sportItemArrayAdapter);
		((ListView) findViewById(R.id.listSportResult)).setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
				int position, long id) {
				
				Intent intent = new Intent(MenuUtamaBagan.this, ResultPage.class);
				intent.putExtra("SID", listSport.get(position).getSID());
				startActivity(intent);
			}
		});
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
		getMenuInflater().inflate(R.menu.menu_utama_bagan, menu);
		return true;
	}
	
	@Override
	protected void onResume() {
		Log.d("resume", "oke");
		dataManager.open();
		listSport = dataManager.getAllSports();
		sportItemArrayAdapter.clear();
		sportItemArrayAdapter.addAll(listSport);
		sportItemArrayAdapter.notifyDataSetChanged();

		super.onResume();
	}
	
	@Override
	protected void onPause() {
		Log.d("pause", "oke");
		dataManager.close();
		super.onPause();
	}
}
