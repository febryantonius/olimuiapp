package com.kawunglabs.olimpiadeuiapp.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

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

import com.kawunglabs.olimpiadeuiapp.MainActivity;
import com.kawunglabs.olimpiadeuiapp.Mixpanel;
import com.kawunglabs.olimpiadeuiapp.R;
import com.kawunglabs.olimpiadeuiapp.Model.Match;

public class KnockoutAdapter extends ArrayAdapter<Integer> {
	private LayoutInflater inflater;
	private List<Integer> items;
	private int SCID;
	private Activity activity;
	
	public KnockoutAdapter(Activity activity, List<Integer> items, int SCID) {
		super(activity, -1, items);
		inflater = activity.getWindow().getLayoutInflater();
		this.items = items;
		this.SCID = SCID;
		this.activity = activity;
	}
	
	@Override
	public View	getView(int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(R.layout.group_result, null);
		
		final int knockoutLvl = (int) items.get(position);
		String knockoutName = "";
		
		switch (knockoutLvl) {
			case 1: {
				knockoutName = "Final";
				break;
			}
			case 2: {
				knockoutName = "Semi Final";
				break;
			}
			case 4: {
				knockoutName = "Perempat Final";
				break;
			}
			case 8: {
				knockoutName = "Perdelapan Final";
				break;
			}
			case 16: {
				knockoutName = "Perenambelas Final";
				break;
			}
		}
		
		((TextView) convertView.findViewById(R.id.groupName)).setText(knockoutName);
		
		List <Match> listMatch = DataUtility.getAllMatchesByKnockoutLvlAndSCID(knockoutLvl, SCID);
		LinearLayout layout = (LinearLayout) convertView.findViewById(R.id.listGroupMatch);
		
		for (int i = 0; i < listMatch.size(); ++i) {
			Match m = listMatch.get(i);
			
			int FID1 = m.getFID1();
			int FID2 = m.getFID2();
			String p1 = m.getpName1();
			String p2 = m.getpName2();
			int logoFaculty1 = (FID1 != -1) ?
						DataUtility.getFaculty(FID1).getLogo() : R.drawable.ui;
			int logoFaculty2 = (FID2 != -1) ?
						DataUtility.getFaculty(FID2).getLogo() : R.drawable.ui;
			int score1 = m.getScore1();
			int score2 = m.getScore2();
			String knockoutKey = m.getKnockoutKey();
			String textScore = "";
			
			View child = inflater.inflate(R.layout.match_knockout_result, null);
			
			textScore = ((score1 == -1) ? "" : score1) + " - " + ((score2 == -1) ? "" : score2);
			int id = activity.getResources().getIdentifier(knockoutKey.toLowerCase(), "drawable", activity.getPackageName());
			
			((TextView) child.findViewById(R.id.knockoutFacultyName1)).setText(p1);
			((TextView) child.findViewById(R.id.knockoutFacultyName2)).setText(p2);
			((ImageView) child.findViewById(R.id.logo_faculty_1_knockout_result)).setImageResource(logoFaculty1);
			((TextView) child.findViewById(R.id.scoreKnockoutResult)).setText(textScore);
			((ImageView) child.findViewById(R.id.knockoutKey)).setImageResource(id);
			((ImageView) child.findViewById(R.id.logo_faculty_2_knockout_result)).setImageResource(logoFaculty2);
			
			if ((i & 1) == 1)
				child.setBackgroundColor(Color.parseColor("#E0EDEA"));
			else
				child.setBackgroundColor(Color.parseColor("#BFE0D6"));
			
			int SID = m.getSID();
			int SCID = m.getSCID();
			String cabang = DataUtility.getSportBySID(SID).getName();
			String kategori = DataUtility.getSportCategoryBySCID(SCID).getName();
			String tanggal = m.getMatchDate();
			String waktu = m.getStartTime();
			String lokasi = m.getLokasi();
			
			final String tweet = "Pertandingan " + cabang + " - " + kategori + " antara " + p1 +
					((score1 == -1) ? "" : " (" + score1 + ")") + " vs " + ((score2 == -1) ? "" : "(" + score2 + ") ") +
					p2 + " pada " + tanggal + " " + waktu + " @ " + lokasi + ". Selamat!" + " #" + DataUtility.hashtag;
			
			child.setOnClickListener(new OnClickListener() {	
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
						val.put("Page", "Hasil");
					}catch(JSONException e) {
						e.printStackTrace();
					}
					Mixpanel.track("tweet", val);
				}
			});
			
			layout.addView(child);
		}
		
		return convertView;
	}
}

