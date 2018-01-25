package android.leo.electricity.utils;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;

/**
 * Created by Leo on 2017/11/23.
 */

public class TimeCount extends CountDownTimer {
    private Button button;

    private TextView textView;

    public TimeCount(TextView textView, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.textView = textView;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        String time = "(" + millisUntilFinished / 1000 + ")";
        setViewInfo(time, "#696969", false);
    }

    private void setViewInfo(String time, String color, boolean isClick) {
        textView.setText(time + "重新发送");
        textView.setClickable(isClick);
        textView.setBackgroundColor(Color.parseColor(color));
    }

    @Override
    public void onFinish() {
       // setViewInfo("获取验证码", "");
    }
}
