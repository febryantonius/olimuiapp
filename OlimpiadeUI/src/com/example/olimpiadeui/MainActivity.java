package com.example.olimpiadeui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.example.olimpiadeui.MenuUtama;
import com.example.olimpiadeui.utils.DataManager;

public class MainActivity extends Activity {
	private MainActivity host = this;
	private DataManager dm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        
        DataManager.createDataManager(getApplicationContext());
        //new DownloadData().execute();
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
	
	class DownloadData extends AsyncTask<Void, Void, Void> {
		private ProgressDialog progressDialog;
		private int statusCode;
		private DataManager dm;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = ProgressDialog.show(host, "Please Wait...", "Updating data...", true, true);
			dm = DataManager.getDataManager();
			dm.open();
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			statusCode = dm.refreshDatabase();
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			dm.close();
			progressDialog.dismiss();
			
			if (statusCode == -1) {
				int duration = Toast.LENGTH_LONG;
				
				Toast toast = Toast.makeText(MainActivity.this,
								"Anda tidak terhubung dengan internet", duration);
				toast.show();
			}
			
			Intent intent = new Intent(MainActivity.this, MenuUtama.class);
			startActivityForResult(intent, 1);
		}
	}
}
