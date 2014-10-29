package com.kawunglabs.olimpiadeuiapp.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

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

import com.kawunglabs.olimpiadeuiapp.MainActivity;
import com.kawunglabs.olimpiadeuiapp.Mixpanel;
import com.kawunglabs.olimpiadeuiapp.R;
import com.kawunglabs.olimpiadeuiapp.Model.Match;
import com.kawunglabs.olimpiadeuiapp.R.id;
import com.kawunglabs.olimpiadeuiapp.R.layout;

public class MatchAdapter extends ArrayAdapter<Match> {
	private LayoutInflater inflater;
	private List<Match> items;
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
		
		final Match currMatch = (Match) items.get(position);
		
		int SID = currMatch.getSID();
		int SCID = currMatch.getSCID();
		int GID = currMatch.getGID();
		
		String sport = DataUtility.getSportBySID(SID).getName();
		String sport_category = DataUtility.getSportCategoryBySCID(SCID).getName();
		String group = "";
		String p1 = currMatch.getpName1();
		String p2 = currMatch.getpName2();
		String pName1 = p1;
		String pName2 = p2;
		int FID1 = currMatch.getFID1();
		int FID2 = currMatch.getFID2();
		int logoFaculty1 = DataUtility.getFaculty(FID1).getLogo();
		int logoFaculty2 = DataUtility.getFaculty(FID2).getLogo();
		int score1 = currMatch.getScore1();
		int score2 = currMatch.getScore2();
		
		if (score1 != -1) {
			p1 = p1 + " (" + score1 + ")";
			p2 = "(" + score2 + ") " + p2;
		}
		
		String kategori;
		
		if (isSchedule) {
			if (GID != -1)
				group = DataUtility.getGroup(GID).getName();
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
		
		String cabang = DataUtility.getSportBySID(SID).getName();
		String sC = DataUtility.getSportCategoryBySCID(SCID).getName();
		String tanggal = currMatch.getMatchDate();
		String waktu = currMatch.getStartTime();
		String lokasi = currMatch.getLokasi();
		
		final String tweet;
		
		if (isPast) {
			tweet = "Pertandingan " + cabang + " - " + sC + " antara " + pName1 +
						((score1 == -1) ? "" : " (" + score1 + ")") + " vs " + ((score2 == -1) ? "" : "(" + score2 + ") ") +
						pName2 + " pada " + tanggal + " " + waktu + " @ " + lokasi + ". Selamat!" + " #" + DataUtility.hashtag;
		}
		else {
			tweet = "Tonton pertandingan " + cabang + " - " + sC + " antara " + pName1 + " vs " +
						pName2 + " pada " + tanggal + " " + waktu + " @ " + lokasi + " #" + DataUtility.hashtag;
		}
		
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String tweetUrl = "";
				
				try {
					tweetUrl = "https://twitter.com/intent/tweet?text=" + URLEncoder.encode(tweet, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Uri uri = Uri.parse(tweetUrl);
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				getContext().startActivity(intent);
				
				JSONObject val = new JSONObject();
				try{
					val.put("UID", MainActivity.uid);
					val.put("Page", "Jadwal");
					val.put("PastUpcoming", isPast ? "Past" : "Upcoming");
				}catch(JSONException e) {
					e.printStackTrace();
				}
				Mixpanel.track("tweet", val);
			}
		});
		
		return convertView;
	}
}
