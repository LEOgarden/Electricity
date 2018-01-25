package android.leo.electricity.bean;

import java.util.List;

/**
 * Created by Leo on 2017/12/6.
 */

public class BoundedUser {
    private List<UserID> data;
    private String msg;
    private String result;

    public List<UserID> getData() {
        return data;
    }

    public void setData(List<UserID> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
