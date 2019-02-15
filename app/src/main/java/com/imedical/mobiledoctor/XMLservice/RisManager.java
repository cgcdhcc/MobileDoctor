package com.imedical.mobiledoctor.XMLservice;

import android.util.Log;
import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.api.WsApiUtil;
import com.imedical.mobiledoctor.entity.BaseBean;
import com.imedical.mobiledoctor.entity.OrderItem;
import com.imedical.mobiledoctor.entity.PacsView;
import com.imedical.mobiledoctor.entity.RisReport;
import com.imedical.mobiledoctor.entity.RisReportList;
import com.imedical.mobiledoctor.util.LogMe;
import com.imedical.mobiledoctor.util.PropertyUtil;

import java.util.List;
import java.util.Map;

public class RisManager {
	public static List<RisReportList> listRisReportList(Map params) throws Exception {
    	List<RisReportList> list = null;
		
    	String serviceUrl = SettingManager.getServerUrl();
		
		String requestXml = PropertyUtil.buildRequestXml(params);
		String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_LIST_Ris, requestXml);			
		list = PropertyUtil.parseBeansToList(RisReportList.class, resultXml);
		return list;
	 }
	public static List<RisReport> listRisReport(Map params) throws Exception {
    	List<RisReport> list = null;
		
    	String serviceUrl = SettingManager.getServerUrl();
		
		String requestXml = PropertyUtil.buildRequestXml(params);
		String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_Ris, requestXml);			
		LogMe.d(requestXml+"\n\n"+resultXml);
		list = PropertyUtil.parseBeansToList(RisReport.class, resultXml);
		return list;
	 }
	public static List<PacsView> listPacsView(Map params) throws Exception {
		List<PacsView> pacs = null;
		String serviceUrl = SettingManager.getServerUrl();
		String requestXml = PropertyUtil.buildRequestXml(params);
		Log.i("mark","请求的字符串味：" + requestXml);
		String resultXml = WsApiUtil.loadSoapObject(serviceUrl, Const.BIZ_CODE_Dicom, requestXml);
		Log.i("mark","pacs view 返回的resultxml为：" + resultXml);
		pacs = PropertyUtil.parseBeansToList(PacsView.class, resultXml);
		return pacs;
	}

}
