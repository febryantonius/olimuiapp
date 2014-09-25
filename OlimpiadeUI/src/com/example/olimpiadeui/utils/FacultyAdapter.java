package com.example.olimpiadeui.utils;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.olimpiadeui.R;
import com.example.olimpiadeui.Model.Faculty;

public class FacultyAdapter extends ArrayAdapter<Faculty> {
	private LayoutInflater inflater;
	private List<Faculty> listFaculty;
	
	public FacultyAdapter(Activity activity, List<Faculty> listFaculty) {
		super(activity, -1, listFaculty);
		inflater = activity.getWindow().getLayoutInflater();
		this.listFaculty = listFaculty;
	}
	
	@Override
	public View	getView(int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(R.layout.medals, null);
		
		final Faculty currFaculty = (Faculty) listFaculty.get(position);
		
		((ImageView) convertView.findViewById(R.id.faculty_logo)).setImageResource(currFaculty.getLogo());
		((TextView) convertView.findViewById(R.id.faculty_name)).setText(currFaculty.getInitialName());
		((TextView) convertView.findViewById(R.id.gold_medal)).setText("" + currFaculty.getGold());
		((TextView) convertView.findViewById(R.id.silver_medal)).setText("" + currFaculty.getSilver());
		((TextView) convertView.findViewById(R.id.bronze_medal)).setText("" + currFaculty.getBronze());
		
		final String tweet = "Perolehan medali " + currFaculty.getInitialName() + ": " + currFaculty.getGold() +
				" emas, " + currFaculty.getSilver() + " perak, dan " + currFaculty.getBronze() +
				" perunggu, menduduki peringkat " + (position + 1) + " #OlimUIApps";
		final int pos = position;
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String tweetUrl = "https://twitter.com/intent/tweet?text=" + tweet;
				Uri uri = Uri.parse(tweetUrl);
				v.setSelected(true);
				v.getFocusables(pos);
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				getContext().startActivity(intent);
			}
		});
		
//		convertView.setOnLongClickListener(new OnLongClickListener() {
//			
//			@Override
//			public boolean onLongClick(View v) {
//				// TODO Auto-generated method stub
//				String tweetUrl = "https://twitter.com/intent/tweet?text=" + tweet;
//				Uri uri = Uri.parse(tweetUrl);
//				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				getContext().startActivity(intent);
//				
//				return true;
//			}
//		});
		
		return convertView;
	}
}