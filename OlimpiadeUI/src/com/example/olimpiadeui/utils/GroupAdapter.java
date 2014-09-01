package com.example.olimpiadeui.utils;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.olimpiadeui.R;
import com.example.olimpiadeui.Model.Group;
import com.example.olimpiadeui.Model.Match;

public class GroupAdapter extends ArrayAdapter<Group> {
	private LayoutInflater inflater;
	private List<Group> items;
	private DataManager dataManager;
	
	public GroupAdapter(Activity activity, List<Group> items) {
		super(activity, -1, items);
		inflater = activity.getWindow().getLayoutInflater();
		this.items = items;
	}
	
	@Override
	public View	getView(int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(R.layout.group_result, null);
		
		dataManager = new DataManager(getContext());
		dataManager.open();
		
		final Group g = (Group) items.get(position);
		int GID = g.getGID();
		String groupName = g.getName();
		((TextView) convertView.findViewById(R.id.groupName)).setText(groupName);
		
		List <Match> listMatch = dataManager.getAllMatchesByGroup(GID);
		LinearLayout layout = (LinearLayout) convertView.findViewById(R.id.listGroupMatch);
		
		for (int i = 0; i < listMatch.size(); ++i) {
			Match m = listMatch.get(i);
			
			int FID1 = m.getFID1();
			int FID2 = m.getFID2();
			String p1 = m.getpName1();
			String p2 = m.getpName2();
			int logoFaculty1 = dataManager.getFacultyLogo(FID1);
			int logoFaculty2 = dataManager.getFacultyLogo(FID2);
			int score1 = m.getScore1();
			int score2 = m.getScore2();
			String textScore = "";
			
			View child = inflater.inflate(R.layout.match_group_result, null);
			
			if (score1 != -1)
				textScore = score1 + " - " + score2;
			else
				textScore = " - ";
			
			((TextView) child.findViewById(R.id.groupFacultyName1)).setText(p1);
			((TextView) child.findViewById(R.id.groupFacultyName2)).setText(p2);
			((ImageView) child.findViewById(R.id.logo_faculty_1_result)).setImageResource(logoFaculty1);
			((TextView) child.findViewById(R.id.scoreResult)).setText(textScore);
			((ImageView) child.findViewById(R.id.logo_faculty_2_result)).setImageResource(logoFaculty2);
			
			if ((i & 1) == 1)
				child.setBackgroundColor(Color.parseColor("#E0EDEA"));
			else
				child.setBackgroundColor(Color.parseColor("#BFE0D6"));
			
			int SID = m.getSID();
			int SCID = m.getSCID();
			String cabang = dataManager.getSportBySID(SID).getName();
			String kategori = dataManager.getSportCategoryName(SCID);
			String tanggal = m.getMatchDate();
			String waktu = m.getStartTime();
			String lokasi = m.getLokasi();
						
			final String tweet = "Pertandingan " + cabang + " - " + kategori + " antara " + p1 +
						((score1 == -1) ? "" : " (" + score1 + ")") + " vs " + ((score2 == -1) ? "" : "(" + score2 + ") ") +
						p2 + " pada " + tanggal + " " + waktu + " @ " + lokasi + ". Selamat!" + " #OlimUIApps";
			
			child.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String tweetUrl = "https://twitter.com/intent/tweet?text=" + tweet;
					Uri uri = Uri.parse(tweetUrl);
					Intent intent = new Intent(Intent.ACTION_VIEW, uri);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					getContext().startActivity(intent);
				}
			});
			
			layout.addView(child);
		}
		
		dataManager.close();
		
		return convertView;
	}
}
