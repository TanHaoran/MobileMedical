package com.thr.mobilemedical.activity;

import java.util.ArrayList;
import java.util.List;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.thr.mobilemedical.R;
import com.thr.mobilemedical.adapter.PatientAdapter;
import com.thr.mobilemedical.constant.LoginInfo;
import com.thr.mobilemedical.fragment.DroppatrolFragment;
import com.thr.mobilemedical.fragment.NursingpatrolFragment;
import com.thr.mobilemedical.utils.ScanUtil;
import com.thr.mobilemedical.utils.TopActivityListener;
import com.thr.mobilemedical.utils.TopExitImpl;
import com.thr.mobilemedical.view.LeftMenuPopupWindow;
import com.thr.mobilemedical.view.PatientPopupWindow;
import com.thr.mobilemedical.view.TitleBar;
import com.thr.mobilemedical.view.TitleBar.OnLeftClickListener;
import com.thr.mobilemedical.view.TitleBar.OnRightClickListener;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @description 护理巡视界面
 * @date 2015年9月25日 下午3:35:11
 * @version 1.0
 * @Company Buzzlysoft
 * @author Jerry Tan
 * @email thrforever@126.com
 * @remark
 */
@ContentView(R.layout.activity_nursingpatrol)
public class NursingpatrolActivity extends Activity {

	// 左侧菜单
	private LeftMenuPopupWindow mLeftWindow;
	// 病患菜单
	private PatientPopupWindow mPatientWindow;

	// 标题栏
	@ViewInject(R.id.titlebar)
	private TitleBar mTitleBar;

	@ViewInject(R.id.ll_tab_nursingpatrol)
	private LinearLayout mTabNursing;
	@ViewInject(R.id.ll_tab_droppatrol)
	private LinearLayout mTabDrop;

	@ViewInject(R.id.ib_tab_nursingpatrol)
	private ImageButton mImgNursing;
	@ViewInject(R.id.ib_tab_droppatrol)
	private ImageButton mImgDrop;

	@ViewInject(R.id.tv_nursingpatrol)
	private TextView mTextNursing;
	@ViewInject(R.id.tv_droppatrol)
	private TextView mTextDrop;

	private ListView mListView; // 病患列表
	private PatientAdapter mAdapter;

	// 2个页面
	private NursingpatrolFragment mNursingFragment;
	private DroppatrolFragment mDropFragment;

	private List<Fragment> fragmentList = new ArrayList<Fragment>();

	private TopActivityListener mTopListener;

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

		mNursingFragment = new NursingpatrolFragment();
		mDropFragment = new DroppatrolFragment();

		// 将所有的fragment添加到集合中方便后面操作
		fragmentList.add(mNursingFragment);
		fragmentList.add(mDropFragment);

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

		// 最开始显示在基础的状态
		showFragment(fragmentList.get(0));
		// 设置最上层退出处理
		setTopListener(new TopExitImpl());
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
			}
		});
	}

	/**
	 * 通过点击右上角的病患列表来改变病患的显示
	 */
	private void changePatient(int position) {
		LoginInfo.patientIndex = position;
		LoginInfo.patient = LoginInfo.PATIENT_ALL.get(LoginInfo.patientIndex);
		if (mNursingFragment.isVisible()) {
			mNursingFragment.initSelectbar();
		} else {
			mDropFragment.initSelectbar();
		}
	}

	/**
	 * 隐藏所有的Fragment
	 * 
	 * @param transaction
	 */
	private void hideFragment(FragmentTransaction transaction) {
		if (mNursingFragment != null) {
			transaction.hide(mNursingFragment);
		}
		if (mDropFragment != null) {
			transaction.hide(mDropFragment);
		}
	}

	/**
	 * 先隐藏所有Fragment，然后显示当前所点击的Fragment
	 * 
	 * @param fragment
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
		mImgNursing.setImageResource(R.drawable.bottom_hlxs_hlxs);
		mImgDrop.setImageResource(R.drawable.bottom_hlxs_jdxs);
		// 重置文字
		mTextNursing.setTextColor(getResources().getColor(
				R.color.bottom_tab_text));
		mTextDrop
				.setTextColor(getResources().getColor(R.color.bottom_tab_text));
	}

	/**
	 * 跳转至护理巡视界面
	 * 
	 * @param v
	 */
	@OnClick(R.id.ll_tab_nursingpatrol)
	public void costClick(View v) {
		if (showFragment(mNursingFragment)) {
			if (mNursingFragment.isAdded()) {
				mNursingFragment.initSelectbar();
			}
			resetBtnState();
			mImgNursing.setImageResource(R.drawable.bottom_hlxs_hlxs_n);
			mTextNursing.setTextColor(getResources().getColor(
					R.color.login_btn_bg_normal));
		}
	}

	/**
	 * 跳转至血氧界面
	 * 
	 * @param v
	 */
	@OnClick(R.id.ll_tab_droppatrol)
	public void eduClick(View v) {
		if (showFragment(mDropFragment)) {
			if (mDropFragment.isAdded()) {
				mDropFragment.initSelectbar();
			}
			resetBtnState();
			mImgDrop.setImageResource(R.drawable.bottom_hlxs_jdxs_n);
			mTextDrop.setTextColor(getResources().getColor(
					R.color.login_btn_bg_normal));
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
