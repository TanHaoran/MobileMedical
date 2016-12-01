package com.thr.mobilemedical.utils;

import com.thr.mobilemedical.view.MyAlertDialog;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InternetUtil {

	private static MyAlertDialog netDialog;

	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		if (netDialog != null && netDialog.isShowing()) {
			return false;
		}
		netDialog = new MyAlertDialog(context, "请检查网络设置！");
		netDialog.show();
		return false;
	}

}
