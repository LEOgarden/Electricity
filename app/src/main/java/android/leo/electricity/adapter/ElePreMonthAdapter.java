package android.leo.electricity.adapter;

import android.content.Context;
import android.leo.electricity.R;
import android.leo.electricity.bean.ElectricPreMonth;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Leo on 2017/9/12.
 */

public class ElePreMonthAdapter extends BaseAdapter {
    private List<ElectricPreMonth> electricPreMonthList;
    private Context context;
    private LayoutInflater inflater;

    public ElePreMonthAdapter(List<ElectricPreMonth> electricPreMonthList, Context context) {
        this.electricPreMonthList = electricPreMonthList;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return electricPreMonthList.size();
    }

    @Override
    public Object getItem(int position) {
        return electricPreMonthList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.electric_pre_month_item, null);
            viewHolder = new ViewHolder();
            viewHolder.month = (TextView) convertView.findViewById(R.id.electric_month);
            viewHolder.elecDegree = (TextView) convertView.findViewById(R.id.elec_degree);
            viewHolder.elecFee = (TextView) convertView.findViewById(R.id.elec_fee);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        ElectricPreMonth electricPreMonth = (ElectricPreMonth) getItem(position);
        viewHolder.month.setText(electricPreMonth.getYUEF().substring(4)+"月");
        viewHolder.elecDegree.setText(electricPreMonth.getJIFDL());
        viewHolder.elecFee.setText(electricPreMonth.getYINGSJE());
        return convertView;
    }

    class ViewHolder{
        TextView month;
        TextView elecDegree;
        TextView elecFee;
    }
}
