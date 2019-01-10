package com.imedical.mobiledoctor.XMLservice;

import android.util.Log;

import com.google.gson.Gson;
import com.imedical.mobiledoctor.AppConfig;
import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.api.RequestUtil;
import com.imedical.mobiledoctor.api.WsApiUtil;
import com.imedical.mobiledoctor.entity.AntBodyPart;
import com.imedical.mobiledoctor.entity.AntIndication;
import com.imedical.mobiledoctor.entity.AntReason;
import com.imedical.mobiledoctor.entity.AntReasonData;
import com.imedical.mobiledoctor.entity.BaseBean;
import com.imedical.mobiledoctor.entity.BaseBeanMy;
import com.imedical.mobiledoctor.entity.BaseInfo;
import com.imedical.mobiledoctor.entity.ConsultDoc;
import com.imedical.mobiledoctor.entity.ConsultLoc;
import com.imedical.mobiledoctor.entity.FormFile;
import com.imedical.mobiledoctor.entity.Instruction;
import com.imedical.mobiledoctor.entity.MainReturn;
import com.imedical.mobiledoctor.entity.OperaTime;
import com.imedical.mobiledoctor.entity.Operation;
import com.imedical.mobiledoctor.entity.OrderItem;
import com.imedical.mobiledoctor.entity.OrderSuscept;
import com.imedical.mobiledoctor.entity.ParamAnti;
import com.imedical.mobiledoctor.entity.PopMessage;
import com.imedical.mobiledoctor.entity.PostFile4Json;
import com.imedical.mobiledoctor.util.LogMe;
import com.imedical.mobiledoctor.util.PropertyUtil;
import java.util.Map;


/**
 * 咨询医生相关的业务处理
 * @author sszvip@qq.com
 * @since  2014-7-4
 */
public class AskExpertService {
	private static AskExpertService askExpertService;

	private AskExpertService() {
	}

	public static synchronized AskExpertService getInstance() {
		if (askExpertService == null) {
			askExpertService = new AskExpertService();
		}
		return askExpertService;
	}

    /**
     * @Title: delFileIdNew
     * @Description: TODO(新的删除附件接口)
     * @author lqz
     * @date 2016-4-23 下午7:14:48
     * @param @param id
     * @param @return    设定文件
     * @return BaseBeanMy    返回类型
     * @throws
     */
    public BaseBeanMy delFileByPath(String path) {
        BaseBeanMy b4j;
        path=path.split("appupload")[1];
        try {
            String url = AppConfig.deleteFile+path;
            Log.d("msg",url);
            String json = RequestUtil.postRequest(url,null,false);
            Gson gson = new Gson();
            b4j = gson.fromJson(json, BaseBeanMy.class);
            if(b4j==null){
                b4j = new BaseBeanMy();
                b4j.setSuccess(false);
                b4j.msg = "文件删除失败!";
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("msg", e.getMessage());
            b4j = new BaseBeanMy();
            b4j.setSuccess(false);
            b4j.msg = "文件删除失败!";
        }
        return b4j;
    }
    /**
     * @Title: postFileNew
     * @Description: TODO(新的上传附件的接口)
     * @author lqz
     * @date 2016-3-23 下午5:34:13
     * @param @param params
     * @param @param arr
     * @param @param bubusinessType
     * @param @return    设定文件
     * @return File4Json    返回类型
     * @throws
     */
    public PostFile4Json postFileNew(Map<String, String> params, FormFile[] arr, String bubusinessType, String businessId) {
        PostFile4Json b4j=null;
//        String url = AppConfig.upload;
//        url = String.format(url,bubusinessType,businessId);
//        Log.d("msg",url);
//        try {
//            String json = RequestUtil.postFile(url, params, arr);
//            LogMe.d("=================="+json);
//            Gson g = new Gson();
//            b4j = g.fromJson(json, PostFile4Json.class);
//        } catch (Exception e) {
//            e.printStackTrace();
//            b4j = new PostFile4Json();
//            b4j.msg = "文件上传失败!";
//        }
        return b4j;
    }


}
