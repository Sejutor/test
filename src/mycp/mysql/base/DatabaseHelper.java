package mycp.mysql.base;

import mycp.mysql.base.cpData;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

//DatabaseHelper��Ϊһ������SQLite�������࣬�ṩ��������Ĺ��ܣ�
//��һ��getReadableDatabase(),getWritableDatabase()���Ի��SQLiteDatabse����ͨ���ö�����Զ����ݿ���в���
//�ڶ����ṩ��onCreate()��onUpgrade()�����ص����������������ڴ������������ݿ�ʱ�������Լ��Ĳ���

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final int VERSION = 1;

	// ��SQLiteOepnHelper�����൱�У������иù��캯��
	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		// ����ͨ��super���ø��൱�еĹ��캯��
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
		//��һ�β�������һ�����ݿ�
	public DatabaseHelper(Context context, String name) {
		this(context, name, VERSION);
	}

	public DatabaseHelper(Context context, String name, int version) {
		this(context, name, null, version);
	}

	// �ú������ڵ�һ�δ������ݿ��ʱ��ִ��,ʵ�������ڵ�һ�εõ�SQLiteDatabse�����ʱ�򣬲Ż�����������
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		System.out.println("create a Database");
		// execSQL��������ִ��SQL���
		db.execSQL("create table " + cpData.USERS_TABLE_NAME
				+ "(" + cpData.UserTableMetaData._ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ cpData.UserTableMetaData.USER_NAME
				+ " varchar(20),"
				+ cpData.UserTableMetaData.USER_SEX 
				+ " varchar(20),"
				+ cpData.UserTableMetaData.USER_JOBNUM
				+ " varchar(20),"
				+ cpData.UserTableMetaData.USER_PHONE
				+ " varchar(20),"
				+ cpData.UserTableMetaData.USER_TERR
				+ " varchar(20)"
				+ ")");
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		System.out.println("update a Database");
	}

}