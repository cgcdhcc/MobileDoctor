package com.imedical.im.service;

import android.app.Activity;
import android.util.Log;

import com.imedical.im.entity.AdmInfo;
import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.XMLservice.PrefManager;
import com.imedical.mobiledoctor.XMLservice.SettingManager;
import com.imedical.mobiledoctor.api.WsApiUtil;
import com.imedical.mobiledoctor.base.BaseActivity;
import com.imedical.mobiledoctor.entity.BaseBean;
import com.imedical.mobiledoctor.entity.LoginInfo;
import com.imedical.mobiledoctor.entity.OPRegisterInfo;
import com.imedical.mobiledoctor.entity.ParamUser;
import com.imedical.mobiledoctor.entity.Schedule;
import com.imedical.mobiledoctor.util.PropertyUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdmManager {



    /**
     获取患者就诊列表
     业务代码：RequestCode
     50206
     类：DWR.BL.InternetConsult
     方法：GetPatientList
     请求参数：RequestXML
     <Request>
     <terminalId>移动终端唯一序列号</terminalId>
     <userCode>医护人员Code</userCode>
     <departmentId>可不传，默认为空</departmentId>
     <visitType>医生接诊类型:N正常门诊 T图文咨询 V视频咨询</visitType>
     <flag>接诊标识，已接诊/未接诊Y/N </falg>
     <startDate>开始日期：默认不传</startDate>
     <startDate>结束日期：默认不传</startDate>
     </Request>
     */
    public static List<AdmInfo> GetPatientList(String terminalId, String userCode, String departmentId, String visitType,String flag,String startDate,String endDate) throws Exception {
        Map<String,String> map=new HashMap<>();
        map.put("terminalId", terminalId);
        map.put("userCode", userCode);
        map.put("departmentId", departmentId);
        map.put("visitType", visitType);
        map.put("flag", flag);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        String serviceUrl = SettingManager.getServerUrl();
        String requestXml = PropertyUtil.buildRequestXml(map);
        String resultXml = WsApiUtil.loadSoapObject(serviceUrl, Const.BIZ_CODE_GetPatientList, requestXml);
        List<AdmInfo> list = PropertyUtil.parseBeansToList(AdmInfo.class, resultXml);
        return list;
    }

    /*
    获取就诊信息患者描述
    <Request>
	<terminalId>移动终端唯一序列号</terminalId>
<userCode>医护人员Code</userCode>
<admId>就诊Id不能为空</admId>
</Request>

     */
    public static List<AdmInfo> GetAdmInfo(String terminalId, String userCode, String admId) throws Exception {
        Map<String,String> map=new HashMap<>();
        map.put("terminalId", terminalId);
        map.put("userCode", userCode);
        map.put("admId", admId);
        String serviceUrl = SettingManager.getServerUrl();
        String requestXml = PropertyUtil.buildRequestXml(map);
        String resultXml = WsApiUtil.loadSoapObject(serviceUrl, Const.BIZ_CODE_GetAdmInfo, requestXml);
        List<AdmInfo> list = PropertyUtil.parseBeansToList(AdmInfo.class, resultXml);
        return list;
    }
    public static List<Schedule> GetScheduleList(String userCode, String startDate, String endDate) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userCode", userCode);
        map.put("startDate", startDate);
        map.put("endDate", endDate);

        String serviceUrl = SettingManager.getServerUrl();
        Log.d("msg", serviceUrl);
        String requestXml = PropertyUtil.buildRequestXml(map);
        String resultXml = WsApiUtil.loadSoapObject(serviceUrl, Const.BIZ_CODE_GetScheduleList, requestXml);
        List<Schedule> list = PropertyUtil.parseBeansToList(Schedule.class, resultXml);
        return list;
    }

    public static List<OPRegisterInfo> GetDoctorAppList(String userCode, String scheduleCode) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userCode", userCode);
        map.put("scheduleCode", scheduleCode);

        String serviceUrl = SettingManager.getServerUrl();
        Log.d("msg", serviceUrl);
        String requestXml = PropertyUtil.buildRequestXml(map);
        String resultXml = WsApiUtil.loadSoapObject(serviceUrl, Const.BIZ_CODE_GetDoctorAppList, requestXml);
        Log.d("msg", "good:" + resultXml);
        List<OPRegisterInfo> list = PropertyUtil.parseBeansToList(OPRegisterInfo.class, resultXml);

        return list;
    }

    /*
    诊疗建议填写
业务代码：RequestCode
21023
类：MHC.BI.OPRegister
方法：UpdateDoctorContentByAdm
请求参数：RequestXML
<Request>
	<terminalId>移动终端唯一序列号</terminalId>
<admId>就诊Id不能为空</admId>
<msgContent>医生建议</ msgContent >
<opProcedure>医生随笔</ opProcedure>
</Request>

     */

    public static BaseBean UpdateDoctorContentByAdm(String terminalId, String admId, String msgContent) throws Exception {
        BaseBean bean = null;
        Map<String, String> parm = new HashMap<String, String>();
        parm.put("terminalId", terminalId);
        parm.put("admId", admId);
        parm.put("msgContent", msgContent);
        parm.put("opProcedure", "");
        String serviceUrl = SettingManager.getServerUrl();
        String requestXml = PropertyUtil.buildRequestXml(parm);
        String resultXml = WsApiUtil.loadSoapObject(serviceUrl, Const.BIZ_CODE_updatedoctorcontentbyadm, requestXml);
        bean = PropertyUtil.parseToBaseInfo(resultXml);
        return bean;
    }

    /*
    <Request>
	<terminalId>移动终端唯一序列号</terminalId>
<userCode>医护人员Code</userCode>
<admId>就诊Id不能为空</admId>
<chatStatus>写死为2</chatStatus>
</Request>
     */

    public static BaseBean updatechatstatus(String terminalId, String userCode,String admId, String chatStatus) throws Exception {
        BaseBean bean = null;
        Map<String, String> parm = new HashMap<String, String>();
        parm.put("terminalId", terminalId);
        parm.put("admId", admId);
        parm.put("userCode", userCode);
        parm.put("chatStatus", chatStatus);
        String serviceUrl = SettingManager.getServerUrl();
        String requestXml = PropertyUtil.buildRequestXml(parm);
        String resultXml = WsApiUtil.loadSoapObject(serviceUrl, Const.BIZ_CODE_updatechatstatus, requestXml);
        bean = PropertyUtil.parseToBaseInfo(resultXml);
        return bean;
    }

     /*
    <Request>his 代码20207  平台 代码 patient.opregister.cancel取消挂号
	'terminalId'             'terminalType'              'hospitalId'             'patientCard'             'patientId'   registerId
</Request>
     */

    public static BaseBean cancleOrder(String terminalId, String patientCard,String patientId, String registerId) throws Exception {
        BaseBean bean = null;
        Map<String, String> parm = new HashMap<String, String>();
        parm.put("terminalId", terminalId);
        parm.put("patientCard", patientCard);
        parm.put("patientId", patientId);
        parm.put("registerId", registerId);
        String serviceUrl = SettingManager.getServerUrl();
        String requestXml = PropertyUtil.buildRequestXml(parm);
        String resultXml = WsApiUtil.loadSoapObject(serviceUrl, Const.BIZ_CODE_OPREGISTER_CANCEL, requestXml);
        bean = PropertyUtil.parseToBaseInfo(resultXml);
        return bean;
    }
}
