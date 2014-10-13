
package com.example.tasktxt_01;


import android.app.Activity;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.content.Intent;

import android.widget.TextView;
import android.widget.Button;
import user.base.UserInfo;


public class EditActivity extends Activity {

	private String name,sex,jobnum,phone,terr;
	private TextView userTextView = null;
	private TextView sexTextView = null;
	private TextView jobnumTextView = null;
	private TextView phonenumTextView = null;
	private TextView terrTextView = null;
	private Button deleButton = null;
	private Button editButton = null;
	private Button callButton = null;
	private Button smsButton = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
			
			Intent intent = getIntent();
			UserInfo userInfo = (UserInfo)intent.getSerializableExtra("edituserinfo");
			
			name = userInfo.getname();
			sex = userInfo.getsex();
			jobnum = userInfo.getjobnum();
			phone = userInfo.getphonenum();
			terr = userInfo.getterr();
			//内容显示
			userTextView = (TextView)findViewById(R.id.user);
			userTextView.setText(name);
			sexTextView = (TextView)findViewById(R.id.sextext);
			sexTextView.setText(sex);
			jobnumTextView = (TextView)findViewById(R.id.jobnum);
			jobnumTextView.setText(jobnum);
			phonenumTextView = (TextView)findViewById(R.id.phonenum);
			phonenumTextView.setText(phone);
			terrTextView = (TextView)findViewById(R.id.terrtext);
			terrTextView.setText(terr);
			//监听器绑定
			smsButton = (Button)findViewById(R.id.sms);
			smsButton.setOnClickListener(new smsButtonListener());
			callButton = (Button)findViewById(R.id.call);
			callButton.setOnClickListener(new callButtonListener());
			
			//删除绑定
			deleButton = (Button)findViewById(R.id.dele);
			deleButton.setOnClickListener(new deleButtonListener());
			
			editButton = (Button)findViewById(R.id.edit);
			editButton.setOnClickListener(new editButtonListener());
			
		}
	}

    class smsButtonListener implements OnClickListener{
    	//生成该类的对象，并将其注册到控件上。如果该控件被用户按下，就会执行onClick方法 
		@Override
		public void onClick(View v) {
			Uri uri = Uri.parse("smsto:"+phone);
			try {
			Intent smsIntent = new Intent(Intent.ACTION_SENDTO,uri);  
			//smsIntent.putExtra("sms_body", "The SMS text");   
			startActivity(smsIntent);
			}catch(Exception e){
					System.out.println(e.toString());
					e.printStackTrace();
				}

		}
    	
    }
    class callButtonListener implements OnClickListener{
    	//生成该类的对象，并将其注册到控件上。如果该控件被用户按下，就会执行onClick方法 
		@Override
		public void onClick(View v) {
			Uri uri = Uri.parse("tel:"+phone);
			Intent phoneIntent = new Intent(Intent.ACTION_CALL, uri);
			//Intent phoneIntent = new Intent(Intent.ACTION_CALL, uri);
			startActivity(phoneIntent);

		}
    	
    }
    class deleButtonListener implements OnClickListener{
    	//生成该类的对象，并将其注册到控件上。如果该控件被用户按下，就会执行onClick方法 
		@Override
		public void onClick(View v) {
			
			
			//删除操作
			int i = getContentResolver().delete(mycp.mysql.base.cpData.UserTableMetaData.CONTENT_URI, "name = ?",new String[]{name});
		
			Intent intent = new Intent();
			intent.setClass(EditActivity.this,MainActivity.class);
			EditActivity.this.startActivity(intent);
//			finish();
		}
    	
    }
    
    class editButtonListener implements OnClickListener{
    	//生成该类的对象，并将其注册到控件上。如果该控件被用户按下，就会执行onClick方法 
		@Override
		public void onClick(View v) {
			
			UserInfo tempInfo = new UserInfo();
			tempInfo.setname(name);
			tempInfo.setsex(sex);
			tempInfo.setjobnum(jobnum);
			tempInfo.setphonenum(phone);
			tempInfo.setterr(terr);
			Intent tempIntent = new Intent();
			tempIntent.putExtra("userinfo", tempInfo);
			tempIntent.setClass(EditActivity.this,EditTextActivity.class);
			EditActivity.this.startActivity(tempIntent);
		}
    	
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_edit, container,
					false);
			return rootView;
		}
	}

}
