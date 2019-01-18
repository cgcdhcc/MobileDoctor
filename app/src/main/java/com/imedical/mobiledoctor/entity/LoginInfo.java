package com.imedical.mobiledoctor.entity;
/**
 * 存放用户登陆成功返回的信息
 * <LoginInfo> <userCode>用户代码</userCode> <userName>用户名</username>
 * <defaultDeptId>科室Id</defaultDeptId> <defaultDeptName>科室名称</defaultDeptName>
 * <defaultGroupId>安全组Id</defaultGroupId>
 * <defaultGroupName>安全组名称</defaultGroupName> <hospitalId>医院Id</hospitalId>
 * <hospitalName>医院名称</hospitalName> <loginNum>当天登陆的次数</loginNum> </LoginInfo>
 */

/**
 * @author lqz
 * @version 1.0
 * @ClassName: LoginInfo
 * @Description: TODO(存放用户登录信息)
 * @date 2015-11-24 上午11:20:07
 */
public class LoginInfo {

    public String userCode;
    public String userName;
    public String defaultDeptId;
    public String defaultDeptName;
    public String defaultGroupId;
    public String defaultGroupName;
    public String hospitalId;
    public String hospitalName;
    public String loginNum;
    public String passWord;
    public String docMarkId;
    public LoginInfo() {
    }

}
