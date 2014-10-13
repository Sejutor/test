package com.example.tasktxt_01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class welcomeActivity extends Activity implements AnimationListener {

	private ImageView  imageView = null;   
    private Animation alphaAnimation = null;   
    
    
    @Override  
    protected void onCreate(Bundle savedInstanceState) {   
           
        super.onCreate(savedInstanceState);   
      //ȥ������
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //����ȫ��
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.welcome_main);
        
        imageView = (ImageView)findViewById(R.id.welcome_image_view);   
        alphaAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha);   
        alphaAnimation.setFillEnabled(true); //����Fill����   
        alphaAnimation.setFillAfter(true);  //���ö��������һ֡�Ǳ�����View����   
        imageView.setAnimation(alphaAnimation);   
        alphaAnimation.setAnimationListener(this);  //Ϊ�������ü���   
        
        new Handler().postDelayed(new Runnable() {
        	

        	@Override
        	public void run() {
        	// TODO Auto-generated method stub
        		
        	Intent intent=new Intent(welcomeActivity.this,MainActivity.class);
        	startActivityForResult(intent,10);
        	}
        }, 1000*3);
        	

        
        
    } 
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    // TODO Auto-generated method stub
    //Log.i("StartActivity+�����ݷ���", requestCode+"");
    	super.onActivityResult(requestCode, resultCode, data);
    	if(resultCode==20){
    		finish();
    	}
    }
    @Override  
    public void onAnimationStart(Animation animation) {   
           
    }   
       
    @Override  
    public void onAnimationEnd(Animation animation) {   
        //��������ʱ������ӭ���沢ת�������������   
        //Intent intent = new Intent(this, MainActivity.class);   
        //startActivity(intent);   
        //this.finish();   
    }   
       
    @Override  
    public void onAnimationRepeat(Animation animation) {   
           
    }   
       
    @Override  
    public boolean onKeyDown(int keyCode, KeyEvent event) {   
        //�ڻ�ӭ��������BACK��   
        if(keyCode==KeyEvent.KEYCODE_BACK) {   
            return false;   
        }   
        return false;   
    }     
}
