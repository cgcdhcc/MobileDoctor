package com.imedical.mobiledoctor.XMLservice;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.api.WsApiUtil;
import com.imedical.mobiledoctor.base.BaseActivity;
import com.imedical.mobiledoctor.entity.SysParameter;
import com.imedical.mobiledoctor.util.PropertyUtil;

import java.util.List;

public class SysManager {


	public static String getCurrVersion(Activity ctx) {
		String versionName = null ;
		try {
			PackageManager pm = ctx.getPackageManager();
			
			 PackageInfo pinfo = pm.getPackageInfo(ctx.getPackageName(),PackageManager.GET_CONFIGURATIONS);
			 versionName = pinfo.versionName;
			
			 } catch(Exception e){
				 e.printStackTrace();
				 versionName = "0.0";
			 }
		return versionName;
	}
	
	public static int getCurrVersionCode(Activity ctx) {
		int code = 0 ;
		try {
			 PackageManager pm = ctx.getPackageManager();
			 PackageInfo pinfo = pm.getPackageInfo(ctx.getPackageName(),PackageManager.GET_CONFIGURATIONS);
			 code = pinfo.versionCode;
			
			 } catch(Exception e){
				 e.printStackTrace();
			 }
		return code;
	}

   public static SysParameter getVersionInfoOnServer(BaseActivity ctx) throws Exception {
		String serviceUrl = SettingManager.getServerUrl();
		String requestXml = "<Request></Request>";
		String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_CHECK_VERSION, requestXml);
		List<SysParameter> list = PropertyUtil.parseBeansToList(SysParameter.class,resultXml);
		
		return list.get(0) ;
	}
   
   /**
    * <admType>类型（I：住院； O：门诊； E：急诊）</admType>
    * @param code
    * @return
    */
   public static String getAdmTypeDesc(String code){
	   String desc = null;
	   if("I".equals(code)){
		   desc = "住院";
	   }
	   if("O".equals(code)){
		   desc = "门诊";
	   }
	   if("E".equals(code)){
		   desc = "急诊";
	   }
	   
	   return desc ;
   }
}
