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
 * @description 静滴医嘱适配器
 * @date 2015年10月19日 下午4:09:12
 * @version 1.0
 * @Company Buzzlysoft
 * @author Jerry Tan
 * @email thrforever@126.com
 * @remark
 */
public class DropAdapter extends CommonAdapter<Order> {

	public DropAdapter(Context context, List<Order> mDatas, int itemLayoutId) {
		super(context, mDatas, itemLayoutId);
	}

	@Override
	public void convert(ViewHolder helper, Order item) {
		// 医嘱名
		TextView tName = (TextView) helper.getView(R.id.tv_ordername);
		// dose
		TextView tDose = (TextView) helper.getView(R.id.tv_orderdose);
		// 用量
		TextView tUsage = (TextView) helper.getView(R.id.tv_orderusage);
		// 频率
		TextView tFrequence = (TextView) helper.getView(R.id.tv_orderfrequence);
		// 执行时间
		TextView tTime = (TextView) helper.getView(R.id.tv_ordertime);
		LinearLayout userLayout = (LinearLayout) helper
				.getView(R.id.ll_exeuser);
		// 执行人
		TextView tUser = (TextView) helper.getView(R.id.tv_orderuser);
		// 状态
		TextView tState = (TextView) helper.getView(R.id.tv_state);
		// 状态图标
		ImageView iState = (ImageView) helper.getView(R.id.iv_state);

		tName.setText(item.getORDERCONTENT());
		tDose.setText(item.getPERTIME());
		tUsage.setText(item.getDOSETYPE());
		tFrequence.setText(item.getFREQUENCY());
		tTime.setText(item.getEXECTIME());
		if (item.getExecUser() != null && item.getExecUser().length() > 0) {
			userLayout.setVisibility(View.VISIBLE);
			tUser.setText(item.getExecUser());
		} else {
			userLayout.setVisibility(View.GONE);
		}
		// 如果有执行时间就显示已执行
		if (item.getEXECTIME() != null && !"".equals(item.getEXECTIME())) {
			tState.setText("已执行");
			iState.setImageResource(R.drawable.icon_yzx);
		} else {
			tState.setText("未执行");
			iState.setImageResource(R.drawable.icon_wzx);
		}
	}
}