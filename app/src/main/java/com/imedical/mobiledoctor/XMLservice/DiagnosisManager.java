package com.imedical.mobiledoctor.XMLservice;



import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.api.WsApiUtil;
import com.imedical.mobiledoctor.entity.BaseBean;
import com.imedical.mobiledoctor.entity.DiagItem;
import com.imedical.mobiledoctor.entity.DiagStatus;
import com.imedical.mobiledoctor.entity.DiagTabs;
import com.imedical.mobiledoctor.entity.DiagType;
import com.imedical.mobiledoctor.entity.Diagnosis;
import com.imedical.mobiledoctor.util.LogMe;
import com.imedical.mobiledoctor.util.PropertyUtil;

import org.dom4j.DocumentException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiagnosisManager {
	
	 public static List<DiagStatus> listDiagStatus(String userCode,String admId) throws Exception {
	    Map params = new HashMap();
	    params.put("userCode", userCode);
	    params.put("admId", admId);
	    
		List<DiagStatus> list = null;
    	String serviceUrl = SettingManager.getServerUrl();
		
		String requestXml = PropertyUtil.buildRequestXml(params);
		String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_LIST_DiagStatus, requestXml);
			
		LogMe.d(requestXml+"\n\n"+resultXml);
		list = PropertyUtil.parseBeansToList(DiagStatus.class, resultXml);
			
		return list;
	 }
	 
	 public static List<Diagnosis> listDiagnosisCurr(Map params) throws Exception {
	    	List<Diagnosis> list = null;
			
	    	String serviceUrl = SettingManager.getServerUrl();
			
			String requestXml = PropertyUtil.buildRequestXml(params);
			String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_LIST_Diagnosis, requestXml);
				
			LogMe.d(requestXml+"\n\n"+resultXml);
			list = PropertyUtil.parseBeansToList(Diagnosis.class, resultXml);
				
			return list;
		 }

	 public static List<Diagnosis> listDiagnosisHis(Map params) throws Exception {
	    	List<Diagnosis> list = null;
			
	    	String serviceUrl = SettingManager.getServerUrl();
			
			String requestXml = PropertyUtil.buildRequestXml(params);
			String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_LIST_Diagnosis_HIS, requestXml);
				
			LogMe.d(requestXml+"\n\n"+resultXml);
			list = PropertyUtil.parseBeansToList(Diagnosis.class, resultXml);
				
			return list;
		 }

	    public static BaseBean saveDiagnosis(String userCode
								    		,String admId
								    		,String departmentId
								    		,String diagICDId
								    		,String diagTypeId
								    		,String diagStatId
	    		                            ,String diagNote) throws DocumentException,Exception {
	    	BaseBean bean = null ;
			Map map = new HashMap();
			map.put("userCode", userCode);
			map.put("admId", admId);
			map.put("departmentId", departmentId);
			map.put("diagICDId", diagICDId);
			map.put("diagTypeId", diagTypeId);
			map.put("diagStatId", diagStatId);
			map.put("diagNote", diagNote);
	    	String serviceUrl = SettingManager.getServerUrl();
			
			String requestXml = PropertyUtil.buildRequestXml(map);
			String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_SAVE_Diagnosis, requestXml);
			
			LogMe.d(requestXml+"\n\n"+resultXml);
			bean = PropertyUtil.parseToBaseInfo(resultXml);
			
			return bean;
		 }
	    /**
	     * <Request>
			<userCode>用户名</userCode>
			<admId>患者就诊id</admId>
			<departmentId>科室Id</departmentId>
			<diagId>诊断Id</diagId>
			<diagNote>诊断备注</diagNote>
			</Request>
	     * @return
	     * @throws DocumentException
	     * @throws Exception
	     */
	    public static BaseBean updateDiagnosis( String  userCode
									    		,String admId
									    		,String departmentId
									    		,String diagId
									            ,String diagNote) throws DocumentException,Exception {
			BaseBean bean = null ;
			Map map = new HashMap();
			map.put("userCode", userCode);
			map.put("admId", admId);
			map.put("departmentId", departmentId);
			map.put("diagId", diagId);
			map.put("diagNote", diagNote);
			String serviceUrl = SettingManager.getServerUrl();
			
			String requestXml = PropertyUtil.buildRequestXml(map);
			String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_UPDATE_Diagnosis, requestXml);
			
			LogMe.d(requestXml+"\n\n"+resultXml);
			bean = PropertyUtil.parseToBaseInfo(resultXml);
			
			return bean;

        }

	    /**
	     * <Request>
			<userCode>用户名</userCode>
			<admId>患者就诊id</admId>
			<tabId>诊断页签id</tabId>
			</Request>
	     * @return
	     * @throws Exception 
	     */
		public static List<DiagItem> loadDiagItemByTab(String userCode,String admId,String tabId) throws Exception {
            List<DiagItem> list = null;
			Map params = new HashMap();
			params.put("userCode", userCode);
			params.put("admId", admId);
			params.put("tabId", tabId);
			
	    	String serviceUrl = SettingManager.getServerUrl();
			
			String requestXml = PropertyUtil.buildRequestXml(params);
			String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CDOE_LIS_Item_of_DiaTabs, requestXml);
				
			LogMe.d(requestXml+"\n\n"+resultXml);
			list = PropertyUtil.parseBeansToList(DiagItem.class,"DiagTabs",resultXml);
				
			return list;
		}
		
	    /**
	     * <Request>
			<userCode>用户名</userCode>
			<admId>患者就诊id</admId>
			<diagCode>诊断检索码/诊断代码/诊断简拼</diagCode>
			</Request>
	     * @return
	     * @throws Exception 
	     */
		public static List<DiagItem> loadDiagItem(String userCode,String admId,String diagCode) throws Exception {
            List<DiagItem> list = null;
			Map params = new HashMap();
			params.put("userCode", userCode);
			params.put("admId", admId);
			params.put("diagCode", diagCode);
			
	    	String serviceUrl = SettingManager.getServerUrl();
			
			String requestXml = PropertyUtil.buildRequestXml(params);
			String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_LIST_DiagItem, requestXml);
				
			LogMe.d(requestXml+"\n\n"+resultXml);
			list = PropertyUtil.parseBeansToList(DiagItem.class, resultXml);
				
			return list;
		}
		
	    /**
	     * <Request>
			<userCode>用户名</userCode>
			<admId>患者就诊id</admId>
			<departmentId>科室id</departmentId>
			</Request>
	     * @return
	     * @throws Exception 
	     */
		public static List<DiagTabs> loadDiagTabs(String userCode,String admId,String departmentId) throws Exception {
            List<DiagTabs> list = null;
			Map params = new HashMap();
			params.put("userCode", userCode);
			params.put("admId", admId);
			params.put("departmentId", departmentId);
			
	    	String serviceUrl = SettingManager.getServerUrl();
			
			String requestXml = PropertyUtil.buildRequestXml(params);
			String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_LIST_DiaTabs, requestXml);
				
			LogMe.d(requestXml+"\n\n"+resultXml);
			list = PropertyUtil.parseBeansToList(DiagTabs.class, resultXml);
				
			return list;
		}

		public static List<DiagType> loadDiagType(String userCode, String admId) throws Exception {
			List<DiagType> list = null;
			Map params = new HashMap();
			params.put("userCode", userCode);
			params.put("admId", admId);
			
	    	String serviceUrl = SettingManager.getServerUrl();
			
			String requestXml = PropertyUtil.buildRequestXml(params);
			String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_LIST_DiagType, requestXml);
				
			LogMe.d(requestXml+"\n\n"+resultXml);
			list = PropertyUtil.parseBeansToList(DiagType.class, resultXml);
				
			return list;
		}

		/**
		 * 
		 * <Request>
			<userCode>用户名</userCode>
			<admId>患者就诊id</admId>
			<departmentId>科室Id</departmentId>
			<diagId>诊断Id</diagId>
			</Request>
		 * @return
		 * @throws Exception
		 */
		public static BaseBean delDiagItem(String userCode,String admId,String departmentId,String diagId) throws Exception {
			Map params = new HashMap();
			params.put("userCode", userCode);
			params.put("admId", admId);
			params.put("departmentId", departmentId);
			params.put("diagId", diagId);
			
	    	String serviceUrl = SettingManager.getServerUrl();
			
			String requestXml = PropertyUtil.buildRequestXml(params);
			String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_DELETE_DiagItem, requestXml);
				
			LogMe.d(requestXml+"\n\n"+resultXml);
			BaseBean b = PropertyUtil.parseToBaseInfo(resultXml);
			
			return b;
		}

  }
