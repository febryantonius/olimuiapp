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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.kawunglabs.olimpiadeuiapp.Model.Group;
import com.kawunglabs.olimpiadeuiapp.Model.Match;
import com.kawunglabs.olimpiadeuiapp.Model.Sport;
import com.kawunglabs.olimpiadeuiapp.Model.SportCategory;
import com.kawunglabs.olimpiadeuiapp.utils.DataUtility;
import com.kawunglabs.olimpiadeuiapp.utils.GroupAdapter;
import com.kawunglabs.olimpiadeuiapp.utils.KnockoutAdapter;

public class ResultPage extends Activity implements OnItemSelectedListener{
	private Button buttonGroup, buttonKnockout;
	private int SCID = -1;
	List<SportCategory> listSportCategory;
	private KnockoutAdapter knockoutAdapter;
	private GroupAdapter groupAdapter;
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
		setContentView(R.layout.activity_result_page);
		// Show the Up button in the action bar.
		setupActionBar();
		
		Intent intent = getIntent();
		final int SID = intent.getIntExtra("SID", -1);
		
		listSportCategory = DataUtility.getAllSportCategoriesBySID(SID);
		Sport sport = DataUtility.getSportBySID(SID);
//		int sportLogo = sport.getLogo();
		
		ArrayList<String> listSCName = new ArrayList<String>();
		
		for (SportCategory sc : listSportCategory)
			listSCName.add(sc.getName());
		
		Spinner spinner = (Spinner) findViewById(R.id.categories_spinner);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, listSCName);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);
		spinner.setSelection(0);
		
		List<Group> listAllGroup = DataUtility.getAllGroupsBySCID(SCID);
		List<Group> listGroup = eliminateListGroup(listAllGroup);
		groupAdapter = new GroupAdapter(this, listGroup);
		
		ListView listView = (ListView) findViewById(R.id.listResult);
		listView.setAdapter(groupAdapter);
		
		buttonKnockout = (Button) findViewById(R.id.buttonKnockoutResult);
		buttonKnockout.setBackgroundColor(Color.parseColor("#C0C2C4"));
		buttonKnockout.setTextColor(Color.parseColor("#8F9194"));
		
		buttonGroup = (Button) findViewById(R.id.buttonGroupResult);
		buttonGroup.setBackgroundColor(Color.parseColor("#FFFFFF"));
		buttonGroup.setTextColor(Color.parseColor("#26B99A"));
		
		((ImageView) findViewById(R.id.logo_sport_result)).setImageResource(sportLogoRound[SID - 1]);
		((TextView) findViewById(R.id.text_sport_result)).setText(sport.getName());
		
		SCID = listSportCategory.get(0).getSCID();
		
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layoutResult);
		
		linearLayout.setVisibility(android.view.View.VISIBLE);
		listView.setVisibility(android.view.View.VISIBLE);
		listView.setBackgroundColor(Color.parseColor("#FFFFFF"));
		
		if(listGroup.size() == 0){
			listView.setVisibility(android.view.View.GONE);
			((LinearLayout) findViewById(R.id.noResult)).setVisibility(android.view.View.VISIBLE);
			((LinearLayout) findViewById(R.id.noResult)).setBackgroundColor(Color.parseColor("#FFFFFF"));
		}else{
			listView.setVisibility(android.view.View.VISIBLE);
			((LinearLayout) findViewById(R.id.noResult)).setVisibility(android.view.View.GONE);
		}
		
		openDefault(linearLayout);
		
		JSONObject val = new JSONObject();
		try{
			val.put("UID", MainActivity.uid);
			val.put("Sport", sport.getName());
		}catch(JSONException e) {
			e.printStackTrace();
		}
		Mixpanel.track("halaman hasil", val);
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
	
	public void openDefault(View view)
	{
		List<Group> listAllGroup = DataUtility.getAllGroupsBySCID(SCID);
		List<Group> listGroup = eliminateListGroup(listAllGroup);
		if(listGroup.size() > 0){
			onClickGroupResult(view);
		}else{
			onClickKnockoutResult(view);
		}
	}
	
	public void onClickKnockoutResult(View view) {
		buttonKnockout.setBackgroundColor(Color.parseColor("#FFFFFF"));
		buttonKnockout.setTextColor(Color.parseColor("#26B99A"));
		
		buttonGroup.setBackgroundColor(Color.parseColor("#C0C2C4"));
		buttonGroup.setTextColor(Color.parseColor("#8F9194"));
		((ListView) findViewById(R.id.listResult)).setVisibility(android.view.View.GONE);
		
		List<Integer> listAllKnockoutLvl = DataUtility.getAllKnockoutLevelBySCID(SCID);
		List<Integer> listKnockoutLvl = eliminateListKnockout(listAllKnockoutLvl, SCID);
		knockoutAdapter = new KnockoutAdapter(this, listKnockoutLvl, SCID);
		
		ListView listView = (ListView) findViewById(R.id.listResult);
		listView.setAdapter(knockoutAdapter);
		listView.setVisibility(android.view.View.VISIBLE);
		
		if(listKnockoutLvl.size() == 0){
			listView.setVisibility(android.view.View.GONE);
			((LinearLayout) findViewById(R.id.noResult)).setVisibility(android.view.View.VISIBLE);
			((LinearLayout) findViewById(R.id.noResult)).setBackgroundColor(Color.parseColor("#FFFFFF"));
		}else{
			listView.setVisibility(android.view.View.VISIBLE);
			((LinearLayout) findViewById(R.id.noResult)).setVisibility(android.view.View.GONE);
		}
		
		int SID = getIntent().getIntExtra("SID", -1);
		JSONObject val = new JSONObject();
		try{
			val.put("UID", MainActivity.uid);
			val.put("Sport", DataUtility.getSportBySID(SID).getName());
			val.put("Tab", "Knockout");
		}catch(JSONException e) {
			e.printStackTrace();
		}
		Mixpanel.track("hasil group/knockout", val);
	}
	
	public void onClickGroupResult(View view) {
		buttonKnockout.setBackgroundColor(Color.parseColor("#C0C2C4"));
		buttonKnockout.setTextColor(Color.parseColor("#8F9194"));
		
		buttonGroup.setBackgroundColor(Color.parseColor("#FFFFFF"));
		buttonGroup.setTextColor(Color.parseColor("#26B99A"));
		
//		((ListView) findViewById(R.id.listResult)).setVisibility(android.view.View.VISIBLE);
//		((ListView) findViewById(R.id.listResult)).setAdapter(groupAdapter);

		List<Group> listAllGroup = DataUtility.getAllGroupsBySCID(SCID);
		List<Group> listGroup = eliminateListGroup(listAllGroup);
		groupAdapter = new GroupAdapter(this, listGroup);
		
		ListView listView = (ListView) findViewById(R.id.listResult);
		listView.setAdapter(groupAdapter);
		listView.setVisibility(android.view.View.VISIBLE);
		
		if(listGroup.size() == 0){
			listView.setVisibility(android.view.View.GONE);
			((LinearLayout) findViewById(R.id.noResult)).setVisibility(android.view.View.VISIBLE);
			((LinearLayout) findViewById(R.id.noResult)).setBackgroundColor(Color.parseColor("#FFFFFF"));
		}else{
			listView.setVisibility(android.view.View.VISIBLE);
			((LinearLayout) findViewById(R.id.noResult)).setVisibility(android.view.View.GONE);
		}
		
		int SID = getIntent().getIntExtra("SID", -1);
		JSONObject val = new JSONObject();
		try{
			val.put("UID", MainActivity.uid);
			val.put("Sport", DataUtility.getSportBySID(SID).getName());
			val.put("Tab", "Group");
		}catch(JSONException e) {
			e.printStackTrace();
		}
		Mixpanel.track("hasil group/knockout", val);
	}
	
	public List<Group> eliminateListGroup(List<Group> listAllGroup) {
		List<Group> ret = new ArrayList<Group>();
		
		for(Group g : listAllGroup){
			List<Match> matches = DataUtility.getAllMatchesByGroup(g.getGID());
			
			if(matches.size() != 0)
				ret.add(g);
		}
		
		return ret;
	}
	
	public List<Integer> eliminateListKnockout(List<Integer> listAllKnockout, int SCID) {
		List<Integer> ret = new ArrayList<Integer>();
		
		for(Integer k : listAllKnockout){
			List<Match> matches = DataUtility.getAllMatchesByKnockoutLvlAndSCID(k, SCID);
			
			if(matches.size() != 0)
				ret.add(k);
		}
		
		return ret;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		// TODO Auto-generated method stub
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layoutResult);
		ListView listView = (ListView) findViewById(R.id.listResult);
		
		SCID = listSportCategory.get(pos).getSCID();
		
		linearLayout.setVisibility(android.view.View.VISIBLE);
		listView.setVisibility(android.view.View.VISIBLE);
		listView.setBackgroundColor(Color.parseColor("#FFFFFF"));
		
		openDefault(linearLayout);
		
		int SID = getIntent().getIntExtra("SID", -1);
		JSONObject val = new JSONObject();
		try{
			val.put("UID", MainActivity.uid);
			val.put("Sport", DataUtility.getSportBySID(SID).getName());
			val.put("Category", listSportCategory.get(pos).getName());
		}catch(JSONException e) {
			e.printStackTrace();
		}
		Mixpanel.track("hasil pilih kategori cabang", val);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
}
