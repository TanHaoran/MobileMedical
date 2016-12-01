package com.thr.mobilemedical.utils;

import android.app.Activity;

import com.thr.mobilemedical.activity.MyApplication;

/**
 * @description 实现了上层窗口接口的类
 * @date 2015年9月14日 下午1:56:07
 * @version 1.0
 * @Company Buzzlysoft
 * @author Jerry Tan
 * @email thrforever@126.com
 * @remark
 */

public class TopExitImpl implements TopActivityListener {

	private long firstTime = 0;

	@Override
	public void onExit(Activity activity) {
		long secondTime = System.currentTimeMillis();
		// 如果两次按键时间间隔大于1500毫秒，则不退出
		if (secondTime - firstTime > 2000) {
			T.showShort(activity, "再按一次退出程序");
			firstTime = secondTime; // 更新firstTime
		} else {
			MyApplication.getInstance().finishApplication();
		}
	}
}