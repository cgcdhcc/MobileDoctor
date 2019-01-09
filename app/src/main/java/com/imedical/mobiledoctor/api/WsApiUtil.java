package com.imedical.mobiledoctor.api;
import android.util.Log;
import com.google.gson.Gson;
import com.imedical.mobiledoctor.AppConfig;
import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.api.platform.BrandInfo;
import com.imedical.mobiledoctor.api.platform.TokenResponse;
import com.imedical.mobiledoctor.entity.BaseResult;
import com.imedical.mobiledoctor.util.AES;
import com.imedical.mobiledoctor.util.DateUtil;
import com.imedical.mobiledoctor.util.PreferManager;
import com.imedical.mobiledoctor.util.Validator;

import java.net.URLDecoder;
import java.net.URLEncoder;

public class WsApiUtil {
    public static String BaseResultStr="<Response><ResultCode>101</ResultCode><ResultDesc>%s</ResultDesc><TotalCount></TotalCount><ResultList></ResultList></Response>";


    public static String loadSoapObject(String serviceUrl, String requestCode, String requestXml) {
        String access_token = PreferManager.getValue("access_token");
        String expires_in = PreferManager.getValue("expires_in");
        if (Validator.isBlank(access_token)) {
            if (client_credentials()) {
                access_token = PreferManager.getValue("access_token");
            } else {
                return String.format(BaseResultStr,"获取Token失败请稍后再试");
            }
        } else {
            if (Long.parseLong(expires_in) - DateUtil.getNowTime() > 100) {//未失效
            } else {//失效了
                if (client_credentials()) {
                    access_token = PreferManager.getValue("access_token");
                } else {
                    return String.format(BaseResultStr,"获取Token失败请稍后再试");
                }
            }
        }

        String temprequestXml = null;
        String methodName = "RequestSubmit";
        if (AppConfig.isTestMode) {
            Log.i("requestXml", requestXml);
            Log.i("requestCode", requestCode);
        }
        //lqz--add 加密
        try {
            if (AppConfig.AES_OR_NOT) {
                requestXml = URLEncoder.encode(requestXml, "UTF-8");
                temprequestXml = AES.replaceUrl(requestXml);
            } else {
                temprequestXml = requestXml;
            }
        } catch (Exception e) {
            return "";
        }


        String url = String.format(serviceUrl, requestCode, access_token, temprequestXml);
        String retData = "";
        try {
            retData = RequestUtil.getRequest(url, false);
            Gson g = new Gson();
            BaseResult br=g.fromJson(retData,BaseResult.class);
            if(br!=null&&br.success){
                retData=br.data;
                if (AppConfig.AES_OR_NOT) {
                    retData= AES.decrypt(retData);
                    retData = URLDecoder.decode(retData, "UTF-8");
                }
            }else {
                retData =String.format(BaseResultStr,(br==null?"网络请求失败请稍后再试":br.data));
            }

            if (AppConfig.isTestMode) {
                Log.i("resultXml", retData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retData;
    }



    public static boolean client_credentials() {
        BrandInfo info=new BrandInfo();
        info.brand_name= Const.brand_name;
        info.brand_type= Const.brand_type;
        Gson g = new Gson();
        String result = "";
        try {
            //result = RequestUtil.getRequestWithHead(Const.URL_CREATE+URLEncoder.encode(URLEncoder.encode(g.toJson(info),"utf-8"),"utf-8"), false);
            result = RequestUtil.getRequestWithHead(Const.URL_CREATE, false);
            TokenResponse tokenResponse = g.fromJson(result, TokenResponse.class);
            PreferManager.saveValue("access_token", tokenResponse.access_token);
            Log.d("msg", "新生成：" + DateUtil.getNowTime()+" ====== "+tokenResponse.expires_in+" ====== "+(DateUtil.getNowTime() + tokenResponse.expires_in));
            PreferManager.saveValue("expires_in", "" + (DateUtil.getNowTime() + tokenResponse.expires_in));
            Log.d("msg", "新生成：" + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean refresh_token(String token) {
        String result = "";
        try {
            result = RequestUtil.getRequestWithHead(Const.URL_REFRESH + token, false);
            Gson g = new Gson();
            TokenResponse tokenResponse = g.fromJson(result, TokenResponse.class);
            PreferManager.saveValue("access_token", tokenResponse.access_token);
            Log.d("msg", "刷新：" + DateUtil.getNowTime()+" ====== "+tokenResponse.expires_in+" ======= "+(DateUtil.getNowTime() + tokenResponse.expires_in));
            PreferManager.saveValue("expires_in", "" + (DateUtil.getNowTime() + tokenResponse.expires_in));
            Log.d("msg", "刷新：" + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("msg", result);
        return false;
    }

}
