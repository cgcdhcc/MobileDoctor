package com.imedical.mobiledoctor.XMLservice;

import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.api.WsApiUtil;
import com.imedical.mobiledoctor.entity.AntBodyPart;
import com.imedical.mobiledoctor.entity.AntIndication;
import com.imedical.mobiledoctor.entity.AntReason;
import com.imedical.mobiledoctor.entity.AntReasonData;
import com.imedical.mobiledoctor.entity.BaseBean;
import com.imedical.mobiledoctor.entity.BaseInfo;
import com.imedical.mobiledoctor.entity.ConsultDoc;
import com.imedical.mobiledoctor.entity.ConsultLoc;
import com.imedical.mobiledoctor.entity.Instruction;
import com.imedical.mobiledoctor.entity.MainReturn;
import com.imedical.mobiledoctor.entity.OperaTime;
import com.imedical.mobiledoctor.entity.Operation;
import com.imedical.mobiledoctor.entity.OrderItem;
import com.imedical.mobiledoctor.entity.OrderSuscept;
import com.imedical.mobiledoctor.entity.ParamAnti;
import com.imedical.mobiledoctor.entity.PopMessage;
import com.imedical.mobiledoctor.util.LogMe;
import com.imedical.mobiledoctor.util.PropertyUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AntiDrugApplyManager {
	
	/**
	 * <Request>
		<userCode>用户名</userCode>
		<admId>就诊Id</admId>
		<arcimId>医嘱项Id</arcimId>
		</Request>
	 * @param userCode
	 * @return
	 * @throws Exception
	 */
	public static BaseInfo loadBaseInfo(String userCode, String admId, String arcimId) throws Exception {
		Map params = new HashMap();
		params.put("userCode", userCode);
		params.put("admId", admId);
		params.put("arcimId", arcimId);
		
    	String serviceUrl = SettingManager.getServerUrl();
		
		String requestXml = PropertyUtil.buildRequestXml(params);
		String resultXml  = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_DETAIL_ANTI_BaseInfo, requestXml);
			
		LogMe.d(requestXml+"\n\n"+resultXml);
		List<BaseInfo> list = PropertyUtil.parseBeansToList(BaseInfo.class, resultXml);
		return list.get(0);
	}
	
	/**
	 * 感染部位
	 * <Request>
		<userCode>用户名</userCode>
		<admId>就诊Id</admId>
		<arcimId>医嘱项Id</arcimId>
		</Request>
	 * @param userCode
	 * @return
	 * @throws Exception
	 */
	public static List<AntBodyPart> listAntBodyPart(String userCode, String admId, String arcimId) throws Exception {
		Map params = new HashMap();
		params.put("userCode", userCode);
		params.put("admId", admId);
		params.put("arcimId", arcimId);
		
    	String serviceUrl = SettingManager.getServerUrl();
		
		String requestXml = PropertyUtil.buildRequestXml(params);
		String resultXml  = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_LIST_AntBodyPart, requestXml);
			
		LogMe.d(requestXml+"\n\n"+resultXml);
		List<AntBodyPart> list = PropertyUtil.parseBeansToList(AntBodyPart.class, resultXml);
		return list;
	}
	
	
	/**
	 * 抗生素使用目的
	 * <Request>
		<userCode>用户名</userCode>
		<admId>就诊Id</admId>
		<arcimId>医嘱项Id</arcimId>
		</Request>
	 * @param userCode
	 * @return
	 * @throws Exception
	 */
	public static List<AntReason> listAntReason(String userCode, String admId, String arcimId) throws Exception {
		Map params = new HashMap();
		params.put("userCode", userCode);
		params.put("admId", admId);
		params.put("arcimId", arcimId);
		
    	String serviceUrl = SettingManager.getServerUrl();
		
		String requestXml = PropertyUtil.buildRequestXml(params);
		String resultXml  = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_LIST_AntReason_2, requestXml);
			
		LogMe.d(requestXml+"\n\n"+resultXml);
		List<AntReason> list = PropertyUtil.parseBeansToList(AntReason.class, resultXml);
		return list;
	}
	
	
	/**
	 * 获取使用目的－指征
	 * <Request>
		<userCode>用户名</userCode>
		<admId>就诊Id</admId>
		<arcimId>医嘱项Id</arcimId>
		<reasonId>用药目的Id</reasonId>
		</Request>
	 * @param userCode
	 * @return
	 * @throws Exception
	 */
	public static List<AntIndication> listAntIndication(String userCode, String admId, String arcimId, String reasonId) throws Exception {
		Map params = new HashMap();
		params.put("userCode", userCode);
		params.put("admId", admId);
		params.put("arcimId", arcimId);
		params.put("reasonId", reasonId);
    	String serviceUrl = SettingManager.getServerUrl();
		
		String requestXml = PropertyUtil.buildRequestXml(params);
		String resultXml  = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_LIST_AntIndication, requestXml);
			
		LogMe.d(requestXml+"\n\n"+resultXml);
		List<AntIndication> list = PropertyUtil.parseBeansToList(AntIndication.class, resultXml);
		return list;
	}
	
	
	/**
	 * 获取手术预防用药时间
	 * <Request>
		<userCode>用户名</userCode>
		<admId>就诊Id</admId>
		<arcimId>医嘱项Id</arcimId>
		</Request>
	 * @param userCode
	 * @return
	 * @throws Exception
	 */
	public static List<OperaTime> listOperaTime(String userCode, String admId, String arcimId) throws Exception {
		Map params = new HashMap();
		params.put("userCode", userCode);
		params.put("admId", admId);
		params.put("arcimId", arcimId);
		
    	String serviceUrl = SettingManager.getServerUrl();
		
		String requestXml = PropertyUtil.buildRequestXml(params);
		String resultXml  = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_LIST_OperaTime, requestXml);
			
		LogMe.d(requestXml+"\n\n"+resultXml);
		List<OperaTime> list = PropertyUtil.parseBeansToList(OperaTime.class, resultXml);
		return list;
	}
	
	
	/**
	 * 获取获取药敏结果
	 * <Request>
		<userCode>用户名</userCode>
		<admId>就诊Id</admId>
		<arcimId>医嘱项Id</arcimId>
		</Request>
	 * @param userCode
	 * @return
	 * @throws Exception
	 */
	public static List<OrderSuscept> listOrderSuscept(String userCode, String admId, String arcimId) throws Exception {
		Map params = new HashMap();
		params.put("userCode", userCode);
		params.put("admId", admId);
		params.put("arcimId", arcimId);
		
    	String serviceUrl = SettingManager.getServerUrl();
		
		String requestXml = PropertyUtil.buildRequestXml(params);
		String resultXml  = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_LIST_OrderSuscept, requestXml);
			
		LogMe.d(requestXml+"\n\n"+resultXml);
		List<OrderSuscept> list = PropertyUtil.parseBeansToList(OrderSuscept.class, resultXml);
		return list;
	}
	
	/**
	 * 获取手术申请
	 * <Request>
		<userCode>用户名</userCode>
		<admId>就诊Id</admId>
		<arcimId>医嘱项Id</arcimId>
		</Request>
	 * @param userCode
	 * @return
	 * @throws Exception
	 */
	public static List<Operation> listOperation(String userCode, String admId, String arcimId) throws Exception {
		Map params = new HashMap();
		params.put("userCode", userCode);
		params.put("admId", admId);
		params.put("arcimId", arcimId);
		
    	String serviceUrl = SettingManager.getServerUrl();
		
		String requestXml = PropertyUtil.buildRequestXml(params);
		String resultXml  = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_LIST_Operation, requestXml);
			
		LogMe.d(requestXml+"\n\n"+resultXml);
		List<Operation> list = PropertyUtil.parseBeansToList(Operation.class, resultXml);
		return list;
	}
	
	/**
	 * 获取会诊科室
	 * <Request>
		 <userCode>用户名</userCode>
		 <admId>就诊Id</admId>
		 <arcimId>医嘱项Id</arcimId>
	   </Request>
	 * @param userCode
	 * @return
	 * @throws Exception
	 */
	public static List<ConsultLoc> listConsultLoc(String userCode, String admId, String arcimId) throws Exception {
		Map params = new HashMap();
		params.put("userCode", userCode);
		params.put("admId", admId);
		params.put("arcimId", arcimId);
		
    	String serviceUrl = SettingManager.getServerUrl();
		
		String requestXml = PropertyUtil.buildRequestXml(params);
		String resultXml  = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_LIST_ConsultLoc, requestXml);
	    
		LogMe.d(requestXml+"\n\n"+resultXml);
		List<ConsultLoc> list = PropertyUtil.parseBeansToList(ConsultLoc.class, resultXml);
		return list;
	}
	
	
	/**
	 * 获取会诊doct
	 *<Request>
		<userCode>用户名</userCode>
		<admId>就诊Id</admId>
		<arcimId>医嘱项Id</arcimId>
		<consLocId>会诊科室Id</consLocId>
	  </Request>
	 * @param userCode
	 * @return
	 * @throws Exception
	 */
	public static List<ConsultDoc> listConsultDoc(String userCode, String admId, String arcimId, String consLocId) throws Exception {
		Map params = new HashMap();
		params.put("userCode", userCode);
		params.put("admId", admId);
		params.put("arcimId", arcimId);
		params.put("consLocId", consLocId);
    	String serviceUrl = SettingManager.getServerUrl();
		
		String requestXml = PropertyUtil.buildRequestXml(params);
		String resultXml  = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_LIST_ConsultDoc, requestXml);
	    
		LogMe.d(requestXml+"\n\n"+resultXml);
		List<ConsultDoc> list = PropertyUtil.parseBeansToList(ConsultDoc.class, resultXml);
		return list;
	}
	
	
	/**
	 * 申请信息－用法
	 * <Request>
		<userCode>用户名</userCode>
		<admId>就诊Id</admId>
		<arcimId>医嘱项Id</arcimId>
		<consLocId>会诊科室Id</consLocId>
		</Request>
	 * @param userCode
	 * @return
	 * @throws Exception
	 */
	public static List<Instruction> listInstruction(String userCode,String admId,String arcimId,String consLocId) throws Exception {
		Map params = new HashMap();
		params.put("userCode", userCode);
		params.put("admId", admId);
		params.put("arcimId", arcimId);
		params.put("consLocId", consLocId);
		
    	String serviceUrl = SettingManager.getServerUrl();
		
		String requestXml = PropertyUtil.buildRequestXml(params);
		String resultXml  = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_LIST_Instruction_apply, requestXml);
	    
		LogMe.d(requestXml+"\n\n"+resultXml);
		List<Instruction> list = PropertyUtil.parseBeansToList(Instruction.class, resultXml);
		return list;
	}
	
	

	public static List<PopMessage> checkAndloadPopMessage(ParamAnti form) throws Exception {
	    String serviceUrl = SettingManager.getServerUrl();
		
		String requestXml = PropertyUtil.buildRequestXml(form);
		String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_POST_ParamAnti_veryf, requestXml);
			
		LogMe.d(requestXml+"\n--------------------------\n"+resultXml);
		List<PopMessage> list = PropertyUtil.parseBeansToList(PopMessage.class, resultXml);
		
		return list ;
	}
	
//	/**
//	 *2.10.20增加/更新前的校验数据的有效性
//	 * @param userCode
//	 * @return
//	 * @throws Exception
//	 */
//	public static List<PopMessage> checkAndloadPopMessage(FormArcimItem form) throws Exception {
//	    String serviceUrl = SettingManager.getServerUrl();
//		
//		String requestXml = PropertyUtil.buildRequestXml(form);
//		String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_LIST_PopMessage, requestXml);
//			
//		LogMe.d(requestXml+"\n--------------------------\n"+resultXml);
//		List<PopMessage> list = PropertyUtil.parseBeansToList(PopMessage.class, resultXml);
//		
//		return list ;
//	}
	

	public static MainReturn postParamAnti(ParamAnti form) throws Exception {
        String serviceUrl = SettingManager.getServerUrl();
		
		String requestXml = PropertyUtil.buildRequestXml(form);
		String resultXml  = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_POST_ParamAnti_MainReturn, requestXml);
	    
		LogMe.d(requestXml+"\n\n"+resultXml);
		List<MainReturn> list = PropertyUtil.parseBeansToList(MainReturn.class, resultXml);
		
		return list.get(0);
	}

	public static AntReasonData loadAntReasonData(String userCode, String departmentId,
			String admId, String arcimId, String userReasonId, String antAppId) throws Exception {
		AntReasonData data = null ;
		Map params = new HashMap();
		params.put("userCode", userCode);
		params.put("departmentId", departmentId);
		params.put("admId", admId);
		params.put("arcimId", arcimId);
		params.put("userReasonId", userReasonId);
		params.put("antAppId", antAppId);
		
    	String serviceUrl = SettingManager.getServerUrl();
		
		String requestXml = PropertyUtil.buildRequestXml(params);
		String resultXml  = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_DETAIL_AntReasonData, requestXml);
	    
		LogMe.d(requestXml+"\n\n"+resultXml);
		List<AntReasonData> list = PropertyUtil.parseBeansToList(AntReasonData.class, resultXml);
		
		return list.get(0);
	}
}
