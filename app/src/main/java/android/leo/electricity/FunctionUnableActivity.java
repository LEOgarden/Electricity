package android.leo.electricity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FunctionUnableActivity extends AppCompatActivity {

    private TextView activityTitle;
    private LinearLayout finishUnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function_unable);
        activityTitle = (TextView) findViewById(R.id.activity_title);
        finishUnable = (LinearLayout) findViewById(R.id.finish_unable);
        finishUnable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        String title = getIntent().getStringExtra("title");
        activityTitle.setText(title);
    }
}
