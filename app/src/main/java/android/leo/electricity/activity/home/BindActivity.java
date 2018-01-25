package android.leo.electricity.activity.home;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.leo.electricity.fragment.FastFragment;
import android.leo.electricity.fragment.HandBindFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.leo.electricity.R;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BindActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView fast;
    private TextView hand;
    private LinearLayout backBind;
    private FragmentManager manager;
    private FastFragment fastFragment;
    private HandBindFragment handBindFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind);
        initView();
        setFragment();
    }

    private void initView() {
        fast = (TextView) findViewById(R.id.fast);
        hand = (TextView) findViewById(R.id.hand);
        backBind = (LinearLayout) findViewById(R.id.finish_bind);
        fast.setOnClickListener(this);
        hand.setOnClickListener(this);
        backBind.setOnClickListener(this);
    }

    private void setFragment(){
        manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        fastFragment = new FastFragment();
        handBindFragment = new HandBindFragment();
        transaction.add(R.id.bind_linear, fastFragment, "fastFragment");
        transaction.add(R.id.bind_linear, handBindFragment, "handBindFragment");
        switchFragment("fastFragment");
        transaction.commit();
    }

    private void switchFragment(String tag) {
        // 事物
        FragmentTransaction transaction = manager.beginTransaction();
        if ("fastFragment".equals(tag)){
            transaction.hide(handBindFragment);
            transaction.show(fastFragment);
        }else if ("handBindFragment".equals(tag)){
            transaction.hide(fastFragment);
            transaction.show(handBindFragment);
        }
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fast:
                switchFragment("fastFragment");
                fast.setTextColor(ContextCompat.getColor(this, R.color.color_green_light));
                fast.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
                hand.setTextColor(ContextCompat.getColor(this, R.color.white));
                hand.setBackgroundColor(ContextCompat.getColor(this, R.color.color_green_light));
                break;
            case R.id.hand:
                switchFragment("handBindFragment");
                fast.setTextColor(ContextCompat.getColor(this, R.color.white));
                fast.setBackgroundColor(ContextCompat.getColor(this, R.color.color_green_light));
                hand.setTextColor(ContextCompat.getColor(this, R.color.color_green_light));
                hand.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
                break;
            case R.id.finish_bind:
                finish();
                break;
        }
    }
}
