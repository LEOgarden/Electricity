package android.leo.electricity.model.modelImpl;

import android.leo.electricity.bean.UserHH;
import android.leo.electricity.callback.DataCallback;
import android.leo.electricity.model.IFastBindtModel;
import android.leo.electricity.utils.OkHttpUtil;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/8/7.
 */

public class FastBindImpl implements IFastBindtModel{
    @Override
    public void loadUserHH(String url, String token, final DataCallback callback){
        OkHttpUtil.getInstance().bindUserHH(url, token, new Callback() {
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
        try{
            JSONObject jsonObject = new JSONObject(responseStr);
            String result = jsonObject.getString("result");
            UserHH userHH = new UserHH();
            if("true".equals(result)){
                JSONObject dataObject = jsonObject.getJSONObject("data");
                userHH.setResult(result);
                userHH.setMsg(jsonObject.getString("msg"));
                userHH.setUserHH(dataObject.getString("customerId"));
                userHH.setCustomerName(dataObject.getString("customerName"));
            }else {
                userHH.setMsg(jsonObject.getString("msg"));
            }
            callback.onSuccess(userHH);
        }catch(JSONException e){
            e.printStackTrace();
        }
    }
}
