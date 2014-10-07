package com.example.olimpiadeui;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.provider.Settings.Secure;

import com.example.olimpiadeui.MenuUtama;
import com.example.olimpiadeui.utils.DataManager;
import com.example.olimpiadeui.utils.DataUtility;

public class MainActivity extends Activity 
{
	public static Context appcontext;
	public static Mixpanel mixpanel;
	public static String uid;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		appcontext = getApplicationContext();
        uid = Secure.getString(appcontext.getContentResolver(), Secure.ANDROID_ID);
        mixpanel = new Mixpanel();
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        
        DataManager.createDataManager(getApplicationContext());
        DataUtility.inisialisasiData();
        Intent intent = new Intent(MainActivity.this, MenuUtama.class);
		startActivityForResult(intent, 1);
		
		JSONObject val = new JSONObject();
		try{
			val.put("uid", uid);
		}catch(JSONException e) {
			e.printStackTrace();
		}
		Mixpanel.track("opening app", val);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_utama_bagan, menu);
		return true;
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		super.onActivityResult(requestCode, resultCode, data);
		finish();
	}
	
	@Override
	public void onDestroy()
	{
		Mixpanel.flush();
		super.onDestroy();
	}
}
