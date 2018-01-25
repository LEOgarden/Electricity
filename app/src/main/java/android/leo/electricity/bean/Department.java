package android.leo.electricity.bean;


/**
 * 管理单位
 * Created by Leo on 2017/7/24.
 */

public class Department{
    private String cengc;      //层次
    private String cengnsxh;   //层次顺序号
    private String danwbh;     //单位编号
    private String danwmc;     //单位名称
    private String shangjdwbh; //上级单位编号

    public Department(){
    }

    public String getCengc(){
        return cengc;
    }

    public void setCengc(String cengc){
        this.cengc = cengc;
    }

    public String getCengnsxh(){
        return cengnsxh;
    }

    public void setCengnsxh(String cengnsxh){
        this.cengnsxh = cengnsxh;
    }

    public String getDanwbh(){
        return danwbh;
    }

    public void setDanwbh(String danwbh){
        this.danwbh = danwbh;
    }

    public String getDanwmc(){
        return danwmc;
    }

    public void setDanwmc(String danwmc){
        this.danwmc = danwmc;
    }

    public String getShangjdwbh(){
        return shangjdwbh;
    }

    public void setShangjdwbh(String shangjdwbh){
        this.shangjdwbh = shangjdwbh;
    }
}
