package com.thr.mobilemedical.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.thr.mobilemedical.R;
import com.thr.mobilemedical.adapter.NursingByBedAdapter;
import com.thr.mobilemedical.adapter.NursingByItemAdapter;
import com.thr.mobilemedical.adapter.PatientAdapter;
import com.thr.mobilemedical.bean.NursingBed;
import com.thr.mobilemedical.bean.NursingBigItem;
import com.thr.mobilemedical.bean.NursingItem;
import com.thr.mobilemedical.bean.Office;
import com.thr.mobilemedical.bean.Patient;
import com.thr.mobilemedical.constant.LoginInfo;
import com.thr.mobilemedical.constant.Method;
import com.thr.mobilemedical.constant.SettingInfo;
import com.thr.mobilemedical.utils.GsonUtil;
import com.thr.mobilemedical.utils.HttpGetUtil;
import com.thr.mobilemedical.utils.L;
import com.thr.mobilemedical.utils.ScanUtil;
import com.thr.mobilemedical.utils.TopActivityListener;
import com.thr.mobilemedical.utils.TopExitImpl;
import com.thr.mobilemedical.view.LeftMenuPopupWindow;
import com.thr.mobilemedical.view.MyProgressDialog;
import com.thr.mobilemedical.view.PatientPopupWindow;
import com.thr.mobilemedical.view.SelectorView;
import com.thr.mobilemedical.view.TitleBar;
import com.thr.mobilemedical.view.TitleBar.OnLeftClickListener;
import com.thr.mobilemedical.view.TitleBar.OnRightClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jerry Tan
 * @version 1.0
 * @description 主界面
 * @date 2015年8月25日 上午10:09:27
 * @Company Buzzlysoft
 * @email thrforever@126.com
 * @remark
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends Activity implements OnClickListener {

    // 左侧菜单
    private LeftMenuPopupWindow mLeftWindow;
    // 病患菜单
    private PatientPopupWindow mPatientWindow;

    // 标题栏
    @ViewInject(R.id.title_bar)
    private TitleBar mTitleBar;

    @ViewInject(R.id.tv_total)
    private TextView mTextTotal;
    @ViewInject(R.id.tv_order)
    private TextView mTextOrder;
    @ViewInject(R.id.tv_execute)
    private TextView mTextExecute;

    @ViewInject(R.id.listview_by_bed)
    private ListView mListViewByBed;
    @ViewInject(R.id.listview_by_item)
    private ListView mListViewByItem;

    private List<NursingBed> mDataByBed;
    private List<NursingBigItem> mDataByItem;
    private NursingByBedAdapter mByBedAdapter;
    private NursingByItemAdapter mByItemAdapter;


    private ListView mListView; // 病患列表
    private PatientAdapter mAdapter;

    private MyProgressDialog mDialog; // 等待框

    private List<Patient> mPatientList; // 病患列表

    private TopActivityListener mTopListener;

    // 读取科室
    private List<Office> mOfficeList;

    @ViewInject(R.id.select_view)
    private SelectorView mSelectorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().addActivity(this);
        initView();
        // 读取病患列表
        loadPatientList(LoginInfo.OFFICE_ID);
        loadNursingData();
        // 读取护理记录单个数
        loadNursingRecord(LoginInfo.OFFICE_ID);
        // 读取默认提交护理记录单
        loadDefaultNursingrecord();
    }

    /**
     * 读取护理项目
     */
    private void loadNursingData() {
        // 按照床位分
        mDataByBed = new ArrayList<NursingBed>();
        for (int i = 0; i < 4; i++) {
            NursingBed bed = new NursingBed();
            bed.setId("04");
            bed.setName("张靓颖");
            bed.setLevel("II");
            bed.setSex("女");
            List<NursingItem> nursingList = new ArrayList<NursingItem>();
            NursingItem item = new NursingItem();
            item.setName("心电监测");
            item.setDesc("24小时心电监测");
            item.setTimes(1);
            NursingItem item2 = new NursingItem();
            item2.setName("血糖");
            item2.setDesc("监测血糖");
            item2.setTimes(6);
            nursingList.add(item);
            nursingList.add(item2);
            bed.setNursingList(nursingList);
            mDataByBed.add(bed);
        }
        mByBedAdapter = new NursingByBedAdapter(this, mDataByBed, R.layout.item_nursing_by_bed);
        mListViewByBed.setAdapter(mByBedAdapter);
        // 按照护理项目分
        mDataByItem = new ArrayList<NursingBigItem>();
        for (int i = 0; i < 2; i++) {
            NursingBigItem item = new NursingBigItem();
            item.setName("静脉点滴");
            List<NursingBed> bedList = new ArrayList<NursingBed>();
            NursingBed b = new NursingBed();
            b.setId("001");
            b.setName("李润泽");
            List<NursingItem> itemList = new ArrayList<NursingItem>();
            NursingItem ni = new NursingItem();
            ni.setName("5%葡萄糖静脉注射");
            ni.setDesc("午餐后注射");
            ni.setTimes(1);
            itemList.add(ni);
            b.setNursingList(itemList);
            bedList.add(b);
            item.setBedList(bedList);
            mDataByItem.add(item);
        }
        mByItemAdapter = new NursingByItemAdapter(this, mDataByItem, R.layout.item_nursing_by_item);
        mListViewByItem.setAdapter(mByItemAdapter);

    }

    private void loadDefaultNursingrecord() {
        String url = SettingInfo.SERVICE + Method.GET_REGIST_OFFICE;

        HttpGetUtil httpGet = new HttpGetUtil(this) {

            @Override
            public void success(String json) {
                L.i("读取默认提交护理记录单------" + json);
                mOfficeList = GsonUtil.getOfficeList(json);
                setDefaultNursingrecord();
            }
        };
        httpGet.doGet(url, mDialog, this, "读取默认提交护理记录单");
    }

    /**
     * 设置默认保存的护理记录单
     */
    protected void setDefaultNursingrecord() {
        if (mOfficeList != null && mOfficeList.size() > 0) {
            for (Office office : mOfficeList) {
                if (LoginInfo.OFFICE_ID.equals(office.getOFFICEHISID())) {
                    LoginInfo.defaultNursingid = office.getNURSINGID();
                    if (LoginInfo.defaultNursingid == null) {
                        LoginInfo.defaultNursingid = "";
                    }
                }
            }
        }
    }

    private void initView() {
        mLeftWindow = new LeftMenuPopupWindow(this);
        mPatientWindow = new PatientPopupWindow(this);

        mTitleBar.setOnLeftClickListener(new OnLeftClickListener() {

            @Override
            public void onLeftClick(View v) {
                mLeftWindow.showAtLocation(findViewById(R.id.ll_main),
                        Gravity.BOTTOM, 0, 0);
            }
        });
        mTitleBar.setOnRightClickListener(new OnRightClickListener() {

            @Override
            public void onRightClick(View v) {
                mAdapter.setDatas(mPatientList);
                mAdapter.notifyDataSetChanged();
                mPatientWindow.showAtLocation(findViewById(R.id.ll_main),
                        Gravity.BOTTOM, 0, 0);
            }
        });
        mDialog = new MyProgressDialog(this);

        mTextTotal.setText("18");
        mTextOrder.setText("8");
        mTextExecute.setText("6");


        // 设置最上层退出处理
        setTopListener(new TopExitImpl());
        mSelectorView.setOnSelectorClickListener(new SelectorView.OnSelectorClickListener() {
            @Override
            public void onLeftClick() {
                mListViewByBed.setVisibility(View.VISIBLE);
                mListViewByItem.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onRightClick() {
                mListViewByBed.setVisibility(View.INVISIBLE);
                mListViewByItem.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            // 跳转病患列表
            case R.id.ll_patientinfo:
                intent.setClass(this, PatientinfoActivity.class);
                break;
            // 跳转护理录入
            case R.id.ll_nursingrecord:
                intent.setClass(this, NursingrecordActivity.class);
                break;
        }
        startActivity(intent);
    }

    public void setTopListener(TopActivityListener topListener) {
        mTopListener = topListener;
    }

    @Override
    public void onBackPressed() {
        mTopListener.onExit(this);
    }


    /**
     * 读取所有病患信息
     */
    private void loadPatientList(String officeId) {
        String url = SettingInfo.SERVICE + Method.PATIENT_INFO_BY_OFFICEID
                + "?OfficeId=" + officeId;
        HttpGetUtil httpGet = new HttpGetUtil(this) {

            @Override
            public void success(String json) {
                L.i("病患信息------" + json);
                mPatientList = GsonUtil.getPatientInfoList(json);
                LoginInfo.PATIENT_ALL = mPatientList;
                if (GsonUtil.hasData(mPatientList)) {
                    LoginInfo.patient = mPatientList.get(0);
                }
                setPatientListView();
            }
        };
        httpGet.doGet(url, mDialog, this, "患者信息");
    }

    /**
     * 读取护理记录单
     */
    private void loadNursingRecord(String officeId) {
        String url = SettingInfo.SERVICE + Method.GET_NURSING_RECORD
                + "?OfficeId=" + officeId;
        HttpGetUtil httpGet = new HttpGetUtil(this) {

            @Override
            public void success(String json) {
                L.i("护理记录单------" + json);
                LoginInfo.nursingrecordList = GsonUtil
                        .getNursingRecordList(json);
            }
        };
        httpGet.doGet(url, mDialog, this, "护理记录单");
    }

    /**
     * 初始化病人列表信息
     */
    protected void setPatientListView() {
        mListView = mPatientWindow.getListView();
        if (GsonUtil.hasData(mPatientList)) {
            mAdapter = new PatientAdapter(this, mPatientList,
                    R.layout.item_patient);
            mListView.setAdapter(mAdapter);
            mListView.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    if (GsonUtil.hasData(mPatientList)) {
                        Patient p = mPatientList.get(position);
                        if (p != null && GsonUtil.hasData(LoginInfo.PATIENT_ALL)) {
                            int index = LoginInfo.PATIENT_ALL.indexOf(p);
                            LoginInfo.patient = p;
                            LoginInfo.patientIndex = index;
                            Intent intent = new Intent(MainActivity.this,
                                    PatientinfoActivity.class);
                            startActivity(intent);
                        }
                    }
                }
            });
        }
    }

    @Override
    protected void onResume() {
        ScanUtil.registerScanReceiver(this);
        super.onResume();
    }
}