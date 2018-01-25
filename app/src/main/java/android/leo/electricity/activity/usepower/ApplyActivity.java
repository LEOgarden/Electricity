package android.leo.electricity.activity.usepower;

import android.graphics.Color;
import android.leo.electricity.MyApplication;
import android.leo.electricity.bean.ApplyInfo;
import android.leo.electricity.utils.ConCla;
import android.leo.electricity.utils.OkHttpUtil;
import android.leo.electricity.utils.Properties;
import android.leo.electricity.utils.UserValUtil;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.leo.electricity.R;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ApplyActivity extends AppCompatActivity implements View.OnClickListener{
    private LinearLayout applyBack;
    private EditText apply_name;
    private EditText apply_tel;
    private EditText apply_address;
    private EditText apply_ic;
    private EditText apply_name_j;
    private EditText apply_tel_j;
    private EditText val_code_editText;
    private TextView apply_val_code;
    private Button submit_apply;
    private final CountDownHandler handler = new CountDownHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);
        init();
    }

    private void init() {
        applyBack = (LinearLayout) findViewById(R.id.applyBac);
        apply_name = (EditText) findViewById(R.id.applyName);
        apply_tel = (EditText) findViewById(R.id.applyTel);
        apply_address = (EditText) findViewById(R.id.applyAddress);
        apply_ic = (EditText) findViewById(R.id.applyIC);
        apply_name_j = (EditText) findViewById(R.id.applyNameJ);
        apply_tel_j = (EditText) findViewById(R.id.applyTelJ);
        submit_apply = (Button) findViewById(R.id.submitApply);
        apply_val_code = (TextView) findViewById(R.id.valCode_apply);
        applyBack.setOnClickListener(this);
        submit_apply.setOnClickListener(this);
        apply_val_code.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.applyBac:
                finish();
                break;
            case R.id.submitApply:
                if (!apply_name.getText().toString().trim().equals("")){
                    if (UserValUtil.valTelp(apply_tel.getText().toString().trim())){
                        if (!apply_address.getText().toString().trim().equals("")){
                            if (!apply_ic.getText().toString().trim().equals("")){
                                if (!apply_name_j.getText().toString().trim().equals("")){
                                    if (UserValUtil.valTelp(apply_tel_j.getText().toString().trim())){
                                        ApplyInfo applyInfo = new ApplyInfo();
                                        applyInfo.setHum(apply_name.getText().toString().trim());
                                        applyInfo.setKehdh(apply_tel.getText().toString().trim());
                                        applyInfo.setYongddz(apply_address.getText().toString().trim());
                                        applyInfo.setZhengjlx("身份证");
                                        applyInfo.setZhengjhm(apply_ic.getText().toString().trim());
                                        applyInfo.setJingbr(apply_name_j.getText().toString().trim());
                                        applyInfo.setJingbrdh(apply_tel_j.getText().toString().trim());
                                        String path = ConCla.getConCla(Properties.WEB_IP, Properties.WEB_PORT,
                                                Properties.PROJECT_NAME, Properties.POWER_LINK, Properties.NEWUSERAPPLY_ACTION);
                                        submitApply(path, applyInfo);
                                    }else {
                                        Toast.makeText(this, "经办人电话格式不正确", Toast.LENGTH_SHORT).show();
                                    }
                                }else {
                                    Toast.makeText(this, "经办人姓名不能为空", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(this, "身份证不能为空", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(this, "用电地址不能为空", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(this, "联系电话格式不正确", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this, "用户姓名不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.valCode_apply:
                countDownClick();
                break;
            default:
                break;
        }
    }

    private void countDownClick() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=59; i>=0; i--){
                    Message msg = new Message();
                    msg.arg1 = i;
                    handler.sendMessage(msg);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void submitApply(final String path, final ApplyInfo applyInfo) {
        OkHttpUtil.newUserApply(path, applyInfo, MyApplication.token, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String msg = jsonObject.getString("msg");
                    Looper.prepare();
                    Toast.makeText(ApplyActivity.this, msg, Toast.LENGTH_SHORT).show();
                    Looper.loop();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public class CountDownHandler extends Handler{
        private final WeakReference<ApplyActivity> reference;

        public CountDownHandler(ApplyActivity activity){
            this.reference = new WeakReference<ApplyActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ApplyActivity applyActivity = reference.get();
            if (applyActivity != null){
                switch (msg.what){
                    case 0:
                        if (msg.arg1 == 0){
                            apply_val_code.setText("获取验证");
                            apply_val_code.setClickable(true);
                            apply_val_code.setBackgroundColor(Color.parseColor("#009bff"));
                        }else {
                            apply_val_code.setText("(" + msg.arg1 + ")" + "重新获取");
                            apply_val_code.setClickable(false);
                            apply_val_code.setBackgroundColor(Color.parseColor("#696969"));
                        }
                        break;
                }
            }
        }
    }

}
