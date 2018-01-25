package android.leo.electricity.activity.usepower;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.leo.electricity.utils.Constants;
import android.leo.electricity.utils.Md5;
import android.leo.electricity.utils.Util;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.leo.electricity.R;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText edtMoney;
    private Button confirmPay;
    private LinearLayout finishPayment;
    PrePayIdAsyncTask prePayIdAsyncTask;
    private Map<String,String> resultUnifiedorder;
    private PayReq req;
    private StringBuffer sb;
    private final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        sb=new StringBuffer();
        req=new PayReq();
        initView();
    }

    private void initView() {
        edtMoney = (EditText) findViewById(R.id.edt_money);
        confirmPay = (Button) findViewById(R.id.confirm_pay);
        finishPayment = (LinearLayout) findViewById(R.id.finish_payment);
        finishPayment.setOnClickListener(this);
        confirmPay.setOnClickListener(this);
        edtMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int digits = 2;
                //删除“.”后面超过2位后的数据
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > digits) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + digits+1);
                        edtMoney.setText(s);
                        edtMoney.setSelection(s.length()); //光标移到最后
                    }
                }
                //如果"."在起始位置,则起始位置自动补0
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    edtMoney.setText(s);
                    edtMoney.setSelection(2);
                }

                //如果起始位置为0,且第二位跟的不是".",则无法后续输入
                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        edtMoney.setText(s.subSequence(0, 1));
                        edtMoney.setSelection(1);
                        return;
                    }
                }

                if (edtMoney.getText().toString().trim().length() != 0) {
                    confirmPay.setBackgroundResource(R.drawable.button_confirm2);
                    confirmPay.setClickable(true);
                    confirmPay.setTextColor(Color.parseColor("#ffffff"));
                }else {
                    confirmPay.setBackgroundResource(R.drawable.button_confirm1);
                    confirmPay.setClickable(false);
                    confirmPay.setTextColor(Color.parseColor("#696969"));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.confirm_pay:
                String str = edtMoney.getText().toString().trim();
                long paySum = 0l;
                if (str.contains(".")) {
                    paySum = (long) (Double.parseDouble(str) * 100);
                }else {
                    paySum = Long.parseLong(str)* 100l;
                }
                String payMpney = String.valueOf(paySum);
                String urlString="https://api.mch.weixin.qq.com/pay/unifiedorder";
                prePayIdAsyncTask=new PrePayIdAsyncTask();
                prePayIdAsyncTask.execute(urlString, payMpney);//生成prepayId
                break;
            case R.id.finish_payment:
                finish();
                break;
        }
    }

    /**
     * 调起支付
     */
    private void sendPayReq() {
        msgApi.registerApp(Constants.APP_ID);
        msgApi.sendReq(req);
        Log.i(">>>>>", req.partnerId);
    }

    /**
     *生成签名参数
     */
    private void genPayReq() {
        req.appId = Constants.APP_ID;
        req.partnerId = Constants.MCH_ID;
        if (resultUnifiedorder!=null) {
            req.prepayId = resultUnifiedorder.get("prepay_id");
            req.packageValue = "prepay_id="+resultUnifiedorder.get("prepay_id");
        }
        else {
            Toast.makeText(PaymentActivity.this, "prepayid为空", Toast.LENGTH_SHORT).show();
        }
        req.nonceStr = getNonceStr();
        req.timeStamp = String.valueOf(genTimeStamp());


        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
        signParams.add(new BasicNameValuePair("appid", req.appId));
        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
        signParams.add(new BasicNameValuePair("package", req.packageValue));
        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

        req.sign = genAppSign(signParams);

        sb.append("sign\n"+req.sign+"\n\n");

        //textView.setText(sb.toString());

        Log.w("TAG_sign", "----"+signParams.toString());
    }

    private String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Constants.API_KEY);

        this.sb.append("sign str\n"+sb.toString()+"\n\n");
        String appSign = Md5.getMessageDigest(sb.toString().getBytes());
        Log.e("TAG_appSign","----"+appSign);
        return appSign;
    }

    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    private class PrePayIdAsyncTask extends AsyncTask<String,Void, Map<String, String>> {

        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(PaymentActivity.this, "提示", "正在提交订单");
        }

        @Override
        protected Map<String, String> doInBackground(String... params) {
            String url=String.format(params[0]);
            String entity=getProductArgs(String.format(params[1]));
            Log.w("TAG_entity",">>>>"+entity);
            byte[] buf= Util.httpPost(url, entity);
            String content = new String(buf);
            Log.w("TAG_orion", "----"+content);
            Map<String,String> xml=decodeXml(content);

            return xml;
        }

        @Override
        protected void onPostExecute(Map<String, String> result) {
            super.onPostExecute(result);
            Log.w("TAG", "onPostExecute");
            if (dialog != null) {
                dialog.dismiss();
            }
            sb.append("prepay_id\n"+result.get("prepay_id")+"\n\n");
            //textView.setText(sb.toString());
            resultUnifiedorder=result;
            genPayReq();//生成签名参数
            sendPayReq();//调起支付
        }
    }

    private String getProductArgs(String payMoney) {
        Log.w("payMoney", payMoney);
        StringBuffer xml=new StringBuffer();
        try {
            String nonceStr=getNonceStr();
            xml.append("<xml>");
            List<NameValuePair> packageParams=new LinkedList<NameValuePair>();
            packageParams.add(new BasicNameValuePair("appid", Constants.APP_ID));
            packageParams.add(new BasicNameValuePair("body", "APP pay test"));
            packageParams.add(new BasicNameValuePair("mch_id", Constants.MCH_ID));
            packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
            packageParams.add(new BasicNameValuePair("notify_url", "https://www.baidu.com"));//写你们的回调地址
            packageParams.add(new BasicNameValuePair("out_trade_no",genOutTradNo()));
            packageParams.add(new BasicNameValuePair("total_fee", payMoney));
            packageParams.add(new BasicNameValuePair("trade_type", "APP"));

            String sign=getPackageSign(packageParams);
            packageParams.add(new BasicNameValuePair("sign", sign));
            String xmlString=toXml(packageParams);
            return xmlString;
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }

    private String getPackageSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Constants.API_KEY);

        String packageSign = Md5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        Log.w("Simon",">>>>"+packageSign);
        return packageSign;
    }

    /**
     *转成xml
     * @param params
     * @return
     */
    private String toXml(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (int i = 0; i < params.size(); i++) {
            sb.append("<"+params.get(i).getName()+">");


            sb.append(params.get(i).getValue());
            sb.append("</"+params.get(i).getName()+">");
        }
        sb.append("</xml>");

        Log.e("Simon",">>>>"+sb.toString());
        return sb.toString();
    }

    /**
     *生成订单号
     * @return
     */
    private String genOutTradNo() {
        Random random = new Random();
//      return "dasgfsdg1234"; //订单号写死的话只能支付一次，第二次不能生成订单
        return Md5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    public Map<String,String> decodeXml(String content) {

        try {
            Map<String, String> xml = new HashMap<String, String>();
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(content));
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {

                String nodeName=parser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:

                        break;
                    case XmlPullParser.START_TAG:

                        if("xml".equals(nodeName)==false){
                            //实例化student对象
                            xml.put(nodeName,parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                event = parser.next();
            }

            return xml;
        } catch (Exception e) {
            Log.e("Simon","----"+e.toString());
        }
        return null;
    }

    //生成随机号，防重发
    private String getNonceStr() {
        // TODO Auto-generated method stub
        Random random=new Random();

        return Md5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }
}
