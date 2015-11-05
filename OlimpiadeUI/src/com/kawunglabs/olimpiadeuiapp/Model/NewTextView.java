package com.kawunglabs.olimpiadeuiapp.Model;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class NewTextView extends TextView {
	public NewTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	public NewTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	public NewTextView(Context context) {
		super(context);
		init();
	}
	
	private void init() {
		if (!isInEditMode()) {
			Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/edelsans_regular.otf");
			setTypeface(tf);
		}
	}
}
