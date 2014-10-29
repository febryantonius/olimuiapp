package com.kawunglabs.olimpiadeuiapp.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import com.kawunglabs.olimpiadeuiapp.Model.Faculty;
import com.kawunglabs.olimpiadeuiapp.Model.Group;
import com.kawunglabs.olimpiadeuiapp.Model.Match;
import com.kawunglabs.olimpiadeuiapp.Model.Sport;
import com.kawunglabs.olimpiadeuiapp.Model.SportCategory;

public class DataUtility {
	private static List<Match> allMatches;
	private static List<Sport> allSports;
	private static List<SportCategory> allSportCategories;
	private static List<Group> allGroups;
	private static List<Faculty> allFaculties;
	private static List<Faculty> sortedFaculties;
	private static boolean downloading = false;
	public static String hashtag = "OlimpiadeUIApp";
	
	public static void inisialisasiData() {
		DataManager dm = DataManager.getDataManager();
		dm.open();
		
		List<Match> tempMatches = dm.getAllMatches();
		List<Sport> tempSports = dm.getAllSports();
		List<SportCategory> tempSportCategories = dm.getAllSportCategories();
		List<Group> tempGroups = dm.getAllGroups();
		List<Faculty> tempFaculties = dm.getAllFaculties();
		
		allMatches = tempMatches;
		allSports = tempSports;
		allSportCategories = tempSportCategories;
		allGroups = tempGroups;
		allFaculties = tempFaculties;
		sortedFaculties = sortFaculty(tempFaculties);
		
		dm.close();
	}

	public static List<Match> getAllMatches() {
		List<Match> ret = new ArrayList<Match>();
		
		for (Match m : allMatches)
			ret.add(m);
		
		return ret;
	}
	
	public static List<Match> getAllMatchesByTime(long millis_time, boolean past) {
		millis_time /= 1000;
		List<Match> ret = new ArrayList<Match>();
		int n = allMatches.size();
		
		if (past) {
			for (int i = n - 1; i >= 0; --i) {
				Match m = allMatches.get(i);
				
				if (m.getMillisTime() <= millis_time)
					ret.add(m);
			}
		}
		else {
			for (int i = 0; i < n; ++i) {
				Match m = allMatches.get(i);
				
				if (m.getMillisTime() > millis_time)
					ret.add(m);
			}
		}
		
		return ret;
	}
	
	public static List<Match> getAllMatchesByKnockoutLvlAndSCID(
			int knockoutLvl, int SCID) {
		List<Match> ret = new ArrayList<Match>();
		
		for (Match m : allMatches) {
			if ((m.getSCID() == SCID)
					&& (m.getKnockoutLvl() == knockoutLvl))
				ret.add(m);
		}
		
		return ret;
	}

	public static List<Match> getAllMatchesByGroup(int GID) {
		List<Match> ret = new ArrayList<Match>();
		
		for (Match m : allMatches) {
			if (m.getGID() == GID)
				ret.add(m);
		}
		
		return ret;
	}

	public static List<Sport> getAllSports() {
		List<Sport> ret = new ArrayList<Sport>();
		
		for (Sport s : allSports)
			ret.add(s);
		
		return ret;
	}
	
	public static Sport getSportBySID(int SID) {
		for (Sport s: allSports) {
			if (s.getSID() == SID)
				return new Sport(s);
		}
		
		return null;
	}

	public static List<SportCategory> getAllSportCategories() {
		List<SportCategory> ret = new ArrayList<SportCategory>();
		
		for (SportCategory sc : allSportCategories)
			ret.add(sc);
		
		return ret;
	}
	
	public static List<SportCategory> getAllSportCategoriesBySID(int SID) {
		List<SportCategory> ret = new ArrayList<SportCategory>();
		
		for (SportCategory sc: allSportCategories) {
			if (sc.getSID() == SID)
				ret.add(sc);
		}
		
		return ret;
	}
	
	public static SportCategory getSportCategoryBySCID(int SCID) {
		for (SportCategory sc : allSportCategories) {
			if (sc.getSCID() == SCID)
				return new SportCategory(sc);
		}
		
		return null;
	}

	public static List<Group> getAllGroups() {
		List<Group> ret = new ArrayList<Group>();
		
		for (Group g : allGroups)
			ret.add(g);
		
		return ret;
	}
	
	public static List<Group> getAllGroupsBySCID(int SCID) {
		List<Group> ret = new ArrayList<Group>();
		
		for (Group g : allGroups) {
			if (g.getSCID() == SCID)
				ret.add(g);
		}
		
		return ret;
	}
	
	public static Group getGroup(int GID) {
		for (Group g : allGroups) {
			if (g.getGID() == GID)
				return new Group(g);
		}
		
		return null;
	}

	public static List<Faculty> getAllFaculties() {
		List<Faculty> ret = new ArrayList<Faculty>();
		
		for (Faculty f : allFaculties)
			ret.add(f);
		
		return ret;
	}
	
	public static List<Faculty> getSortedFaculties() {
		List<Faculty> ret = new ArrayList<Faculty>();
		
		for (Faculty f : sortedFaculties)
			ret.add(f);
		
		return ret;
	}
	
	public static Faculty getFaculty(int FID) {
		for (Faculty f : allFaculties) {
			if (f.getFID() == FID)
				return new Faculty(f);
		}
		
		return null;
	}

	public static List<Integer> getAllKnockoutLevelBySCID(int SCID) {
		List<Integer> ret = new ArrayList<Integer>();
		TreeSet<Integer> treeSet = new TreeSet<Integer>();
		
		for (Match m : allMatches) {
			if (m.getKnockoutLvl() != -1)
				treeSet.add(m.getKnockoutLvl());
		}
		
		Iterator<Integer> it = treeSet.descendingIterator();
		
		while (it.hasNext()) {
			ret.add(it.next());
		}
		
		return ret;
	}
	
	public static boolean isDownloading() {
		return downloading;
	}
	
	public static void startDownload() {
		downloading = true;
	}
	
	public static void finishDownload() {
		downloading = false;
	}
	
	private static List<Faculty> sortFaculty(List<Faculty> faculty) {
		List<Faculty> ret = new ArrayList<Faculty>();
		
		for (Faculty f : faculty)
			ret.add(new Faculty(f));
		
		int size = ret.size();
		
		for (int i = 1; i < size; ++i) {
			Faculty temp = ret.get(i);
			int j = i - 1;
			
			while ((j >= 0) && isHigher(temp, ret.get(j))) {
				ret.set(j + 1, ret.get(j));
				--j;
			}
			
			ret.set(j + 1, temp);
		}
		
		return ret;
	}
	
	private static boolean isHigher(Faculty a, Faculty b) {
		if (a.getGold() != b.getGold())
			return (a.getGold() > b.getGold());
		
		if (a.getSilver() != b.getSilver())
			return (a.getSilver() > b.getSilver());
		
		if (a.getBronze() != b.getBronze())
			return (a.getBronze() > b.getBronze());
		
		return (a.getFID() < b.getFID());
	}
}
