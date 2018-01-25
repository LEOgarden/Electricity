package android.leo.electricity.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2018/1/11.
 */
@Entity
public class ElectricFee {
    @Id
    private long id;
    private String name;
    private String serialNum;
    private String money;
    private String time;
    private String state;
    @Generated(hash = 197519019)
    public ElectricFee(long id, String name, String serialNum, String money,
            String time, String state) {
        this.id = id;
        this.name = name;
        this.serialNum = serialNum;
        this.money = money;
        this.time = time;
        this.state = state;
    }
    @Generated(hash = 1026536557)
    public ElectricFee() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSerialNum() {
        return this.serialNum;
    }
    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }
    public String getMoney() {
        return this.money;
    }
    public void setMoney(String money) {
        this.money = money;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getState() {
        return this.state;
    }
    public void setState(String state) {
        this.state = state;
    }
}
