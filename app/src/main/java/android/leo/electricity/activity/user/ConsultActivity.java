package android.leo.electricity.activity.user;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.leo.electricity.R;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ConsultActivity extends AppCompatActivity implements View.OnClickListener{
    private LinearLayout consult_back;
    private Button consult_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult);
        initView();
    }

    private void initView() {
        consult_back = (LinearLayout) findViewById(R.id.consultBac);
        consult_button = (Button) findViewById(R.id.consultBtn);
        consult_back.setOnClickListener(this);
        consult_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.consultBac:
                finish();
                break;
            case R.id.consultBtn:
                Toast.makeText(this, "提交成功", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }
}
