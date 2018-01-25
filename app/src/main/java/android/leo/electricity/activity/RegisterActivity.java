package android.leo.electricity.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.leo.electricity.bean.RegisterInfo;
import android.leo.electricity.model.RegisterModel;
import android.leo.electricity.utils.ConCla;
import android.leo.electricity.utils.Md5;
import android.leo.electricity.utils.OkHttpUtil;
import android.leo.electricity.utils.Properties;
import android.leo.electricity.utils.UserValUtil;
import android.os.Bundle;
import android.leo.electricity.R;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.security.PrivilegedAction;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RegisterActivity extends Activity implements View.OnClickListener{

    private EditText registerPhone;
    private EditText registerPwd;
    private EditText valCodeEdit;
    private static TextView captureSms;
    private Button register;
    private FloatingActionButton cancel;
    private String vid;
    private final MyHandler myHandler = new MyHandler(this);
    private Handler mHandler = new Handler(){
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what){
                case 10002:
                    String info = (String) msg.obj;
                    if ("成功".equals(info)){
                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                        registerPhone.setText("");
                        registerPwd.setText("");
                        valCodeEdit.setText("");
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myHandler.removeCallbacksAndMessages(null);
        mHandler.removeCallbacksAndMessages(null);
    }

    private void initView(){
        registerPhone = (EditText) findViewById(R.id.et_phone);
        registerPwd = (EditText) findViewById(R.id.et_password);
        valCodeEdit = (EditText) findViewById(R.id.et_valCode);
        captureSms = (TextView) findViewById(R.id.btn_valCode);
        register = (Button) findViewById(R.id.btn_go);
        cancel = (FloatingActionButton) findViewById(R.id.fab);
        captureSms.setOnClickListener(this);
        register.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.btn_valCode:
                if(!"".equals(registerPhone.getText().toString())){
                    sendMessageClick();
                    gainValCode();
                }else {
                    Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_go:
                String phoneno = registerPhone.getText().toString();
                String password = registerPwd.getText().toString();
                String valCode = valCodeEdit.getText().toString();
                String path = ConCla.getConCla(Properties.WEB_IP, Properties.WEB_PORT, Properties.PROJECT_NAME,
                        Properties.USER_LINK, Properties.REGISTER_ACTION);
                String url = path + "?phoneno=" + phoneno + "&password=" + Md5.getMD5(password) + "&vCode=" + valCode + "&vid=" + vid;
                if(UserValUtil.valTelp(phoneno)){
                    if(password.length() >= 6){
                        userRegister(url, phoneno, password, valCode);
                    }else{
                        Toast.makeText(this, "密码长度最少为6位", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.fab:
                finish();
                break;
            default:
                break;
        }
    }

    private void sendMessageClick() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 59; i >= 0; i--) {
                    Message msg = myHandler.obtainMessage();
                    msg.arg1 = i;
                    myHandler.sendMessage(msg);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void userRegister(String url, String phoneno, String password, String valCode){
        OkHttpUtil.getInstance().registerPost(url, phoneno, password, valCode, new Callback(){
            @Override
            public void onFailure(Call call, IOException e){
                Looper.prepare();
                Toast.makeText(RegisterActivity.this, "服务器繁忙，请稍后再试", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException{
                RegisterInfo registerInfo = new RegisterInfo();
                registerInfo = RegisterModel.getRegisterInfo(response.body().string());
                Message message = new Message();
                message.obj = registerInfo.getMsg();
                message.what = 10002;
                mHandler.sendMessage(message);
            }
        });

    }

    /**
     * 获取验证码
     */
    private void gainValCode(){
        String phoneno = registerPhone.getText().toString();
        String url = ConCla.getConCla(Properties.WEB_IP, Properties.WEB_PORT, Properties.PROJECT_NAME, Properties.USER_LINK, Properties.GETSECURITYCODE_ACTION) + "?phoneno=" + phoneno;
        OkHttpUtil.getInstance().valCodePost(url, phoneno, new Callback(){
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException{
                String body = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(body);
                    JSONObject data = jsonObject.getJSONObject("data");
                    vid = data.getString("vid");
                    String errorNum = data.getString("ErrorNum");
                    Log.d("errorNum", errorNum);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public class MyHandler extends Handler{
        private final WeakReference<RegisterActivity> weakReference;

        public MyHandler(RegisterActivity activity) {
            this.weakReference = new WeakReference<RegisterActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            RegisterActivity activity = weakReference.get();
            if (activity != null){
                switch (msg.what){
                    case 0:
                        if (msg.arg1 == 0){
                            captureSms.setText("获取验证");
                            captureSms.setBackgroundColor(Color.parseColor("#ff4081"));
                            captureSms.setClickable(true);
                        }else {
                            captureSms.setText("(" + msg.arg1 + ")重新获取");
                            captureSms.setBackgroundColor(Color.parseColor("#696969"));
                            captureSms.setClickable(false);
                        }
                        break;
                }
            }
        }
    }
}
