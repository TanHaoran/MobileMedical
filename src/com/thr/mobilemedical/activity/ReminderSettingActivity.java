package com.thr.mobilemedical.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.thr.mobilemedical.R;
import com.thr.mobilemedical.adapter.ReminderAdapter;
import com.thr.mobilemedical.bean.Reminder;
import com.thr.mobilemedical.utils.GsonUtil;
import com.thr.mobilemedical.utils.TopActivityListener;
import com.thr.mobilemedical.utils.TopExitImpl;
import com.thr.mobilemedical.view.LeftMenuPopupWindow;
import com.thr.mobilemedical.view.TitleBar;

import java.util.List;

@ContentView(R.layout.activity_reminder_setting)
public class ReminderSettingActivity extends Activity {


    // 标题栏
    @ViewInject(R.id.title_bar)
    private TitleBar mTitleBar;

    @ViewInject(R.id.list_view)
    private ListView mListView;


    private List<Reminder> mReminderList;

    private ReminderAdapter mAdapter;

    private TopActivityListener mTopListener;
    private LeftMenuPopupWindow mLeftWindow;

    private DbUtils mDbUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().addActivity(this);

        initView();

    }

    private void initView() {
        mLeftWindow = new LeftMenuPopupWindow(this);

        mTitleBar.setOnLeftClickListener(new TitleBar.OnLeftClickListener() {

            @Override
            public void onLeftClick(View v) {
                mLeftWindow.showAtLocation(findViewById(R.id.ll_main),
                        Gravity.BOTTOM, 0, 0);
            }
        });
        mTitleBar.setOnRightClickListener(new TitleBar.OnRightClickListener() {

            @Override
            public void onRightClick(View v) {
                changeRightImageState();
            }
        });

        mDbUtils = DbUtils.create(this);

        // 设置最上层退出处理
        setTopListener(new TopExitImpl());
    }

    /**
     * 根据此时的状态来设置右侧图标的状态
     */
    private void changeRightImageState() {
        if (mAdapter != null) {
            if (mAdapter.isEdit()) {
                mTitleBar.setRightImage(R.drawable.icon_write);
                mAdapter.setEdit(false);
            } else {
                mTitleBar.setRightImage(R.drawable.icon_ok);
                mAdapter.setEdit(true);
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 设置右上角的状态
     */
    private void setRightImageState() {
        if (mAdapter != null) {
            if (mAdapter.isEdit()) {
                mTitleBar.setRightImage(R.drawable.icon_ok);
                mAdapter.setEdit(true);
            } else {
                mTitleBar.setRightImage(R.drawable.icon_write);
                mAdapter.setEdit(false);
            }
            mAdapter.notifyDataSetChanged();
        }
    }


    public void setTopListener(TopActivityListener topListener) {
        mTopListener = topListener;
    }


    @OnClick(R.id.ll_add_reminder)
    public void onAddReminder(View v) {
        Intent intent = new Intent();
        intent.setClass(this, NewReminderActivity.class);
        intent.putExtra("type", "new");
        startActivity(intent);
    }


    @Override
    protected void onResume() {
        super.onResume();
        // 返回的时候需要重置右上角图标的状态
        setRightImageState();
        try {
            mReminderList = mDbUtils.findAll(Reminder.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        mAdapter = new ReminderAdapter(this, mReminderList, R.layout.item_reminder);
        if (GsonUtil.hasData(mReminderList)) {
            mListView.setAdapter(mAdapter);
        }


    }

    @Override
    public void onBackPressed() {
        mTopListener.onExit(this);
    }

}
