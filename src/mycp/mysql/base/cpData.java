package mycp.mysql.base;

import android.net.Uri;
import android.provider.BaseColumns;

public class cpData {
	
	public static final String AUTHORIY = "mycp.mysql.base.TxtPcontentProviderBag";
	//数据库名称
	public static final String DATABASE_NAME = "XMLreader.db";
	//数据库的版本
	public static final int DATABASE_VERSION = 1;
	//表名 
	public static final String USERS_TABLE_NAME = "XMLresolution";
	
	public static final class UserTableMetaData implements BaseColumns{
		//表名
		public static final String TABLE_NAME = "XMLresolution";
		//访问该ContentProvider的URI
		public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORIY + "/XMLresolution");
		//该ContentProvider所返回的数据类型的定义
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.contentproviderdata.XMLresolution";
		public static final String CONTENT_TYPE_ITEM = "vnd.android.cursor.item/vnd.contentproviderdata.XMLresolution";
		//列名
		
		public static final String USER_NAME = "name";
		public static final String USER_SEX = "sex";
		public static final String USER_JOBNUM = "job_number";
		public static final String USER_PHONE = "Phone_number";
		public static final String USER_TERR = "territoriality";
		//默认的排序方法
		public static final String DEFAULT_SORT_ORDER = "_id desc";
	}
	
}