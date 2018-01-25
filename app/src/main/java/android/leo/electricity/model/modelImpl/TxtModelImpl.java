package android.leo.electricity.model.modelImpl;

import android.leo.electricity.MyApplication;
import android.leo.electricity.R;
import android.leo.electricity.model.ITxtModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by Leo on 2017/7/31.
 */

public class TxtModelImpl implements ITxtModel{
    @Override
    public String getTxtFileToString(int pathId){
        InputStream inputStream = MyApplication.getInstance().getResources().openRawResource(pathId);
        BufferedReader reader = null;
        try{
            reader = new BufferedReader(
                    new InputStreamReader(
                            inputStream,
                            "utf-8"));
        }catch(UnsupportedEncodingException e1){
            e1.printStackTrace();
        }

        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null){
                sb.append(line + "\n");
            }
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            try {
                inputStream.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
