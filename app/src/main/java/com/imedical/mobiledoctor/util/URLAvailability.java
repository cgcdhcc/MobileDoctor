package com.imedical.mobiledoctor.util;

import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * 项目名称： xxxxxxxxxx* <p>
 * 功能模块名称：
 * </p><p>
 * 文件名称为：URLAvailability.java
 * </p><p>
 * 文件功能简述：
 *
 * @author ssz
 * @version v1.0
 * @time
 * @copyright xxxxxxxxxxxxx
 */
@SuppressWarnings("unused")
public class URLAvailability {

    private static URL urlStr;


    /**
     * 功能描述 : 检测当前网络是否断开 或 URL是否可连接,
     * 如果网络没断开,最多连接网络 5 次, 如果 5 次都不成功说明该地址不存在或视为无效地址.
     *
     * @param url 指定URL网络地址
     * @return void
     */
    public static synchronized boolean isConnected(String url) {
        boolean success = false;
        if (url == null || url.length() <= 0) {
            return false;
        }

        int counts = 0;

        while (counts < 2) {
            try {
                urlStr = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlStr.openConnection();
                int state = connection.getResponseCode();
                if (state == 200) {
                    success = true;
                }
                break;
            } catch (UnknownHostException ex) {
                success = false;
                Log.d(">>>>>>>>>>", "网络连接状态已断开,请检查网络连接设备");
                success = isConnected(url);//再次连接
            } catch (Exception ex) {
                success = false;
                continue;
            } finally {
                counts++;
            }
        }//end while

        return success;
    }
}
