package android.leo.electricity.bean;

import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * Created by Leo on 2017/7/17.
 */

public class User{
    private int id;
    private String username;
    private String password;
    private String telephone;

    public User(){
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getTelephone(){
        return telephone;
    }

    public void setTelephone(String telephone){
        this.telephone = telephone;
    }
}
