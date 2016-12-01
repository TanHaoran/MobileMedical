package com.thr.mobilemedical.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thr.mobilemedical.bean.Temperature;
import com.thr.mobilemedical.constant.LoginInfo;
import com.thr.mobilemedical.R;
import com.thr.mobilemedical.activity.TemperaturedetailActivity;
import com.thr.mobilemedical.constant.SettingInfo;
import com.thr.mobilemedical.utils.CommonAdapter;
import com.thr.mobilemedical.utils.DateUtil;
import com.thr.mobilemedical.utils.ViewHolder;

/**
 * @description 体温列表适配器
 * @date 2015年9月30日 下午1:37:59
 * @version 1.0
 * @Company Buzzlysoft
 * @author Jerry Tan
 * @email thrforever@126.com
 * @remark
 */
public class TemperatureAdapter extends CommonAdapter<Temperature> {

	private Context mContext;

	public TemperatureAdapter(Context context, List<Temperature> mDatas,
			int itemLayoutId) {
		super(context, mDatas, itemLayoutId);
		mContext = context;

	}

	@Override
	public void convert(ViewHolder helper, final Temperature item) {
		TextView tBed = helper.getView(R.id.tv_bed);
		TextView tName = helper.getView(R.id.tv_name);
		RelativeLayout layoutTemper1 = helper.getView(R.id.rl_time1);
		RelativeLayout layoutTemper2 = helper.getView(R.id.rl_time2);
		RelativeLayout layoutTemper3 = helper.getView(R.id.rl_time3);
		RelativeLayout layoutTemper4 = helper.getView(R.id.rl_time4);
		RelativeLayout layoutTemper5 = helper.getView(R.id.rl_time5);
		RelativeLayout layoutTemper6 = helper.getView(R.id.rl_time6);
		TextView tTemper1 = helper.getView(R.id.tv_time1);
		TextView tTemper2 = helper.getView(R.id.tv_time2);
		TextView tTemper3 = helper.getView(R.id.tv_time3);
		TextView tTemper4 = helper.getView(R.id.tv_time4);
		TextView tTemper5 = helper.getView(R.id.tv_time5);
		TextView tTemper6 = helper.getView(R.id.tv_time6);
		final ImageView iAdd = helper.getView(R.id.iv_time6);

		tBed.setText(item.getBEDNO());
		tName.setText(item.getNAME());
		if ("".equals(item.getHEAT1())) {
			tTemper1.setVisibility(View.INVISIBLE);
		} else {
			tTemper1.setVisibility(View.VISIBLE);
			tTemper1.setText(item.getHEAT1());
		}
		if ("".equals(item.getHEAT2())) {
			tTemper2.setVisibility(View.INVISIBLE);
		} else {
			tTemper2.setVisibility(View.VISIBLE);
			tTemper2.setText(item.getHEAT2());
		}
		if ("".equals(item.getHEAT3())) {
			tTemper3.setVisibility(View.INVISIBLE);
		} else {
			tTemper3.setVisibility(View.VISIBLE);
			tTemper3.setText(item.getHEAT3());
		}
		if ("".equals(item.getHEAT4())) {
			tTemper4.setVisibility(View.INVISIBLE);
		} else {
			tTemper4.setVisibility(View.VISIBLE);
			tTemper4.setText(item.getHEAT4());
		}
		if ("".equals(item.getHEAT5())) {
			tTemper5.setVisibility(View.INVISIBLE);
		} else {
			tTemper5.setVisibility(View.VISIBLE);
			tTemper5.setText(item.getHEAT5());
		}
		if ("".equals(item.getHEAT6())) {
			tTemper6.setVisibility(View.INVISIBLE);
			iAdd.setVisibility(View.VISIBLE);
		} else {
			tTemper6.setVisibility(View.VISIBLE);
			iAdd.setVisibility(View.INVISIBLE);
			tTemper6.setText(item.getHEAT6());
		}

		// 设置点击监听器，
		// BUG：######
		// 此处记得使用mDatas.indexOf(item)来获取序列号，而不是使用holder.getPosition()。
		// BUG：######
		TemperatureListener listener = new TemperatureListener(
				mDatas.indexOf(item));
		layoutTemper1.setOnClickListener(listener);
		layoutTemper2.setOnClickListener(listener);
		layoutTemper3.setOnClickListener(listener);
		layoutTemper4.setOnClickListener(listener);
		layoutTemper5.setOnClickListener(listener);

		layoutTemper6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (SettingInfo.IS_DEMO) {
					Intent intent = new Intent();
					intent.setClass(mContext, TemperaturedetailActivity.class);
					mContext.startActivity(intent);
					return ;
				}
				if (iAdd.getVisibility() == View.VISIBLE) {
					LoginInfo.patientIndex = mDatas.indexOf(item);
					LoginInfo.patient = LoginInfo.PATIENT_ALL.get(mDatas
							.indexOf(item));
					LoginInfo.timeIndex = checkTimeIndex();
					Intent intent = new Intent();
					intent.setClass(mContext, TemperaturedetailActivity.class);
					mContext.startActivity(intent);
				} else {
					LoginInfo.patientIndex = mDatas.indexOf(item);
					LoginInfo.patient = LoginInfo.PATIENT_ALL.get(mDatas
							.indexOf(item));
					LoginInfo.timeIndex = 5;
					Intent intent = new Intent();
					intent.setClass(mContext, TemperaturedetailActivity.class);
					mContext.startActivity(intent);
				}
			}
		});
	}

	class TemperatureListener implements OnClickListener {
		/**
		 * 点击的是第几个体温
		 */
		private int position;

		public TemperatureListener(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			if (SettingInfo.IS_DEMO) {
				Intent intent = new Intent();
				intent.setClass(mContext, TemperaturedetailActivity.class);
				mContext.startActivity(intent);
				return ;
			}
			int timeIndex = 0;
			switch (v.getId()) {
			case R.id.rl_time1:
				timeIndex = 0;
				break;
			case R.id.rl_time2:
				timeIndex = 1;
				break;
			case R.id.rl_time3:
				timeIndex = 2;
				break;
			case R.id.rl_time4:
				timeIndex = 3;
				break;
			case R.id.rl_time5:
				timeIndex = 4;
				break;
			}
			LoginInfo.patientIndex = position;
			LoginInfo.patient = LoginInfo.PATIENT_ALL.get(position);
			LoginInfo.timeIndex = timeIndex;
			Intent intent = new Intent();
			intent.setClass(mContext, TemperaturedetailActivity.class);
			mContext.startActivity(intent);
		}
	}

	/**
	 * 根据当前时间点计算出，当前应该录什么时间点的体温单
	 * 
	 * @return
	 */
	public int checkTimeIndex() {
		int nowHour = DateUtil.getCurrentHour();
		int index = 0;
		int pos = 24;// 偏移量
		for (int i = 0; i < LoginInfo.timePoints.size(); i++) {
			String t = LoginInfo.timePoints.get(i).substring(2);
			int time = Integer.parseInt(t);
			int temp = Math.abs(time - nowHour);
			if (temp < pos) {
				pos = temp;
				index = i;
			}
		}
		return index;
	}
}
