package android.leo.electricity.activity.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.leo.electricity.R;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SuggestActivity extends AppCompatActivity implements View.OnClickListener{
    private RelativeLayout suggest_type_r;
    private TextView suggest_type_t;
    private RelativeLayout behavior_r;
    private TextView behavior_t;
    private ImageView finish_suggest;
    private Button suggest_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest);
        init();
    }

    private void setTextView(Intent intent) {
        String type = intent.getStringExtra("type");
        suggest_type_t.setText(type);
        String behavior = intent.getStringExtra("behavior");
        if ("表扬".equals(suggest_type_t.getText().toString().trim())){
            if (!(behavior == null)) {
                behavior_t.setText(behavior);
            }else {
                behavior_t.setText("请选择表扬类型");
            }
        }else if("建议".equals(suggest_type_t.getText())){
            if (!(behavior== null)) {
                behavior_t.setText(behavior);
            }else {
                behavior_t.setText("请选择建议类型");
            }
        }else if ("意见".equals(suggest_type_t.getText())){
            if (!(behavior == null)) {
                behavior_t.setText(behavior);
            }else {
                behavior_t.setText("请选择意见类型");
            }
        }
    }

    private void init() {
        suggest_type_r = (RelativeLayout) findViewById(R.id.suggestTypeR);
        suggest_type_t = (TextView) findViewById(R.id.suggestionTypeT);
        behavior_r = (RelativeLayout) findViewById(R.id.suggestBehaviorR);
        behavior_t = (TextView) findViewById(R.id.suggestBehaviorT);
        finish_suggest = (ImageView) findViewById(R.id.suggestBack);
        suggest_button = (Button) findViewById(R.id.suggestBtn);
        suggest_type_r.setOnClickListener(this);
        behavior_r.setOnClickListener(this);
        finish_suggest.setOnClickListener(this);
        suggest_button.setOnClickListener(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setTextView(intent);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.suggestTypeR:
                intent = new Intent(this, TypeActivity.class);
                startActivity(intent);
                break;
            case R.id.suggestBehaviorR:
                List<String> list = new ArrayList<>();
                String text = suggest_type_t.getText().toString().trim();
                if ("表扬".equals(suggest_type_t.getText())){
                    list.clear();
                    list.add("优质服务表扬");
                    list.add("风行建设表扬");
                    list.add("供电服务");
                    list.add("电网建设");
                    list.add("其他表扬");
                }else if ("建议".equals(suggest_type_t.getText())){
                    list.clear();
                    list.add("电网建设建议");
                    list.add("服务质量建议");
                    list.add("用户批评");
                    list.add("营业业务");
                    list.add("其他");
                }else if ("意见".equals(suggest_type_t.getText())){
                    list.clear();
                    list.add("供电业务");
                    list.add("供电服务");
                    list.add("电网建设");
                    list.add("其他意见");
                }
                intent = new Intent(this, SuggestBehaviorActivity.class);
                intent.putStringArrayListExtra("behavior", (ArrayList<String>) list);
                intent.putExtra("type", text);
                startActivity(intent);
                break;
            case R.id.suggestBack:
                finish();
                break;
            case R.id.suggestBtn:
                Toast.makeText(this, "提交成功", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }
}
