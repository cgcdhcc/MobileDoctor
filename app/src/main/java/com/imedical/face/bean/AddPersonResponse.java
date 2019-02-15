package com.imedical.face.bean;

public class AddPersonResponse {
    public int code;//": 0,  -1302
    public String message;//": "OK",  ERROR_PERSON_EXISTED
    public AddPersonData data;

    public class AddPersonData {
        public String person_id;//":"1122221","
        public int suc_group;//":1,"
        public int suc_face;//":1,"
        public String session_id;//":"","
        public String face_id;//":"2891384694425811237"
        public String[] group_ids;
    }
}
