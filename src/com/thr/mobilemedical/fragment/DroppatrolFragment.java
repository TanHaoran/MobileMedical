package com.thr.mobilemedical.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.thr.mobilemedical.R;
import com.thr.mobilemedical.bean.Droppatrol;
import com.thr.mobilemedical.utils.CommonAdapter;
import com.thr.mobilemedical.utils.SelectbarUtil;
import com.thr.mobilemedical.utils.ViewHolder;
import com.thr.mobilemedical.view.SpeedPopupWindow;
import com.thr.mobilemedical.view.SpeedPopupWindow.SaveClickListener;

/**
 * @description 护理巡视-静滴巡视
 * @date 2015-9-29 17:15:11
 * @version 1.0
 * @Company Buzzlysoft
 * @author Jerry Tan
 * @email thrforever@126.com
 * @remark
 */
public class DroppatrolFragment extends Fragment {

	private View v;
	private ListView mListView;
	private List<Droppatrol> mPatrolList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.fragment_droppatrol, container, false);
		initView();
		return v;
	}

	private void initView() {
		initSelectbar();

		mListView = (ListView) v.findViewById(R.id.lv_droppatrol);
		mPatrolList = new ArrayList<Droppatrol>();
		for (int i = 0; i < 10; i++) {
			Droppatrol patrol = new Droppatrol();
			patrol.setTime("10:38:05");
			patrol.setContent("5%葡萄糖注射液（软袋）100ml，注射用水溶性维生素复方");
			patrol.setSpeed("60");
			mPatrolList.add(patrol);
		}
		mListView.setAdapter(new CommonAdapter<Droppatrol>(getActivity(),
				mPatrolList, R.layout.item_droppatrol) {

			@Override
			public void convert(ViewHolder helper, Droppatrol item) {
				View itemView = helper.getView(R.id.ll_layout);
				TextView mStartime = helper.getView(R.id.tv_starttime);
				TextView mContent = helper.getView(R.id.tv_content);
				final TextView mTextSpeed = helper.getView(R.id.tv_dropspeed);
				TextView mPatroltime = helper.getView(R.id.tv_patroltime);
				mStartime.setText(item.getTime());
				mContent.setText(item.getContent());
				mTextSpeed.setText(item.getSpeed());
				mPatroltime.setText(item.getPatrolTime());

				itemView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						final SpeedPopupWindow speedWindow = new SpeedPopupWindow(
								getActivity(), mTextSpeed);
						speedWindow.showAtLocation(
								getActivity().findViewById(R.id.ll_main),
								Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0,
								0);
						speedWindow
								.setSaveClickListener(new SaveClickListener() {
									@Override
									public void onSaveClick() {
										// 保存低速并修改界面上的显示
										String speed = speedWindow.getSpeed();
										mTextSpeed.setText(speed);
									}
								});
					}
				});
			}
		});
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
		selectbarUtil.initView(getActivity(), v);
	}

}
