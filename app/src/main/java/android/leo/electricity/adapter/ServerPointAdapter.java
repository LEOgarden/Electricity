package android.leo.electricity.adapter;

import android.content.Context;
import android.leo.electricity.R;
import android.leo.electricity.bean.ServicePoint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/7/24.
 */

public class ServerPointAdapter extends BaseAdapter{

    private Context context;
    private LayoutInflater layoutInflater;
    private List<ServicePoint> servicePointList;

    public ServerPointAdapter(Context context, List<ServicePoint> servicePointList){
        this.context = context;
        this.servicePointList = servicePointList;
        layoutInflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount(){
        return servicePointList.size();
    }

    @Override
    public Object getItem(int position){
        return servicePointList.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.server_point_item, null);
            viewHolder = new ViewHolder();
            viewHolder.pointName = (TextView) convertView.findViewById(R.id.serverPointName);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        ServicePoint point = (ServicePoint) getItem(position);
        viewHolder.pointName.setText(point.getPointName());
        ListView.LayoutParams params = new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT,70);//设置listview中item的宽度和高度
        convertView.setLayoutParams(params);
        return convertView;
    }

    class ViewHolder{
        TextView pointName;
    }
}
