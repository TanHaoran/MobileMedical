package com.thr.mobilemedical.activity;

import android.app.Activity;
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
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.thr.mobilemedical.R;
import com.thr.mobilemedical.adapter.OrderAdapter;
import com.thr.mobilemedical.adapter.PatientAdapter;
import com.thr.mobilemedical.bean.Order;
import com.thr.mobilemedical.constant.LoginInfo;
import com.thr.mobilemedical.constant.Method;
import com.thr.mobilemedical.constant.SettingInfo;
import com.thr.mobilemedical.utils.ClientUtil;
import com.thr.mobilemedical.utils.ClientUtil.CallBack;
import com.thr.mobilemedical.utils.DateUtil;
import com.thr.mobilemedical.utils.GsonUtil;
import com.thr.mobilemedical.utils.HttpGetUtil;
import com.thr.mobilemedical.utils.L;
import com.thr.mobilemedical.utils.ScanUtil;
import com.thr.mobilemedical.utils.SelectbarUtil;
import com.thr.mobilemedical.utils.TopActivityListener;
import com.thr.mobilemedical.utils.TopExitImpl;
import com.thr.mobilemedical.view.LeftMenuPopupWindow;
import com.thr.mobilemedical.view.MyAlertDialog;
import com.thr.mobilemedical.view.MyDialog;
import com.thr.mobilemedical.view.MyDialog.SureClickListener;
import com.thr.mobilemedical.view.MyProgressDialog;
import com.thr.mobilemedical.view.PatientPopupWindow;
import com.thr.mobilemedical.view.TitleBar;
import com.thr.mobilemedical.view.TitleBar.OnLeftClickListener;
import com.thr.mobilemedical.view.TitleBar.OnRightClickListener;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @description 口服界面
 * @date 2015年11月23日 下午4:03:13
 * @version 1.0
 * @Company Buzzlysoft
 * @author Jerry Tan
 * @email thrforever@126.com
 * @remark
 */
@ContentView(R.layout.activity_order)
public class OrallyActivity extends Activity {

	// 左侧菜单
	private LeftMenuPopupWindow mLeftWindow;
	// 病患菜单
	private PatientPopupWindow mPatientWindow;

	// 标题栏
	@ViewInject(R.id.titlebar)
	private TitleBar mTitleBar;

	@ViewInject(R.id.ll_longorder)
	private LinearLayout mLong;
	@ViewInject(R.id.ll_temporder)
	private LinearLayout mTemp;
	@ViewInject(R.id.v_longline)
	private View mLongline;
	@ViewInject(R.id.v_templine)
	private View mTempline;

	@ViewInject(R.id.tv_longorder)
	private TextView mTextLong;
	@ViewInject(R.id.tv_temporder)
	private TextView mTextTemp;

	@ViewInject(R.id.tv_total)
	private TextView mTotal;
	@ViewInject(R.id.tv_executed)
	private TextView mExecute;
	@ViewInject(R.id.tv_notexecute)
	private TextView mNotExe;

	@ViewInject(R.id.lv_order)
	private ListView mListView;
	private List<Order> mOrderList;
	private OrderAdapter mAdapter;

	private MyProgressDialog mDialog;

	private TopActivityListener mTopListener;

	private PatientAdapter mPatientAdapter;
	private List<Order> showOrders;

	private static final int RESULT = 0;
	private static final int UPDATELIST = 1;

	private int state = 1;
	private static final int LONG = 1;
	private static final int TEMP = 0;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case RESULT:
				String result = (String) msg.obj;
				showResultDialog(result);
				break;
			}
		};
	};

	/**
	 * 对提交结果进行提醒
	 */
	private void showResultDialog(String result) {
		String content = null;
		if ("\"1\"".equals(result)) {
			content = "提交成功";
			if (state == LONG) {
				loadHandlerOrder(LoginInfo.patient.getPATIENTHOSID(),
						LoginInfo.patient.getPATIENTINTIMES(),
						Method.ORDER_LONG, Method.ORALLY);
			} else {
				loadHandlerOrder(LoginInfo.patient.getPATIENTHOSID(),
						LoginInfo.patient.getPATIENTINTIMES(),
						Method.ORDER_TEMP, Method.ORALLY);
			}
		} else {
			content = "提交失败";
		}
		MyAlertDialog alertDialog = new MyAlertDialog(this, content);
		alertDialog.show();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication.getInstance().addActivity(this);
		initView();
		setPatientListView();
		loadHandlerOrder(LoginInfo.patient.getPATIENTHOSID(),
				LoginInfo.patient.getPATIENTINTIMES(), Method.ORDER_LONG,
				Method.ORALLY);
	}

	public void setTopListener(TopActivityListener topListener) {
		mTopListener = topListener;
	}

	private void loadHandlerOrder(String patientInHosId, String patientInTimes,
			String orderClassify, String execType) {
		String url = SettingInfo.SERVICE
				+ Method.DOCTOR_ORDER_DETAILS_BY_PATIENTHOSID
				+ "?PatientHosId=" + patientInHosId + "&PatientInTimes="
				+ patientInTimes + "&OrderClassify=" + orderClassify
				+ "&ExecType=" + execType;
		HttpGetUtil httpGet = new HttpGetUtil(this) {

			@Override
			public void success(String json) {
				L.i("口服长期医嘱------" + json);
				mOrderList = GsonUtil.getOrderList(json);
				initOrderList(mOrderList);
			}
		};
		httpGet.doGet(url, mDialog, this, "口服长期医嘱");
	}

	private void initView() {

		initSelectbar();
		state = LONG;
		mDialog = new MyProgressDialog(this);

		mLeftWindow = new LeftMenuPopupWindow(this);
		mPatientWindow = new PatientPopupWindow(this);

		mTitleBar.setTitle("口服执行");
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

		mDialog = new MyProgressDialog(this);

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
				changePatient(position);
				changeToLong(mLong);
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
				changeToLong(mLong);
			}
		};
		selectbarUtil.initView(this, this);
	}

	/**
	 * 切换到长期医嘱
	 */
	@OnClick(R.id.ll_longorder)
	public void changeToLong(View v) {
		state = LONG;
		mLongline.setVisibility(View.VISIBLE);
		mTempline.setVisibility(View.INVISIBLE);
		mTextLong.setTextColor(getResources().getColor(
				R.color.login_btn_bg_normal));
		mTextTemp
				.setTextColor(getResources().getColor(R.color.bottom_tab_text));
		loadHandlerOrder(LoginInfo.patient.getPATIENTHOSID(),
				LoginInfo.patient.getPATIENTINTIMES(), Method.ORDER_LONG,
				Method.ORALLY);
	}

	/**
	 * 切换到临时医嘱
	 */
	@OnClick(R.id.ll_temporder)
	public void chagneToTemp(View v) {
		state = TEMP;
		mLongline.setVisibility(View.INVISIBLE);
		mTempline.setVisibility(View.VISIBLE);
		mTextTemp.setTextColor(getResources().getColor(
				R.color.login_btn_bg_normal));
		mTextLong
				.setTextColor(getResources().getColor(R.color.bottom_tab_text));
		loadHandlerOrder(LoginInfo.patient.getPATIENTHOSID(),
				LoginInfo.patient.getPATIENTINTIMES(), Method.ORDER_TEMP,
				Method.ORALLY);
	}

	private List<Order> getTogether(List<Order> orderList) {
		showOrders = new ArrayList<Order>();
		for (int i = 0; i < orderList.size(); i++) {
			Order o = orderList.get(i);
			if (i == 0) {
				showOrders.add(o);
			} else {
				// 如果两个父医嘱号是一致并且计划执行时间一致的就显示为一条医嘱
				if (orderList.get(i - 1).getPARENTORDER()
						.equals(orderList.get(i).getPARENTORDER())
						&& orderList.get(i - 1).getEXECTIMES()
								.equals(orderList.get(i).getEXECTIMES())) {
					String content = showOrders.get(showOrders.size() - 1)
							.getORDERCONTENT()
							+ showOrders.get(showOrders.size() - 1)
									.getPERTIME();
					if (!orderList.get(i - 1).getPARENTORDER()
							.equals(orderList.get(i).getPARENTORDER())) {
						showOrders.get(showOrders.size() - 1).setORDERCONTENT(
								content + "\n" + o.getORDERCONTENT()
										+ o.getPERTIME());
					}
					continue;
				}
				showOrders.add(o);
			}
		}
		return showOrders;
	}

	/**
	 * 初始化医嘱数据
	 * 
	 * @param orderList
	 */
	private void initOrderList(List<Order> orderList) {
		if (orderList != null && orderList.size() > 0
				&& !orderList.get(0).getPATIENTHOSID().trim().equals("")) {
			showOrders = getTogether(orderList);
			mListView.setVisibility(View.VISIBLE);
			mAdapter = new OrderAdapter(this, showOrders, R.layout.item_order);
			mListView.setAdapter(mAdapter);
			mTotal.setText(String.valueOf(showOrders.size()));
			int execute = 0;
			for (Order o : showOrders) {
				if (o.getEXECTIME() != null && !"".equals(o.getEXECTIME())) {
					execute++;
				}
			}
			mExecute.setText(String.valueOf(execute));
			mNotExe.setText(String.valueOf(showOrders.size() - execute));
			mListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						final int position, long id) {
					if (showOrders.get(position).getEXECTIME() == null
							|| "".equals(showOrders.get(position).getEXECTIME())) {
						TextView textView = new TextView(OrallyActivity.this);
						LayoutParams lp = new LayoutParams(
								LayoutParams.MATCH_PARENT,
								LayoutParams.MATCH_PARENT);
						lp.setMargins(50, 0, 50, 0);
						textView.setLayoutParams(lp);
						textView.setTextColor(getResources().getColor(
								R.color.bottom_tab_text));
						StringBuilder sb = new StringBuilder();
						Order order = showOrders.get(position);
						sb.append("类型：" + order.getORDERCLASSIFY() + "\n");
						sb.append("父医嘱号：" + order.getPARENTORDER() + "\n");
						sb.append("计划执行时间：" + order.getPLANEXECTIME() + "\n");
						sb.append("开始时间：" + order.getSTARTTIME() + "\n");
						sb.append("医嘱内容：" + order.getORDERCONTENT() + "\n");
						// sb.append("剂量：" + order.getDOSETYPE() + "\n");
						sb.append("用药方式：" + order.getMEDICINEWAY() + "\n");
						sb.append("执行频率：" + order.getFREQUENCY() + "\n");
						sb.append("查对时间：" + order.getSUBTIME() + "\n");
						sb.append("开嘱医生：" + order.getDOCTOR() + "\n");
						textView.setText(sb.toString());
						MyDialog myDialo = new MyDialog(OrallyActivity.this,
								textView, new SureClickListener() {

									@Override
									public void onSureClick() {
										postDropOrder(showOrders.get(position));
									}
								});
						myDialo.show();
						myDialo.setTitle("口服执行");
					}
				}
			});
		} else {
			mListView.setVisibility(View.INVISIBLE);
			mTotal.setText("0");
			mExecute.setText("0");
			mNotExe.setText("0");
		}
	}

	private void postDropOrder(Order order) {

		StringBuilder sb = new StringBuilder();
		try {
			sb.append("{\"ds\":[");
			// 护理记录单Id
			sb.append("{\"KeyProperty\":\"NursingID\",\"ValueProperty\":\""
					+ URLEncoder.encode("", "utf-8") + "\"},");
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
					+ URLEncoder.encode(order.getEXECTIMES(), "utf-8") + "\"},");
			// 计划执行时间
			sb.append("{\"KeyProperty\":\"PlanExecTime\",\"ValueProperty\":\""
					+ URLEncoder.encode(order.getPLANEXECTIME(), "utf-8")
					+ "\"},");
			// 计划执行日期
			sb.append("{\"KeyProperty\":\"PLANEXECDAY\",\"ValueProperty\":\""
					+ URLEncoder.encode(DateUtil.getYMD(), "utf-8") + "\"},");
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

		L.i("口服医嘱Post信息：" + str);

		String url = SettingInfo.SERVICE + Method.SET_DOCTOR_ORDER_DETAILS;

		L.i("口服医嘱url地址：" + url);
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

	@Override
	public void onBackPressed() {
		mTopListener.onExit(this);
	}

	@Override
	protected void onResume() {
		ScanUtil.registerScanReceiver(this);
		super.onResume();
	}

}
