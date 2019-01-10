package com.imedical.mobiledoctor.XMLservice;

import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.api.WsApiUtil;
import com.imedical.mobiledoctor.entity.AntBodyPart;
import com.imedical.mobiledoctor.entity.AntIndication;
import com.imedical.mobiledoctor.entity.AntReason;
import com.imedical.mobiledoctor.entity.AntReasonData;
import com.imedical.mobiledoctor.entity.ArcimItem;
import com.imedical.mobiledoctor.entity.BaseBean;
import com.imedical.mobiledoctor.entity.BaseInfo;
import com.imedical.mobiledoctor.entity.ConsultDoc;
import com.imedical.mobiledoctor.entity.ConsultLoc;
import com.imedical.mobiledoctor.entity.DeptInfo;
import com.imedical.mobiledoctor.entity.FlowRateUnit;
import com.imedical.mobiledoctor.entity.FormArcimItem;
import com.imedical.mobiledoctor.entity.Frequency;
import com.imedical.mobiledoctor.entity.Instruction;
import com.imedical.mobiledoctor.entity.LabelValue;
import com.imedical.mobiledoctor.entity.MainReturn;
import com.imedical.mobiledoctor.entity.OETabs;
import com.imedical.mobiledoctor.entity.OperaTime;
import com.imedical.mobiledoctor.entity.Operation;
import com.imedical.mobiledoctor.entity.OrderItem;
import com.imedical.mobiledoctor.entity.OrderSuscept;
import com.imedical.mobiledoctor.entity.OtherInfo;
import com.imedical.mobiledoctor.entity.ParamAnti;
import com.imedical.mobiledoctor.entity.PopMessage;
import com.imedical.mobiledoctor.entity.Priority;
import com.imedical.mobiledoctor.entity.SkinAction;
import com.imedical.mobiledoctor.util.LogMe;
import com.imedical.mobiledoctor.util.PropertyUtil;
import com.imedical.mobiledoctor.util.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArcimItemManager {
	
	
	public static List<AntReason> loadAntib(String userCode) throws Exception {
		Map params = new HashMap();
		params.put("userCode", userCode);
		
    	String serviceUrl = SettingManager.getServerUrl();
		
		String requestXml = PropertyUtil.buildRequestXml(params);
		String resultXml  = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_LIST_AntReason, requestXml);
			
		LogMe.d(requestXml+"\n\n"+resultXml);
		List<AntReason> list = PropertyUtil.parseBeansToList(AntReason.class, resultXml);
		return list;
	}
	
	/**
	 *2.10.21审核医嘱前校验数据有效性
	 * <Request>
		<userCode>用户名</userCode>
		<admId>就诊Id</admId>
		<departmentId>科室Id</departmentId>
		</Request>
	 * @return
	 * @throws Exception
	 */
	public static List<PopMessage> checkAndloadVerifyPopMessage(String userCode,String admId,String departmentId) throws Exception {
		Map params = new HashMap();
		params.put("userCode", userCode);
		params.put("admId", admId);
		params.put("departmentId", departmentId);
		
		String serviceUrl = SettingManager.getServerUrl();
		
		String requestXml = PropertyUtil.buildRequestXml(params);
		String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_LIST_PopMessage_VERYFY, requestXml);
			
		LogMe.d(requestXml+"\n--------------------------\n"+resultXml);
		List<PopMessage> list = PropertyUtil.parseBeansToList(PopMessage.class, resultXml);
		
		return list ;
	}
	

	public static List<PopMessage> checkAndloadPopMessage(FormArcimItem form) throws Exception {
	    String serviceUrl = SettingManager.getServerUrl();
		
		String requestXml = PropertyUtil.buildRequestXml(form);
		String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_LIST_PopMessage, requestXml);
			
		LogMe.d(requestXml+"\n--------------------------\n"+resultXml);
		List<PopMessage> list = PropertyUtil.parseBeansToList(PopMessage.class, resultXml);
		
		return list ;
	}

	
	/**
	 * 获取输液流速代码
	 * @param userCode
	 * @return
	 * @throws Exception
	 */
	public static List<FlowRateUnit> loadAntFlowRateUnit(String userCode) throws Exception {
		Map params = new HashMap();
		params.put("userCode", userCode);
		
    	String serviceUrl = SettingManager.getServerUrl();
		
		String requestXml = PropertyUtil.buildRequestXml(params);
		String resultXml  = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_LIST_FlowRateUnit, requestXml);
			
		LogMe.d(requestXml+"\n\n"+resultXml);
		List<FlowRateUnit> list = PropertyUtil.parseBeansToList(FlowRateUnit.class, resultXml);
		return list;
	}

	
	/**
	 * 获取抗生素用药原因
	 * @param userCode
	 * @return
	 * @throws Exception
	 */
	public static List<AntReason> loadAntReason(String userCode) throws Exception {
		Map params = new HashMap();
		params.put("userCode", userCode);
		
    	String serviceUrl = SettingManager.getServerUrl();
		
		String requestXml = PropertyUtil.buildRequestXml(params);
		String resultXml  = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_LIST_AntReason, requestXml);
			
		LogMe.d(requestXml+"\n\n"+resultXml);
		List<AntReason> list = PropertyUtil.parseBeansToList(AntReason.class, resultXml);
		return list;
	}
	
	/**
	 * <Request>
		<userCode>用户名</userCode>
		<admId>就诊Id</admId>
		</Request>
	 * @param userCode
	 * @param admId
	 * @return
	 * @throws Exception 
	 */
	public static List<ArcimItem> loadArcimItemListSaved(String userCode, String admId) throws Exception {
		
		Map params = new HashMap();
		params.put("userCode", userCode);
		params.put("admId", admId);
		
		String serviceUrl = SettingManager.getServerUrl();
		String requestXml = PropertyUtil.buildRequestXml(params);
		String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_LIST_INPUT_ArcimItem, requestXml);
			
		LogMe.d(requestXml+"\n\n"+resultXml);
		List<ArcimItem> list = PropertyUtil.parseBeansToList(ArcimItem.class, resultXml);
		return list;
	}
	

	public static BaseBean saveArcimItem(FormArcimItem form) throws Exception {
		
    	String serviceUrl = SettingManager.getServerUrl();
		
		String requestXml = PropertyUtil.buildRequestXml(form);
		String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_SAVE_ArcimItem, requestXml);
			
		LogMe.d(requestXml+"\n--------------------------\n"+resultXml);
		BaseBean b = PropertyUtil.parseToBaseInfo(resultXml);
			
		return b;
	}
	
		/**
		 * <Request>
			<userCode>用户名</userCode>
			<admId>就诊Id</admId>
			<groupId>安全组Id</groupId>
			<departmentId>科室Id</departmentId>
			</Request>
		 * @param userCode
		 * @param admId
		 * @param departmentId
		 * @return
		 * @throws Exception 
		 */
		public static List<OETabs> loadOETabs(String userCode, String admId,String groupId,
				String departmentId) throws Exception {
			List<OETabs> list = null;
			Map params = new HashMap();
			params.put("userCode", userCode);
			params.put("admId", admId);
			params.put("groupId", groupId);
			params.put("departmentId", departmentId);
			
	    	String serviceUrl = SettingManager.getServerUrl();
			
			String requestXml = PropertyUtil.buildRequestXml(params);
			String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_LIST_OETabs, requestXml);
				
			LogMe.d(requestXml+"\n\n"+resultXml);
			list = PropertyUtil.parseBeansToList(OETabs.class, resultXml);
				
			return list;
		}
		
		/**
		 * <Request>
			<userCode>用户名</userCode>
			<admId>就诊Id</admId>
			<groupId>安全组Id</groupId>
			<departmentId>科室Id</departmentId>
			<tabId>一级页签Id</tabId>
			</Request>
		 * @param userCode
		 * @param admId
		 * @param departmentId
		 * @return
		 * @throws Exception 
		 */
		public static List<OETabs> loadOETabs_2(String userCode, String admId,String groupId,
				String departmentId,String tabId) throws Exception {
			
			Map params = new HashMap();
			params.put("userCode", userCode);
			params.put("admId", admId);
			params.put("groupId", groupId);
			params.put("departmentId", departmentId);
			params.put("tabId", tabId);
			
	    	String serviceUrl = SettingManager.getServerUrl();
			
			String requestXml = PropertyUtil.buildRequestXml(params);
			String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_LIST_OETabs_2, requestXml);
				
			LogMe.d(requestXml+"\n\n"+resultXml);
			List<OETabs> list = PropertyUtil.parseBeansToList(OETabs.class, resultXml);
			return list;
		}

		/**
		 * <Request>
			<userCode>用户名</userCode>
			<admId>就诊Id</admId>
			<itemCode>医嘱代码</itemCode>
			<groupId>安全组Id</groupId>
			<departmentId>科室Id</departmentId>
			</Request>
		 * @param userCode
		 * @param admId
		 * @return
		 * @throws Exception 
		 */
		public static List<ArcimItem> loadArcimItem(String userCode, String admId,String itemCode,String groupId,String departmentId) throws Exception {
			
			Map params = new HashMap();
			params.put("userCode", userCode);
			params.put("admId", admId);
			params.put("itemCode", itemCode);
			params.put("groupId", groupId);
			params.put("departmentId", departmentId);
			
	    	String serviceUrl = SettingManager.getServerUrl();
			
			String requestXml = PropertyUtil.buildRequestXml(params);
			String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_LIST_ArcimItem, requestXml);
				
			LogMe.d(requestXml+"\n\n"+resultXml);
			List<ArcimItem> list = PropertyUtil.parseBeansToList(ArcimItem.class, resultXml);
			return list;
		}

		/**
		 * <Request>
			<userCode>用户名</userCode>
			</Request>
		 * @param userCode
		 * @return
		 * @throws Exception 
		 */
		public static List<Priority> loadPriority(String userCode) throws Exception {
			
			Map params = new HashMap();
			params.put("userCode", userCode);
			
	    	String serviceUrl = SettingManager.getServerUrl();
			
			String requestXml = PropertyUtil.buildRequestXml(params);
			String resultXml  = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_LIST_Priority, requestXml);
				
			LogMe.d(requestXml+"\n\n"+resultXml);
			List<Priority> list = PropertyUtil.parseBeansToList(Priority.class, resultXml);
			return list;
		}

		public static List<Instruction> loadInstruction(String userCode) throws Exception {
			Map params = new HashMap();
			params.put("userCode", userCode);
			
	    	String serviceUrl = SettingManager.getServerUrl();
			
			String requestXml = PropertyUtil.buildRequestXml(params);
			String resultXml  = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_LIST_Instruction, requestXml);
				
			LogMe.d(requestXml+"\n\n"+resultXml);
			List<Instruction> list = PropertyUtil.parseBeansToList(Instruction.class, resultXml);
			return list;
		}

		public static List<Frequency> loadFrequency(String userCode) throws Exception {
			Map params = new HashMap();
			params.put("userCode", userCode);
			
	    	String serviceUrl = SettingManager.getServerUrl();
			
			String requestXml = PropertyUtil.buildRequestXml(params);
			String resultXml  = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_LIST_Frequency, requestXml);
				
			LogMe.d(requestXml+"\n\n"+resultXml);
			List<Frequency> list = PropertyUtil.parseBeansToList(Frequency.class, resultXml);
			return list;
		}
		
		public static List<SkinAction> loadSkinAction(String userCode) throws Exception {
			Map params = new HashMap();
			params.put("userCode", userCode);
			
	    	String serviceUrl = SettingManager.getServerUrl();
			
			String requestXml = PropertyUtil.buildRequestXml(params);
			String resultXml  = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_LIST_SkinAction, requestXml);
				
			LogMe.d(requestXml+"\n\n"+resultXml);
			List<SkinAction> list = PropertyUtil.parseBeansToList(SkinAction.class, resultXml);
			return list;
		}

		/**
		 * BIZ_CODE_DETAIL_ArcimItem = "50909";//获取医嘱项目的默认参数
		 * <Request>
			<userCode>用户名</userCode>
			<admId>就诊Id</admId>
			<arcItemId>医嘱项Id</arcItemId>
			<departmentId>登录科室Id</departmentId>
			</Request>
		 * @return
		 * @throws Exception 
		 */
		public static ArcimItem loadArcimItemById(String userCode,String admId,String arcItemId,String departmentId) throws IndexOutOfBoundsException,Exception {
			Map params = new HashMap();
			params.put("userCode", userCode);
			params.put("admId", admId);
			params.put("arcItemId", arcItemId);
			params.put("departmentId", departmentId);
			
	    	String serviceUrl = SettingManager.getServerUrl();
			
			String requestXml = PropertyUtil.buildRequestXml(params);
			String resultXml  = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_DETAIL_ArcimItem, requestXml);
				
			LogMe.d(requestXml+"\n\n"+resultXml);
			List<ArcimItem> list = PropertyUtil.parseBeansToList(ArcimItem.class, resultXml);
			return list.get(0);
		}

		/**
		 * BIZ_CODE_DETAIL_ArcimItem = "50909";//获取医嘱项目的默认参数
		 * <Request>
			<userCode>用户名</userCode>
			<admId>就诊Id</admId>
			<showIndex>当前医嘱在临时数据中的Id</showIndex>
			<departmentId>登录科室Id</departmentId>
			</Request>
		 * @return
		 * @throws Exception 
		 */
		public static ArcimItem loadArcimItemByShowIndex(String userCode,String admId,String showIndex,String departmentId) throws Exception {
			Map params = new HashMap();
			params.put("userCode", userCode);
			params.put("admId", admId);
			params.put("showIndex", showIndex);
			params.put("departmentId", departmentId);
			
	    	String serviceUrl = SettingManager.getServerUrl();
			
			String requestXml = PropertyUtil.buildRequestXml(params);
			String resultXml  = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_DETAIL_SAVED_ArcimItem, requestXml);
				
			LogMe.d(requestXml+"\n\n"+resultXml);
			List<ArcimItem> list = PropertyUtil.parseBeansToList(ArcimItem.class, resultXml);
			return list.get(0);
		}
		
		/**
		 * <Request>
			<userCode>用户名</userCode>
			<admId>就诊Id</admId>
			<groupId>安全组Id</groupId>
			<departmentId>科室Id</departmentId>
			<tabId>一级页签Id</tabId>
			<subTabId>二级页签Id</subTabId>
			</Request>
		 * @throws Exception 
		 */
		public static List<LabelValue> loadArciItemByTab2(String userCode,String admId,String groupId,String departmentId,String tabId,String subTabId) throws Exception {
			Map params = new HashMap();
			params.put("userCode", userCode);
			params.put("admId", admId);
			params.put("groupId", groupId);
			params.put("departmentId", departmentId);
			params.put("tabId", tabId);
			params.put("subTabId", subTabId);
			
	    	String serviceUrl = SettingManager.getServerUrl();
			
			String requestXml = PropertyUtil.buildRequestXml(params);
			String resultXml  = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_LIST_OETabs_info, requestXml);
				
			LogMe.d(requestXml+"\n\n"+resultXml);
			List<OETabs> list = PropertyUtil.parseBeansToList(OETabs.class, resultXml);
			
			List<LabelValue> list2 = new ArrayList();
			for(OETabs t:list){
				list2.add(new LabelValue(t.arcDesc,t.arcRowId,t));
			}
			return list2;
		}

		/**
		 * <Request>
			<userCode>用户名</userCode>
			<admId>就诊Id</admId>
			<showIndex>临时数据中的Id/当为空时删除所有临时数据,当为空时删除提交前要提醒是否清除列表中的医嘱项目</showIndex>
			</Request>
		 * @throws Exception 
		 */
		public static BaseBean delete(String userCode,String admId,String showIndex) throws Exception {
			Map params = new HashMap();
			params.put("userCode", userCode);
			params.put("admId", admId);
			params.put("showIndex", showIndex);
			
	    	String serviceUrl = SettingManager.getServerUrl();
			
			String requestXml = PropertyUtil.buildRequestXml(params);
			String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_DEL_ArcimItem, requestXml);
				
			LogMe.d(requestXml+"\n\n"+resultXml);
			BaseBean b = PropertyUtil.parseToBaseInfo(resultXml);
				
			return b;
		}

		/**
		 * 
		 * <Request>
			<userCode>用户名</userCode>
			<admId>就诊Id</admId>
			<departmentId>科室Id</departmentId>
			</Request>
		 * @throws Exception 
		 */
		public static BaseBean verifyArcimItem(String userCode,String admId,String departmentId) throws Exception {
			Map params = new HashMap();
			params.put("userCode", userCode);
			params.put("admId", admId);
			params.put("departmentId", departmentId);
			
	    	String serviceUrl = SettingManager.getServerUrl();
			
			String requestXml = PropertyUtil.buildRequestXml(params);
			String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_VERIFY_ArcimItem, requestXml);
				
			LogMe.d(requestXml+"\n\n"+resultXml);
			BaseBean b = PropertyUtil.parseToBaseInfo(resultXml);
				
			return b;
		}

		/**
		 * <Request>
			<userCode>用户名</userCode>
			<admId>就诊Id</admId>
			<instrId>用法Id</instrId>
			<arcItemId>医嘱项Id</arcItemId>
			<departmentId>登录科室Id</departmentId>
			<priorId>医嘱优先级Id</priorId>
		   </Request>
		 * @return
		 * @throws Exception 
		 */
		public static List<DeptInfo> loadDeptAsy(String userCode, String admId,String instrId
				                          ,String arcItemId,String departmentId,String priorId) throws Exception {
			
			Map params = new HashMap();
			params.put("userCode", userCode);
			params.put("admId", admId);
			params.put("instrId", instrId);
			params.put("arcItemId", arcItemId);
			params.put("departmentId", departmentId);
			params.put("priorId", priorId);
			
	    	String serviceUrl = SettingManager.getServerUrl();
			
			String requestXml = PropertyUtil.buildRequestXml(params);
			String resultXml  = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_LIST_DEPT_BY_PR_IN, requestXml);
				
			LogMe.d(requestXml+"\n\n"+resultXml);
			List<DeptInfo> list = PropertyUtil.parseBeansToList(DeptInfo.class, resultXml);
			return list;
		}

		public static OtherInfo loadFirstTimeOther(String userCode, String admId,
												   String arcItemId, String priorId, String freqId,
												   String ordStartDate, String ordStartTime, String doseQty,
												   String doseUomId, String recLocId, String eventSource) throws Exception {
			if(Validator.isBlank(ordStartDate)){
				ordStartDate = "";
			}
			if(Validator.isBlank(ordStartTime)){
				ordStartTime = "";
			}
			if(Validator.isBlank(doseQty)){
				doseQty = "";
			}
			
			Map params = new HashMap();
			params.put("userCode", userCode);
			params.put("admId", admId);
			params.put("arcItemId", arcItemId);
			params.put("priorId", priorId);
			params.put("freqId", freqId);
			params.put("ordStartDate", ordStartDate);
			
			params.put("ordStartTime", ordStartTime);
			params.put("doseQty", doseQty);
			params.put("doseUomId", doseUomId);
			params.put("recLocId", recLocId);
			params.put("eventSource", eventSource);
			
	    	String serviceUrl = SettingManager.getServerUrl();
			
			String requestXml = PropertyUtil.buildRequestXml(params);
			String resultXml  = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_DETAIL_FISTTIME_INFO, requestXml);
				
			LogMe.d(requestXml+"\n\n"+resultXml);
			List<OtherInfo> list = PropertyUtil.parseBeansToList(OtherInfo.class, resultXml);
			
			return list.get(0);
		}

		
  }
