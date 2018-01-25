package android.leo.electricity.model;

import android.leo.electricity.callback.DataCallback;

/**
 * Created by Leo on 2017/7/24.
 */

public interface IDepartmentModel{
    void obtainDepartment(String url, String token, DataCallback callback);
}
