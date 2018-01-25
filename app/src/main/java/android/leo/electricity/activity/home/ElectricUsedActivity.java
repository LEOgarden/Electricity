package android.leo.electricity.activity.home;

import android.content.Intent;
import android.leo.electricity.MyApplication;
import android.leo.electricity.bean.ElectricPreMonth;
import android.leo.electricity.bean.UserID;
import android.leo.electricity.presenter.IUseElectricPresenter;
import android.leo.electricity.presenter.presenterImpl.UseElectricPresenterImpl;
import android.leo.electricity.utils.ConCla;
import android.leo.electricity.utils.Properties;
import android.leo.electricity.view.ElectricityBoardView;
import android.leo.electricity.view.IElectricUsedView;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.leo.electricity.R;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ElectricUsedActivity extends AppCompatActivity implements View.OnClickListener,
        IElectricUsedView{
    private RelativeLayout toDetail;
    private ImageView finishUsed;
    private IUseElectricPresenter useElectricPresenter;
    private int year;
    private String month;
    private List<ElectricPreMonth> electricPreMonthList;
    private ElectricityBoardView electricityBoardView;
    private TextView electricUserName;
    private TextView electricUserId;
    private TextView electricUserAddress;
    private TextView electricDegree;
    private TextView electricGrow;
    private TextView electricFee;
    private TextView feeGrow;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 9529:
                    electricPreMonthList = (List<ElectricPreMonth>) msg.obj;
                    setData();
                    break;
            }
        }
    };

    private void setData() {
        if (electricPreMonthList.size() == 1){
            electricityBoardView.setQuantityWithAnim(Float.valueOf(electricPreMonthList.get(electricPreMonthList.size()-1)
                    .getJIFDL()));
            electricUserId.setText(electricPreMonthList.get(electricPreMonthList.size()-1).
                    getHUH());
            electricUserName.setText(electricPreMonthList.get(electricPreMonthList.size()-1).
                    getHUM());
            //electricUserAddress.setText(electricPreMonthList.get(i).);
            electricDegree.setText(electricPreMonthList.get(electricPreMonthList.size()-1).
                    getJIFDL());
            electricGrow.setText("0");
            electricFee.setText(electricPreMonthList.get(electricPreMonthList.size()-1).
                    getYINGSJE());
            feeGrow.setText("0");
        }else if (electricPreMonthList.size() >1){
            electricityBoardView.setQuantityWithAnim(40.0f);
            electricUserId.setText(electricPreMonthList.get(electricPreMonthList.size()-1).
                    getHUH());
            electricUserName.setText(electricPreMonthList.get(electricPreMonthList.size()-1).
                    getHUM());
            //electricUserAddress.setText(electricPreMonthList.get(i).);
            electricDegree.setText(electricPreMonthList.get(electricPreMonthList.size()-1).
                    getJIFDL());
            electricGrow.setText(""+(Float.parseFloat(electricPreMonthList.get(electricPreMonthList.size()-1).getJIFDL())-
                    Float.parseFloat(electricPreMonthList.get(electricPreMonthList.size()-2).getJIFDL())));
            electricFee.setText(electricPreMonthList.get(electricPreMonthList.size()-1).
                    getYINGSJE());
            feeGrow.setText(""+(Float.parseFloat(electricPreMonthList.get(electricPreMonthList.size()-1).getYINGSJE())-
                    Float.parseFloat(electricPreMonthList.get(electricPreMonthList.size()-2).getYINGSJE())));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electric_used);
        initView();
        UserID userID = (UserID) getIntent().getSerializableExtra("powerUserID");
        String path = ConCla.getConCla(Properties.WEB_IP, Properties.WEB_PORT, Properties.PROJECT_NAME,
                Properties.POWER_LINK, Properties.QUERYELECTRIC_ACTION);
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = year+""+(c.get(Calendar.MONTH)+1);
        useElectricPresenter = new UseElectricPresenterImpl(this);
        useElectricPresenter.loadUseElectricInfo(path, userID.getHUH(), year, MyApplication.token);
    }

    private void initView(){
        toDetail = (RelativeLayout) findViewById(R.id.toDetail);
        finishUsed = (ImageView) findViewById(R.id.finish_used);
        finishUsed = (ImageView) findViewById(R.id.finish_used);
        electricityBoardView = (ElectricityBoardView) findViewById(R.id.board);
        electricUserId = (TextView) findViewById(R.id.electric_user_id);
        electricUserName = (TextView) findViewById(R.id.electric_user_name);
        electricUserAddress = (TextView) findViewById(R.id.electric_user_address);
        electricDegree = (TextView) findViewById(R.id.electric_degree);
        electricGrow = (TextView) findViewById(R.id.electric_grow);
        electricFee = (TextView) findViewById(R.id.electric_fee);
        feeGrow = (TextView) findViewById(R.id.fee_grow);
        toDetail.setOnClickListener(this);
        finishUsed.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.toDetail:
                Intent intent = new Intent(this, AnnualDetailActivity.class);
                intent.putExtra("ElectricPreMonthList", (Serializable) electricPreMonthList);
                startActivity(intent);
                break;
            case R.id.finish_used:
                finish();
                break;
        }
    }

    @Override
    public void showElectricUsed(List<ElectricPreMonth> electricPreMonths) {
        Message message = new Message();
        message.obj = electricPreMonths;
        message.what = 9529;
        handler.sendMessage(message);
    }
}
