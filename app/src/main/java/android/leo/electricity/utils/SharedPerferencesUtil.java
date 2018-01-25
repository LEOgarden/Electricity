package android.leo.electricity.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Leo on 2017/7/19.
 */

public class SharedPerferencesUtil{
    /**
     * 保存在手机里面的文件名
     */
    private static final String FILE_NAME = "share_data";

    /**
     * 保存不同的数据类型
     * @param context
     * @param key
     * @param object
     */
    public static void setParam(Context context, String key, Object object, String file_mane){
        String type = object.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(file_mane, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if("String".equals(type)){
            editor.putString(key, (String)object);
        }
        else if("Integer".equals(type)){
            editor.putInt(key, (Integer)object);
        }
        else if("Boolean".equals(type)){
            editor.putBoolean(key, (Boolean)object);
        }
        else if("Float".equals(type)){
            editor.putFloat(key, (Float)object);
        }
        else if("Long".equals(type)){
            editor.putLong(key, (Long)object);
        }

        editor.commit();
    }

    /**
     * 读取数据
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object getParam(Context context, String key, Object defaultObject, String file_name){
        String type = defaultObject.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(file_name, Context.MODE_PRIVATE);

        if("String".equals(type)){
            if(sp.contains(key)){
                return sp.getString(key, (String) defaultObject);
            }else{
                return null;
            }
        }
        else if("Integer".equals(type)){
            if (sp.contains(key)){
                return sp.getInt(key, (Integer)defaultObject);
            }else{
                return null;
            }
        }
        else if("Boolean".equals(type)){
            if(sp.contains(key)){
                return sp.getBoolean(key, (Boolean)defaultObject);
            }else{
                return null;
            }
        }
        else if("Float".equals(type)){
            if (sp.contains(key)){
                return sp.getFloat(key, (Float)defaultObject);
            }else{
                return null;
            }
        }
        else if("Long".equals(type)){
            if (sp.contains(key)){
                return sp.getLong(key, (Long)defaultObject);
            }else{
                return null;
            }
        }
        return null;
    }
}
