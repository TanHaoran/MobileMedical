package com.thr.mobilemedical.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.thr.mobilemedical.R;
import com.thr.mobilemedical.bean.Patient;
import com.thr.mobilemedical.bean.Reminder;
import com.thr.mobilemedical.constant.LoginInfo;
import com.thr.mobilemedical.receiver.AlarmReceiver;
import com.thr.mobilemedical.utils.DateUtil;
import com.thr.mobilemedical.utils.GsonUtil;
import com.thr.mobilemedical.utils.L;
import com.thr.mobilemedical.view.MyAlertDialog;
import com.thr.mobilemedical.view.SelectPopupWindow;
import com.thr.mobilemedical.view.TimePopupWindow;
import com.thr.mobilemedical.view.TitleBar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@ContentView(R.layout.activity_new_reminder)
public class NewReminderActivity extends Activity {


    public enum TYPE {
        NEW,
        EDIT
    }

    private TYPE mType = TYPE.NEW;

    private Reminder mReminder;


    @ViewInject(R.id.title_bar)
    private TitleBar mTitleBar;


    private SelectPopupWindow mPatientSelectWindow;
    private SelectPopupWindow mItemSelectWindow;
    private SelectPopupWindow mModeSelectWindow;
    private TimePopupWindow mTimeWindow;

    private int mPatientIndex;
    private int mItemIndex;
    private int mModeIndex;
    private List<String> mPatientList;
    private List<String> mItemList;
    private List<String> mModeList;

    @ViewInject(R.id.tv_bed)
    private TextView mTextBed;
    @ViewInject(R.id.tv_item)
    private TextView mTextItem;
    @ViewInject(R.id.tv_mode)
    private TextView mTextMode;
    @ViewInject(R.id.tv_start_time)
    private TextView mTextStart;
    @ViewInject(R.id.tv_end_time)
    private TextView mTextEnd;

    private DbUtils mDbUtils;

    private AlarmManager mAlerAlarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().addActivity(this);
        init();
        initView();
    }

    private void init() {
        String type = getIntent().getStringExtra("type");
        if (type.equals("edit")) {
            mType = TYPE.EDIT;
            mReminder = (Reminder) getIntent().getSerializableExtra("reminder");
        } else {
            mType = TYPE.NEW;
        }
        mAlerAlarmManager = (AlarmManager) getSystemService(Service.ALARM_SERVICE);
    }

    private void initView() {

        mTitleBar.setOnLeftClickListener(new TitleBar.OnLeftClickListener() {
            @Override
            public void onLeftClick(View v) {
                finish();
            }
        });

        mTimeWindow = new TimePopupWindow(this);
        mPatientSelectWindow = new SelectPopupWindow(this);
        mItemSelectWindow = new SelectPopupWindow(this);
        mModeSelectWindow = new SelectPopupWindow(this);
        // 床位的选择
        mPatientList = new ArrayList<String>();
        if (GsonUtil.hasData(LoginInfo.PATIENT_ALL)) {
            for (Patient p : LoginInfo.PATIENT_ALL) {
                mPatientList.add(p.getBEDNO() + "床    " + p.getNAME());
            }
        }
        mPatientSelectWindow.setItems(mPatientList);
        mPatientSelectWindow.setOnEnsureClickListener(new SelectPopupWindow.OnEnsureClickListener() {
            @Override
            public void onEnsureClick() {
                mPatientIndex = mPatientSelectWindow.getSelectIndex();
                mTextBed.setText(mPatientList.get(mPatientIndex));
            }
        });
        // 护理项目的选择
        mItemList = new ArrayList<String>();
        mItemList.add("血糖");
        mItemList.add("血压");
        mItemList.add("血氧");
        mItemList.add("体温");
        mItemSelectWindow.setItems(mItemList);
        mItemSelectWindow.setOnEnsureClickListener(new SelectPopupWindow.OnEnsureClickListener() {
            @Override
            public void onEnsureClick() {
                mItemIndex = mItemSelectWindow.getSelectIndex();
                mTextItem.setText(mItemList.get(mItemIndex));
            }
        });
        // 提醒模式的选择
        mModeList = new ArrayList<String>();
        mModeList.add("不重复");
        mModeList.add("每1分钟");
        mModeList.add("每5分钟");
        mModeList.add("每10分钟");
        mModeSelectWindow.setItems(mModeList);
        mModeSelectWindow.setOnEnsureClickListener(new SelectPopupWindow.OnEnsureClickListener() {
            @Override
            public void onEnsureClick() {
                mModeIndex = mModeSelectWindow.getSelectIndex();
                mTextMode.setText(mModeList.get(mModeIndex));
            }
        });
        if (mReminder != null) {
            setText(mReminder);
        }
    }


    @OnClick(R.id.tv_bed)
    public void onBed(View v) {
        mPatientSelectWindow.show(findViewById(R.id.ll_main));
    }

    @OnClick(R.id.tv_item)
    public void onItem(View v) {
        mItemSelectWindow.show(findViewById(R.id.ll_main));
    }

    @OnClick(R.id.tv_mode)
    public void onMode(View v) {
        mModeSelectWindow.show(findViewById(R.id.ll_main));
    }


    @OnClick({R.id.tv_start_time, R.id.tv_end_time})
    public void onTime(View v) {
        if (v.getId() == R.id.tv_start_time) {
            // 如果此时有数据就显示已经保存的数据
            String start = mTextStart.getText().toString();
            if (!TextUtils.isEmpty(start) && start.split(":").length == 2) {
                int startH = Integer.parseInt(start.split(":")[0]);
                int startM = Integer.parseInt(start.split(":")[1]);
                mTimeWindow.show(findViewById(R.id.ll_main), startH, startM);
            } else {
                mTimeWindow.show(findViewById(R.id.ll_main));
            }

            mTimeWindow.setSureClickListener(new TimePopupWindow.SureClickListener() {

                @Override
                public void onSureClick() {
                    String s = mTimeWindow.getContent();
                    if (s.split(" ").length == 2) {
                        s = s.split(" ")[1];
                        s = s.substring(0, s.length() - 3);
                        mTextStart.setText(s);
                    }
                }
            });
        } else if (v.getId() == R.id.tv_end_time) {
            // 如果此时有数据就显示已经保存的数据
            String end = mTextEnd.getText().toString();
            if (!TextUtils.isEmpty(end) && end.split(":").length == 2) {
                String endH = end.split(":")[0];
                String endM = end.split(":")[1];
                mTimeWindow.show(findViewById(R.id.ll_main), Integer.parseInt(endH), Integer.parseInt(endM));
            } else {
                mTimeWindow.show(findViewById(R.id.ll_main));
            }
            mTimeWindow.setSureClickListener(new TimePopupWindow.SureClickListener() {

                @Override
                public void onSureClick() {
                    String s = mTimeWindow.getContent();
                    if (s.split(" ").length == 2) {
                        s = s.split(" ")[1];
                        s = s.substring(0, s.length() - 3);
                        mTextEnd.setText(s);
                    }
                }
            });
        }
    }

    @OnClick(R.id.btn_sure)
    public void onSure(View v) {
        if (checkReminderInfoNotNull()) {
            saveReminder();
        } else {
            new MyAlertDialog(this, "请填写完整提醒").show();
        }
    }

    /**
     * 检测所有信息是否都填写
     *
     * @return
     */
    private boolean checkReminderInfoNotNull() {
        return !(TextUtils.isEmpty(mTextBed.getText().toString()) || TextUtils.isEmpty(mTextItem.getText().toString()) ||
                TextUtils.isEmpty(mTextStart.getText().toString()) || TextUtils.isEmpty(mTextEnd.getText().toString()) ||
                TextUtils.isEmpty(mTextMode.getText().toString()));
    }

    /**
     * 保存一个提醒到数据库
     */
    private void saveReminder() {
        mDbUtils = DbUtils.create(this);
        Reminder r = new Reminder();
        String bed = "";
        String name = "";
        String level = "";
        String sex = "";
        int age = 0;
        if (LoginInfo.PATIENT_ALL != null && LoginInfo.PATIENT_ALL.get(mPatientIndex) != null) {
            bed = LoginInfo.PATIENT_ALL.get(mPatientIndex).getBEDNO();
            name = LoginInfo.PATIENT_ALL.get(mPatientIndex).getNAME();
            level = setLevelData();
            if (LoginInfo.PATIENT_ALL.get(mPatientIndex).getBIRTHDAY() != null) {
                age = DateUtil.getAge(LoginInfo.PATIENT_ALL.get(mPatientIndex).getBIRTHDAY());
            }
            sex = LoginInfo.PATIENT_ALL.get(mPatientIndex).getSEX();
        }
        String item = mItemList.get(mItemIndex);
        String startTime = mTextStart.getText().toString();
        String endTime = mTextEnd.getText().toString();
        int mode = 0;
        if (mModeIndex == 0) {
            mode = 0;
        } else if (mModeIndex == 1) {
            mode = 1;
        } else if (mModeIndex == 2) {
            mode = 5;
        } else if (mModeIndex == 3) {
            mode = 10;
        }
        r.setBed(bed);
        r.setName(name);
        r.setLevel(level);
        r.setAge(age);
        r.setSex(sex);
        r.setItem(item);
        r.setStartTime(startTime);
        r.setEndTime(endTime);
        r.setMode(mode);
        try {
            if (mReminder != null) {
                mReminder.setBed(bed);
                mReminder.setName(name);
                mReminder.setLevel(level);
                mReminder.setAge(age);
                mReminder.setSex(sex);
                mReminder.setItem(item);
                mReminder.setStartTime(startTime);
                mReminder.setEndTime(endTime);
                mReminder.setMode(mode);
                mDbUtils.update(mReminder, null);
//                new MyAlertDialog(this, "修改提醒项成功").show();
            } else {
                mDbUtils.saveBindingId(r);
//                new MyAlertDialog(this, "添加提醒项成功").show();
            }
            // 保存闹钟
            setAlert(startTime, mode);
            clearText();
        } catch (DbException e) {
            e.printStackTrace();
            new MyAlertDialog(this, "编辑提醒项失败").show();
        }
    }

    /**
     * 保存闹钟
     */
    private void setAlert(String startTime, int repeatTime) {
        // 指定启动AlarmActivity组件
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("reminder", mReminder);
        // 创建PendingIntent对象
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, 0);
        // 获取时和分
        String sh = startTime.split(":")[0];
        String sm = startTime.split(":")[1];

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        // 根据用户选择时间来设置Calendar对象
        c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(sh));
        c.set(Calendar.MINUTE, Integer.parseInt(sm));
        // 设置AlarmManager将在Calendar对应的时间启动指定组件
        L.i("当前时间为：" + System.currentTimeMillis());
        L.i("时间设置为：" + c.getTimeInMillis());
        // 如果设置间隔大于0，就表示需要重复定时提醒，否则不重复
        if (repeatTime > 0) {
            mAlerAlarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
        } else {
            mAlerAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), repeatTime, pi);
        }
        // 显示闹铃设置成功的提示信息
        new MyAlertDialog(this, "提醒设置成功").show();
    }

    /**
     * 根据护理等级设置提醒项的文字
     *
     * @return
     */
    private String setLevelData() {
        String result = "III";
        String level = LoginInfo.PATIENT_ALL.get(mPatientIndex).getNURSELEVEL();
        if ("一级护理".equals(level)) {
            result = "I";
        } else if ("二级护理".equals(level)) {
            result = "II";
        } else if ("三级护理".equals(level)) {
            result = "III";
        } else if ("特级护理".equals(level)) {
            result = "特";
        }
        return result;
    }


    @OnClick(R.id.btn_cancel)
    public void onCancel(View v) {

        clearText();
    }

    private void clearText() {
        mTextBed.setText("");
        mTextItem.setText("");
        mTextStart.setText("");
        mTextMode.setText("");
        mTextEnd.setText("");
    }

    private void setText(Reminder reminder) {
        mTextBed.setText(reminder.getBed());
        mTextItem.setText(reminder.getItem());
        mTextStart.setText(reminder.getStartTime());
        mTextMode.setText(reminder.getMode());
        mTextEnd.setText(reminder.getEndTime());
    }

}
