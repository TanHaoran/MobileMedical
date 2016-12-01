package com.thr.mobilemedical.fragment;

import java.text.ParseException;
import java.util.Date;

import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thr.mobilemedical.R;
import com.thr.mobilemedical.constant.LoginInfo;
import com.thr.mobilemedical.utils.DateUtil;
import com.thr.mobilemedical.utils.SelectbarUtil;
import com.thr.mobilemedical.view.PatientBarView;

/**
 * @author Jerry Tan
 * @version 1.0
 * @description 病患信息-基础
 * @date 2015年9月11日14:11:46
 * @Company Buzzlysoft
 * @email thrforever@126.com
 * @remark
 */
public class BaseFragment extends Fragment {

    private View v;

    private TextView mTextBed;
    private TextView mTextInhosid;
    private TextView mTextName;
    private TextView mTextAge;
    private TextView mTextSex;
    private TextView mTextBorn;
    private TextView mTextPre;
    private TextView mTextBalance;
    private TextView mTextWay;
    private TextView mTextPhone;

    private TextView mTextInDate;
    private TextView mTextIllness;
    private TextView mTextDay;
    private TextView mTextTime;
    private TextView mTextDoc;
    private TextView mTextNurse;
    private TextView mTextDiag;
    private TextView mTextHistory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_base, container, false);
        initView();
        return v;
    }

    private void initView() {

        mTextBed = (TextView) v.findViewById(R.id.tv_bedno);
        mTextInhosid = (TextView) v.findViewById(R.id.tv_inhos);
        mTextName = (TextView) v.findViewById(R.id.tv_namebase);
        mTextAge = (TextView) v.findViewById(R.id.tv_agebase);
        mTextSex = (TextView) v.findViewById(R.id.tv_sex);
        mTextBorn = (TextView) v.findViewById(R.id.tv_born);
        mTextPre = (TextView) v.findViewById(R.id.tv_prepaid);
        mTextBalance = (TextView) v.findViewById(R.id.tv_balance);
        mTextWay = (TextView) v.findViewById(R.id.tv_payway);
        mTextPhone = (TextView) v.findViewById(R.id.tv_phone);

        mTextInDate = (TextView) v.findViewById(R.id.tv_indate);
        mTextIllness = (TextView) v.findViewById(R.id.tv_illness);
        mTextDay = (TextView) v.findViewById(R.id.tv_day);
        mTextTime = (TextView) v.findViewById(R.id.tv_time);
        mTextDoc = (TextView) v.findViewById(R.id.tv_doc);
        mTextNurse = (TextView) v.findViewById(R.id.tv_nurse);
        mTextDiag = (TextView) v.findViewById(R.id.tv_diag);
        mTextHistory = (TextView) v.findViewById(R.id.tv_history);

        // 初始化病人信息
        initData();

    }

    /**
     * 显示当前病患信息
     */
    public void initData() {
        try {
            if (LoginInfo.patient != null) {
                mTextBed.setText(LoginInfo.patient.getBEDNO());
                mTextInhosid.setText(LoginInfo.patient.getPATIENTHOSID());
                mTextName.setText(LoginInfo.patient.getNAME());
                mTextAge.setText(DateUtil.getAge(LoginInfo.patient
                        .getBIRTHDAY()) + "岁");
                mTextSex.setText(LoginInfo.patient.getSEX());
                if (!"".equals(LoginInfo.patient.getBIRTHDAY())) {
                    mTextBorn.setText(LoginInfo.patient.getBIRTHDAY()
                            .split(" ")[0]);
                }
                mTextPre.setText("¥ " + LoginInfo.patient.getPRECOST());
                mTextBalance.setText("¥ " + LoginInfo.patient.getBALANCE());
                mTextWay.setText(LoginInfo.patient.getMEDICARETYPE());
                mTextPhone.setText(LoginInfo.patient.getCONTACT());

                mTextInDate.setText(LoginInfo.patient.getPATIENTINHOSDATE());
                mTextIllness.setText(LoginInfo.patient.getILLNESSSTATE());
                if (!TextUtils.isEmpty(LoginInfo.patient.getPATIENTINHOSDATE())) {
                    int days = DateUtil.daysBetween(DateUtil.StringToDate(
                            LoginInfo.patient.getPATIENTINHOSDATE(), "yyyy-MM-dd"),
                            new Date());
                    mTextDay.setText(days + "天");
                }
                mTextTime.setText(LoginInfo.patient.getPATIENTINTIMES());
                mTextDoc.setText(LoginInfo.patient.getDOCTOR());
                mTextNurse.setText(LoginInfo.patient.getNURSE());
                mTextDiag.setText(LoginInfo.patient.getDIAGNOSIS());
                mTextHistory.setText("");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
