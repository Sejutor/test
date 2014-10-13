package user.base;

import java.io.Serializable;

public class UserInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String name,sex,terr;
	private String jobnum,phonenum;
	
	public UserInfo() {
		super();
	}
	
	//
	public void setname(String name) {
		this.name = name;
	}
	public String getname() {
		return name;
	}
	
	public void setsex(String sex) {
		this.sex = sex;
	}
	public String getsex() {
		return sex;
	}
	
	public void setjobnum(String jobnum) {
		this.jobnum = jobnum;
	}
	public String getjobnum() {
		return jobnum;
	}
	
	public void setphonenum(String phonenum) {
		this.phonenum = phonenum;
	}
	public String getphonenum() {
		return phonenum;
	}
	
	public void setterr(String terr) {
		this.terr = terr;
	}
	public String getterr() {
		return terr;
	}
	

}