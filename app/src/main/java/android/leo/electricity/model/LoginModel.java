package android.leo.electricity.model;

import android.leo.electricity.bean.LoginInfo;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Leo on 2017/7/19.
 */

public class LoginModel{

    public static LoginInfo getLoginInfo(String body){
        LoginInfo loginInfo = new LoginInfo();
        try{
            JSONObject jsonObject = new JSONObject(body);
            String msg = jsonObject.getString("msg");
            String result = jsonObject.getString("result");
            if("true".equals(result)){
                JSONObject data = jsonObject.getJSONObject("data");
                String token = data.getString("token");
                loginInfo.setToken(token);
            }
            loginInfo.setMsg(msg);
            loginInfo.setResult(result);
        }catch(JSONException e){
            e.printStackTrace();
        }
        return loginInfo;
    }

}
