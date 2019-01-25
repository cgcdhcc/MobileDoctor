package com.imedical.face.bean;

public class DectIdentifyRequest {
    public Body body;

    public DectIdentifyRequest(String group_id, String mode){
        this.body=new  Body();
        this.body.group_id=group_id;
        this.body.mode=mode;
    }

    public class Body{
        public String group_id;
        public String mode;
    }

}
