package com.imedical.face.bean;

public class AddGroupidsResponse {
    public int code;//": 0,  -1302
    public String message;//": "OK",  ERROR_PERSON_EXISTED
    public AddGroupidsData data;

    public class AddGroupidsData {
        public String person_id;//":"1122221","
        public int added;//":1,"
        public String[] group_ids;
    }
}
