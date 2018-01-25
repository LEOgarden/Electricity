package android.leo.electricity.model.modelImpl;

        import android.leo.electricity.bean.Department;
        import android.leo.electricity.bean.ElectricPreMonth;
        import android.leo.electricity.callback.DataCallback;
        import android.leo.electricity.model.IUseElectricModel;
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
 * Created by Administrator on 2017/9/11.
 */

public class UseElectricModelImpl implements IUseElectricModel {
    @Override
    public void useElectricInfo(String path, String customerId, int year,
                                String token, final DataCallback callback) {
        OkHttpUtil.checkUsedElectric(path, customerId, year, token, new Callback() {
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

    private void handleResponse(String res, DataCallback callback) {
        JsonObject jsonObject = new JsonParser().parse(res).getAsJsonObject();
        JsonArray dataArray = jsonObject.getAsJsonArray("data");
        Gson gson = new Gson();
        List<ElectricPreMonth> electPreMonths = new ArrayList<>();
        for (JsonElement electricPreMonth : dataArray){
            ElectricPreMonth electricPreMonthBean = gson.fromJson(electricPreMonth, new TypeToken<ElectricPreMonth>(){}.getType());
            electPreMonths.add(electricPreMonthBean);
        }
        callback.onSuccess(electPreMonths);
    }
}
