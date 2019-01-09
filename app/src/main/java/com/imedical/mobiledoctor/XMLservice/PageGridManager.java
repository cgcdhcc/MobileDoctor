package com.imedical.mobiledoctor.XMLservice;

import android.app.Activity;
import android.util.Log;
import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.api.WsApiUtil;
import com.imedical.mobiledoctor.entity.BaseBean;
import com.imedical.mobiledoctor.entity.homegrid.funcCode;
import com.imedical.mobiledoctor.util.PropertyUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageGridManager {

	/**
	 * 显示功能：   业务代码：50107
	 类：DWR.BL.CalendarBook
	 方法：GetMonthRegData
	 请求参数：RequestXML
	 <Request>
	 <userCode>用户名</userCode>
	 </Request>
	 */
	
	public static List<funcCode> dispalyList(Activity ctx, String userCode) throws Exception {
		List<funcCode> list=null;
		Map<String, String> parm = new HashMap<String, String>();
		parm.put("userCode", userCode);
		SettingManager.initContext(ctx);
		String serviceUrl = SettingManager.getServerUrl();
		String requestXml = PropertyUtil.buildRequestXml(parm);
		String resultXml = WsApiUtil.loadSoapObject(serviceUrl, Const.BIZ_CODE_LIST_GRID, requestXml);
		Log.d("msg","resultXml:"+resultXml);
		list=PropertyUtil.parseBeansToList(funcCode.class,"RegisterInfo", resultXml);
		return list;
	}

	/**
	 * 用户反馈      业务代码：50108
	 类：DWR.BL.Login
	 方法：GetFeedbackData
	 请求参数：RequestXML
	 <Request>
	 <feedbackdata>反馈内容</feedbackdata>
	 <userCode>用户名</userCode>
	 <anonymous>1/0</anonymous>
	 </Request>
	 */

	public static BaseBean feedBack(Activity ctx, String feedbackdata, String userCode, String anonymous) throws Exception {
		BaseBean bean;
		Map<String, String> parm = new HashMap<String, String>();
		parm.put("feedbackdata", feedbackdata);
		parm.put("userCode", userCode);
		parm.put("anonymous", anonymous);
		SettingManager.initContext(ctx);
		String serviceUrl = SettingManager.getServerUrl();
		String requestXml = PropertyUtil.buildRequestXml(parm);
		String resultXml = WsApiUtil.loadSoapObject(serviceUrl, Const.BIZ_CODE_FEEDBACK, requestXml);
		bean=PropertyUtil.parseToBaseInfo(resultXml);
		return bean;
	}
}
