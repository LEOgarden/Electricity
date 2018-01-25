package android.leo.electricity.model;

import android.leo.electricity.callback.DataCallback;

/**
 * Created by Administrator on 2017/9/7.
 */

public interface ICheckUserModel {
    void loadUser(String path, String token, DataCallback callback);
}
