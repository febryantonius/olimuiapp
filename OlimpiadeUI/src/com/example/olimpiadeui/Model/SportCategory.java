package com.example.olimpiadeui.Model;

public class SportCategory {
	private int SCID;
	private int SID;
	private String name;
	
	public SportCategory() {
		
	}
	
	public SportCategory(int SCID, int SID, String name) {
		this.SCID = SCID;
		this.SID = SID;
		this.name = name;
	}

	public int getSCID() {
		return SCID;
	}

	public void setSCID(int sCID) {
		SCID = sCID;
	}

	public int getSID() {
		return SID;
	}

	public void setSID(int sID) {
		SID = sID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
