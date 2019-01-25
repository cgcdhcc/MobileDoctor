package com.imedical.mobiledoctor.XMLservice;

import android.app.Activity;
import android.util.Log;


import com.imedical.mobiledoctor.Const;
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

public class UserManager {

    public static void logout(Activity ctx) {
        try {
            PrefManager.getSharedPreferences_PARAM(ctx).edit().clear().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //清空本地信息
    }


    /*/*
       * 创建新密码
       * 登录 http://172.23.6.209/dtHealth/web/MHC.PublicService.cls
       * @param requestCode
       * @param requestXml
       *
       * <Request>
        <phoneNo>手机号码</phoneNo>
        <terminalId>终端编号</terminalId>
        <patientCard>患者卡号</patientCard>
        <idNo>身份证号</idNo>
        <newPassWord>新口令</newPassWord>
        </Request>
       * @throws Exception
       */
    public static BaseBean resetPassword(BaseActivity ctx, ParamUser param) throws Exception {

        String serviceUrl = SettingManager.getServerUrl();
        String requestXml = PropertyUtil.buildRequestXml(param);
        String resultXml = WsApiUtil.loadSoapObject(serviceUrl, Const.BIZ_CODE_LOSE_PSW, requestXml);
        BaseBean bean = PropertyUtil.parseToBaseInfo(resultXml);

        return bean;
    }


    /**
     * 登录 http://172.23.6.209/dtHealth/web/MHC.PublicService.cls
     *
     * @throws Exception
     */
    public static LoginInfo login(Activity ctx, String userCode, String password, String terminalId) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userCode", userCode);
        map.put("password", password);
        map.put("terminalId", terminalId);
        SettingManager.initContext(ctx);
        String serviceUrl = SettingManager.getServerUrl();
        Log.d("msg", serviceUrl);
        String requestXml = PropertyUtil.buildRequestXml(map);
        String resultXml = WsApiUtil.loadSoapObject(serviceUrl, Const.BIZ_CODE_LOGIN, requestXml);
        List<LoginInfo> list = PropertyUtil.parseBeansToList(LoginInfo.class, resultXml);

        return list.get(0);
    }

    public static LoginInfo login(String userCode) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userCode", userCode);
        String serviceUrl = SettingManager.getServerUrl();
        Log.d("msg", serviceUrl);
        String requestXml = PropertyUtil.buildRequestXml(map);
        String resultXml = WsApiUtil.loadSoapObject(serviceUrl, Const.BIZ_CODE_FACE_LOGIN, requestXml);
        List<LoginInfo> list = PropertyUtil.parseBeansToList(LoginInfo.class, resultXml);

        return list.get(0);
    }
//			
//			/**
//			    * 获取患者基本信息
//			    * 登录 http://172.23.6.209/dtHealth/web/MHC.PublicService.cls
//			    * @param requestCode
//			    * @param requestXml
//			    * <Request>
//					<patientCard>患者卡号</patientCard>
//					<cardType>卡类型</cardType>
//				    <phoneNo>电话号码</phoneNo>
//					<passWord>密码</passWord>
//					<terminalId>终端编号</terminalId>
//					<hospitalId>医院Id</hospitalId>
//					<idNo>身份证号</idNo>
//				  </Request>
//			    * @throws Exception
//			    * 
//			    */
//				public static List<LabelValue> loadPatInfoProperties(String patientCard
//															,String cardType
//															,String phoneNo
//															,String passWord
//															,String terminalId
//															,String hospitalId
//															,String idNo ) throws Exception{
//					
//					Patinfo p = loadPatInfo(patientCard, cardType, phoneNo, passWord, terminalId, hospitalId, idNo);
//					List<LabelValue> list = new ArrayList<LabelValue>();
//                    list.add(new LabelValue("姓名：",p.patientName));
//                    list.add(new LabelValue("性别：",p.sex));
//                    list.add(new LabelValue("病人类型：",p.patType));
//                    list.add(new LabelValue("手机号码：",phoneNo));
//                    list.add(new LabelValue("出生日期：",p.dob));
//                    list.add(new LabelValue("家庭住址：",p.address));
//                    list.add(new LabelValue("证件号码：",p.idNo));
//                    list.add(new LabelValue("就诊卡号：",p.patientCard));
//                    list.add(new LabelValue("卡内余额：",p.balance));
//                    list.add(new LabelValue("医保标识：",p.insuFlag));
//					return list ;
//				}
//				

    /**
     * 更新密码
     *
     * <Request>
     * <phoneNo>手机号码</phoneNo>
     * <terminalId>终端编号</terminalId>
     * <passWord>口令</passWord>
     * <newPassWord>新口令</newPassWord>
     * </Request>
     */
    public static BaseBean updatePassword(BaseActivity ctx, String phoneNo, String passWord, String newPassWord, String terminalId) throws Exception {
        ParamUser u = new ParamUser();
        u.setPhoneNo(phoneNo);
        u.setPassWord(passWord);
        u.setTerminalId(terminalId);
        u.setNewPassWord(newPassWord);

        SettingManager.initContext(ctx);
        String serviceUrl = SettingManager.getServerUrl();
        String requestXml = PropertyUtil.buildRequestXml(u);
        String resultXml = WsApiUtil.loadSoapObject(serviceUrl, Const.BIZ_CODE_UPDATE_PSW, requestXml);
        BaseBean bean = PropertyUtil.parseToBaseInfo(resultXml);

        return bean;
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


}
