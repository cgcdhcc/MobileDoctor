package com.imedical.mobiledoctor.entity;

/**
 * <FileParm>
 * <ftpIP>Ftp 服务器IP 地址</ftpIP>
 * <ftpPort>Ftp 服务器端口</ftpPort>
 * <ftpUser>Ftp 用户</ftpUser>
 * <ftpPassword>Ftp 口令</ftpPassword>
 * <filePath>文件路径</filePath>
 * <fileName>文件名称</fileName>
 * </FileParm>
 *
 * @author sszvip
 */
public class FileParm {
    public FileParm() {
    }

    public String ftpIP;
    public String ftpPort;
    public String ftpUser;
    public String ftpPassword;
    public String filePath;
    public String fileName;
}
