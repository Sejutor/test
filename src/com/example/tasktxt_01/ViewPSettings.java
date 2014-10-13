package com.example.tasktxt_01;


import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.util.Log;


//继承PreferenceActivity，并实现OnPreferenceChangeListener和OnPreferenceClickListener监听接口
public class ViewPSettings extends PreferenceActivity implements OnPreferenceClickListener,OnPreferenceChangeListener{

	//定义相关变量
	String updateSwitchKey;
	String updateFrequencyKey;
	CheckBoxPreference updateSwitchCheckPref;
	ListPreference updateFrequencyListPref;
	//private TimerTask task;
	//private final Timer timer = new Timer();
	
	//private GestureDetector gds;
	//private static final int sFLING_MIN_DISTANCE = 100;
	//private static final int sFLING_MIN_VELOCITY = 200;
    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //从xml文件中添加Preference项
        addPreferencesFromResource(R.xml.preferencesii);
        //获取各个Preference
        updateSwitchKey = getResources().getString(R.string.auto_update_switch_key);
        updateFrequencyKey = getResources().getString(R.string.auto_update_frequency_key);
        updateSwitchCheckPref = (CheckBoxPreference)findPreference(updateSwitchKey);
        updateFrequencyListPref = (ListPreference)findPreference(updateFrequencyKey);
        //为各个Preference注册监听接口    
        updateSwitchCheckPref.setOnPreferenceClickListener(this);
        updateFrequencyListPref.setOnPreferenceClickListener(this); 
        updateSwitchCheckPref.setOnPreferenceChangeListener(this);
        updateFrequencyListPref.setOnPreferenceChangeListener(this);
        
        /*final Handler handler = new Handler() {
        	@Override
        	public void handleMessage(Message msg) {
        	// TODO Auto-generated method stub
        	//要做的事情
        		//System.out.println("update!!!!!!!!!!!!");
        	super.handleMessage(msg);
        	}

        	};
        task = new TimerTask() {
        	@Override
        	public void run() {
        	// TODO Auto-generated method stub
        	Message message = new Message();
        	message.what = 1;
        	
			handler.sendMessage(message);
        	}
        	};
        	*/
              
        	
    }
 
    /*-------------未添加滑动页面-------------*/
	@Override
	public boolean onPreferenceClick(Preference preference) {
		// TODO Auto-generated method stub
		Log.v("SystemSetting", "preference is clicked");
		Log.v("Key_SystemSetting", preference.getKey());
		//判断是哪个Preference被点击了
		if(preference.getKey().equals(updateSwitchKey))
		{
			Log.v("SystemSetting", "checkbox preference is clicked");
		}
		else if(preference.getKey().equals(updateFrequencyKey))
		{
			Log.v("SystemSetting", "list preference is clicked");
		}
		else
		{
			return false;
		}
		return true;
	}
	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		// TODO Auto-generated method stub
		Log.v("SystemSetting", "preference is changed");
		Log.v("Key_SystemSetting", preference.getKey());
		//判断是哪个Preference改变了
		if(preference.getKey().equals(updateSwitchKey))
		{
			/*if(preference.getSummary().equals(R.string.auto_update_switch_summary_on)) {
				String temp = preference.getSharedPreferences().getString("auto_update_frequency_key", "");
				int period = Integer.valueOf(temp).intValue();
				timer.schedule(task, 2000, period*1000);
			}
			else
				timer.cancel();*/
			Log.v("SystemSetting", "checkbox preference is changed");
		}
		else if(preference.getKey().equals(updateFrequencyKey))
		{
			//String temp = preference.getSharedPreferences().getString("auto_update_frequency_key", "");
			//int period = Integer.valueOf(temp).intValue();
			//timer.schedule(task, 2000, period*1000);
		
			Log.v("SystemSetting", "list preference is changed");
		}
		else
		{
			//如果返回false表示不允许被改变
			return false;
		}
		//返回true表示允许改变
		return true;
	}
}
