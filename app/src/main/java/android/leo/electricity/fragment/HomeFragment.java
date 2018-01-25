package android.leo.electricity.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.leo.electricity.FunctionUnableActivity;
import android.leo.electricity.MyApplication;
import android.leo.electricity.R;
import android.leo.electricity.activity.LoginActivity;
import android.leo.electricity.activity.home.ArrearageUserListActivity;
import android.leo.electricity.activity.home.BindActivity;
import android.leo.electricity.activity.home.CheckActivity;
import android.leo.electricity.activity.home.NoticeActivity;
import android.leo.electricity.activity.home.PowerUserListActivity;
import android.leo.electricity.activity.home.RecordUserListActivity;
import android.leo.electricity.activity.user.DepartmentListActivity;
import android.leo.electricity.activity.usepower.ApplyActivity;
import android.leo.electricity.adapter.AdViewPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements ViewPager.OnPageChangeListener,
        View.OnTouchListener, View.OnClickListener{
    private ViewPager adViewPager;
    private AdViewPagerAdapter adapter;
    private List<ImageView> images;
    private List<View> dots;
    //轮播图片默认位置
    private final int DEFAULT_POSITION = 0;
    //当前轮播图片位置
    private int currentPosition = DEFAULT_POSITION;
    //记录上一次点的位置
    private int nextPosition = 1;
    private final int FAKE_BANNER_SIZE = 100;
    private Timer mtimer;
    private Button electricityAndCharge;//电费电量按钮
    private Button bill;//电费记录按钮
    private Button notice;//停电公告按钮
    private Button check;//用户查询按钮
    private Button serverPoint;//服务网点
    private Button operation;//业务办理
    private Button arrearage;//欠费记录
    private RelativeLayout bind_num_r;//绑定用户
    private Button fastPay;

    TimerTask mTimerTask = new TimerTask(){
        @Override
        public void run(){
            if(!mIsUserTouched){
                currentPosition = nextPosition;
                nextPosition = (nextPosition + 1) % imageIds.length;
                if (getActivity() == null)
                    return;
                    //更新ui
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adViewPager.setCurrentItem(currentPosition, false);
                            setImageViewList(currentPosition);
                        }
                    });
            }
        }
    };
    //图片id
    private int[] imageIds = new int[]{
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4
    };

    private boolean mIsUserTouched = false;

    public HomeFragment(){
        images = new ArrayList<>();
        dots = new ArrayList<>();
        adapter = new AdViewPagerAdapter(images);
        mtimer = new Timer();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        adViewPager = (ViewPager) view.findViewById(R.id.ad_viewpager);
        //将图片添加到imageview中
        imageViewAddBac();
        //显示小圆点
        dots.add(view.findViewById(R.id.dot0));
        dots.add(view.findViewById(R.id.dot1));
        dots.add(view.findViewById(R.id.dot2));
        dots.add(view.findViewById(R.id.dot3));
        adViewPager.setAdapter(adapter);
        adViewPager.setOnTouchListener(this);
        //轮播更新
        mtimer.schedule(mTimerTask, 3000, 3000);
        electricityAndCharge = (Button) view.findViewById(R.id.electricityAndCharge);
        electricityAndCharge.setOnClickListener(this);
        bill = (Button) view.findViewById(R.id.bill);
        bill.setOnClickListener(this);
        notice = (Button) view.findViewById(R.id.notice);
        notice.setOnClickListener(this);
        check = (Button) view.findViewById(R.id.check);
        check.setOnClickListener(this);
        serverPoint = (Button) view.findViewById(R.id.severPoint);
        serverPoint.setOnClickListener(this);
        operation = (Button) view.findViewById(R.id.operation);
        operation.setOnClickListener(this);
        arrearage = (Button) view.findViewById(R.id.arrearage);
        arrearage.setOnClickListener(this);
        bind_num_r = (RelativeLayout) view.findViewById(R.id.bind_num_r);
        bind_num_r.setOnClickListener(this);
        fastPay = (Button) view.findViewById(R.id.fastPay);
        fastPay.setOnClickListener(this);
        return view;
    }

    private void imageViewAddBac(){
        for(int i = 0; i < imageIds.length; i++){
            ImageView imagView = new ImageView(MyApplication.getInstance());
            imagView.setBackgroundResource(imageIds[i]);
            images.add(imagView);
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1){

    }

    @Override
    public void onPageSelected(int position){
        currentPosition = position % imageIds.length;
        mtimer.schedule(mTimerTask, 3000, 3000);
    }

    private void setImageViewList(int position){
        position %= imageIds.length;
        //遍历dots重置src为normal
        for(View dot : dots){
            dot.setBackgroundResource(R.drawable.dot);
        }
        dots.get(position).setBackgroundResource(R.drawable.dot_color);
    }

    @Override
    public void onPageScrollStateChanged(int i){

    }

    @Override
    public boolean onTouch(View v, MotionEvent event){
        int action = event.getAction();
        if(action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE){
            mIsUserTouched = true;
        }else if(action == MotionEvent.ACTION_UP){
            mIsUserTouched = false;
        }
        return false;
    }

    @Override
    public void onClick(View v){
        Intent intent;
        switch(v.getId()){
            case R.id.electricityAndCharge:
                if (MyApplication.token == null){
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }else {
                    intent = new Intent(getActivity(), PowerUserListActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.bill:
                if (MyApplication.token == null){
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }else {
                    intent = new Intent(getActivity(), RecordUserListActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.notice:
                intent = new Intent(getActivity(), NoticeActivity.class);
                startActivity(intent);
                break;
            case R.id.check:
                if (MyApplication.token == null){
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }else {
                    intent = new Intent(getActivity(), CheckActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.severPoint:
                if (MyApplication.token == null){
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }else {
                    intent = new Intent(getActivity(), DepartmentListActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.operation:
                if (MyApplication.token == null){
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }else {
                    intent = new Intent(getActivity(), ApplyActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.arrearage:
                if (MyApplication.token == null){
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }else {
                    intent = new Intent(getActivity(), ArrearageUserListActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.bind_num_r:
                if (MyApplication.token == null){
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }else {
                    intent = new Intent(getActivity(), BindActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.fastPay:
                if (MyApplication.token == null){
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }else {
                    intent = new Intent(getActivity(), FunctionUnableActivity.class);
                    intent.putExtra("title", "快速缴费");
                    startActivity(intent);
                }
                break;
        }
    }
}