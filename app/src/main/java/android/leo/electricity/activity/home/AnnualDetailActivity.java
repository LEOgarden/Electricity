package android.leo.electricity.activity.home;

import android.leo.electricity.adapter.ElePreMonthAdapter;
import android.leo.electricity.bean.ElectricPreMonth;
import android.leo.electricity.view.ElectricLineView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.leo.electricity.R;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AnnualDetailActivity extends AppCompatActivity implements View.OnClickListener{
    private ElectricLineView electricLineView;
    private List<String> mdata = new ArrayList<>();
    private ImageView annualBack;
    private ListView elecMonthList;
    private ElePreMonthAdapter elePreMonthAdapter;
    private List<ElectricPreMonth> electricPreMonthList;
    private TextView totalDegree;
    private TextView totalFee;
    private float sumDegree = 0.0f;
    private float sumFee = 0.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annual_detial);
        initView();
        electricPreMonthList = (List<ElectricPreMonth>) getIntent().
                getSerializableExtra("ElectricPreMonthList");
        setData(electricPreMonthList);
        setAdapter();
    }

    private void setAdapter() {
        elePreMonthAdapter = new ElePreMonthAdapter(electricPreMonthList, this);
        elecMonthList.setAdapter(elePreMonthAdapter);
        setListViewHeightBasedOnChildren(elecMonthList);
    }

    /**
     * 重新计算ListView的高度(ScorllView 中如果再放入scrollView 是无法计算的，我们可以计算后再赋值)
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ElePreMonthAdapter listAdapter = (ElePreMonthAdapter) listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    private void setData(List<ElectricPreMonth> electricPreMonthList) {
        for (int i = electricPreMonthList.size() - 1; i >= 0; i--) {
            mdata.add(electricPreMonthList.get(i).getJIFDL());
            float degree = Float.parseFloat(electricPreMonthList.get(i).getJIFDL());
            float fee = Float.parseFloat(electricPreMonthList.get(i).getYINGSJE());
            sumDegree += degree;
            sumFee += fee;
        }
        electricLineView.setInfo(mdata);
        totalDegree.
                setText(String.valueOf(sumDegree));
        totalFee.
                setText(String.valueOf(sumFee));
    }

    private void initView(){
        electricLineView = (ElectricLineView) findViewById(R.id.lineView);
        annualBack = (ImageView) findViewById(R.id.annualBack);
        elecMonthList = (ListView) findViewById(R.id.elec_month_list);
        totalDegree = (TextView) findViewById(R.id.sum_degree);
        totalFee = (TextView) findViewById(R.id.sum_fee);
        annualBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.annualBack:
                finish();
                break;
        }
    }
}
