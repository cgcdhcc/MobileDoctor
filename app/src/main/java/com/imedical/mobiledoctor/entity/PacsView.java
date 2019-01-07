package com.imedical.mobiledoctor.entity;

import com.imedical.mobiledoctor.util.Desc;

public class PacsView {
    @Desc(label = "客户端AETitle", type = "")
    public String localAETitle;
    @Desc(label = "客户端端口", type = "")
    public String localPort;
    @Desc(label = "客户端语法", type = "")
    public String localSyntax;
    @Desc(label = "服务器描述", type = "")
    public String serverDesc;
    @Desc(label = "服务器端AETitle", type = "")
    public String serverAETitle;
    @Desc(label = "服务器IP", type = "")
    public String serverAddress;
    @Desc(label = "服务器端口", type = "")
    public String serverPort;
    @Desc(label = "Retrieve", type = "")
    public String serverRetrieve;
    @Desc(label = "TLS", type = "")
    public String serverTLS;
    @Desc(label = "服务器语法", type = "")
    public String serverSyntax;
    @Desc(label = "accNum,当不为空时，想当于传参类型为AccNum", type = "")
    public String accNum;
    @Desc(label = "patId，当不为空时，相当于传参类型PatID", type = "")
    public String patId;

}
