package com.thr.mobilemedical.receiver;

import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.thr.mobilemedical.R;
import com.thr.mobilemedical.bean.Reminder;
import com.thr.mobilemedical.utils.DateUtil;

import java.io.IOException;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String TAG = "AlarmReceiver";
    KeyguardManager.KeyguardLock kl;
    PowerManager.WakeLock wl;

    /*
     * (non-Javadoc)
     *
     * @see android.content.BroadcastReceiver#onReceive(android.content.Context,
     * android.content.Intent)
     */
    @Override
    public void onReceive(Context context, Intent data) {


        Reminder reminder = (Reminder) data.getSerializableExtra("reminder");
        String end = reminder.getEndTime();
        end += DateUtil.getYesterdayYMD() + " " + end + ":00";
        // 如果已经超出提醒的时间范围，就返回
        if (DateUtil.compareDate(end, DateUtil.getYMDHMS()) < 0) {
            return;
        }
        Log.i(TAG, "闹钟时间到了");
        // get the system audio manager
        AudioManager am = (AudioManager) context
                .getSystemService(Context.AUDIO_SERVICE);

        final MediaPlayer player = new MediaPlayer();
        if (am.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
            // get the alarm uri to show later
            Uri alert = RingtoneManager
                    .getDefaultUri(RingtoneManager.TYPE_ALARM);

            try {
                // set data source
                player.setDataSource(context, alert);
            } catch (IllegalArgumentException e1) {
                e1.printStackTrace();
            } catch (SecurityException e1) {
                e1.printStackTrace();
            } catch (IllegalStateException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            // set type
            player.setAudioStreamType(AudioManager.STREAM_ALARM);
            player.setLooping(true);
            try {
                player.prepare();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            player.start();
        }

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_alert, null);
        Button b = (Button) view.findViewById(R.id.btn);
        TextView medText = (TextView) view.findViewById(R.id.tv_content);
        medText.setText("闹钟时间到了");

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.getWindow()
                .setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.show();
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                player.stop();
                dialog.dismiss();

            }
        });


        // 获取电源管理器对象
        PowerManager pm = (PowerManager) context
                .getSystemService(Context.POWER_SERVICE);
        // 获取PowerManager.WakeLock对象，后面的参数表示同时传入两个值，最后的是调试用的Tag
        wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP
                | PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");
        // 点亮屏幕
        wl.acquire();

        KeyguardManager km = (KeyguardManager) context
                .getSystemService(Context.KEYGUARD_SERVICE);
        // 初始化键盘锁，可以锁定或解开键盘锁
        KeyguardManager.KeyguardLock kl = km.newKeyguardLock("");
        // 禁用显示键盘锁定
        kl.disableKeyguard();
    }

}