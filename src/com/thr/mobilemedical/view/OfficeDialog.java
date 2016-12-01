package com.thr.mobilemedical.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.thr.mobilemedical.R;
import com.thr.mobilemedical.bean.Office;
import com.thr.mobilemedical.constant.LoginInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jerry Tan
 * @version 1.0
 * @description 自定义Dialog弹出框
 * @date 2015年9月30日 上午9:40:13
 * @Company Buzzlysoft
 * @email thrforever@126.com
 * @remark
 */
public class OfficeDialog extends Dialog {

    private Context mContext;

    private WheelView mWheel;
    private List<Office> mOfficeList;

    private Button mSure;
    private Button mCancle;

    public OfficeDialog(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_office);

        mSure = (Button) findViewById(R.id.btn_sure);
        mCancle = (Button) findViewById(R.id.btn_cancel);
        mWheel = (WheelView) findViewById(R.id.wv_office);

        mWheel.setOffset(2);

        mSure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                saveInfo();
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
     * 保存选择的科室信息
     */
    protected void saveInfo() {
        int index = mWheel.getSeletedIndex();
        if (index == -1) {
            index = 0;
        }
        if (mOfficeList != null) {
            Office office = mOfficeList.get(index);
            SharedPreferences sp = mContext.getSharedPreferences(
                    ConfigDialog.CONFIG, Activity.MODE_PRIVATE);
            String officeId = office.getOFFICEHISID();
            String officeName = office.getOFFICENAME();
            sp.edit().putString("office_id", officeId).commit();
            sp.edit().putString("office_name", officeName).commit();
            LoginInfo.OFFICE_ID = officeId;
            LoginInfo.OFFICE_NAME = officeName;
        }
    }

    /**
     * 设置科室数据
     *
     * @param offices
     */
    public void setItems(List<Office> offices) {
        mOfficeList = offices;
        List<String> items = new ArrayList<String>();
        for (Office o : offices) {
            items.add(o.getOFFICENAME());
        }
        mWheel.setItems(items);
    }

}
