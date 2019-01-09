package com.imedical.mobiledoctor.XMLservice;


import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.api.WsApiUtil;
import com.imedical.mobiledoctor.entity.AntAppData;
import com.imedical.mobiledoctor.entity.AntAppInfo;
import com.imedical.mobiledoctor.entity.BaseBean;
import com.imedical.mobiledoctor.entity.ConsultData;
import com.imedical.mobiledoctor.entity.ConsultInfo;
import com.imedical.mobiledoctor.entity.DepartmentInfo;
import com.imedical.mobiledoctor.entity.PatientInfo;
import com.imedical.mobiledoctor.entity.ReportData;
import com.imedical.mobiledoctor.entity.SeeDoctorRecord;
import com.imedical.mobiledoctor.util.LogMe;
import com.imedical.mobiledoctor.util.PropertyUtil;

import org.dom4j.DocumentException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BusyManager {
	



	
	public static List<AntAppData> listAddressBook(String userCode, String departmentId) throws Exception {
		Map map = new HashMap();
		map.put("userCode", userCode);
		map.put("departmentId", departmentId);
		String serviceUrl = SettingManager.getServerUrl();
		
		String requestXml = PropertyUtil.buildRequestXml(map);
		String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_LIST_ADDRESS_BOOK, requestXml);
		List<AntAppData> list = PropertyUtil.parseBeansToList(AntAppData.class, resultXml);
		
		return list ;
	}
	/**
	 * <Request>
		<userCode>用户名</userCode>
		<departmentId>登录科室Id</departmentId>
		<antAppId>抗生素申请Id</antAppId>
		<appStatus>U/审核，R/拒绝</appStatus>
		</Request>
	 * @return
	 * @throws Exception
	 */
	public static BaseBean verifyApplyAntAppData(String userCode, String departmentId, String antAppId, String appStatus) throws Exception {
		Map map = new HashMap();
		map.put("userCode", userCode);
		map.put("departmentId", departmentId);
		map.put("antAppId", antAppId);
		map.put("appStatus", appStatus);
		String serviceUrl = SettingManager.getServerUrl();
		
		String requestXml = PropertyUtil.buildRequestXml(map);
		String resultXml  = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_VERYFY_AntAppInfo, requestXml);
		BaseBean b = PropertyUtil.parseToBaseInfo(resultXml);
		
		return b ;
	}

	//抗生素审核详细信息
	public static AntAppInfo loadAntAppInfo(String userCode, String departmentId, String antAppId) throws Exception {

		Map<String,String> map = new HashMap<String,String>();
		map.put("userCode", userCode);
		map.put("departmentId", departmentId);
		map.put("antAppId", antAppId);
		String serviceUrl = SettingManager.getServerUrl();

		String requestXml = PropertyUtil.buildRequestXml(map);
		String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_DETAIL_AntAppInfo, requestXml);
		List<AntAppInfo> list = PropertyUtil.parseBeansToList(AntAppInfo.class, resultXml);
		if(list.size()>0){
			return list.get(0);
		}else{
			throw new Exception("没有获取到数数据");
		}
	}
	
	/**
	<Request>
	<userCode>用户名</userCode>
	<departmentId>登录科室Id</departmentId>
	<startDate>开始日期：开始日期与结束日期为空时默认查询三天之内的抗生素用药申请</startDate>
	<endDate>结束日期</endDate>
	<appStatus>
	     抗生素使用申请状态，当为空时（即为A）查询结果为当前申请状态的申请记录（需要审核的），当为U时，查询已经处理过的申请
	</appStatus>
	<conLoad>继续加载的标识，第一次加载不传</conLoad>
	</Request>
	 * @return
	 * @throws Exception
	 */
	public static List<AntAppData> listAntAppData(String userCode, String departmentId, String startDate, String endDate, String appStatus, String conLoad) throws Exception {
		Map map = new HashMap();
		map.put("userCode", userCode);
		map.put("departmentId", departmentId);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("appStatus", appStatus);
		map.put("conLoad", conLoad);
		String serviceUrl = SettingManager.getServerUrl();
		
		String requestXml = PropertyUtil.buildRequestXml(map);
		String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_LIST_AntAppInfo, requestXml);
		List<AntAppData> list = PropertyUtil.parseBeansToList(AntAppData.class, resultXml);
		
		return list ;
	}
	
	/**
	 * 执行会诊记录计费
	 * <Request>
		<userCode>用户名</userCode>
		<departmentId>登录科室Id</departmentId>
		<conId>会诊申请Id</conId>
		</Request>
	 * @throws Exception

	 */
	public static BaseBean doConsultFee(String userCode
											,String departmentId
											,String conId) throws Exception {
		Map map = new HashMap();
		map.put("userCode", userCode);
		map.put("departmentId", departmentId);
		map.put("conId", conId);
		
    	String serviceUrl = SettingManager.getServerUrl();
		
		String requestXml = PropertyUtil.buildRequestXml(map);
		String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_DO_Consult_FEE, requestXml);
		
		LogMe.d(requestXml+"\n\n"+resultXml);
		BaseBean b = PropertyUtil.parseToBaseInfo(resultXml);
	    
		return b;
	}
	
	/**
	 * 会诊更新会诊意见 51503
	 * <Request>
		<userCode>用户名</userCode>
		<departmentId>登录科室Id</departmentId>
		<conId>会诊申请Id</conId>
		<conAttitude>会诊意见</conAttitude>
		<docAttitude>医生意见,0/1,不同意/同意</docAttitude>
		</Request>
	 * @throws Exception

	 */
	public static BaseBean updateConsultInfo(String userCode
											,String departmentId
											,String conId
											,String conAttitude
											,String docAttitude) throws Exception {
		Map map = new HashMap();
		map.put("userCode", userCode);
		map.put("departmentId", departmentId);
		map.put("conId", conId);
		map.put("conAttitude", conAttitude);
		map.put("docAttitude", docAttitude);
		
    	String serviceUrl = SettingManager.getServerUrl();
		
		String requestXml = PropertyUtil.buildRequestXml(map);
		String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_UPDATE_ConsultInfo, requestXml);
		
		LogMe.d(requestXml+"\n\n"+resultXml);
		BaseBean b = PropertyUtil.parseToBaseInfo(resultXml);
	    
		return b;
	}
	
	/**
	 <Request>
		<userCode>用户名</userCode>
		<departmentId>登录科室Id</departmentId>
		<startDate>开始日期</startDate>
		<endDate>结束日期</endDate>
		<conStatus>会诊状态，0/1，需处理/已处理</conStatus>
	 </Request>
	 * @return
	 * @throws Exception
	 */
	public static List<ConsultData> listConsultData(String userCode, String departmentId, String startDate, String endDate, String conStatus) throws Exception {
		Map map = new HashMap();
		map.put("userCode", userCode);
		map.put("departmentId", departmentId);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("conStatus", conStatus);
		
		String serviceUrl = SettingManager.getServerUrl();
		
		String requestXml = PropertyUtil.buildRequestXml(map);
		String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_LIST_ConsultData, requestXml);
		List<ConsultData> list = PropertyUtil.parseBeansToList(ConsultData.class, resultXml);
		
		return list ;
	}
	

	 public static BaseBean postReadInfo(String userCode, String departmentId, String ordItemId) throws DocumentException,Exception {
			Map map = new HashMap();
			map.put("userCode", userCode);
			map.put("departmentId", departmentId);
			map.put("ordItemId", ordItemId);
			
	    	String serviceUrl = SettingManager.getServerUrl();
			
			String requestXml = PropertyUtil.buildRequestXml(map);
			String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_READ_BASEINFO, requestXml);
			
			LogMe.d(requestXml+"\n\n"+resultXml);
			BaseBean b = PropertyUtil.parseToBaseInfo(resultXml);
		
			return b;
		 }
	 
	 /**
	    * 获取一级在部门列表
	    * 登录 http://172.23.6.209/dtHealth/web/MHC.PublicService.cls

	    */
		public static List<ReportData> listReportData(String userCode
													, String departmentId
													, String startDate
													, String endDate
													, String readFlag
													, String conLoad) throws Exception {
			Map map = new HashMap();
			map.put("userCode", userCode);
			map.put("departmentId", departmentId);
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			map.put("readFlag", readFlag);
			map.put("conLoad", conLoad);
			
			String serviceUrl = SettingManager.getServerUrl();
			
			String requestXml = PropertyUtil.buildRequestXml(map);
			LogMe.d("msg", requestXml);
			String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_LIST_ReportData, requestXml);
			LogMe.d("msg", resultXml);
			List<ReportData> list = PropertyUtil.parseBeansToList(ReportData.class, resultXml);
			
			return list ;
		}
	
	 public static PatientInfo loadPatientInfo(String userCode, String admId) throws DocumentException,Exception {
			Map map = new HashMap();
			map.put("userCode", userCode);
			map.put("admId", admId);
			
	    	String serviceUrl = SettingManager.getServerUrl();
			
			String requestXml = PropertyUtil.buildRequestXml(map);
			String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_CONTENT_PatientInfo, requestXml);
			
			LogMe.d(requestXml+"\n\n"+resultXml);
			List<PatientInfo> list = PropertyUtil.parseBeansToList(PatientInfo.class, resultXml);
		
			return list.get(0);
		 }
	 
	
 /**
    * 获取一级在部门列表
    * 登录 http://172.23.6.209/dtHealth/web/MHC.PublicService.cls
    */
	public static List<DepartmentInfo> listDept(String userCode) throws Exception {
		Map map = new HashMap();
		map.put("userCode", userCode);
		String serviceUrl = SettingManager.getServerUrl();
		
		String requestXml = PropertyUtil.buildRequestXml(map);
		String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_LIST_DEPT, requestXml);
		List<DepartmentInfo> list = PropertyUtil.parseBeansToList(DepartmentInfo.class, resultXml);
		
		return list ;
	}
	
	/**
	 * 获取一级在部门列表
	 * 登录 http://172.23.6.209/dtHealth/web/MHC.PublicService.cls

	 */
	public static List<DepartmentInfo> listAllDept(String userCode) throws Exception {
		Map map = new HashMap();
		map.put("userCode", userCode);
		String serviceUrl = SettingManager.getServerUrl();
		
		String requestXml = PropertyUtil.buildRequestXml(map);
		String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_LIST_ALL_DEPT, requestXml);
		List<DepartmentInfo> list = PropertyUtil.parseBeansToList(DepartmentInfo.class, resultXml);
		
		return list ;
	}
		
 /**
    * 获取二级在部门列表
    * 登录 http://172.23.6.209/dtHealth/web/MHC.PublicService.cls

    */
	public static List<PatientInfo> listPatientInfo(String userCode, String departmentId, String mainDoc, String conLoad) throws Exception {

		List<PatientInfo> list = null ;
		Map map = new HashMap();
		map.put("userCode", userCode);
		map.put("departmentId", departmentId);
		map.put("conLoad", conLoad); 
		map.put("mainDoc", mainDoc);
		String serviceUrl = SettingManager.getServerUrl();
		
		String requestXml = PropertyUtil.buildRequestXml(map);
		String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_LIST_PatientInfo, requestXml);
		list = PropertyUtil.parseBeansToList(PatientInfo.class, resultXml);
		for(PatientInfo p:list){//设置部门id
			p.departmentId = departmentId ;
		}
		return list ;
	}
	
	public static List<PatientInfo> listQrPatientInfo(String userCode, String departmentId, String bracelet) throws Exception {

		List<PatientInfo> list = null ;
		
		Map map = new HashMap();
		map.put("userCode", userCode);
		map.put("departmentId", departmentId);
		map.put("bracelet", bracelet);
		String serviceUrl = SettingManager.getServerUrl();
		
		String requestXml = PropertyUtil.buildRequestXml(map);
		LogMe.d("mark","qr请求为" + requestXml);
		String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_LIST_PatientInfo, requestXml);
		LogMe.d("mark","qr返回信息" + resultXml);
		list = PropertyUtil.parseBeansToList(PatientInfo.class, resultXml);
		for(PatientInfo p:list){//设置部门id
			p.departmentId = departmentId ;
		}
		return list ;
	}
	
	/** 
	* @Title: listQrPatientInfoByName
	* @Description: TODO(通过姓名，床位号查病人)
	* @author lqz
	* @date 2015-12-9 上午10:26:09
	* @param @param userCode
	* @param @param departmentId
	* @param @param bracelet
	* @param @return
	* @param @throws Exception    设定文件
	* @return List<PatientInfo>    返回类型
	* @throws 
	*/
	public static List<PatientInfo> listQrPatientInfoByName(String userCode, String departmentId, String condition) throws Exception {

		List<PatientInfo> list = null ;
		
		Map map = new HashMap();
		map.put("userCode", userCode);
		map.put("departmentId", departmentId);
		map.put("condition", condition);
		
		String serviceUrl = SettingManager.getServerUrl();
		
		String requestXml = PropertyUtil.buildRequestXml(map);
		LogMe.d("mark","qr请求为" + requestXml);
		String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ__SEARCH_PatientInfo, requestXml);
		LogMe.d("mark","qr返回信息" + resultXml);
		list = PropertyUtil.parseBeansToList(PatientInfo.class, resultXml);
		for(PatientInfo p:list){//设置部门id
			p.departmentId = departmentId ;
		}
		return list ;
	}
		
	/**
	 * <Request>
		<admId>患者就诊id</admId>
	   </Request>
	 * @param admId
	 * @return
	 * @throws Exception
	 */
	public static List<SeeDoctorRecord> listSeeDoctorRecord(String admId)throws Exception {
		List<SeeDoctorRecord> list = null;
		try {
			Map map = new HashMap();
			map.put("admId", admId);
			String serviceUrl = SettingManager.getServerUrl();

			String requestXml = PropertyUtil.buildRequestXml(map);
			String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_SeeDoctorRecord, requestXml);
			list = PropertyUtil.parseBeansToList(SeeDoctorRecord.class,resultXml);
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	/**
	 * <Request>
		<userCode>用户名</userCode>
		<departmentId>登录科室Id</departmentId>
		<conId>会诊申请Id</conId>
		</Request>
	 * @return
	 * @throws Exception
	 */
	public static ConsultInfo loadConsultInfo(String userCode, String departmentId, String conId) throws Exception {
		
		Map map = new HashMap();
		map.put("userCode", userCode);
		map.put("departmentId", departmentId);
		map.put("conId", conId);
		String serviceUrl = SettingManager.getServerUrl();
		
		String requestXml = PropertyUtil.buildRequestXml(map);
		String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_DETAIL_ConsultInfo, requestXml);
		List<ConsultInfo> list = PropertyUtil.parseBeansToList(ConsultInfo.class, resultXml);
		if(list.size()>0){
			return list.get(0);
		}else{
			throw new Exception("没有获取到数据!");
		}
	}

	


}
