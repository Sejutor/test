package mycp.mysql.base;

import java.util.HashMap;

import mycp.mysql.base.DatabaseHelper;
import mycp.mysql.base.cpData.UserTableMetaData;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class TxtPcontentProviderBag extends ContentProvider {
	
	public static final UriMatcher uriMatcher;
	public static final int INCOMING_USER_COLLECTION = 1;
	public static final int INCOMING_USER_SINGLE = 2;
	private DatabaseHelper dh;
	
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(cpData.AUTHORIY, "/XMLresolution",
				INCOMING_USER_COLLECTION);
		uriMatcher.addURI(cpData.AUTHORIY, "/XMLresolution/#",
				INCOMING_USER_SINGLE);
	}
	
	public static HashMap<String,String> userProjectionMap;
	static
	{
		userProjectionMap = new HashMap<String,String>();
		userProjectionMap.put(UserTableMetaData._ID,UserTableMetaData._ID);
		userProjectionMap.put(UserTableMetaData.USER_NAME, UserTableMetaData.USER_NAME);
		userProjectionMap.put(UserTableMetaData.USER_SEX, UserTableMetaData.USER_SEX);
		userProjectionMap.put(UserTableMetaData.USER_JOBNUM, UserTableMetaData.USER_JOBNUM);
		userProjectionMap.put(UserTableMetaData.USER_PHONE, UserTableMetaData.USER_PHONE);
		userProjectionMap.put(UserTableMetaData.USER_TERR, UserTableMetaData.USER_TERR);
		
	}
	@Override
	public int delete(Uri uri, String where, String[] whereArgs) {
		// TODO Auto-generated method stub
		
		SQLiteDatabase db = dh.getWritableDatabase();
		int num = 0;
		switch(uriMatcher.match(uri)){
		case INCOMING_USER_COLLECTION:
			num = db.delete("XMLresolution", where, whereArgs);
			break;
		case INCOMING_USER_SINGLE:
			long id = ContentUris.parseId(uri);
			String whereClause = UserTableMetaData._ID+"="+id;
			
			if (where != null && !where.equals("")) {
				
				whereClause = whereClause + " and " +where;
			}
			num = db.delete("XMLresolution", whereClause, whereArgs);
			break;
			default:
				throw new IllegalArgumentException("未知Uri" + uri);
		}
		System.out.println("delete");
		getContext().getContentResolver().notifyChange(uri, null);
		return num;
	}

	//根据传入的URI，返回该URI所表示的数据类型
	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		System.out.println("getType");
		
		switch(uriMatcher.match(uri)){
		
		case INCOMING_USER_COLLECTION:
			return UserTableMetaData.CONTENT_TYPE;
		case INCOMING_USER_SINGLE:
			return UserTableMetaData.CONTENT_TYPE_ITEM;
		default:
			throw new IllegalArgumentException("Unknown URI" + uri);
		}
	}

	/*
	 * 该函数的返回值是一个Uri，这个Uri表示的是刚刚使用这个函数所插入的数据
	 * content://mars.cp.FirstContentProvider/users/1
	 */
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		
		System.out.println("ContentProviderBag： insert");
		SQLiteDatabase db = dh.getWritableDatabase();
		long rowId = db.insert(UserTableMetaData.TABLE_NAME, null, values);
		if(rowId > 0){
			Uri insertedUserUri = ContentUris.withAppendedId(UserTableMetaData.CONTENT_URI, rowId);
			//通知监听器，数据已经改变
			getContext().getContentResolver().notifyChange(insertedUserUri, null);
			return insertedUserUri;
		}
		throw new SQLException("Failed to insert row into" + uri);
	}

	//是一个回调方法，所以说在ContentProvider创建的时候执行 
	@Override
	public boolean onCreate() {
		//打开数据库  
		dh = new DatabaseHelper(getContext(),cpData.DATABASE_NAME);
		System.out.println("onCreate");
		return true;
		
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
			String sortOrder) {
		
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		switch(uriMatcher.match(uri)){
		case INCOMING_USER_COLLECTION:
			qb.setTables(UserTableMetaData.TABLE_NAME);
			qb.setProjectionMap(userProjectionMap);
			break;
		case INCOMING_USER_SINGLE:
			qb.setTables(UserTableMetaData.TABLE_NAME);
			qb.setProjectionMap(userProjectionMap);
			qb.appendWhere(UserTableMetaData._ID + "=" + uri.getPathSegments().get(1));
			break;
		}
		String orderBy;
		if(TextUtils.isEmpty(sortOrder)){
			orderBy = UserTableMetaData.DEFAULT_SORT_ORDER;
		}
		else{
			orderBy = sortOrder;
		}
		SQLiteDatabase db = dh.getWritableDatabase();
		Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);
		c.setNotificationUri(getContext().getContentResolver(), uri);
		System.out.println("query");
		return c;
	}

	@Override
	public int update(Uri uri, ContentValues valuses, String where, String[] whereArps) {
		// TODO Auto-generated method stub
		SQLiteDatabase ub = dh.getWritableDatabase();
		int num = 0;
		
		switch(uriMatcher.match(uri)){
		case INCOMING_USER_COLLECTION:
			num = ub.update("XMLresolution", valuses, where, whereArps);
			break;
		case INCOMING_USER_SINGLE:
			long id = ContentUris.parseId(uri);
			String whereClause = UserTableMetaData._ID+"="+id;
			if (where != null && !where.equals("")) {
				
				whereClause = whereClause + " and " +where;
			}
			num = ub.update("XMLresolution", valuses,whereClause, whereArps);
			break;
			default:
				throw new IllegalArgumentException("未知Uri" + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		System.out.println("update");
		return num;
	}

	
	
	
}