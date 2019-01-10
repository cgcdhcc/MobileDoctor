package com.imedical.mobiledoctor.XMLservice;

import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.api.WsApiUtil;
import com.imedical.mobiledoctor.entity.BaseBean;
import com.imedical.mobiledoctor.entity.OrderItem;
import com.imedical.mobiledoctor.entity.PatientInfo;
import com.imedical.mobiledoctor.util.LogMe;
import com.imedical.mobiledoctor.util.PropertyUtil;

import org.dom4j.DocumentException;

import java.util.List;
import java.util.Map;

public class PatientInfoManager {
	public static String BIZ_CODE_LIST_PatientInfo = "" ;
	public static String BIZ_CODE_DEAL_PatientInfo = "" ;
	
    public static List<PatientInfo> listPatientInfo(Map params) throws Exception {
    	List<PatientInfo> list = null;
		
    	String serviceUrl = SettingManager.getServerUrl();
		
		String requestXml = PropertyUtil.buildRequestXml(params);
		String resultXml = WsApiUtil.loadSoapObject(serviceUrl,BIZ_CODE_LIST_PatientInfo, requestXml);
			
		LogMe.d(requestXml+"\n\n"+resultXml);
		list = PropertyUtil.parseBeansToList(PatientInfo.class, resultXml);
			
		return list;
	 }

    public static BaseBean dealPatientInfo(Map map) throws DocumentException,Exception {
    	BaseBean bean = null ;
		
    	String serviceUrl = SettingManager.getServerUrl();
		
		String requestXml = PropertyUtil.buildRequestXml(map);
		String resultXml = WsApiUtil.loadSoapObject(serviceUrl,BIZ_CODE_DEAL_PatientInfo, requestXml);
		
		LogMe.d(requestXml+"\n\n"+resultXml);
		bean = PropertyUtil.parseToBaseInfo(resultXml);
		
		return bean;
	 }
  

}
