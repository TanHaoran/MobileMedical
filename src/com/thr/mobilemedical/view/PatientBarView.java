package com.thr.mobilemedical.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thr.mobilemedical.R;
import com.thr.mobilemedical.constant.LoginInfo;
import com.thr.mobilemedical.constant.Method;
import com.thr.mobilemedical.utils.DateUtil;

/**
 * Created by Jerry on 2016/5/18.
 */
public class PatientBarView extends LinearLayout {

    private Context mContext;

    private LinearLayout leftLayout;
    private LinearLayout rightLayout;
    private TextView textBed;
    private TextView textName;
    private TextView textAge;
    private TextView textLevel;

    private OnPatientChangeListener onPatientChangeListener;

    public interface OnPatientChangeListener {
        void onPatientChange(int position);
    }

    public  void setOnPatientChangeListener(OnPatientChangeListener onPatientChangeListener) {
        this.onPatientChangeListener = onPatientChangeListener;
    }

    public PatientBarView(Context context) {
        this(context, null);
    }

    public PatientBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PatientBarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mContext = context;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_patient_bar, this);

        leftLayout = (LinearLayout) findViewById(R.id.ll_leftpatient);
        rightLayout = (LinearLayout) findViewById(R.id.ll_rightpatient);
        textBed = (TextView)findViewById(R.id.tv_bed);
        textName = (TextView)findViewById(R.id.tv_name);
        textAge = (TextView)findViewById(R.id.tv_age);
        textLevel = (TextView)findViewById(R.id.tv_nursinglevel);

        // 点击向左
        leftLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginInfo.patientIndex--;
                if (LoginInfo.patientIndex < 0) {
                    LoginInfo.patientIndex = 0;
                    MyAlertDialog dialog = new MyAlertDialog(mContext,
                            R.string.info_first_patient);
                    dialog.show();
                } else {
                    LoginInfo.patient = LoginInfo.PATIENT_ALL
                            .get(LoginInfo.patientIndex);
                    setBarData();
                    if (onPatientChangeListener != null){
                        onPatientChangeListener.onPatientChange(LoginInfo.patientIndex);
                    }
                }
            }
        });

        // 点击向右
        rightLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginInfo.patientIndex++;
                if (LoginInfo.patientIndex > LoginInfo.PATIENT_ALL.size() - 1) {
                    LoginInfo.patientIndex = LoginInfo.PATIENT_ALL.size() - 1;
                    MyAlertDialog dialog = new MyAlertDialog(mContext,
                            R.string.info_last_patient);
                    dialog.show();
                } else {
                    LoginInfo.patient = LoginInfo.PATIENT_ALL
                            .get(LoginInfo.patientIndex);
                    setBarData();
                    if (onPatientChangeListener != null){
                        onPatientChangeListener.onPatientChange(LoginInfo.patientIndex);
                    }
                }
            }
        });
    }

    /**
     * 切换标题的内容
     */
    public  void setBarData() {
        if (LoginInfo.patient != null) {
            textBed.setText(LoginInfo.patient.getBEDNO());
            textName.setText(LoginInfo.patient.getNAME());
            textAge.setText(DateUtil.getAge(LoginInfo.patient.getBIRTHDAY())
                    + "岁");
            String level = formatNursingLevel(LoginInfo.patient.getNURSELEVEL());
            textLevel.setText(level);
        }
    }


    /**
     * 格式化护理等级
     *
     * @param nurselevel
     * @return
     */
    public String formatNursingLevel(String nurselevel) {
        String level = "III";
        if (Method.isNursingSpecial(nurselevel)) {
            level = "特";
        } else if (Method.isNursingOne(nurselevel)) {
            level = "I级";
        } else if (Method.isNursingTwo(nurselevel)) {
            level = "II级";
        } else {
            level = "III级";
        }
        return level;
    }
}
