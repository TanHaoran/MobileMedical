package com.thr.mobilemedical.adapter;

import android.content.Context;
import android.widget.LinearLayout;

import com.thr.mobilemedical.R;
import com.thr.mobilemedical.bean.NursingBed;
import com.thr.mobilemedical.bean.NursingBigItem;
import com.thr.mobilemedical.bean.NursingItem;
import com.thr.mobilemedical.utils.CommonAdapter;
import com.thr.mobilemedical.utils.ViewHolder;
import com.thr.mobilemedical.view.NursingBedBlock;
import com.thr.mobilemedical.view.NursingItemLine;
import com.thr.mobilemedical.view.NursingItemView;

import java.util.List;

/**
 * Created by Jerry on 2016/5/17.
 */
public class NursingByItemAdapter extends CommonAdapter<NursingBigItem> {

    private Context mContext;



    public NursingByItemAdapter(Context context, List<NursingBigItem> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        mContext = context;
    }

    @Override
    public void convert(ViewHolder helper, NursingBigItem item) {
        helper.setText(R.id.tv_name, item.getName());
        LinearLayout nursingLayout = helper.getView(R.id.ll_bed);
        // 清空护理项的布局，根据具体的多少进行添加
        nursingLayout.removeAllViews();
        for(NursingBed nb: item.getBedList()) {
            NursingBedBlock b = new NursingBedBlock(mContext);
            b.setNursingBed(nb);
            nursingLayout.addView(b);
        }
    }
}
