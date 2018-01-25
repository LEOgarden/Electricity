package android.leo.electricity.activity.user;

import android.content.Intent;
import android.graphics.Color;
import android.leo.electricity.FunctionUnableActivity;
import android.leo.electricity.MyApplication;
import android.leo.electricity.activity.RegisterActivity;
import android.leo.electricity.utils.ConCla;
import android.leo.electricity.utils.OkHttpUtil;
import android.leo.electricity.utils.Properties;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.leo.electricity.R;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ChangePwdValCodeActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView nextStep;
    private TextView sendChangeValCode;
    private final ChangeValCodeHandler mHandler = new ChangeValCodeHandler(this);
    private boolean stopThread = false;
    private LinearLayout finishValCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd_val_code);
        initView();
        sendChangeValCode.setText("获取验证");
        sendChangeValCode.setTextColor(Color.parseColor("#2828ff"));
        sendChangeValCode.setClickable(true);
    }

    private void sendChangeValCodeClick() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(!stopThread) {
                    for (int i = 59; i >= 0; i--) {
                        Message msg = mHandler.obtainMessage();
                        msg.arg1 = i;
                        mHandler.sendMessage(msg);
                        if (i == 0){
                            stopThread = true;
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    private void obtainChangeValCode(String phone) {
        String path = ConCla.getConCla(Properties.WEB_IP, Properties.WEB_PORT, Properties.PROJECT_NAME,
                Properties.USER_LINK, Properties.GETSECURITYCODEOFRESETPSD_ACTION);
        OkHttpUtil.changeValCode(path, phone, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

    }

    private void initView() {
        nextStep = (TextView) findViewById(R.id.bt_next_step);
        sendChangeValCode = (TextView) findViewById(R.id.bt_send_change_val_code);
        finishValCode = (LinearLayout) findViewById(R.id.finish_valCode);
        nextStep.setOnClickListener(this);
        sendChangeValCode.setOnClickListener(this);
        finishValCode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_next_step:
                Intent intent = new Intent(this, ModifyActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.bt_send_change_val_code:
                obtainChangeValCode(MyApplication.phone);
                sendChangeValCodeClick();
                break;
            case R.id.finish_valCode:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        stopThread = true;
    }

    public class ChangeValCodeHandler extends Handler{
        private final WeakReference<ChangePwdValCodeActivity> weakReference;

        public ChangeValCodeHandler(ChangePwdValCodeActivity activity) {
            this.weakReference = new WeakReference<ChangePwdValCodeActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ChangePwdValCodeActivity activity = weakReference.get();
            if (activity != null){
                switch (msg.what){
                    case 0:
                        if (msg.arg1 == 0){
                            sendChangeValCode.setText("获取验证");
                            sendChangeValCode.setTextColor(Color.parseColor("#2828ff"));
                            sendChangeValCode.setClickable(true);

                        }else {
                            sendChangeValCode.setText(msg.arg1 + "S");
                            sendChangeValCode.setTextColor(Color.parseColor("#696969"));
                            sendChangeValCode.setClickable(false);
                        }
                        break;
                }
            }
        }
    }
}
