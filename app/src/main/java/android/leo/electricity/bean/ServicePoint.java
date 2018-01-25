package android.leo.electricity.bean;

import java.io.Serializable;

/**
 * Created by Leo on 2017/7/24.
 */

public class ServicePoint implements Serializable{
    private String pointName;
    private String departmentId;
    private String pointNo;
    private String pointType;
    private String address;
    private String content;
    private String serverTime;
    private String tel;

    public ServicePoint(){
    }

    public String getPointName(){
        return pointName;
    }

    public void setPointName(String pointName){
        this.pointName = pointName;
    }

    public String getDepartmentId(){
        return departmentId;
    }

    public void setDepartmentId(String departmentId){
        this.departmentId = departmentId;
    }

    public String getPointNo(){
        return pointNo;
    }

    public void setPointNo(String pointNo){
        this.pointNo = pointNo;
    }

    public String getPointType(){
        return pointType;
    }

    public void setPointType(String pointType){
        this.pointType = pointType;
    }

    public String getAddress(){
        return address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }

    public String getServerTime(){
        return serverTime;
    }

    public void setServerTime(String serverTime){
        this.serverTime = serverTime;
    }

    public String getTel(){
        return tel;
    }

    public void setTel(String tel){
        this.tel = tel;
    }

}
