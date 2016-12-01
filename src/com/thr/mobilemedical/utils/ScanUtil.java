package com.thr.mobilemedical.utils;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.thr.mobilemedical.bean.Patient;
import com.thr.mobilemedical.constant.LoginInfo;
import com.thr.mobilemedical.constant.SettingInfo;
import com.thr.mobilemedical.activity.DropexecuteActivity;
import com.thr.mobilemedical.view.MyAlertDialog;

/**
 * @description 注册接收器工具
 * @date 2015年10月16日 上午10:54:15
 * @version 1.0
 * @Company Buzzlysoft
 * @author Jerry Tan
 * @email thrforever@126.com
 * @remark
 */
public class ScanUtil {

	private static MyAlertDialog dialog;

	public static void registerScanReceiver(Context context) {

		final ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);

		BroadcastReceiver scanReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				int barocodelen = intent.getIntExtra("length", -1);
				String result = new String();
				if (barocodelen == -1) {
					result = intent.getStringExtra(SettingInfo.RECEIVE_STRING)
							.toString().replace("#", "");
				} else {
					byte[] barcode = intent.getByteArrayExtra("barcode");
					byte temp = intent.getByteExtra("barcodeType", (byte) 0);
					android.util.Log.i("debug", "----codetype--" + temp);
					result = new String(barcode, 0, barocodelen).replace("#",
							"");
				}
				L.i("扫描结果-------------------" + result);
				String[] strArray = result.split("@");
				if (strArray.length == 3) {
					for (Patient info : LoginInfo.PATIENT_ALL) {
						if (strArray[0].equals(info.getPATIENTHOSID())
								&& strArray[1].equals(info.getPATIENTINTIMES())) {
							LoginInfo.patient = info;
							LoginInfo.patientIndex = LoginInfo.PATIENT_ALL
									.indexOf(info);
							String runningActivity = activityManager
									.getRunningTasks(1).get(0).topActivity
									.getClassName();
							if (!runningActivity
									.endsWith("DropexecuteActivity")) {
								Intent scanIntent = new Intent(context,
										DropexecuteActivity.class);
								context.startActivity(scanIntent);
							}
							return;
						}
					}
					dialog = new MyAlertDialog(context, "病患信息不存在");
					dialog.show();
				} else {
					dialog = new MyAlertDialog(context, "请先扫描病人腕带！");
					dialog.show();
				}
			}

		};
		IntentFilter filter = new IntentFilter(SettingInfo.SCAN_FILTER);
		filter.addAction("urovo.rcv.message");
		context.registerReceiver(scanReceiver, filter);
	}
}
