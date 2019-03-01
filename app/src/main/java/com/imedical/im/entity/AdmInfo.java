package com.imedical.im.entity;

import java.io.Serializable;

public class AdmInfo implements Serializable {
    public String admId;//>就诊Id</admId>
    public String patientAge;//>患者年龄</patientAge>
    public String patientSex;//>患者性别</patientSex>
    public String patientName;//>患者姓名</patientName>
    public String doctorId;//>医生Id</doctorId>
    public String doctorName;//>医生姓名 </doctorName>
    public String patientId;//>患者Id</patientId>
    public String doctorAlias;//>挂号号别 </doctorAlias>
    public String doctorTitle;//>医生级别</doctorTitle>
    public String departmentId;//>科室Id</departmentId>
    public String departmentName;//>科室名称</departmentName>
    public String registerDate;//>挂号日期</registerDate>
    public String stateCode;//>状态Code A待就诊  F已就诊</stateCode>
    public String stateDesc;//>状态描述 </stateDesc>
    public String feeSum;//>费用</feeSum>
    public String seqCode;//>排队号</seqCode>
    public String sessionName;//>医生出诊时段：全天 上午 下午 夜间</sessionName>
    public String visitType;//>医生接诊类型 N 正常 V视频 T图文</visitType>
    public String patientIDNo;//>患者身份证号</patientIDNo>
    public String doctorSessType;//>医生出诊服务级别</doctorSessType>
    public String admitAddress;//>就诊地点</admitAddress>
    public String admType;//>就诊类型 门诊  急诊 体检</admType>
    public String admitDate;//>就诊日期</admitDate>
    public String registerId;//>挂号记录Id</registerId>
    public String patientCard;//>患者卡号</patientCard>
    public String doctorPicUrl;//>医生头像</doctorPicUrl>
    public String admitTimeRange;//>就诊时间段</admitTimeRange>
    public String complaintStr;//>既往史 婚育等</complaintStr>
    public String evaluateFlag;//>是否评价 Y/N</evaluateFlag>
    public String picUrl;//>患者上传的图片，多个用”,”分割</picUrl>
    public String patientContent;//>患者所填写病情描述</patientContent>
    public String doctorContent;//>医生建议</doctorContent>
    public String callCode;//>视频呼叫状态</callCode>
    public String opProcedure;//>医生随笔</opProcedure>
    public String videoDuration;//>视频长度</videoDuration>
    public String videoStreamUrl;//>视频直播地址</videoStreamUrl>
    public String videoUrl;//>归档视频调阅地址</videoUrl>
    public String picVersion;//>OP 老工程 NW 新成功</picVersion>
    public String chatStatus;//>0</chatStatus>聊天状态
    public String patAvatarUrl;//患者头像
    public String docMarkId;
    public String toOpenId;//视频对象
    public String toPhone;

    public AdmInfo(String patName,String admId,String patSex,String patAge,String patAvatarUrl,String docMarkId,String doctorName,String doctorTitle,String doctorCode,String doctorPicUrl,String chatStatus,String registerId){
       this.patientName=patName;
       this.admId=admId;
       this.patientSex=patSex;
       this.patientAge=patAge;
       this.patAvatarUrl=patAvatarUrl;
       this.doctorName=doctorName;
       this.doctorTitle=doctorTitle;
       this.doctorId=doctorCode;
       this.doctorPicUrl=doctorPicUrl;
       this.chatStatus=chatStatus;
       this.registerId=registerId;
    }
    public AdmInfo(){

    }
}
