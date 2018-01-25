package android.leo.electricity.activity.user;

import android.leo.electricity.MyApplication;
import android.leo.electricity.utils.ConCla;
import android.leo.electricity.utils.Md5;
import android.leo.electricity.utils.OkHttpUtil;
import android.leo.electricity.utils.Properties;
import android.leo.electricity.utils.UserValUtil;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.leo.electricity.R;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ModifyActivity extends AppCompatActivity implements View.OnClickListener{

    private LinearLayout modifyBack;
    private TextView modifyPhone;
    private TextView modifyOldPwd;
    private EditText modifyNewPwd;
    private EditText confirmNewPwd;
    private Button btConfirmChange;
    private String info;
    private Handler mHandler = new Handler(){
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what){
                case 10000:
                    Toast.makeText(ModifyActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        initView();
        String phone = MyApplication.phone;
        StringBuilder builder = new StringBuilder(phone);
        builder.replace(3, 7, "····");
        modifyPhone.setText(builder);
    }

    private void initView() {
        modifyBack = (LinearLayout) findViewById(R.id.modifyBack);
        modifyPhone = (TextView) findViewById(R.id.modify_phone);
        modifyNewPwd = (EditText) findViewById(R.id.modify_new_pwd);
        confirmNewPwd = (EditText) findViewById(R.id.modify_confirm_new_pwd);
        btConfirmChange = (Button) findViewById(R.id.bt_confirm_change);
        modifyBack.setOnClickListener(this);
        btConfirmChange.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.modifyBack:
                finish();
                break;
            case R.id.bt_confirm_change:
                String newPwd = modifyNewPwd.getText().toString().trim();
                String confirmPwd = confirmNewPwd.getText().toString().trim();
                if (!"".equals(newPwd)){
                    if (!(newPwd.length() < 6)){
                        if (!("".equals(confirmPwd))){
                            if (newPwd.equals(confirmPwd)){
                                final String[] msg = new String[1];
                                modifyPwd(MyApplication.phone, Md5.getMD5(newPwd), msg);
                                modifyNewPwd.setText("");
                                confirmNewPwd.setText("");
                            }else {
                                Toast.makeText(this, "确认密码与新密码不符", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(this, "确认密码与新密码不符", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(this, "密码长度不小于6位", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this, "新密码不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 更改密码
     * @param phone 手机号
     * @param newPwd 新密码
     */
    private void modifyPwd(String phone, String newPwd, final String[] msg) {
        String path = ConCla.getConCla(Properties.WEB_IP, Properties.WEB_PORT, Properties.PROJECT_NAME,
                Properties.USER_LINK, Properties.UPDATEPASSWORD_ACTION);
        OkHttpUtil.updatePassword(path, phone, newPwd, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseStr = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(responseStr);
                    Message msg = new Message();
                    msg.obj = jsonObject.getString("msg");
                    msg.what = 10000;
                    mHandler.sendMessage(msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
