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

package com.imedical.mobiledoctor.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 请求服务器工具类
 *
 * @author sszvip@qq.com
 * @since 2013-7-2
 */
public class DownloadUtil {


    public static void loadImage(ImageView imageView, String url, int loadingImage, int emptyImage, int failImage) {


        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(loadingImage)
                .showImageForEmptyUri(emptyImage)
                .showImageOnFail(failImage)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        ImageLoader.getInstance().displayImage(url, imageView, options);
    }

    public static void loadImage(ImageView imageView, String url, DisplayImageOptions options) {
        ImageLoader.getInstance().displayImage(url, imageView, options);
    }

    public static void downFileAsyn(final Activity ctx, final String upgradeUrl,
                                    final String savedPath, final MyCallback<Boolean> callback) {
        final ProgressDialog xh_pDialog = new ProgressDialog(ctx);
        // 设置进度条风格，风格为圆形，旋转的
        xh_pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // 设置ProgressDialog 标题
        xh_pDialog.setTitle("提示信息");
        // 设置ProgressDialog提示信息
        xh_pDialog.setMessage("正在打开...");
        // 设置ProgressDialog标题图标
        // xh_pDialog.setIcon(R.drawable.img2);
        // 设置ProgressDialog 的进度条是否不明确 false 就是不设置为不明确
        xh_pDialog.setIndeterminate(false);
        // 设置ProgressDialog 进度条进度
        // xh_pDialog.setProgress(100);
        // 设置ProgressDialog 是否可以按退回键取消
        xh_pDialog.setCancelable(true);
        // 让ProgressDialog显示
        xh_pDialog.show();

        new Thread() {
            public void run() {

                boolean downloadSuccess = true;
                FileOutputStream fileOutputStream = null;
                try {
                    Looper.prepare();
                    HttpClient client = new DefaultHttpClient();
                    HttpGet get = new HttpGet(upgradeUrl);

                    File f = new File(savedPath);
                    if (!f.exists()) {
                        f.createNewFile();
                    }

                    HttpResponse response = client.execute(get);
                    HttpEntity entity = response.getEntity();
                    // 设置进度条的总长度
                    Long length = entity.getContentLength();
                    xh_pDialog.setMax(length.intValue());
                    //
                    InputStream is = entity.getContent();
                    fileOutputStream = null;

                    if (is != null && length > 0) {

                        fileOutputStream = new FileOutputStream(f);

                        byte[] buf = new byte[1024];
                        int ch = -1;
                        int count = 0;
                        while ((ch = is.read(buf)) != -1) {

                            if (xh_pDialog.isShowing()) {
                                fileOutputStream.write(buf, 0, ch);
                                // 设置进度条的值
                                count += ch;
                                xh_pDialog.setProgress(count);
                            } else {
                                downloadSuccess = false;
                                break;// 取消下载
                            }
                        }

                    } else {
                        callback.onCallback(false);
                    }

                    if (downloadSuccess && fileOutputStream != null) {
                        xh_pDialog.dismiss();
                        fileOutputStream.flush();
                        fileOutputStream.close();
                        callback.onCallback(true);// 成功
                    }
                    Looper.loop();
                } catch (FileNotFoundException e) {
                    xh_pDialog.dismiss();
                    e.printStackTrace();
                    callback.onCallback(false);
                    xh_pDialog.dismiss();
                } catch (Exception e) {
                    xh_pDialog.dismiss();
                    e.printStackTrace();
                    callback.onCallback(false);
                } finally {
                    try {
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.start();

    }

    public static void loadImageFile(File localFile, String urlStr) {
        Log.d("msg", "下载信息localFile：" + localFile.getPath() + " urlStr： " + urlStr);
        try {
            URL url = new URL(urlStr);
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            InputStream input = urlConn.getInputStream();
            FileOutputStream output = new FileOutputStream(localFile);
            byte buffer[] = new byte[4 * 1024];
            while ((input.read(buffer)) != -1) {
                output.write(buffer);
            }
            output.flush();
            input.close();
            Log.d("msg", "下载成功" + localFile.getPath() + "  " + urlStr);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("msg", "下载失败了" + e.getMessage());
        }

    }

    public static void loadMp3File(File localFile, String urlStr) {
        Log.d("msg", "下载信息localFile：" + localFile.getPath() + " urlStr： " + urlStr);
        try {
            localFile.createNewFile();
            URL url = new URL(urlStr);
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            InputStream input = urlConn.getInputStream();
            FileOutputStream output = new FileOutputStream(localFile);
            byte buffer[] = new byte[4 * 1024];
            int len = -1;
            while ((len = input.read(buffer)) != -1) {
                output.write(buffer, 0, len);
            }
            output.flush();
            input.close();
            Log.d("msg", "下载成功" + localFile.getPath() + "  " + urlStr);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("msg", "下载失败了" + e.getMessage());
        }

    }
}
