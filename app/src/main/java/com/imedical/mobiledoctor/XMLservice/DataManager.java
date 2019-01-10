package com.imedical.mobiledoctor.XMLservice;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.imedical.mobiledoctor.AppConfig;


/**
 * 
 * created by sszvip@qq.com
 *
 */
public class DataManager {
	
//	/**
//	 * 
//	 * @param s
//	 * @param e
//	 * @return
//	 */
//	public static List<ItemCommon> queryAccountByDate(String s,String e){
//		List l= new ArrayList();
////		String sql = " select AccountCode,CompanyName,(InAmount-OutAmount) money from Account where 1=1 ";
////		if(s!=null){
////			sql+= " and  CreateDate >= '"+s+"' ";
////		}
////		if(e!=null){
////			sql+= " and  CreateDate <= '"+e+"' ";
////		}
//	    String view  = getViewSql(s,e);
//	    String sql = "select code,(inm-outm) money,CompanyName from "+view ;
//		sql+= " order by money desc ";
//		
//		Log.d("#####sql",sql);
//		SQLiteDatabase db = openDb();
//		Cursor cursor = db.rawQuery(sql,null);
//		///cursor.moveToFirst();
//		while (cursor.moveToNext()) {
//			String code = cursor.getString(cursor.getColumnIndex("code"));
//			if("".equals(code)){
//				code= "无";
//			}
//        	ItemCommon i = new ItemCommon(
//        			code,
//        			cursor.getString(cursor.getColumnIndex("CompanyName")),
//        			cursor.getDouble(cursor.getColumnIndex("money"))+"元"
//        	);
//    	    l.add(i);
//    	}
//		
//		if (cursor != null) {
//			cursor.close();
//			cursor = null;
//		}
//		db.close();
//		Log.d("#####result size",l.size()+"");
//        return l;		
//	}
	
	
	
	private static SQLiteDatabase db;

	public static SQLiteDatabase openDb() {
		String databaseFilename = AppConfig.PIRVATE_FOLDER + "/" + AppConfig.DB_FILE;
		try {
			// 获得dictionary.db文件的绝对路径
			// 如果/sdcard/dictionary目录中存在，创建这个目录
			db = SQLiteDatabase.openOrCreateDatabase(databaseFilename, null);
			if(db==null){
				Log.d("###open db####", "db is opened !");
				db = SQLiteDatabase.openOrCreateDatabase(databaseFilename, null);
			}
			
			//LogUtil.writeFileToSD("db:"+db);
		} catch (Exception e) {
			e.printStackTrace();
			//LogUtil.writeFileToSD(e.getMessage());
			db = SQLiteDatabase.openOrCreateDatabase(databaseFilename, null);
		}
		
		return db;
	}
	
	public static void closeDb() {
		db.close();
	}
	
	
	
}
