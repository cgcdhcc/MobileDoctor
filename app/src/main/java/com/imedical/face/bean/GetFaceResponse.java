package com.imedical.face.bean;

public class GetFaceResponse {
    public int code;//": 0,  -1302
    public String message;//": "OK",  ERROR_PERSON_EXISTED
    public FaceData data;
    public class FaceData {
        public String[] face_ids;
    }
}

