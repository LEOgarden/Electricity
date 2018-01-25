package android.leo.electricity.model;

import android.leo.electricity.callback.DataCallback;

/**
 * Created by Administrator on 2017/12/12.
 */

public interface IArrearageModel {
    void loadArrearageInfo(String path, String customerId, String token, DataCallback callback);
}
