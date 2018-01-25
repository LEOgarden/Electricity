package android.leo.electricity.activity.home;

import android.content.Intent;
import android.leo.electricity.MyApplication;
import android.leo.electricity.adapter.CheckUserAdapter;
import android.leo.electricity.bean.UserHH;
import android.leo.electricity.bean.UserID;
import android.leo.electricity.presenter.ICheckUserPresenter;
import android.leo.electricity.presenter.presenterImpl.CheckUserPresenterImpl;
import android.leo.electricity.utils.ConCla;
import android.leo.electricity.utils.Properties;
import android.leo.electricity.view.ICheckUserView;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.leo.electricity.R;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class CheckActivity extends AppCompatActivity implements View.OnClickListener,
        ICheckUserView, AdapterView.OnItemClickListener{
    private LinearLayout finishCheck;
    private String path;
    private List<UserID> userIDList;
    private ICheckUserPresenter checkUserPresenter;
    private ListView customerListView;
    private CheckUserAdapter checkUserAdapter;
    private FloatingActionButton addUser;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 9527:
                    userIDList = (List<UserID>) msg.obj;
                    setAdapter(userIDList);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        path = ConCla.getConCla(Properties.WEB_IP, Properties.WEB_PORT, Properties.PROJECT_NAME,
                Properties.USER_LINK, Properties.GETBOUNDCUSTOMERS_ACTION);
        init();
        checkUserPresenter = new CheckUserPresenterImpl(this);
        checkUserPresenter.checkUser(path, MyApplication.token);
}

    private void setAdapter(List<UserID> userIDList) {
        if (userIDList.size() != 0) {
            checkUserAdapter = new CheckUserAdapter(this, userIDList);
            customerListView.setAdapter(checkUserAdapter);
        }else {
            Toast.makeText(this, "未绑定户号", Toast.LENGTH_SHORT).show();
        }
    }

    private void init() {
        finishCheck = (LinearLayout) findViewById(R.id.finish_check);
        customerListView = (ListView) findViewById(R.id.customer_check_list);
        addUser = (FloatingActionButton) findViewById(R.id.add_user);
        finishCheck.setOnClickListener(this);
        customerListView.setOnItemClickListener(this);
        addUser.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.finish_check:
                finish();
                break;
            case R.id.add_user:
                Intent intent = new Intent(this, BindActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void showCheckUser(Object obj) {
        if ("".equals(obj.toString())){
        }else {
            userIDList = (List<UserID>) obj;
            Message message = new Message();
            message.obj = obj;
            message.what = 9527;
            handler.sendMessage(message);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        UserID userID = userIDList.get(position);
        Intent intent = new Intent(this, CustomerInfoActivity.class);
        intent.putExtra("userID", userID);
        startActivity(intent);
    }
}
