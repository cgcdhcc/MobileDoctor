package com.imedical.mobiledoctor.entity;

import java.util.List;

public class PostFile4Json extends BaseBeanMy {
    public List<FileInfo> data;

    public class FileInfo {
        public String id;
        public String businessId;
        public String businessType;
        public String uploadAttachmentUrl;
        public String uploadAttachmentName;
        public String contentType;
        public String remark;
        public String addDate;
        public String fileUrl;
    }


}
