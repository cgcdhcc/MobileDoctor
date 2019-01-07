package com.imedical.mobiledoctor.util;

/**
 *
 */

import android.util.Log;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.net.URLEncoder;


public class GetShortUrlUtils {


    @SuppressWarnings("deprecation")
    public static String getShortUrl(String longUrl) {
        String appKey = "4039677146";
        longUrl = URLEncoder.encode(longUrl);
        String requesturl = "http://api.t.sina.com.cn/short_url/shorten.json?source=" + appKey + "&url_long=" + longUrl;
        StringBuilder stringBuilder = new StringBuilder();
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(requesturl);
        HttpResponse responseGet;
        try {
            responseGet = httpClient.execute(httpget);
            if (responseGet.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                stringBuilder.append(EntityUtils.toString(responseGet
                        .getEntity()));

                //@SuppressWarnings({ "unchecked" })
                //List<Map<String, Object>> urlList = JsonUtils.toObject(stringBuilder.toString(), List.class);

//				if(urlList!=null&&urlList.size()>0){
//					return urlList.get(0).get("url_short").toString();
//				}
                String str = stringBuilder.toString();
                str = str.substring(1, str.lastIndexOf("]"));
                Log.d("msg", str);
                ShortUrl su = (new Gson()).fromJson(str, ShortUrl.class);
                Log.d("msg", su.url_short);
                return su.url_short;
            } else {
            }
        } catch (Exception e) {
            Log.d("msg", "异常信息：" + e.getMessage());
        }
        return "www.jiankangle.com";
    }
}
