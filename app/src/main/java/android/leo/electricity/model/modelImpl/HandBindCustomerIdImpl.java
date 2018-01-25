package android.leo.electricity.model.modelImpl;

import android.leo.electricity.MyApplication;
import android.leo.electricity.bean.User;
import android.leo.electricity.bean.UserID;
import android.leo.electricity.callback.DataCallback;
import android.leo.electricity.model.IHandBindCustomerId;
import android.leo.electricity.utils.OkHttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/9/5.
 */

public class HandBindCustomerIdImpl implements IHandBindCustomerId {
    @Override
    public void handBindCustomerId(String ip_port, String phone, String customerId, String password,
                                   String token, final DataCallback callback) {
        String url = ip_port+"?phoneno="+phone+"&customerid="+customerId+"&password="+password;
        OkHttpUtil.bindUserHH(url, token, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseStr = response.body().string();
                handleResponse(responseStr, callback);
            }
        });
    }

    private void handleResponse(String responseStr, DataCallback callback) {
        try {
            JSONObject jsonObject = new JSONObject(responseStr);
            String msg = jsonObject.getString("msg");
            callback.onSuccess(msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
