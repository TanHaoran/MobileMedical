package com.thr.mobilemedical.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.pm.ActivityInfo;
import android.view.Window;

import com.lidroid.xutils.ViewUtils;

/**
 * @description 管理app工具类
 * @date 2015年8月25日 上午10:09:27
 * @version 1.0
 * @Company Buzzlysoft
 * @author Jerry Tan
 * @email thrforever@126.com
 * @remark
 */
public class MyApplication extends Application {

	private List<Activity> activityList = new ArrayList<Activity>();
	private static MyApplication instance;

	private MyApplication() {
	}

	/**
	 * 单例模式中获取唯一的MyApplication实例
	 * 
	 * @return
	 */
	public static MyApplication getInstance() {
		if (null == instance) {
			instance = new MyApplication();
		}
		return instance;
	}

	/**
	 * 添加Activity
	 * 
	 * @param activity
	 */
	public void addActivity(Activity activity) {
		activityList.add(activity);
		// 设置强制竖屏
		if (activity.getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
			activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 添加ViewUtil的设置
		ViewUtils.inject(activity);
	}

	/**
	 * 移除一个Activity
	 * 
	 * @param activity
	 */
	public void removeActivity(Activity activity) {
		activityList.remove(activity);
	}

	/**
	 * 遍历所有Activity
	 */
	public void finishAllActivity() {
		for (Activity activity : activityList) {
			activity.finish();
		}
		activityList.clear();
	}

	/**
	 * 遍历所有Activity并finish
	 */
	public void finishApplication() {
		for (Activity activity : activityList) {
			activity.finish();
		}
		activityList.clear();
		System.exit(0);
	}
}
