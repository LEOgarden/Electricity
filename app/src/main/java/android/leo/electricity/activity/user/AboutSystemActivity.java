package android.leo.electricity.activity.user;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.leo.electricity.R;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class AboutSystemActivity extends AppCompatActivity implements View.OnClickListener{

    private LinearLayout backAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_system);
        initView();
    }

    private void initView() {
        backAbout = (LinearLayout) findViewById(R.id.back_about);
        backAbout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_about:
                finish();
                break;
        }
    }
}
