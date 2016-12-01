package com.thr.mobilemedical.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.thr.mobilemedical.R;
import com.thr.mobilemedical.view.TitleBar;

@ContentView(R.layout.activity_choose_execution)
public class ChooseExecutionActivity extends Activity {

    @ViewInject(R.id.title_bar)
    private TitleBar mTitleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().addActivity(this);
        initView();

    }

    private void initView() {
        mTitleBar.setOnLeftClickListener(new TitleBar.OnLeftClickListener() {
            @Override
            public void onLeftClick(View v) {
                finish();
            }
        });
    }


    @OnClick({R.id.ll_drop_execute, R.id.iv_nursing_execute, R.id.iv_nursing_patrol, R.id.ll_reminder_setting})
    public void onItemChoose(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.ll_drop_execute:
                intent.setClass(this, DropexecuteActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_nursing_execute:

                break;
            case R.id.iv_nursing_patrol:
                intent.setClass(this, NursingpatrolActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_reminder_setting:
                intent.setClass(this, NewReminderActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
