package android.leo.electricity.activity.home;

import android.leo.electricity.bean.UserID;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.leo.electricity.R;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomerInfoActivity extends AppCompatActivity implements View.OnClickListener{
    private UserID userID;
    private TextView huh_info;
    private TextView hum_info;
    private TextView tel_info;
    private TextView address_info;
    private LinearLayout customerInfoBac;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_info);
        userID = (UserID) getIntent().getSerializableExtra("userID");
        init();
        setText();
    }

    private void setText() {
        huh_info.setText(userID.getHUH());
        hum_info.setText(userID.getHUM());
        tel_info.setText(userID.getCUIFDH());
        address_info.setText(userID.getYONGDDZ());
    }

    private void init() {
        huh_info = (TextView) findViewById(R.id.customer_info_huh);
        hum_info = (TextView) findViewById(R.id.customer_info_hum);
        tel_info = (TextView) findViewById(R.id.customer_info_tel);
        address_info = (TextView) findViewById(R.id.customer_info_address);
        customerInfoBac = (LinearLayout) findViewById(R.id.customer_info_bac);
        customerInfoBac.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.customer_info_bac:
                finish();
                break;
        }
    }
}
