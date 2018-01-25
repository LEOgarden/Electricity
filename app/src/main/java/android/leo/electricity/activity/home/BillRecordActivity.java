package android.leo.electricity.activity.home;

import android.leo.electricity.MyApplication;
import android.leo.electricity.adapter.BillListAdapter;
import android.leo.electricity.bean.Bill;
import android.leo.electricity.bean.UserID;
import android.leo.electricity.presenter.IBillListPresenter;
import android.leo.electricity.presenter.presenterImpl.BIllListPresenterImpl;
import android.leo.electricity.utils.ConCla;
import android.leo.electricity.utils.Properties;
import android.leo.electricity.view.IBillView;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.leo.electricity.R;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class BillRecordActivity extends AppCompatActivity implements View.OnClickListener,
        IBillView{
    private LinearLayout finishBillRecord;
    private RecyclerView billListView;
    private IBillListPresenter billListPresenter;
    private BillListAdapter billListAdapter;
    private List<Bill> billList = new ArrayList<>();
    private RecyclerView.LayoutManager mLayoutManager;

    Handler billHandler = new Handler(){ //处理账单界面传入的数据
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    List<Bill> bills = (List<Bill>) msg.obj;
                    Log.w("billsA", msg.obj.toString());
                    setBillAdapter(bills);
                    break;
            }
        }
    };

    private void setBillAdapter(List<Bill> bills){
        billListAdapter = new BillListAdapter(bills, this);
        mLayoutManager = new LinearLayoutManager(this);
        billListView.setLayoutManager(mLayoutManager);
        billListView.setAdapter(billListAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_record);
        initView();
        UserID userID = (UserID) getIntent().getSerializableExtra("recordUserID");
        String customerId = userID.getHUH();
        String path = ConCla.getConCla(Properties.WEB_IP, Properties.WEB_PORT, Properties.PROJECT_NAME,
                Properties.POWER_LINK, Properties.QUERYCHARGE_ACTION);
        billListPresenter = new BIllListPresenterImpl(this);
        billListPresenter.loadBillList(path, customerId, MyApplication.token);
    }

    private void initView(){
        finishBillRecord = (LinearLayout) findViewById(R.id.finish_bill_record);
        finishBillRecord.setOnClickListener(this);
        billListView = (RecyclerView) findViewById(R.id.bill_view);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.finish_bill_record:
                finish();
                break;
        }
    }

    @Override
    public void showBillList(final List<Bill> bills){
        new Thread(new Runnable(){
            @Override
            public void run(){
                Message msg = new Message();
                msg.obj = bills;
                msg.what = 1;
                billHandler.sendMessage(msg);
            }
        }).start();
    }
}
