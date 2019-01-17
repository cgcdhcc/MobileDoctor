package com.imedical.mobiledoctor.XMLservice;

import android.util.Log;

import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.api.WsApiUtil;
import com.imedical.mobiledoctor.base.BaseActivity;
import com.imedical.mobiledoctor.entity.BaseBean;
import com.imedical.mobiledoctor.entity.dateorder.OrderDate;
import com.imedical.mobiledoctor.entity.dateorder.OrderDept;
import com.imedical.mobiledoctor.entity.dateorder.OrderHistoryData;
import com.imedical.mobiledoctor.entity.dateorder.OrderRegData;
import com.imedical.mobiledoctor.entity.dateorder.OrderRegDetail;
import com.imedical.mobiledoctor.entity.dateorder.OrderSche;
import com.imedical.mobiledoctor.entity.dateorder.OrderSureData;
import com.imedical.mobiledoctor.entity.dateorder.TimeRange;
import com.imedical.mobiledoctor.util.PropertyUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DateOrderManager {
    /**
     * 日期选择：   业务代码：52101
     *
     * <Request>
     * <userCode>用户名</userCode>
     * <departmentId>科室Id</departmentId>
     * <startDate>开始日期</startDate>
     * <endDate>结束日期</endDate>
     * </Request>
     */

    public static List<OrderDate> selectDate(String userCode, String departmentId, String startDate, String endDate) throws Exception {
        List<OrderDate> list = null;
        Map<String, String> parm = new HashMap<String, String>();
        parm.put("userCode", userCode);
        parm.put("departmentId", departmentId);
        parm.put("startDate", startDate);
        parm.put("endDate", endDate);
        String serviceUrl = SettingManager.getServerUrl();
        String requestXml = PropertyUtil.buildRequestXml(parm);
        String resultXml = WsApiUtil.loadSoapObject(serviceUrl, Const.BIZ_CODE_LIST_ORDER_BY_DATE, requestXml);
        list = PropertyUtil.parseBeansToList(OrderDate.class, "RegisterInfo", resultXml);
        return list;
    }

    /**
     * 获取医生科室列表
     *
     * <Request>
     * <userCode>用户名</userCode>
     * </Request>
     */

    public static List<OrderDept> getDept(BaseActivity ctx, String userCode) throws Exception {
        List<OrderDept> list = null;
        Map<String, String> parm = new HashMap<String, String>();
        parm.put("userCode", userCode);
        SettingManager.initContext(ctx);
        String serviceUrl = SettingManager.getServerUrl();
        String requestXml = PropertyUtil.buildRequestXml(parm);
        String resultXml = WsApiUtil.loadSoapObject(serviceUrl, Const.BIZ_CODE_LIST_ORDER_DEPT, requestXml);
        list = PropertyUtil.parseBeansToList(OrderDept.class, "RegisterInfo", resultXml);
        return list;
    }

    /**
     * 获取号别数量
     * 时间选择(上午  下午   晚上)      业务代码：52102
     *
     *
     * <Request>
     * <userCode>用户名</userCode>
     * <departmentId>科室Id</departmentId>
     * <nowDate>当前的日期</nowDate>
     * </Request>
     */

    public static List<OrderRegData> getRegData(BaseActivity ctx, String userCode, String departmentId, String nowDate) throws Exception {
        List<OrderRegData> list = null;
        Map<String, String> parm = new HashMap<String, String>();
        parm.put("userCode", userCode);
        parm.put("departmentId", departmentId);
        parm.put("nowDate", nowDate);
        SettingManager.initContext(ctx);
        String serviceUrl = SettingManager.getServerUrl();
        String requestXml = PropertyUtil.buildRequestXml(parm);
        String resultXml = WsApiUtil.loadSoapObject(serviceUrl, Const.BIZ_CODE_LIST_ORDER_REG, requestXml);
        list = PropertyUtil.parseBeansToList(OrderRegData.class, "RegisterInfo", resultXml);
        return list;
    }

    /**
     * 获取固定日期的时间段的预约情况      业务代码：52103
     * *
     *
     * <Request>
     * <userCode>用户名</userCode>
     * <departmentId>科室Id</departmentId>
     * <chooseDate>选择的日期</chooseDate>
     * <timeRange>时间段</timeRange>
     * <ScheduleItemCode>排班资源ID</ScheduleItemCode>
     * </Request>
     */

    public static List<OrderRegDetail> getRegDetail(BaseActivity ctx, String userCode, String departmentId, String chooseDate, String timeRange, String ScheduleItemCode, String conLoad) throws Exception {
        List<OrderRegDetail> list = null;
        Map<String, String> parm = new HashMap<String, String>();
        parm.put("userCode", userCode);
        parm.put("departmentId", departmentId);
        parm.put("chooseDate", chooseDate);
        parm.put("timeRange", timeRange);
        parm.put("ScheduleItemCode", ScheduleItemCode);
        parm.put("conLoad", conLoad);
        SettingManager.initContext(ctx);
        String serviceUrl = SettingManager.getServerUrl();
        String requestXml = PropertyUtil.buildRequestXml(parm);
        String resultXml = WsApiUtil.loadSoapObject(serviceUrl, Const.BIZ_CODE_LIST_ORDER_REG_DETAIL, requestXml);
//				测试数据
//				String resultXml = "<Response> <ResultCode>0</ResultCode> <ResultDesc>成功</ResultDesc> <TotalCount/> <GroupName/> <TotalNum/> <GidStr/> <ResultList> <RegisterInfo> <patName>杨颖</patName> <patSex>女</patSex> <patAge>30</patAge> <RegisterId>19212463</RegisterId> <departmentName>基本外科门诊</departmentName> <OrderCode>8623||1314||1</OrderCode> <OrderContent>诊间预约</OrderContent> </RegisterInfo> <RegisterInfo> <patName>闫云龙</patName> <patSex>男</patSex> <patAge>60</patAge> <RegisterId>41964480</RegisterId> <departmentName>基本外科门诊</departmentName> <OrderCode>8623||1314||2</OrderCode> <OrderContent>号源平台</OrderContent> </RegisterInfo> <RegisterInfo> <patName>王保秀</patName> <patSex>男</patSex> <patAge>55</patAge> <RegisterId>42482138</RegisterId> <departmentName>基本外科门诊</departmentName> <OrderCode>8623||1314||3</OrderCode> <OrderContent>号源平台</OrderContent> </RegisterInfo> <RegisterInfo> <patName>海风菊</patName> <patSex>女</patSex> <patAge>64</patAge> <RegisterId>40663301</RegisterId> <departmentName>基本外科门诊</departmentName> <OrderCode>8623||1314||4</OrderCode> <OrderContent>号源平台</OrderContent> </RegisterInfo> <RegisterInfo> <OrderContent>号源平台</OrderContent> </RegisterInfo> </ResultList> </Response>";
        list = PropertyUtil.parseBeansToList(OrderRegDetail.class, "RegisterInfo", resultXml);
        return list;
    }

    /**
     * 取消预约      业务代码：52105
     *
     *
     * <Request>
     * <userCode>用户名</userCode>
     * <departmentId>科室Id</departmentId>
     * <timeRange>时间段</timeRange>
     * <RegisterId>登记Id、病人ID</RegisterId>
     * <OrderCode>预约单号(预约记录rowid)</OrderCode>
     *
     * </Request>
     */

    public static BaseBean getCancel(BaseActivity ctx, String userCode, String departmentId, String timeRange, String RegisterId, String OrderCode) throws Exception {
        BaseBean bean = null;
        Map<String, String> parm = new HashMap<String, String>();
        parm.put("userCode", userCode);
        parm.put("departmentId", departmentId);
        parm.put("RegisterId", RegisterId);
        parm.put("timeRange", timeRange);
        parm.put("OrderCode", OrderCode);
        SettingManager.initContext(ctx);
        String serviceUrl = SettingManager.getServerUrl();
        String requestXml = PropertyUtil.buildRequestXml(parm);
        String resultXml = WsApiUtil.loadSoapObject(serviceUrl, Const.BIZ_CODE_LIST_ORDER_REG_CANCEL, requestXml);
        bean = PropertyUtil.parseToBaseInfo(resultXml);
        return bean;
    }


    /**
     * 获取某个时间段的预约列表      业务代码：52104
     *
     *
     * <Request>
     * <userCode>用户名</userCode>
     * <departmentId>科室Id</departmentId>
     * <startDate>开始日期</startDate>
     * <endDate>结束日期</endDate>
     * </Request>
     */

    public static List<OrderRegDetail> getRegByDate(BaseActivity ctx, String userCode, String departmentId, String startDate, String endDate, String conLoad) throws Exception {
        List<OrderRegDetail> list = null;
        Map<String, String> parm = new HashMap<String, String>();
        parm.put("userCode", userCode);
        parm.put("departmentId", departmentId);
        parm.put("startDate", startDate);
        parm.put("endDate", endDate);
        parm.put("conLoad", conLoad);
        SettingManager.initContext(ctx);
        String serviceUrl = SettingManager.getServerUrl();
        String requestXml = PropertyUtil.buildRequestXml(parm);
        String resultXml = WsApiUtil.loadSoapObject(serviceUrl, Const.BIZ_CODE_LIST_ORDER_REG_DETAIL_BY_DATE, requestXml);
        list = PropertyUtil.parseBeansToList(OrderRegDetail.class, "RegisterInfo", resultXml);
        return list;
    }

    /**
     * 获取某个时间段的预约列表      业务代码：52104
     *
     *
     * <Request>
     * <RegisterId>病人ID或者卡号、身份证号码</RegisterId>
     * <userCode>用户名</userCode>
     * </Request>
     */

    public static List<OrderSureData> searchByName(BaseActivity ctx, String RegisterId, String userCode) throws Exception {
        List<OrderSureData> list = null;
        Map<String, String> parm = new HashMap<String, String>();
        parm.put("RegisterId", RegisterId);
        parm.put("userCode", userCode);
        SettingManager.initContext(ctx);
        String serviceUrl = SettingManager.getServerUrl();
        String requestXml = PropertyUtil.buildRequestXml(parm);
        String resultXml = WsApiUtil.loadSoapObject(serviceUrl, Const.BIZ_CODE_LIST_ORDER_REG_DETAIL_BY_NAME, requestXml);
        list = PropertyUtil.parseBeansToList(OrderSureData.class, "RegisterInfo", resultXml);
        return list;
    }

    /**
     * 预约      业务代码：52108
     *
     *
     * <Request>
     * <userCode>用户名</userCode>
     * <departmentId>科室ID</departmentId>
     * <RegisterId>病人ID</RegisterId>
     * <ScheduleItemCode>排班资源ID</ScheduleItemCode>
     * </Request>
     */

    public static BaseBean sureOrder(BaseActivity ctx, String userCode, String departmentId, String RegisterId, String ScheduleItemCode) throws Exception {
        BaseBean bean = null;
        Map<String, String> parm = new HashMap<String, String>();
        parm.put("RegisterId", RegisterId);
        parm.put("departmentId", departmentId);
        parm.put("userCode", userCode);
        parm.put("ScheduleItemCode", ScheduleItemCode);
        SettingManager.initContext(ctx);
        String serviceUrl = SettingManager.getServerUrl();
        String requestXml = PropertyUtil.buildRequestXml(parm);
        String resultXml = WsApiUtil.loadSoapObject(serviceUrl, Const.BIZ_CODE_LIST_ORDER_REG_SURE, requestXml);
        bean = PropertyUtil.parseToBaseInfo(resultXml);
        return bean;
    }

    /**
     * 历史记录      业务代码：52109
     *
     *
     * <Request>
     * <userCode>用户名</userCode>
     * <departmentId>科室ID</departmentId>
     * </Request>
     */

    public static List<OrderHistoryData> getsearchHistory(BaseActivity ctx, String userCode, String departmentId) throws Exception {
        List<OrderHistoryData> list = null;
        Map<String, String> parm = new HashMap<String, String>();
        parm.put("departmentId", departmentId);
        parm.put("userCode", userCode);
        SettingManager.initContext(ctx);
        String serviceUrl = SettingManager.getServerUrl();
        String requestXml = PropertyUtil.buildRequestXml(parm);
        String resultXml = WsApiUtil.loadSoapObject(serviceUrl, Const.BIZ_CODE_LIST_ORDER_REG_HISTORY, requestXml);
        list = PropertyUtil.parseBeansToList(OrderHistoryData.class, "RegisterInfo", resultXml);
        return list;
    }


    /**
     * 预约      业务代码：52109
     *
     *
     * <Request>
     * <userCode>用户名</userCode>
     * <chooseDate>选择的日期</chooseDate>
     * </Request>
     */

    public static List<OrderSche> getScheDept(BaseActivity ctx, String userCode, String chooseDate) throws Exception {
        List<OrderSche> list = null;
        Map<String, String> parm = new HashMap<String, String>();
        parm.put("userCode", userCode);
        parm.put("chooseDate", chooseDate);
        SettingManager.initContext(ctx);
        String serviceUrl = SettingManager.getServerUrl();
        String requestXml = PropertyUtil.buildRequestXml(parm);
        String resultXml = WsApiUtil.loadSoapObject(serviceUrl, Const.BIZ_CODE_LIST_ORDER_SCHE, requestXml);
        list = PropertyUtil.parseBeansToList(OrderSche.class, "RegisterInfo", resultXml);
        return list;
    }

    /**
     * 新增排班     业务代码：52111
     *
     *
     * <Request>
     * <chooseDate>选择的日期</chooseDate>
     * <userCode>用户名</userCode>
     * <departmentId>科室ID</departmentId>
     * <timeRange>时间段</timeRange>
     * </Request>
     */

    public static BaseBean sureCreate(BaseActivity ctx, String chooseDate, String userCode, String departmentCode, String timeRange) throws Exception {
        BaseBean bean = null;
        Map<String, String> parm = new HashMap<String, String>();
        parm.put("chooseDate", chooseDate);
        parm.put("userCode", userCode);
        parm.put("departmentCode", departmentCode);
        parm.put("timeRange", timeRange);
        SettingManager.initContext(ctx);
        String serviceUrl = SettingManager.getServerUrl();
        String requestXml = PropertyUtil.buildRequestXml(parm);
        String resultXml = WsApiUtil.loadSoapObject(serviceUrl, Const.BIZ_CODE_LIST_ORDER_SCHE_SURE, requestXml);
        bean = PropertyUtil.parseToBaseInfo(resultXml);
        return bean;
    }


			/*
			获取出诊时段
业务代码：RequestCode
51901
类：DWR.BL.InternetConsult
方法：GetTimeRange
请求参数：RequestXML
<Request>
<terminalId>终端id</terminalId>
<userCode>用户名</userCode>
</Request>
返回数据集ResultList
<TimeRange></TimeRange>…<TimeRange></TimeRange>
<TimeRange>
<timeRangeId>出诊时段Id</timeRangeId>
<timeRangeDesc>出诊时段描述</timeRangeDesc>
<startTime>开始时间</startTime>
<endTime>结束时间</endTime>
</TimeRange>

			 */

    public static  List<TimeRange> GetTimeRange(String userCode, String terminalId) throws Exception {
        List<TimeRange> list = null;
        Map<String, String> parm = new HashMap<String, String>();
        parm.put("userCode", userCode);
        parm.put("terminalId", terminalId);
        String serviceUrl = SettingManager.getServerUrl();
        String requestXml = PropertyUtil.buildRequestXml(parm);
        String resultXml = WsApiUtil.loadSoapObject(serviceUrl, Const.BIZ_CODE_GETTIMERANGE, requestXml);
        Log.d("msg:resultXml",resultXml );
        list = PropertyUtil.parseBeansToList(TimeRange.class, resultXml);
        return list;
    }

    /*
    设置图文出诊时段
业务代码：RequestCode
51902
类：DWR.BL.InternetConsult
方法：DoctorSchedule
请求参数：RequestXML
<Request>
<terminalId>终端id</terminalId>
<userCode>用户名</userCode>
<timeRangeId>出诊时段Id</timeRangeId>
<regLimit>限号数量</regLimit>
<visitType>出诊类型，默认为T图文咨询，V视频咨询</visitType>
<scheduleDate>出诊日期，默认不传为当日</scheduleDate>
</Request>
返回数据集ResultList
0^成功
	其他 ResultCode^ResultDesc ResultCode>100 ^  错误描述

     */
    public static BaseBean DoctorSchedule(String terminalId, String userCode, String timeRangeId, String regLimit, String visitType, String scheduleDate) throws Exception {
        BaseBean bean = null;
        Map<String, String> parm = new HashMap<String, String>();
        parm.put("terminalId", terminalId);
        parm.put("userCode", userCode);
        parm.put("timeRangeId", timeRangeId);
        parm.put("regLimit", regLimit);
        parm.put("visitType", visitType);
        parm.put("scheduleDate", scheduleDate);
        String serviceUrl = SettingManager.getServerUrl();
        String requestXml = PropertyUtil.buildRequestXml(parm);
        String resultXml = WsApiUtil.loadSoapObject(serviceUrl, Const.BIZ_CODE_SETSCHEDULE, requestXml);
        bean = PropertyUtil.parseToBaseInfo(resultXml);
        return bean;
    }
}
