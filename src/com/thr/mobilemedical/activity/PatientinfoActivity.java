package com.thr.mobilemedical.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.thr.mobilemedical.R;
import com.thr.mobilemedical.adapter.PatientAdapter;
import com.thr.mobilemedical.constant.LoginInfo;
import com.thr.mobilemedical.fragment.BaseFragment;
import com.thr.mobilemedical.fragment.CostFragment;
import com.thr.mobilemedical.fragment.ExamFragment;
import com.thr.mobilemedical.fragment.OrderFragment;
import com.thr.mobilemedical.utils.ScanUtil;
import com.thr.mobilemedical.utils.TopActivityListener;
import com.thr.mobilemedical.utils.TopExitImpl;
import com.thr.mobilemedical.view.LeftMenuPopupWindow;
import com.thr.mobilemedical.view.PatientBarView;
import com.thr.mobilemedical.view.PatientPopupWindow;
import com.thr.mobilemedical.view.TitleBar;
import com.thr.mobilemedical.view.TitleBar.OnLeftClickListener;
import com.thr.mobilemedical.view.TitleBar.OnRightClickListener;

/**
 * @description PatientinfoActivity.java
 * @date 2015年9月11日 上午10:06:17
 * @version 1.0
 * @Company Buzzlysoft
 * @author Jerry Tan
 * @email thrforever@126.com
 * @remark
 */

@ContentView(R.layout.activity_patientinfo)
public class PatientinfoActivity extends Activity {

	// 标题栏
	@ViewInject(R.id.titlebar)
	private TitleBar mTitleBar;

	// 左侧菜单
	private LeftMenuPopupWindow mLeftWindow;
	// 病患菜单
	private PatientPopupWindow mPatientWindow;

	@ViewInject(R.id.patient_bar)
	private PatientBarView mPatientBar;

	@ViewInject(R.id.ll_tab_base)
	private LinearLayout mTabBase;
	@ViewInject(R.id.ll_tab_order)
	private LinearLayout mTabOrder;
	@ViewInject(R.id.ll_tab_exam)
	private LinearLayout mTabExam;
	@ViewInject(R.id.ll_tab_cost)
	private LinearLayout mTabCost;

	@ViewInject(R.id.ib_tab_base)
	private ImageButton mImgBase;
	@ViewInject(R.id.ib_tab_order)
	private ImageButton mImgOrder;
	@ViewInject(R.id.ib_tab_exam)
	private ImageButton mImgExam;
	@ViewInject(R.id.ib_tab_cost)
	private ImageButton mImgCost;

	@ViewInject(R.id.tv_base)
	private TextView mTextBase;
	@ViewInject(R.id.tv_order)
	private TextView mTextOrder;
	@ViewInject(R.id.tv_exam)
	private TextView mTextExam;
	@ViewInject(R.id.tv_cost)
	private TextView mTextCost;

	// 4个页面
	private BaseFragment mBaseFragment;
	private OrderFragment mOrderFragment;
	private ExamFragment mExamFragment;
	private CostFragment mCostFragment;

	private ListView mListView; // 病患列表
	private PatientAdapter mAdapter;

	private List<Fragment> fragmentList = new ArrayList<Fragment>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication.getInstance().addActivity(this);
		initView();
		setPatientListView();
	}

	private void initView() {
		mBaseFragment = new BaseFragment();
		mOrderFragment = new OrderFragment();
		mExamFragment = new ExamFragment();
		mCostFragment = new CostFragment();

		// 将所有的fragment添加到集合中方便后面操作
		fragmentList.add(mBaseFragment);
		fragmentList.add(mOrderFragment);
		fragmentList.add(mExamFragment);
		fragmentList.add(mCostFragment);

		mTitleBar.setLeftImage(R.drawable.top_list);
		mTitleBar.setRightImage(R.drawable.top_bh);

		mLeftWindow = new LeftMenuPopupWindow(this);
		mPatientWindow = new PatientPopupWindow(this);

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

		// 最开始显示在基础的状态
		showFragment(fragmentList.get(0));

		// 设置最上层退出处理
		setTopListener(new TopExitImpl());

		mPatientBar.setBarData();

		mPatientBar.setOnPatientChangeListener(new PatientBarView.OnPatientChangeListener() {
			@Override
			public void onPatientChange(int position) {
				changePatient(position);
			}
		});

	}

	/**
	 * 初始化病人列表信息
	 */
	private void setPatientListView() {
		mListView = mPatientWindow.getListView();
		if (LoginInfo.PATIENT_ALL != null) {
			mAdapter = new PatientAdapter(this, LoginInfo.PATIENT_ALL,
					R.layout.item_patient);
			mListView.setAdapter(mAdapter);
		}
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mPatientWindow.dismiss();
				changePatient(position);
				if (mOrderFragment.isVisible()) {
					mOrderFragment.changeToLong();
				}
			}
		});
	}

	/**
	 * 通过点击右上角的病患列表来改变病患的显示
	 */
	private void changePatient(int position) {
		LoginInfo.patientIndex = position;
		LoginInfo.patient = LoginInfo.PATIENT_ALL.get(LoginInfo.patientIndex);
		if (mBaseFragment.isVisible()) {
			mBaseFragment.initData();
		} else if (mOrderFragment.isVisible()) {
//			mOrderFragment.loadLongOrder(LoginInfo.patient.getPATIENTHOSID(),
//					LoginInfo.patient.getPATIENTINTIMES(), "1");
		} else if (mExamFragment.isVisible()) {
//			mExamFragment.loadExamList(LoginInfo.patient.getPATIENTHOSID());
		} else if (mCostFragment.isVisible()) {
			mCostFragment.initData();
		}
	}

	/**
	 * 隐藏所有的Fragment
	 * 
	 * @param transaction
	 */
	private void hideFragment(FragmentTransaction transaction) {
		if (mBaseFragment != null) {
			transaction.hide(mBaseFragment);
		}
		if (mOrderFragment != null) {
			transaction.hide(mOrderFragment);
		}
		if (mExamFragment != null) {
			transaction.hide(mExamFragment);
		}
		if (mCostFragment != null) {
			transaction.hide(mCostFragment);
		}
	}

	/**
	 * 先隐藏所有Fragment，然后显示当前所点击的Fragment
	 * 
	 * @param fragment
	 * @return 是执行功切换显示了
	 */
	private boolean showFragment(Fragment fragment) {
		if (!fragment.isVisible()) {
			FragmentManager manager = getFragmentManager();
			FragmentTransaction transaction = manager.beginTransaction();
			hideFragment(transaction);
			if (!fragment.isAdded()) {
				transaction.add(R.id.fl_content, fragment);
			}
			transaction.show(fragment);
			transaction.commit();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 重置所有按钮文字状态
	 */
	private void resetBtnState() {
		// 重置图片
		mImgBase.setImageResource(R.drawable.bottom_bhxx_jc);
		mImgOrder.setImageResource(R.drawable.bottom_bhxx_yz);
		mImgExam.setImageResource(R.drawable.bottom_bhxx_jy);
		mImgCost.setImageResource(R.drawable.bottom_bhxx_fy);
		// 重置文字
		mTextBase
				.setTextColor(getResources().getColor(R.color.bottom_tab_text));
		mTextOrder.setTextColor(getResources()
				.getColor(R.color.bottom_tab_text));
		mTextExam
				.setTextColor(getResources().getColor(R.color.bottom_tab_text));
		mTextCost
				.setTextColor(getResources().getColor(R.color.bottom_tab_text));
	}

	/**
	 * 跳转至基础界面
	 * 
	 * @param v
	 */
	@OnClick(R.id.ll_tab_base)
	public void baseClick(View v) {
		if (showFragment(mBaseFragment)) {
			if (mBaseFragment.isAdded()) {
				mBaseFragment.initData();
//				mBaseFragment.initSelectbar();
			}
			resetBtnState();
			mImgBase.setImageResource(R.drawable.bottom_bhxx_jc_n);
			mTextBase.setTextColor(getResources().getColor(
					R.color.login_btn_bg_normal));
		}
	}

	/**
	 * 跳转至医嘱界面
	 * 
	 * @param v
	 */
	@OnClick(R.id.ll_tab_order)
	public void orderClick(View v) {
		if (showFragment(mOrderFragment)) {
			// 先判断是否加载过此fragment
			if (mOrderFragment.isAdded()) {
//				mOrderFragment.initSelectbar();
			}
			resetBtnState();
			mImgOrder.setImageResource(R.drawable.bottom_bhxx_yz_n);
			mTextOrder.setTextColor(getResources().getColor(
					R.color.login_btn_bg_normal));
		}
	}

	/**
	 * 跳转至检验界面
	 * 
	 * @param v
	 */
	@OnClick(R.id.ll_tab_exam)
	public void examClick(View v) {
		if (showFragment(mExamFragment)) {
			// 先判断是否加载过此fragment
			if (mExamFragment.isAdded()) {
//				mExamFragment.loadExamList(LoginInfo.patient.getPATIENTHOSID());
			}
			resetBtnState();
			mImgExam.setImageResource(R.drawable.bottom_bhxx_jy_n);
			mTextExam.setTextColor(getResources().getColor(
					R.color.login_btn_bg_normal));

		}
	}

	/**
	 * 跳转至花费界面
	 * 
	 * @param v
	 */
	@OnClick(R.id.ll_tab_cost)
	public void costClick(View v) {
		if (showFragment(mCostFragment)) {
			if (mCostFragment.isAdded()) {
				mCostFragment.initData();
			}
			resetBtnState();
			mImgCost.setImageResource(R.drawable.bottom_bhxx_fy_n);
			mTextCost.setTextColor(getResources().getColor(
					R.color.login_btn_bg_normal));
		}
	}

	private TopActivityListener mTopListener;

	public void setTopListener(TopActivityListener topListener) {
		mTopListener = topListener;
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
