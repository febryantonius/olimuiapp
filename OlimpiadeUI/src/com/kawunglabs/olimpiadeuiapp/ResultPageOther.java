package com.kawunglabs.olimpiadeuiapp;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kawunglabs.olimpiadeuiapp.Model.Group;
import com.kawunglabs.olimpiadeuiapp.Model.Match;
import com.kawunglabs.olimpiadeuiapp.Model.Sport;
import com.kawunglabs.olimpiadeuiapp.Model.SportCategory;
import com.kawunglabs.olimpiadeuiapp.utils.DataUtility;
import com.kawunglabs.olimpiadeuiapp.utils.GroupAdapter;
import com.kawunglabs.olimpiadeuiapp.utils.KnockoutAdapter;
import com.kawunglabs.olimpiadeuiapp.utils.ResultOtherAdapter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.AdapterView.OnItemSelectedListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.os.Build;

public class ResultPageOther extends Activity {
	private JSONArray jsonResult;
	private ResultOtherAdapter resultAdapter;
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
		setContentView(R.layout.activity_result_page_other);
		// Show the Up button in the action bar.
		setupActionBar();
		
		Intent intent = getIntent();
		final int SID = intent.getIntExtra("SID", -1);
		
		Sport sport = DataUtility.getSportBySID(SID);
		jsonResult = DataUtility.getResultsOther(sport.getName());
		
		ListView listView = (ListView) findViewById(R.id.listResult_other);
		((ImageView) findViewById(R.id.logo_sport_result_other)).setImageResource(sportLogoRound[SID - 1]);
		((TextView) findViewById(R.id.text_sport_result_other)).setText(sport.getName());
		
		if (jsonResult != null) {
			List<JSONObject> items = new ArrayList<JSONObject>();
			
			for (int i = 0; i < jsonResult.length(); ++i) {
				JSONObject jsonObject = null;
				
				try {
					jsonObject = jsonResult.getJSONObject(i);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				items.add(jsonObject);
			}
			
			resultAdapter = new ResultOtherAdapter(this, items);
			
			listView.setAdapter(resultAdapter);
			listView.setVisibility(android.view.View.VISIBLE);
			listView.setBackgroundColor(Color.parseColor("#FFFFFF"));
			
			((LinearLayout) findViewById(R.id.noResult_other)).setVisibility(android.view.View.GONE);
		}
		else {
			listView.setVisibility(android.view.View.GONE);
			((LinearLayout) findViewById(R.id.noResult_other)).setVisibility(android.view.View.VISIBLE);
			((LinearLayout) findViewById(R.id.noResult_other)).setBackgroundColor(Color.parseColor("#FFFFFF"));
		}
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
		getMenuInflater().inflate(R.menu.result_page, menu);
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
//			NavUtils.navigateUpFromSameTask(this);
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
