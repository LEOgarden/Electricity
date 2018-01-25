package android.leo.electricity.bean;

/**
 * 注册结果
 * Created by Leo on 2017/7/24.
 */

public class RegisterInfo{
    private String msg;
    private String result;
    private String vid;

    public RegisterInfo(){
    }

    public String getMsg(){
        return msg;
    }

    public void setMsg(String msg){
        this.msg = msg;
    }

    public String getResult(){
        return result;
    }

    public void setResult(String result){
        this.result = result;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }
}
