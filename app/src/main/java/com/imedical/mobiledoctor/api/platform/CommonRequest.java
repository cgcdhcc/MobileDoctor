package com.imedical.mobiledoctor.api.platform;

import android.util.Log;
import com.google.gson.Gson;
import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.util.AES;
import com.imedical.mobiledoctor.util.DateUtil;
import com.imedical.mobiledoctor.util.MD5Tools;
import com.imedical.mobiledoctor.util.Validator;


/**
 * Created by dashan on 2018/7/3.
 */

public class CommonRequest {
    public String token_type="api_credentials";
    public String token;//":"LDc0NTAyNTAzNTEsZGNiZjhlZTIsNGVjNjMwZTM2MTAwLTI4ZWEtOGUxMS0zNGQ2LTJhYmExYjI2LDE1MzA1MjA1NDc=","
    public String method;//":"patient.opregister.attention.doctor",
    public String version="v1.0";
    public String app_id= Const.appId;//":"62b1aba2-6d43-11e8-ae82-00163e036ce4","
    public String biz_content;//":{"RequestXML":"<Request><openId>15140281443</openId><terminalId>ms_client</terminalId><terminalType>web</terminalType><hospitalId>1</hospitalId></Request>"}}
    public String nonce_str;
    public String sign;//
    public String app_update="1";
    public class Content{
        public Content(String RequestXML){
            this.RequestXML=RequestXML;
        }
        public String RequestXML;
    }
    public CommonRequest(String token, String method, String RequestXML ){
        this.token=token;
        this.method=method;
        this.nonce_str= DateUtil.getNowDateTime("yyyyMMddHHmmss");
        String stringA="RequestXML="+RequestXML+"&app_id="+app_id+"&app_update=1&method="+method+"&nonce_str="+nonce_str+"&token="+token+"&token_type=api_credentials&version=v1.0";
        String stringSignTemp=stringA+ "doctor_sign_rad8";
        Log.d("msg","HIS:"+stringSignTemp);
        this.sign= MD5Tools.encode(stringSignTemp).toUpperCase();
        try{
            this.biz_content= AES.encrypt(new Gson().toJson(new Content(RequestXML)));
        }catch (Exception e){
            e.printStackTrace();
            Log.d("msg","加密失败");
        }

    }
    public CommonRequest(String token, String method, String query, String body){//RESTFul  type:body  query   放在url的参数才放在query http body里面的参数是放在body里面的
        this.token=token;
        this.method=method;
        this.nonce_str= DateUtil.getNowDateTime("yyyyMMddHHmmss");
        String stringA="";
        String content="";
        if(!Validator.isBlank(body)){
            content="{\"body\":"+body+"}";
            stringA="app_id="+app_id+"&app_update=1&body="+body+"&method="+method+"&nonce_str="+nonce_str+"&token="+token+"&token_type=api_credentials&version=v1.0";
        }else{
            stringA="app_id="+app_id+"&app_update=1&method="+method+"&nonce_str="+nonce_str+"&query="+query+"&token="+token+"&token_type=api_credentials&version=v1.0";
            content="{\"query\":"+query+"}";
        }
        String stringSignTemp=stringA+ "doctor_sign_rad8";
        Log.d("msg","RESTFul:"+stringSignTemp);
        this.sign= MD5Tools.encode(stringSignTemp).toUpperCase();

        Log.d("msg","content:"+content);
        try{
            this.biz_content= AES.encrypt(content).toLowerCase();//
        }catch (Exception e){
            e.printStackTrace();
            Log.d("msg","加密失败");
        }

    }

}
