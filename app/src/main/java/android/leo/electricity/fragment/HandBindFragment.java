package android.leo.electricity.fragment;


import android.app.Fragment;
import android.leo.electricity.MyApplication;
import android.leo.electricity.bean.BoundedUser;
import android.leo.electricity.bean.UserID;
import android.leo.electricity.presenter.IHandBindCustomer;
import android.leo.electricity.presenter.presenterImpl.HandBindCustomerImpl;
import android.leo.electricity.utils.ConCla;
import android.leo.electricity.utils.OkHttpUtil;
import android.leo.electricity.utils.Properties;
import android.leo.electricity.view.IHandBindView;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.leo.electricity.R;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class HandBindFragment extends Fragment implements View.OnClickListener, IHandBindView{
    private EditText hand_bind_customerId;
    private EditText hand_bind_password;
    private Button hand_bind_confirm;
    private IHandBindCustomer handBindCustomer;

    public HandBindFragment() {
        // Required empty public constructor
        handBindCustomer = new HandBindCustomerImpl(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hand_bind, container, false);
        hand_bind_customerId = (EditText) view.findViewById(R.id.hand_bind_customerId);
        hand_bind_password = (EditText) view.findViewById(R.id.hand_bind_password);
        hand_bind_confirm = (Button) view.findViewById(R.id.hand_bind_confirm);
        hand_bind_confirm.setOnClickListener(this);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.hand_bind_confirm:
                String ip_port = ConCla.getConCla(Properties.WEB_IP, Properties.WEB_PORT, Properties.PROJECT_NAME,
                        Properties.USER_LINK, Properties.LOCKUSER_ACTION);
                String customerId = hand_bind_customerId.getText().toString();
                if (!customerId.equals("")){
                        String password = hand_bind_password.getText().toString();
                        handBindCustomer.loadCustomer(ip_port, MyApplication.phone, customerId, password, MyApplication.token);
                }else {
                    Toast.makeText(getActivity(), "输入的户号不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void handBindView(String msg) {
        Looper.prepare();
        Toast.makeText(getActivity(),msg, Toast.LENGTH_SHORT).show();
        Looper.loop();
    }
}
