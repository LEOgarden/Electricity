package android.leo.electricity.adapter;

import android.content.Context;
import android.leo.electricity.R;
import android.leo.electricity.bean.Bill;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Leo on 2017/8/7.
 */

public class BillListAdapter extends RecyclerView.Adapter<BillListAdapter.MyHolder>{

    private List<Bill> bills;
    private Context context;

    public BillListAdapter(List<Bill> bills, Context context){
        this.bills = bills;
        this.context = context;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bill_list_item, viewGroup, false);
        MyHolder myHolder= new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder myHolder, int position){
        myHolder.chargeTime.setText(bills.get(position).getSHOUFSJ());
        myHolder.billDate.setText(bills.get(position).getSHOUFSJ());
        myHolder.billMoney.setText("¥"+bills.get(position).getYINGSJE());
        //myHolder.billType.setText(bills.get(position).getshou);
        //myHolder.billDept.setText(bills.get(position).getChargeDept());
        myHolder.billNu.setText(bills.get(position).getSHOUFLSH());
        myHolder.billState.setText("支付成功");
    }

    @Override
    public int getItemCount(){
        return bills.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        TextView chargeTime;
        TextView billDate;
        TextView billMoney;
        TextView billType;
        TextView billDept;
        TextView billNu;
        TextView billState;

        public MyHolder(View itemView){
            super(itemView);
            chargeTime = (TextView) itemView.findViewById(R.id.chargeTime);
            billDate = (TextView) itemView.findViewById(R.id.billDate);
            billMoney = (TextView) itemView.findViewById(R.id.billMoney);
            billType = (TextView) itemView.findViewById(R.id.billType);
            billDept = (TextView) itemView.findViewById(R.id.billDept);
            billNu = (TextView) itemView.findViewById(R.id.billNu);
            billState = (TextView) itemView.findViewById(R.id.billState);
        }
    }
}