package android.leo.electricity.adapter;

import android.content.Context;
import android.leo.electricity.R;
import android.leo.electricity.bean.Department;
import android.leo.electricity.view.SpinnerWindow;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/7/24.
 */

public class DepartmentListAdapter extends BaseAdapter{

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Department> departmentList;

    public DepartmentListAdapter(Context context, List<Department> departmentList){
        this.context = context;
        this.departmentList = departmentList;
        layoutInflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount(){
        return departmentList.size();
    }

    @Override
    public Object getItem(int position){
        return departmentList.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder = null;

        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.department_item, null);
            viewHolder = new ViewHolder();
            viewHolder.departmentName = (TextView) convertView.findViewById(R.id.departmentName);
            viewHolder.departmentNo = (TextView) convertView.findViewById(R.id.departmentNo);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        Department department = (Department) getItem(position);
        viewHolder.departmentName.setText(department.getDanwmc());
        viewHolder.departmentNo.setText(department.getDanwbh());
        return convertView;
    }

    class ViewHolder{
        TextView departmentName;
        TextView departmentNo;
    }
}
