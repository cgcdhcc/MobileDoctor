package com.imedical.mobiledoctor.util;

import com.imedical.mobiledoctor.entity.BaseBean;
import com.imedical.mobiledoctor.entity.LabelValue;
import com.imedical.mobiledoctor.exception.BaseException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 对象属性动态操作处理
 */
public class PropertyUtil {


    public static <T> List<T> parseBeansToList(Class<T> classzz, String xmlData) throws Exception {
        List<Map> beansList = pasreXMLToMapList(classzz, xmlData);

        List<T> list = new ArrayList<T>();
        for (int i = 0; i < beansList.size(); i++) {
            try {
                T targetObj = classzz.newInstance();
                //第i个bean
                Map beanSoap = beansList.get(i);
                copyProperty(targetObj, beanSoap);

                list.add(targetObj);

            } catch (InstantiationException e) {
                e.printStackTrace();
                throw e;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw e;
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }
        return list;
    }

    /**
     * （解析返回数据的倒数第二层对象列表节点）
     * 复制数据列表
     *
     * @param <T>
     * @throws Exception
     */
    public static <T> List<T> parseBeansToList(Class<T> classzz, String firstLevelNodeName, String xmlData) throws Exception {
        List<Map<String, String>> beansList = pasreXMLToMapList(classzz, firstLevelNodeName, xmlData);

        List<T> list = new ArrayList<T>();
        for (int i = 0; i < beansList.size(); i++) {
            try {
                T targetObj = classzz.newInstance();
                //第i个bean
                Map<String, String> beanSoap = beansList.get(i);
                copyProperty(targetObj, beanSoap);

                list.add(targetObj);

            } catch (InstantiationException e) {
                e.printStackTrace();
                throw e;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw e;
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }
        return list;
    }

    /**
     * （解析返回数据的倒数第二层对象列表节点）
     * 复制数据列表
     *
     * @param <T>
     */
    private static <T> List<T> parseBeansToList(Class<T> classzz, List<Map> beansList) {
        List<T> list = new ArrayList<T>();

        for (int i = 0; i < beansList.size(); i++) {
            try {

                T targetObj = classzz.newInstance();
                //第i个bean
                Map beanSoap = beansList.get(i);
                copyProperty(targetObj, beanSoap);

                list.add(targetObj);

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }


    private static List<Map> pasreXMLToMapList(Class classzz, String xml) throws BaseException, DocumentException {
        List<Map> list = new ArrayList();
        Document doc;
        try {

            doc = DocumentHelper.parseText(xml);
            Element root = doc.getRootElement();// 指向根节点
            // normal解析
            Element eCode = root.element("ResultCode");
            Element eDesc = root.element("ResultDesc");
            String code = eCode.getText();
            String desc = eDesc.getText();

            if (!"0".equals(code)) {
                throw new BaseException(desc);
            }

            String nodeName = classzz.getSimpleName();
            Element listEl = root.element("ResultList");
            List listChild = listEl.elements(nodeName);// 所有的Item节点
            for (int i = 0; i < listChild.size(); i++) {
                Element elChild = (Element) listChild.get(i);
                Map map = new HashMap();
                copyDataToMap(map, elChild);
                list.add(map);
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
            throw e;
        }
        return list;
    }


    /**
     * 此方法需要对参数进行强制类型转化，可能导致不必要的类型转化错误，不对外公开
     * （解析返回数据的倒数第二层对象列表节点）
     * 复制数据列表
     *
     * @param <T>
     * @throws Exception
     */
    private static <T> List<T> parseBeansToList2(Class classzz, String xmlData) throws Exception {
        List<Map> beansList = pasrePropertyXMLToMapList2(classzz, xmlData);

        List<T> list = new ArrayList<T>();
        for (int i = 0; i < beansList.size(); i++) {
            try {
                T targetObj = (T) classzz.newInstance();
                //第i个bean
                Map beanSoap = beansList.get(i);
                copyProperty(targetObj, beanSoap);

                list.add(targetObj);

            } catch (InstantiationException e) {
                e.printStackTrace();
                throw e;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw e;
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }
        return list;
    }

    /**
     * 解析多级xml时使用
     *
     * @param classzz
     * @return
     * @throws BaseException
     * @throws DocumentException
     */
    private static List<Map> pasrePropertyXMLToMapList2(Class classzz, String innerXml) throws BaseException, DocumentException {
        List<Map> list = new ArrayList();
        Document doc;
        try {
            doc = DocumentHelper.parseText(innerXml);
            Element root = doc.getRootElement();// 指向根节点
            // normal解析
            String nodeName = classzz.getSimpleName();
            List<?> listChild = root.elements(nodeName);// 所有的Item节点
            for (int i = 0; i < listChild.size(); i++) {
                Element elChild = (Element) listChild.get(i);
                Map<String, String> map = new HashMap<String, String>();
                copyDataToMap(map, elChild);
                list.add(map);
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
            throw e;
        }
        return list;
    }



    private static Map pasrePropertyXMLToMap(Class classzz, String innerXml) throws BaseException, DocumentException {
        Map<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNullOrEmpty(innerXml)) {
            return map;
        }

        try {
            Document doc = DocumentHelper.parseText(innerXml);
            Element root = doc.getRootElement();// 指向根节点
            // normal解析
            //String nodeName = classzz.getSimpleName();
            copyDataToMap(map, root);

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
            throw e;
        }
        return map;
    }

    private static List<Map<String, String>> pasreXMLToMapList(Class classzz, String firstLevelNodeName, String xml) throws BaseException, DocumentException {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        try {
//			Log.d("#########"+firstLevelNodeName, "pasreXMLToMapList:"+xml);
            Document doc = DocumentHelper.parseText(xml);
            Element root = doc.getRootElement();// 指向根节点
            // normal解析
            Element eCode = root.element("ResultCode");
            Element eDesc = root.element("ResultDesc");
            String code = eCode.getText();
            String desc = eDesc.getText();

            if (!"0".equals(code)) {
                throw new BaseException(desc);
            }

            String nodeName = null;
            if (Validator.isBlank(firstLevelNodeName)) {
                nodeName = classzz.getSimpleName();
            } else {
                nodeName = firstLevelNodeName;
            }
            Element listEl = root.element("ResultList");
            List listChild = listEl.elements(nodeName);// 所有的Item节点
            for (int i = 0; i < listChild.size(); i++) {
                Element elChild = (Element) listChild.get(i);
                Map map = new HashMap();
                copyDataToMap(map, elChild);
                list.add(map);
            }

        } catch (DocumentException e) {
            e.printStackTrace();
            throw e;
        }
        return list;
    }

    public static BaseBean parseToBaseInfo(String xml) throws DocumentException, Exception {
        BaseBean bean = new BaseBean();
        Document doc;
        try {
            doc = DocumentHelper.parseText(xml);
            Element root = doc.getRootElement();// 指向根节点
            // normal解析
            Element eCode = root.element("ResultCode");
            Element eDesc = root.element("ResultDesc");
            String code = eCode.getText();
            String desc = eDesc.getText();

            bean.setResultCode(code);
            bean.setResultDesc(desc);

        } catch (DocumentException e) {
            LogMe.d(e.getMessage());
            throw e;
        } catch (Exception e) {
            throw e;
        }
        return bean;
    }

    private static <T> T parseToBean(Class classzz, String xml) throws DocumentException, Exception {
        T targetObj = (T) classzz.newInstance();
        Map map = pasrePropertyXMLToMap(classzz, xml);
        //第i个bean
        copyProperty(targetObj, map);
        return targetObj;
    }

    /**
     * （解析返回数据的倒数第一层对象节点）
     * 把一个单层的soap对象属性复制到目标对象中，两个对象的属性必须一一对应
     *
     * @param targetObj 目标对象
     * @param map       操作对象
     */
    private static void copyProperty(Object targetObj, Map map) {

        Field[] fields = targetObj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            //property
            String fieldName = field.getName();
            Object fieldValue = map.get(fieldName);

            Desc desc = field.getAnnotation(Desc.class);
            if (desc != null && desc.type().equals(Desc.TYPE_PARSE_IGNORE)) {
                continue;
            }
            if ("serialVersionUID".equals(fieldName)) {
                continue;
            }
            ///解析到List集合
            ///解析到List集合getCanonicalName().equals("")
            ///不再使用if(desc!=null && desc.type().equals(Desc.TYPE_LIST)){
            if ("java.util.List".equals(field.getType().getCanonicalName())) {
                //范型参数类型
                Class classParam = getTypeParam0(field);
                try {
                    if (fieldValue != null) {// fieldValue 为一段xmlNode
                        fieldValue = PropertyUtil.parseBeansToList2(classParam, fieldValue.toString());
                    } else {
                        fieldValue = new ArrayList();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (desc != null && Desc.TYPE_BEAN.equals(desc.type())) {
                //范型参数类型
                Class classParam = field.getType();
                try {
                    if (fieldValue != null) {// fieldValue 为一段xmlNode
                        fieldValue = PropertyUtil.parseToBean(classParam, fieldValue.toString());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                //FIXME 空值判断
//				if(fieldValue==null){
//					fieldValue="null";
//				}else{
//				}
                field.set(targetObj, fieldValue);
            } catch (IllegalArgumentException e) {
                LogMe.d(">>>>>>fieldName:" + fieldName + " \n " + e.getMessage());
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * 获取集合属性的第一个范型参数类型
     *
     * @param listField
     * @return
     */
    private static Class getTypeParam0(Field listField) {
        Class classRet = null;
        Type genericFieldType = listField.getGenericType();
        if (genericFieldType instanceof ParameterizedType) {
            ParameterizedType aType = (ParameterizedType) genericFieldType;
            Type[] fieldArgTypes = aType.getActualTypeArguments();
            for (Type fieldArgType : fieldArgTypes) {
                classRet = (Class) fieldArgType;
                break;//只获取第一个，后面的不管了
            }
        }

        return classRet;
    }

    /**
     * 复制人员信息到map
     *
     * @param map
     * @param elChild
     */
    private static void copyDataToMap(Map<String, String> map, Element elChild) {
        List<?> properties = elChild.elements();
        for (int i = 0; i < properties.size(); i++) {
            Element p = (Element) properties.get(i);
            String key = p.getName();
            String v = null;

            //if(p.selectNodes("//*").size()>0){//dom4j判断存在子节点
            if (p.elements().size() > 0) {
                v = p.asXML();
            } else {
                v = p.getText();
            }
            map.put(key, v);
        }
    }

    /**
     * @param dataBean
     * @return
     */

    public static String buildRequestXml(Object dataBean) {

        StringBuilder sb = new StringBuilder();
        sb.append("<Request>");

        Field[] fields = dataBean.getClass().getDeclaredFields();
        for (Field field : fields) {

            field.setAccessible(true);
            //property
            try {

                String key = field.getName();
                Object v = field.get(dataBean);
                if ("serialVersionUID".equals(key)) {
                    continue;
                }
                if (v == null) {
                    continue;//查获取非空属性的参数
                }

                sb.append("<").append(key).append(">");
                sb.append(v);
                sb.append("</").append(key).append(">");

            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        sb.append("</Request>");

        ///LogMe.d("#####buildRequestXml:",sb.toString());
        return sb.toString();
    }

    /**
     * @param mapParam
     * @return
     */

    public static String buildRequestXml(Map<String, String> mapParam) {

        StringBuilder sb = new StringBuilder();
        sb.append("<Request>");

        Iterator<String> it = mapParam.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            String v = mapParam.get(key);
            sb.append("<").append(key).append(">");
            sb.append(v);
            sb.append("</").append(key).append(">");
        }
        sb.append("</Request>");

        ///LogMe.d("#####buildRequestXml:",sb.toString());
        return sb.toString();
    }

    /**
     * 用于拼出键值显示界面的数据：如 一个对象的明细界面数据
     *
     * @param targetObj
     * @return
     */
    public static List<LabelValue> parseToLvList(Object targetObj) {
        List<LabelValue> list = new ArrayList<LabelValue>();

        Field[] fields = targetObj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            //property
            String fieldName = field.getName();

            Desc desc = field.getAnnotation(Desc.class);
            if (desc != null && desc.type().equals(Desc.TYPE_PARSE_IGNORE)) {
                continue;
            }
            if ("serialVersionUID".equals(fieldName)) {
                continue;
            }
            try {
                Object v = field.get(targetObj);
                if (v == null) {
                    v = "";
                }
                LabelValue lv = new LabelValue(desc.label(), v.toString());
                list.add(lv);
            } catch (IllegalArgumentException e) {
                LogMe.d(">>>>>>fieldName:" + fieldName + " \n " + e.getMessage());
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return list;
    }
}
