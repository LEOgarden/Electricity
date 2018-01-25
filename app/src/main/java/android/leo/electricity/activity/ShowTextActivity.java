package android.leo.electricity.activity;

import android.content.Intent;
import android.leo.electricity.model.ITxtModel;
import android.leo.electricity.model.modelImpl.TxtModelImpl;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.leo.electricity.R;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class ShowTextActivity extends AppCompatActivity{

    private Toolbar substanceToolbar;
    private String title;
    private int pathId;
    private ITxtModel txtModel;
    private TextView textSubstance;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_text);
        Intent intent = this.getIntent();
        title = intent.getStringExtra("title");
        pathId = intent.getIntExtra("path", 0);
        initView();
        setElements();
        setListener();
    }

    private void setListener() {
        substanceToolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
    }

    private void setElements(){
        substanceToolbar.setNavigationIcon(R.drawable.back3);
        substanceToolbar.setTitle(title);
        String substance = txtModel.getTxtFileToString(pathId);
        textSubstance.setText(substance);
    }

    private void initView(){
        txtModel = new TxtModelImpl();
        substanceToolbar = (Toolbar) findViewById(R.id.substance_toolbar);
        textSubstance = (TextView) findViewById(R.id.substance);
    }
}
