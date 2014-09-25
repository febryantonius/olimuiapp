package com.example.olimpiadeui.Model;

public class Group {
	private int GID, SCID;
	private String name;
	
	public Group() {
		
	}
	
	public Group(int GID, int SCID, String name) {
		this.GID = GID;
		this.SCID = SCID;
		this.name = name;
	}

	public int getGID() {
		return GID;
	}

	public void setGID(int gID) {
		GID = gID;
	}

	public int getSCID() {
		return SCID;
	}

	public void setSCID(int sCID) {
		SCID = sCID;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
