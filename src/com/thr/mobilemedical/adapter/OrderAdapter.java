package com.thr.mobilemedical.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thr.mobilemedical.bean.Order;
import com.thr.mobilemedical.R;
import com.thr.mobilemedical.utils.CommonAdapter;
import com.thr.mobilemedical.utils.ViewHolder;

/**
 * @description 医嘱列表适配器
 * @date 2015年9月28日 下午12:14:05
 * @version 1.0
 * @Company Buzzlysoft
 * @author Jerry Tan
 * @email thrforever@126.com
 * @remark
 */
public class OrderAdapter extends CommonAdapter<Order> {

	public OrderAdapter(Context context, List<Order> mDatas, int itemLayoutId) {
		super(context, mDatas, itemLayoutId);
	}

	@Override
	public void convert(ViewHolder helper, Order item) {
		TextView tName = (TextView) helper.getView(R.id.tv_ordername);
		LinearLayout lUsage = (LinearLayout) helper.getView(R.id.ll_usage);
		TextView tUsage = (TextView) helper.getView(R.id.tv_orderusage);
		TextView tType = (TextView) helper.getView(R.id.tv_ordertype);
		TextView tFrequence = (TextView) helper.getView(R.id.tv_orderfrequence);
		TextView tTime = (TextView) helper.getView(R.id.tv_ordertime);
		TextView tState = (TextView) helper.getView(R.id.tv_state);
		LinearLayout lExectime = (LinearLayout) helper.getView(R.id.ll_exetime);
		TextView tExectime = (TextView) helper.getView(R.id.tv_exetime);
		ImageView iState = (ImageView) helper.getView(R.id.iv_state);
		tName.setText(item.getORDERCONTENT());
		tUsage.setText(item.getMEDICINEWAY());
		if ("".equals(item.getMEDICINEWAY())) {
			lUsage.setVisibility(View.GONE);
		} else {
			lUsage.setVisibility(View.VISIBLE);
		}
		tType.setText(item.getORDERTYPE());
		tFrequence.setText(item.getFREQUENCY());
		tTime.setText(item.getSTARTTIME());
		// 有执行时间就显示已执行
		if (item.getEXECTIME() != null && !"".equals(item.getEXECTIME())) {
			lExectime.setVisibility(View.VISIBLE);
			tState.setText("已执行");
			tExectime.setText(item.getEXECTIME());
			iState.setImageResource(R.drawable.icon_yzx);
		} else {
			lExectime.setVisibility(View.GONE);
			tState.setText("未执行");
			iState.setImageResource(R.drawable.icon_wzx);
		}
	}
}
