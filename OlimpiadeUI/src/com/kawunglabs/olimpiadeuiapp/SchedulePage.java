package com.kawunglabs.olimpiadeuiapp;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kawunglabs.olimpiadeuiapp.Model.Match;
import com.kawunglabs.olimpiadeuiapp.Model.Sport;
import com.kawunglabs.olimpiadeuiapp.utils.DataUtility;
import com.kawunglabs.olimpiadeuiapp.utils.MatchAdapter;

public class SchedulePage extends Activity {
	private ArrayAdapter<Match> matchItemArrayAdapter;
	private int SID = -1;
	private Button buttonPast;
	private Button buttonUpcoming;
	private int[] sportLogoRound = {R.drawable.atletik_round,
			R.drawable.bulutangkis_round, R.drawable.basket_round,
			R.drawable.futsal_round, R.drawable.hockey_round,
			R.drawable.renang_round, R.drawable.sepakbola_round,
			R.drawable.taekwondo_round, R.drawable.tenis_round,
			R.drawable.tenis_meja_round, R.drawable.voli_round};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_schedule_page);
		// Show the Up button in the action bar.
		setupActionBar();
		
		Intent intent = getIntent();
		SID = intent.getIntExtra("SID", -1);
		Sport sport = DataUtility.getSportBySID(SID);
		
		((ImageView) findViewById(R.id.logo_sport_schedule)).setImageResource(sportLogoRound[SID - 1]);
		((TextView) findViewById(R.id.text_sport_schedule)).setText(sport.getName());
		
		List<Match> listMatch = getAllMatchesByTimeAndSID(SID, false);
		
		matchItemArrayAdapter = new MatchAdapter(this, listMatch, true, false);
		
		ListView listView = (ListView) findViewById(R.id.listSchedule);
		listView.setAdapter(matchItemArrayAdapter);
		listView.setBackgroundColor(Color.parseColor("#FFFFFF"));
		
		buttonPast = (Button) findViewById(R.id.buttonPastSchedule);
		buttonPast.setBackgroundColor(Color.parseColor("#C0C2C4"));
		buttonPast.setTextColor(Color.parseColor("#8F9194"));
		
		buttonUpcoming = (Button) findViewById(R.id.buttonUpcomingSchedule);
		buttonUpcoming.setBackgroundColor(Color.parseColor("#FFFFFF"));
		buttonUpcoming.setTextColor(Color.parseColor("#26B99A"));
		
		if(listMatch.size() == 0){
			listView.setVisibility(android.view.View.GONE);
			((LinearLayout) findViewById(R.id.noSchedule)).setVisibility(android.view.View.VISIBLE);
			((LinearLayout) findViewById(R.id.noSchedule)).setBackgroundColor(Color.parseColor("#FFFFFF"));
		}else{
			listView.setVisibility(android.view.View.VISIBLE);
			((LinearLayout) findViewById(R.id.noSchedule)).setVisibility(android.view.View.GONE);
		}
		
		JSONObject val = new JSONObject();
		try{
			val.put("UID", MainActivity.uid);
			val.put("Sport", sport.getName());
		}catch(JSONException e) {
			e.printStackTrace();
		}
		Mixpanel.track("halaman jadwal", val);
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.schedule_upcoming_page, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
//			Intent upIntent = new Intent(this, MainActivity.class);
//			NavUtils.navigateUpTo(this, upIntent);
			onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	public void onClickPastSchedule(View view) {
		buttonPast.setBackgroundColor(Color.parseColor("#FFFFFF"));
		buttonPast.setTextColor(Color.parseColor("#26B99A"));
		
		buttonUpcoming.setBackgroundColor(Color.parseColor("#C0C2C4"));
		buttonUpcoming.setTextColor(Color.parseColor("#8F9194"));
		
		List<Match> listMatch = getAllMatchesByTimeAndSID(SID, true);
		
		matchItemArrayAdapter = new MatchAdapter(this, listMatch, true, true);
		((ListView) findViewById(R.id.listSchedule)).setAdapter(matchItemArrayAdapter);
		
		if(listMatch.size() == 0){
			((ListView) findViewById(R.id.listSchedule)).setVisibility(android.view.View.GONE);
			((LinearLayout) findViewById(R.id.noSchedule)).setVisibility(android.view.View.VISIBLE);
			((LinearLayout) findViewById(R.id.noSchedule)).setBackgroundColor(Color.parseColor("#FFFFFF"));
		}else{
			((ListView) findViewById(R.id.listSchedule)).setVisibility(android.view.View.VISIBLE);
			((LinearLayout) findViewById(R.id.noSchedule)).setVisibility(android.view.View.GONE);
		}
		
		JSONObject val = new JSONObject();
		try{
			val.put("UID", MainActivity.uid);
			val.put("Sport", DataUtility.getSportBySID(SID).getName());
			val.put("Tab", "Past");
		}catch(JSONException e) {
			e.printStackTrace();
		}
		Mixpanel.track("jadwal past/upcoming", val);
	}
	
	public void onClickUpcomingSchedule(View view) {
		buttonPast.setBackgroundColor(Color.parseColor("#C0C2C4"));
		buttonPast.setTextColor(Color.parseColor("#8F9194"));
		
		buttonUpcoming.setBackgroundColor(Color.parseColor("#FFFFFF"));
		buttonUpcoming.setTextColor(Color.parseColor("#26B99A"));
		
		List<Match> listMatch = getAllMatchesByTimeAndSID(SID, false);
		
		matchItemArrayAdapter = new MatchAdapter(this, listMatch, true, false);
		((ListView) findViewById(R.id.listSchedule)).setAdapter(matchItemArrayAdapter);
		
		if(listMatch.size() == 0){
			((ListView) findViewById(R.id.listSchedule)).setVisibility(android.view.View.GONE);
			((LinearLayout) findViewById(R.id.noSchedule)).setVisibility(android.view.View.VISIBLE);
			((LinearLayout) findViewById(R.id.noSchedule)).setBackgroundColor(Color.parseColor("#FFFFFF"));
		}else{
			((ListView) findViewById(R.id.listSchedule)).setVisibility(android.view.View.VISIBLE);
			((LinearLayout) findViewById(R.id.noSchedule)).setVisibility(android.view.View.GONE);
		}
		
		JSONObject val = new JSONObject();
		try{
			val.put("UID", MainActivity.uid);
			val.put("Sport", DataUtility.getSportBySID(SID).getName());
			val.put("Tab", "Upcoming");
		}catch(JSONException e) {
			e.printStackTrace();
		}
		Mixpanel.track("hasil past/upcoming", val);
	}
	
	public List<Match> getAllMatchesByTimeAndSID(int SID, boolean isPast) {
		long currTime = System.currentTimeMillis();
		
		List<Match> listAllMatches = DataUtility.getAllMatchesByTime(currTime,
				isPast);
		
		List<Match> retList = new ArrayList<Match>();
		
		for (Match m : listAllMatches)
			if (m.getSID() == SID)
				retList.add(m);
		
		return retList;
	}
}
