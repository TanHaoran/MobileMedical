package com.thr.mobilemedical.activity;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.thr.mobilemedical.R;
import com.thr.mobilemedical.adapter.NursinglistAdapter;
import com.thr.mobilemedical.bean.Nursingpaper;
import com.thr.mobilemedical.constant.LoginInfo;
import com.thr.mobilemedical.constant.Method;
import com.thr.mobilemedical.constant.SettingInfo;
import com.thr.mobilemedical.utils.DateUtil;
import com.thr.mobilemedical.utils.GsonUtil;
import com.thr.mobilemedical.utils.HttpGetUtil;
import com.thr.mobilemedical.utils.L;
import com.thr.mobilemedical.utils.ScanUtil;
import com.thr.mobilemedical.utils.TopActivityListener;
import com.thr.mobilemedical.utils.TopExitImpl;
import com.thr.mobilemedical.view.LeftMenuPopupWindow;
import com.thr.mobilemedical.view.MyProgressDialog;
import com.thr.mobilemedical.view.TitleBar;
import com.thr.mobilemedical.view.TitleBar.OnLeftClickListener;

/**
 * @description 护理清单（静滴清单）界面
 * @date 2015年9月25日 下午3:35:11
 * @version 1.0
 * @Company Buzzlysoft
 * @author Jerry Tan
 * @email thrforever@126.com
 * @remark
 */
@ContentView(R.layout.activity_nursinglist)
public class NursinglistActivity extends Activity {

	// 左侧菜单
	private LeftMenuPopupWindow mLeftWindow;

	// 标题栏
	@ViewInject(R.id.titlebar)
	private TitleBar mTitleBar;

	@ViewInject(R.id.tv_all)
	private TextView mTextAll;
	@ViewInject(R.id.tv_executed)
	private TextView mTextExecuted;
	@ViewInject(R.id.tv_notexecute)
	private TextView mTextNotexe;

	@ViewInject(R.id.lv_nursinglist)
	private ListView mListView;

	private TopActivityListener mTopListener;

	private List<Nursingpaper> mNursingList;

	private NursinglistAdapter mAdapter;

	private MyProgressDialog mDialog;

	@ViewInject(R.id.ll_today)
	private LinearLayout mTodayLayout;
	@ViewInject(R.id.ll_yesterday)
	private LinearLayout mYesterdayLayout;
	@ViewInject(R.id.v_today)
	private View mTodayLine;
	@ViewInject(R.id.v_yesterday)
	private View mYesterdayLine;

	@ViewInject(R.id.tv_today)
	private TextView mTextToday;
	@ViewInject(R.id.tv_yesterday)
	private TextView mTextYesterday;

	// 是否是今天
	private boolean mIsToday = true;

	public void setTopListener(TopActivityListener topListener) {
		mTopListener = topListener;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication.getInstance().addActivity(this);
		initView();
		loadNursinglistData(DateUtil.getYMD(), LoginInfo.OFFICE_ID);
	}

	private void initView() {
		mDialog = new MyProgressDialog(this);
		mLeftWindow = new LeftMenuPopupWindow(this);
		mTitleBar.setLeftImage(R.drawable.top_list);
		mTitleBar.setOnLeftClickListener(new OnLeftClickListener() {

			@Override
			public void onLeftClick(View v) {
				mLeftWindow.showAtLocation(findViewById(R.id.ll_main),
						Gravity.BOTTOM, 0, 0);
			}
		});

		// 设置最上层退出处理
		setTopListener(new TopExitImpl());
	}

	/**
	 * 读取护理清单数据
	 * 
	 * @param string
	 */
	private void loadNursinglistData(String date, String officeId) {
		String url = SettingInfo.SERVICE + Method.GET_HLQD_BY_OFFICEID
				+ "?PLANEXECDAY=" + date + "&officeid=" + officeId
				+ "&PatientHosId=&MedType=";
		HttpGetUtil httpGet = new HttpGetUtil(this) {
			@Override
			public void success(String json) {
				L.i("返回值------" + json);
				mNursingList = GsonUtil.getNursingpaperData(json);
				setNursingpaperData();
			}
		};
		httpGet.doGet(url, mDialog, this, "静滴清单数据");
	}

	/**
	 * 设置护理清单记录
	 */
	protected void setNursingpaperData() {
		int all = 0; // 全部清单
		int done = 0; // 已执行清单
		if (mNursingList != null) {
			for (Nursingpaper p : mNursingList) {
				all += Integer.parseInt(p.getZCOUNT());
				done += Integer.parseInt(p.getYCOUNT());
			}
		}
		mTextAll.setText(String.valueOf(all));
		mTextExecuted.setText(String.valueOf(done));
		mTextNotexe.setText(String.valueOf(all - done));

		if (mNursingList != null && mNursingList.size() > 0) {
			mAdapter = new NursinglistAdapter(this, mNursingList,
					R.layout.item_nursingpaper);
			mListView.setAdapter(mAdapter);
			mListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					LoginInfo.patientIndex = position;
					LoginInfo.patient = LoginInfo.PATIENT_ALL.get(position);
					Intent intent = new Intent(NursinglistActivity.this,
							DropdetailActivity.class);
					intent.putExtra("istoday", mIsToday);
					startActivity(intent);
				}
			});
		}
	}

	@OnClick(R.id.ll_today)
	public void onToday(View v) {
		mIsToday = true;
		mTodayLine.setVisibility(View.VISIBLE);
		mYesterdayLine.setVisibility(View.INVISIBLE);
		mTextToday.setTextColor(getResources().getColor(
				R.color.login_btn_bg_normal));
		mTextYesterday.setTextColor(getResources().getColor(
				R.color.bottom_tab_text));
		loadNursinglistData(DateUtil.getYMD(), LoginInfo.OFFICE_ID);
	}

	@OnClick(R.id.ll_yesterday)
	public void onYesterday(View v) {
		mIsToday = false;
		mTodayLine.setVisibility(View.INVISIBLE);
		mYesterdayLine.setVisibility(View.VISIBLE);
		mTextYesterday.setTextColor(getResources().getColor(
				R.color.login_btn_bg_normal));
		mTextToday.setTextColor(getResources()
				.getColor(R.color.bottom_tab_text));
		loadNursinglistData(DateUtil.getYesterdayYMD(), LoginInfo.OFFICE_ID);
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
