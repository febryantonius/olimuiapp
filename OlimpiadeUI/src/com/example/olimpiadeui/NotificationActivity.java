package com.example.olimpiadeui;

import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.Switch;
import android.widget.ToggleButton;

import com.example.olimpiadeui.Model.Faculty;
import com.example.olimpiadeui.Model.Item;
import com.example.olimpiadeui.Model.ItemNotification;
import com.example.olimpiadeui.Model.Sport;
import com.example.olimpiadeui.utils.DataManager;
import com.example.olimpiadeui.utils.SettingsAdapter;

public class NotificationActivity extends Activity {
	private SparseArray<Item> items = new SparseArray<Item>();
	private Switch button;
	private ExpandableListView listView;
	private SettingsAdapter adapter;
	private SharedPreferences pref;
	private Activity activity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_notification);
		this.activity = this;
		
		pref = getSharedPreferences("NotificationSetting", 0);
		
		createData();
		listView = (ExpandableListView) findViewById(R.id.listSettings);
		adapter = new SettingsAdapter(this, items, pref);
		listView.setAdapter(adapter);
		
		button = ((Switch) findViewById(R.id.toggleButton));
		button.setChecked(pref.getBoolean("isToggle", false));
		button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				pref = getSharedPreferences("NotificationSetting", 0);
				SharedPreferences.Editor editor = pref.edit();
				editor.putBoolean("isToggle", isChecked);
				editor.commit();
				
				if (isChecked) {
					createData();
					listView.setVisibility(android.view.View.VISIBLE);
					adapter = new SettingsAdapter(activity, items, pref);
					listView.setAdapter(adapter);
					Log.d("size anak", "" + adapter.getChildrenCount(0));
				}
				else
					listView.setVisibility(android.view.View.INVISIBLE);
			}
		});
		
		if (button.isChecked())
			listView.setVisibility(android.view.View.VISIBLE);
		else
			listView.setVisibility(android.view.View.INVISIBLE);
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
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onToggleClick(View view) {
		boolean on = button.isChecked();
		
		pref = getSharedPreferences("NotificationSetting", 0);
		SharedPreferences.Editor editor = pref.edit();
		editor.putBoolean("isToggle", on);
		editor.commit();
		
		if (on) {
			createData();
			listView.setVisibility(android.view.View.VISIBLE);
			adapter = new SettingsAdapter(this, items, pref);
			listView.setAdapter(adapter);
			Log.d("size anak", "" + adapter.getChildrenCount(0));
		}
		else
			listView.setVisibility(android.view.View.INVISIBLE);
	}
	
	public void createData() {
		DataManager dm = DataManager.getDataManager();
		dm.open();
		
		Item item1 = new Item("Faculty");
		List<Faculty> listFaculty = dm.getAllFaculties();
		
		for (Faculty f : listFaculty) {
			boolean isSelected = pref.getBoolean(f.getInitialName(), false);
			item1.children.add(new ItemNotification(f.getInitialName(), isSelected));
		}
		
		Item item2 = new Item("Sport");
		List<Sport> listSport = dm.getAllSports();
		
		for (Sport s : listSport) {
			boolean isSelected = pref.getBoolean(s.getName(), false);
			item2.children.add(new ItemNotification(s.getName(), isSelected));
		}
		
//		Log.d("a", item1.children.get(0).getName());
		
		items.append(0, item1);
		items.append(1, item2);
		
		dm.close();
	}
}
