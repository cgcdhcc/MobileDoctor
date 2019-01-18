package com.imedical.im.service;
import android.util.Log;
import com.google.gson.Gson;
import com.imedical.im.entity.ImBaseResponse;
import com.imedical.im.entity.userregister;
import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.XMLservice.SettingManager;
import com.imedical.mobiledoctor.api.RequestUtil;
import com.imedical.mobiledoctor.api.WsApiUtil;
import com.imedical.mobiledoctor.entity.QrCodeGenerateRequest;
import com.imedical.mobiledoctor.util.PropertyUtil;

public class ImUserService {
	public static ImUserService us;
	public static Gson g = new Gson();

	public static synchronized ImUserService getInstance() {
		if (us == null) {
			us = new ImUserService();
		}
		return us;
	}

	// 用户注册
	public ImBaseResponse userRegister(userregister ub) {
		ImBaseResponse imBaseResponse;
		try {
			String serviceUrl = SettingManager.getServerUrl();
			String resultXml  = WsApiUtil.loadSoapObjectJson(serviceUrl,Const.BIZ_CODE_IM_USER_REGISTER,null, g.toJson(ub));
			Log.d("msg",resultXml);
			imBaseResponse=new Gson().fromJson(resultXml,ImBaseResponse.class);
		} catch (Exception e) {
			imBaseResponse=null;
		}
		return imBaseResponse;
	}

	public void qrcodegenerate(QrCodeGenerateRequest request){
		try {
			String serviceUrl = SettingManager.getServerUrl();
			String resultXml  = WsApiUtil.loadSoapObjectJson(serviceUrl,Const.BIZ_CODE_qrcodegenerate,null, new Gson().toJson(request));
			Log.d("msg",resultXml);
		} catch (Exception e) {
		}

	}
}
