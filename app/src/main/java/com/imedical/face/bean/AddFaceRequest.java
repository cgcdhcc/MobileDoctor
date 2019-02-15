package com.imedical.face.bean;

public class AddFaceRequest {
    public Body body;

    public AddFaceRequest(String person_id){
        this.body=new Body();
        this.body.person_id=person_id;
    }

    public class Body{
        public String person_id;
    }
}

