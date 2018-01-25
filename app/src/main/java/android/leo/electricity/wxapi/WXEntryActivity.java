package android.leo.electricity.wxapi;

import android.content.Intent;
import android.leo.electricity.utils.Constants;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.leo.electricity.R;
import android.view.View;
import android.widget.Button;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler, View.OnClickListener{

    private static String TAG = "WXEntryActivity" ;
    private static final int TIMELINE_SUPPORTED_VERSION = 0x21020001;
    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;

    /**
     * 案例
     * @param savedInstanceState
     */
    private Button regBtn;
    private Button gotoSendBnt;
    private Button gotoPayBtn;
    private Button gotoFavBtn;
    private Button launchWxBtn;
    private Button checkTimeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxentry);
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID, false);
        initView();
    }

    private void initView() {
        regBtn = (Button) findViewById(R.id.reg_btn);
        gotoSendBnt = (Button) findViewById(R.id.goto_send_btn);
        gotoPayBtn = (Button) findViewById(R.id.goto_pay_btn);
        gotoFavBtn = (Button) findViewById(R.id.goto_fav_btn);
        launchWxBtn = (Button) findViewById(R.id.launch_wx_btn);
        checkTimeBtn = (Button) findViewById(R.id.check_timeline_supported_btn);
        regBtn.setOnClickListener(this);
        gotoSendBnt.setOnClickListener(this);
        gotoPayBtn.setOnClickListener(this);
        gotoFavBtn.setOnClickListener(this);
        launchWxBtn.setOnClickListener(this);
        checkTimeBtn.setOnClickListener(this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        
    }

    @Override
    public void onResp(BaseResp baseResp) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.reg_btn:
                // 将该app注册到微信
                api.registerApp(Constants.APP_ID);
                break;
            case R.id.goto_send_btn:
                /*startActivity(new Intent(WXEntryActivity.this, SendToWXActivity.class));
                finish();*/
                break;
            case R.id.goto_pay_btn:
                break;
            case R.id.goto_fav_btn:
                break;
            case R.id.launch_wx_btn:
                break;
            case R.id.check_timeline_supported_btn:
                break;
        }
    }
}
