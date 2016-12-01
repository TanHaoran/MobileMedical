package com.thr.mobilemedical.fragment;

import java.util.List;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.thr.mobilemedical.activity.BloodsugerdetailActivity;
import com.thr.mobilemedical.R;
import com.thr.mobilemedical.adapter.BloodAdapter;
import com.thr.mobilemedical.bean.Blood;
import com.thr.mobilemedical.constant.LoginInfo;
import com.thr.mobilemedical.constant.Method;
import com.thr.mobilemedical.constant.SettingInfo;
import com.thr.mobilemedical.utils.GsonUtil;
import com.thr.mobilemedical.utils.HttpUtils;
import com.thr.mobilemedical.utils.L;
import com.thr.mobilemedical.utils.HttpUtils.CallBack;
import com.thr.mobilemedical.view.MyProgressDialog;

/**
 * @description 护理录入-血糖
 * @date 2015年9月11日14:14:36
 * @version 1.0
 * @Company Buzzlysoft
 * @author Jerry Tan
 * @email thrforever@126.com
 * @remark
 */
public class BloodsugerFragment extends Fragment {

	private ListView mListView;

	private MyProgressDialog mDialog;

	private List<Blood> mBloodsugerList;

	private BloodAdapter mAdapter;
	
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			updateView();
		};
	};
	
	/**
	 * 更新体温表
	 */
	private void updateView() {
		setBloodsugerData();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_bloodsuger, container,
				false);
		initView(v);
		loadBloodsugerData(LoginInfo.OFFICE_ID);
		return v;
	}

	/**
	 * 读取血糖的数据
	 * 
	 * @param string
	 */
	private void loadBloodsugerData(String officeId) {
		String url = SettingInfo.NSIS
				+ Method.QUERY_NURSE_ITEM_MOBILE_BY_BLOODSUGAR + "?officeid="
				+ officeId;
		HttpUtils.doGetAsyn(getActivity(), url, new CallBack() {

			@Override
			public void onRequestComplete(String json) {
				L.i("返回值------" + json);
				mBloodsugerList = GsonUtil.getBloodData(json);
				handler.sendEmptyMessage(0);
			}
		}, "血糖数据");
	}

	protected void setBloodsugerData() {
		if (mBloodsugerList != null && mBloodsugerList.size() > 0) {
			mAdapter = new BloodAdapter(getActivity(), mBloodsugerList,
					R.layout.item_blood);
			mListView.setAdapter(mAdapter);
		}
	}

	private void initView(View v) {
		mDialog = new MyProgressDialog(getActivity());
		mListView = (ListView) v.findViewById(R.id.lv_bloodsuger);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				LoginInfo.patientIndex = position;
				LoginInfo.patient = LoginInfo.PATIENT_ALL.get(position);
				Intent intent = new Intent();
				intent.setClass(getActivity(), BloodsugerdetailActivity.class);
				startActivity(intent);
			}
		});
	}
	
	@Override
	public void onResume() {
		super.onResume();
		loadBloodsugerData(LoginInfo.OFFICE_ID);
	}
}
