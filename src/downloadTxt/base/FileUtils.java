package downloadTxt.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.os.Environment;

public class FileUtils {
	private String SDPATH;

	public String getSDPATH() {
		return SDPATH;
	}
	public FileUtils() {
		//�õ���ǰ�ⲿ�洢�豸��Ŀ¼
		// /SDCARD
		SDPATH = Environment.getExternalStorageDirectory() + "/";
	}
	/**
	 * ��SD���ϴ����ļ�
	 * 
	 * @throws IOException
	 */
	public File creatSDFile(String fileName) throws IOException {
		File file = new File(SDPATH + fileName);
		file.createNewFile();
		return file;
	}
	
	/**
	 * ��SD���ϴ���Ŀ¼
	 * 
	 * @param dirName
	 */
	public File creatSDDir(String dirName) {
		File dir = new File(SDPATH + dirName);
		dir.mkdirs();
		return dir;
	}

	/**
	 * �ж�SD���ϵ��ļ����Ƿ����
	 */
	public boolean isFileExist(String fileName){
		File file = new File(SDPATH + fileName);
		return file.exists();
	}
	
	/**
	 * ��һ��InputStream���������д�뵽SD����
	 */
	public File write2SDFromInput(String path,String fileName,InputStream input){
		File file = null;
		OutputStream output = null;
		try{
			creatSDDir(path);
			file = creatSDFile(path + fileName);
			output = new FileOutputStream(file);
			byte buffer [] = new byte[4 * 1024];
			while((input.read(buffer)) != -1){
				output.write(buffer);
			}
			output.flush();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				output.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		return file;
	}
	//�����ܳ��쳣����
	public FileInputStream openFSD(String path,String fileName) throws IOException
	{
		String tempPath = SDPATH + path + fileName;
		File file = new File(tempPath);
		if(file.isDirectory())
		{
			System.out.println("File doesn't exit");
		}
		System.out.println("File"+file.length());	
		FileInputStream fin = new FileInputStream(file);
	
		return fin;
	}
	
	public void closeFSD(FileInputStream fin) throws IOException
	{
		fin.close();
	}
	

}