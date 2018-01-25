package android.leo.electricity.activity.user;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.leo.electricity.MyApplication;
import android.leo.electricity.activity.MainActivity;
import android.leo.electricity.utils.FileUtil;
import android.leo.electricity.utils.MethodsCompat;
import android.os.Bundle;
import android.leo.electricity.R;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class SetupActivity extends Activity implements View.OnClickListener{

    private Button exitSys;
    private RelativeLayout aboutSystem;
    private RelativeLayout checkAndUpdate;
    private RelativeLayout cleanCache;
    private TextView tv_cacheSize;
    private LinearLayout finishSet;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 10007:
                    tv_cacheSize.setText("0M");
            }
        }

        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            Toast.makeText(SetupActivity.this, "缓存已清除", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        initView();
        calculateCacheSize();//计算缓存
    }

    private void initView(){
        exitSys = (Button) findViewById(R.id.exit_system);
        aboutSystem = (RelativeLayout) findViewById(R.id.about_system);
        checkAndUpdate = (RelativeLayout) findViewById(R.id.version_update);
        cleanCache = (RelativeLayout) findViewById(R.id.cleanCacheBtn);
        tv_cacheSize = (TextView) findViewById(R.id.cacheSize);
        finishSet = (LinearLayout) findViewById(R.id.back_setup);
        aboutSystem.setOnClickListener(this);
        checkAndUpdate.setOnClickListener(this);
        cleanCache.setOnClickListener(this);
        exitSys.setOnClickListener(this);
        finishSet.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.exit_system:
                MyApplication.token = null;
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.about_system:
                Intent intent1 = new Intent(this, AboutSystemActivity.class);
                startActivity(intent1);
                overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                break;
            case R.id.version_update:
                AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.Theme_AppCompat_Light_Dialog_Alert);
                builder.setTitle("检查更新");
                builder.setMessage("已是最新版本");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                break;
            case R.id.cleanCacheBtn:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert);
                builder1.setMessage("清除缓存!");
                builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cleanCacheFunction(MyApplication.getInstance());
                        Message message = new Message();
                        message.obj = "success";
                        message.what = 10007;
                        handler.sendMessage(message);
                        dialog.dismiss();
                    }
                });
                builder1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder1.show();
                break;
            case R.id.back_setup:
                finish();
                break;
        }
    }

    /**
     * 缓存清理
     */
    private void cleanCacheFunction(Context context) {
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getExternalCacheDir());
        }
    }

    private boolean deleteDir(File cacheDir) {
        if (cacheDir != null && cacheDir.isDirectory()) {
            String[] children = cacheDir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(cacheDir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return cacheDir.delete();
    }

    /**
     * 计算缓存大小
     */
    private void calculateCacheSize() {
        long fileSize = 0;
        String cacheSize = "0KB";
        File filesDir = this.getFilesDir();
        File cacheDir = this.getCacheDir();
        fileSize += FileUtil.getDirSize(filesDir);
        fileSize += FileUtil.getDirSize(cacheDir);

        // 2.2版本才有将应用缓存转移到sd卡的功能
        if (isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)) {
            File externalCacheDir = MethodsCompat
                    .getExternalCacheDir(this);
            fileSize += FileUtil.getDirSize(externalCacheDir);
            //fileSize += FileUtil.getDirSize(MyApplication.getInstance().getCacheDir());
        }
        if (fileSize > 0)
            cacheSize = FileUtil.formatFileSize(fileSize);
        tv_cacheSize.setText(cacheSize);
    }

    public static boolean isMethodsCompat(int VersionCode) {
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        return currentVersion >= VersionCode;
    }
}
