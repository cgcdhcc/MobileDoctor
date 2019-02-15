package com.imedical.face.service;

import com.imedical.face.bean.AddFaceRequest;
import com.imedical.face.bean.AddFaceResponse;
import com.imedical.face.bean.AddGroupidsRequest;
import com.imedical.face.bean.AddGroupidsResponse;
import com.imedical.face.bean.AddPersonResponse;
import com.imedical.face.bean.DectIdentifyRequest;
import com.imedical.face.bean.DetectRequest;
import com.imedical.face.bean.DetectResponse;
import com.imedical.face.bean.IdentifyRequest;
import com.imedical.face.bean.IdentifyResponse;
import com.imedical.face.bean.NewPersonRequest;
import com.google.gson.Gson;
import com.imedical.mobiledoctor.api.WsApiUtil;

import java.io.File;


public class HttpFaceService {


    public static Gson gson = new Gson();

    public static AddPersonResponse newperson(File file, String person_id, String person_name) {//tencentcloud.face.detect
        AddPersonResponse response = null;
        NewPersonRequest request = new NewPersonRequest(person_id,person_name);
        String result = WsApiUtil.loadHttpFormDataObject("tencentcloud.face.newperson", gson.toJson(request), gson.toJson(request.body), file,"image");
        response = gson.fromJson(result, AddPersonResponse.class);
        return response;
    }
    public static AddGroupidsResponse addgroupids(String person_id) {//tencentcloud.face.detect
        AddGroupidsResponse response = null;
        AddGroupidsRequest request = new AddGroupidsRequest(person_id);
        String result = WsApiUtil.loadHttpFormDataObject("tencentcloud.face.addgroupids", gson.toJson(request), gson.toJson(request.body), null,"");
        response = gson.fromJson(result, AddGroupidsResponse.class);
        return response;
    }

    public static AddFaceResponse addface(File file, String person_id) {//tencentcloud.face.detect
        AddFaceResponse response = null;
        AddFaceRequest request = new AddFaceRequest(person_id);
        String result = WsApiUtil.loadHttpFormDataObject("tencentcloud.face.addface", gson.toJson(request), gson.toJson(request.body), file,"images[0]");
        response = gson.fromJson(result, AddFaceResponse.class);
        return response;
    }

    public static DetectResponse detect(File file, String mode) {//tencentcloud.face.detect
        DetectResponse response = null;
        DetectRequest request = new DetectRequest(mode);
        String result = WsApiUtil.loadHttpFormDataObject("tencentcloud.face.detect", gson.toJson(request), gson.toJson(request.body), file,"image");
        response = gson.fromJson(result, DetectResponse.class);
        return response;
    }
    public static IdentifyResponse identify(String group_id, File file) {
        IdentifyResponse response = null;

            IdentifyRequest  request=new IdentifyRequest(group_id);
            String result = WsApiUtil.loadHttpFormDataObject("tencentcloud.face.identify", gson.toJson(request), gson.toJson(request.body), file,"image");
            response = gson.fromJson(result, IdentifyResponse.class);
            return response;
    }

    public static String identify(String group_id, String mode, File file) {
        DectIdentifyRequest  request=new DectIdentifyRequest(group_id,mode);
        String result = WsApiUtil.loadHttpFormDataObject("tencentcloud.face.detect.identify", gson.toJson(request), gson.toJson(request.body), file,"image");
        return result;
    }

//	public static LivedetectpictureResponse livedetectpicture(String filepath){
//		LivedetectpictureResponse response=null;
//		try{
//			String host_url="http://recognition.image.myqcloud.com/face/livedetectpicture";
//			Map<String,String> headers=new HashMap<String,String>();
//			headers.put("Host", "recognition.image.myqcloud.com");
//			headers.put("Authorization", Sign.appSign(appId, secretId, secretKey, bucketName, 3));
//			HashMap<String, String> body=new HashMap<String, String>();
//			body.put("appid", appId+"");
//			File file=new File(filepath);
//			String result=RequestUtil.uploadFile(host_url, headers, "image", file, body);
//			response=gson.fromJson(result, LivedetectpictureResponse.class);
//			return response;
//		}catch (Exception e){
//			e.printStackTrace();
//		}
//		return response;
//	}
//


}
