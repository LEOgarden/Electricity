package android.leo.electricity.model.modelImpl;

import android.leo.electricity.bean.ServicePoint;
import android.leo.electricity.callback.DataCallback;
import android.leo.electricity.model.IServerModel;
import android.leo.electricity.utils.OkHttpUtil;

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
 * Created by Administrator on 2017/7/24.
 */

public class ServerModelImpl implements IServerModel{

    private List<ServicePoint> serviceList;
    @Override
    public void obtainServerPoint(String url, String token, final DataCallback callback){
        OkHttpUtil.serverPointPost(url, token, new Callback(){
            @Override
            public void onFailure(Call call, IOException e){
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException{
                String responseStr = response.body().string();
                handleResponse(responseStr, callback);
            }
        });
    }

    private void handleResponse(String responseStr, DataCallback callback){
        try {
            JSONObject jsonObject = new JSONObject(responseStr);
            String result = jsonObject.getString("result");
            serviceList = new ArrayList<>();
            if("true".equals(result)){
                JSONArray dataArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < dataArray.length(); i++){
                    ServicePoint servicePoint = new ServicePoint();
                    JSONObject serverObj = dataArray.getJSONObject(i);
                    servicePoint.setPointName(serverObj.getString("POINTNAME"));
                    servicePoint.setAddress(serverObj.getString("ADDRESS"));
                    servicePoint.setContent(serverObj.getString("CONTENT"));
                    servicePoint.setDepartmentId(serverObj.getString("DEPARTMENTID"));
                    servicePoint.setPointNo(serverObj.getString("POINTNO"));
                    servicePoint.setPointType(serverObj.getString("POINTTYPE"));
                    servicePoint.setServerTime(serverObj.getString("SERVERTIME"));
                    servicePoint.setTel(serverObj.getString("TEL"));
                    serviceList.add(servicePoint);
                }
                callback.onSuccess(serviceList);
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
    }
}
