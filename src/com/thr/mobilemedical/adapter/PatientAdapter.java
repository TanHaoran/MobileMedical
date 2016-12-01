package com.thr.mobilemedical.adapter;

import java.util.List;

import android.content.Context;
import android.widget.TextView;

import com.thr.mobilemedical.bean.Patient;
import com.thr.mobilemedical.constant.Method;
import com.thr.mobilemedical.R;
import com.thr.mobilemedical.utils.CommonAdapter;
import com.thr.mobilemedical.utils.DateUtil;
import com.thr.mobilemedical.utils.ViewHolder;

/**
 * @description 病患列表适配器
 * @date 2015年9月24日 下午6:33:06
 * @version 1.0
 * @Company Buzzlysoft
 * @author Jerry Tan
 * @email thrforever@126.com
 * @remark
 */
public class PatientAdapter extends CommonAdapter<Patient> {

	private Context mContext;

	public PatientAdapter(Context context, List<Patient> mDatas,
			int itemLayoutId) {
		super(context, mDatas, itemLayoutId);
		mContext = context;
	}

	@Override
	public void convert(ViewHolder helper, Patient item) {
		TextView tvBed = helper.getView(R.id.tv_bed);
		TextView tvName = helper.getView(R.id.tv_name);
		TextView tvSex = helper.getView(R.id.tv_sex);
		TextView tvAge = helper.getView(R.id.tv_age);
		TextView tvDiagnosis = helper.getView(R.id.tv_diagnosis);

		tvBed.setText(item.getBEDNO());
		tvName.setText(item.getNAME());
		tvSex.setText(item.getSEX());
		tvAge.setText(DateUtil.getAge(item.getBIRTHDAY()) + "岁");
		tvDiagnosis.setText(item.getDIAGNOSIS());

		// 根据不同的护理级别来显示不同的图片
		String nursingLevel = item.getNURSELEVEL();
		if (Method.isNursingSpecial(nursingLevel)) {
			tvBed.setBackgroundResource(R.drawable.nursinglevel_special);
			tvBed.setTextColor(mContext.getResources().getColor(R.color.white));
		} else if (Method.isNursingOne(nursingLevel)) {
			tvBed.setBackgroundResource(R.drawable.nursinglevel_one);
			tvBed.setTextColor(mContext.getResources().getColor(R.color.white));
		} else if (Method.isNursingTwo(nursingLevel)) {
			tvBed.setBackgroundResource(R.drawable.nursinglevel_two);
			tvBed.setTextColor(mContext.getResources().getColor(R.color.white));
		} else {
			tvBed.setBackgroundResource(R.drawable.nursinglevel_three);
			tvBed.setTextColor(mContext.getResources().getColor(
					R.color.login_btn_bg_normal));
		}
	}
}
