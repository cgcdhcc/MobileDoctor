package com.imedical.mobiledoctor.XMLservice;

import android.util.Log;
import android.util.Xml;
import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.api.WsApiUtil;
import com.imedical.mobiledoctor.entity.BrowseLocation;
import com.imedical.mobiledoctor.exception.BaseException;
import com.imedical.mobiledoctor.util.LogMe;
import com.imedical.mobiledoctor.util.PropertyUtil;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BrowseManager {

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //电子病例，ssz重写
    //
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public static BrowseLocation getBrowseLocationInfo(String userCode, String admId, String netWorkType) throws Exception{
		
		List<BrowseLocation> list = null;
		
		Map<String, String> parm = new HashMap<String, String>();
		parm.put("userCode", userCode);
		parm.put("admId", admId);
		parm.put("netWorkType", netWorkType);
		String serviceUrl = SettingManager.getServerUrl();
		String req = PropertyUtil.buildRequestXml(parm);
		String res = WsApiUtil.loadSoapObject(serviceUrl, Const.BIZ_CODE_LIST_BrowseLocation, req);
		list = PropertyUtil.parseBeansToList(BrowseLocation.class, res);
		if(list.size()==0){
			throw new BaseException("此人的电子病例不存在!");
		}
		return list.get(0);
		
	}

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    //
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public Map<String, Object> getBrowseLocationInfoByMap(String userCode,String admId, String state) throws Exception{
		Map<String, String> parm = new HashMap<String, String>();
		
		parm.put("userCode", userCode);
		parm.put("admId", admId);
		parm.put("netWorkType", state);
		
		String serviceUrl = SettingManager.getServerUrl();
		String req = PropertyUtil.buildRequestXml(parm);
		String res = WsApiUtil.loadSoapObject(serviceUrl, Const.BIZ_CODE_LIST_BrowseLocation, req);
		
		
		Map<String, Object> map = getXmlRes(res);
		
		Log.i("res ", res);
		
//		Log.i("type", (String )map.get("browseType"));
		
		return map;
	}
	
	
	
	private static Map<String, Object> getXmlRes(String str) {
		String key=null;
		Map<String, Object> mapRes = new HashMap<String, Object>();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        List<String> pageList = null;
        XmlPullParser parse = Xml.newPullParser();
        try {
        	InputStream in = new ByteArrayInputStream(str.getBytes());
			parse.setInput(in, "UTF-8");
			
			
			int event = parse.getEventType();
	        while(event != XmlPullParser.END_DOCUMENT){
	        	
	        	switch (event) {
	        	
				case XmlPullParser.START_TAG:{
					LogMe.d("<<<<<<<<<<parse.getName():"+parse.getName());
					if(parse.getName()!=null && parse.getName().equals("cateCharpterName")){
						key = parse.nextText();
//						pageList.clear();
						pageList = new ArrayList<String>();
					}
					if(parse.getName()!=null && parse.getName().equals("fileLocation")){
						String key2 = parse.nextText();
						pageList.add(key2);						
					}
					if(parse.getName()!=null && parse.getName().equals("browseType")){
						key = parse.nextText();
						mapRes.put("browseType", key);
					}
					if(parse.getName()!=null && parse.getName().equals("fileServerLocation")){
						key = parse.nextText();
						mapRes.put("fileServerLocation", key);
					}
					
					break;
				}
				case XmlPullParser.END_TAG:{
					if (parse.getName()!=null && parse.getName().equals("CateCharpter")) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("list", pageList);
						map.put("name", key);
						list.add(map);
					}
					if(parse.getName()!=null && parse.getName().equals("browseType")){
						mapRes.put("browseType", key);
					}
					if(parse.getName()!=null && parse.getName().equals("fileServerLocation")){
						mapRes.put("fileServerLocation", key);
					}
					break;
				}

				default:
					break;
				}
	        	
	        	event = parse.next();
	        }
	        mapRes.put("page_list", list);
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			LogMe.d("exception1 : "+e.getLocalizedMessage());
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LogMe.d("exception2 : "+e.getLocalizedMessage());
			e.printStackTrace();
		}

        return mapRes;
    }
}
