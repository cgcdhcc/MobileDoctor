/*
 * Copyright 2010 Renren, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.imedical.mobiledoctor.api;


import android.util.Base64;
import android.util.Log;
import com.google.gson.Gson;
import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.api.platform.BrandInfo;
import com.imedical.mobiledoctor.util.HttpClientManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class RequestUtil {

    public static String getRequestWithHead(final String url,final boolean isSingleton) throws Exception {
        Log.d("msg",url);
        try{
            String Authorization="Basic "+  new String(Base64.encode((Const.key+":"+ Const.DeviceId).getBytes(),Base64.DEFAULT));//
            Log.d("msg",Authorization.replaceAll("\r|\n", ""));
            BrandInfo info=new BrandInfo();
            info.brand_name= Const.brand_name;
            info.brand_type= Const.brand_type;
            Gson g = new Gson();
            OkHttpClient client = new OkHttpClient();
            HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
            urlBuilder.addQueryParameter("device_info",URLEncoder.encode(g.toJson(info),"utf-8"));
            Log.d("msg",URLEncoder.encode(g.toJson(info),"utf-8"));
            Request request = new Request.Builder().addHeader("Authorization",Authorization.replaceAll("\r|\n", ""))
                    .url(urlBuilder.build())
                    .get()
                    .build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            throw e;
        }

    }

    public static String getRequest(final String url,final boolean isSingleton) throws Exception {

        String json = null;
        HttpClient client = HttpClientManager.getHttpClient(isSingleton);
        HttpEntity resEntity = null ;
        HttpGet get = new HttpGet(url);
        try {
            //long t1 = System.currentTimeMillis();
            HttpResponse response = client.execute(get,new BasicHttpContext());
            resEntity = response.getEntity();
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {// 200
                //取出回应字串
                json = EntityUtils.toString(resEntity);
            }
        } catch (Exception e) {
            get.abort();
            throw e;
        } finally {

            if (resEntity != null) {
                try {
                    resEntity.consumeContent();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            client.getConnectionManager().closeExpiredConnections();
        }
        return json;
    }


    public static String postRequest(final String url, final StringEntity params, final boolean isSingleton) throws Exception {
        String json = null;
        HttpClient client = HttpClientManager.getHttpClient(isSingleton);
        HttpEntity resEntity = null ;
        HttpPost post = new HttpPost(url);
        try {
            //long t1 = System.currentTimeMillis();

            if (params != null) {
                params.setContentEncoding(HTTP.UTF_8);
                params.setContentType("application/x-www-form-urlencoded");
                post.setEntity(params);
            }

            HttpResponse response = client.execute(post,new BasicHttpContext());
            resEntity = response.getEntity();
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {// 200
                // 取出回应字串
                json = EntityUtils.toString(resEntity);
            }
        } catch (Exception e) {
            if (post != null){
                post.abort();
            }
            throw e;
        } finally {

            if (resEntity != null) {
                try {
                    resEntity.consumeContent();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            client.getConnectionManager().closeExpiredConnections();
        }
        return json;
    }// end method submitForm

    public static String postForm(String url, List<NameValuePair> params,
                                  boolean isSingleton) throws Exception {
        String json = null;
        HttpClient client = HttpClientManager.getHttpClient(isSingleton);
        HttpEntity resEntity = null ;
        HttpPost post = new HttpPost(url);
        try {

            if (params != null) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
                post.setEntity(entity);
            }
            HttpResponse response = client.execute(post,new BasicHttpContext());
            resEntity = response.getEntity();
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {// 200
                // 取出回应字串
                json = EntityUtils.toString(resEntity);
            }
        } catch (Exception e) {
            if (post != null){
                post.abort();//终止请求
            }
            throw e;
        } finally {

            if (resEntity != null) {
                try {
                    resEntity.consumeContent();//释放资源

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            client.getConnectionManager().closeExpiredConnections();
            ///client.getConnectionManager().shutdown();
        }
        return json;

    }


}
