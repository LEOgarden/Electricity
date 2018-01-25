package android.leo.electricity.activity.user;

import android.content.Context;
import android.content.DialogInterface;
import android.leo.electricity.MyApplication;
import android.leo.electricity.bean.UserID;
import android.leo.electricity.presenter.ICheckUserPresenter;
import android.leo.electricity.presenter.presenterImpl.CheckUserPresenterImpl;
import android.leo.electricity.utils.ConCla;
import android.leo.electricity.utils.OkHttpUtil;
import android.leo.electricity.utils.Properties;
import android.leo.electricity.view.ICheckUserView;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.leo.electricity.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class UnbindActivity extends AppCompatActivity implements View.OnClickListener,
        ICheckUserView{
    private List<UserID> datas;
    private UnbindAdapter unbindListAdapter;
    private ListView unbindListView;
    private LinearLayout finishUnbind;
    private ICheckUserPresenter checkUserPresenter;

    private Handler mHandler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 9999:
                    datas = (List<UserID>) msg.obj;
                    setAdapter(datas);
                    break;
                default:
                    break;
            }
        }

        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what){
                case 10001:
                    String info = (String) msg.obj;
                    Toast.makeText(UnbindActivity.this, info, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private void setAdapter(List<UserID> userIDList) {
        unbindListAdapter = new UnbindAdapter(this, userIDList);
        unbindListView.setAdapter(unbindListAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unbind);
        init();
        datas = new ArrayList<>();
        obtainAllBondedUser(MyApplication.token);
    }

    /**
     * 通过手机号查询改手机号已绑定的户号
     * @param token
     */
    private void obtainAllBondedUser(String token) {
        checkUserPresenter = new CheckUserPresenterImpl(this);
        String path = ConCla.getConCla(Properties.WEB_IP, Properties.WEB_PORT, Properties.PROJECT_NAME,
                Properties.USER_LINK, Properties.GETBOUNDCUSTOMERS_ACTION);
        checkUserPresenter.checkUser(path, token);
    }

    private void init() {
        unbindListView = (ListView) findViewById(R.id.unbind_listView);
        finishUnbind = (LinearLayout) findViewById(R.id.unbindBac);
        finishUnbind.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.unbindBac:
                finish();
                break;
        }
    }

    @Override
    public void showCheckUser(Object obj) {
        if (!"".equals(obj)){
            Message msg = new Message();
            msg.what = 9999;
            msg.obj = obj;
            mHandler.sendMessage(msg);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    public class UnbindAdapter extends BaseAdapter{
        private LayoutInflater mInflater;
        private List<UserID> datas;

        public UnbindAdapter(Context context, List<UserID> userIDList){
            this.mInflater = LayoutInflater.from(context);
            this.datas = userIDList;
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            MyViewHolder myViewholder = null;
            if (convertView == null){
                convertView = mInflater.inflate(R.layout.unbind_item_layout, null);
                myViewholder = new MyViewHolder();
                myViewholder.deleteBtn = (TextView) convertView.findViewById(R.id.btn_delete);
                myViewholder.unbindNum = (TextView) convertView.findViewById(R.id.unbind_user_number);
                myViewholder.unbindName = (TextView) convertView.findViewById(R.id.unbind_username);
                convertView.setTag(myViewholder);
            }else{
                myViewholder = (MyViewHolder) convertView.getTag();
            }
            final UserID data = datas.get(position);
            if(data != null){
                myViewholder.unbindNum.setText(data.getHUH());
                myViewholder.unbindName.setText("(" + data.getHUM() + ")");
            }
            myViewholder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toastDialog(position);
                }
            });
            return convertView;
        }
    }

    /**
     * 弹出对话框
     * @param position
     */
    public void toastDialog(final int position) {
        new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert).setTitle("提示").setMessage("确认删除？").
                setPositiveButton("确定", new DialogInterface.OnClickListener() {//确定按钮
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        UserID userID = datas.get(position);
                        String path = ConCla.getConCla(Properties.WEB_IP, Properties.WEB_PORT, Properties.PROJECT_NAME,
                                Properties.USER_LINK, Properties.UNLOCKUSER_ACTION);
                        OkHttpUtil.unlockUser(path, MyApplication.token, MyApplication.phone, userID.getHUH(), new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                dialog.dismiss();
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                dialog.dismiss();
                                obtainAllBondedUser(MyApplication.token);
                                String responseStr = response.body().string();
                                try {
                                    JSONObject object = new JSONObject(responseStr);
                                    String msg = object.getString("msg");
                                    Message message = new Message();
                                    message.obj = msg;
                                    message.what = 10001;
                                    mHandler.sendMessage(message);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {//取消按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    public final class  MyViewHolder{
        TextView deleteBtn;
        TextView unbindNum;
        TextView unbindName;
    }
}
