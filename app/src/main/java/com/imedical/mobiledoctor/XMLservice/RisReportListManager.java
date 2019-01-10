package com.imedical.mobiledoctor.XMLservice;

import android.util.Log;

import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.api.WsApiUtil;
import com.imedical.mobiledoctor.entity.BaseBean;
import com.imedical.mobiledoctor.entity.OrderItem;
import com.imedical.mobiledoctor.entity.PatientInfo;
import com.imedical.mobiledoctor.entity.RisReportList;
import com.imedical.mobiledoctor.entity.UisView;
import com.imedical.mobiledoctor.util.LogMe;
import com.imedical.mobiledoctor.util.PropertyUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RisReportListManager {
	public static List<RisReportList> listRisReportList(Map params) throws Exception {
    	List<RisReportList> list = null;
		
    	String serviceUrl = SettingManager.getServerUrl();
		
		String requestXml = PropertyUtil.buildRequestXml(params);
		String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_LIST_Ris, requestXml);
			
		LogMe.d(requestXml+"\n\n"+resultXml);
		list = PropertyUtil.parseBeansToList(RisReportList.class, resultXml);
		Log.i("mark", "返回的信息为:" + resultXml);	
		return list;
	 }

	public static UisView getUisView(String userCode,String admId,String studyId,String padIP) throws Exception {
    	Map map = new HashMap();
    	map.put("userCode", userCode);
     	map.put("admId", admId);
     	map.put("studyId", studyId);
     	map.put("padIP", padIP);
     	
		List<UisView> list = null;
    	String serviceUrl = SettingManager.getServerUrl();
		String requestXml = PropertyUtil.buildRequestXml(map);
		String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_Ris_PACS, requestXml);
			
		LogMe.d(requestXml+"\n\n"+resultXml);
		list = PropertyUtil.parseBeansToList(UisView.class, resultXml);
		Log.i("mark", "返回的检查报告信息为:" + resultXml);	
		if(list.size()==0){
			throw new Exception("不存在DICOM图像!");
		}
		return list.get(0);
	 }
}
