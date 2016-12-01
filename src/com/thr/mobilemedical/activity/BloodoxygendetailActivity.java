package com.thr.mobilemedical.activity;

import java.util.ArrayList;
import java.util.List;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.thr.mobilemedical.R;
import com.thr.mobilemedical.adapter.PatientAdapter;
import com.thr.mobilemedical.bean.Nursingrecord;
import com.thr.mobilemedical.constant.LoginInfo;
import com.thr.mobilemedical.utils.SelectbarUtil;
import com.thr.mobilemedical.view.PatientPopupWindow;
import com.thr.mobilemedical.view.SelectPopupWindow;
import com.thr.mobilemedical.view.TitleBar;
import com.thr.mobilemedical.view.SelectPopupWindow.OnEnsureClickListener;
import com.thr.mobilemedical.view.TitleBar.OnLeftClickListener;
import com.thr.mobilemedical.view.TitleBar.OnRightClickListener;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

/**
 * @description BloodsugerdetailActivity.java
 * @date 2015年10月14日 上午11:54:09
 * @version 1.0
 * @Company Buzzlysoft
 * @author Jerry Tan
 * @email thrforever@126.com
 * @remark
 */
@ContentView(R.layout.activity_bloodoxygendetail)
public class BloodoxygendetailActivity extends Activity {

	@ViewInject(R.id.titlebar)
	private TitleBar mTitlebar;
	@ViewInject(R.id.tv_time)
	private TextView mTextTime;

	@ViewInject(R.id.tv_breath)
	private TextView mTextBreath;

	@ViewInject(R.id.tv_saveto)
	private TextView mTextSaveTo;
	// 护理记录单id
	@SuppressWarnings("unused")
	private String nursingrecordId = "";

	// 病患列表
	private PatientPopupWindow mPatientWindow;
	private SelectPopupWindow mSelectTimeWindow;

	private ListView mListView; // 病患列表
	private PatientAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication.getInstance().addActivity(this);
		initView();
	}

	private void initView() {

		initSelectbar();
		mPatientWindow = new PatientPopupWindow(this);
		mSelectTimeWindow = new SelectPopupWindow(this);

		mTitlebar.setLeftImage(R.drawable.top_fh);
		mTitlebar.setRightImage(R.drawable.top_bh);
		mTitlebar.setOnLeftClickListener(new OnLeftClickListener() {

			@Override
			public void onLeftClick(View v) {
				finish();
			}
		});
		mTitlebar.setOnRightClickListener(new OnRightClickListener() {

			@Override
			public void onRightClick(View v) {
				mPatientWindow.showAtLocation(findViewById(R.id.ll_main),
						Gravity.BOTTOM, 0, 0);
			}
		});
		setPatientListView();
	}

	/**
	 * 初始化选择条内容
	 */
	public void initSelectbar() {
		SelectbarUtil selectbarUtil = new SelectbarUtil() {

			@Override
			public void data() {
			}
		};
		selectbarUtil.initView(this, this);
	}

	/**
	 * 初始化病人列表信息
	 */
	protected void setPatientListView() {
		mListView = mPatientWindow.getListView();
		mAdapter = new PatientAdapter(this, LoginInfo.PATIENT_ALL,
				R.layout.item_patient);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				LoginInfo.patientIndex = position;
				LoginInfo.patient = LoginInfo.PATIENT_ALL.get(position);
				mPatientWindow.dismiss();
				initSelectbar();
			}
		});
	}

	@OnClick(R.id.tv_time)
	public void onTime(View v) {
		mSelectTimeWindow.setTitle(R.string.select_time);
		mSelectTimeWindow.setOnEnsureClickListener(new OnEnsureClickListener() {

			@Override
			public void onEnsureClick() {
				mSelectTimeWindow.dismiss();
				mTextTime.setText(mSelectTimeWindow.getWheelString());
				LoginInfo.timeIndex = mSelectTimeWindow.getSelectIndex();
			}
		});
		mSelectTimeWindow.setItems(LoginInfo.timePoints);
		mSelectTimeWindow.showAtLocation(this.findViewById(R.id.ll_main),
				Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
		mSelectTimeWindow.setSelection(LoginInfo.timeIndex);
	}

	@OnClick(R.id.tv_breath)
	public void onBreath(View v) {
	}

	@OnClick(R.id.tv_saveto)
	public void onSaveTo(View v) {
		final SelectPopupWindow saveToWindow = new SelectPopupWindow(this);
		List<String> nursingrecordList = new ArrayList<String>();
		for (Nursingrecord n : LoginInfo.nursingrecordList) {
			nursingrecordList.add(n.getNURSINGNAME());
		}
		saveToWindow.setItems(nursingrecordList);
		saveToWindow.showAtLocation(findViewById(R.id.ll_main), Gravity.BOTTOM
				| Gravity.CENTER_HORIZONTAL, 0, 0);
		saveToWindow.setOnEnsureClickListener(new OnEnsureClickListener() {

			@Override
			public void onEnsureClick() {
				mTextSaveTo.setText(saveToWindow.getWheelString());
				int index = saveToWindow.getSelectIndex();
				nursingrecordId = LoginInfo.nursingrecordList.get(index)
						.getNURSINGID();
			}
		});
	}

	@OnClick(R.id.btn_save)
	public void onSave(View v) {

	}

}
