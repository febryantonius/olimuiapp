package com.kawunglabs.olimpiadeuiapp.utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;

import com.kawunglabs.olimpiadeuiapp.R;
import com.kawunglabs.olimpiadeuiapp.Model.Item;
import com.kawunglabs.olimpiadeuiapp.Model.ItemNotification;

public class SettingsAdapter extends BaseExpandableListAdapter {
	private final SparseArray<Item> items;
	public LayoutInflater inflater;
	public Activity activity;
	private SharedPreferences pref;
	
	public SettingsAdapter(Activity act, SparseArray<Item> items, SharedPreferences pref) {
		activity = act;
		this.items = items;
		inflater = act.getLayoutInflater();
		this.pref = pref;
	}
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return items.get(groupPosition).children.get(childPosition);
	}
	
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ItemNotification children = (ItemNotification) getChild(groupPosition, childPosition);
		
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.listrow_details, null);
		}
		
		final CheckBox box = (CheckBox) convertView.findViewById(R.id.checkBox);
		
		box.setText(children.getName());
		box.setChecked(children.getIsSelected());
		box.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SharedPreferences.Editor editor = pref.edit();
				editor.putBoolean(children.getName(), box.isChecked());
				editor.commit();
			}
		});
		
//		convertView.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Toast.makeText(activity, children.getName(), Toast.LENGTH_SHORT).show();
//				Log.d("")
//			}
//		});
		
		return convertView;
	}
	
	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return items.get(groupPosition).children.size();
	}
	
	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return items.get(groupPosition);
	}
	
	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return items.size();
	}
	
	@Override
	public void onGroupCollapsed(int groupPosition) {
		super.onGroupCollapsed(groupPosition);
	}
	
	@Override
	public void onGroupExpanded(int groupPosition) {
		super.onGroupExpanded(groupPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.listrow_group, null);
		}
		
		Item item = (Item) getGroup(groupPosition);
		((CheckedTextView) convertView).setText(item.name);
		((CheckedTextView) convertView).setChecked(isExpanded);
		
		return convertView;
	}
	
	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}
} 