package android.leo.electricity.model;

import android.leo.electricity.bean.RegisterInfo;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Leo on 2017/7/24.
 */

public class RegisterModel{
    public static RegisterInfo getRegisterInfo(String body){
        RegisterInfo registerInfo = new RegisterInfo();
        try{
            JSONObject jsonObject = new JSONObject(body);
            JSONObject data = jsonObject.getJSONObject("data");
            String vid = data.getString("vid");
            String msg = jsonObject.getString("msg");
            String result = jsonObject.getString("result");
            registerInfo.setMsg(msg);
            registerInfo.setResult(result);
            registerInfo.setVid(vid);
        }catch(JSONException e){
            e.printStackTrace();
        }
        return registerInfo;
    }
}
