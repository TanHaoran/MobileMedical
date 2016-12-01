package com.thr.mobilemedical.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.thr.mobilemedical.R;
import com.thr.mobilemedical.bean.NursingBed;
import com.thr.mobilemedical.bean.NursingItem;
import com.thr.mobilemedical.utils.CommonAdapter;
import com.thr.mobilemedical.utils.L;
import com.thr.mobilemedical.utils.ViewHolder;
import com.thr.mobilemedical.view.NursingItemView;

import java.util.List;

/**
 * Created by Jerry on 2016/5/17.
 */
public class NursingByBedAdapter extends CommonAdapter<NursingBed> {

    private Context mContext;



    public NursingByBedAdapter(Context context, List<NursingBed> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        mContext = context;
    }

    @Override
    public void convert(ViewHolder helper, NursingBed item) {
        LinearLayout nursingLayout = helper.getView(R.id.ll_nursing_layout);
        // 清空护理项的布局，根据具体的多少进行添加
        nursingLayout.removeAllViews();
        for(NursingItem ni: item.getNursingList()) {
            NursingItemView v = new NursingItemView(mContext);
            v.setName(ni.getName());
            v.setDesc(ni.getDesc());
            v.setTimes(ni.getTimes());
            nursingLayout.addView(v);
        }
    }
}
