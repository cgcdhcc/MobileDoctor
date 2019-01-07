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

import java.io.IOException;
import java.net.URLEncoder;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

/**
 * 请求服务器工具类
 *
 * @author sszvip@qq.com
 * @since 2013-7-2
 */
public class RequestUtil {


    public static String postRequest(String url, final String json) {
        Log.d("msg", url);
        Log.d("msg", json);
        try {

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .post(new RequestBody() {
                        @Override
                        public MediaType contentType() {
                            return MediaType.parse("application/json");
                        }

                        @Override
                        public void writeTo(BufferedSink sink) throws IOException {
                            byte[] str = json.getBytes();
                            sink.write(str, 0, str.length);
                        }
                    })
                    .addHeader("Connection", "close")
                    .build();
            Response response = client.newCall(request).execute();
            if (response.code() == 200) {
                String retData = response.body().string();
                return retData;
            } else {
                Log.d("msg", response.message());
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("msg", "异常信息" + e.getMessage());
        }
        return "";
    }

    public static String getRequestWithHead(final String url, final boolean isSingleton) throws Exception {
        Log.d("msg", url);
        try {
            Log.d("msg", Const.DeviceId);
            String Authorization = new String(Base64.encode((Const.appId + ":" + Const.appKey + ":" + Const.DeviceId).getBytes(), Base64.DEFAULT));//
            Log.d("msg", Authorization);
            BrandInfo info = new BrandInfo();
            info.brand_name = Const.brand_name;
            info.brand_type = Const.brand_type;
            Gson g = new Gson();
            OkHttpClient client = new OkHttpClient();
            HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
            urlBuilder.addQueryParameter("device_info", URLEncoder.encode(g.toJson(info), "utf-8"));
            Request request = new Request.Builder().addHeader("Authorization", Authorization.replaceAll("\r|\n", ""))
                    .url(urlBuilder.build())
                    .get()
                    .build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            throw e;
        }

    }


}
