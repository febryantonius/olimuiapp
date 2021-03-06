package com.kawunglabs.olimpiadeuiapp.utils;

import java.io.IOException;
import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kawunglabs.olimpiadeuiapp.MainActivity;
import com.kawunglabs.olimpiadeuiapp.R;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {
	private int[] logoFaculty = {R.drawable.fk, R.drawable.fkg, R.drawable.fmipa,
			R.drawable.ft, R.drawable.fh, R.drawable.fe, R.drawable.fib,
			R.drawable.fpsi, R.drawable.fisip, R.drawable.fkm, R.drawable.fasilkom,
			R.drawable.fik, R.drawable.ff, R.drawable.vokasi};
	
	private int[] sportLogo = {R.drawable.atletik,
			R.drawable.bulutangkis, R.drawable.basket,
			R.drawable.futsal, R.drawable.hockey,
			R.drawable.renang, R.drawable.sepakbola,
			R.drawable.taekwondo, R.drawable.tenis,
			R.drawable.tenis_meja, R.drawable.voli};
	
	public static final String TABLE_MATCH = "table_match";
	public static final String TABLE_FACULTY = "table_faculty";
	public static final String TABLE_SPORT = "table_sport";
	public static final String TABLE_SPORT_CATEGORY = "table_sport_category";
	public static final String TABLE_GROUP = "table_group";
	public static final String TABLE_LAST_UPDATED = "table_last_updated";
	
	//column tabel match
	public static final String MATCH_ID = "MATCH_ID";
	public static final String GROUP_ID = "GROUP_ID";
	public static final String SPORT_ID = "SPORT_ID";
	public static final String SPORT_CATEGORY_ID = "SPORT_CATEGORY_ID";
	public static final String PARTICIPANT1_NAME = "PARTICIPANT1_NAME";
	public static final String PARTICIPANT2_NAME = "PARTICIPANT2_NAME";
	public static final String FACULTY1_ID = "FID1";
	public static final String FACULTY2_ID = "FID2";
	public static final String SCORE1 = "SCORE1";
	public static final String SCORE2 = "SCORE2";
	public static final String KNOCKOUT_LEVEL = "KNOCKOUT_LEVEL";
	public static final String KNOCKOUT_KEY = "KNOCKOUT_KEY";
	public static final String LOCATION = "LOCATION";
	public static final String MATCH_DAY = "MATCH_DAY";
	public static final String MATCH_DATE = "MATCH_DATE";
	public static final String START_TIME = "START_TIME";
	public static final String END_TIME = "END_TIME";
	public static final String MILLIS_TIME = "MILLIS_TIME";
	
	public static final String DB_CREATE_MATCH = "create table "
		      + TABLE_MATCH + "("
		      + MATCH_ID + " integer primary key, "
		      + GROUP_ID + " integer not null, "
		      + SPORT_ID + " integer not null, "
		      + SPORT_CATEGORY_ID + " integer not null, "
		      + PARTICIPANT1_NAME + " text not null, "
		      + PARTICIPANT2_NAME + " text not null, "
		      + FACULTY1_ID + " integer not null, "
		      + FACULTY2_ID + " integer not null, "
		      + SCORE1 + " integer not null, "
		      + SCORE2 + " integer not null, "
		      + KNOCKOUT_LEVEL + " integer not null, "
		      + KNOCKOUT_KEY + " text not null, "
		      + LOCATION + " text not null, "
		      + MATCH_DAY + " text not null, "
		      + MATCH_DATE + " text not null, "
		      + START_TIME + " text not null, "
		      + END_TIME + " text not null, "
		      + MILLIS_TIME + " integer not null);";
	
	//column tabel sport
	//public static final String SPORT_ID = "SPORT_ID";
	public static final String NAME = "NAME";
	public static final String SPORT_LOGO = "SPORT_LOGO";
	
	public static final String DB_CREATE_SPORT = "create table "
			+ TABLE_SPORT + "("
			+ SPORT_ID + " integer primary key, "
			+ NAME + " text not null, "
			+ SPORT_LOGO + " integer not null);";
	
	//column tabel sport_category
//	public static final String SPORT_ID = "SPORT_ID";
//	public static final String SPORT_CATEGORY_ID = "SPORT_CATEGORY_ID";
//	public static final String NAME = "NAME";
	
	public static final String DB_CREATE_SPORT_CATEGORY = "create table "
			+ TABLE_SPORT_CATEGORY + "("
			+ SPORT_CATEGORY_ID + " integer primary key, "
			+ SPORT_ID + " integer not null, "
			+ NAME + " text not null);";
	
	//column tabel faculty
	public static final String FACULTY_ID = "FACULTY_ID";
	public static final String FACULTY_INITIAL = "FACULTY_INITIAL";
	//public static final String NAME = "NAME";
	public static final String GOLD_MEDAL = "gold";
	public static final String SILVER_MEDAL = "silver";
	public static final String BRONZE_MEDAL = "bronze";
	public static final String FACULTY_LOGO = "faculty_logo";
	
	public static final String DB_CREATE_FACULTY = "create table "
			+ TABLE_FACULTY + "("
			+ FACULTY_ID + " integer primary key, "
			+ FACULTY_INITIAL + " text not null, "
			+ NAME + " text not null, "
			+ GOLD_MEDAL + " integer not null, "
			+ SILVER_MEDAL + " integer not null, "
			+ BRONZE_MEDAL + " integer not null, "
			+ FACULTY_LOGO + " integer not null);";
	
	//column tabel group
	//public static final String GROUP_ID = "GROUP_ID";
	//public static final String SPORT_CATEGORY_ID = "SPORT_CATEGORY_ID";
	//public static final String NAME = "NAME";

	public static final String DB_CREATE_GROUP = "create table "
			+ TABLE_GROUP + "("
			+ GROUP_ID + " integer primary key, "
			+ SPORT_CATEGORY_ID + " integer not null, "
			+ NAME + " text not null);";
	
	//column tabel last updated
	public static final String LAST_UPDATED_ID = "LAST_UPDATED_ID";
	public static final String LAST_UPDATED_TIME = "LAST_UPDATED_TIME";
	
	public static final String DB_CREATE_LAST_UPDATED = "create table "
			+ TABLE_LAST_UPDATED + "("
			+ LAST_UPDATED_ID + " integer primary key, "
			+ LAST_UPDATED_TIME + " long not null);";
	
	public static final String DB_NAME = "olimpiadeui.db";
	public static final int DB_VERSION = 1;
	
	public SQLiteHelper (Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(DB_CREATE_MATCH);
		db.execSQL(DB_CREATE_FACULTY);
		db.execSQL(DB_CREATE_SPORT);
		db.execSQL(DB_CREATE_SPORT_CATEGORY);
		db.execSQL(DB_CREATE_GROUP);
		db.execSQL(DB_CREATE_LAST_UPDATED);
		
		insertFaculty(db);
		insertSport(db);
		insertSportCategory(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.w(SQLiteHelper.class.getName(), "Upgrading database from version " +
				oldVersion + " to " + newVersion);
		
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MATCH);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_FACULTY);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SPORT);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SPORT_CATEGORY);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUP);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LAST_UPDATED);
		onCreate(db);
	}
	
	public String loadJSONFromAsset(String filename) {
		String json = null;
		
		try {
			InputStream inputStream = DataManager.getContext()
										.getAssets()
										.open("JSON/" + filename);
			
			int size = inputStream.available();
			
			byte[] buffer = new byte[size];
			
			inputStream.read(buffer);
			inputStream.close();
			
			json = new String(buffer, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		return json;
	}
	
	public void insertFaculty(SQLiteDatabase db) {
		try {
			JSONArray array = new JSONArray(loadJSONFromAsset("faculties.json"));
			
			for (int i = 0; i < array.length(); ++i) {
				JSONObject obj = array.getJSONObject(i);
				int FID = obj.getInt(SQLiteHelper.FACULTY_ID);
				String initial = obj.getString(SQLiteHelper.FACULTY_INITIAL);
				String name = obj.getString(SQLiteHelper.NAME);
				int gold = obj.getInt(SQLiteHelper.GOLD_MEDAL);
				int silver = obj.getInt(SQLiteHelper.SILVER_MEDAL);
				int bronze = obj.getInt(SQLiteHelper.BRONZE_MEDAL);
				int logo = logoFaculty[FID - 1];
				
				String query = "INSERT INTO table_faculty VALUES (" +
											FID + ", '" +
											initial + "', '" +
											name + "', " +
											gold + ", " +
											silver + ", " +
											bronze + ", " +
											logo + ");";
				
				db.execSQL(query);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void insertSport(SQLiteDatabase db) {
		try {
			JSONArray array = new JSONArray(loadJSONFromAsset("sports.json"));
			
			for (int i = 0; i < array.length(); ++i) {
				JSONObject obj = array.getJSONObject(i);
				int SID = obj.getInt(SQLiteHelper.SPORT_ID);
				String name = obj.getString(SQLiteHelper.NAME);
				int logo = sportLogo[SID - 1];
				
				String query = "INSERT INTO table_sport VALUES (" +
											SID + ", '" +
											name + "', " +
											logo + ");";
				
				db.execSQL(query);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void insertSportCategory(SQLiteDatabase db) {
		try {
			JSONArray array = new JSONArray(loadJSONFromAsset("sport_categories.json"));
			
			for (int i = 0; i < array.length(); ++i) {
				JSONObject obj = array.getJSONObject(i);
				int SCID = obj.getInt(SQLiteHelper.SPORT_CATEGORY_ID);
				int SID = obj.getInt(SQLiteHelper.SPORT_ID);
				String name = obj.getString(SQLiteHelper.NAME);
				
				String query = "INSERT INTO table_sport_category VALUES (" +
											SCID + ", " +
											SID + ", '" +
											name + "');";
				
				db.execSQL(query);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
