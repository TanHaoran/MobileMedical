package com.thr.mobilemedical.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.thr.mobilemedical.bean.Nursingpaper;
import com.thr.mobilemedical.R;
import com.thr.mobilemedical.utils.CommonAdapter;
import com.thr.mobilemedical.utils.ViewHolder;

/**
 * @description 护理清单列表适配器
 * @date 2015年10月14日 上午10:59:36
 * @version 1.0
 * @Company Buzzlysoft
 * @author Jerry Tan
 * @email thrforever@126.com
 * @remark
 */
public class NursinglistAdapter extends CommonAdapter<Nursingpaper> {

	public NursinglistAdapter(Context context, List<Nursingpaper> mDatas,
			int itemLayoutId) {
		super(context, mDatas, itemLayoutId);
	}

	@Override
	public void convert(ViewHolder helper, Nursingpaper item) {
		TextView tBed = (TextView) helper.getView(R.id.tv_bed);
		TextView tName = (TextView) helper.getView(R.id.tv_name);
		TextView tTotal = (TextView) helper.getView(R.id.tv_total);
		TextView tExe = (TextView) helper.getView(R.id.tv_execute);
		TextView tNot = (TextView) helper.getView(R.id.tv_notexe);
		ImageView iState = (ImageView) helper.getView(R.id.iv_state);

		tBed.setText(item.getBEDNO());
		tName.setText(item.getNAME());
		tTotal.setText(item.getZCOUNT());
		tExe.setText(item.getYCOUNT());
		// 计算执行未执行的数目
		int not = Integer.parseInt(item.getWCOUNT());
		tNot.setText(String.valueOf(not));
		if (Integer.parseInt(item.getZCOUNT()) == Integer.parseInt(item
				.getYCOUNT())) {
			iState.setVisibility(View.VISIBLE);
		} else {
			iState.setVisibility(View.INVISIBLE);
		}
	}
}
