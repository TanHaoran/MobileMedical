package com.thr.mobilemedical.utils;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;

/**
 * @description ActivityUtil.java
 * @date 2015年9月14日 下午2:39:53
 * @version 1.0
 * @Company Buzzlysoft
 * @author Jerry Tan
 * @email thrforever@126.com
 * @remark
 */
public class ActivityUtil {

	private ActivityUtil() {
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	/**
	 * 获取当前正在运行的Activity类名
	 * 
	 * @return
	 */
	public static String getCurrentActivity(Context context) {
		ActivityManager manager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> runningTasks = manager.getRunningTasks(1);
		RunningTaskInfo cinfo = runningTasks.get(0);
		ComponentName component = cinfo.topActivity;
		L.i(component.getClassName());
		L.i(component.getShortClassName());
		return component.getShortClassName();
	}

}
