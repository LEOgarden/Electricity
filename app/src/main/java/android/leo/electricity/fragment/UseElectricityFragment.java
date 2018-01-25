package android.leo.electricity.fragment;

import android.content.Intent;
import android.leo.electricity.FunctionUnableActivity;
import android.leo.electricity.MyApplication;
import android.leo.electricity.activity.LoginActivity;
import android.leo.electricity.activity.home.ElectricUsedActivity;
import android.leo.electricity.activity.home.PowerUserListActivity;
import android.leo.electricity.activity.usepower.ApplyActivity;
import android.leo.electricity.activity.usepower.PayActivity;
import android.leo.electricity.activity.usepower.RechargeActivity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.leo.electricity.R;
import android.widget.Button;


public class UseElectricityFragment extends Fragment implements View.OnClickListener{

    private Button payAndBuy;
    private Button fareAndQuantity;
    private Button recAndBuy;
    private Button useAndApply;
    private Button addition;
    private Button emergency;

    public UseElectricityFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_use_electricity, container, false);
        payAndBuy = (Button) view.findViewById(R.id.payAndBuy);
        fareAndQuantity = (Button) view.findViewById(R.id.fareAndQuantity);
        recAndBuy = (Button) view.findViewById(R.id.recAndBuy);
        useAndApply = (Button) view.findViewById(R.id.useAndApply);
        addition = (Button) view.findViewById(R.id.addition);
        emergency = (Button) view.findViewById(R.id.emergency);
        payAndBuy.setOnClickListener(this);
        fareAndQuantity.setOnClickListener(this);
        recAndBuy.setOnClickListener(this);
        useAndApply.setOnClickListener(this);
        addition.setOnClickListener(this);
        emergency.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v){
        Intent intent;
        switch(v.getId()){
            case R.id.payAndBuy:
                if (MyApplication.token == null){
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }else {
                    intent = new Intent(getActivity(), PayActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.fareAndQuantity:
                if (MyApplication.token == null){
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }else {
                    intent = new Intent(getActivity(), PowerUserListActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.recAndBuy:
                if (MyApplication.token == null){
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }else {
                    intent = new Intent(getActivity(), RechargeActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.useAndApply:
                if (MyApplication.token == null){
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }else {
                    intent = new Intent(getActivity(), ApplyActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.addition:
                if (MyApplication.token == null){
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }else {
                    intent = new Intent(getActivity(), FunctionUnableActivity.class);
                    intent.putExtra("title", "余额查询");
                    startActivity(intent);
                }
                break;
            case R.id.emergency:
                if (MyApplication.token == null){
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }else {
                    intent = new Intent(getActivity(), FunctionUnableActivity.class);
                    intent.putExtra("title", "应急送电");
                    startActivity(intent);
                }
                break;
        }
    }
}
