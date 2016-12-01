package com.thr.mobilemedical.fragment;

import java.util.ArrayList;
import java.util.List;

import com.thr.mobilemedical.R;
import com.thr.mobilemedical.bean.Nursingpatrol;
import com.thr.mobilemedical.utils.CommonAdapter;
import com.thr.mobilemedical.utils.SelectbarUtil;
import com.thr.mobilemedical.utils.ViewHolder;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @description 护理巡视-护理巡视
 * @date 2015-9-29 17:15:11
 * @version 1.0
 * @Company Buzzlysoft
 * @author Jerry Tan
 * @email thrforever@126.com
 * @remark
 */
public class NursingpatrolFragment extends Fragment {

	private View v;

	private ListView mListView;

	private List<Nursingpatrol> mPatrolList = new ArrayList<Nursingpatrol>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.fragment_nursingpatrol, container, false);
		initView();
		return v;
	}

	private void initView() {
		initSelectbar();
		mListView = (ListView) v.findViewById(R.id.lv_nursingpatrol);
		for (int i = 0; i < 10; i++) {
			Nursingpatrol patrol = new Nursingpatrol();
			patrol.setTime("2015-9-30 10:38:05");
			patrol.setName("章子怡");
			mPatrolList.add(patrol);
		}
		mListView.setAdapter(new CommonAdapter<Nursingpatrol>(getActivity(),
				mPatrolList, R.layout.item_nursingpatrol) {

			@Override
			public void convert(ViewHolder helper, Nursingpatrol item) {
				TextView mTime = helper.getView(R.id.tv_time);
				TextView mName = helper.getView(R.id.tv_name);
				mTime.setText(item.getTime());
				mName.setText(item.getName());

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
