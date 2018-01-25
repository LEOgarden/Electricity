package android.leo.electricity.bean;

/**
 * Created by Leo on 2017/8/7.
 */

public class Bill{
    private String HUH;//户号
    private String SHISJE;//实收金额
    private String SHOUFLSH;//收费流水号
    private String SHOUFSJ;//收费时间
    private String YINGSJE;//应收金额

    public String getHUH() {
        return HUH;
    }

    public void setHUH(String HUH) {
        this.HUH = HUH;
    }

    public String getSHISJE() {
        return SHISJE;
    }

    public void setSHISJE(String SHISJE) {
        this.SHISJE = SHISJE;
    }

    public String getSHOUFLSH() {
        return SHOUFLSH;
    }

    public void setSHOUFLSH(String SHOUFLSH) {
        this.SHOUFLSH = SHOUFLSH;
    }

    public String getSHOUFSJ() {
        return SHOUFSJ;
    }

    public void setSHOUFSJ(String SHOUFSJ) {
        this.SHOUFSJ = SHOUFSJ;
    }

    public String getYINGSJE() {
        return YINGSJE;
    }

    public void setYINGSJE(String YINGSJE) {
        this.YINGSJE = YINGSJE;
    }
}
