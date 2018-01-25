package android.leo.electricity.utils;

import android.leo.electricity.MyApplication;
import android.leo.electricity.bean.ApplyInfo;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Leo on 2017/7/19.
 */

public class OkHttpUtil{
    private static final byte[] LOCKER = new byte[0];
    private static OkHttpUtil mInstance;
    private static OkHttpClient mOkHttpClient;

    public OkHttpUtil(){
//        okhttp3.OkHttpClient.Builder clientBuilder=new okhttp3.OkHttpClient.Builder();
//        clientBuilder.readTimeout(30, TimeUnit.SECONDS);//读取超时
//        clientBuilder.connectTimeout(10, TimeUnit.SECONDS);//连接超时
//        clientBuilder.writeTimeout(60, TimeUnit.SECONDS);//写入超时
        mOkHttpClient = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
    }

    public static OkHttpUtil getInstance(){
        if (mInstance == null){
            synchronized (LOCKER){
                if (mInstance == null){
                    mInstance = new OkHttpUtil();
                }
            }
        }
        return mInstance;
    }

    /**
     * 登录网络请求
     * @请求方式 post
     * @param url
     * @param phoneno
     * @param pwd
     * @param callback
     * @throws IOException
     */
    public static void loginPost(String url, String phoneno, String pwd, Callback callback) throws IOException{
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("phoneno", phoneno);
        builder.add("password", pwd);
        FormBody body = builder.build();
        Request request = new Request.Builder().
                url(url).
                post(body).
                build();
        mOkHttpClient.
                newCall(request).
                enqueue(callback);
    }

    /**
     * 验证码请求
     * @请求方式 post
     * @param url
     * @param phone
     * @param callback
     */
    public static void valCodePost(final String url, final String phone, Callback callback){
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("phoneno", phone);
        FormBody body = builder.build();
        String str = null;
        Request request = new Request.Builder().
                url(url).
                post(body).
                build();

        mOkHttpClient.
                    newCall(request).
                    enqueue(callback);
    }

    /**
     * 注册网络请求
     * @请求方式 post
     * @param url
     * @param phone
     * @param password
     * @param valCode
     * @param callback
     */
    public static void registerPost(final String url, final String phone, String password, String valCode, Callback callback){
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("phoneno", phone);
        builder.add("password", password);
        builder.add("vCode", valCode);
        FormBody body = builder.build();
        Request request = new Request.Builder().
                url(url).
                post(body).
                build();
        mOkHttpClient.
                newCall(request).
                enqueue(callback);
    }

    /**
     * 获取服务网点
     * @请求方式 post
     * @param url
     * @param token
     * @param callback
     */
    public static void serverPointPost(final String url, String token, Callback callback){
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("token", token);
        FormBody body = builder.build();
        Request request = new Request.Builder().
                url(url).
                post(body).
                build();
        mOkHttpClient.
                newCall(request).
                enqueue(callback);
    }

    /**
     * 快速绑定
     * @param url
     * @param token
     * @param callback
     */
    public static void bindUserHH(String url, String token, Callback callback){
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("token", token);
        FormBody body = builder.build();
        Request request = new Request.Builder().
                url(url).
                post(body).
                build();
        mOkHttpClient.
                newCall(request).
                enqueue(callback);
    }

    /**
     * 查询
     * @param url
     * @param token
     * @param callback
     */
    public static void checkUserHH(String url, String token, Callback callback){
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("token", MyApplication.token);
        FormBody body = builder.build();
        Request request = new Request.Builder().
                url(url).
                post(body).
                build();
        mOkHttpClient.
                newCall(request).
                enqueue(callback);
    }

    /**
     * 查询电费电量
     * @param path
     * @param customerId
     * @param year
     * @param callback
     */
    public static void checkUsedElectric(String path, String customerId, int year,
                                         String token, Callback callback){
        String url = path+"?customerid="+customerId+"&year="+year+"&token="+token;
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("token", token);
        FormBody body = builder.build();
        Request request = new Request.Builder().
                url(url).
                post(body).
                build();
        mOkHttpClient.
                newCall(request).
                enqueue(callback);
    }
    /**
     * 查询缴费记录
     * @param path
     * @param customerId
     * @param callback
     */
    public static void checkUsedRecord(String path, String customerId,
                                         String token, Callback callback){
        String url = path+"?customerid="+customerId+"&token="+token;
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("token", token);
        FormBody body = builder.build();
        Request request = new Request.Builder().
                url(url).
                post(body).
                build();
        mOkHttpClient.
                newCall(request).
                enqueue(callback);
    }

    /**
     * 用电申请
     * @param path
     * @param applyInfo
     * @param token
     * @param callback
     */
    public static void newUserApply(String path, ApplyInfo applyInfo,
                                    String token, Callback callback){
        Gson gson = new Gson();
        String infoJson = gson.toJson(applyInfo);
        String url = path+"?applyInfo="+infoJson;
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("token", token);
        FormBody body = builder.build();
        Request request = new Request.Builder().
                url(url).
                post(body).
                build();
        mOkHttpClient.
                newCall(request).
                enqueue(callback);
    }

    /**
     * 更改密码短信验证服务
     * @param path
     * @param phone
     * @param callback
     */
    public static void changeValCode(String path, String phone, Callback callback){
        String url = path + "?phoneno=" + phone;
        FormBody.Builder builder = new FormBody.Builder();
        FormBody body = builder.build();
        Request request = new Request.Builder().
                url(url).
                post(body).
                build();
        mOkHttpClient.
                newCall(request).
                enqueue(callback);
    }

    /**
     * 更新密码服务
     * @param path
     * @param phone
     * @param newPassword
     * @param callback
     */
    public static void updatePassword(String path, String phone, String newPassword, Callback callback){
        String url = path + "?phoneno=" + phone + "&newPassword=" + newPassword;
        FormBody.Builder builder = new FormBody.Builder();
        FormBody body = builder.build();
        Request request = new Request.Builder().
                url(url).
                post(body).
                build();
        mOkHttpClient.
                newCall(request).
                enqueue(callback);
    }

    /**
     * 解绑用户服务
     * @param path
     * @param token
     * @param phone
     * @param customerId
     * @param callback
     */
    public static void unlockUser(String path, String token, String phone, String customerId,
                                  Callback callback){
        String url = path + "?token=" + token + "&phoneno=" + phone + "&customerid=" + customerId;
        FormBody.Builder builder = new FormBody.Builder();
        FormBody body = builder.build();
        Request request = new Request.Builder().
                url(url).
                post(body).
                build();
        mOkHttpClient.
                newCall(request).
                enqueue(callback);
    }

    /**
     * 欠费记录查询服务
     * @param path
     * @param customerId
     * @param token
     * @param callback
     */
    public static void queryBalance(String path, String customerId, String token,
                                    Callback callback){
        String url = path + "?customerid=" + customerId + "&token=" + token;
        FormBody.Builder builder = new FormBody.Builder();
        FormBody body = builder.build();
        Request request = new Request.Builder().
                url(url).
                post(body).
                build();
        mOkHttpClient.
                newCall(request).
                enqueue(callback);
    }
}
