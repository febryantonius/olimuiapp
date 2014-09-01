package com.example.olimpiadeui.Model;

import java.util.ArrayList;
import java.util.List;

public class Item {
	public String name;
	public final List<ItemNotification> children = new ArrayList<ItemNotification>();
	
	public Item(String name) {
		this.name = name;
	}
}
