package com.thr.mobilemedical.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thr.mobilemedical.R;
import com.thr.mobilemedical.constant.LoginInfo;
import com.thr.mobilemedical.utils.SelectbarUtil;

/**
 * @description 病患信息-花费查询界面
 * @date 2015年9月11日14:12:14
 * @version 1.0
 * @Company Buzzlysoft
 * @author Jerry Tan
 * @email thrforever@126.com
 * @remark
 */
public class CostFragment extends Fragment {

	private View v;

	private TextView mInhos;
	private TextView mName;
	private TextView mBed;
	private TextView mPrepaid;
	private TextView mTotal;
	private TextView mBalance;
	private TextView mPayway;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.fragment_cost, container, false);
		initView();
		return v;
	}

	private void initView() {

		mInhos = (TextView) v.findViewById(R.id.tv_inhosid);
		mName = (TextView) v.findViewById(R.id.tv_nametext);
		mBed = (TextView) v.findViewById(R.id.tv_bedtext);
		mPrepaid = (TextView) v.findViewById(R.id.tv_prepaid);
		mTotal = (TextView) v.findViewById(R.id.tv_total);
		mBalance = (TextView) v.findViewById(R.id.tv_balance);
		mPayway = (TextView) v.findViewById(R.id.tv_payway);

		initData();
	}


	public void initData() {
		if (LoginInfo.patient != null) {
			mInhos.setText(LoginInfo.patient.getPATIENTHOSID());
			mName.setText(LoginInfo.patient.getNAME());
			mBed.setText(LoginInfo.patient.getBEDNO());
			mPrepaid.setText("¥" + LoginInfo.patient.getPRECOST());
			mTotal.setText("¥ " + LoginInfo.patient.getTOTALCOST());
			mBalance.setText("¥ " + LoginInfo.patient.getBALANCE());
			mPayway.setText(LoginInfo.patient.getMEDICARETYPE());
		}
	}
}
