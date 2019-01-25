package com.imedical.face.bean;

import com.imedical.mobiledoctor.Const;

public class AddGroupidsRequest {
    public  Body body;

    public AddGroupidsRequest(String person_id){
        this.body=new Body();
        this.body.person_id=person_id;
        this.body.group_ids=new String[]{Const.face_groupid};
    }

    public class Body{
        public String person_id;
        public String[] group_ids;
    }
}
