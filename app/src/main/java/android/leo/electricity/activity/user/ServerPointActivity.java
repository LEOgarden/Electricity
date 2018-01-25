package android.leo.electricity.activity.user;

import android.leo.electricity.bean.ServicePoint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.leo.electricity.R;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ServerPointActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView pointbh;
    private TextView pointName;
    private TextView pointType;
    private TextView pointAddress;
    private TextView pointContent;
    private TextView pointTime;
    private TextView pointPhone;
    private LinearLayout finishServerPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_point);
        ServicePoint servicePoint = (ServicePoint) getIntent().getSerializableExtra("serverpoint");
        init();
        setData(servicePoint);
    }

    private void setData(ServicePoint servicePoint) {
        pointbh.setText(servicePoint.getPointNo());
        pointName.setText(servicePoint.getPointName());
        pointType.setText(servicePoint.getPointType());
        pointContent.setText(servicePoint.getContent());
        pointAddress.setText(servicePoint.getAddress());
        pointTime.setText(servicePoint.getServerTime());
        pointPhone.setText(servicePoint.getTel());
    }

    private void init() {
        pointbh = (TextView) findViewById(R.id.pointbh);
        pointName = (TextView) findViewById(R.id.pointName);
        pointType = (TextView) findViewById(R.id.pointType);
        pointAddress = (TextView) findViewById(R.id.pointAddress);
        pointContent = (TextView) findViewById(R.id.serverContent);
        pointTime = (TextView) findViewById(R.id.openDuration);
        pointPhone = (TextView) findViewById(R.id.pointPhone);
        finishServerPoint = (LinearLayout) findViewById(R.id.finish_server_point);
        finishServerPoint.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.finish_server_point:
                finish();
                break;
        }
    }
}
