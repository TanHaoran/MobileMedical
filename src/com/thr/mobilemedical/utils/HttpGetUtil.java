package com.thr.mobilemedical.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.thr.mobilemedical.view.MyAlertDialog;
import com.thr.mobilemedical.view.MyProgressDialog;

/**
 * @description HttpGet请求工具类
 * @date 2015年9月15日 上午10:52:28
 * @version 1.0
 * @Company Buzzlysoft
 * @author Jerry Tan
 * @email thrforever@126.com
 * @remark
 */
public abstract class HttpGetUtil {

	private Context mContext;

	public HttpGetUtil(Context context) {
		mContext = context;
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String content = (String) msg.obj;
			if ("".equals(content)) {
				MyAlertDialog alert = new MyAlertDialog(mContext, "网络错误");
				alert.show();
			} else {
				MyAlertDialog alert = new MyAlertDialog(mContext, content);
				alert.show();
			}
		};
	};

	/**
	 * 通过HttpGet获取读取信息
	 * 
	 * @param url
	 * @param dialog
	 */
	public void doGet(final String url, final MyProgressDialog dialog,
			final Context context) {
		HttpUtils http = new HttpUtils();
		L.i("URL地址------" + url);
		http.send(HttpRequest.HttpMethod.GET, url,
				new RequestCallBack<String>() {

					@Override
					public void onStart() {
						dialog.show();
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						dialog.dismiss();
						String json = responseInfo.result;
						success(json);
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						dialog.dismiss();
						Message m = new Message();
						m.obj = "读取数据失败";
						handler.sendMessage(m);
					}
				});
	}

	/**
	 * 通过HttpGet获取读取信息
	 * 
	 * @param url
	 * @param dialog
	 */
	public void doGet(String url, final MyProgressDialog dialog,
			final Context context, final String message) {
		if (InternetUtil.isNetworkConnected(context)) {

			L.i(message + "URL地址--------" + url);
			HttpUtils http = new HttpUtils(10000);
			http.configCurrentHttpCacheExpiry(1000);
			http.send(HttpRequest.HttpMethod.GET, url,
					new RequestCallBack<String>() {

						@Override
						public void onStart() {
							dialog.show();
						}

						@Override
						public void onSuccess(ResponseInfo<String> responseInfo) {
							dialog.dismiss();
							String json = responseInfo.result;
							success(json);
						}

						@Override
						public void onFailure(HttpException error, String msg) {
							dialog.dismiss();
							Message m = new Message();
							m.obj = "读取" + message + "失败";
							handler.sendMessage(m);
						}
					});
		}
	}

	/**
	 * 成功的方法
	 * 
	 * @param json
	 */
	public abstract void success(String json);

}
