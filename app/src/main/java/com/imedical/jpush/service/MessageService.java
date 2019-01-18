package com.imedical.jpush.service;

import com.google.gson.Gson;
import com.imedical.jpush.bean.MessageNoRead;
import com.imedical.jpush.bean.MsgResponse;
import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.api.RequestUtil;
import com.imedical.mobiledoctor.util.LogMe;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dashan on 2017/7/10.
 */

public class MessageService {

    public static MsgResponse queryMsg(final String canload){
        final List<NameValuePair> param=new ArrayList<NameValuePair>();
        param.add(new NameValuePair() {
            @Override
            public String getName() {
                return "userid";
            }

            @Override
            public String getValue() {
                return "reg_"+ Const.loginInfo.userCode;
            }
        });
        param.add(new NameValuePair() {
            @Override
            public String getName() {
                return "appkey";
            }

            @Override
            public String getValue() {
                return Const.jpushkey;
            }
        });
        param.add(new NameValuePair() {
            @Override
            public String getName() {
                return "pagesize";
            }

            @Override
            public String getValue() {
                return "10";
            }
        });
        param.add(new NameValuePair() {
            @Override
            public String getName() {
                return "canload";
            }

            @Override
            public String getValue() {
                return canload;
            }
        });
        String result= RequestUtil.doPost(Const.jpushurl+"getappmsg",param);

        //result=result.replaceAll("\\[\\]","\"\"");//容错，防止返回的jumpdata中返回[]导致解析失败
        LogMe.d("msg",result);
        Gson g=new Gson();
        MsgResponse response=g.fromJson(result,MsgResponse.class);

        return response;

    }
    //获取未读消息个数
    public static MessageNoRead getunreadcount(){
        final List<NameValuePair> param=new ArrayList<NameValuePair>();
        param.add(new NameValuePair() {
            @Override
            public String getName() {
                return "userid";
            }

            @Override
            public String getValue() {
                return "reg_"+ Const.loginInfo.userCode;
            }
        });
        param.add(new NameValuePair() {
            @Override
            public String getName() {
                return "appkey";
            }

            @Override
            public String getValue() {
                return Const.jpushkey;
            }
        });

        String result= RequestUtil.doPost(Const.jpushurl+"getunreadcount",param);
        LogMe.d("msg",result);
        Gson g=new Gson();
        MessageNoRead response=g.fromJson(result,MessageNoRead.class);
        return response;

    }

    //全部置为已读

    public static MessageNoRead updateallmsgstatus(){
        final List<NameValuePair> param=new ArrayList<NameValuePair>();
        param.add(new NameValuePair() {
            @Override
            public String getName() {
                return "userid";
            }

            @Override
            public String getValue() {
                return "reg_"+ Const.loginInfo.userCode;
            }
        });
        param.add(new NameValuePair() {
            @Override
            public String getName() {
                return "appkey";
            }

            @Override
            public String getValue() {
                return Const.jpushkey;
            }
        });

        String result= RequestUtil.doPost(Const.jpushurl+"updateallmsgstatus",param);
        LogMe.d("msg",result);
        Gson g=new Gson();
        MessageNoRead response=g.fromJson(result,MessageNoRead.class);
        return response;

    }

    //一条信息置为已读
    public static MessageNoRead updatemsgstatus(final String msgid, final String markread){
        final List<NameValuePair> param=new ArrayList<NameValuePair>();
        param.add(new NameValuePair() {
            @Override
            public String getName() {
                return "userid";
            }

            @Override
            public String getValue() {
                return "reg_"+ Const.loginInfo.userCode;
            }
        });
        param.add(new NameValuePair() {
            @Override
            public String getName() {
                return "appkey";
            }

            @Override
            public String getValue() {
                return Const.jpushkey;
            }
        });
        param.add(new NameValuePair() {
            @Override
            public String getName() {
                return "msglist";
            }

            @Override
            public String getValue() {
                return msgid;
            }
        });
        param.add(new NameValuePair() {
            @Override
            public String getName() {
                return "markread";
            }

            @Override
            public String getValue() {
                return markread;
            }
        });


        String result= RequestUtil.doPost(Const.jpushurl+"updatemsgstatus",param);
        LogMe.d("msg",result);
        Gson g=new Gson();
        MessageNoRead response=g.fromJson(result,MessageNoRead.class);
        return response;

    }
    //删除全部消息

    public static MessageNoRead deleteallmsg(final String total){//是否删除全部(0=>已读,1=>全部),默认不传0,删除已读消息
        final List<NameValuePair> param=new ArrayList<NameValuePair>();
        param.add(new NameValuePair() {
            @Override
            public String getName() {
                return "userid";
            }

            @Override
            public String getValue() {
                return "reg_"+ Const.loginInfo.userCode;
            }
        });
        param.add(new NameValuePair() {
            @Override
            public String getName() {
                return "appkey";
            }

            @Override
            public String getValue() {
                return Const.jpushkey;
            }
        });
        param.add(new NameValuePair() {
            @Override
            public String getName() {
                return "total";
            }

            @Override
            public String getValue() {
                return total;
            }
        });
        String result= RequestUtil.doPost(Const.jpushurl+"deleteallmsg",param);
        LogMe.d("msg",result);
        Gson g=new Gson();
        MessageNoRead response=g.fromJson(result,MessageNoRead.class);
        return response;

    }

    //删除一条消息

    public static MessageNoRead deletemsg(final String msgid){
        final List<NameValuePair> param=new ArrayList<NameValuePair>();
        param.add(new NameValuePair() {
            @Override
            public String getName() {
                return "userid";
            }

            @Override
            public String getValue() {
                return "reg_"+ Const.loginInfo.userCode;
            }
        });
        param.add(new NameValuePair() {
            @Override
            public String getName() {
                return "appkey";
            }

            @Override
            public String getValue() {
                return Const.jpushkey;
            }
        });
        param.add(new NameValuePair() {
            @Override
            public String getName() {
                return "msglist";
            }

            @Override
            public String getValue() {
                return msgid;
            }
        });

        String result= RequestUtil.doPost(Const.jpushurl+"deletemsg",param);
        LogMe.d("msg",result);
        Gson g=new Gson();
        MessageNoRead response=g.fromJson(result,MessageNoRead.class);
        return response;

    }

}
