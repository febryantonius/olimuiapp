package com.example.olimpiadeui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class HelpGuide extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help_guide);
		
		Intent intent = getIntent();
		int idGambar = intent.getIntExtra("gambarHelp", -1);
//		Log.d("cek", "" + idGambar);
		((ImageView) findViewById(R.id.gambarHelp)).setImageResource(idGambar);
	}
}
