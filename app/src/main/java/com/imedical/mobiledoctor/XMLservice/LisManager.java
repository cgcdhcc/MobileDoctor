package com.imedical.mobiledoctor.XMLservice;

import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.api.WsApiUtil;
import com.imedical.mobiledoctor.entity.BaseBean;
import com.imedical.mobiledoctor.entity.DiagItem;
import com.imedical.mobiledoctor.entity.DiagStatus;
import com.imedical.mobiledoctor.entity.Diagnosis;
import com.imedical.mobiledoctor.entity.LisReportList;
import com.imedical.mobiledoctor.util.LogMe;
import com.imedical.mobiledoctor.util.PropertyUtil;

import org.dom4j.DocumentException;

import java.util.List;
import java.util.Map;

public class LisManager {
	public static List<LisReportList> listLisReportList(Map params) throws Exception {
    	List<LisReportList> list = null;
		
    	String serviceUrl = SettingManager.getServerUrl();
		
		String requestXml = PropertyUtil.buildRequestXml(params);
		String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_LIST_Lis, requestXml);			
		LogMe.d(requestXml+"\n\n"+resultXml);
		list = PropertyUtil.parseBeansToList(LisReportList.class, resultXml);
		LogMe.d("mark", "返回的信息为:" + resultXml);	
  		return list;
	 }

 public static List<LisReportList> listLisReportListHis(Map params) throws Exception {
    	List<LisReportList> list = null;
		
    	String serviceUrl = SettingManager.getServerUrl();
		
		String requestXml = PropertyUtil.buildRequestXml(params);
		String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_LIST_Diagnosis_HIS, requestXml);
			
		LogMe.d(requestXml+"\n\n"+resultXml);
		list = PropertyUtil.parseBeansToList(LisReportList.class, resultXml);
			
		return list;
	 }
 
    public static BaseBean saveLisReportList(Map map) throws DocumentException,Exception {
    	BaseBean bean = null ;
		
    	String serviceUrl = SettingManager.getServerUrl();
		
		String requestXml = PropertyUtil.buildRequestXml(map);
		String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_SAVE_Diagnosis, requestXml);
		
		LogMe.d(requestXml+"\n\n"+resultXml);
		bean = PropertyUtil.parseToBaseInfo(resultXml);
		
		return bean;
	 }
    public static BaseBean updateLisReportList(Map map) throws DocumentException,Exception {
    	BaseBean bean = null ;
		
    	String serviceUrl = SettingManager.getServerUrl();
		
		String requestXml = PropertyUtil.buildRequestXml(map);
		String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_UPDATE_Diagnosis, requestXml);
		
		LogMe.d(requestXml+"\n\n"+resultXml);
		bean = PropertyUtil.parseToBaseInfo(resultXml);
		
		return bean;
	 }
}

