package android.leo.electricity.bean;

import java.io.Serializable;

/**
 * Created by Leo on 2017/8/16.
 */

public class UserID implements Serializable{
    private String CUIFDH ;
    private String HUH ;
    private String HUM ;
    private String YONGDDZ;

    public String getCUIFDH() {
        return CUIFDH;
    }

    public void setCUIFDH(String CUIFDH) {
        this.CUIFDH = CUIFDH;
    }

    public String getHUH() {
        return HUH;
    }

    public void setHUH(String HUH) {
        this.HUH = HUH;
    }

    public String getHUM() {
        return HUM;
    }

    public void setHUM(String HUM) {
        this.HUM = HUM;
    }

    public String getYONGDDZ() {
        return YONGDDZ;
    }

    public void setYONGDDZ(String YONGDDZ) {
        this.YONGDDZ = YONGDDZ;
    }
}
