package android.leo.electricity.bean;

/**
 * 解绑用户对象
 * Created by Administrator on 2017/12/6.
 */

public class UnbundlingUser extends UserID{
    public boolean isCheck;
    public String name;
    public String id;
    public String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isCheck(){
        return isCheck;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
