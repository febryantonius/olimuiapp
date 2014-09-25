package com.example.olimpiadeui.Model;

public class ItemNotification {
	private String name;
	private boolean isSelected;
	
	public ItemNotification(String name, boolean isSelected) {
		this.name = name;
		this.isSelected = isSelected;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean getIsSelected() {
		return isSelected;
	}
	
	public void setIsSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
}
