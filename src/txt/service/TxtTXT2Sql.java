package txt.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import user.base.UserInfo;
import android.net.Uri;

import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.Iterator;

import downloadTxt.base.TXTparse;

//import android.content.ContentResolver;
//import android.content.Context;

public class TxtTXT2Sql extends Service{
	
	private List<UserInfo> UserInfos = null;
	private UserInfo userInfo = null;
	private	ContentValues values=null;
	private	Uri uri = null;
	private Iterator iterator =null;
    
    
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		System.out.println("Service onBind");
		return null;
	}

	//当创建一个Servcie对象之后，会首先调用这个函数
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		System.out.println("Service onCreate");
	}
	

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stubo
		System.out.println("Service onDestory");
		super.onDestroy();
	}

	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		System.out.println("flags--->" + flags);
		System.out.println("startId--->" + startId);
		System.out.println("Service onStartCommand");
		//取得信息,要改!!!要解决！！！
		String txtstr = (String) intent.getSerializableExtra("strtxt");
		String txtpath = (String) intent.getSerializableExtra("pahttxt");
		String txtfilename = (String) intent.getSerializableExtra("filenametxt");
		DownloadThread downloadThread = new DownloadThread(txtstr,txtpath,txtfilename);
		//启动新线程
		Thread thread = new Thread(downloadThread);
		thread.start();
		return super.onStartCommand(intent, flags, startId);
		//return START_NOT_STICKY;
	}
	
	class DownloadThread implements Runnable{
		String txtStr;
		String txtPath;
		String txtFileName;
		public DownloadThread(String str,String path,String filename)
		{
			this.txtStr = str;
			this.txtPath = path;
			this.txtFileName= filename;
		}
		@Override
		public void run() {
			TXTparse myTXTparse = new TXTparse();
			int istxt = myTXTparse.downloadTXT(txtStr, txtPath, txtFileName);
			if(istxt==-1) {
				System.out.println("can not download txtfile!");
			}
			System.out.println("-txt");
				
			try {
				UserInfos = myTXTparse.parse(txtPath,txtFileName);
			} catch(Exception e){
				System.out.println(e.toString());
				e.printStackTrace();
			}
			System.out.println("txt2sqlsize:"+UserInfos.size());
				//插入数据库
			for (iterator = UserInfos.iterator(); iterator.hasNext();){
				System.out.println("iterator");
				values = new ContentValues();
				userInfo = (UserInfo)iterator.next();
				values.put(mycp.mysql.base.cpData.UserTableMetaData.USER_NAME, userInfo.getname());
				//System.out.println("Xml2sqlname:"+userInfo.getname());
				values.put(mycp.mysql.base.cpData.UserTableMetaData.USER_SEX, userInfo.getsex());
				values.put(mycp.mysql.base.cpData.UserTableMetaData.USER_JOBNUM, userInfo.getjobnum());
				values.put(mycp.mysql.base.cpData.UserTableMetaData.USER_PHONE, userInfo.getphonenum());
				values.put(mycp.mysql.base.cpData.UserTableMetaData.USER_TERR, userInfo.getterr());

				try {	
					Cursor c=getContentResolver().query(mycp.mysql.base.cpData.UserTableMetaData.CONTENT_URI,
							new String[]{"_id","name"},"name = ?", new String[]{userInfo.getname()}, null);
					c.moveToFirst();
					//System.out.println("XML2SQL:::" +c.getString(c.getColumnIndex("name")));
						if(c==null || c.moveToFirst()==false){	
								//插入数据库
							uri = getContentResolver()
									.insert(mycp.mysql.base.cpData.UserTableMetaData.CONTENT_URI,
									values);
							System.out.println("Intert uri--->" + uri.toString());
						}
						else{
							
							int i = getContentResolver()
								.update(mycp.mysql.base.cpData.UserTableMetaData.CONTENT_URI, 
										values, 
										"name = ?", new String[]{userInfo.getname()});
							System.out.println("update record" + i);
						}
						
					
					}catch(Exception e){
						System.out.println("insert sql: "+e.toString());
						e.printStackTrace();
				}
				
					
			}	
				
			
		}
	}


}