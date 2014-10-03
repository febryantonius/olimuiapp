package com.example.olimpiadeui;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;

import com.example.olimpiadeui.MenuUtama;

import com.example.olimpiadeui.utils.DataManager;
import com.example.olimpiadeui.utils.DataUtility;

public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        
        DataManager.createDataManager(getApplicationContext());
        DataUtility.inisialisasiData();
        Intent intent = new Intent(MainActivity.this, MenuUtama.class);
		startActivityForResult(intent, 1);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_utama_bagan, menu);
		return true;
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		finish();
	}
}
