package com.thr.mobilemedical.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.thr.mobilemedical.bean.Nursingrecord;
import com.thr.mobilemedical.R;

/**
 * @description 自定义Dialog选择框
 * @date 2015年9月30日 上午9:40:13
 * @version 1.0
 * @Company Buzzlysoft
 * @author Jerry Tan
 * @email thrforever@126.com
 * @remark
 */
public class SelectDialog extends Dialog {

	private Context mContext;

	private WheelView mWheel;
	private List<Nursingrecord> mNursingrecords;

	private TextView mTextTitle;
	private Button mSure;
	private Button mCancle;

	private int selectIndex = 0;

	private OnSureClickListener mListener;

	public void setOnSureClickListener(OnSureClickListener listener) {
		mListener = listener;
	}

	public interface OnSureClickListener {
		void onSureClick();
	}

	public SelectDialog(Context context) {
		super(context);
		mContext = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_office);

		mTextTitle = (TextView) findViewById(R.id.tv_title);

		mSure = (Button) findViewById(R.id.btn_sure);
		mCancle = (Button) findViewById(R.id.btn_cancel);
		mWheel = (WheelView) findViewById(R.id.wv_office);

		mWheel.setOffset(2);
		mWheel.setSeletion(0);

		mSure.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mListener.onSureClick();
				dismiss();
			}
		});
		mCancle.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});

	}

	/**
	 * 获取选择id
	 * 
	 * @return
	 */
	public String getNursingrecordId() {
		selectIndex = mWheel.getSeletedIndex();
		if (selectIndex == mNursingrecords.size()) {
			return "";
		} else {
			return mNursingrecords.get(selectIndex).getNURSINGID();
		}
	}

	/**
	 * 设置科室数据
	 * 
	 * @param items
	 */
	public void setItems(List<Nursingrecord> nursingrecords) {
		mNursingrecords = nursingrecords;
		List<String> items = new ArrayList<String>();
		for (Nursingrecord n : mNursingrecords) {
			items.add(n.getNURSINGNAME());
		}
		items.add("不插入");
		mWheel.setItems(items);
	}

	public void setTitle(String title) {
		mTextTitle.setText(title);
	}

}
