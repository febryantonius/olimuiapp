package com.kawunglabs.olimpiadeuiapp.Model;

public class Match {
	//MILLIS TIME PAKE LONG
	private int MID, GID, SID, SCID, FID1, FID2, score1, score2,
				knockoutLvl;
	private long millisTime;
	private String pName1, pName2, lokasi, startTime,
					endTime, matchDay, matchDate, knockoutKey;
	
	public Match() {
		
	}
	
	public Match(int mID, int gID, int sID, int sCID, String pName1, String pName2,
			int fID1, int fID2, int knockoutLvl, int score1, int score2,
			String lokasi, String matchDay, String matchDate, String startTime,
			String endTime, long millisTime, String knockoutKey) {
		super();
		MID = mID;
		GID = gID;
		SID = sID;
		SCID = sCID;
		FID1 = fID1;
		FID2 = fID2;
		this.score1 = score1;
		this.score2 = score2;
		this.knockoutLvl = knockoutLvl;
		this.millisTime = millisTime;
		this.pName1 = pName1;
		this.pName2 = pName2;
		this.lokasi = lokasi;
		this.startTime = startTime;
		this.endTime = endTime;
		this.matchDay = matchDay;
		this.matchDate = matchDate;
		this.knockoutKey = knockoutKey;
	}

	public int getMID() {
		return MID;
	}

	public void setMID(int mID) {
		MID = mID;
	}

	public int getGID() {
		return GID;
	}

	public void setGID(int gID) {
		GID = gID;
	}

	public int getSID() {
		return SID;
	}

	public void setSID(int sID) {
		SID = sID;
	}

	public int getSCID() {
		return SCID;
	}

	public void setSCID(int sCID) {
		SCID = sCID;
	}

	public int getFID1() {
		return FID1;
	}

	public void setFID1(int fID1) {
		FID1 = fID1;
	}

	public int getFID2() {
		return FID2;
	}

	public void setFID2(int fID2) {
		FID2 = fID2;
	}

	public int getScore1() {
		return score1;
	}

	public void setScore1(int score1) {
		this.score1 = score1;
	}

	public int getScore2() {
		return score2;
	}

	public void setScore2(int score2) {
		this.score2 = score2;
	}

	public int getKnockoutLvl() {
		return knockoutLvl;
	}

	public void setKnockoutLvl(int knockoutLvl) {
		this.knockoutLvl = knockoutLvl;
	}

	public long getMillisTime() {
		return millisTime;
	}

	public void setMillisTime(long millisTime) {
		this.millisTime = millisTime;
	}

	public String getpName1() {
		return pName1;
	}

	public void setpName1(String pName1) {
		this.pName1 = pName1;
	}

	public String getpName2() {
		return pName2;
	}

	public void setpName2(String pName2) {
		this.pName2 = pName2;
	}

	public String getLokasi() {
		return lokasi;
	}

	public void setLokasi(String lokasi) {
		this.lokasi = lokasi;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getMatchDay() {
		return matchDay;
	}

	public void setMatchDay(String matchDay) {
		this.matchDay = matchDay;
	}

	public String getMatchDate() {
		return matchDate;
	}

	public void setMatchDate(String matchDate) {
		this.matchDate = matchDate;
	}
	
	public String getKnockoutKey() {
		return knockoutKey;
	}

	public void setKnockoutKey(String knockoutKey) {
		this.knockoutKey = knockoutKey;
	}
}
