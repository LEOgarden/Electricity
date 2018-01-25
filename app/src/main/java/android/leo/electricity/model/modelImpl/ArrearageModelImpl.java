package android.leo.electricity.model.modelImpl;

import android.leo.electricity.bean.ArrearageBean;
import android.leo.electricity.callback.DataCallback;
import android.leo.electricity.model.IArrearageModel;
import android.leo.electricity.utils.OkHttpUtil;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Leo on 2017/12/12.
 */

public class ArrearageModelImpl implements IArrearageModel {
    @Override
    public void loadArrearageInfo(String path, String customerId, String token, final DataCallback callback) {
        OkHttpUtil.queryBalance(path, customerId, token, new Callback() {
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
            callback.onSuccess(responseStr);
    }
}
