package com.imedical.mobiledoctor.XMLservice;
import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.api.RequestUtil;
import com.imedical.mobiledoctor.api.WsApiUtil;
import com.imedical.mobiledoctor.entity.DiagItem;
import com.imedical.mobiledoctor.entity.DiagStatus;
import com.imedical.mobiledoctor.entity.Diagnosis;
import com.imedical.mobiledoctor.entity.UisView;
import com.imedical.mobiledoctor.util.DateUtil;
import com.imedical.mobiledoctor.util.LogMe;
import com.imedical.mobiledoctor.util.PhoneUtil;
import com.imedical.mobiledoctor.util.PropertyUtil;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EncriptManager {
	

    /*
    String adapterUrl = Request["adapterUrl"];
    String issuedServerAddress = Request["issuedServerAddress"];
    String clientAddress = Request["clientAddress"];
    String userId = Request["userId"];
    String password = Request["password"];
    String issuedDateTime = Request["issuedDateTime"];
    String appId = Request["appId"];
    String userViewId = Request["userViewId"];
    String columnName = Request["columnName"];
    String columnValue = Request["columnValue"];
    */
	public static String loadEncriptedStr(UisView uv) throws Exception {

		Date date = DateUtil.getDate(uv.nowDate+" "+uv.nowTime,"yyyy-MM-dd HH:mm:ss");
		//String strDate = DateUtil.getDateTimeStr(date, "mm/dd/yyyy hh:mm:ss");
		String strDate = DateUtil.getDateTimeGregorian(date, "MM/dd/yyyy HH:mm:ss");

		String str = null ;
		try {
			String ip = PhoneUtil.getLocalIpAddress_v4() ;
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("adapterUrl",uv.uisUrl));//1
			params.add(new BasicNameValuePair("issuedServerAddress",ip));
			params.add(new BasicNameValuePair("clientAddress",ip));
			params.add(new BasicNameValuePair("userId",".\\"+uv.userName));
			params.add(new BasicNameValuePair("password",uv.password));//5
			params.add(new BasicNameValuePair("issuedDateTime",strDate));
			params.add(new BasicNameValuePair("appId",uv.applicationId));
			params.add(new BasicNameValuePair("userViewId",uv.userViewId));
			params.add(new BasicNameValuePair("columnName",uv.columnName));
			params.add(new BasicNameValuePair("columnValue",uv.column));//10
			////String serviceUrl = "http://10.160.16.131/mhealth/Default.aspx?param=123";//测试用地址
			//String serviceUrl = SettingManager.getServerUrl();
			
			str  = RequestUtil.postForm(uv.rsaUrl, params,true).trim();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("加密服务器返回结果错误！");
		}
		
		return str ;
	}

	
}
