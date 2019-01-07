package com.imedical.mobiledoctor.entity;

import com.imedical.mobiledoctor.util.Desc;

import java.io.Serializable;
import java.util.List;

/**
 * 一个人，只有一个BrowseLocation
 * sszvip 重写 20130827  (此类结构，与xml结构严格对应)
 *
 * @param args <BrowseLocation>
 *             <browseType>浏览类型/FTP、HTTP、ONEURL</browseType>
 *             <fileServerLocation>FTP或HTTP服务器的主地址</fileServerLocation>
 *             <cateCharpters>章节（页签）数</cateCharpters>
 *             <CateCharpterList>
 *             <CateCharpter>
 *             <categoryId>目录Id</categoryId>
 *             <cateCharpterId>章节（页签）Id</cateCharpterId>
 *             <cateCharpterName>章节（页签）名称</cateCharpterName>
 *             <pages>本章节页数</pages>
 *             <pageFileList>
 *             <PageFile>
 *             <fileLocation>文件目录名称</fileLocation>
 *             </PageFile>
 *             </pageFileList>
 *             </CateCharpter>
 *             </CateCharpterList>
 *             </BrowseLocation>
 */

public class BrowseLocation implements Serializable {

    public String browseType;
    public String fileServerLocation;

    @Desc(label = "章节（页签）数", type = "")
    public String cateCharpters;
    @Desc(label = "章节列表", type = Desc.TYPE_LIST) //第二层自动解析到List中
    public List<CateCharpter> CateCharpterList;

    public BrowseLocation() {

    }


}


