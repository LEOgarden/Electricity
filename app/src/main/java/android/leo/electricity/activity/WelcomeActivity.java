package android.leo.electricity.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.leo.electricity.utils.NetUtil;
import android.os.Bundle;
import android.leo.electricity.R;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.widget.RelativeLayout;

public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //showView();
        Integer time = 2000;    //设置等待时间，单位为毫秒
        Handler handler = new Handler();
        //当计时结束时，跳转至主界面
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                finish();
            }
        }, time);
    }

    @Override
    public void onNetChange(int netMobile) {
        super.onNetChange(netMobile);
        if (netMobile == -1){
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提醒");
            builder.setMessage("当前网络不可用");
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //跳转到系统网络设置的界面
                    Intent intent = new Intent();
                    intent.setClassName("com.android.settings", "com.android.settings.Settings");
                    startActivity(intent);
                }
            }).show();
        }else{
            //判断是否连接服务器
            if ("success".equals(NetUtil.isLinkedNetWeb())){
                //3秒跳转
                countDownTime(3000);
            }
        }
    }

    private void countDownTime(long i) {
        //倒计时
        new CountDownTimer(i, 100){

            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
            }
        }.start();
    }
}