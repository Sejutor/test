package com.example.tasktxt_01;


import mycp.mysql.base.cpData.UserTableMetaData;

import txt.service.TxtTXT2Sql;
import user.base.UserInfo;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends ListActivity implements OnTouchListener,OnGestureListener,NavigationDrawerFragment.NavigationDrawerCallbacks {
	
	private List<UserInfo> userInfos = null;
	private UserInfo userInfo = null;
	private Button addButton = null;
    private List<HashMap<String, String>> list = null;
	private EditText searchText = null;
	private SimpleAdapter mSimpleAdapter;
	
	private static final int REQ_SYSTEM_SETTINGS = 0;
	private static final int FLING_MIN_DISTANCE = 200;
	private static final int FLING_MIN_VELOCITY = 200;
	
	private final CharSequence[] items = {"Call", "SMS", "Dele"};   
    //private AlertDialog.Builder builder = null;
	
	private GestureDetector gd;
	
	/**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setResult(20);
        setContentView(R.layout.activity_main);
        
        //Intent welIntent = new Intent(this,welcomeActivity.class);  
        //startActivity(welIntent);
        
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        
        
        addButton = (Button)findViewById(R.id.add);
		addButton.setOnClickListener(new addButtonListener());
		
		//searchText = (EditText)findViewById(R.id.entry);
		//searchText.setFocusable(true);   
		//searchText.setFocusableInTouchMode(true);   
		//searchText.requestFocus();  
		//searchText.addTextChangedListener(new myTextWatcher());
		
		
		//getListView().setOnItemLongClickListener(this);
		Uri uri = Uri.parse(mycp.mysql.base.cpData.UserTableMetaData.CONTENT_URI.toString());  
		this.getContentResolver().registerContentObserver(uri, true, new PersonObserver(new Handler())); 
		updateList();
		//ҳ�滬��,����4��ָ��
		gd = new GestureDetector( this,this );
        //???��ôȫ�ּ���
		//��Ӷ�Ӧ��������ҳ���л�
		ListView ll =  (ListView)findViewById(android.R.id.list);
		//FrameLayout ll = (FrameLayout) findViewById(R.id.container);
		ll.setOnTouchListener(this);
		ll.setLongClickable(true);
		
		//��������dialog
		getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){  
		      public boolean onItemLongClick(AdapterView parent, View view, int position,  
		           long id) {  
		        System.out.println("..........OnItemLongClickListener"+view.toString() + "position=" + position);               
		        //Toast.makeText(testList.this, "You OnItemLongClickListener click: " + position, Toast.LENGTH_SHORT).show();  
		        UserInfo tInfo = userInfos.get(position);
		        final String tphone = tInfo.getphonenum();
		        final String tname = tInfo.getname();
		        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this); 
		        builder.setTitle(tInfo.getname() + "\b\b\b\b\b\b" + tInfo.getphonenum());  
		        
		        builder.setItems(items, new DialogInterface.OnClickListener() {  
		            public void onClick(DialogInterface dialog, int item) {  
		                //Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();  
		            	switch (item) {
		            	case 0:
		            		Uri puri = Uri.parse("tel:"+tphone);
		        			Intent phoneIntent = new Intent(Intent.ACTION_CALL, puri);
		        			startActivity(phoneIntent);
		        			

		            		break;
		            	case 1:
		            		Uri suri = Uri.parse("smsto:"+tphone);
		            		Intent smsIntent = new Intent(Intent.ACTION_SENDTO,suri);  
		            		startActivity(smsIntent);
		            		break;
		            	case 2:
		            		getContentResolver().delete(mycp.mysql.base.cpData.UserTableMetaData.CONTENT_URI, "name = ?",new String[]{tname});
		            		break;
		            	default:break;
		            	}
		            }  
		        });  
		        builder.setNegativeButton("Cancel", null);
		        AlertDialog alert = builder.create();
		        alert.show();
		        return true;  
		       }  
		 });
	
    }
    
   
    
    /*----δ����viewpageʵ��activity��������Ϊ�������ϵ���activity���view����Ļ���-----*/
    /*----��������ĳ�����ֿؼ�ʵ�ֵģ������Ǹ�д����-----*/
    @Override  
    public boolean onTouch(View v, MotionEvent event) {  
        // TODO Auto-generated method stub  
    	//System.out.println("Gesture onTouch ");
    	boolean tt = gd.onTouchEvent(event);
    	System.out.println("Gesture onTouch " + tt);
    	return tt;
        //return gd.onTouchEvent(event);  
    }   
    
    @Override  
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,  
              float velocityY) {  
          // TODO Auto-generated method stub  
    	System.out.println("Gesture onFling ");
    	if(e1.getX() - e2.getX() > FLING_MIN_DISTANCE&&Math.abs(velocityX) > FLING_MIN_VELOCITY)  
        {  
    		//������һ��activity
            Intent intent = new Intent(this,ViewPSettings.class);  
            startActivity(intent);  
             //Toast.makeText(this, "��������", Toast.LENGTH_SHORT).show();   
  
        }  
        else if (e2.getX()-e1.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) >FLING_MIN_VELOCITY) {  
              
            //�л�Activity  
            //Intent intent = new Intent(WeatherActivity.this, IndexActivity.class);  
            //startActivity(intent);  
            Toast.makeText(this, "��������", Toast.LENGTH_SHORT).show();  
        }   
            
          return false;  
      }  
    @Override  
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)  
    {  
    	System.out.println("Gesture onScroll ");
        return false;  
    } 
    @Override  
    public boolean onDown(MotionEvent e)  
    {  
    	System.out.println("Gesture onDown ");
        return false;  
    }
    @Override  
    public void onLongPress(MotionEvent e)  
    {  
    	System.out.println("Gesture onLongPress ");
        return ; 
  
    }
    
    @Override  
    public void onShowPress(MotionEvent e)  
    {  
    	System.out.println("Gesture onShowPress ");
        return ;
    }  
  
    @Override  
    public boolean onSingleTapUp(MotionEvent e)  
    {  
    	System.out.println("onSingleTapUp ");
        return false;  
    }  

    /*-----------------------δ�ϲ�viewpageǰ�Ĵ���----------------------------*/
    //���ݿ���
    private class PersonObserver extends ContentObserver{  
    	  
        public PersonObserver(Handler handler) {  
            super(handler);  
        }  
        //��ContentProvier���ݷ����ı䣬�򴥷��ú���  
        @Override  
        public void onChange(boolean selfChange) {  
            super.onChange(selfChange);  
            updateList();
            
        }  
    } 
    
    //EditText���
  /*class myTextWatcher implements TextWatcher{
	  
	  @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
		  
		 System.out.println(searchText.getText().toString());
		 
		 String tname = searchText.getText().toString();
		 
		 Cursor ct=getContentResolver().query(mycp.mysql.base.cpData.UserTableMetaData.CONTENT_URI,
					new String[]{"_id","name"},"name = ?", new String[]{tname}, null);
		 ct.moveToFirst();
		 
		 if( ct != null)
		 {
			 
		 }
		 
 
      }
       
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count,
              int after) {
          // TODO Auto-generated method stub
           
      }
       
      @Override
      public void afterTextChanged(Editable s) {
          // TODO Auto-generated method stub
           
      }
	  
  }
  */
  
  //��дonResume��ˢ���б�
   protected void onResume() {
		super.onResume();
		updateList();
		
	
}
    	

  //�б�ˢ�·���
	protected void updateList() {
		
		try {
			userInfos = new ArrayList<UserInfo>();
			
			System.out.println("get begin");
			Cursor c= getContentResolver().query(mycp.mysql.base.cpData.UserTableMetaData.CONTENT_URI,
					null,null, null, null);
			if(c==null || c.moveToFirst()==false)
			{
				System.out.println("nothing");
			}
			else {
				do{
					userInfo = new UserInfo();
					System.out.println("get" + c.toString());
					userInfo.setname(c.getString(c.getColumnIndex(UserTableMetaData.USER_NAME)));
					userInfo.setsex(c.getString(c.getColumnIndex(UserTableMetaData.USER_SEX)));
					userInfo.setjobnum(c.getString(c.getColumnIndex(UserTableMetaData.USER_JOBNUM)));
					userInfo.setphonenum(c.getString(c.getColumnIndex(UserTableMetaData.USER_PHONE)));
					userInfo.setterr(c.getString(c.getColumnIndex(UserTableMetaData.USER_TERR)));
					userInfos.add(userInfo);
					System.out.println("name = " + userInfo.getname());
				}while(c.moveToNext());
			}
				
		} catch(Exception e){
			System.out.println(e.toString());
			e.printStackTrace();
		}
		System.out.println("get suucces "+userInfos.size());
			System.out.println("creat list uplist");
			list = new ArrayList<HashMap<String, String>>();
		for (Iterator iterator = userInfos.iterator(); iterator.hasNext();) {
			UserInfo userite = (UserInfo) iterator.next();
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("user_name", userite.getname());
			map.put("user_sex", userite.getsex());
			map.put("user_job", userite.getjobnum());
			map.put("user_phone", userite.getphonenum());
			map.put("user_terr", userite.getterr());

			list.add(map);
			System.out.println("map name = " + map.get("user_name"));
		}
		
		
		mSimpleAdapter = new SimpleAdapter(this, list,
				R.layout.userinfo_item, new String[] { "user_name", "user_sex","user_job","user_phone","user_terr"},
				new int[] { R.id.user_name, R.id.user_sex ,R.id.user_job,R.id.user_phone,R.id.user_terr});
		
		setListAdapter(mSimpleAdapter);
		mSimpleAdapter.notifyDataSetChanged();
		
		System.out.println("display");
		
	}
	
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }
	
    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                //mTitle = getString(R.string.title_section1);
            	mTitle = getString(R.string.app_name);
        		String txtstr = "http://172.20.200.136/test2.txt";
        		String txtpath = "mdown/";
        		String txtfilename = "test.txt";
                Intent intent = new Intent();
                intent.putExtra("strtxt", txtstr);
                intent.putExtra("strpaht", txtpath);
                intent.putExtra("strfilename", txtfilename);
        		intent.setClass(this, TxtTXT2Sql.class);
        		startService(intent);
        		
        		
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        //System.out.println("!!!!!!!!!!!!!"+mTitle.toString());
        actionBar.setTitle(mTitle);
    }
    
	//��Ӱ�ť��غ���
    class addButtonListener implements OnClickListener{
    	//���ɸ���Ķ��󣬲�����ע�ᵽ�ؼ��ϡ�����ÿؼ����û����£��ͻ�ִ��onClick���� 
		@Override
		public void onClick(View v) {
			
			Intent intent = new Intent();
			intent.setClass(MainActivity.this,EditTextActivity.class);
			MainActivity.this.startActivity(intent);
		}
    	
    }
    
    //�˵���
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
    	if (!mNavigationDrawerFragment.isDrawerOpen()) {
    		
    	//����û�򿪵�ʱ��
        getMenuInflater().inflate(R.menu.main, menu);//ԭ�е�
        restoreActionBar();
        return true;//ԭ�е�
    	}
    	//����򿪵�ʱ����ʾ������
    	return super.onCreateOptionsMenu(menu);
    }

    //�˵���ѡ�м�ط���
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_update) {
        	System.out.println("update");
        	String txtstr = "http://172.20.200.136/test2.txt";
    		String txtpath = "mdown/";
    		String txtfilename = "test.txt";
            Intent intent = new Intent();
            intent.putExtra("strtxt", txtstr);
            intent.putExtra("strpaht", txtpath);
            intent.putExtra("strfilename", txtfilename);
    		intent.setClass(this, TxtTXT2Sql.class);
    		// ����Service
    		startService(intent);	
        }
        
        if(id == R.id.action_settings){//ԭ�е�
        	//System.out.println("??????????????menu action_settings");
        	startActivityForResult(new Intent(this, ViewPSettings.class), REQ_SYSTEM_SETTINGS);
        	//System.out.println("!!!!!!!!!!!!!!menu action_settings");
        	//return true;//ԭ�е�
        }
        if(id == R.id.action_example)
        {
        	Intent intent = new Intent(MainActivity.this,ViewPSettings.class);  
            startActivity(intent);
            //���뵭��
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
        	return super.onOptionsItemSelected(item);
        
    }
    
    
    //�б�ĳ�ѡ�еļ�ط���

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		UserInfo tempInfo = userInfos.get(position);
		
		System.out.println("click: "+tempInfo.getname());
		// ����Intent����
		Intent tempIntent = new Intent();
		tempIntent.putExtra("edituserinfo", tempInfo);
		tempIntent.setClass(MainActivity.this, EditActivity.class);
		MainActivity.this.startActivity(tempIntent);
		super.onListItemClick(l, v, position, id);
	}
	
	//getListAdapter() ; 
	//getListView().setOnItemLongClickListener(this);//ע��
	
	
    /**
     * A placeholder fragment containing a simple view.
     */
	
	
    public static class PlaceholderFragment extends Fragment {
    	
    	private static final String ARG_SECTION_NUMBER = "section_number";
    	
    	public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }
    	//ԭ�е�
        public PlaceholderFragment() {
        }
        //ԭ�е�
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
        
        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }
    
}
