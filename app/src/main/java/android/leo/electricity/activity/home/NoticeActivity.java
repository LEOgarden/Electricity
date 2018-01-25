package android.leo.electricity.activity.home;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.leo.electricity.R;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NoticeActivity extends AppCompatActivity implements View.OnClickListener{

    private LinearLayout backNotice;
    private TextView noticeText;
    private TextView chooseDistrict;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        initView();
    }

    private void initView(){
        backNotice = (LinearLayout) findViewById(R.id.backNotice);
        noticeText = (TextView) findViewById(R.id.noticeText);
        chooseDistrict = (TextView) findViewById(R.id.choose_district);
        backNotice.setOnClickListener(this);
        chooseDistrict.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.backNotice:
                finish();
                break;
            case R.id.choose_district:
                dialogChoice();
                break;
            default:
                break;
        }
    }

    private void dialogChoice() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择区域");
        
    }
}
