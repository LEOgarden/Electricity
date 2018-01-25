package android.leo.electricity.activity.user;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.leo.electricity.R;
        import android.view.View;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.TextView;

public class TypeActivity extends AppCompatActivity implements View.OnClickListener{
    private LinearLayout prise_l;
    private LinearLayout advise_l;
    private LinearLayout opinion_l;
    private TextView prise_T;
    private TextView advise_T;
    private TextView opinion_T;
    private ImageView type_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);
        init();
    }

    private void init() {
        prise_l = (LinearLayout) findViewById(R.id.priseL);
        advise_l = (LinearLayout) findViewById(R.id.adviseL);
        opinion_l = (LinearLayout) findViewById(R.id.opinionL);
        prise_T = (TextView) findViewById(R.id.priseT);
        advise_T = (TextView) findViewById(R.id.adviseT);
        opinion_T = (TextView) findViewById(R.id.opinionT);
        type_back = (ImageView) findViewById(R.id.typeBack);
        prise_l.setOnClickListener(this);
        advise_l.setOnClickListener(this);
        opinion_l.setOnClickListener(this);
        type_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, SuggestActivity.class);;
        String type;
        switch (v.getId()){
            case R.id.priseL:
                type = prise_T.getText().toString().trim();
                intent.putExtra("type", type);
                startActivity(intent);
                finish();
                break;
            case  R.id.adviseL:
                type = advise_T.getText().toString().trim();
                intent.putExtra("type", type);
                startActivity(intent);
                finish();
                break;
            case  R.id.opinionL:
                type = opinion_T.getText().toString().trim();
                intent.putExtra("type", type);
                startActivity(intent);
                finish();
                break;
            case R.id.typeBack:
                finish();
                break;
        }
    }
}
