package com.imedical.mobiledoctor.XMLservice;

import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.api.WsApiUtil;
import com.imedical.mobiledoctor.entity.BaseBean;
import com.imedical.mobiledoctor.entity.OrderItem;
import com.imedical.mobiledoctor.entity.PatientInfo;
import com.imedical.mobiledoctor.entity.TempImageFile;
import com.imedical.mobiledoctor.util.LogMe;
import com.imedical.mobiledoctor.util.PropertyUtil;
import com.imedical.mobiledoctor.entity.TempData;

import org.dom4j.DocumentException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TempManager {
	/**
	 * 2.8.2获取绘制体温单曲线的数据
		如果50701返回的体温单图片文件为空时，采用绘制曲线的模式
		业务代码：RequestCode
		50702
		请求参数：RequestXML
		<Request>
		<userCode>用户名</userCode>
		<admId>患者就诊id</admId>
		<weekNo>周数</weekNo>
		<startDate>开始日期</startDate>
		<endDate>结束日期</endDate>
		</Request>
		说明：当weekNo不为空时，startDate、endDate入参无效
	 * @throws DocumentException
	 * @throws Exception
	 */
	 public static TempData loadViewTemprature(String userCode, String admId, String weekNo, String startDate, String endDate ) throws DocumentException,Exception {
			Map map = new HashMap();
			map.put("userCode",   userCode);
			map.put("admId",   admId);
			map.put("startDate",   startDate);
			map.put("weekNo",   weekNo);
			map.put("endDate", endDate);
			
	    	String serviceUrl = SettingManager.getServerUrl();
			
			String requestXml = PropertyUtil.buildRequestXml(map);
			String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_ViewTempture, requestXml);
			
			LogMe.d(requestXml+"\n\n"+resultXml);
			List<TempData> list = PropertyUtil.parseBeansToList(TempData.class, resultXml);
			TempData viewData = null ;
			if(list.size()!=0){
				viewData = list.get(0);
			 }
			return viewData;
		 }

	 
	public static List<TempImageFile> getPatTempInfo(String userCode, String admId, String state) throws Exception{
		
		List<TempImageFile> list = null;
		
		Map<String, String> parm = new HashMap<String, String>();
		parm.put("userCode", userCode);
		parm.put("admId", admId);
		parm.put("netWorkType", state);
		
		String server = SettingManager.getServerUrl();
		String req = PropertyUtil.buildRequestXml(parm);
		String res = WsApiUtil.loadSoapObject(server, Const.BIZ_CODE_LIST_Temp, req);
		
		list = PropertyUtil.parseBeansToList(TempImageFile.class, res);
		
		LogMe.d("temp info ", res);
		
		return list;
		
	}
}
