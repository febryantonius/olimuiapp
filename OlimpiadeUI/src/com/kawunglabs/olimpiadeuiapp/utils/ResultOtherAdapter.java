package com.kawunglabs.olimpiadeuiapp.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kawunglabs.olimpiadeuiapp.R;

public class ResultOtherAdapter extends ArrayAdapter<JSONObject> {
	private LayoutInflater inflater;
	private List<JSONObject> items;
	
	public ResultOtherAdapter(Activity activity, List<JSONObject> items) {
		super(activity, -1, items);
		inflater = activity.getWindow().getLayoutInflater();
		this.items = items;
	}
	
	@Override
	public View	getView(int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(R.layout.group_result, null);
		
		String categoryName = "";
		
		try {
			categoryName = items.get(position).getString("kategori");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		((TextView) convertView.findViewById(R.id.groupName)).setText(categoryName);
		
		List <String> listRank = new ArrayList<String>();
		
		for (int i = 1; i <= 3; ++i) {
			String stringRank;
			
			try {
				stringRank = items.get(position).getString("" + i);
			} catch (JSONException e) {
				stringRank = "-1#-";
				e.printStackTrace();
			}
			
			if (stringRank.length() != 0)
				listRank.add(stringRank);
			else
				listRank.add("-1#-");
		}
		
		LinearLayout layout = (LinearLayout) convertView.findViewById(R.id.listGroupMatch);
		
		for (int i = 0; i < listRank.size(); ++i) {
			String stringRank = listRank.get(i);
			String stringRankSplit[] = stringRank.split("#");
			
			int FID = Integer.parseInt(stringRankSplit[0]);
			String facultyName = stringRankSplit[1];
			int logoFaculty = (FID != -1) ?
					DataUtility.getFaculty(FID).getLogo() : R.drawable.ui;
			
			View child = inflater.inflate(R.layout.match_result_other, null);
			
			((TextView) child.findViewById(R.id.result_rank)).setText((i + 1) + ".");
			((TextView) child.findViewById(R.id.result_faculty)).setText(facultyName);
			((ImageView) child.findViewById(R.id.result_logo_faculty)).setImageResource(logoFaculty);
			
			if ((i & 1) == 1)
				child.setBackgroundColor(Color.parseColor("#E0EDEA"));
			else
				child.setBackgroundColor(Color.parseColor("#BFE0D6"));
			
			layout.addView(child);
		}
		
		return convertView;
	}
}

