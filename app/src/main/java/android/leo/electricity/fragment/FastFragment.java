package android.leo.electricity.fragment;


import android.app.Fragment;
import android.content.DialogInterface;
import android.leo.electricity.MyApplication;
import android.leo.electricity.bean.UserHH;
import android.leo.electricity.presenter.IUserHHPresenter;
import android.leo.electricity.presenter.presenterImpl.UserHHPresenterImpl;
import android.leo.electricity.utils.ConCla;
import android.leo.electricity.utils.OkHttpUtil;
import android.leo.electricity.utils.Properties;
import android.leo.electricity.view.IUserHHView;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.leo.electricity.R;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class FastFragment extends Fragment implements View.OnClickListener,
        IUserHHView{

    private TextView userphone;
    private Button checkUserId;
    private IUserHHPresenter userHHPresenter;
    private TextView bind_customerId;
    private TextView bind_name;
    public FastFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fast, container, false);
        userphone = (TextView) view.findViewById(R.id.userPhone);
        userphone.setText(MyApplication.phone);
        checkUserId = (Button) view.findViewById(R.id.check_user_id);
        checkUserId.setOnClickListener(this);
        userHHPresenter = new UserHHPresenterImpl(this);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.check_user_id:
                String phone = (String) userphone.getText();
                String url = ConCla.getConCla(Properties.WEB_IP, Properties.WEB_PORT, Properties.PROJECT_NAME,
                        Properties.USER_LINK, Properties.QUERYCUSTOMERID_ACTION) + "?phoneno="+phone;
                userHHPresenter.bindUserHH(url, MyApplication.token);
                break;
        }
    }

    @Override
    public void showUserHHView(final UserHH userHH) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Looper.prepare();
        AlertDialog bindDialog = builder.create();
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.bing_dialog, null);
        bind_customerId = (TextView) view.findViewById(R.id.bind_customerId);
        bind_name = (TextView) view.findViewById(R.id.bind_name);
        if (userHH.getCustomerName() != null && userHH.getUserHH() != null) {
            bind_customerId.setText(userHH.getUserHH());
            bind_name.setText(userHH.getCustomerName());
        }else {
            bind_customerId.setText(userHH.getMsg());
            bind_name.setVisibility(View.GONE);
        }
        bindDialog.setView(view);
        bindDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        bindDialog.setButton(AlertDialog.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (userHH.getCustomerName() != null && userHH.getUserHH() != null){

                }
                dialog.dismiss();
            }
        });
        bindDialog.show();
        Looper.loop();
    }
}
