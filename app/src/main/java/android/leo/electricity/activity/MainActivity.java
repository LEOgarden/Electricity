package android.leo.electricity.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.leo.electricity.MyApplication;
import android.leo.electricity.R;
import android.leo.electricity.fragment.HomeFragment;
import android.leo.electricity.fragment.UseElectricityFragment;
import android.leo.electricity.fragment.UserFragment;
import android.leo.electricity.utils.Constants;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

public class MainActivity extends Activity implements View.OnClickListener{

    private AMapLocationClient locationClient;

    private ImageView locationImageView;
    private TextView locationTextView;
    private RadioGroup radioGroup;
    private RadioButton butHome;
    private RadioButton button2;
    //private RadioButton button3;
    private RadioButton button4;
    private FragmentManager manager;
    private HomeFragment homeFragment;
    private UseElectricityFragment useElectricityFragment;
    private UserFragment userFragment;
    private Button turnToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setFragment();
        initLocation();
    }


    private void setFragment(){
        manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        homeFragment = new HomeFragment();
        useElectricityFragment = new UseElectricityFragment();
        userFragment = new UserFragment();
        transaction.add(R.id.linearLayout, homeFragment,"homeFragment");
        transaction.add(R.id.linearLayout, useElectricityFragment, "useElectricityFragment");
        transaction.add(R.id.linearLayout, userFragment, "userFragment");
        //默认
        radioGroup.check(R.id.button1);
        switchFragment("homeFragment");
        transaction.commit();
    }

    private void initLocation(){
        //初始化locationClient
        locationClient = new AMapLocationClient(MyApplication.getInstance());
        //设置定位参数
        locationClient.setLocationOption(getDefaultOption());
        //开始定位
        startLocation();
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
    }

    /**
     * 开始定位
     */
    private void startLocation(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 启动定位
                locationClient.startLocation();
            }
        }).start();
    }

    /**
     *默认的定位参数
     * @return
     */
    private AMapLocationClientOption getDefaultOption(){
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(true);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        return mOption;
    }

    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener(){
        @Override
        public void onLocationChanged(AMapLocation location){
            if(null != location){

                //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
                if(location.getErrorCode() == 0){
                    String result = location.getCity();
                    locationTextView.setText(result);
                    Log.d("locationTextView", locationTextView.getText().toString());
                }else{
                    Toast.makeText(MainActivity.this, "定位失败", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(MainActivity.this, "定位失败", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyLocation();
    }

    private void destroyLocation() {
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (MyApplication.token != null){
            turnToLogin.setVisibility(View.GONE);
        }else {
            turnToLogin.setVisibility(View.VISIBLE);
        }
    }

    private void initView() {
        locationImageView = (ImageView) this.findViewById(R.id.location_imageview);
        locationTextView = (TextView) this.findViewById(R.id.location_textview);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        butHome = (RadioButton) findViewById(R.id.button1);
        button2 = (RadioButton) findViewById(R.id.button2);
        //button3 = (RadioButton) findViewById(R.id.button3);
        button4 = (RadioButton) findViewById(R.id.button4);
        butHome.setOnClickListener(this);
        button2.setOnClickListener(this);
        //button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        turnToLogin = (Button) findViewById(R.id.turn_to_loginactivity_button);
        if (MyApplication.token == null){
            turnToLogin.setOnClickListener(this);
            turnToLogin.setClickable(true);
            turnToLogin.setText("登录");
        }else {
            turnToLogin.setClickable(false);
            turnToLogin.setText("");
        }

    }

    private void switchFragment(String tag) {
        // 事物
        FragmentTransaction transaction = manager.beginTransaction();
        if("homeFragment".equals(tag)){
            transaction.hide(useElectricityFragment);
            transaction.hide(userFragment);
            transaction.show(homeFragment);
        }
        if ("useElectricityFragment".equals(tag)){
            transaction.hide(homeFragment);
            transaction.hide(userFragment);
            transaction.show(useElectricityFragment);
        }
        if ("userFragment".equals(tag)){
            transaction.hide(homeFragment);
            transaction.hide(useElectricityFragment);
            transaction.show(userFragment);
        }
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button1:
                switchFragment("homeFragment");
                break;
            case R.id.button2:
                switchFragment("useElectricityFragment");
                break;
            case R.id.button4:
                switchFragment("userFragment");
                break;
            case R.id.turn_to_loginactivity_button:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
}
