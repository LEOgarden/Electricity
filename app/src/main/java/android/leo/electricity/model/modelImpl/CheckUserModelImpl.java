package android.leo.electricity.model.modelImpl;

import android.leo.electricity.bean.UserID;
import android.leo.electricity.callback.DataCallback;
import android.leo.electricity.model.ICheckUserModel;
import android.leo.electricity.utils.OkHttpUtil;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Leo on 2017/9/7.
 */

public class CheckUserModelImpl implements ICheckUserModel {
    @Override
    public void loadUser(String path, String token, final DataCallback callback) {
        String url = path+"?token="+token;
        OkHttpUtil.getInstance().checkUserHH(url, token, new Callback() {
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
        JsonObject jsonObject = new JsonParser().parse(responseStr).getAsJsonObject();
        JsonArray dataArray = jsonObject.getAsJsonArray("data");
        Gson gson = new Gson();
        List<UserID> userIDList = new ArrayList<>();
        for (JsonElement userID : dataArray){
            UserID userIDBean = gson.fromJson(userID, new TypeToken<UserID>(){}.getType());
            userIDList.add(userIDBean);
        }
        callback.onSuccess(userIDList);
    }
}
