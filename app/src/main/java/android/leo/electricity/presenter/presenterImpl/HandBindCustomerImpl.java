package android.leo.electricity.presenter.presenterImpl;

import android.leo.electricity.bean.BoundedUser;
import android.leo.electricity.bean.UserID;
import android.leo.electricity.callback.DataCallback;
import android.leo.electricity.model.IHandBindCustomerId;
import android.leo.electricity.model.modelImpl.HandBindCustomerIdImpl;
import android.leo.electricity.presenter.IHandBindCustomer;
import android.leo.electricity.view.IHandBindView;
import android.util.Log;

/**
 * Created by Administrator on 2017/9/5.
 */

public class HandBindCustomerImpl implements IHandBindCustomer {
    private IHandBindCustomerId handBindModel;
    private IHandBindView handBindView;

    public HandBindCustomerImpl(IHandBindView handBindView) {
        this.handBindView = handBindView;
        handBindModel = new HandBindCustomerIdImpl();
    }

    @Override
    public void loadCustomer(String ip_port, String phone, String customerId, String password, String token) {
        handBindModel.handBindCustomerId(ip_port, phone, customerId, password, token, new DataCallback() {
            @Override
            public void onSuccess(Object object) {
                String msg = (String) object;
                handBindView.handBindView(msg);
            }
            @Override
            public void onError() {
            }
        });
    }
}
