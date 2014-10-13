package com.example.tasktxt_01;




import user.base.UserInfo;
import android.app.Activity;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

import android.widget.EditText;
import android.widget.Button;

import android.database.Cursor;

public class EditTextActivity extends Activity {
	
	private String name,sex,jobnum,phonenum,terr;

	private EditText nameEditText = null;
	private EditText sexEditText = null;
	private EditText jobEditText = null;
	private EditText phoneEditText = null;
	private EditText terrEditText = null;
	private	ContentValues values=null;
	
	private int userflag = 0;
	
	private Button saveButton = null;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_text);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		nameEditText = (EditText)findViewById(R.id.user2);
		sexEditText = (EditText)findViewById(R.id.sexedit);
		jobEditText = (EditText)findViewById(R.id.jobedit);
		phoneEditText = (EditText)findViewById(R.id.phoneedit);
		terrEditText = (EditText)findViewById(R.id.terredit);
		Intent intent = getIntent();
		UserInfo userInfo = (UserInfo)intent.getSerializableExtra("userinfo");
		if(userInfo != null){
			userflag = 1;
			name = userInfo.getname();
			sex = userInfo.getsex();
			jobnum = userInfo.getjobnum();
			phonenum = userInfo.getphonenum();
			terr = userInfo.getterr();
			
			
			nameEditText.setText(name);
			sexEditText.setText(sex);
			jobEditText.setText(jobnum);
			phoneEditText.setText(phonenum);
			terrEditText.setText(terr);
			
		}
		saveButton = (Button)findViewById(R.id.save);
		saveButton.setOnClickListener(new saveButtonListener());
		
		
	}

    class saveButtonListener implements OnClickListener{
    	//生成该类的对象，并将其注册到控件上。如果该控件被用户按下，就会执行onClick方法 
		@Override
		public void onClick(View v) {
			
			System.out.println("save!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			String tempname = name;
			name = nameEditText.getText().toString();
			sex = sexEditText.getText().toString();
			jobnum = jobEditText.getText().toString();
			phonenum = phoneEditText.getText().toString();
			terr = terrEditText.getText().toString();
			
			values = new ContentValues();
			values.put(mycp.mysql.base.cpData.UserTableMetaData.USER_NAME, name);
			values.put(mycp.mysql.base.cpData.UserTableMetaData.USER_SEX, sex);
			values.put(mycp.mysql.base.cpData.UserTableMetaData.USER_JOBNUM, jobnum);
			values.put(mycp.mysql.base.cpData.UserTableMetaData.USER_PHONE, phonenum);
			values.put(mycp.mysql.base.cpData.UserTableMetaData.USER_TERR, terr);
			
			if (userflag == 1) {
				//来自修改界面
				int i = getContentResolver().update(
						mycp.mysql.base.cpData.UserTableMetaData.CONTENT_URI,
						values, "name = ?", new String[] { tempname });
				System.out.println("update user" + i + name);
			} else {
				
				System.out.println("insert user" + name);
				//来自添加界面
				Cursor c = getContentResolver().query(
						mycp.mysql.base.cpData.UserTableMetaData.CONTENT_URI,
						new String[] { "_id", "name" }, "name = ?",
						new String[] { name }, null);
				c.moveToFirst();
				if (c == null || c.moveToFirst() == false) {
					// 没有该姓名的时候，插入数据库
					Uri tempuri = getContentResolver()
							.insert(mycp.mysql.base.cpData.UserTableMetaData.CONTENT_URI,
									values);
					System.out.println("Intert uri--->" + tempuri.toString());
				} else {
					//如果数据库有，更新。
					int i = getContentResolver()
							.update(mycp.mysql.base.cpData.UserTableMetaData.CONTENT_URI,
									values, "name = ?", new String[] { name });
					System.out.println("update record" + i);
				}
			}
			
			Intent intent = new Intent();
			intent.setClass(EditTextActivity.this,MainActivity.class);
			EditTextActivity.this.startActivity(intent);
			

		}
    	
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_text, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_edit_text,
					container, false);
			return rootView;
		}
	}

}
