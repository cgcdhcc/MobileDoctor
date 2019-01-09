package com.imedical.mobiledoctor;


import com.imedical.mobiledoctor.entity.LoginInfo;
import com.imedical.mobiledoctor.entity.PatientInfo;
import com.imedical.mobiledoctor.entity.SeeDoctorRecord;

public final class Const {
	public static int suffontsize=30;
	public static String DeviceId;
	public static String brand_name;
	public static String brand_type;
	public static final String key="4d9d9f934c214829aa4d46381de7f013";
	public static final String SERVICEURL = "https://pay.qduhospital.cn/apimanager/api/%s?token=%s&biz_content=%s&service=qyfy.web.encrypt.soap";
	public static final String URL_CREATE="https://pay.qduhospital.cn/apimanager/api/token/app/generate?grant_type=client_credentials";
	public static final String URL_REFRESH="https://pay.qduhospital.cn/apimanager/api/token/generate?grant_type=refresh_token&refresh_token=";
	public static LoginInfo loginInfo;
	public static PatientInfo curPat;
	public static SeeDoctorRecord curSRecorder;
	public static final String KEY_DEFAULT_HOS_ID                  = "KEY_DEFAULT_HOS_ID";
	public static final String KEY_DEFAULT_HOS_NAME                = "KEY_DEFAULT_HOS_NAME";
	public static final String BIZ_CODE_LOSE_PSW = "1";
	public static final String BIZ_CODE_UPDATE_PSW = null;
	
	public static final int VALUE_PageFrag2 = 1000;

	//业务代码
	//
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
//首页网格布局
	public static final String BIZ_CODE_LIST_GRID					=  "50107";//获取网格菜单协和测试(50109)
	public static final String BIZ_CODE_FEEDBACK				=  "50108";//用户反馈
//医生出诊信息
	public static final String BIZ_CODE_GetScheduleList				=  "51801";//出诊信息
	public static final String BIZ_CODE_GetDoctorAppList				=  "51802";//查询已预约患者列表
	public static final String API_PHONEVALIDATE     ="mhealth/dhccApi/user/validUser/2/";
	public static final String API_FINDPASS                 ="mhealth/dhccApi/user/updatePwdByVeriCode/";
	public static final String VALUE_URL_JKB              = "http://www.jiankangbao.com/";
}
