package com.example.olimpiadeui;

import org.json.JSONObject;
import android.content.Context;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

public class Mixpanel
{
	public static final String MIXPANEL_TOKEN = "b0ca7a74eeec9a7ba55c37d48f4acc54";
	private static MixpanelAPI instance = null;
	
	public Mixpanel()
	{
		instance = MixpanelAPI.getInstance(MainActivity.appcontext, MIXPANEL_TOKEN);
	}
	
	public static MixpanelAPI getInstance()
	{
		if(instance == null){
			instance = MixpanelAPI.getInstance(MainActivity.appcontext, MIXPANEL_TOKEN);
			return instance;
		}
		return instance;
	}
	
	public static void track(String key, JSONObject val)
	{
		if(instance == null) instance = getInstance();
		instance.track(key, val);
	}
	
	public static void flush()
	{
		if(instance == null) instance = getInstance();
		instance.flush();
	}
}

