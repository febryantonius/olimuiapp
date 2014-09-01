package com.example.olimpiadeui;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HelpActivity extends ListActivity {
	private String[] daftarFAQ = {
			"1. Melihat Halaman Utama Schedule",
			"2. Melihat Halaman Past Schedule",
			"3. Melihat Halaman Upcoming Schedule",
			"4. Melihat Halaman Utama Match",
			"5. Melihat Halaman Group Match",
			"6. Melihat Halaman Knock Out Match",
			"7. Melihat Halaman Utama Medals",
			"8. Melihat Halaman Utama Filter",
			"9. Melihat Halaman About"
	};
	
	private int[] gambar = {
			R.drawable.help_schedule,
			R.drawable.help_past,
			R.drawable.help_upcoming,
			R.drawable.help_match,
			R.drawable.help_group,
			R.drawable.help_knockout,
			R.drawable.help_medals,
			R.drawable.help_filter,
			R.drawable.help_about
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, daftarFAQ);
		
		setListAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.help, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(this, HelpGuide.class);
		Log.d("posisi", position + "");
		intent.putExtra("gambarHelp", gambar[position]);
		startActivity(intent);
	}
}
