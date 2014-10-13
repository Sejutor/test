package broad.service;


import txt.service.TxtTXT2Sql;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class TxtBootRecevier extends BroadcastReceiver {
	

	@Override
    public void onReceive(Context context, Intent intent) {
		
		System.out.println("Star BootRecevier: !!!!!!!!!!!!!!!!!!!!!!!");
		
		String txtstr = "http://172.20.200.136/test2.txt";
		String txtpath = "mdown/";
		String txtfilename = "test.txt";
		
		Intent tintent =new Intent();
        tintent.putExtra("strtxt", txtstr);
        tintent.putExtra("pahttxt", txtpath);
        tintent.putExtra("filenametxt", txtfilename);
		tintent.setClass(context, TxtTXT2Sql.class);
		
		context.startService(tintent);
		System.out.println("End BootRecevier: ????????????????????????");
	}
		
}