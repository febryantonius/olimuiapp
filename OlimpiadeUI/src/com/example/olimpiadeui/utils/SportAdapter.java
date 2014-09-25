package com.example.olimpiadeui.utils;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.olimpiadeui.R;
import com.example.olimpiadeui.Model.Sport;

public class SportAdapter extends ArrayAdapter<Sport> {
	private LayoutInflater inflater;
	private List<Sport> listSport;
	
	public SportAdapter(Activity activity, List<Sport> listSport) {
		super(activity, -1, listSport);
		inflater = activity.getWindow().getLayoutInflater();
		this.listSport = listSport;
	}
	
	@Override
	public View	getView(int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(R.layout.sport, null);
		
		final Sport currSport = (Sport) listSport.get(position);
		
		((ImageView) convertView.findViewById(R.id.logo_sport)).setImageResource(currSport.getLogo());
		
		return convertView;
	}
}