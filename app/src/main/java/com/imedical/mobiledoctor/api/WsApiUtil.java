package com.imedical.mobiledoctor.api;

import android.util.Log;

import com.google.gson.Gson;
import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.api.platform.BrandInfo;
import com.imedical.mobiledoctor.api.platform.CommonRequest;
import com.imedical.mobiledoctor.api.platform.CommonResponse;
import com.imedical.mobiledoctor.api.platform.TokenResponse;
import com.imedical.mobiledoctor.util.AES;
import com.imedical.mobiledoctor.util.DateUtil;
import com.imedical.mobiledoctor.util.MD5Tools;
import com.imedical.mobiledoctor.util.PreferManager;
import com.imedical.mobiledoctor.util.Validator;

import java.io.File;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dashan on 2017/11/6.
 */

public class WsApiUtil {
    public static String BaseResultStr = "<Response><ResultCode>101</ResultCode><ResultDesc>%s</ResultDesc><TotalCount></TotalCount><ResultList></ResultList></Response>";

    public static Gson gson=new Gson();
    public static String loadSoapObject(String serviceUrl, String requestCode, String requestXml) {
        String access_token = PreferManager.getValue("access_token");
        String expires_in = PreferManager.getValue("expires_in");
        if (Validator.isBlank(access_token)) {
            if (client_credentials()) {
                access_token = PreferManager.getValue("access_token");
            } else {
                return String.format(BaseResultStr, "获取Token失败请稍后再试");
            }
        }
        else {
            if (Long.parseLong(expires_in) - DateUtil.getNowTime() > 100) {//未失效
            } else {//失效了
                if (client_credentials()) {
                    access_token = PreferManager.getValue("access_token");
                } else {
                    return String.format(BaseResultStr, "获取Token失败请稍后再试");
                }
            }
        }

        CommonRequest request = new CommonRequest(access_token,requestCode, requestXml);
        String retData = "";
        try {
            retData = RequestUtil.postRequest(serviceUrl,gson.toJson(request));
            if (Const.ISTEST) {
                Log.d("resultJSON", retData);
            }
            CommonResponse response=gson.fromJson(retData,CommonResponse.class);
            if(response!=null&&response.code.equals("10000")){
                retData=response.data;
                if (Const.AES_OR_NOT) {
                    retData = AES.decrypt(retData);
                }
            }else {
                retData =String.format(BaseResultStr,(response==null?"网络请求失败请稍后再试":response.msg));
            }

            if (Const.ISTEST) {
                Log.d("resultXml", retData);
            }
        } catch (Exception e) {
            e.printStackTrace();
            retData="<Response><ResultCode>-1</ResultCode><ResultDesc>网络竟然崩溃了，别紧张，请再次刷新试试</ResultDesc></Response>";
        }
        if(Validator.isBlank(retData)){
            retData="<Response><ResultCode>-1</ResultCode><ResultDesc>网络竟然崩溃了，别紧张，请再次刷新试试</ResultDesc></Response>";
        }
        return retData;
    }
    public static String loadSoapObjectJson(String serviceUrl, String requestCode, String query, String body) {
        String access_token = PreferManager.getValue("access_token");
        String expires_in = PreferManager.getValue("expires_in");
        if (Validator.isBlank(access_token)) {
            if (client_credentials()) {
                access_token = PreferManager.getValue("access_token");
            } else {
                return String.format(BaseResultStr, "获取Token失败请稍后再试");
            }
        }
        else {
            if (Long.parseLong(expires_in) - DateUtil.getNowTime() > 100) {//未失效
            } else {//失效了
                if (client_credentials()) {
                    access_token = PreferManager.getValue("access_token");
                } else {
                    return String.format(BaseResultStr, "获取Token失败请稍后再试");
                }
            }
        }

        if (Const.ISTEST) {
            Log.i("requestXml", query+body);
            Log.i("requestCode", requestCode);
        }
        CommonRequest request = new CommonRequest(access_token,requestCode, query,body);
        String retData = "";
        try {
            retData = RequestUtil.postRequest(serviceUrl,gson.toJson(request));
            if (Const.ISTEST) {
                Log.d("resultJSON", retData);
            }
            CommonResponse response=gson.fromJson(retData,CommonResponse.class);
            if(response!=null&&response.code.equals("10000")){
                retData=response.data;
                if (Const.AES_OR_NOT) {
                    retData = AES.decrypt(retData);
                }
            }else {
                retData =String.format(BaseResultStr,(response==null?"网络请求失败请稍后再试":response.msg));
            }

            if (Const.ISTEST) {
                Log.d("resultXml", retData);
            }
        } catch (Exception e) {
            e.printStackTrace();
            retData="<Response><ResultCode>-1</ResultCode><ResultDesc>网络竟然崩溃了，别紧张，请再次刷新试试</ResultDesc></Response>";
        }
        if(Validator.isBlank(retData)){
            retData="<Response><ResultCode>-1</ResultCode><ResultDesc>网络竟然崩溃了，别紧张，请再次刷新试试</ResultDesc></Response>";
        }
        return retData;
    }



    public static String loadHttpFormDataObject(String requestCode, String biz_content, String body, File file, String filename) {
        String retData = "";
        try {
//            biz_content = formatJson(biz_content);
//            body = formatJson(body);
            String access_token = PreferManager.getValue("access_token");
            String expires_in = PreferManager.getValue("expires_in");
            if (Validator.isBlank(access_token)) {
                if (client_credentials()) {
                    access_token = PreferManager.getValue("access_token");
                } else {
                    return String.format(BaseResultStr, "获取Token失败请稍后再试");
                }
            } else {
                if (Long.parseLong(expires_in) - DateUtil.getNowTime() > 100) {//未失效
                } else {//失效了
                    if (client_credentials()) {
                        access_token = PreferManager.getValue("access_token");
                    } else {
                        return String.format(BaseResultStr, "获取Token失败请稍后再试");
                    }
                }
            }

            String temprequestXml = null;
            if (Const.ISTEST) {
                Log.i("requestXml", biz_content);
                Log.i("requestCode", requestCode);
            }
            String nonce_str = DateUtil.getNowDateTime("yyyyMMddHHmmss");
            Map<String, String> formdata = new HashMap<String, String>();
            formdata.put("app_id", Const.appId);
            formdata.put("biz_content", AES.encrypt(biz_content));
            formdata.put("method", requestCode);
            formdata.put("token", access_token);
            formdata.put("token_type", "api_credentials");
            formdata.put("version", "v1.0");
            formdata.put("nonce_str", nonce_str);
            String stringA = "app_id=" + Const.appId + "&body=" + body + "&method=" + requestCode + "&nonce_str=" + nonce_str + "&token=" + access_token + "&token_type=api_credentials&version=v1.0";
            String stringSignTemp = stringA + "doctor_sign_rad8";
            String sign = MD5Tools.encode(stringSignTemp).toUpperCase();
            formdata.put("sign", sign);


            try {
                retData = RequestUtil.uploadFile(Const.SERVICEURL, null, filename, file, formdata);
                if (Const.ISTEST) {
                    Log.d("resultJSON", retData);
                }
                CommonResponse response = gson.fromJson(retData, CommonResponse.class);
                if (response != null && response.code.equals("10000")) {
                    retData = response.data;
                    if (Const.AES_OR_NOT) {
                        retData = AES.decrypt(retData);
                    }
                } else {
                    retData = String.format(BaseResultStr, (response == null ? "网络请求失败请稍后再试" : response.msg));
                }

                if (Const.ISTEST) {
                    Log.d("resultXml", retData);
                }
            } catch (Exception e) {
                e.printStackTrace();
                retData = String.format(BaseResultStr, ("网络请求失败请稍后再试"));
            }
            if (Validator.isBlank(retData)) {
                retData = String.format(BaseResultStr, ("网络请求失败请稍后再试"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retData;
    }




    public static boolean client_credentials() {
        BrandInfo info = new BrandInfo();
        info.brand_name = Const.brand_name;
        info.brand_type = Const.brand_type;
        Gson g = new Gson();
        String result = "";
        try {
            result = RequestUtil.getRequestWithHead(Const.URL_CREATE);
            TokenResponse tokenResponse = g.fromJson(result, TokenResponse.class);
            PreferManager.saveValue("access_token", tokenResponse.access_token);
            if(Const.ISTEST){
                Log.d("msg", "新生成：" + DateUtil.getNowTime() + " ====== " + tokenResponse.remain_time + " ====== " + (DateUtil.getNowTime() + tokenResponse.remain_time));
            }
            PreferManager.saveValue("expires_in", "" + (DateUtil.getNowTime() + tokenResponse.remain_time));
            if(Const.ISTEST) {
                Log.d("msg", "新生成：" + result);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}
