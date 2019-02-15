package com.imedical.face.bean;

public class IdentifyRequest {
    public Body body;

    public IdentifyRequest(String group_id){
        this.body=new  Body();
        this.body.group_id=group_id;
    }

    public class Body{
        public String group_id;
    }

}
