package com.kawunglabs.olimpiadeuiapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.ImageView;

public class HelpGuide extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_help_guide);
		
		Intent intent = getIntent();
		int idGambar = intent.getIntExtra("gambarHelp", -1);
		((ImageView) findViewById(R.id.gambarHelp)).setImageResource(idGambar);
	}
}
