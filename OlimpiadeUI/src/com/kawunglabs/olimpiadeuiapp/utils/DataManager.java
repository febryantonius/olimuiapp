package com.kawunglabs.olimpiadeuiapp.utils;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.kawunglabs.olimpiadeuiapp.R;
import com.kawunglabs.olimpiadeuiapp.Model.Faculty;
import com.kawunglabs.olimpiadeuiapp.Model.Group;
import com.kawunglabs.olimpiadeuiapp.Model.Match;
import com.kawunglabs.olimpiadeuiapp.Model.Sport;
import com.kawunglabs.olimpiadeuiapp.Model.SportCategory;

public class DataManager {
	private SQLiteDatabase db;
	private SQLiteHelper dbHelper;
	private static DataManager dm;
	private static Context context;
	
	private String[] api = {"faculties", "groups", "matches", "sports",
			"sport_categories", "deleted", "schedule_other", "result_other"};
	
//	private String[] initialFaculty = {"FK", "FKG", "FMIPA", "FT", "FH", "FE",
//			"FIB", "FPsi", "FISIP", "FKM", "Fasilkom", "FIK", "FF", "Vokasi"};
	private int[] logoFaculty = {R.drawable.fk, R.drawable.fkg, R.drawable.fmipa,
			R.drawable.ft, R.drawable.fh, R.drawable.fe, R.drawable.fib,
			R.drawable.fpsi, R.drawable.fisip, R.drawable.fkm, R.drawable.fasilkom,
			R.drawable.fik, R.drawable.ff, R.drawable.vokasi};
	
//	private String[] sportName = {"Atletik", "Badminton", "Basket", "Futsal",
//			"Hockey", "Renang", "Sepak Bola", "Taekwondo", "Tenis Lapangan",
//			"Tenis Meja", "Voli"};

	private int[] sportLogo = {R.drawable.atletik,
			R.drawable.bulutangkis, R.drawable.basket,
			R.drawable.futsal, R.drawable.hockey,
			R.drawable.renang, R.drawable.sepakbola,
			R.drawable.taekwondo, R.drawable.tenis,
			R.drawable.tenis_meja, R.drawable.voli};
	
	private String[] column_match = {SQLiteHelper.MATCH_ID, SQLiteHelper.GROUP_ID,
			SQLiteHelper.SPORT_ID, SQLiteHelper.SPORT_CATEGORY_ID,
			SQLiteHelper.PARTICIPANT1_NAME, SQLiteHelper.PARTICIPANT2_NAME,
			SQLiteHelper.FACULTY1_ID, SQLiteHelper.FACULTY2_ID,
			SQLiteHelper.SCORE1, SQLiteHelper.SCORE2, SQLiteHelper.KNOCKOUT_LEVEL,
			SQLiteHelper.KNOCKOUT_KEY, SQLiteHelper.LOCATION, SQLiteHelper.MATCH_DAY,
			SQLiteHelper.MATCH_DATE, SQLiteHelper.START_TIME, SQLiteHelper.END_TIME,
			SQLiteHelper.MILLIS_TIME
	};
	
	private String[] column_sport = {SQLiteHelper.SPORT_ID, SQLiteHelper.NAME,
			SQLiteHelper.SPORT_LOGO
	};
	
	private String[] column_sport_category = {SQLiteHelper.SPORT_CATEGORY_ID,
			SQLiteHelper.SPORT_ID, SQLiteHelper.NAME
	};
	
	private String[] column_group = {SQLiteHelper.GROUP_ID,
			SQLiteHelper.SPORT_CATEGORY_ID, SQLiteHelper.NAME};
	
	private String[] column_faculty = {SQLiteHelper.FACULTY_ID,
			SQLiteHelper.FACULTY_INITIAL, SQLiteHelper.NAME,
			SQLiteHelper.GOLD_MEDAL, SQLiteHelper.SILVER_MEDAL,
			SQLiteHelper.BRONZE_MEDAL, SQLiteHelper.FACULTY_LOGO
	};
	
	private String[] column_last_updated = {SQLiteHelper.LAST_UPDATED_ID,
			SQLiteHelper.LAST_UPDATED_TIME
	};
	
	public static void createDataManager(Context context) {
		if (dm == null)
			dm = new DataManager(context);
	}
	
	public static DataManager getDataManager() {
		return dm;
	}
	
	public static Context getContext() {
		return context;
	}
	
	public DataManager(Context context) {
		dbHelper = new SQLiteHelper(context);
		DataManager.context = context;
	}
	
	public void open() throws SQLException {
		db = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close();
	}
	
	public void createMatch(JSONObject obj) throws JSONException {
		ContentValues values = new ContentValues();
		values.put(SQLiteHelper.MATCH_ID, obj.getInt(SQLiteHelper.MATCH_ID));
		values.put(SQLiteHelper.GROUP_ID, obj.getInt(SQLiteHelper.GROUP_ID));
		values.put(SQLiteHelper.SPORT_ID, obj.getInt(SQLiteHelper.SPORT_ID));
		values.put(SQLiteHelper.SPORT_CATEGORY_ID, obj.getInt(SQLiteHelper.SPORT_CATEGORY_ID));
		values.put(SQLiteHelper.PARTICIPANT1_NAME, obj.getString(SQLiteHelper.PARTICIPANT1_NAME));
		values.put(SQLiteHelper.PARTICIPANT2_NAME, obj.getString(SQLiteHelper.PARTICIPANT2_NAME));
		values.put(SQLiteHelper.FACULTY1_ID, obj.getInt(SQLiteHelper.FACULTY1_ID));
		values.put(SQLiteHelper.FACULTY2_ID, obj.getInt(SQLiteHelper.FACULTY2_ID));
		values.put(SQLiteHelper.KNOCKOUT_LEVEL, obj.getInt(SQLiteHelper.KNOCKOUT_LEVEL));
		values.put(SQLiteHelper.KNOCKOUT_KEY, obj.getString(SQLiteHelper.KNOCKOUT_KEY));
		values.put(SQLiteHelper.SCORE1, obj.getInt(SQLiteHelper.SCORE1));
		values.put(SQLiteHelper.SCORE2, obj.getInt(SQLiteHelper.SCORE2));
		values.put(SQLiteHelper.LOCATION, obj.getString(SQLiteHelper.LOCATION));
		values.put(SQLiteHelper.MATCH_DAY, obj.getString(SQLiteHelper.MATCH_DAY));
		values.put(SQLiteHelper.MATCH_DATE, obj.getString(SQLiteHelper.MATCH_DATE));
		values.put(SQLiteHelper.START_TIME, obj.getString(SQLiteHelper.START_TIME));
		values.put(SQLiteHelper.END_TIME, obj.getString(SQLiteHelper.END_TIME));
		values.put(SQLiteHelper.MILLIS_TIME, obj.getLong(SQLiteHelper.MILLIS_TIME));
		
		int MID = obj.getInt(SQLiteHelper.MATCH_ID);
		
		String whereClause = SQLiteHelper.MATCH_ID + " = " + MID;
		
		int rowUpdated = db.update(SQLiteHelper.TABLE_MATCH, values, whereClause, null);
		
		if (rowUpdated == 0)
			db.insert(SQLiteHelper.TABLE_MATCH, null, values);
	}
	
	public void createGroup(JSONObject obj) throws JSONException {
		ContentValues values = new ContentValues();
		values.put(SQLiteHelper.GROUP_ID, obj.getInt(SQLiteHelper.GROUP_ID));
		values.put(SQLiteHelper.SPORT_CATEGORY_ID, obj.getInt(SQLiteHelper.SPORT_CATEGORY_ID));
		values.put(SQLiteHelper.NAME, obj.getString(SQLiteHelper.NAME));
		
		int GID = obj.getInt(SQLiteHelper.GROUP_ID);
		
		String whereClause = SQLiteHelper.GROUP_ID + " = " + GID;
		
		int rowUpdated = db.update(SQLiteHelper.TABLE_GROUP, values, whereClause, null);
		
		if (rowUpdated == 0)
			db.insert(SQLiteHelper.TABLE_GROUP, null, values);
	}
	
	public void createSportCategory(JSONObject obj) throws JSONException {
		ContentValues values = new ContentValues();
		values.put(SQLiteHelper.SPORT_CATEGORY_ID, obj.getInt(SQLiteHelper.SPORT_CATEGORY_ID));
		values.put(SQLiteHelper.SPORT_ID, obj.getInt(SQLiteHelper.SPORT_ID));
		values.put(SQLiteHelper.NAME, obj.getString(SQLiteHelper.NAME));
		
		int SCID = obj.getInt(SQLiteHelper.SPORT_CATEGORY_ID);
		
		String whereClause = SQLiteHelper.SPORT_CATEGORY_ID + " = " + SCID;
		
		int rowUpdated = db.update(SQLiteHelper.TABLE_SPORT_CATEGORY, values, whereClause, null);
		
		if (rowUpdated == 0)
			db.insert(SQLiteHelper.TABLE_SPORT_CATEGORY, null, values);
	}
	
	public void createSport(JSONObject obj) throws JSONException {
		ContentValues values = new ContentValues();
		values.put(SQLiteHelper.SPORT_ID, obj.getInt(SQLiteHelper.SPORT_ID));
		values.put(SQLiteHelper.NAME, obj.getString(SQLiteHelper.NAME));
		
		int SID = obj.getInt(SQLiteHelper.SPORT_ID);
		values.put(SQLiteHelper.SPORT_LOGO, sportLogo[SID - 1]);
		
		String whereClause = SQLiteHelper.SPORT_ID + " = " + SID;
		
		int rowUpdated = db.update(SQLiteHelper.TABLE_SPORT, values, whereClause, null);
		
		if (rowUpdated == 0)
			db.insert(SQLiteHelper.TABLE_SPORT, null, values);
	}
	
	public void createFaculty(JSONObject obj) throws JSONException {
		ContentValues values = new ContentValues();
		values.put(SQLiteHelper.FACULTY_ID, obj.getInt(SQLiteHelper.FACULTY_ID));
		values.put(SQLiteHelper.GOLD_MEDAL, obj.getInt(SQLiteHelper.GOLD_MEDAL));
		values.put(SQLiteHelper.SILVER_MEDAL, obj.getInt(SQLiteHelper.SILVER_MEDAL));
		values.put(SQLiteHelper.BRONZE_MEDAL, obj.getInt(SQLiteHelper.BRONZE_MEDAL));
		values.put(SQLiteHelper.NAME, obj.getString(SQLiteHelper.NAME));
		values.put(SQLiteHelper.FACULTY_INITIAL, obj.getString(SQLiteHelper.FACULTY_INITIAL));
		
		int FID = obj.getInt(SQLiteHelper.FACULTY_ID);
		values.put(SQLiteHelper.FACULTY_LOGO, logoFaculty[FID - 1]);
		
		String whereClause = SQLiteHelper.FACULTY_ID + " = " + FID;
		
		int rowUpdated = db.update(SQLiteHelper.TABLE_FACULTY, values, whereClause, null);
		
		if (rowUpdated == 0)
			db.insert(SQLiteHelper.TABLE_FACULTY, null, values);
	}
	
	public void createLastUpdated(int LUID, long LUTime) {
		ContentValues values = new ContentValues();
		values.put(SQLiteHelper.LAST_UPDATED_ID, LUID);
		values.put(SQLiteHelper.LAST_UPDATED_TIME, LUTime);
		
		Cursor cursor = db.query(SQLiteHelper.TABLE_LAST_UPDATED, column_last_updated,
				SQLiteHelper.LAST_UPDATED_ID + " = " + LUID, null, null, null, null);
		
		if (cursor.getCount() == 0)
			db.insert(SQLiteHelper.TABLE_LAST_UPDATED, null, values);
		else {
			String whereClause = SQLiteHelper.LAST_UPDATED_ID + " = " + LUID;
			db.update(SQLiteHelper.TABLE_LAST_UPDATED, values, whereClause, null);
		}
			
		cursor.close();
	}
	
	public List<Match> getAllMatches() {
		List<Match> matches = new ArrayList<Match>();
		
		String sortOrder = SQLiteHelper.MILLIS_TIME + " COLLATE LOCALIZED ASC";
		
		Cursor cursor = db.query(SQLiteHelper.TABLE_MATCH, column_match, 
				null, null, null, null, sortOrder);
		
		cursor.moveToFirst();
		
		while (!cursor.isAfterLast()) {
			Match match = cursorToMatch(cursor);
			matches.add(match);
			cursor.moveToNext();
		}
		
		cursor.close();
		return matches;
	}
	
	public List<SportCategory> getAllSportCategories() {
		List<SportCategory> sportCategories = new ArrayList<SportCategory>();
		
		Cursor cursor = db.query(SQLiteHelper.TABLE_SPORT_CATEGORY,
				column_sport_category, null, null, null, null, null);
		
		cursor.moveToFirst();
		
		while (!cursor.isAfterLast()) {
			SportCategory sportCategory = cursorToSportCategory(cursor);
			sportCategories.add(sportCategory);
			cursor.moveToNext();
		}
		
		cursor.close();
		return sportCategories;
	}
	
	public List<Faculty> getAllFaculties() {
		List<Faculty> faculties = new ArrayList<Faculty>();
		
		Cursor cursor = db.query(SQLiteHelper.TABLE_FACULTY,
				column_faculty, null, null, null, null, null);
		
		cursor.moveToFirst();
		
		while (!cursor.isAfterLast()) {
			Faculty faculty = cursorToFaculty(cursor);
			faculties.add(faculty);
			cursor.moveToNext();
		}
		
		cursor.close();
		return faculties;
	}
	
	public List<Group> getAllGroups() {
		List<Group> groups = new ArrayList<Group>();
		
		String sortOrder = SQLiteHelper.GROUP_ID + " COLLATE LOCALIZED ASC";
		
		Cursor cursor = db.query(SQLiteHelper.TABLE_GROUP,
				column_group, null, null, null, null, sortOrder);
		
		cursor.moveToFirst();
		
		while (!cursor.isAfterLast()) {
			Group group = cursorToGroup(cursor);
			groups.add(group);
			cursor.moveToNext();
		}
		
		cursor.close();
		return groups;
	}
	
	public List<Sport> getAllSports() {
		List<Sport> sports = new ArrayList<Sport>();
		
		Cursor cursor = db.query(SQLiteHelper.TABLE_SPORT,
				column_sport, null, null, null, null, null);
		
		cursor.moveToFirst();
		
		while (!cursor.isAfterLast()) {
			Sport sport = cursorToSport(cursor);
			sports.add(sport);
			cursor.moveToNext();
		}
		
		cursor.close();
		return sports;
	}
	
	private long getLastUpdateTime(int LUID) {
		String selection = SQLiteHelper.LAST_UPDATED_ID + " = " + LUID;
		
		Cursor cursor = db.query(SQLiteHelper.TABLE_LAST_UPDATED,
				column_last_updated, selection, null, null, null, null);
		
		if (cursor.getCount() == 0) {
			createLastUpdated(1, 0);
			cursor = db.query(SQLiteHelper.TABLE_LAST_UPDATED,
					column_last_updated, selection, null, null, null, null);
		}
		
		cursor.moveToFirst();
		
		long ret = cursor.getLong(1);
		
		cursor.close();
		
		return ret;
	}
	
	private Match cursorToMatch(Cursor cursor) {
		int ind = 0;
		
		Match match = new Match();
		match.setMID(cursor.getInt(ind++));
		match.setGID(cursor.getInt(ind++));
		match.setSID(cursor.getInt(ind++));
		match.setSCID(cursor.getInt(ind++));
		match.setpName1(cursor.getString(ind++));
		match.setpName2(cursor.getString(ind++));
		match.setFID1(cursor.getInt(ind++));
		match.setFID2(cursor.getInt(ind++));
		match.setScore1(cursor.getInt(ind++));
		match.setScore2(cursor.getInt(ind++));
		match.setKnockoutLvl(cursor.getInt(ind++));
		match.setKnockoutKey(cursor.getString(ind++));
		match.setLokasi(cursor.getString(ind++));
		match.setMatchDay(cursor.getString(ind++));
		match.setMatchDate(cursor.getString(ind++));
		match.setStartTime(cursor.getString(ind++));
		match.setEndTime(cursor.getString(ind++));
		match.setMillisTime(cursor.getInt(ind++));
		
		return match;
	}
	
	private Sport cursorToSport(Cursor cursor) {
		int ind = 0;
		Sport sport = new Sport();
		
		sport.setSID(cursor.getInt(ind++));
		sport.setName(cursor.getString(ind++));
		sport.setLogo(cursor.getInt(ind++));
		
		return sport;
	}
	
	private SportCategory cursorToSportCategory(Cursor cursor) {
		int ind = 0;
		SportCategory sc = new SportCategory();
		
		sc.setSCID(cursor.getInt(ind++));
		sc.setSID(cursor.getInt(ind++));
		sc.setName(cursor.getString(ind++));
		
		return sc;
	}
	
	private Group cursorToGroup(Cursor cursor) {
		int ind = 0;
		Group group = new Group();
		
		group.setGID(cursor.getInt(ind++));
		group.setSCID(cursor.getInt(ind++));
		group.setName(cursor.getString(ind++));
		
		return group;
	}

	private Faculty cursorToFaculty(Cursor cursor) {
		int ind = 0;
		Faculty f = new Faculty();
		
		f.setFID(cursor.getInt(ind++));
		f.setInitialName(cursor.getString(ind++));
		f.setName(cursor.getString(ind++));
		f.setGold(cursor.getInt(ind++));
		f.setSilver(cursor.getInt(ind++));
		f.setBronze(cursor.getInt(ind++));
		f.setLogo(cursor.getInt(ind++));
		
		return f;
	}
	
	public void deleteData(JSONObject obj) throws JSONException {
		String tableName = obj.getString("table");
		int row_id = obj.getInt("id");
		
		String whereClause = tableName.toUpperCase() + "_ID = " + row_id;
		
		db.delete("table_" + tableName, whereClause, null);
	}
	
	public int refreshDatabase() {
		long lastUpdatedTime = getLastUpdateTime(1);
		long currTime = java.lang.System.currentTimeMillis();
		int status = 400;
		
//		Log.d("waktu", "" + (lastUpdatedTime / 1000));
		
		String urlAPI = "http://dev.nandarustam.id/olimuiapp/api_v2/get_";
		
		try {
			for (int numAPI = 0; numAPI < api.length; ++numAPI) {
//				Log.d("waktu", "" + (lastUpdatedTime / 1000));
//				Log.d("url", urlAPI + api[numAPI] + "/" + (lastUpdatedTime / 1000));
				URL u = new URL(urlAPI + api[numAPI] + "/" + (lastUpdatedTime / 1000));
				HttpURLConnection con = (HttpURLConnection) u.openConnection();
				con.setRequestMethod("GET");
				con.setDoOutput(true);
//				Log.d("API", numAPI + "");
				
				con.connect();
				status = con.getResponseCode();
				
				if ((status != 200) && (status != 201))
					break;
//				Log.d("code", "" + status);
				switch (status) {
					case 200:
					case 201: {
						BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
						String input = reader.readLine();
						
						switch (numAPI) {
							case 0: {
								JSONArray listFaculty = new JSONArray(input);
//								Log.d("faculty", "" + listFaculty.length());
								for (int i = 0; i < listFaculty.length(); ++i) {
									JSONObject obj = listFaculty.getJSONObject(i);
									createFaculty(obj);
								}
								break;
							}
							case 1: {
								JSONArray listGroup = new JSONArray(input);
								
								for (int i = 0; i < listGroup.length(); ++i) {
									JSONObject obj = listGroup.getJSONObject(i);
									createGroup(obj);
								}
								break;
							}
							case 2: {
								JSONArray listMatch = new JSONArray(input);

//								Log.d("match", "" + listMatch.length());
								for (int i = 0; i < listMatch.length(); ++i) {
									JSONObject obj = listMatch.getJSONObject(i);
									createMatch(obj);
								}
								break;
							}
							case 3: {
								JSONArray listSport = new JSONArray(input);
								
								for (int i = 0; i < listSport.length(); ++i) {
									JSONObject obj = listSport.getJSONObject(i);
									createSport(obj);
								}
								break;
							}
							case 4: {
								JSONArray listSportCategory = new JSONArray(input);
								
								for (int i = 0; i < listSportCategory.length(); ++i) {
									JSONObject obj = listSportCategory.getJSONObject(i);
									createSportCategory(obj);
								}
								break;
							}
							case 5: {
								JSONArray listDeleteRow = new JSONArray(input);
								
								for (int i = 0; i < listDeleteRow.length(); ++i) {
									JSONObject obj = listDeleteRow.getJSONObject(i);
									deleteData(obj);
								}
								break;
							}
							case 6: {
								JSONArray listScheduleOther = new JSONArray(input);
								
								FileOutputStream fos = DataManager.getContext().openFileOutput(api[numAPI] + ".json", Context.MODE_PRIVATE);
								fos.write(listScheduleOther.toString(3).getBytes());
								fos.close();
							}
							case 7: {
								JSONArray listScheduleOther = new JSONArray(input);
								
								FileOutputStream fos = DataManager.getContext().openFileOutput(api[numAPI] + ".json", Context.MODE_PRIVATE);
								fos.write(listScheduleOther.toString(3).getBytes());
								fos.close();
							}
						}
						
						break;
					}
					case 400: {
//						InputStream is = con.getErrorStream();
//						BufferedReader in = new BufferedReader(new InputStreamReader(is));
//						String inputLine;
//						while ((inputLine = in.readLine()) != null) {
//							Log.d("error", inputLine);
//						}
						break;
					}
				}
			}
			
			
		} catch (Exception e) {
			Log.d("E", "Error " + e.toString());
			status = -1;
		}
		
		if ((status == 200) || (status == 201))
			createLastUpdated(1, currTime);
		else
			status = -1;
		
//		Log.d("A", "Sukses");
		
		return status; // success code
	}
}
