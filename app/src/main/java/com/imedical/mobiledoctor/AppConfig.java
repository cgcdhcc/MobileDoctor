package com.imedical.mobiledoctor;

import android.os.Environment;

/**
 * 发前配置此文件
 * @author sszvip
 *
 */
public class AppConfig {
	//是否测试模式,控制日志,内外网按钮
	public static boolean isTestMode = true;
	//是否内网
	public static boolean isIntranet = false;
	public static String g_basePath = Environment.getExternalStorageDirectory().getAbsolutePath() ;
	public static String FILE_PATH=g_basePath+"/mobiledoctor";
	public static final String HOSPATAL_ID        = "1";//医院ID

	public static final String ServerURL_INTERNET = "https://cloud.qduhospital.cn/MHCApi/services/PublicServiceSoap?wsdl";//秦皇岛
	public static final String ServerURL_INTRANET = "https://cloud.qduhospital.cn/MHCApi/services/PublicServiceSoap?wsdl";// 秦皇岛
	public static final String PIRVATE_FOLDER        = "/newaround";
	public static final String PIRVATE_FOLDER_AUDIO  = PIRVATE_FOLDER+"/audio";
	public static final String PIRVATE_FOLDER_PDF    = PIRVATE_FOLDER+"/pdf";
	public static final String PIRVATE_FOLDER_PAINTCARD = PIRVATE_FOLDER+"/paintPad";
	public static final String FILE_NAME = "dharound.apk";
	public static final String DB_FILE = "dharound.db";

	public static  boolean IS2016=false;//是否是2016的请求方式
	public static  boolean AES_OR_NOT = true; //接口是否加密
	public static  boolean ISADDORDERITEM=false;//是否开放录入医嘱
	public static String deleteFile="https://cloud.qduhospital.cn/dhcappupload/delete.php?filepath=";//文件删除
	public static String upload="https://cloud.qduhospital.cn/dhcappupload/upload.php?businessType=%s&businessId=%s";//文件上传
}
