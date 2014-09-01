package com.example.olimpiadeui.utils;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.View.OnLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.olimpiadeui.R;
import com.example.olimpiadeui.Model.Match;
import com.example.olimpiadeui.R.id;
import com.example.olimpiadeui.R.layout;

public class MatchAdapter extends ArrayAdapter<Match> {
	private LayoutInflater inflater;
	private List<Match> items;
	private DataManager dataManager;
	private boolean isSchedule, isPast;
	
	public MatchAdapter(Activity activity, List<Match> items, boolean isSchedule, boolean isPast) {
		super(activity, -1, items);
		inflater = activity.getWindow().getLayoutInflater();
		this.items = items;
		this.isSchedule = isSchedule;
		this.isPast = isPast;
	}
	
	@Override
	public View	getView(int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(R.layout.jadwal_pertandingan, null);
		
		dataManager = new DataManager(getContext());
		dataManager.open();
		
		final Match currMatch = (Match) items.get(position);
		
		int SID = currMatch.getSID();
		int SCID = currMatch.getSCID();
		int GID = currMatch.getGID();
		
		String sport = dataManager.getSportBySID(SID).getName();
		String sport_category = dataManager.getSportCategoryName(SCID);
		String group = "";
		String p1 = currMatch.getpName1();
		String p2 = currMatch.getpName2();
		String pName1 = p1;
		String pName2 = p2;
		int FID1 = currMatch.getFID1();
		int FID2 = currMatch.getFID2();
		int logoFaculty1 = dataManager.getFacultyLogo(FID1);
		int logoFaculty2 = dataManager.getFacultyLogo(FID2);
		int score1 = currMatch.getScore1();
		int score2 = currMatch.getScore2();
		
		if (score1 != -1) {
			p1 = p1 + " (" + score1 + ")";
			p2 = "(" + score2 + ") " + p2;
		}
		
		String kategori;
		
		if (isSchedule) {
			if (GID != -1)
				group = dataManager.getGroupName(GID);
			else {
				int knockoutLvl = currMatch.getKnockoutLvl();
				
				switch (knockoutLvl) {
					case 1: {
						group = "Final";
						break;
					}
					case 2: {
						group = "Semi Final";
						break;
					}
					case 4: {
						group = "Perempat Final";
						break;
					}
					case 8: {
						group = "Perdelapan Final";
						break;
					}
					case 16: {
						group = "Perenambelas Final";
						break;
					}
				}
			}
			
			kategori = sport_category + " - " + group;
		}
		else
			kategori = sport + " - " + sport_category;
		
		String participant = p1 + " vs " + p2;
		String match_date = currMatch.getMatchDay() + ", " + currMatch.getMatchDate();
		String match_time = currMatch.getStartTime() + " - " + currMatch.getEndTime();
		String match_location = currMatch.getLokasi();
		
		((TextView) convertView.findViewById(R.id.kategori)).setText(kategori);
		((TextView) convertView.findViewById(R.id.participant)).setText(participant);
		((TextView) convertView.findViewById(R.id.match_date)).setText(match_date);
		((TextView) convertView.findViewById(R.id.match_time_location)).setText(match_time + " @ " + match_location);
		
		((ImageView) convertView.findViewById(R.id.logo_faculty_1)).setImageResource(logoFaculty1);
		((ImageView) convertView.findViewById(R.id.logo_faculty_2)).setImageResource(logoFaculty2);
		
		String cabang = dataManager.getSportBySID(SID).getName();
		String sC = dataManager.getSportCategoryName(SCID);
		String tanggal = currMatch.getMatchDate();
		String waktu = currMatch.getStartTime();
		String lokasi = currMatch.getLokasi();
		
		final String tweet;
		
		if (isPast) {
			tweet = "Pertandingan " + cabang + " - " + sC + " antara " + pName1 +
						((score1 == -1) ? "" : " (" + score1 + ")") + " vs " + ((score2 == -1) ? "" : "(" + score2 + ") ") +
						pName2 + " pada " + tanggal + " " + waktu + " @ " + lokasi + ". Selamat!" + " #OlimUIApps";
		}
		else {
			tweet = "Tonton pertandingan " + cabang + " - " + sC + " antara " + pName1 + " vs " +
						pName2 + " pada " + tanggal + " " + waktu + " @ " + lokasi + " #OlimUIApps";
		}
		
		convertView.setOnClickListener(new OnClickListener() {
			
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
		
		dataManager.close();
		
		return convertView;
	}
}
