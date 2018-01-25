package android.leo.electricity.utils;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * Created by leo on 2017/7/21.
 */

public class UserValUtil{

    public static boolean valTelp(String phoneno){
        if(isMatchLength(phoneno,11) && isMobileNo(phoneno)){
            return true;
        }
        return false;
    }

    private static boolean isMobileNo(String phoneno){
        /*
		 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */
        String telRegex = "[1][3578]\\d{9}";//"[1]第一位数字1,[3578]第二位数字3、5、7、8"
        if(TextUtils.isEmpty(phoneno)){
            return false;
        }else
            return phoneno.matches(telRegex);
    }

    private static boolean isMatchLength(String phoneno, int length){
        if(phoneno.isEmpty()){
            return false;
        }else{
            return phoneno.length() == length ? true : false;
        }
    }
}
