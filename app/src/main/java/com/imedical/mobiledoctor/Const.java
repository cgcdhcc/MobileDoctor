package com.imedical.mobiledoctor;


import android.support.v4.content.res.ColorStateListInflaterCompat;

import com.imedical.mobiledoctor.entity.LoginInfo;
import com.imedical.mobiledoctor.entity.PatientInfo;
import com.imedical.mobiledoctor.entity.SeeDoctorRecord;

import java.util.List;

public final class Const {
	public static String jpushurl="https://pay.qduhospital.cn/message/api/messageapi/";
	public static String jpushkey="04bf4651651d89ac88cda97c";
	public static boolean AES_OR_NOT=true;
	public static boolean ISTEST=true;
	public static int suffontsize=30;
	public static String DeviceId;
	public static String brand_name;
	public static String brand_type;
	public static final String source="android";
	public static final String key="d33c40752927d5d6d7ef904a73ea253e";
	public static final String appId="548d19cb-1ac8-11e9-9475-005056914e7e";
	public static final String IMG_URL="https://wxzfb.qduhospital.cn/mpupload";
	public static final String SERVICEURL = "https://wxzfb.qduhospital.cn/dhccam/gateway/index";
	public static final String URL_CREATE="https://wxzfb.qduhospital.cn/dhccam/token/generate?grant_type=api_credentials";
	public static final String URL_REFRESH="https://pay.qduhospital.cn/apimanager/api/token/generate?grant_type=refresh_token&refresh_token=";
	public static LoginInfo loginInfo=null;
	public static PatientInfo curPat=null;
	public static SeeDoctorRecord curSRecorder=null;
	public static List<SeeDoctorRecord> SRecorderList=null; //只在wardRoundActivity加载一次
	public static final String KEY_DEFAULT_HOS_ID                  = "KEY_DEFAULT_HOS_ID";
	public static final String KEY_DEFAULT_HOS_NAME                = "KEY_DEFAULT_HOS_NAME";
	public static final String BIZ_CODE_LOSE_PSW = "1";
	public static final String BIZ_CODE_UPDATE_PSW = null;
	
	public static final int VALUE_PageFrag2 = 1000;
    /*
	//业务代码
	//
	//首页网格布局
	public static final String BIZ_CODE_LIST_GRID					=  "50107";//获取网格菜单协和测试(50109)
	public static final String BIZ_CODE_FEEDBACK				=  "50108";//用户反馈
	//医生出诊信息
	public static final String BIZ_CODE_GetScheduleList				=  "51801";//出诊信息
	public static final String BIZ_CODE_GetDoctorAppList				=  "51802";//查询已预约患者列表
	public static final String BIZ_CODE_LIST_HOSPITAL              = "10001";//
	public static final String BIZ_CODE_actCode_Respiratory        =  "020201";//呼吸
	public static final String BIZ_CODE_actCode_pulse              =  "020301";//脉搏
	public static final String BIZ_CODE_TYPE_temperature           =  "020401";//体温
	public static final String BIZ_CODE_CHECK_VERSION              =  "50100";//检测版本号
	public static final String BIZ_CODE_LOGIN                      =  "50101";
	public static final String BIZ_CODE_LIST_DEPT                  =  "50102";
	public static final String BIZ_CODE_LIST_ALL_DEPT              =  "50106";
	public static final String BIZ_CODE_LIST_PatientInfo           =  "50201";
	public static final String BIZ_CODE_CONTENT_PatientInfo        =  "50202";
	public static final String BIZ__SEARCH_PatientInfo             =  "50204";//通过床位号和姓名查病人--lqz--add
	public static final String BIZ_CODE_LIST_Diagnosis             =  "50301";
	public static final String BIZ_CODE_LIST_Diagnosis_HIS         =  "50302";//历史诊断
	public static final String BIZ_CODE_SAVE_Diagnosis             =  "50308";
	public static final String BIZ_CODE_UPDATE_Diagnosis           =  "50309";
	public static final String BIZ_CODE_LIST_Lis                   =  "50501";//检验报告
	public static final String BIZ_CDOE_LIS_Item			       =  "50502";//检验报告明细
	public static final String BIZ_CODE_LIS_Curve	               =  "50503";//单个检验标本的曲线图
	public static final String BIZ_CDOE_LIS_Compare				   =  "50504";//相同项目历次数据比较
	
	public static final String BIZ_CODE_LIST_DiagType              =  "50303";
	public static final String BIZ_CODE_LIST_DiagStatus            =  "50304";//诊断状态
	public static final String BIZ_CODE_LIST_DiaTabs               =  "50305";//诊断模板页签诊断
	public static final String BIZ_CDOE_LIS_Item_of_DiaTabs        =  "50306";//诊断模板页签
	public static final String BIZ_CODE_LIST_DiagItem              =  "50307";//查询ICD诊断项目
	public static final String BIZ_CODE_DELETE_DiagItem            =  "50310";//查询ICD诊断项目
	
	public static final String BIZ_CODE_LIST_OrderItem_LONG        =  "50401";//长期医嘱
	public static final String BIZ_CODE_LIST_OrderItem_TEMP        =  "50402";//临时医嘱
	public static final String BIZ_CODE_LIST_OrderItem_STOP        =  "50403";//停止医嘱
	
	public static final String BIZ_CODE_LIST_Ris	               =  "50601";//检查列表
	public static final String BIZ_CODE_Ris                        =  "50602";//检查报告
	public static final String BIZ_CODE_Dicom					   =  "50603";//Pacs Dicom
	public static final String BIZ_CODE_Ris_PACS                   =  "50604";//协和pacs
	
	public static final String BIZ_CODE_LIST_Temp 				   =  "50701";//体温单
	public static final String BIZ_CODE_ViewTempture               =  "50702";//手动绘制体温单所需数据
	public static final String BIZ_CODE_LIST_BrowseLocation 	   =  "50801";//电子病历
	
	public static final String BIZ_CODE_LIST_Priority 	           =  "50901";//获取医嘱优先级代码
	public static final String BIZ_CODE_LIST_Frequency 	           =  "50902";//获取医嘱频次代码
	public static final String BIZ_CODE_LIST_Instruction           =  "50903";//获取医嘱优先级代码
	public static final String BIZ_CODE_LIST_SkinAction 	       =  "50904";//获取皮试备注代码
	public static final String BIZ_CODE_LIST_ArcimItem 	           =  "50905";//获取医嘱优先级代码
	public static final String BIZ_CODE_LIST_OETabs 	           =  "50906";//获取医嘱模板一级页签
	public static final String BIZ_CODE_LIST_OETabs_2 	           =  "50907";//获取医嘱模板二级页签
	public static final String BIZ_CODE_LIST_OETabs_info           =  "50908";//获取医嘱模板的医嘱信息
	public static final String BIZ_CODE_DETAIL_ArcimItem           =  "50909";//获取医嘱项目的默认参数
	public static final String BIZ_CODE_SAVE_ArcimItem             =  "50910";//增加/更新
	public static final String BIZ_CODE_LIST_INPUT_ArcimItem       =  "50911";//保存的医嘱项目
	public static final String BIZ_CODE_DETAIL_SAVED_ArcimItem     =  "50912";//点击其中一条医嘱项目时加载50912
	public static final String BIZ_CODE_DEL_ArcimItem              =  "50913";//删除医嘱项目
	public static final String BIZ_CODE_VERIFY_ArcimItem           =  "50914";//审核医嘱项目
	public static final String BIZ_CODE_LIST_DEPT_BY_PR_IN         =  "50915";//删除医嘱项目
	public static final String BIZ_CODE_DETAIL_FISTTIME_INFO       =  "50917";//50916返回的数据格式有问题，长期医嘱首日次数
	public static final String BIZ_CODE_LIST_FlowRateUnit          =  "50918";//获取输液流速代码
	public static final String BIZ_CODE_LIST_AntReason   	       =  "50919";//获取抗生素用药原因
	public static final String BIZ_CODE_LIST_PopMessage   	       =  "50920";//获取抗生素用药原因
	public static final String BIZ_CODE_LIST_PopMessage_VERYFY     =  "50921";//获取抗生素用药原因
	//集成视图
	public static final String BIZ_CODE_TimeLineCategory           =  "51001";
	public static final String BIZ_CODE_SET_TimeLineCategory       =  "51002";
	public static final String BIZ_CODE_TimeLineCategory_Setting   =  "51003";
	public static final String BIZ_CODE_ViewInfo                   =  "51004";//
	public static final String BIZ_CODE_ViewData                   =  "51005";//
	
	public static final String BIZ_CODE_SeeDoctorRecord            =  "51006";//获取历次就诊纪录
	
	public static final String BIZ_CODE_DETAIL_ANTI_BaseInfo       =  "51201";//基本信息
	public static final String BIZ_CODE_LIST_AntBodyPart           =  "51202";//感染部位
	public static final String BIZ_CODE_LIST_AntReason_2           =  "51203";//原因
	public static final String BIZ_CODE_LIST_AntIndication         =  "51204";//目的
	public static final String BIZ_CODE_LIST_OperaTime             =  "51205";//预防用药时间
	public static final String BIZ_CODE_LIST_OrderSuscept          =  "51206";//获取药敏结果
	public static final String BIZ_CODE_LIST_Operation             =  "51207";//获取手术申请
	public static final String BIZ_CODE_LIST_ConsultLoc            =  "51208";//获取会诊科室
	public static final String BIZ_CODE_LIST_ConsultDoc            =  "51209";//获取会诊
	public static final String BIZ_CODE_LIST_Instruction_apply     =  "51210";//获取会诊
	public static final String BIZ_CODE_POST_ParamAnti_veryf       =  "51211";//校验
	public static final String BIZ_CODE_POST_ParamAnti_MainReturn  =  "51212";//提交
	public static final String BIZ_CODE_DETAIL_AntReasonData       =  "51213";//加载抗生素信息
	public static final String BIZ_CODE_LIST_AntAppInfo            =  "51214";//抗生素用药审核列表
	public static final String BIZ_CODE_DETAIL_AntAppInfo          =  "51215";//抗生素详细信息
	public static final String BIZ_CODE_VERYFY_AntAppInfo          =  "51216";//抗生素审核与拒绝
	
	public static final String BIZ_CODE_DETAIL_FileParm            =  "51301";//文件参数
	public static final String BIZ_CODE_LIST_MediaFile             =  "51302";//文件列表
	public static final String BIZ_CODE_UPLOAD_File                =  "51303";//上传
	public static final String BIZ_CODE_DEL_File                   =  "51304";//删除
	
	public static final String BIZ_CODE_LIST_ReportData            =  "51401";//危急值列表
	public static final String BIZ_CODE_READ_BASEINFO              =  "51402";//阅读危急值
	
	public static final String BIZ_CODE_LIST_ConsultData           =  "51501";//会诊记录管理
	public static final String BIZ_CODE_DETAIL_ConsultInfo         =  "51502";//获取会诊记录的详细内容
	public static final String BIZ_CODE_UPDATE_ConsultInfo         =  "51503";//会诊更新会诊意见
	public static final String BIZ_CODE_DO_Consult_FEE             =  "51504";//执行会诊记录计费
	
	public static final String BIZ_CODE_LIST_ADDRESS_BOOK          =  "51601";//通讯录
//日历预约
	public static final String BIZ_CODE_LIST_ORDER_BY_DATE	        =  "52101";//获取日历预约信息
	public static final String BIZ_CODE_LIST_ORDER_DEPT		        =  "52107";//获取科室列表
	public static final String BIZ_CODE_LIST_ORDER_REG		        =  "52102";//获取号别信息
	public static final String BIZ_CODE_LIST_ORDER_REG_DETAIL		=  "52103";//获取患者挂号详情
	public static final String BIZ_CODE_LIST_ORDER_REG_CANCEL		=  "52105";//取消预约
	public static final String BIZ_CODE_LIST_ORDER_REG_DETAIL_BY_DATE=  "52104";//获取某个时间段的预约列表
	public static final String BIZ_CODE_LIST_ORDER_REG_DETAIL_BY_NAME=  "52106";//搜索病人
	public static final String BIZ_CODE_LIST_ORDER_REG_SURE			=  "52108";//确认预约
	public static final String BIZ_CODE_LIST_ORDER_REG_HISTORY		=  "52109";//历史记录
	public static final String BIZ_CODE_LIST_ORDER_SCHE				=  "52110";//排班科室
	public static final String BIZ_CODE_LIST_ORDER_SCHE_SURE		=  "52111";//是否新增排班
*/
	//业务代码
	//
	public static final String BIZ_CODE_LIST_OperationData         =  "doctor.operation.operationlist";//"51505";//手术列表
	public static final String BIZ_CODE_CHECK_VERSION              =  "doctor.login.systemparams";//50100//检测版本号
	public static final String BIZ_CODE_LOGIN                      =  "doctor.login.login";//50101
	public static final String BIZ_CODE_LIST_DEPT                  =  "doctor.login.logindepartment";//50102
	public static final String BIZ_CODE_LIST_ALL_DEPT              =  "doctor.login.allhospitaldepartment";//50106
	public static final String BIZ_CODE_LIST_PatientInfo           =  "doctor.patient.departmentpatints";//50201
	public static final String BIZ_CODE_CONTENT_PatientInfo        =  "doctor.patient.getpatient";//50202
	public static final String BIZ__SEARCH_PatientInfo             =  "doctor.report.itemreportcompare";//"50204";//通过床位号和姓名查病人--lqz--add
	public static final String BIZ_CODE_LIST_Diagnosis             =  "doctor.diagnose.getdiagnose";//50301
	public static final String BIZ_CODE_LIST_Diagnosis_HIS         =  "doctor.diagnose.historydiagnose";//50302//历史诊断
	public static final String BIZ_CODE_SAVE_Diagnosis             =  "doctor.diagnose.adddiagnose";//50308
	public static final String BIZ_CODE_UPDATE_Diagnosis           =  "doctor.diagnose.updatediagnoseremark";//50309
	public static final	String BIZ_CODE_LIST_Lis                   =  "doctor.report.reports";//50501//检验报告
	public static final String BIZ_CDOE_LIS_Item			       =  "doctor.report.itemreport";//"50502";//检验报告明细
	public static final String BIZ_CODE_LIS_Curve	               =  "doctor.report.itemreportchart";//50503//单个检验标本的曲线图
	public static final String BIZ_CDOE_LIS_Compare				   =  "doctor.report.itemreportcompare";//"50504";//相同项目历次数据比较

	public static final String BIZ_CODE_LIST_DiagType              =  "doctor.diagnose.diagnosetype";//50303
	public static final String BIZ_CODE_LIST_DiagStatus            =  "doctor.diagnose.diagnosestatus";//50304//诊断状态
	public static final String BIZ_CODE_LIST_DiaTabs               =  "doctor.diagnose.diagnosetabs";//50305//诊断模板页签诊断
	public static final String BIZ_CDOE_LIS_Item_of_DiaTabs        =  "doctor.diagnose.diagnosetablist";//诊断模板页签
	public static final String BIZ_CODE_LIST_DiagItem              =  "doctor.diagnose.diagnoseicd";//50307//查询ICD诊断项目
	public static final String BIZ_CODE_DELETE_DiagItem            =  "doctor.diagnose.removediagnose";//50310//查询ICD诊断项目

	public static final String BIZ_CODE_LIST_OrderItem_LONG        =  "doctor.order.longorder";//50401//长期医嘱
	public static final String BIZ_CODE_LIST_OrderItem_TEMP        =  "doctor.order.temporaryorder";//50402//临时医嘱
	public static final String BIZ_CODE_LIST_OrderItem_STOP        =  "doctor.order.stoporder";//50403//停止医嘱

	public static final String BIZ_CODE_LIST_Ris	               =  "doctor.report.advicelist";//50601//检查列表
	public static final String BIZ_CODE_Ris                        =  "doctor.report.inspectreport";//50602//检查报告
	public static final String BIZ_CODE_Dicom					   =  "doctor.report.visitparams";//50603//Pacs Dicom
	public static final String BIZ_CODE_Ris_PACS                   =  "doctor.report.connectionparams";//50604//协和pacs

	public static final String BIZ_CODE_LIST_Temp 				   =  "doctor.tempature.image";//50701//体温单
	public static final String BIZ_CODE_ViewTempture               =  "doctor.tempature.chart";//50702//手动绘制体温单所需数据
	public static final String BIZ_CODE_LIST_BrowseLocation 	   =  "doctor.case.image";//50801//电子病历

	public static final String BIZ_CODE_LIST_Priority 	           =  "doctor.advice.prioritycode";//50901//获取医嘱优先级代码
	public static final String BIZ_CODE_LIST_Frequency 	           =  "doctor.advice.frequencycode";//50902//获取医嘱频次代码
	public static final String BIZ_CODE_LIST_Instruction           =  "doctor.advice.instructioncode";//50903//获取医嘱优先级代码
	public static final String BIZ_CODE_LIST_SkinAction 	       =  "doctor.advice.skinaction";//50904//获取皮试备注代码
	public static final String BIZ_CODE_LIST_ArcimItem 	           =  "doctor.advice.adviceitem";//50905//获取医嘱优先级代码
	public static final String BIZ_CODE_LIST_OETabs 	           =  "doctor.advice.tabs";//50906//获取医嘱模板一级页签
	public static final String BIZ_CODE_LIST_OETabs_2 	           =  "doctor.advice.subtabs";//50907//获取医嘱模板二级页签
	public static final String BIZ_CODE_LIST_OETabs_info           =  "doctor.advice.tabitem";//50908//获取医嘱模板的医嘱信息
	public static final String BIZ_CODE_DETAIL_ArcimItem           =  "doctor.advice.itemdetails";//50909//获取医嘱项目的默认参数
	public static final String BIZ_CODE_SAVE_ArcimItem             =  "doctor.advice.temporaryitem";//50910//增加/更新
	public static final String BIZ_CODE_LIST_INPUT_ArcimItem       =  "doctor.advice.getadviceinfo";//50911//保存的医嘱项目
	public static final String BIZ_CODE_DETAIL_SAVED_ArcimItem     =  "doctor.advice.getadvicedetails";//50912//点击其中一条医嘱项目时加载50912
	public static final String BIZ_CODE_DEL_ArcimItem              =  "doctor.advice.removetemporarydata";//50913//删除医嘱项目
	public static final String BIZ_CODE_VERIFY_ArcimItem           =  "doctor.advice.examineorder";//50914//审核医嘱项目
	public static final String BIZ_CODE_LIST_DEPT_BY_PR_IN         =  "doctor.advice.departmentbypriority";//50915//删除医嘱项目
	public static final String BIZ_CODE_DETAIL_FISTTIME_INFO       =  "doctor.advice.supconfigparams";//50917//50916返回的数据格式有问题，长期医嘱首日次数
	public static final String BIZ_CODE_LIST_FlowRateUnit          =  "doctor.advice.flowvelocity";//50918//获取输液流速代码
	public static final String BIZ_CODE_LIST_AntReason   	       =  "doctor.advice.antibioticdecription";//50919//获取抗生素用药原因
	public static final String BIZ_CODE_LIST_PopMessage   	       =  "doctor.advice.checkvalidity";//50920//获取抗生素用药原因
	public static final String BIZ_CODE_LIST_PopMessage_VERYFY     =  "doctor.advice.examinecheckvalidity";//50921//获取抗生素用药原因
	//集成视图
	public static final String BIZ_CODE_TimeLineCategory           =  "doctor.view.category";//51001
	public static final String BIZ_CODE_SET_TimeLineCategory       =  "doctor.view.addconfig";//51002
	public static final String BIZ_CODE_TimeLineCategory_Setting   =  "doctor.view.getconfig";//51003
	public static final String BIZ_CODE_ViewInfo                   =  "doctor.view.info";//51004//
	public static final String BIZ_CODE_ViewData                   =  "doctor.view.dataitem";//51005//

	public static final String BIZ_CODE_SeeDoctorRecord            =  "doctor.view.historydiagnoserecord";//51006//获取历次就诊纪录

	public static final String BIZ_CODE_DETAIL_ANTI_BaseInfo       =  "doctor.antibiotic.info";//51201//基本信息
	public static final String BIZ_CODE_LIST_AntBodyPart           =  "doctor.antibiotic.infectedpart";//51202//感染部位
	public static final String BIZ_CODE_LIST_AntReason_2           =  "doctor.antibiotic.purpose";//51203//原因
	public static final String BIZ_CODE_LIST_AntIndication         =  "doctor.antibiotic.indication";//51204//目的
	public static final String BIZ_CODE_LIST_OperaTime             =  "doctor.antibiotic.operationtime";//51205//预防用药时间
	public static final String BIZ_CODE_LIST_OrderSuscept          =  "doctor.antibiotic.suscept";//51206//获取药敏结果
	public static final String BIZ_CODE_LIST_Operation             =  "doctor.antibiotic.operationapplication";//51207//获取手术申请
	public static final String BIZ_CODE_LIST_ConsultLoc            =  "doctor.antibiotic.consultdeparment";//51208//获取会诊科室
	public static final String BIZ_CODE_LIST_ConsultDoc            =  "doctor.antibiotic.consultdoctor";//51209//获取会诊
	public static final String BIZ_CODE_LIST_Instruction_apply     =  "doctor.antibiotic.instruction";//51210//获取会诊
	public static final String BIZ_CODE_POST_ParamAnti_veryf       =  "doctor.antibiotic.checkvalidity";//51211//校验
	public static final String BIZ_CODE_POST_ParamAnti_MainReturn  =  "doctor.antibiotic.savedata";//51212//提交
	public static final String BIZ_CODE_DETAIL_AntReasonData       =  "doctor.antibiotic.getdata";//51213//加载抗生素信息
	public static final String BIZ_CODE_LIST_AntAppInfo            =  "doctor.antibiotic.antibiotcapplication";//51214//抗生素用药审核列表
	public static final String BIZ_CODE_DETAIL_AntAppInfo          =  "doctor.antibiotic.antibiotcapplicationdetails";//51215//抗生素详细信息
	public static final String BIZ_CODE_VERYFY_AntAppInfo          =  "doctor.antibiotic.examine";//51216//抗生素审核与拒绝

	public static final String BIZ_CODE_DETAIL_FileParm            =  "doctor.media.fileparams";//51301//文件参数
	public static final String BIZ_CODE_LIST_MediaFile             =  "doctor.media.files";//51302//文件列表
	public static final String BIZ_CODE_UPLOAD_File                =  "doctor.media.uploadfile";//51303//上传
	public static final String BIZ_CODE_DEL_File                   =  "doctor.media.removefile";//51304//删除

	public static final String BIZ_CODE_LIST_ReportData            =  "doctor.warn.reports";//51401//危急值列表
	public static final String BIZ_CODE_READ_BASEINFO              =  "doctor.warn.readreport";//51402//阅读危急值

	public static final String BIZ_CODE_LIST_ConsultData           =  "doctor.consult.recordlist";//51501//会诊记录管理
	public static final String BIZ_CODE_DETAIL_ConsultInfo         =  "doctor.consult.recorddetails";//51502//获取会诊记录的详细内容
	public static final String BIZ_CODE_UPDATE_ConsultInfo         =  "doctor.consult.updateconsult";//51503//会诊更新会诊意见
	public static final String BIZ_CODE_DO_Consult_FEE             =  "doctor.consult.excuteconsult";//51504//执行会诊记录计费

	public static final String BIZ_CODE_LIST_ADDRESS_BOOK          =  "null";//51601//通讯录
	//日历预约
	public static final String BIZ_CODE_LIST_ORDER_BY_DATE	        =  "doctor.book.bookdata";//52101//获取日历预约信息
	public static final String BIZ_CODE_LIST_ORDER_DEPT		        =  "doctor.book.departmentlist";//52107//获取科室列表
	public static final String BIZ_CODE_LIST_ORDER_REG		        =  "doctor.book.timerangedata";//52102//获取号别信息
	public static final String BIZ_CODE_LIST_ORDER_REG_DETAIL		=  "doctor.book.timerangedetails";//52103//获取患者挂号详情
	public static final String BIZ_CODE_LIST_ORDER_REG_CANCEL		=  "doctor.book.cancelbook";//52105//取消预约
	public static final String BIZ_CODE_LIST_ORDER_REG_DETAIL_BY_DATE=  "doctor.book.daterangedata";//52104//获取某个时间段的预约列表
	public static final String BIZ_CODE_LIST_ORDER_REG_DETAIL_BY_NAME=  "doctor.book.searchpatients";//52106//搜索病人
	public static final String BIZ_CODE_LIST_ORDER_REG_SURE			=  "doctor.book.book";//52108//确认预约
	public static final String BIZ_CODE_LIST_ORDER_REG_HISTORY		=  "doctor.book.historyresearchrecord";//52109//历史记录
	public static final String BIZ_CODE_LIST_ORDER_SCHE				=  "doctor.book.addscheduletime";//52110//排班科室
	public static final String BIZ_CODE_LIST_ORDER_SCHE_SURE		=  "doctor.book.addscheduledate";//52111//是否新增排班
	//首页网格布局
	public static final String BIZ_CODE_LIST_GRID					=  "doctor.login.functions";//50107//获取网格菜单协和测试(50109)
	public static final String BIZ_CODE_FEEDBACK				=  "doctor.login.feedback";//50108//用户反馈
	//医生出诊信息
	public static final String BIZ_CODE_GetScheduleList				=  "doctor.dashboard.schedule";//51801//出诊信息
	public static final String BIZ_CODE_GetDoctorAppList				=  "doctor.dashboard.patientlist";//51802//查询已预约患者列表
	public static final String BIZ_CODE_GetHttpFilePath				=  "doctor.media.tags";//"51305";//获取HTTP服务器参数
	public static final String BIZ_CODE_IM_USER_REGISTER="instantchat.usermanagementcategory.RegisteredUserI";//医生聊天注册
	public static final String BIZ_CODE_qrcodegenerate="doctor.ihospital.qrcodegenerate";//生成医生头像
	public static final String BIZ_CODE_GETTIMERANGE="doctor.ihospital.gettimerange";//获取出诊时段
	public static final String BIZ_CODE_SETSCHEDULE="doctor.ihospital.setschedule";//设置出诊时间段
	public static final String BIZ_CODE_DoctorSchedule="doctor.ihospital.getoneschedule";//互联网医院排班-获取医生图文咨询出诊信息（图文咨询）
	public static final String BIZ_CODE_UpdateOneSchedule="doctor.ihospital.updateoneschedule";//## 51903   互联网医院排班-调整挂号限额（图文咨询）
	public static final String BIZ_CODE_GetPatientList="doctor.ihospital.getpatientlist";//获取就诊患者
	public static final String BIZ_CODE_GetAdmInfo="doctor.ihospital.getadminfo";//获取就诊信息
	public static final String API_PHONEVALIDATE     ="mhealth/dhccApi/user/validUser/2/";
	public static final String API_FINDPASS                 ="mhealth/dhccApi/user/updatePwdByVeriCode/";
	public static final String VALUE_URL_JKB              = "http://www.jiankangbao.com/";
}
