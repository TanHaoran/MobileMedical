package com.thr.mobilemedical.activity;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.thr.mobilemedical.R;
import com.thr.mobilemedical.bean.ExamItem;
import com.thr.mobilemedical.constant.Method;
import com.thr.mobilemedical.constant.SettingInfo;
import com.thr.mobilemedical.utils.CommonAdapter;
import com.thr.mobilemedical.utils.GsonUtil;
import com.thr.mobilemedical.utils.HttpGetUtil;
import com.thr.mobilemedical.utils.L;
import com.thr.mobilemedical.utils.ViewHolder;
import com.thr.mobilemedical.view.MyProgressDialog;
import com.thr.mobilemedical.view.TitleBar;
import com.thr.mobilemedical.view.TitleBar.OnLeftClickListener;

/**
 * @description ExamDetailActivity.java
 * @date 2015年10月19日 上午11:13:57
 * @version 1.0
 * @Company Buzzlysoft
 * @author Jerry Tan
 * @email thrforever@126.com
 * @remark
 */
@ContentView(R.layout.activity_examdetail)
public class ExamDetailActivity extends Activity {

	@ViewInject(R.id.titlebar)
	private TitleBar mTitlebar;

	@ViewInject(R.id.lv_examdetaillist)
	private ListView mListView;

	private MyProgressDialog mDialog;

	private String mExamId;

	private List<ExamItem> mExamItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication.getInstance().addActivity(this);
		initView();
		loadExamDetail(mExamId);
	}

	private void initView() {
		mDialog = new MyProgressDialog(this);
		// 获取从上个界面传过来的标题和检验单号
		String title = getIntent().getStringExtra("title");
		mExamId = getIntent().getStringExtra("examid");

		mTitlebar.setTitle(title);
		mTitlebar.setLeftImage(R.drawable.top_fh);
		// 点击左侧返回按钮
		mTitlebar.setOnLeftClickListener(new OnLeftClickListener() {

			@Override
			public void onLeftClick(View v) {
				finish();
			}
		});
	}

	/**
	 * 读取检验报告单条记录
	 * 
	 * @param patientHosId
	 */
	private void loadExamDetail(String examId) {
		String url = SettingInfo.SERVICE + Method.GET_LIS_DETAILED + "?ID="
				+ examId;
		HttpGetUtil httpGet = new HttpGetUtil(this) {

			@Override
			public void success(String json) {
				L.i("读取检验报告单条------" + json);
				mExamItems = GsonUtil.getExamItemList(json);
				setData();
			}
		};
		httpGet.doGet(url, mDialog, this, "检验报告单条");
	}

	protected void setData() {
		if (mExamItems != null && mExamItems.size() > 0) {
			mListView.setAdapter(new CommonAdapter<ExamItem>(this, mExamItems,
					R.layout.item_examdetail) {

				@Override
				public void convert(ViewHolder helper, ExamItem item) {
					TextView tItem = helper.getView(R.id.tv_item);
					TextView tResult = helper.getView(R.id.tv_result);
					TextView tState = helper.getView(R.id.tv_state);
					TextView tRange = helper.getView(R.id.tv_range);
					TextView tUnit = helper.getView(R.id.tv_unit);

					tItem.setText(item.getName());
					tResult.setText(item.getResult());
					tState.setText(item.getHint());
					tRange.setText(item.getRange());
					tUnit.setText(item.getUnit());
				}
			});
		}
	}

}
