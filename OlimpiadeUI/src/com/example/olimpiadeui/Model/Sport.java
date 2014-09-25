package com.example.olimpiadeui.Model;

public class Sport {
	private int SID, logo;
	private String name;
	
	public Sport() {
		
	}
	
	public Sport(int SID, String name, int logo) {
		this.SID = SID;
		this.name = name;
		this.logo = logo;
	}

	public int getSID() {
		return SID;
	}

	public void setSID(int sID) {
		SID = sID;
	}

	public int getLogo() {
		return logo;
	}

	public void setLogo(int logo) {
		this.logo = logo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
