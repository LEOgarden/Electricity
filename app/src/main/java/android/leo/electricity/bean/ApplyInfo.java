package android.leo.electricity.bean;

/**
 * Created by Leo on 2017/9/13.
 */

public class ApplyInfo {
    private String hum;
    private String kehdh;//客户电话
    private String Yongddz;//用电地址
    private String Zhengjlx;//证件类型
    private String Zhengjhm;//证件号码
    private String Jingbr;//经办人姓名
    private String Jingbrdh;//经办人手机号

    public String getHum() {
        return hum;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }

    public String getKehdh() {
        return kehdh;
    }

    public void setKehdh(String kehdh) {
        this.kehdh = kehdh;
    }

    public String getYongddz() {
        return Yongddz;
    }

    public void setYongddz(String yongddz) {
        Yongddz = yongddz;
    }

    public String getZhengjlx() {
        return Zhengjlx;
    }

    public void setZhengjlx(String zhengjlx) {
        Zhengjlx = zhengjlx;
    }

    public String getZhengjhm() {
        return Zhengjhm;
    }

    public void setZhengjhm(String zhengjhm) {
        Zhengjhm = zhengjhm;
    }

    public String getJingbr() {
        return Jingbr;
    }

    public void setJingbr(String jingbr) {
        Jingbr = jingbr;
    }

    public String getJingbrdh() {
        return Jingbrdh;
    }

    public void setJingbrdh(String jingbrdh) {
        Jingbrdh = jingbrdh;
    }

    @Override
    public String toString() {
        return "{" +
                "hum:" + hum +
                ",kehdh:" + kehdh +
                ",Yongddz:" + Yongddz +
                ",Zhengjlx:" + Zhengjlx +
                ",Zhengjhm:" + Zhengjhm +
                ",Jingbr:" + Jingbr +
                ",Jingbrdh:" + Jingbrdh +
                '}';
    }
}
