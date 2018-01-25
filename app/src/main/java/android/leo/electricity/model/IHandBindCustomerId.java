package android.leo.electricity.model;

import android.leo.electricity.MyApplication;
import android.leo.electricity.callback.DataCallback;

/**
 * Created by Administrator on 2017/9/5.
 */

public interface IHandBindCustomerId {
    void handBindCustomerId(String ip_port, String phone, String customerId, String password,
                            String token, DataCallback callback);
}
