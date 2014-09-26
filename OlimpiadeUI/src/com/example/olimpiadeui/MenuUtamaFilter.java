package com.example.olimpiadeui;

import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
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

import com.example.olimpiadeui.Model.Faculty;
import com.example.olimpiadeui.Model.Match;
import com.example.olimpiadeui.utils.DataUtility;
import com.example.olimpiadeui.utils.MatchAdapter;

public class MenuUtamaFilter extends Activity implements OnItemSelectedListener {
	private ArrayAdapter<Match> matchItemArrayAdapter;
	private ArrayAdapter<String> facultyAdapter;
	private List<Faculty> listFaculty;
	private List<String> listFacultyName;
	private int FID = -1;
	private Spinner spinner;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_menu_utama_filter);
		setupActionBar();
		
		Typeface tf = Typeface.createFromAsset(this.getAssets(), "fonts/edelsans_regular.otf");
		((TextView) findViewById(R.id.filter_title)).setTypeface(tf, Typeface.BOLD);
		
		listFaculty = DataUtility.getAllFaculties();
		listFacultyName = new ArrayList<String>();
		
		listFacultyName.add("Pilih Fakultas");
		
		for (Faculty f : listFaculty)
			listFacultyName.add(f.getInitialName());
		
		spinner = (Spinner) findViewById(R.id.faculties_spinner);
		facultyAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, listFacultyName);
		
		facultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(facultyAdapter);
		spinner.setOnItemSelectedListener(this);
		spinner.setSelection(0);
		FID = 0;
		
		((ImageView) findViewById(R.id.facultyLogoFilter)).setImageResource(R.drawable.ui);
		
		List<Match> listAllMatch =
				DataUtility.getAllMatchesByTime(System.currentTimeMillis(), false);
		
		List<Match> listMatch = new ArrayList<Match>();
		
		for (Match m : listAllMatch) {
			if (isInclude(m))
				listMatch.add(m);
		}
		
		matchItemArrayAdapter = new MatchAdapter(this, listMatch, false, false);
		
		ListView listView = (ListView) findViewById(R.id.listScheduleFilter);
		listView.setAdapter(matchItemArrayAdapter);
		listView.setBackgroundColor(Color.parseColor("#FFFFFF"));
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
		getMenuInflater().inflate(R.menu.menu_utama_filter, menu);
		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		// TODO Auto-generated method stub
		
		if (pos == 0) {
			((Button) findViewById(R.id.buttonPastFilter)).setVisibility(android.view.View.GONE);
			((Button) findViewById(R.id.buttonUpcomingFilter)).setVisibility(android.view.View.GONE);
			((ListView) findViewById(R.id.listScheduleFilter)).setVisibility(android.view.View.GONE);
			((ImageView) findViewById(R.id.facultyLogoFilter)).setImageResource(R.drawable.ui);
			((LinearLayout) findViewById(R.id.noFilter)).setVisibility(android.view.View.GONE);
		}
		else {
			FID = listFaculty.get(pos - 1).getFID();
			
			((Button) findViewById(R.id.buttonPastFilter)).setVisibility(android.view.View.VISIBLE);
			((Button) findViewById(R.id.buttonUpcomingFilter)).setVisibility(android.view.View.VISIBLE);
			((ListView) findViewById(R.id.listScheduleFilter)).setVisibility(android.view.View.VISIBLE);
			
			Faculty f = listFaculty.get(pos - 1);
			((ImageView) findViewById(R.id.facultyLogoFilter)).setImageResource(f.getLogo());
			
			onClickUpcoming(view);
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
	
	public void onClickPast(View view) {
		((Button) findViewById(R.id.buttonPastFilter)).setBackgroundColor(Color.parseColor("#FFFFFF"));
		((Button) findViewById(R.id.buttonPastFilter)).setTextColor(Color.parseColor("#26B99A"));
		((Button) findViewById(R.id.buttonUpcomingFilter)).setBackgroundColor(Color.parseColor("#C0C2C4"));
		((Button) findViewById(R.id.buttonUpcomingFilter)).setTextColor(Color.parseColor("#8F9194"));
		
		List<Match> listAllMatch =
				DataUtility.getAllMatchesByTime(System.currentTimeMillis(), true);
		
		List<Match> listMatch = new ArrayList<Match>();
		
		for (Match m : listAllMatch) {
			if (isInclude(m))
				listMatch.add(m);
		}
		
		matchItemArrayAdapter = new MatchAdapter(this, listMatch, false, true);
		((ListView) findViewById(R.id.listScheduleFilter)).setAdapter(matchItemArrayAdapter);
		
		if (listMatch.size() == 0){
			((ListView) findViewById(R.id.listScheduleFilter)).setVisibility(android.view.View.GONE);
			((LinearLayout) findViewById(R.id.noFilter)).setVisibility(android.view.View.VISIBLE);
			((LinearLayout) findViewById(R.id.noFilter)).setBackgroundColor(Color.parseColor("#FFFFFF"));
		}
		else {
			((ListView) findViewById(R.id.listScheduleFilter)).setVisibility(android.view.View.VISIBLE);
			((LinearLayout) findViewById(R.id.noFilter)).setVisibility(android.view.View.GONE);
		}
	}

	public void onClickUpcoming(View view) {
		((Button) findViewById(R.id.buttonUpcomingFilter)).setBackgroundColor(Color.parseColor("#FFFFFF"));
		((Button) findViewById(R.id.buttonUpcomingFilter)).setTextColor(Color.parseColor("#26B99A"));
		((Button) findViewById(R.id.buttonPastFilter)).setBackgroundColor(Color.parseColor("#C0C2C4"));
		((Button) findViewById(R.id.buttonPastFilter)).setTextColor(Color.parseColor("#8F9194"));
		
		List<Match> listAllMatch =
				DataUtility.getAllMatchesByTime(System.currentTimeMillis(), false);
		
		List<Match> listMatch = new ArrayList<Match>();
		
		for (Match m : listAllMatch) {
			if (isInclude(m))
				listMatch.add(m);
		}
		
		matchItemArrayAdapter = new MatchAdapter(this, listMatch, false, false);
		((ListView) findViewById(R.id.listScheduleFilter)).setAdapter(matchItemArrayAdapter);
		
		if (listMatch.size() == 0){
			((ListView) findViewById(R.id.listScheduleFilter)).setVisibility(android.view.View.GONE);
			((LinearLayout) findViewById(R.id.noFilter)).setVisibility(android.view.View.VISIBLE);
			((LinearLayout) findViewById(R.id.noFilter)).setBackgroundColor(Color.parseColor("#FFFFFF"));
		}
		else {
			((ListView) findViewById(R.id.listScheduleFilter)).setVisibility(android.view.View.VISIBLE);
			((LinearLayout) findViewById(R.id.noFilter)).setVisibility(android.view.View.GONE);
		}
	}
	
	public boolean isInclude(Match m) {
		int mFID1 = m.getFID1();
		int mFID2 = m.getFID2();
				
		return ((mFID1 == FID) || (mFID2 == FID));
	}
	
	@Override
	protected void onResume() {
		listFaculty = DataUtility.getAllFaculties();
		listFacultyName = new ArrayList<String>();
		
		listFacultyName.add("Pilih Fakultas");
		
		for (Faculty f : listFaculty)
			listFacultyName.add(f.getInitialName());
		
		facultyAdapter.clear();
		facultyAdapter.addAll(listFacultyName);
		facultyAdapter.notifyDataSetChanged();
		spinner.setSelection(0);
		((ImageView) findViewById(R.id.facultyLogoFilter)).setImageResource(R.drawable.ui);
		
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
}
