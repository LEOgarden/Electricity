package android.leo.electricity.activity.user;

import android.app.Activity;
import android.content.Intent;
import android.leo.electricity.MyApplication;
import android.leo.electricity.adapter.DepartmentListAdapter;
import android.leo.electricity.adapter.ServerPointAdapter;
import android.leo.electricity.bean.Department;
import android.leo.electricity.bean.ServicePoint;
import android.leo.electricity.presenter.IDepartmentPresenter;
import android.leo.electricity.presenter.IServerPresenter;
import android.leo.electricity.presenter.presenterImpl.DepartmentPresenterImpl;
import android.leo.electricity.presenter.presenterImpl.ServerPresenterImpl;
import android.leo.electricity.utils.ConCla;
import android.leo.electricity.utils.Properties;
import android.leo.electricity.view.IDepartmentView;
import android.leo.electricity.view.IServerView;
import android.leo.electricity.view.SpinnerWindow;
import android.os.Bundle;
import android.leo.electricity.R;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.List;

public class DepartmentListActivity extends Activity implements View.OnClickListener, IDepartmentView,
        AdapterView.OnItemClickListener, PopupWindow.OnDismissListener, IServerView {

    private ListView serverPointListView;
    private IDepartmentPresenter departmentPresenter;
    private IServerPresenter serverPresenter;
    private LinearLayout backImage;
    private DepartmentListAdapter departmentListAdapter;
    private List<Department> departmentList;//部门选择列表
    private SpinnerWindow spinnerWindow;//部门选择弹窗
    private ServerPointAdapter serverPointAdapter;
    private List<ServicePoint> serverPointList;
    private ImageButton imageButton;
    private TextView departmentTextView;
    private LinearLayout departmentLinear;
    /*private String url = "http://192.168.0.63:8080/ws/Power/powerService/getDepartMent?token=";
    private String url2 = "http://192.168.0.63:8080/ws/Power/powerService/getPowerArea";*/

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 1:
                    departmentList = (List<Department>) msg.obj;
                    setDepartmentAdapter(departmentList);
                    break;
                case 2:
                    serverPointList = (List<ServicePoint>) msg.obj;
                    setServerPointAdapter(serverPointList);
                    break;
            }
        }
    };

    /**
     * 网点配适
     * @param serverPoints
     */
    private void setServerPointAdapter(List<ServicePoint> serverPoints){
        serverPointAdapter = new ServerPointAdapter(this, serverPoints);
        serverPointListView.setAdapter(serverPointAdapter);
    }

    /**
     * 部门列表适配
     * @param departmentList
     */
    private void setDepartmentAdapter(List<Department> departmentList){
        departmentListAdapter = new DepartmentListAdapter(this, departmentList);
        spinnerWindow.setAdapter(departmentListAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_list);
        String url = ConCla.getConCla(Properties.WEB_IP, Properties.WEB_PORT, Properties.PROJECT_NAME,
                Properties.POWER_LINK, Properties.GETDEPARTMENT_ACTION);
        departmentPresenter = new DepartmentPresenterImpl(this);
        departmentPresenter.obtainDepartment(url + "?token=" + MyApplication.token, MyApplication.token);
        initView();
        setListener();
    }

    private void setListener() {
        serverPointListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ServicePoint servicePoint = null;
                servicePoint = serverPointList.get(position);
                Intent intent = new Intent(DepartmentListActivity.this, ServerPointActivity.class);
                intent.putExtra("serverpoint", servicePoint);
                startActivity(intent);
            }
        });
    }

    private void initView(){
        backImage = (LinearLayout) findViewById(R.id.back_Image);
        serverPointListView = (ListView) findViewById(R.id.serverPoint_list);
        imageButton = (ImageButton) findViewById(R.id.popup_button);
        departmentTextView = (TextView) findViewById(R.id.department_textView);
        departmentLinear = (LinearLayout) findViewById(R.id.department_linear);
        spinnerWindow = new SpinnerWindow(this, this);//context, listener
        spinnerWindow.setOnDismissListener(this);
        backImage.setOnClickListener(this);
        imageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.back_Image:
                finish();
                break;
            case R.id.popup_button:
                if (spinnerWindow != null){
                    if (spinnerWindow.isShowing()){
                        onDismiss();
                    }
                    spinnerWindow.setWidth(departmentLinear.getWidth());
                    spinnerWindow.setHeight(departmentLinear.getWidth());
                    spinnerWindow.showAsDropDown(departmentLinear);
                    imageButton.setImageDrawable(getDrawable(R.drawable.up));
                }
                break;
        }

    }

    /**
     * 设置屏幕透明度
     * @param bgAlpha
     */
    private void backgroundAlpha(float bgAlpha){
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    /**
     * 显示部门列表
     * @param departmentList
     */
    @Override
    public void showDepartmentView(final List<Department> departmentList){
        this.departmentList = departmentList;
        new Thread(){
            @Override
            public void run() {
                Message msg = new Message();
                msg.obj = departmentList;
                msg.what = 1;
                handler.sendMessage(msg);
            }
        }.start();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        String url = ConCla.getConCla(Properties.WEB_IP, Properties.WEB_PORT, Properties.PROJECT_NAME,
                Properties.POWER_LINK, Properties.GETPOWERAREA_ACTION);
        Department department;
        department = departmentList.get(position);
        departmentTextView.setText(departmentList.get(position).getDanwmc());
        departmentTextView.setTextSize(12);
        serverPresenter = new ServerPresenterImpl(this);
        serverPresenter.obtainServerPoint(url + "?departmentid=" + department.getDanwbh(), MyApplication.token);
        onDismiss();
    }

    /**
     * spninner消失
     */
    @Override
    public void onDismiss(){
        if (spinnerWindow != null){
            spinnerWindow.dismiss();
            imageButton.setImageDrawable(getDrawable(R.drawable.down));
            backgroundAlpha(1f);
        }
    }

    /**
     * 显示网点列表
     * @param serverPointList
     */
    @Override
    public void showServerPointView(final List<ServicePoint> serverPointList){
        this.serverPointList = serverPointList;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.obj = serverPointList;
                msg.what = 2;
                handler.sendMessage(msg);
            }
        }).start();
    }
}
