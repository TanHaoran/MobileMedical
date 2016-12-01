package com.thr.mobilemedical.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.thr.mobilemedical.R;
import com.thr.mobilemedical.adapter.DropAdapter;
import com.thr.mobilemedical.adapter.PatientAdapter;
import com.thr.mobilemedical.bean.Order;
import com.thr.mobilemedical.bean.Patient;
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
import com.thr.mobilemedical.utils.TopActivityListener;
import com.thr.mobilemedical.utils.TopExitImpl;
import com.thr.mobilemedical.view.LeftMenuPopupWindow;
import com.thr.mobilemedical.view.MyAlertDialog;
import com.thr.mobilemedical.view.MyDialog;
import com.thr.mobilemedical.view.MyDialog.SureClickListener;
import com.thr.mobilemedical.view.MyProgressDialog;
import com.thr.mobilemedical.view.PatientPopupWindow;
import com.thr.mobilemedical.view.SelectDialog;
import com.thr.mobilemedical.view.SelectDialog.OnSureClickListener;
import com.thr.mobilemedical.view.TitleBar;
import com.thr.mobilemedical.view.TitleBar.OnLeftClickListener;
import com.thr.mobilemedical.view.TitleBar.OnRightClickListener;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @description 医嘱执行界面
 * @date 2015年9月25日 下午3:35:11
 * @version 1.0
 * @Company Buzzlysoft
 * @author Jerry Tan
 * @email thrforever@126.com
 * @remark
 */
@SuppressLint("HandlerLeak")
@ContentView(R.layout.activity_dropexecute)
public class DropexecuteActivity extends Activity {

	// 左侧菜单
	private LeftMenuPopupWindow mLeftWindow;
	// 病患菜单
	private PatientPopupWindow mPatientWindow;

	// 标题栏
	@ViewInject(R.id.titlebar)
	private TitleBar mTitleBar;

	@ViewInject(R.id.ll_statistics)
	private LinearLayout mStatisticsLayout;

	private ListView mListView;
	private DropAdapter mAdapter;

	private PatientAdapter mPatientAdapter;

	private TopActivityListener mTopListener;

	private BroadcastReceiver mReceiver;

	private MyProgressDialog mDialog;

	// 已执行医嘱列表
	private List<Order> execOrderList;

	// 医嘱类型，1：长期；0：临时
	private String codeOrderClassify;
	// 父医嘱号
	private String codeParentOrder;
	// 执行次数
	private String codeExecTimes;
	// 计划执行日期
	private String codeExecPlanDay;
	// 计划执行时间
	private String codeExecPlanTime;

	@ViewInject(R.id.tv_total)
	private TextView mTotal;
	@ViewInject(R.id.tv_executed)
	private TextView mExecuted;
	@ViewInject(R.id.tv_notexecute)
	private TextView mNotexe;

	private boolean isExec; // 是否执行

	private static final int RESULT = 0;
	private static final int UPDATELIST = 1;

	// 显示的医嘱
	protected List<Order> mOrderList;

	// 要插入的护理记录单id
	private String mNursingrecordId;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case RESULT:
				String result = (String) msg.obj;
				showResultDialog(result);
				break;
			case UPDATELIST:
				setData();
				break;
			}
		};
	};

	public void setTopListener(TopActivityListener topListener) {
		mTopListener = topListener;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication.getInstance().addActivity(this);
		initView();
		setPatientListView();
	}

	private void initView() {

		initSelectbar();

		mDialog = new MyProgressDialog(this);

		mLeftWindow = new LeftMenuPopupWindow(this);
		mPatientWindow = new PatientPopupWindow(this);

		mTitleBar.setLeftImage(R.drawable.top_list);
		mTitleBar.setRightImage(R.drawable.top_bh);
		mTitleBar.setOnLeftClickListener(new OnLeftClickListener() {

			@Override
			public void onLeftClick(View v) {
				mLeftWindow.showAtLocation(findViewById(R.id.ll_main),
						Gravity.BOTTOM, 0, 0);
			}
		});
		mTitleBar.setOnRightClickListener(new OnRightClickListener() {

			@Override
			public void onRightClick(View v) {
				mPatientWindow.showAtLocation(findViewById(R.id.ll_main),
						Gravity.BOTTOM, 0, 0);
			}
		});

		mListView = (ListView) findViewById(R.id.lv_order);

		// 设置最上层退出处理
		setTopListener(new TopExitImpl());
	}

	/**
	 * 初始化病人列表信息
	 */
	private void setPatientListView() {
		ListView mPatientListView = mPatientWindow.getListView();
		if (LoginInfo.PATIENT_ALL != null) {
			mPatientAdapter = new PatientAdapter(this, LoginInfo.PATIENT_ALL,
					R.layout.item_patient);
			mPatientListView.setAdapter(mPatientAdapter);
		}
		mPatientListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mPatientWindow.dismiss();
				mStatisticsLayout.setVisibility(View.INVISIBLE);
				mListView.setVisibility(View.INVISIBLE);
				changePatient(position);
			}
		});
	}

	/**
	 * 通过点击右上角的病患列表来改变病患的显示
	 */
	private void changePatient(int position) {
		LoginInfo.patientIndex = position;
		LoginInfo.patient = LoginInfo.PATIENT_ALL.get(LoginInfo.patientIndex);
		initSelectbar();
	}

	/**
	 * 初始化选择条内容
	 */
	public void initSelectbar() {
		SelectbarUtil selectbarUtil = new SelectbarUtil() {

			@Override
			public void data() {
				mStatisticsLayout.setVisibility(View.INVISIBLE);
				mListView.setVisibility(View.INVISIBLE);
			}
		};
		selectbarUtil.initView(this, this);
	}

	/**
	 * 在ListView中设置好数据
	 */
	private void setData() {
		if (mOrderList != null && mOrderList.size() > 0) {
			mListView.setVisibility(View.VISIBLE);
			mStatisticsLayout.setVisibility(View.VISIBLE);
			// 先清空所有医嘱
			StringBuilder sb = new StringBuilder();
			for (Order o : mOrderList) {
				sb.append(o.getORDERCONTENT() + o.getPERTIME() + "\n\n");
			}

			List<Order> box = new ArrayList<Order>();
			Order one = new Order();
			one.setORDERCONTENT(sb.toString());
			one.setFREQUENCY(mOrderList.get(0).getFREQUENCY());
			box.add(one);

			int total = mOrderList.size();

			mTotal.setText(String.valueOf(total));

			if (isExec) {
				one.setEXECTIME(execOrderList.get(0).getEXECTIME());
				mExecuted.setText(String.valueOf(total));
				mNotexe.setText(String.valueOf(0));
			} else {
				one.setEXECTIME("");
				mExecuted.setText(String.valueOf(0));
				mNotexe.setText(String.valueOf(total));
			}
			mAdapter = new DropAdapter(this, box, R.layout.item_drop);
			mListView.setAdapter(mAdapter);

			// if (isExec || !"".equals(box.get(0).getEXECTIME())) {
			if (isExec) {
				Order o = execOrderList.get(0);
				mListView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
					}
				});
				new MyAlertDialog(this, "此组医嘱已执行！\n执行时间：" + o.getEXECTIME())
						.show();
			} else {
				mListView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							final int position, long id) {
						TextView textView = new TextView(
								DropexecuteActivity.this);
						LayoutParams lp = new LayoutParams(
								LayoutParams.MATCH_PARENT,
								LayoutParams.MATCH_PARENT);
						lp.setMargins(50, 0, 50, 0);
						textView.setLayoutParams(lp);
						textView.setTextColor(getResources().getColor(
								R.color.bottom_tab_text));
						StringBuilder sb = new StringBuilder();
						Order order = mOrderList.get(position);
						sb.append("类型：" + order.getORDERTYPE() + "\n");
						// sb.append("父医嘱号：" + order.getPARENTORDER() + "\n");
						sb.append("计划执行时间：" + codeExecPlanTime + "\n");
						sb.append("开始时间：" + order.getSTARTTIME() + "\n");

						StringBuilder contentStr = new StringBuilder();
						int num = 1;
						for (Order o : mOrderList) {
							contentStr.append((num++) + ". "
									+ o.getORDERCONTENT() + "；");
						}
						sb.append("医嘱内容："
								+ contentStr.substring(0,
										contentStr.length() - 1) + "\n");
						// sb.append("剂量：" + order.getDOSETYPE() + "\n");
						sb.append("用药方式：" + order.getMEDICINEWAY() + "\n");
						sb.append("执行频率：" + order.getFREQUENCY() + "\n");
						sb.append("查对时间：" + order.getSUBTIME() + "\n");
						sb.append("开嘱医生：" + order.getDOCTOR() + "\n");
						textView.setText(sb.toString());
						MyDialog myDialo = new MyDialog(
								DropexecuteActivity.this, textView,
								new SureClickListener() {

									@Override
									public void onSureClick() {
										showSelectDialog(position);
									}

								});
						myDialo.show();
						myDialo.setTitle("静滴执行");
					}
				});
			}
		} else {
			new MyAlertDialog(this, "医嘱信息为空").show();
		}
	}

	/**
	 * 选择将要插入的护理记录单
	 * 
	 * @param position
	 */
	private void showSelectDialog(final int position) {
		final SelectDialog selectDialog = new SelectDialog(
				DropexecuteActivity.this);
		selectDialog.show();
		selectDialog.setItems(LoginInfo.nursingrecordList);
		selectDialog.setTitle("选择插入的护理记录单");
		selectDialog.setOnSureClickListener(new OnSureClickListener() {

			@Override
			public void onSureClick() {
				mNursingrecordId = selectDialog.getNursingrecordId();
				postDropOrder(mOrderList.get(position));
			}
		});
	}

	private List<Order> filterOrders(List<Order> list) {
		List<Order> newOrders = new ArrayList<Order>();
		// 长期
		if (codeOrderClassify.equals("1")) {
			for (Order order : list) {
				if (codeParentOrder.equals(order.getPARENTORDER())
						&& codeExecTimes.equals(order.getEXECTIMES())
						&& codeExecPlanTime.equals(order.getPLANEXECTIME())) {
					newOrders.add(order);
				}
			}
		}
		// 临时
		else {
			for (Order order : list) {
				if (codeParentOrder.equals(order.getPARENTORDER())
						&& codeExecTimes.equals(order.getEXECTIMES())) {
					newOrders.add(order);
				}
			}
		}
		return newOrders;
	}

	@Override
	public void onBackPressed() {
		mTopListener.onExit(this);
	}

	@Override
	protected void onResume() {
		registerScanReceiver();
		super.onResume();
	}

	@Override
	protected void onStop() {
		if (mReceiver != null) {
			unregisterScanReceiver();
		}
		super.onStop();
	}

	/**
	 * 解绑广播
	 */
	private void unregisterScanReceiver() {
		unregisterReceiver(mReceiver);
	}

	/**
	 * 注册广播
	 */
	private void registerScanReceiver() {
		mReceiver = new BroadcastReceiver() {
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

				// result = "00507471@2@1@95@2@2015-11-19 0:00:00@15:00";

				result = result.replace("/", "-");
				String[] strArray = result.split("@");
				String inHosId = strArray[0];

				// 先判断是否是该病患的医嘱
				if (!inHosId.equals(LoginInfo.patient.getPATIENTHOSID())
						&& strArray.length > 5) {
					new MyAlertDialog(context, "不是该病人医嘱，请核对后重新选！").show();
				}
				// 是否是切换病患
				else if (strArray.length == 3 && !result.contains("&")) {
					for (Patient info : LoginInfo.PATIENT_ALL) {
						if (strArray[0].equals(info.getPATIENTHOSID())
								&& strArray[1].equals(info.getPATIENTINTIMES())) {
							LoginInfo.patient = info;
							LoginInfo.patientIndex = LoginInfo.PATIENT_ALL
									.indexOf(info);
							initSelectbar();
							mListView.setVisibility(View.INVISIBLE);
							mStatisticsLayout.setVisibility(View.INVISIBLE);
							return;
						}
					}
					new MyAlertDialog(context, "病患信息不存在！").show();
				} else if (result.contains("&")) {
					new MyAlertDialog(context, "请扫描正确腕带！").show();
				}
				// 跳转到静滴执行界面
				else {
					readCode(result);
				}
			}
		};
		IntentFilter filter = new IntentFilter(SettingInfo.SCAN_FILTER);
		filter.addAction("urovo.rcv.message");
		registerReceiver(mReceiver, filter);
	}

	/**
	 * 解析扫描到的二维码
	 * 
	 * @param result
	 */
	protected void readCode(String result) {

		String[] strArray = result.split("@");
		codeOrderClassify = strArray[2];
		codeParentOrder = strArray[3];
		codeExecTimes = strArray[4];
		codeExecPlanDay = DateUtil.formatDate(strArray[5]);
		// 如果是临时医嘱
		if (codeOrderClassify.equals("0")) {
			codeExecPlanTime = "";
		} else {
			codeExecPlanTime = DateUtil.formatTime(strArray[6]
					.replace("\r", "").replace("\n", ""));
		}
		L.i("计划执行时间------" + codeExecPlanTime);

		// 读取已执行的医嘱
		loadExecOrder(LoginInfo.patient.getPATIENTHOSID(),
				LoginInfo.patient.getPATIENTINTIMES(), codeParentOrder,
				codeOrderClassify, Method.DROP);

	}

	/**
	 * 读取已执行的医嘱
	 */
	private void loadExecOrder(String patientHosId, String patientInTimes,
			String parentOrder, String orderClassify, String execType) {

		String url = SettingInfo.SERVICE
				+ Method.DOCTOR_ORDER_DETAILS_BY_PATIENTHOSID_PARENTORDER
				+ "?PatientHosId=" + patientHosId + "&PatientInTimes="
				+ patientInTimes + "&ParentOrder=" + parentOrder
				+ "&OrderClassify=" + orderClassify + "&ExecType=" + execType;
		HttpUtils.doGetAsyn(this, url, new HttpUtils.CallBack() {

			@Override
			public void onRequestComplete(String json) {
				L.i("已执行医嘱------" + json);
				execOrderList = GsonUtil.getOrderList(json);
				if (execOrderList != null && execOrderList.size() > 0) {
					if (execOrderList.size() == 1
							&& "".equals(execOrderList.get(0).getEXECTIME())) {
						isExec = false;
					} else {
						for (Order o : execOrderList) {
							// 判断如果列表中存在这个医嘱的话说明执行过了。
							// 长期 1 临时 0
							// 临时就不需要对比计划执行时间了
							if ("1".equals(codeOrderClassify)) {
								if (codeExecPlanDay.equals(DateUtil
										.formatDate(o.getPLANEXECDAY()))
										&& codeExecPlanTime.equals(DateUtil
												.formatTime(o.getPLANEXECTIME()))
										&& codeParentOrder.equals(o
												.getPARENTORDER())) {
									isExec = true;
									break;
								} else {
									isExec = false;
								}
							} else {
								if (codeExecPlanDay.equals(DateUtil
										.formatDate(o.getPLANEXECDAY()))
										&& codeParentOrder.equals(o
												.getPARENTORDER())) {
									isExec = true;
									break;
								} else {
									isExec = false;
								}
							}

						}
					}
				}
				// 读取所有静滴医嘱
				loadOrder(LoginInfo.patient.getPATIENTHOSID(),
						LoginInfo.patient.getPATIENTINTIMES(),
						codeOrderClassify, codeParentOrder);
			}
		}, "已执行医嘱");

	}

	/**
	 * 
	 * @param patientHosId
	 * @param patientInTimes
	 * @param orderType
	 *            长期还是临时
	 * @param parentOrder
	 */
	private void loadOrder(String patientHosId, String patientInTimes,
			String orderType, String parentOrder) {

		String url = SettingInfo.SERVICE
				+ Method.DOCTOR_ORDER_BY_PATIENTHOSID_PARENTORDER
				+ "?PatientHosId=" + LoginInfo.patient.getPATIENTHOSID()
				+ "&PatientInTimes=" + LoginInfo.patient.getPATIENTINTIMES()
				+ "&OrderType=" + orderType + "&ParentOrder=" + parentOrder;
		L.i("查询的医嘱------" + url);
		HttpUtils.doGetAsyn(this, url, new HttpUtils.CallBack() {

			@Override
			public void onRequestComplete(String json) {
				mOrderList = GsonUtil.getOrderList(json);
				Message msg = new Message();
				msg.what = UPDATELIST;
				mHandler.sendMessage(msg);
			}
		}, "查询的医嘱");

	}

	private void postDropOrder(Order order) {

		StringBuilder sb = new StringBuilder();
		try {
			sb.append("{\"ds\":[");
			// 护理记录单Id
			sb.append("{\"KeyProperty\":\"NursingID\",\"ValueProperty\":\""
					+ URLEncoder.encode(mNursingrecordId, "utf-8") + "\"},");
			// 住院号
			sb.append("{\"KeyProperty\":\"PatientHosId\",\"ValueProperty\":\""
					+ URLEncoder.encode(LoginInfo.patient.getPATIENTHOSID(),
							"utf-8") + "\"},");
			// 住院次数
			sb.append("{\"KeyProperty\":\"PatientInTimes\",\"ValueProperty\":\""
					+ URLEncoder.encode(LoginInfo.patient.getPATIENTINTIMES(),
							"utf-8") + "\"},");
			// 长期还是临时
			sb.append("{\"KeyProperty\":\"OrderClassify\",\"ValueProperty\":\""
					+ URLEncoder.encode(order.getORDERCLASSIFY(), "utf-8")
					+ "\"},");
			// 中西药
			sb.append("{\"KeyProperty\":\"ORDERTYPE\",\"ValueProperty\":\""
					+ URLEncoder.encode(order.getORDERTYPE(), "utf-8") + "\"},");
			// 药品
			sb.append("{\"KeyProperty\":\"ORDERCONTENT\",\"ValueProperty\":\""
					+ URLEncoder.encode(order.getORDERCONTENT(), "utf-8")
					+ "\"},");
			// 开始时间
			sb.append("{\"KeyProperty\":\"STARTTIME\",\"ValueProperty\":\""
					+ URLEncoder.encode(order.getSTARTTIME(), "utf-8") + "\"},");
			// 服药方式
			sb.append("{\"KeyProperty\":\"ExecType\",\"ValueProperty\":\""
					+ URLEncoder.encode(order.getEXECTYPE(), "utf-8") + "\"},");
			// 父医嘱号
			sb.append("{\"KeyProperty\":\"ParentOrder\",\"ValueProperty\":\""
					+ URLEncoder.encode(order.getPARENTORDER(), "utf-8")
					+ "\"},");
			// 子医嘱号
			sb.append("{\"KeyProperty\":\"CHILDORDER\",\"ValueProperty\":\""
					+ URLEncoder.encode(order.getCHILDORDER(), "utf-8")
					+ "\"},");
			// 频率嘱号
			sb.append("{\"KeyProperty\":\"FREQUENCY\",\"ValueProperty\":\""
					+ URLEncoder.encode(order.getFREQUENCY(), "utf-8") + "\"},");
			// 续静滴
			sb.append("{\"KeyProperty\":\"MEDICINEWAY\",\"ValueProperty\":\""
					+ URLEncoder.encode(order.getMEDICINEWAY(), "utf-8")
					+ "\"},");
			// 执行次数
			sb.append("{\"KeyProperty\":\"ExecTimes\",\"ValueProperty\":\""
					+ URLEncoder.encode(codeExecTimes, "utf-8") + "\"},");
			// 计划执行时间
			sb.append("{\"KeyProperty\":\"PlanExecTime\",\"ValueProperty\":\""
					+ URLEncoder.encode(codeExecPlanTime, "utf-8") + "\"},");
			// 计划执行日期
			if (codeExecPlanDay != null) {
				sb.append("{\"KeyProperty\":\"PLANEXECDAY\",\"ValueProperty\":\""
						+ URLEncoder.encode(codeExecPlanDay, "utf-8") + "\"},");
			} else {
				sb.append("{\"KeyProperty\":\"PLANEXECDAY\",\"ValueProperty\":\""
						+ URLEncoder.encode(DateUtil.getYMD(), "utf-8")
						+ "\"},");
			}
			// 执行人id
			sb.append("{\"KeyProperty\":\"ExecPersonId\",\"ValueProperty\":\""
					+ URLEncoder.encode(LoginInfo.USER_ID, "utf-8")
					+ "\"},");
			// 执行时间
			sb.append("{\"KeyProperty\":\"ExecTime\",\"ValueProperty\":\""
					+ URLEncoder.encode(DateUtil.getYMDHMS(), "utf-8") + "\"}");
			sb.append("]}");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		final String str = sb.toString();

		L.i("Post信息：" + str);

		String url = SettingInfo.SERVICE + Method.SET_DOCTOR_ORDER_DETAILS;

		L.i("执行医嘱url地址：" + url);
		try {
			ClientUtil.doPostAsyn(url, str, new CallBack() {

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
			// 提交成功后，更新UI界面，并且注销点击监听事件。
			for (Order o : mOrderList) {
				o.setEXECTIME(DateUtil.getYMDHMS());
			}
			StringBuilder sb = new StringBuilder();
			for (Order o : mOrderList) {
				sb.append(o.getORDERCONTENT() + o.getPERTIME() + "\n\n");
			}
			List<Order> box = new ArrayList<Order>();
			Order one = new Order();
			one.setORDERCONTENT(sb.substring(0, sb.length() - 4));
			one.setFREQUENCY(mOrderList.get(0).getFREQUENCY());
			box.add(one);
			mExecuted.setText(String.valueOf(mOrderList.size()));
			mNotexe.setText("0");
			mAdapter.setDatas(mOrderList);
			mAdapter.notifyDataSetChanged();
			mListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {

				}
			});
		} else {
			content = "提交失败";
		}
		MyAlertDialog alertDialog = new MyAlertDialog(this, content);
		alertDialog.show();
	}
}
