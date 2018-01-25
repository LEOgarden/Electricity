package android.leo.electricity.model.modelImpl;

import android.leo.electricity.bean.Bill;
import android.leo.electricity.callback.DataCallback;
import android.leo.electricity.model.IBillListModel;
import android.leo.electricity.utils.OkHttpUtil;
import android.util.Log;

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
 * Created by Leo on 2017/8/7.
 */

public class BillListImpl implements IBillListModel{
    @Override
    public void loadBillList(String path, String customerId, String token, final DataCallback callback){
        OkHttpUtil.checkUsedRecord(path, customerId, token, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseStr = response.body().string();
                handleResponseStr(responseStr, callback);
            }
        });
    }

    private void handleResponseStr(String responseStr, DataCallback callback) {
        JsonObject jsonObject = new JsonParser().parse(responseStr).getAsJsonObject();
        JsonArray dataArray = jsonObject.getAsJsonArray("data");
        Gson gson = new Gson();
        List<Bill> billList = new ArrayList<>();
        for (JsonElement jsonElement : dataArray){
            Bill bill = gson.fromJson(jsonElement, new TypeToken<Bill>(){}.getType());
            billList.add(bill);
        }
        callback.onSuccess(billList);
    }
}
