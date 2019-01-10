package com.imedical.mobiledoctor.XMLservice;


import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.api.WsApiUtil;
import com.imedical.mobiledoctor.entity.BaseBean;
import com.imedical.mobiledoctor.entity.OrderItem;
import com.imedical.mobiledoctor.util.LogMe;
import com.imedical.mobiledoctor.util.PropertyUtil;

import org.dom4j.DocumentException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderItemManager {
	
	/**
	 * 
	 <Request>
	 <userCode>用户名</userCode>
	 <admId>患者就诊id</admId>
	 <orderState>医嘱状态(在用/V、已停/D)</orderState>
	 <groupId>安全组Id</groupId>
	 <departmentId>科室Id</departmentId>
	</Request>
	 * @return
	 * @throws Exception
	 */
    public static List<OrderItem> listOrderItemLong(String isDoc, String userCode, String admId, String orderState, String groupId, String departmentId, String conLoad) throws Exception {
    	List<OrderItem> list = null;
		Map params = new HashMap();
		params.put("isDoc",isDoc);
		params.put("userCode",userCode);
		params.put("admId",admId);
		params.put("orderState",orderState);
		params.put("groupId",groupId);
		params.put("departmentId",departmentId);
		params.put("conLoad", conLoad);
    	String serviceUrl = SettingManager.getServerUrl();
		
		String requestXml = PropertyUtil.buildRequestXml(params);
		String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_LIST_OrderItem_LONG, requestXml);
			
		LogMe.d(requestXml+"\n\n"+resultXml);
		
		list = PropertyUtil.parseBeansToList(OrderItem.class, resultXml);
			
		return list;
	 }
    /**
     * <Request>
		<userCode>用户名</userCode>
		<admId>患者就诊id</admId>
		<startDate>开始日期</startDate>
		<endDate>结束日期</endDate>
		<groupId>安全组Id</groupId>
		<departmentId>科室Id</departmentId>
		</Request>
     * @return
     * @throws Exception
     */
    public static List<OrderItem> listOrderItemTemp(String isDoc,String userCode,String admId,String startDate,String endDate,String groupId,String departmentId,final String conLoad) throws Exception {
    	List<OrderItem> list = null;
    	Map params = new HashMap();
    	params.put("isDoc",isDoc);
    	params.put("userCode",userCode);
		params.put("admId",admId);
		params.put("startDate",startDate);
		params.put("endDate",endDate);
		params.put("groupId",groupId);
		params.put("departmentId",departmentId);
		params.put("conLoad", conLoad);
    	String serviceUrl = SettingManager.getServerUrl();
		
		String requestXml = PropertyUtil.buildRequestXml(params);
		String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_LIST_OrderItem_TEMP, requestXml);
			
		LogMe.d(requestXml+"\n\n"+resultXml);
		list = PropertyUtil.parseBeansToList(OrderItem.class, resultXml);
			
		return list;
	 }
    /**
     * <Request>
		<userCode>用户名</userCode>
		<admId>患者就诊id</admId>
		<ordItemId>医嘱Id</ordItemId>
		</Request>

     */
    public static void stopOrderItem(String userCode,String admId,String ordItemId) throws DocumentException,Exception {
    	BaseBean bean = null ;
    	Map map = new HashMap();
    	map.put("userCode", userCode);
    	map.put("admId", admId);
    	map.put("ordItemId", ordItemId);
    	
    	String serviceUrl = SettingManager.getServerUrl();
		
		String requestXml = PropertyUtil.buildRequestXml(map);
		String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_LIST_OrderItem_STOP, requestXml);
		
		LogMe.d(requestXml+"\n\n"+resultXml);
		bean = PropertyUtil.parseToBaseInfo(resultXml);
		if(!"0".equals(bean.getResultCode())){
			throw new Exception(bean.getResultDesc());
		}
	 }
  

}
