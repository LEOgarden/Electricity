package android.leo.electricity.activity.home;

import android.content.Intent;
import android.leo.electricity.MyApplication;
import android.leo.electricity.bean.ArrearageBean;
import android.leo.electricity.bean.UserID;
import android.leo.electricity.presenter.IArrearagePresenter;
import android.leo.electricity.presenter.presenterImpl.ArrearagePresenterImpl;
import android.leo.electricity.utils.ConCla;
import android.leo.electricity.utils.Properties;
import android.leo.electricity.view.IArrearageView;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.leo.electricity.R;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autonavi.ae.gmap.MapMessage;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class ArrearageActivity extends AppCompatActivity implements View.OnClickListener, IArrearageView{
    private LinearLayout backArrearage;
    private TextView name;
    private TextView customerId;
    private TextView address;
    private TextView point;
    private TextView pointName;
    private TextView sumPay;
    private TextView paid;
    private TextView remain;
    private UserID userID;
    private TextView noData;

    Handler arrearageHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 10004:
                    ArrearageBean arrearageBean = (ArrearageBean) msg.obj;
                    name.setText(arrearageBean.data.HUM);
                    customerId.setText(arrearageBean.data.HUH);
                    address.setText(arrearageBean.data.YONGDDZ);
                    point.setText(arrearageBean.data.DANWBH);
                    pointName.setText(arrearageBean.data.DANWMC);
                    sumPay.setText(arrearageBean.data.YINGSJE + "元");
                    paid.setText(arrearageBean.data.ZANSJE + "元");
                    remain.setText(arrearageBean.data.QIANFHJ + "元");
                    noData.setVisibility(View.GONE);
                    break;
                case 10005:
                    name.setText(userID.getHUM());
                    customerId.setText(userID.getHUH());
                    address.setText("");
                    point.setText("");
                    pointName.setText("");
                    sumPay.setText(0 + "元");
                    paid.setText(0 + "元");
                    remain.setText(0 + "元");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrearage);
        Intent intent = getIntent();
        userID = (UserID) intent.getSerializableExtra("userinfo");
        initView();
        String path = ConCla.getConCla(Properties.WEB_IP, Properties.WEB_PORT, Properties.PROJECT_NAME,
                Properties.POWER_LINK, Properties.QUERYBALABCE_ACTION);
        IArrearagePresenter presenter = new ArrearagePresenterImpl(this);
        presenter.passArrearageInfo(path, userID.getHUH(), MyApplication.token);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        arrearageHandler.removeCallbacksAndMessages(null);
    }

    private void initView() {
        backArrearage = (LinearLayout) findViewById(R.id.finish_arrearage);
        name = (TextView) findViewById(R.id.arrearage_name);
        customerId = (TextView) findViewById(R.id.arrearage_customer_id);
        address = (TextView) findViewById(R.id.arrearage_address);
        point = (TextView) findViewById(R.id.arrearage_point);
        pointName = (TextView) findViewById(R.id.arrearage_point_name);
        sumPay = (TextView) findViewById(R.id.arrearage_need_to_pay);
        paid = (TextView) findViewById(R.id.arrearage_paid);
        remain = (TextView) findViewById(R.id.arrearage_remain);
        noData = (TextView) findViewById(R.id.arrearage_no_data);
        backArrearage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.finish_arrearage:
                finish();
                break;
        }
    }

    @Override
    public void showArrearageInfo(String response) {
        JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
        String msg = jsonObject.get("msg").getAsString();
        Message message = new Message();
        if ("true".equals(msg)) {
            Gson gson = new Gson();
            ArrearageBean arrearageBean = gson.fromJson(response, new TypeToken<ArrearageBean>() {
            }.getType());
            message.obj = arrearageBean;
            message.what = 10004;
            arrearageHandler.sendMessage(message);
        }else {
            message.obj = "没有数据！";
            message.what = 10005;
            arrearageHandler.sendMessage(message);
        }
    }
}
