package com.imedical.mobiledoctor.XMLservice;
import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.api.WsApiUtil;
import com.imedical.mobiledoctor.entity.BaseBean;
import com.imedical.mobiledoctor.entity.DiagItem;
import com.imedical.mobiledoctor.entity.DiagStatus;
import com.imedical.mobiledoctor.entity.Diagnosis;
import com.imedical.mobiledoctor.entity.TimeLineCategory;
import com.imedical.mobiledoctor.entity.ViewData;
import com.imedical.mobiledoctor.entity.ViewInfo;
import com.imedical.mobiledoctor.util.LogMe;
import com.imedical.mobiledoctor.util.PropertyUtil;
import org.dom4j.DocumentException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IntegrationViewManager {

	 public static List<TimeLineCategory> loadTimeLineCategoryAll() throws DocumentException,Exception {
			Map map = new HashMap();
			map.put("timeLineId", "");
	    	String serviceUrl = SettingManager.getServerUrl();
			
			String requestXml = PropertyUtil.buildRequestXml(map);
			String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_TimeLineCategory, requestXml);
			
			LogMe.d(requestXml+"\n\n"+resultXml);
			List<TimeLineCategory> list = PropertyUtil.parseBeansToList(TimeLineCategory.class, resultXml);
		
			return list;
		 }
	 
	 /**
	  * 设置某用户集成视图显示配置
	  *<Request>
	  *<userCode>demo</userCode>
	  *<timeLineId>AdmView</timeLineId>
	  *<timeLineConfigStr>01:0101:A|02:0202:C|02:0203:C|02:0204:C|03:0306:T|03:0301:T|03:0303:T|03:0302:T|03:0305:T|03:0304:T|04:0401:A|05:0501:A|06:0601:N|07:0701:L|19:1901:I|08:0801:L|09:0901:L|13:1301:A
	  *</timeLineConfigStr>
	  *</Request>
	  * @return
	  * @throws DocumentException
	  * @throws Exception
	  */
	 public static BaseBean setTimeLineCategory_CONFIG(String userCode, String timeLineId,String timeLineConfigStr) throws DocumentException,Exception {
			Map map = new HashMap();
			map.put("userCode", userCode);
			map.put("timeLineId", timeLineId);
			map.put("timeLineConfigStr", timeLineConfigStr);
			
	    	String serviceUrl = SettingManager.getServerUrl();
			
			String requestXml = PropertyUtil.buildRequestXml(map);
			String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_SET_TimeLineCategory, requestXml);
			
			LogMe.d(requestXml+"\n\n"+resultXml);
			BaseBean b = PropertyUtil.parseToBaseInfo(resultXml);
		
			return b;
		 }
	 
	 /**
	  2.11.3获取某用户集成视图显示配置
		业务代码：RequestCode
		60103
		请求参数：RequestXML
		<Request>
			<userCode>用户名</userCode>
			<timeLineId>时间线表id</timeLineId >
		</Request>

	  * @throws DocumentException
	  * @throws Exception
	  */
	 public static List<TimeLineCategory> loadTimeLineCategory_USER_CONFIG(String userCode,String timeLineId) throws DocumentException,Exception {
			Map map = new HashMap();
			map.put("userCode",   userCode);
			map.put("timeLineId", timeLineId);
	    	String serviceUrl = SettingManager.getServerUrl();
			
			String requestXml = PropertyUtil.buildRequestXml(map);
			String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_TimeLineCategory_Setting, requestXml);
			
			LogMe.d(requestXml+"\n\n"+resultXml);
			List<TimeLineCategory> list = PropertyUtil.parseBeansToList(TimeLineCategory.class, resultXml);
		    for(TimeLineCategory tc:list){
		    	tc.isSelected = true ;
		    }
			return list;
		 }
	 
	 
	 /**
	  * 2.11.4获取集成视图基本信息
      * 业务代码：RequestCode
		60104
		请求参数：RequestXML
		<Request>
		<admId>患者就诊id</admId>
		</Request>

	  * @throws DocumentException
	  * @throws Exception
	  */
	 public static ViewInfo loadViewInfo(String admId) throws DocumentException,Exception {
			Map map = new HashMap();
			map.put("admId",   admId);
	    	String serviceUrl = SettingManager.getServerUrl();
			
			String requestXml = PropertyUtil.buildRequestXml(map);
			String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_ViewInfo, requestXml);
			
			LogMe.d(requestXml+"\n\n"+resultXml);
			List<ViewInfo> list = PropertyUtil.parseBeansToList(ViewInfo.class, resultXml);
		
			return list.get(0);
		 }
	 
	 
	 /**
	  * 2.11.5获取集成视图显示项数据
		业务代码：RequestCode
		60105
		请求参数：RequestXML
		<Request>
		<admId>患者就诊id</admId>
		<startDate>查询的开始时间</startDate> //首次查询，传入参数为空，点击上一天传入的参数为：本次界面显示的开始日期-1，选择第n周，传入参数为weekNo 
		<weekNo>周数</weekNo>//当周数存在时，startDate无效
		<timeLineConfigStr></timeLineConfigStr>
		</Request>
		<timeLineConfigStr>
		项目分类代码: 项目分类明细代码:显示类型|项目分类代码:项目分类明细代码:显示类型
		</timeLineConfigStr>
	  * @return
	  * @throws DocumentException
	  * @throws Exception
	  */
	 public static ViewData loadViewData(String admId,String startDate,String weekNo,String timeLineConfigStr ) throws DocumentException,Exception {
			Map map = new HashMap();
			map.put("admId",   admId);
			map.put("startDate",   startDate);
			map.put("weekNo",   weekNo);
			map.put("timeLineConfigStr", timeLineConfigStr);
			
	    	String serviceUrl = SettingManager.getServerUrl();
			
			String requestXml = PropertyUtil.buildRequestXml(map);
			String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_ViewData, requestXml);
			
			LogMe.d(requestXml+"\n\n"+resultXml);
			List<ViewData> list = PropertyUtil.parseBeansToList(ViewData.class, resultXml);
			ViewData viewData = null ;
			if(list.size()!=0){
				viewData = list.get(0);
			 }
			return viewData;
		 }

}
