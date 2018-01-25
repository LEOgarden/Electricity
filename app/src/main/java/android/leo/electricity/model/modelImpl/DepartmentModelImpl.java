package android.leo.electricity.model.modelImpl;

import android.leo.electricity.bean.Department;
import android.leo.electricity.callback.DataCallback;
import android.leo.electricity.model.IDepartmentModel;
import android.leo.electricity.utils.OkHttpUtil;
import android.util.Log;

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
 * Created by Leo on 2017/7/24.
 */

public class DepartmentModelImpl implements IDepartmentModel{

    private List<Department> departmentList;
    @Override
    public void obtainDepartment(String url, String token, final DataCallback callback){
        OkHttpUtil.getInstance().serverPointPost(url, token, new Callback(){
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
        try{
            JSONObject jsonObject = new JSONObject(responseStr);
            String result = jsonObject.getString("result");
            departmentList = new ArrayList<>();
            if("true".equals(result)){
                JSONArray dataArray = jsonObject.getJSONArray("data");
                for(int i = 0; i < dataArray.length(); i++){
                    JSONObject departmentObj = dataArray.getJSONObject(i);
                    Department department = new Department();
                    department.setCengc(departmentObj.getString("cengc"));
                    department.setCengnsxh(departmentObj.getString("cengnsxh"));
                    department.setDanwbh(departmentObj.getString("danwbh"));
                    department.setDanwmc(departmentObj.getString("danwmc"));
                    department.setShangjdwbh(departmentObj.getString("shangjdwbh"));
                    departmentList.add(department);
                }
                callback.onSuccess(departmentList);
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
    }
}
