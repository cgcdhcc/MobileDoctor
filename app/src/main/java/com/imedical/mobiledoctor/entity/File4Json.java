package com.imedical.mobiledoctor.entity;


/**
 * @author sszvip
 */
public class File4Json extends BaseBeanMy {

    public FileInfo data;

    public class FileInfo {
        public String id;
        public String businessId;
        public String businessType;
        public String uploadAttachmentUrl;
        public String uploadAttachmentName;
        public String contentType;
        public String remark;
        public String addDate;
    }

    /**
     * {"data":{"id":"8a8185c646f6429e0146f65e8eb4000c"
     * ,"businessId":"7e6d225c366746f9b8be8d26a47107f8","businessType":"A"
     * ,"uploadAttachmentUrl":
     * "file/A/7e6d225c366746f9b8be8d26a47107f8/15654321234_20140702172206.jpg"
     * ,"uploadAttachmentName":"15654321234_20140702172206.jpg"
     * ,"contentType":"I","remark":null,"addDate":"2014-07-02 17:18:50"}
     * ,"msg":"ä¸ä¼ æåï¼","success":true}
     */

}
