package android.leo.electricity.model;

import android.leo.electricity.callback.DataCallback;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface IUseElectricModel {
    void useElectricInfo(String path, String customerId, int year, String token,DataCallback callback);
}
