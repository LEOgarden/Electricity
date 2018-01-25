package android.leo.electricity.model;

import android.leo.electricity.callback.DataCallback;

/**
 * 获取服务网点业务层
 * Created by Administrator on 2017/7/24.
 */

public interface IServerModel{
    void obtainServerPoint(String url, String token, DataCallback callback);
}
