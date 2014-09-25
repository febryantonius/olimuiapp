package com.example.olimpiadeui.Model;

public class Faculty {
	private int FID, gold, silver, bronze, logo;
	private String name, initialName;
	
	public Faculty() {
		
	}
	
	public Faculty(int FID, int gold, int silver, int bronze, int logo,
			String name, String initialName) {
		this.FID = FID;
		this.gold = gold;
		this.silver = silver;
		this.bronze = bronze;
		this.logo = logo;
		this.name = name;
		this.initialName = initialName;
	}

	public int getFID() {
		return FID;
	}

	public void setFID(int fID) {
		FID = fID;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public int getSilver() {
		return silver;
	}

	public void setSilver(int silver) {
		this.silver = silver;
	}

	public int getBronze() {
		return bronze;
	}

	public void setBronze(int bronze) {
		this.bronze = bronze;
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

	public String getInitialName() {
		return initialName;
	}

	public void setInitialName(String initialName) {
		this.initialName = initialName;
	}
}
