package com.thr.mobilemedical.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @description Marquee.java
 * @date 2015年11月16日 下午4:12:06
 * @version 1.0
 * @Company Buzzlysoft
 * @author Jerry Tan
 * @email thrforever@126.com
 * @remark
 */
public class MarqueeView extends TextView {
	public MarqueeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setFocusable(true);
		setFocusableInTouchMode(true);
	}

	@Override
	protected void onFocusChanged(boolean focused, int direction,
			Rect previouslyFocusedRect) {
		// TODO Auto-generated method stub
		if (focused)
			super.onFocusChanged(focused, direction, previouslyFocusedRect);
	}

	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		// TODO Auto-generated method stub
		if (hasWindowFocus)
			super.onWindowFocusChanged(hasWindowFocus);
	}

	@Override
	public boolean isFocused() {
		return true;
	}
}
