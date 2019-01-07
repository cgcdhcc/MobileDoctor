package com.imedical.mobiledoctor.entity;

import com.imedical.mobiledoctor.util.Desc;

import java.io.Serializable;
import java.util.List;

//内部类,章节
public class CateCharpter implements Serializable {
    public String categoryId;
    public String cateCharpterId;
    public String cateCharpterName;
    public String pages;
    @Desc(label = "文件列表", type = Desc.TYPE_LIST)//第三层自动解析到List中
    public List<PageFile> PageFileList; //这里大写是为了跟xml中的节点对应（不符合java规范）

    public CateCharpter() {

    }
}