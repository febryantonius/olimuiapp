package com.kawunglabs.olimpiadeuiapp;

import java.io.IOException;
import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONObject;

import com.kawunglabs.olimpiadeuiapp.Model.Sport;
import com.kawunglabs.olimpiadeuiapp.utils.DataManager;
import com.kawunglabs.olimpiadeuiapp.utils.DataUtility;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.os.Build;

public class SchedulePageOther extends Activity {
	private int SID = -1;
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
		setContentView(R.layout.activity_schedule_page_other);
		// Show the Up button in the action bar.
		setupActionBar();
		
		Intent intent = getIntent();
		SID = intent.getIntExtra("SID", -1);
		Sport sport = DataUtility.getSportBySID(SID);
		
		((ImageView) findViewById(R.id.logo_sport_schedule_other)).setImageResource(sportLogoRound[SID - 1]);
		((TextView) findViewById(R.id.text_sport_schedule_other)).setText(sport.getName());
		
		((LinearLayout) findViewById(R.id.noSchedule_other)).setVisibility(android.view.View.VISIBLE);
		((LinearLayout) findViewById(R.id.noSchedule_other)).setBackgroundColor(Color.parseColor("#FFFFFF"));
		
		String textMatches = DataUtility.getMatchesOther(sport.getName());
		
		((TextView) findViewById(R.id.noSchedule_other_text)).setText(textMatches);
		
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
}
