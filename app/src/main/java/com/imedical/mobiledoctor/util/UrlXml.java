package com.imedical.mobiledoctor.util;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * ClassName:UrlXml
 * Function: TODO ADD FUNCTION Reason: TODO ADD REASON
 * 工具类
 *
 * @author 焦昆
 * @see
 * @since Ver 1.1
 */
public class UrlXml {


    // 获取网络数据
    public static String writeTxtFromUrl(String uriTxt, String path) {
        FileOutputStream fos = null;
        String back = "";
        Log.d("str >>>>>>>>>>>>>9", "----------uriTxt---------" + uriTxt);
        byte[] geByte = null;
        try {
            geByte = getImageFromURL(uriTxt);
            if (geByte != null) {
                fos = new FileOutputStream(path);
                fos.write(geByte);
                Log.d("MyHandler", "write over ......");
                back = "ok";
            } else {
                back = "false";
            }
        } catch (OutOfMemoryError err) {
            back = "false";
            Log.e("MyHandler", "write over ......" + err.getMessage());
        } catch (Exception e) {
            back = "false";
            Log.e("MyHandler", "write over ......" + e.getMessage());
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                fos = null;
            }
        }

        return back;
    }

    public static String writeTxt(byte[] geByte, String path) {
        FileOutputStream fos = null;
        try {

            if (geByte != null) {
                fos = new FileOutputStream(path);
                fos.write(geByte);
                Log.d("MyHandler", "write over ......");
            }
        } catch (OutOfMemoryError err) {
            geByte = null;
            Log.e("MyHandler", "write over ......" + err.getMessage());
        } catch (Exception e) {
            geByte = null;
            Log.e("MyHandler", "write over ......" + e.getMessage());
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                fos = null;
            }
        }
        if (geByte == null) {
            return null;
        }
        return "OK";
    }

    public static byte[] getImageFromURL(String urlPath) {
        byte[] data = null;
        InputStream is = null;
        HttpURLConnection conn = null;
        try {
            URL url = new URL(urlPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            // conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(60000);
            is = conn.getInputStream();
            if (conn.getResponseCode() == 200) {
                data = readInputStream(is);
            }
        } catch (MalformedURLException e) {

            Log.e("str >>>>>>>>>>>>>9", "----------uriPic---------" + e.getMessage());
        } catch (IOException e) {
            Log.e("str >>>>>>>>>>>>>9", "----------uriPic---------" + e.getMessage());
        } finally {

            try {
                if (is != null) {
                    is.close();
                    is = null;
                    conn.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    public static byte[] readInputStream(InputStream is) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length = -1;
        try {
            while ((length = is.read(buffer)) != -1) {
                baos.write(buffer, 0, length);
            }
            baos.flush();
        } catch (IOException e) {

            Log.e("str >>>>>>>>>>>>>9", "----------uriPic---------" + e.getMessage());
        }
        byte[] data = baos.toByteArray();
        try {
            is.close();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static boolean fileIsExists(String path) {

        try {
            File f = new File(path);


            if (!f.exists()) {
                return false;
            } else {

                FileInputStream fis = null;
                fis = new FileInputStream(f);
                long s = fis.available();
                Log.d("---------", "f.Size() ............" + s);
                if (s == 1642) {
                    return false;
                }
            }

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static InputStream readfile(String path) {
        FileInputStream fis = null;
        try {
            File f = new File(path);


            if (!f.exists()) {
                return null;
            } else {

                fis = new FileInputStream(f);

            }

        } catch (Exception e) {
            return null;
        }
        return fis;
    }


    //日期
    public static String getNowDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");//"yyyyMMdd"
        String dateString = formatter.format(currentTime);
        // ParsePosition pos = new ParsePosition(8);
        // Date currentTime_2 = formatter.parse(dateString, pos);
        return dateString;
    }

    public static Date getNowDateF(String dateString) {
        if ("".equals(dateString)) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");//"yyyyMMddHHmm"
        // ParsePosition pos = new ParsePosition(8);
        Date currentTime_2 = null;
        try {
            currentTime_2 = formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return currentTime_2;
    }

    public static String getNowDate(String format) {
        String dateString = null;
        try {
            Date currentTime = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat(format);//"yyyyMMdd"
            dateString = formatter.format(currentTime);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(">>>>>>dateString>>>>", "dateString: " + dateString);
        }
        return dateString;
    }

    public static String getNowDate_0(String forma) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");//"yyyyMMdd"
        String dateString = formatter.format(currentTime);
        // ParsePosition pos = new ParsePosition(8);
        // Date currentTime_2 = formatter.parse(dateString, pos);
        return dateString;
    }

    public static Date getNowDateF(String dateString, String forma) {
        if ("".equals(dateString)) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(forma);//"yyyyMMddHHmm"
        // ParsePosition pos = new ParsePosition(8);
        Date currentTime_2 = null;
        try {
            currentTime_2 = formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return currentTime_2;
    }

    public static List<String> readFiles(String path) {
        List picFiles = new ArrayList();
        // 构建文件对象
        File dir = new File(path);
        // 得到改文件夹下所有文件
        File[] files = dir.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                // 过滤所有后缀为.jpg的文件
                if (fileName.lastIndexOf(".") > 0 && fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).equals("jpg") || fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).equals(".gif")) {
                    picFiles.add(files[i].getPath());
                }
            }
        }
        return picFiles;
    }

    public static File zip(String zipFileName, String inputFile) throws Exception {

        File f = new File(inputFile);
        zip(zipFileName, f);
        return f;
    }

    public static void zip(String zipFileName, File inputFile) throws Exception {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
        zip(out, inputFile, "");
        Log.d("uploadFile", "************** >>> zip done");
        out.close();
    }

    public static void zip(ZipOutputStream out, File f, String base) throws Exception {
        Log.d("uploadFile", "************** >>> Zipping " + f.getName());
        if (f.isDirectory()) {
            File[] fl = f.listFiles();
            out.putNextEntry(new ZipEntry(base + "/"));
            base = base.length() == 0 ? "" : base + "/";
            for (int i = 0; i < fl.length; i++) {
                zip(out, fl[i], base + fl[i].getName());
            }
        } else {
            out.putNextEntry(new ZipEntry(base + "/" + f.getName()));
            FileInputStream in = new FileInputStream(f);
            int b;
            while ((b = in.read()) != -1)
                out.write(b);
            in.close();
        }
    }
}
