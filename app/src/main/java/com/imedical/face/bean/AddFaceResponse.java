package com.imedical.face.bean;

public class AddFaceResponse {
    public int code;//": 0,  -1302
    public String message;//": "OK",  ERROR_PERSON_EXISTED
    public AddFaceData data;
    public class AddFaceData {
        public String person_id;//":"1122221","
        public int added;//":1,"
        public String[] face_ids;
        public int[] ret_codes;
    }
}

