package com.imedical.mobiledoctor.XMLservice;
import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.api.WsApiUtil;
import com.imedical.mobiledoctor.entity.BaseBean;
import com.imedical.mobiledoctor.entity.DiagItem;
import com.imedical.mobiledoctor.entity.DiagStatus;
import com.imedical.mobiledoctor.entity.Diagnosis;
import com.imedical.mobiledoctor.entity.FileParm;
import com.imedical.mobiledoctor.entity.MediaFile;
import com.imedical.mobiledoctor.util.LogMe;
import com.imedical.mobiledoctor.util.PropertyUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileTransferManager {
	

	
	/**
	 *获取文件参数
	<Request>
	<userCode>用户名</userCode>
	<admId>就诊Id</admId>
	<fileName>文件名字</fileName>
	</Request>
	 * @param userCode
	 * @return
	 * @throws Exception
	 */
	public static FileParm loadFileParam(String userCode,String admId,String fileName) throws Exception {
		Map params = new HashMap();
		params.put("userCode", userCode);
		params.put("admId", admId);
		params.put("fileName", fileName);
		
    	String serviceUrl = SettingManager.getServerUrl();
		
		String requestXml = PropertyUtil.buildRequestXml(params);
		String resultXml  = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_DETAIL_FileParm, requestXml);
			
		LogMe.d(requestXml+"\n\n"+resultXml);
		List<FileParm> list = PropertyUtil.parseBeansToList(FileParm.class, resultXml);
		return list.get(0);
	}
	
	/**
	 * 获取文件列表
	 *<Request>
		<userCode>用户名</userCode>
		<admId>就诊Id</admId>
		<fileType>文件类型S、P、V</fileType>
	  </Request>
	 * @param userCode
	 * @return
	 * @throws Exception
	 */
	public static List<MediaFile> listMediaFile(String userCode,String admId,String fileType) throws Exception {
		Map params = new HashMap();
		params.put("userCode", userCode);
		params.put("admId", admId);
		params.put("fileType", fileType);
		
    	String serviceUrl = SettingManager.getServerUrl();
		
		String requestXml = PropertyUtil.buildRequestXml(params);
		String resultXml  = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_LIST_MediaFile, requestXml);
			
		LogMe.d(requestXml+"\n\n"+resultXml);
		List<MediaFile> list = PropertyUtil.parseBeansToList(MediaFile.class, resultXml);
		return list;
	}
	
	
	/**
	 * 上传文件
	 * <Request>
		<userCode>用户名</userCode>
		<admId>就诊Id</admId>
		<filePath>文件路径</filePath>
		<fileName>文件名称</fileName>
		<fileType>文件类型</fileType>
		</Request>
	 * @param userCode
	 * @return
	 * @throws Exception
	 */
	public static BaseBean uploadFile(String userCode,String admId,String filePath,String fileName,String fileType,String fileNote) throws Exception {
		Map params = new HashMap();
		params.put("userCode", userCode);
		params.put("admId", admId);
		params.put("filePath", filePath);
		params.put("fileName", fileName);
		params.put("fileType", fileType);
		params.put("fileNote", fileNote);
		
    	String serviceUrl = SettingManager.getServerUrl();
		
		String requestXml = PropertyUtil.buildRequestXml(params);
		String resultXml  = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_UPLOAD_File, requestXml);
			
		LogMe.d(requestXml+"\n\n"+resultXml);
		BaseBean b = PropertyUtil.parseToBaseInfo(resultXml);
		return b;
	}
	
	
	/**
	 * 删除文件
	 * 
	 * <Request>
		<userCode>用户名</userCode>
		<admId>就诊Id</admId>
		<fileName>文件名称</fileName>
		</Request>
	 * @param userCode
	 * @return
	 * @throws Exception
	 */
	public static BaseBean delFile(String userCode,String admId,String filePath,String fileName) throws Exception {
		Map params = new HashMap();
		params.put("userCode", userCode);
		params.put("admId", admId);
		params.put("fileName", fileName);
		
    	String serviceUrl = SettingManager.getServerUrl();
		
		String requestXml = PropertyUtil.buildRequestXml(params);
		String resultXml  = WsApiUtil.loadSoapObject(serviceUrl,Const.BIZ_CODE_DEL_File, requestXml);
			
		LogMe.d(requestXml+"\n\n"+resultXml);
		BaseBean b = PropertyUtil.parseToBaseInfo(resultXml);
		
		return b;
	}
	
	public static FileParm GetTestParm()
	{
		FileParm FP= new FileParm();
		FP.ftpIP="10.10.16.103";
		FP.ftpPort="21";
		FP.ftpUser="dhcc";
		FP.ftpPassword="1";
		FP.filePath="/pic";
		return FP;
	}
}
