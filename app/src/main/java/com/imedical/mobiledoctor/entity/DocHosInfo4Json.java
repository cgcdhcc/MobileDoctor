/**
 *
 */
package com.imedical.mobiledoctor.entity;


/**
 * @author sunzheng
 * public String city_id;// public String 181public String ,;//医院城市id
 * public String id;// public String 10360public String ,;//医院id
 * public String hos_address;// public String 山东省青岛市江苏路16号public String ,;//医院地址
 * public String service_url;// public String http:;//218.58.75.218:8080/dthealth/web/public String ,;//his外网地址
 * public String hos_name;// public String 青岛大学医学院附属医院public String ,;//医院名称
 * public String province_id;// public String 15public String ,;//医院省份id
 * public String simple_desc;// public String 青岛大学医学院附属医院始建于1898年，是一所省属综合性教学医院，山东省东部区域医疗中心，面积达20万平方米，是科室齐全、设备先进、技术雄厚、环境优雅、建筑布局合理，集医疗、教学、科研、预防保健和康复为一体的区域龙头医院。public String ,;//医院描述
 * public String province_desc;// public String 山东public String ,;//省份描述
 * public String city_desc;// public String 青岛市public String ;//城市描述
 * <p>
 * 2015-8-21
 */
public class DocHosInfo4Json extends BaseBeanMy {
    private static final long serialVersionUID = 1L;

    public DocHosInfo4Json() {

    }

    public DocHosInfo4Json(boolean b, String msg) {
        this.success = b;
        this.msg = msg;
    }

    public HosInfoData data;

    public class HosInfoData {
        public String city_id;// public String 181public String ,;//医院城市id
        public String id;// public String 10360public String ,;//医院id
        public String hos_address;// public String 山东省青岛市江苏路16号public String ,;//医院地址
        public String service_url;// public String http:;//218.58.75.218:8080/dthealth/web/public String ,;//his外网地址
        public String hos_name;// public String 青岛大学医学院附属医院public String ,;//医院名称
        public String province_id;// public String 15public String ,;//医院省份id
        public String simple_desc;// public String 青岛大学医学院附属医院始建于1898年，是一所省属综合性教学医院，山东省东部区域医疗中心，面积达20万平方米，是科室齐全、设备先进、技术雄厚、环境优雅、建筑布局合理，集医疗、教学、科研、预防保健和康复为一体的区域龙头医院。public String ,;//医院描述
        public String province_desc;// public String 山东public String ,;//省份描述
        public String city_desc;// public String 青岛市public String ;//城市描述
    }
}
