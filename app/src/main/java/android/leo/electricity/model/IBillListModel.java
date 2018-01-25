package android.leo.electricity.model;

import android.leo.electricity.callback.DataCallback;

/**
 * Created by Administrator on 2017/8/7.
 */

public interface IBillListModel{
    void loadBillList(String path, String customerId, String token, DataCallback callback);
}
