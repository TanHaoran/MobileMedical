package com.thr.mobilemedical.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.thr.mobilemedical.bean.Blood;
import com.thr.mobilemedical.R;
import com.thr.mobilemedical.utils.CommonAdapter;
import com.thr.mobilemedical.utils.ViewHolder;

/**
 * @description 血糖列表、血压列表、血氧列表的适配器
 * @date 2015年10月14日 上午10:18:11
 * @version 1.0
 * @Company Buzzlysoft
 * @author Jerry Tan
 * @email thrforever@126.com
 * @remark
 */
public class BloodAdapter extends CommonAdapter<Blood> {

	public BloodAdapter(Context context, List<Blood> mDatas, int itemLayoutId) {
		super(context, mDatas, itemLayoutId);
	}

	@Override
	public void convert(ViewHolder helper, Blood item) {
		// 床号
		TextView tBed = (TextView) helper.getView(R.id.tv_bed);
		// 姓名
		TextView tName = (TextView) helper.getView(R.id.tv_name);
		// 总数
		TextView tTotal = (TextView) helper.getView(R.id.tv_totaltimes);
		// 已执行
		TextView tExe = (TextView) helper.getView(R.id.tv_exetimes);
		// 状态
		ImageView iState = (ImageView) helper.getView(R.id.iv_state);

		tBed.setText(item.getBEDNO());
		tName.setText(item.getPATIENTNAME());
		if ("".equals(item.getPERFROMTIMES())) {
			item.setPERFROMTIMES("0");
		}
		tTotal.setText(item.getPERFROMTIMES());
		tExe.setText(item.getPERFORMEDTIMES());
		// 根据已执行和总数是否相等判断状态是否完毕，从而是否显示已完成的对号
		if (Integer.parseInt(item.getPERFROMTIMES()) == Integer.parseInt(item
				.getPERFORMEDTIMES())) {
			iState.setVisibility(View.VISIBLE);
		} else {
			iState.setVisibility(View.INVISIBLE);
		}
	}

}
