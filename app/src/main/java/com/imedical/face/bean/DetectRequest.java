package com.imedical.face.bean;

public class DetectRequest {
    public Body body;

    public DetectRequest(String mode){
        this.body=new Body();
        this.body.mode=mode;
    }

    public class Body{
        public String mode;
    }
}

