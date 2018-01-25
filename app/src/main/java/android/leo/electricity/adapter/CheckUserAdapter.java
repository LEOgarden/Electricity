package android.leo.electricity.adapter;

import android.content.Context;
import android.content.Intent;
import android.leo.electricity.R;
import android.leo.electricity.bean.UserID;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Leo on 2017/9/7.
 */

public class CheckUserAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    private List<UserID> userIDList;

    public CheckUserAdapter(Context context, List<UserID> userIDList) {
        this.context = context;
        this.userIDList = userIDList;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return userIDList.size();
    }

    @Override
    public Object getItem(int position) {
        return userIDList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.customer_id_item, null);
            viewHolder = new ViewHolder();
            viewHolder.customerId = (TextView) convertView.findViewById(R.id.customerId_check);
            viewHolder.customerName = (TextView) convertView.findViewById(R.id.customerName_check);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        UserID userID = (UserID) getItem(position);
        viewHolder.customerId.setText(userID.getHUH());
        viewHolder.customerName.setText(userID.getHUM());
        return convertView;
    }
    class ViewHolder{
        TextView customerId;
        TextView customerName;
    }
}
