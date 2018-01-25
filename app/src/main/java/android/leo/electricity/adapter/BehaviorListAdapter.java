package android.leo.electricity.adapter;

import android.content.Context;
import android.leo.electricity.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by LEo on 2017/8/31.
 */

public class BehaviorListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<String> list;
    public boolean flag = false;

    public BehaviorListAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.behavior_item_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.behavior = (TextView) convertView.findViewById(R.id.suggestBehavior);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        String data = list.get(position);
        viewHolder.behavior.setText(data);
        return convertView;
    }

    class ViewHolder{
        TextView behavior;
    }
}
