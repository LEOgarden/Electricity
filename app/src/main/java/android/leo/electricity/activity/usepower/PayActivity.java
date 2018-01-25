package android.leo.electricity.activity.usepower;


import android.content.Intent;
import android.leo.electricity.MyApplication;
import android.leo.electricity.adapter.CheckUserAdapter;
import android.leo.electricity.bean.UserID;
import android.leo.electricity.presenter.ICheckUserPresenter;
import android.leo.electricity.presenter.presenterImpl.CheckUserPresenterImpl;
import android.leo.electricity.utils.ConCla;
import android.leo.electricity.utils.Properties;
import android.leo.electricity.view.CustomerSpinnerWindow;
import android.leo.electricity.view.ICheckUserView;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.leo.electricity.R;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.List;


public class PayActivity extends AppCompatActivity implements View.OnClickListener ,
        PopupWindow.OnDismissListener, AdapterView.OnItemClickListener, ICheckUserView{
    private LinearLayout finishPay;
    private ImageView spinnerMoreUser;
    private CustomerSpinnerWindow customerSpinnerWindow;
    private RelativeLayout payUserRelativeLayout;
    private static final String TAG = "PayActivity";
    private List<UserID> userIDs;
    private CheckUserAdapter checkUserAdapter;
    private ICheckUserPresenter checkUserPresenter;
    private EditText inputNum;
    private Button payNext;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            userIDs = (List<UserID>) msg.obj;
            setAdapter(userIDs);
        }
    };

    private void setAdapter(List<UserID> userIDs) {
        checkUserAdapter = new CheckUserAdapter(this, userIDs);
        customerSpinnerWindow.setAdapter(checkUserAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        init();
        checkUserPresenter = new CheckUserPresenterImpl(this);
        String path = ConCla.getConCla(Properties.WEB_IP, Properties.WEB_PORT, Properties.PROJECT_NAME,
                Properties.USER_LINK, Properties.GETBOUNDCUSTOMERS_ACTION);
        String token = MyApplication.token;
        checkUserPresenter.checkUser(path, token);
    }

    private void init() {
        finishPay = (LinearLayout) findViewById(R.id.finishPay);
        spinnerMoreUser = (ImageView) findViewById(R.id.spinner_more_user);
        payUserRelativeLayout = (RelativeLayout) findViewById(R.id.pay_user_relativeLayout);
        inputNum = (EditText) findViewById(R.id.edt_input_numb);
        payNext = (Button) findViewById(R.id.pay_next);
        finishPay.setOnClickListener(this);
        spinnerMoreUser.setOnClickListener(this);
        payNext.setOnClickListener(this);
        customerSpinnerWindow = new CustomerSpinnerWindow(this, this);//context, listener
        customerSpinnerWindow.setOnDismissListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.finishPay:
                finish();
                break;
            case R.id.spinner_more_user:
                if (customerSpinnerWindow != null){
                    if (customerSpinnerWindow.isShowing()){
                        onDismiss();
                    }
                    customerSpinnerWindow.setWidth(payUserRelativeLayout.getWidth());
                    customerSpinnerWindow.setHeight(payUserRelativeLayout.getWidth()/2);
                    customerSpinnerWindow.showAsDropDown(payUserRelativeLayout);
                }
                break;
            case R.id.pay_next:
                if (inputNum.getText().toString().trim().length() != 0) {
                    Intent intent = new Intent(this, PaymentActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(this, "请输入正确的户号", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onDismiss() {
        if (customerSpinnerWindow != null){
            customerSpinnerWindow.dismiss();
            backgroundAlpha(1f);
        }
    }

    private void backgroundAlpha(float v) {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = v; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        UserID userID = userIDs.get(position);
        inputNum.setText(userID.getHUH());
        customerSpinnerWindow.dismiss();
    }

    @Override
    public void showCheckUser(Object obj) {
        Message message = new Message();
        message.obj = obj;
        message.what = 10009;
        handler.sendMessage(message);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(this);
    }
}
