package com.example.olimpiadeui;

import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.olimpiadeui.Model.Faculty;
import com.example.olimpiadeui.utils.DataManager;
import com.example.olimpiadeui.utils.FacultyAdapter;

public class MenuUtamaMedali extends Activity {
	private ArrayAdapter facultyItemArrayAdapter;
	private DataManager dataManager;
	private List<Faculty> listFaculty;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_menu_utama_medali);
		setupActionBar();
		
		Typeface tf = Typeface.createFromAsset(this.getAssets(), "fonts/edelsans_regular.otf");
		((TextView) findViewById(R.id.medals_title)).setTypeface(tf, Typeface.BOLD);
		
		dataManager = DataManager.getDataManager();
		dataManager.open();
		
		listFaculty = dataManager.getAllFaculties();
		
		listFaculty = sortFaculty(listFaculty);
		
		facultyItemArrayAdapter = new FacultyAdapter(this, listFaculty);
		
		ListView listViewFaculty = ((ListView) findViewById(R.id.listFaculty));
		listViewFaculty.setAdapter(facultyItemArrayAdapter);
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
		dataManager.open();
		listFaculty = dataManager.getAllFaculties();
		listFaculty = sortFaculty(listFaculty);
		facultyItemArrayAdapter.clear();
		facultyItemArrayAdapter.addAll(listFaculty);
		facultyItemArrayAdapter.notifyDataSetChanged();
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		dataManager.close();
		super.onPause();
	}
	
	public List<Faculty> sortFaculty(List<Faculty> faculty) {
		int size = faculty.size();
		
		for (int i = 1; i < size; ++i) {
			Faculty temp = faculty.get(i);
			int j = i - 1;
			
			while ((j >= 0) && isHigher(temp, faculty.get(j))) {
				faculty.set(j + 1, faculty.get(j));
				--j;
			}
			
			faculty.set(j + 1, temp);
		}
		
		return faculty;
	}
	
	public boolean isHigher(Faculty a, Faculty b) {
		if (a.getGold() != b.getGold())
			return (a.getGold() > b.getGold());
		
		if (a.getSilver() != b.getSilver())
			return (a.getSilver() > b.getSilver());
		
		if (a.getBronze() != b.getBronze())
			return (a.getBronze() > b.getBronze());
		
		return (a.getFID() < b.getFID());
	}
}
