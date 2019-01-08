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
import com.imedical.mobiledoctor.util.PreferManager;
import com.imedical.mobiledoctor.util.Validator;


/**
 * Created by dashan on 2017/11/6.
 */

public class WsApiUtil {
    public static String BaseResultStr = "<Response><ResultCode>101</ResultCode><ResultDesc>%s</ResultDesc><TotalCount></TotalCount><ResultList></ResultList></Response>";

    public static Gson gson = new Gson();

    public static String loadSoapObject(String serviceUrl, String requestCode, String requestXml) {
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
            Log.i("requestXml", requestXml);
            Log.i("requestCode", requestCode);
        }
        CommonRequest request = new CommonRequest(access_token, requestCode, requestXml);
        String retData = "";
        try {
            retData = RequestUtil.postRequest(Const.SERVICEURL, gson.toJson(request));
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
            retData = "<Response><ResultCode>-1</ResultCode><ResultDesc>网络竟然崩溃了，别紧张，请再次刷新试试</ResultDesc></Response>";
        }
        if (Validator.isBlank(retData)) {
            retData = "<Response><ResultCode>-1</ResultCode><ResultDesc>网络竟然崩溃了，别紧张，请再次刷新试试</ResultDesc></Response>";
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
            //result = RequestUtil.getRequestWithHead(Const.URL_CREATE+URLEncoder.encode(URLEncoder.encode(g.toJson(info),"utf-8"),"utf-8"), false);
            result = RequestUtil.getRequestWithHead(Const.URL_CREATE, false);
            TokenResponse tokenResponse = g.fromJson(result, TokenResponse.class);
            PreferManager.saveValue("access_token", tokenResponse.access_token);
            Log.d("msg", "新生成：" + DateUtil.getNowTime() + " ====== " + tokenResponse.remain_time + " ====== " + (DateUtil.getNowTime() + tokenResponse.remain_time));
            PreferManager.saveValue("expires_in", "" + (DateUtil.getNowTime() + tokenResponse.remain_time));
            Log.d("msg", "新生成：" + result);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}

