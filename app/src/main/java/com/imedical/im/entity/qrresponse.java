package com.imedical.im.entity;

import java.util.List;

/**
 * Created by dashan on 2018/7/25.
 */

public class qrresponse {
    public String code;
    public String message;
    public qrdata data;
    public class qrdata{
        public String qr;
        public String url;

    }
}
