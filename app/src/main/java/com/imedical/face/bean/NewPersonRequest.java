package com.imedical.face.bean;


import com.imedical.mobiledoctor.Const;

public class NewPersonRequest {
    public Body body;

    public NewPersonRequest(String person_id, String person_name){
        this.body=new Body();
        this.body.person_id=person_id;
        this.body.person_name=person_name;
        this.body.group_ids=new String[]{Const.face_groupid};
    }

    public class Body{
        public String person_id;
        public String person_name;
        public String[] group_ids;
    }
}

