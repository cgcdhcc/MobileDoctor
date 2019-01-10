package com.imedical.mobiledoctor.XMLservice;
import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.api.WsApiUtil;
import com.imedical.mobiledoctor.entity.DiagItem;
import com.imedical.mobiledoctor.entity.DiagStatus;
import com.imedical.mobiledoctor.entity.Diagnosis;
import com.imedical.mobiledoctor.entity.LisReportItem;
import com.imedical.mobiledoctor.util.LogMe;
import com.imedical.mobiledoctor.util.PropertyUtil;

import java.util.List;
import java.util.Map;

public class LisReportItemManager {
	public static List<LisReportItem> listLisReportItem(Map params) throws Exception {
    	List<LisReportItem> list = null;
    	String serviceUrl = SettingManager.getServerUrl();
		
		String requestXml = PropertyUtil.buildRequestXml(params);
		String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CDOE_LIS_Item, requestXml);
		LogMe.d(requestXml+"\n\n"+resultXml);
		list = PropertyUtil.parseBeansToList(LisReportItem.class, resultXml);
//		Log.i("mark", "返回的信息为:" + resultXml);	
		return list;
	 }
	
public static List<LisReportItem> listLisReportCurve(Map params) throws Exception {
	List<LisReportItem> list = null;	
	String serviceUrl = SettingManager.getServerUrl();
	
	String requestXml = PropertyUtil.buildRequestXml(params);
	String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_LIS_Curve, requestXml);		
//	Log.i("mark","数据返回结果" + resultXml);
	LogMe.d(requestXml+"\n\n"+resultXml);
	list = PropertyUtil.parseBeansToList(LisReportItem.class, resultXml);		
	return list;
}

 public static List<LisReportItem> listLisReportItemCompare(Map params) throws Exception {
    	List<LisReportItem> list = null;
		
    	String serviceUrl = SettingManager.getServerUrl();
		
		String requestXml = PropertyUtil.buildRequestXml(params);
		String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CDOE_LIS_Compare, requestXml);
//		Log.i("mark","数据返回结果" + resultXml);
		LogMe.d(requestXml+"\n\n"+resultXml);
		list = PropertyUtil.parseBeansToList(LisReportItem.class, resultXml);
		
//		List<Result> reList = PropertyUtil.parseBeansToList(Result.class, list.get(0).resultList);
		
		return list;
	 }
 
//    public static BaseBean saveLisReportList(Map map) throws DocumentException,Exception {
//    	BaseBean bean = null ;
//		
//    	String serviceUrl = SettingManager.getServerUrl();
//		
//		String requestXml = PropertyUtil.buildRequestXml(map);
//		String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_SAVE_Diagnosis, requestXml);
//		
//		LogMe.d(requestXml+"\n\n"+resultXml);
//		bean = PropertyUtil.parseToBaseInfo(resultXml);
//		
//		return bean;
//	 }
//    public static BaseBean updateLisReportList(Map map) throws DocumentException,Exception {
//    	BaseBean bean = null ;
//		
//    	String serviceUrl = SettingManager.getServerUrl();
//		
//		String requestXml = PropertyUtil.buildRequestXml(map);
//		String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_UPDATE_Diagnosis, requestXml);
//		
//		LogMe.d(requestXml+"\n\n"+resultXml);
//		bean = PropertyUtil.parseToBaseInfo(resultXml);
//		
//		return bean;
//	 }
}
