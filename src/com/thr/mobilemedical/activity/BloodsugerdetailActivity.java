package com.thr.mobilemedical.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.thr.mobilemedical.R;
import com.thr.mobilemedical.adapter.PatientAdapter;
import com.thr.mobilemedical.bean.Nursingrecord;
import com.thr.mobilemedical.constant.LoginInfo;
import com.thr.mobilemedical.constant.Method;
import com.thr.mobilemedical.constant.SettingInfo;
import com.thr.mobilemedical.utils.ClientUtil;
import com.thr.mobilemedical.utils.ClientUtil.CallBack;
import com.thr.mobilemedical.utils.DateUtil;
import com.thr.mobilemedical.utils.GsonUtil;
import com.thr.mobilemedical.utils.HttpUtils;
import com.thr.mobilemedical.utils.L;
import com.thr.mobilemedical.utils.SelectbarUtil;
import com.thr.mobilemedical.view.MyAlertDialog;
import com.thr.mobilemedical.view.MyDialog;
import com.thr.mobilemedical.view.MyDialog.SureClickListener;
import com.thr.mobilemedical.view.NumberPopupWindow;
import com.thr.mobilemedical.view.NumberPopupWindow.SaveClickListener;
import com.thr.mobilemedical.view.PatientPopupWindow;
import com.thr.mobilemedical.view.SelectPopupWindow;
import com.thr.mobilemedical.view.SelectPopupWindow.OnEnsureClickListener;
import com.thr.mobilemedical.view.TitleBar;
import com.thr.mobilemedical.view.TitleBar.OnLeftClickListener;
import com.thr.mobilemedical.view.TitleBar.OnRightClickListener;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description BloodsugerdetailActivity.java
 * @date 2015年10月14日 上午11:54:09
 * @version 1.0
 * @Company Buzzlysoft
 * @author Jerry Tan
 * @email thrforever@126.com
 * @remark
 */
@SuppressLint("HandlerLeak")
@ContentView(R.layout.activity_bloodsugerdetail)
public class BloodsugerdetailActivity extends Activity {

	@ViewInject(R.id.titlebar)
	private TitleBar mTitlebar;
	@ViewInject(R.id.tv_time)
	private TextView mTextTime;

	@ViewInject(R.id.tv_bloodsuger)
	private TextView mTextBloodsuger;

	@ViewInject(R.id.tv_saveto)
	private TextView mTextSaveTo;
	// 护理记录单id
	private String nursingrecordId = "";

	// 病患列表
	private PatientPopupWindow mPatientWindow;
	private SelectPopupWindow mSelectTimeWindow;

	private NumberPopupWindow mNumberWindow;

	private ListView mListView; // 病患列表
	private PatientAdapter mAdapter;
	protected Map<String, String> mDatas;
	// 保存至的序号
	protected int saveToIndex;

	private static final int UPDATE = 0;
	protected static final int RESULT = 1;

	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case UPDATE:
				setData();
				break;
			case RESULT:
				String result = (String) msg.obj;
				showResultDialog(result);
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication.getInstance().addActivity(this);
		initView();
		loadNursingrecordData(LoginInfo.patient.getPATIENTHOSID(),
				LoginInfo.patient.getPATIENTINTIMES(), DateUtil.getYMD(),
				LoginInfo.timePoints.get(LoginInfo.timeIndex));
	}

	private void initView() {

		initSelectbar();
		mPatientWindow = new PatientPopupWindow(this);
		mSelectTimeWindow = new SelectPopupWindow(this);
		mNumberWindow = new NumberPopupWindow(this);

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
		// 读取进来时设定的护理时间
		mTextTime.setText(LoginInfo.timePoints.get(LoginInfo.timeIndex));
		nursingrecordId = LoginInfo.nursingrecordList.get(0).getNURSINGID();
		setPatientListView();
	}

	/**
	 * 初始化选择条内容
	 */
	public void initSelectbar() {
		SelectbarUtil selectbarUtil = new SelectbarUtil() {

			@Override
			public void data() {
				loadNursingrecordData(LoginInfo.patient.getPATIENTHOSID(),
						LoginInfo.patient.getPATIENTINTIMES(),
						DateUtil.getYMD(),
						LoginInfo.timePoints.get(LoginInfo.timeIndex));
			}
		};
		selectbarUtil.initView(this, this);
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
				loadNursingrecordData(LoginInfo.patient.getPATIENTHOSID(),
						LoginInfo.patient.getPATIENTINTIMES(),
						DateUtil.getYMD(),
						LoginInfo.timePoints.get(LoginInfo.timeIndex));
			}
		});
		mSelectTimeWindow.setItems(LoginInfo.timePoints);
		mSelectTimeWindow.showAtLocation(this.findViewById(R.id.ll_main),
				Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
		mSelectTimeWindow.setSelection(LoginInfo.timeIndex);
	}

	@OnClick(R.id.tv_bloodsuger)
	public void onBloodsuger(View v) {

		mNumberWindow.setTitle("血糖");
		// 将之前保存好的值显示在显示框中
		mNumberWindow.showAtLocation(findViewById(R.id.ll_main), Gravity.BOTTOM
				| Gravity.CENTER_HORIZONTAL, 0, 0);
		// 如果之前保存值了，就显示出来，否则显示0
		String content = mTextBloodsuger.getText().toString();
		if (content != null && !"".equals(content.trim())) {
			mNumberWindow.setScreen(content);
		} else {
			mNumberWindow.setScreen("0");
		}
		mNumberWindow.setSaveClickListener(new SaveClickListener() {

			@Override
			public void onSaveClick() {
				mTextBloodsuger.setText(mNumberWindow.getContent());
				mNumberWindow.dismiss();
			}
		});
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
				loadNursingrecordData(LoginInfo.patient.getPATIENTHOSID(),
						LoginInfo.patient.getPATIENTINTIMES(),
						DateUtil.getYMD(),
						LoginInfo.timePoints.get(LoginInfo.timeIndex));
			}
		});
		mTextSaveTo.setText(LoginInfo.nursingrecordList.get(saveToIndex)
				.getNURSINGNAME());
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
		saveToWindow.setSelection(saveToIndex);
		saveToWindow.setOnEnsureClickListener(new OnEnsureClickListener() {

			@Override
			public void onEnsureClick() {
				mTextSaveTo.setText(saveToWindow.getWheelString());
				saveToIndex = saveToWindow.getSelectIndex();
				nursingrecordId = LoginInfo.nursingrecordList.get(saveToIndex)
						.getNURSINGID();
			}
		});
	}

	/**
	 * 读取单条记录
	 */
	private void loadNursingrecordData(String patientHosId,
			String patientInTimes, String tendDay, String tendTime) {

		String url = SettingInfo.SERVICE + Method.GET_NURSING_LIST
				+ "?PatientHosId=" + patientHosId + "&PatientInTimes="
				+ patientInTimes + "&TENDDAY=" + tendDay + "&TENDTIME="
				+ tendTime;
		HttpUtils.doGetAsyn(this, url, new HttpUtils.CallBack() {

			@Override
			public void onRequestComplete(String json) {
				L.i("血糖单条记录------" + json);
				mDatas = GsonUtil.getNursingrecordToMap(json);
				Message msg = new Message();
				msg.what = UPDATE;
				mHandler.sendMessage(msg);
			}
		}, "血糖单条记录");
	}

	protected void setData() {
		String value = mDatas.get("BLOODSUGAR");
		mTextBloodsuger.setText(value);
	}

	@OnClick(R.id.btn_save)
	public void onSave(View v) {
		// 必须填写好保存到护理记录单才可以提交内容
		String saveTo = mTextSaveTo.getText().toString();
		if (saveTo == null || "".equals(saveTo)) {
			MyAlertDialog alert = new MyAlertDialog(this, "请选择保存至一个护理记录单");
			alert.show();
		} else {
			LayoutInflater inflater = LayoutInflater.from(this);
			View view = inflater
					.inflate(R.layout.view_nursingrecord_text, null);
			ScrollView scroll = new ScrollView(this);
			scroll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					300));
			TextView tContent = (TextView) view.findViewById(R.id.tv_content);
			StringBuilder sb = new StringBuilder();
			MyDialog dialog = null;
			sb.append("护理时间：" + mTextTime.getText().toString() + "\n");
			sb.append("血糖：" + mTextBloodsuger.getText().toString() + "\n");
			sb.append("保存到：" + mTextSaveTo.getText().toString() + "\n");
			tContent.setText(sb.toString());
			scroll.addView(tContent);
			dialog = new MyDialog(this, scroll, new SureClickListener() {

				@Override
				public void onSureClick() {
					postBloodsugerData();
				}
			});
			dialog.show();
			dialog.setTitle("血糖记录单");
		}
	}

	protected void postBloodsugerData() {
		final StringBuilder sb = new StringBuilder();
		sb.append("{\"ds\":[");
		try {
			// TENDTIME 护理时间
			sb.append("{\"KeyProperty\":\"TENDTIME\",\"ValueProperty\":\""
					+ URLEncoder
							.encode(mTextTime.getText().toString(), "utf-8")
					+ "\"},");
			// NURSINGID 护理记录单id
			sb.append("{\"KeyProperty\":\"NURSINGID\",\"ValueProperty\":\""
					+ URLEncoder.encode(nursingrecordId, "utf-8") + "\"},");
			// PATIENTHOSID 病患住院号
			sb.append("{\"KeyProperty\":\"PATIENTHOSID\",\"ValueProperty\":\""
					+ URLEncoder.encode(LoginInfo.patient.getPATIENTHOSID(),
							"utf-8") + "\"},");
			// PATIENTINTIMES 病患住院次数
			sb.append("{\"KeyProperty\":\"PATIENTINTIMES\",\"ValueProperty\":\""
					+ URLEncoder.encode(LoginInfo.patient.getPATIENTINTIMES(),
							"utf-8") + "\"},");
			// NURSINGLISTID 用来表示记录是否编辑的id
			sb.append("{\"KeyProperty\":\"NURSINGLISTID\",\"ValueProperty\":\""
					+ URLEncoder.encode(mDatas.get("NURSINGLISTID"), "utf-8")
					+ "\"},");
			// TENDDAY 护理日期
			sb.append("{\"KeyProperty\":\"TENDDAY\",\"ValueProperty\":\""
					+ URLEncoder.encode(DateUtil.getYMD(), "utf-8") + "\"},");
			// OPERATORTIME 操作时间
			sb.append("{\"KeyProperty\":\"OPERATORTIME\",\"ValueProperty\":\""
					+ URLEncoder.encode(DateUtil.getYMDHMS(), "utf-8") + "\"},");
			// OPERATORID 操作人员id
			sb.append("{\"KeyProperty\":\"OPERATORID\",\"ValueProperty\":\""
					+ URLEncoder.encode(LoginInfo.USER_ID, "utf-8")
					+ "\"},");
			// TENDDATE 护理保存时间
			sb.append("{\"KeyProperty\":\"TENDDATE\",\"ValueProperty\":\""
					+ URLEncoder.encode(DateUtil.getYMDHMS(), "utf-8") + "\"},");
			// 血糖
			sb.append("{\"KeyProperty\":\"BLOODSUGAR\",\"ValueProperty\":\""
					+ URLEncoder.encode(mTextBloodsuger.getText().toString(),
							"utf-8") + "\"},");
			// 拼接地址，发送请求
			final String json = sb.substring(0, sb.length() - 1) + "]}";

			L.i("处理数据" + json);

			String url = SettingInfo.SERVICE + Method.SET_NURSING_LIST;

			L.i("url地址：" + url);
			ClientUtil.doPostAsyn(url, json, new CallBack() {

				@Override
				public void onRequestComplete(String result) {
					// 返回1成功，0失败
					L.i("返回值是：" + result);
					Message msg = new Message();
					msg.obj = result;
					msg.what = RESULT;
					mHandler.sendMessage(msg);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 对提交结果进行提醒
	 */
	private void showResultDialog(String result) {
		String content = null;
		if ("\"1\"".equals(result)) {
			content = "提交成功";
		} else {
			content = "提交失败";
		}
		MyAlertDialog alertDialog = new MyAlertDialog(this, content);
		alertDialog.show();
	}
}
