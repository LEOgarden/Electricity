package android.leo.electricity.model;

import android.leo.electricity.callback.DataCallback;

/**
 * Created by Administrator on 2017/8/7.
 */

public interface IFastBindtModel {
    void loadUserHH(String url, String token, final DataCallback callback);
}
