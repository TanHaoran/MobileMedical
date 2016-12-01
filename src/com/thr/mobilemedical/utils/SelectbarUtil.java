package com.thr.mobilemedical.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thr.mobilemedical.constant.LoginInfo;
import com.thr.mobilemedical.constant.Method;
import com.thr.mobilemedical.R;
import com.thr.mobilemedical.view.MyAlertDialog;

/**
 * @description 初始化病患选择条的工具类
 * @date 2015年9月29日 上午10:40:17
 * @version 1.0
 * @Company Buzzlysoft
 * @author Jerry Tan
 * @email thrforever@126.com
 * @remark
 */
public abstract class SelectbarUtil {

	// 需要加载数据的方法
	public abstract void data();

	private Context mContext;

	TextView mTopbarBed;
	TextView mTopbarName;
	TextView mTopbarAge;
	TextView mTopbarLevel;

	public void initView(Context context, View v) {
		mContext = context;
		View mTopbar = v.findViewById(R.id.in_topbar);
		init(mTopbar);
	}

	public void initView(Context context, Activity a) {
		mContext = context;
		View mTopbar = a.findViewById(R.id.in_topbar);
		init(mTopbar);
	}

	private void init(View mTopbar) {
		LinearLayout mLeft = (LinearLayout) mTopbar
				.findViewById(R.id.ll_leftpatient);
		LinearLayout mRight = (LinearLayout) mTopbar
				.findViewById(R.id.ll_rightpatient);
		mTopbarBed = (TextView) mTopbar.findViewById(R.id.tv_bed);
		mTopbarName = (TextView) mTopbar.findViewById(R.id.tv_name);
		mTopbarAge = (TextView) mTopbar.findViewById(R.id.tv_age);
		mTopbarLevel = (TextView) mTopbar.findViewById(R.id.tv_nursinglevel);

		changeSelectbar();
		// 点击前一个病患
		mLeft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				LoginInfo.patientIndex--;
				if (LoginInfo.patientIndex < 0) {
					LoginInfo.patientIndex = 0;
					MyAlertDialog dialog = new MyAlertDialog(mContext,
							R.string.info_first_patient);
					dialog.show();
				} else {
					LoginInfo.patient = LoginInfo.PATIENT_ALL
							.get(LoginInfo.patientIndex);
					changeSelectbar();
					data();
				}
			}
		});
		// 点击后一个病患
		mRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				LoginInfo.patientIndex++;
				if (LoginInfo.patientIndex > LoginInfo.PATIENT_ALL.size() - 1) {
					LoginInfo.patientIndex = LoginInfo.PATIENT_ALL.size() - 1;
					MyAlertDialog dialog = new MyAlertDialog(mContext,
							R.string.info_last_patient);
					dialog.show();
				} else {
					LoginInfo.patient = LoginInfo.PATIENT_ALL
							.get(LoginInfo.patientIndex);
					changeSelectbar();
					data();
				}
			}
		});
	}

	/**
	 * 改变文字
	 */
	private void changeSelectbar() {
		if (LoginInfo.patient != null) {
			mTopbarBed.setText(LoginInfo.patient.getBEDNO());
			mTopbarName.setText(LoginInfo.patient.getNAME());
			mTopbarAge.setText(DateUtil.getAge(LoginInfo.patient.getBIRTHDAY())
					+ "岁");
			String level = formatNursingLevel(LoginInfo.patient.getNURSELEVEL());
			mTopbarLevel.setText(level);
		}
	}

	/**
	 * 格式化护理等级
	 * 
	 * @param nurselevel
	 * @return
	 */
	public String formatNursingLevel(String nurselevel) {
		String level = "III";
		if (Method.isNursingSpecial(nurselevel)) {
			level = "特";
		} else if (Method.isNursingOne(nurselevel)) {
			level = "I级";
		} else if (Method.isNursingTwo(nurselevel)) {
			level = "II级";
		} else {
			level = "III级";
		}
		return level;
	}
}