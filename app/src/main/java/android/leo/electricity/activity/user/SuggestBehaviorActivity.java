package android.leo.electricity.activity.user;

import android.content.Intent;
import android.leo.electricity.adapter.BehaviorListAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.leo.electricity.R;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.StringTokenizer;

public class SuggestBehaviorActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,View.OnClickListener{
    private ListView behavior_l;
    private List<String> list;
    private BehaviorListAdapter behaviorListAdapter;
    private TextView type_choice;
    private String text;
    private ImageView finish_behavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest_behivor);
        init();
        list = getIntent().getStringArrayListExtra("behavior");
        text = getIntent().getStringExtra("type");
        setTextView(text);
        behaviorListAdapter = new BehaviorListAdapter(this, list);
        setAdapter();
    }

    private void setTextView(String text) {
        if ("表扬".equals(text)){
            type_choice.setText("选择表扬类型");
        }else if ("建议".equals(text)){
            type_choice.setText("选择建议类型");
        }else if ("意见".equals(text)){
            type_choice.setText("选择意见类型");
        }
    }

    private void setAdapter() {
        behavior_l.setAdapter(behaviorListAdapter);
    }

    private void init() {
        behavior_l = (ListView) findViewById(R.id.behaviorL);
        type_choice = (TextView) findViewById(R.id.typeChoice);
        finish_behavior = (ImageView) findViewById(R.id.finishBehavior);
        behavior_l.setOnItemClickListener(this);
        finish_behavior.setOnClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String behavior = list.get(position);
        Intent intent = new Intent(this, SuggestActivity.class);
        intent.putExtra("behavior", behavior);
        intent.putExtra("type", text);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.finishBehavior:
                finish();
                break;
        }
    }
}
