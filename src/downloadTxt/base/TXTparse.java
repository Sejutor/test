package downloadTxt.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.util.EncodingUtils;

import user.base.UserInfo;


public class TXTparse {
	
	private List<UserInfo> UserInfos = null;
	private UserInfo userInfo = null;
	//����
	public int downloadTXT(String urlStr, String path, String fileName) {
		HttpDownLoader httpDownloader = new HttpDownLoader();
		int result = httpDownloader.downFile(urlStr, path, fileName);
		return result;
	}
	
	public List<UserInfo> parse (String path, String fileName) throws IOException {
	
		String res = "";
		
		UserInfos = new ArrayList<UserInfo>();
		
		FileUtils tfileUtils = new FileUtils();
		FileInputStream tfile = tfileUtils.openFSD(path, fileName);
		int length = tfile.available();
		//System.out.println("ntest size"+length);				
		byte[] buffer = new byte[length];			
		tfile.read(buffer);
        tfileUtils.closeFSD(tfile);
        res =  EncodingUtils.getString(buffer, "Unicode");
        //�ҵ���Ч��
        int endIndex;
        if ((endIndex = res.indexOf("\0"))!=-1)
        {
        	System.out.println("index:"+endIndex);
        }
        res = res.substring(0,endIndex);
        //�ִ�
        String[] split = res.split("\n");
        //System.out.println(res.length()+"-"+split.length);
        for(int j=0;j<split.length;j++){
        	String temp = new String(split[j]);
        	//�ҳ��ǿ���
        	if(temp.length()>2){
        		//���ʹ��"."��"|"��"^"���ַ����ָ���ʱ��Ҫд��("\\^")�ĸ�ʽ
        		userInfo = new UserInfo();
        		String[] tsplit = temp.split("\\|");
        		//�ִ�
        		for(int i=0;i<tsplit.length;i++){
        			//System.out.println(j+"��"+i+":"+tsplit[i]);
        			String ttemp = new String(tsplit[i]);
        			switch (i) {
        			case 0: 
        				userInfo.setname(ttemp);
        				break;
        			case 1:
        				userInfo.setsex(ttemp);
        				break;
        			case 2:
        				userInfo.setphonenum(ttemp);
        				break;
        			case 3:
        				userInfo.setjobnum(ttemp);
        				break;
        			case 4:
        				userInfo.setterr(ttemp);
        				break;
        			default :
        				break;
        			}	
        		}
        		UserInfos.add(userInfo);
        	}
        }
		
		return UserInfos;
	}
}