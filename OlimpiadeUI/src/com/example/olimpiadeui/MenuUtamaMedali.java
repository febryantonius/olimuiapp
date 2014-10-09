package com.example.olimpiadeui;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.olimpiadeui.Model.Faculty;
import com.example.olimpiadeui.utils.DataUtility;
import com.example.olimpiadeui.utils.FacultyAdapter;

public class MenuUtamaMedali extends Activity {
	private ArrayAdapter<Faculty> facultyItemArrayAdapter;
	private List<Faculty> listFaculty;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_utama_medali);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setupActionBar();
		
		Typeface tf = Typeface.createFromAsset(this.getAssets(), "fonts/edelsans_regular.otf");
		((TextView) findViewById(R.id.medals_title)).setTypeface(tf, Typeface.BOLD);
		
		listFaculty = DataUtility.getSortedFaculties();
		
		facultyItemArrayAdapter = new FacultyAdapter(this, listFaculty);
		
		ListView listViewFaculty = ((ListView) findViewById(R.id.listFaculty));
		listViewFaculty.setAdapter(facultyItemArrayAdapter);
		
		JSONObject val = new JSONObject();
		try{
			val.put("UID", MainActivity.uid);
		}catch(JSONException e) {
			e.printStackTrace();
		}
		Mixpanel.track("tab medali", val);
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
		getMenuInflater().inflate(R.menu.menu_utama_medali, menu);
		return true;
	}
	
	@Override
	protected void onResume() {
		listFaculty = DataUtility.getSortedFaculties();
		facultyItemArrayAdapter.clear();
		facultyItemArrayAdapter.addAll(listFaculty);
		facultyItemArrayAdapter.notifyDataSetChanged();
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
}
