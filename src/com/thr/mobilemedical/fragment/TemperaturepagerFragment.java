package com.thr.mobilemedical.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.thr.mobilemedical.R;
import com.thr.mobilemedical.adapter.TemperatureAdapter;
import com.thr.mobilemedical.bean.NursingrecordFiled;
import com.thr.mobilemedical.bean.Temperature;
import com.thr.mobilemedical.constant.LoginInfo;
import com.thr.mobilemedical.constant.Method;
import com.thr.mobilemedical.constant.SettingInfo;
import com.thr.mobilemedical.utils.GsonUtil;
import com.thr.mobilemedical.utils.HttpGetUtil;
import com.thr.mobilemedical.utils.HttpUtils;
import com.thr.mobilemedical.utils.HttpUtils.CallBack;
import com.thr.mobilemedical.utils.L;
import com.thr.mobilemedical.view.MyProgressDialog;

/**
 * @author Jerry Tan
 * @version 1.0
 * @description 护理录入-体温单
 * @date 2015年9月11日14:13:33
 * @Company Buzzlysoft
 * @email thrforever@126.com
 * @remark
 */
@SuppressLint("HandlerLeak")
public class TemperaturepagerFragment extends Fragment {

    private ListView mListView;
    private TemperatureAdapter mAdapter;

    private TextView mTime1;
    private TextView mTime2;
    private TextView mTime3;
    private TextView mTime4;
    private TextView mTime5;
    private TextView mTime6;

    private List<Temperature> mTemperatureList;
    // 护理记录单字段结构
    private List<NursingrecordFiled> mFileds;

    private MyProgressDialog mDialog;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            updateOnListView();
        }

        ;
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_temperaturepager,
                container, false);

        initView(v);
        if (!SettingInfo.IS_DEMO) {
            // 读取护理记录单字段以获取时间段
            if (LoginInfo.nursingrecordList.get(0) != null) {
                loadNursingRecordFiled(LoginInfo.nursingrecordList.get(0)
                        .getNURSINGID());
            }
            loadPatientTemperature(LoginInfo.OFFICE_ID);
        } else {
            mTime1.setText("8");
            mTime2.setText("10");
            mTime3.setText("14");
            mTime4.setText("16");
            mTime5.setText("18");
            mTime6.setText("20");
            mTemperatureList = new ArrayList<Temperature>();
            for(int i = 0; i < LoginInfo.PATIENT_ALL.size(); i++) {
                Temperature t = new Temperature();
                t.setNAME(LoginInfo.PATIENT_ALL.get(i).getNAME());
                t.setBEDNO(LoginInfo.PATIENT_ALL.get(i).getBEDNO());
                mTemperatureList.add(t);
            }
            mAdapter = new TemperatureAdapter(getActivity(), mTemperatureList,
                    R.layout.item_temperature);
            mListView.setAdapter(mAdapter);
        }
        return v;
    }

    /**
     * 读取所有病患的体温
     */
    private void loadPatientTemperature(String officeId) {
        String url = SettingInfo.SERVICE + Method.GET_PATIENT_TEMPERATURE
                + "?OfficeID=" + officeId;
        HttpGetUtil httpGet = new HttpGetUtil(getActivity()) {

            @Override
            public void success(String json) {
                L.i("病患体温单------" + json);
                mTemperatureList = GsonUtil.getPatientTemperature(json);
                setOnListView();
            }
        };
        httpGet.doGet(url, mDialog, getActivity(), "病患体温单");
    }

    /**
     * 读取护理记录单结构
     */
    /**
     * 读取护理记录单结构
     */
    private void loadNursingRecordFiled(String nursignId) {
        String url = SettingInfo.SERVICE + Method.GET_NURSING_FILED
                + "?NursingId=" + nursignId;
        HttpGetUtil httpGet = new HttpGetUtil(getActivity()) {

            @Override
            public void success(String json) {
                mFileds = GsonUtil.getNursingStructureList(json);
                getTimePoint();
                setTimePotin();
            }
        };
        httpGet.doGet(url, mDialog, getActivity(), "护理记录单");
    }

    /**
     * 设置好时间点
     */
    protected void setTimePotin() {
        if (LoginInfo.timePoints != null) {
            mTime1.setText(LoginInfo.timePoints.get(0).substring(2));
            mTime2.setText(LoginInfo.timePoints.get(1).substring(2));
            mTime3.setText(LoginInfo.timePoints.get(2).substring(2));
            mTime4.setText(LoginInfo.timePoints.get(3).substring(2));
            mTime5.setText(LoginInfo.timePoints.get(4).substring(2));
            mTime6.setText(LoginInfo.timePoints.get(5).substring(2));
        }
    }

    /**
     * 根据护理记录单结构获取测量时间点
     */
    private void getTimePoint() {
        if (mFileds != null && mFileds.size() > 0) {
            for (NursingrecordFiled filed : mFileds) {
                if ("TENDTIME".equals(filed.getCOLNAMES())) {
                    if (filed.getNURSINGCONTENT() != null) {
                        String[] times = filed.getNURSINGCONTENT().split("\\|");
                        if (times != null) {
                            List<String> timpPotins = new ArrayList<String>();
                            for (String time : times) {
                                if (!"空".equals(time)) {
                                    timpPotins.add(time);
                                }
                            }
                            LoginInfo.timePoints = timpPotins;
                        }
                    }
                }
            }
        }
    }

    /**
     * 在界面上显示出来体温数据
     */
    protected void setOnListView() {
        if (mTemperatureList != null && mTemperatureList.size() > 0) {
            mAdapter = new TemperatureAdapter(getActivity(), mTemperatureList,
                    R.layout.item_temperature);
            mListView.setAdapter(mAdapter);
        }
    }

    /**
     * 更新体温表
     */
    private void updateOnListView() {
        if (mAdapter == null) {
            mAdapter = new TemperatureAdapter(getActivity(), mTemperatureList,
                    R.layout.item_temperature);
        }
        mAdapter.setDatas(mTemperatureList);
        mAdapter.notifyDataSetChanged();
    }

    private void initView(View v) {

        mTime1 = (TextView) v.findViewById(R.id.tv_time1);
        mTime2 = (TextView) v.findViewById(R.id.tv_time2);
        mTime3 = (TextView) v.findViewById(R.id.tv_time3);
        mTime4 = (TextView) v.findViewById(R.id.tv_time4);
        mTime5 = (TextView) v.findViewById(R.id.tv_time5);
        mTime6 = (TextView) v.findViewById(R.id.tv_time6);
        mListView = (ListView) v.findViewById(R.id.lv_temperature_normal);

        mTemperatureList = new ArrayList<Temperature>();
        mDialog = new MyProgressDialog(getActivity());
    }

    /**
     * 更新体温列表
     */
    private void updateTemperatureForm() {
        String url = SettingInfo.SERVICE + Method.GET_PATIENT_TEMPERATURE
                + "?OfficeID=" + LoginInfo.OFFICE_ID;
        HttpUtils.doGetAsyn(getActivity(), url, new CallBack() {

            @Override
            public void onRequestComplete(String json) {
                L.i("护理记录单单条记录------" + json);
                mTemperatureList = GsonUtil.getPatientTemperature(json);
                handler.sendEmptyMessage(0);
            }
        }, "体温列表");
    }

    @Override
    public void onResume() {
        super.onResume();
        updateTemperatureForm();
    }
}
