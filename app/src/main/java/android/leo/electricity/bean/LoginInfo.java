package android.leo.electricity.bean;

/**
 * 登录结果
 * Created by Administrator on 2017/7/19.
 */

public class LoginInfo{
    private String token;
    private String result;
    private String msg;

    public LoginInfo(){
    }

    public String getToken(){
        return token;
    }

    public void setToken(String token){
        this.token = token;
    }

    public String getResult(){
        return result;
    }

    public void setResult(String result){
        this.result = result;
    }

    public String getMsg(){
        return msg;
    }

    public void setMsg(String msg){
        this.msg = msg;
    }
}
