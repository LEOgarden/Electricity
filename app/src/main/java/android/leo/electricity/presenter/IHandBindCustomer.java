package android.leo.electricity.presenter;

/**
 * Created by Administrator on 2017/9/5.
 */

public interface IHandBindCustomer {
    void loadCustomer(String ip_port, String phone, String customerId, String password,
                      String token);
}
