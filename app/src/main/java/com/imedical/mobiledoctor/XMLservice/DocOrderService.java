package com.imedical.mobiledoctor.XMLservice;



import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.api.WsApiUtil;
import com.imedical.mobiledoctor.entity.ArcimItem;
import com.imedical.mobiledoctor.entity.BaseBean;
import com.imedical.mobiledoctor.entity.DiagItem;
import com.imedical.mobiledoctor.entity.DiagStatus;
import com.imedical.mobiledoctor.entity.Diagnosis;
import com.imedical.mobiledoctor.entity.FormArcimItem;
import com.imedical.mobiledoctor.entity.OETabs;
import com.imedical.mobiledoctor.entity.PopMessage;
import com.imedical.mobiledoctor.util.LogMe;
import com.imedical.mobiledoctor.util.PropertyUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dashan on 2017/8/7.
 */

public class DocOrderService {
    private static DocOrderService docOrderService;
    static String serviceUrl;
    static {
        serviceUrl = SettingManager.getServerUrl();
    }
    private DocOrderService() {
    }

    public static synchronized DocOrderService getInstance() {
        if (docOrderService == null) {
            docOrderService = new DocOrderService();
        }
        return docOrderService;
    }
   //查询一级医嘱模板
    public static List<OETabs> loadOETabs(String userCode,
                                          String admId, String groupId, String departmentId) throws Exception {

        Map<String, String> m = new HashMap<String, String>();
        m.put("userCode", userCode);
        m.put("admId", admId);
        m.put("groupId", groupId);
        m.put("departmentId", departmentId);
        String requestXml = PropertyUtil.buildRequestXml(m);
        String resultXml = WsApiUtil.loadSoapObject(serviceUrl,
                Const.BIZ_CODE_LIST_OETabs, requestXml);
        List<OETabs> list = PropertyUtil.parseBeansToList(OETabs.class, resultXml);

        return list;
    }
    //获取二级页签
    public static List<OETabs> loadOETabs_2(String userCode, String admId, String groupId,
                                            String departmentId, String tabId) throws Exception {

        Map params = new HashMap();
        params.put("userCode", userCode);
        params.put("admId", admId);
        params.put("groupId", groupId);
        params.put("departmentId", departmentId);
        params.put("tabId", tabId);


        String requestXml = PropertyUtil.buildRequestXml(params);
        String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_LIST_OETabs_2, requestXml);

        LogMe.d(requestXml+"\n\n"+resultXml);
        List<OETabs> list = PropertyUtil.parseBeansToList(OETabs.class, resultXml);
        return list;
    }
    //获取医嘱模板的医嘱信息
    public static List<OETabs> loadArciItemByTab2(String userCode, String admId, String groupId, String departmentId, String tabId, String subTabId) throws Exception {
        Map params = new HashMap();
        params.put("userCode", userCode);
        params.put("admId", admId);
        params.put("groupId", groupId);
        params.put("departmentId", departmentId);
        params.put("tabId", tabId);
        params.put("subTabId", subTabId);


        String requestXml = PropertyUtil.buildRequestXml(params);
        String resultXml  = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_LIST_OETabs_info, requestXml);

        LogMe.d(requestXml+"\n\n"+resultXml);
        List<OETabs> list = PropertyUtil.parseBeansToList(OETabs.class, resultXml);


        return list;
    }
    //获取增加的未审核的医嘱信息
    public static List<ArcimItem> loadArcimItemListSaved(String userCode, String admId) throws Exception {

        Map params = new HashMap();
        params.put("userCode", userCode);
        params.put("admId", admId);

        String requestXml = PropertyUtil.buildRequestXml(params);
        String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_LIST_INPUT_ArcimItem, requestXml);

        LogMe.d(requestXml+"\n\n"+resultXml);
        List<ArcimItem> list = PropertyUtil.parseBeansToList(ArcimItem.class, resultXml);
        return list;
    }
    //获取医嘱项目的默认参数
    public static ArcimItem loadArcimItemById(String userCode, String admId, String arcItemId, String departmentId) throws IndexOutOfBoundsException,Exception {
        Map params = new HashMap();
        params.put("userCode", userCode);
        params.put("admId", admId);
        params.put("arcItemId", arcItemId);
        params.put("departmentId", departmentId);
        String requestXml = PropertyUtil.buildRequestXml(params);
        String resultXml  = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_DETAIL_ArcimItem, requestXml);

        LogMe.d(requestXml+"\n\n"+resultXml);
        List<ArcimItem> list = PropertyUtil.parseBeansToList(ArcimItem.class, resultXml);
        if(list.size()==0){
            return new ArcimItem();
        }
        return list.get(0);
    }

    /**
     *2.10.20增加/更新前的校验数据的有效性
     */
    public static List<PopMessage> checkAndloadPopMessage(FormArcimItem form) throws Exception {

        String requestXml = PropertyUtil.buildRequestXml(form);
        String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_LIST_PopMessage, requestXml);

        LogMe.d(requestXml+"\n--------------------------\n"+resultXml);
        List<PopMessage> list = PropertyUtil.parseBeansToList(PopMessage.class, resultXml);

        return list ;
    }


    /**
     * <Request>
     <userCode>用户名</userCode>
     <admId>就诊Id</admId>
     <showIndex>临时数据中的Id/当为空时删除所有临时数据,当为空时删除提交前要提醒是否清除列表中的医嘱项目</showIndex>
     </Request>
     * @throws Exception
     */
    public static BaseBean delete(String userCode, String admId, String showIndex) throws Exception {
        Map params = new HashMap();
        params.put("userCode", userCode);
        params.put("admId", admId);
        params.put("showIndex", showIndex);


        String requestXml = PropertyUtil.buildRequestXml(params);
        String resultXml = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_DEL_ArcimItem, requestXml);

        LogMe.d(requestXml+"\n\n"+resultXml);
        BaseBean b = PropertyUtil.parseToBaseInfo(resultXml);

        return b;
    }

    public static BaseBean GetAdmIdForLis(String patientId,
                                          String userCode, String extendCode, String admReason, String sepcMode) throws Exception {

        Map<String, String> m = new HashMap<String, String>();
        m.put("patientId", patientId);
        m.put("userCode", userCode);
        m.put("extendCode", extendCode);
        m.put("admReason", admReason);
        m.put("specMode", sepcMode);
        String requestXml = PropertyUtil.buildRequestXml(m);
        String resultXml = WsApiUtil.loadSoapObject(serviceUrl, Const.BIZ_CODE_GetAdmIdForLis, requestXml);
        return PropertyUtil.parseToBaseInfo(resultXml);
    }
}
