package android.leo.electricity.activity.service;

import android.app.Service;
import android.content.Intent;
import android.leo.electricity.MyApplication;
import android.leo.electricity.bean.UserID;
import android.leo.electricity.utils.ConCla;
import android.leo.electricity.utils.OkHttpUtil;
import android.leo.electricity.utils.Properties;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Leo on 2018/1/23.
 */

public class UserService extends Service {

    List<UserID> userIDs;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onCreate() {
        String path = ConCla.getConCla(Properties.WEB_IP, Properties.WEB_PORT, Properties.PROJECT_NAME,
                Properties.USER_LINK, Properties.GETBOUNDCUSTOMERS_ACTION);
        String url = path + "?token=" + MyApplication.token ;
        OkHttpUtil.checkUserHH(url, MyApplication.token, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseStr = response.body().string();
                userIDs = handleResponse(responseStr);
            }
        });

        Intent intent = new Intent();
        intent.putExtra("userIDs", (Serializable) userIDs);
        intent.setAction("android.leo.electricity.activity.service.UserService");
        sendBroadcast(intent);
    }

    private List<UserID> handleResponse(String responseStr) {
        JsonObject jsonObject = new JsonParser().parse(responseStr).getAsJsonObject();
        JsonArray dataArray = jsonObject.getAsJsonArray("data");
        Gson gson = new Gson();
        List<UserID> userIDList = new ArrayList<>();
        for (JsonElement userID : dataArray){
            UserID userIDBean = gson.fromJson(userID, new TypeToken<UserID>(){}.getType());
            userIDList.add(userIDBean);
        }
        return userIDList;
    }
}
