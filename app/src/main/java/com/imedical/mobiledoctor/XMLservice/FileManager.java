package com.imedical.mobiledoctor.XMLservice;

import java.io.File;
import java.io.FileFilter;

/**
 * 
 * created by sszvip@qq.com
 *dd
 */
public class FileManager {
	
	public static File[] loadRecordFiles(String path){
		File dir = new File(path);
		if(!dir.exists()){
			dir.mkdir();
		}
		File[] files = dir.listFiles(new FileFilter() {
            
            @Override
            public boolean accept(File pathname) {
                String filename = pathname.getPath();
                if (pathname.isDirectory())
                    return true;  
                if(filename.endsWith(".mp3"))  
                    return true;  
                else  
                    return false;
            }
        });  
		return files;
	}
	
	public static File[] loadImageFiles(String path){
		//String path = AppConfig.g_basePath+AppConfig.PIRVATE_FOLDER_PAINTCARD ;
		File dir = new File(path);
		if(!dir.exists()){
			dir.mkdir();
		}
		File[] files = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                String filename = pathname.getPath();
                if (pathname.isDirectory())
                    return true;  
                if(filename.endsWith(".png")||filename.endsWith(".jpg")||filename.endsWith(".JPEG"))  
                    return true;  
                else  
                    return false;
            }
        });  
		return files;
	}
	
}
