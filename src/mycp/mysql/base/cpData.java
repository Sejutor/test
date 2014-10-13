package mycp.mysql.base;

import android.net.Uri;
import android.provider.BaseColumns;

public class cpData {
	
	public static final String AUTHORIY = "mycp.mysql.base.TxtPcontentProviderBag";
	//���ݿ�����
	public static final String DATABASE_NAME = "XMLreader.db";
	//���ݿ�İ汾
	public static final int DATABASE_VERSION = 1;
	//���� 
	public static final String USERS_TABLE_NAME = "XMLresolution";
	
	public static final class UserTableMetaData implements BaseColumns{
		//����
		public static final String TABLE_NAME = "XMLresolution";
		//���ʸ�ContentProvider��URI
		public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORIY + "/XMLresolution");
		//��ContentProvider�����ص��������͵Ķ���
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.contentproviderdata.XMLresolution";
		public static final String CONTENT_TYPE_ITEM = "vnd.android.cursor.item/vnd.contentproviderdata.XMLresolution";
		//����
		
		public static final String USER_NAME = "name";
		public static final String USER_SEX = "sex";
		public static final String USER_JOBNUM = "job_number";
		public static final String USER_PHONE = "Phone_number";
		public static final String USER_TERR = "territoriality";
		//Ĭ�ϵ����򷽷�
		public static final String DEFAULT_SORT_ORDER = "_id desc";
	}
	
}