package com.thr.mobilemedical.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.thr.mobilemedical.R;
import com.thr.mobilemedical.activity.NewReminderActivity;
import com.thr.mobilemedical.bean.Reminder;
import com.thr.mobilemedical.utils.CommonAdapter;
import com.thr.mobilemedical.utils.ViewHolder;
import com.thr.mobilemedical.view.MyAlertDialog;

import java.util.List;

/**
 * Created by Jerry on 2016/5/23.
 */
public class ReminderAdapter extends CommonAdapter<Reminder> {

    private Context context;
    private DbUtils dbUtils;

    private boolean isEdit = false;


    public ReminderAdapter(Context context, List<Reminder> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        this.context = context;
        dbUtils = DbUtils.create(context);
    }

    @Override
    public void convert(ViewHolder helper, final Reminder item) {
        helper.setText(R.id.tv_bed, item.getBed());
        helper.setText(R.id.tv_name, item.getName());
        helper.setText(R.id.tv_level, item.getLevel());
        helper.setText(R.id.tv_sex, item.getSex());
        helper.setText(R.id.tv_age, String.valueOf(item.getAge()));
        helper.setText(R.id.tv_content, item.getItem());
        helper.setText(R.id.tv_time, item.getStartTime());
        if (isEdit) {
            helper.getView(R.id.iv_delete).setVisibility(View.VISIBLE);
            helper.getView(R.id.iv_edit).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.iv_delete).setVisibility(View.INVISIBLE);
            helper.getView(R.id.iv_edit).setVisibility(View.INVISIBLE);
        }
        // 编辑
        helper.getView(R.id.iv_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isEdit = false;
                Intent intent = new Intent(context, NewReminderActivity.class);
                intent.putExtra("type", "edit");
                intent.putExtra("reminder", item);
                context.startActivity(intent);
            }
        });
        // 删除
        helper.getView(R.id.iv_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    dbUtils.deleteById(Reminder.class, item.getId());
                    new MyAlertDialog(context, "删除成功！").show();
                    List<Reminder> list = dbUtils.findAll(Reminder.class);
                    setDatas(list);
                } catch (DbException e) {
                    e.printStackTrace();
                    new MyAlertDialog(context, "删除失败！").show();
                }
            }
        });
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    public boolean isEdit() {
        return isEdit;
    }
}
