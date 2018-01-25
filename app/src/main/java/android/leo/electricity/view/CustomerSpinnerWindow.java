package android.leo.electricity.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.leo.electricity.R;
import android.leo.electricity.adapter.CheckUserAdapter;
import android.leo.electricity.adapter.DepartmentListAdapter;
import android.leo.electricity.bean.Department;
import android.leo.electricity.bean.UserHH;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import java.util.List;

/**
 * Created by Leo on 2018/1/23.
 */

public class CustomerSpinnerWindow extends PopupWindow {
    private Context mContext;
    private ListView mListView;
    private List<UserHH> list;
    private LayoutInflater inflater;
    private DepartmentListAdapter mAdapter;

    public CustomerSpinnerWindow(Context mContext,AdapterView.OnItemClickListener clickListener) {
        super(mContext);
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        init(clickListener);
    }

    private void init(AdapterView.OnItemClickListener clickListener) {
        View view = inflater.inflate(R.layout.spinner_layout, null);
        setContentView(view);
        setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x00);
        setBackgroundDrawable(dw);
        mListView = (ListView) view.findViewById(R.id.department_listView);
        mListView.setOnItemClickListener(clickListener);
    }

    public void setAdapter(CheckUserAdapter mAdapter){
        mListView.setAdapter(mAdapter);
    }
}
