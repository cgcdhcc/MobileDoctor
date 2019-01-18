package com.imedical.mobiledoctor.entity;

public class QrCodeGenerateRequest {
    public String type;//": "doctor",
    public Info info;

    public QrCodeGenerateRequest(String id, String name) {
        this.info = new Info(id, name);
        this.type = "doctor";
    }

    class Info {
        Info(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String id;//": "17756",
        public String name;//": "吕文山"
    }
}
