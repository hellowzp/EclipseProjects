/**
 * 用户信息的类
 * 当用户登录时向服务器发送消息
 */
package musicClient.model;

public class UserModel {
	
	//定义用户属性（用户名和密码）
	public String userName;
	public String password;
	
	//当用户登录时，设置为上线模式
	public String online;
	
	//一堆set和get方法
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOnline() {
		return online;
	}

	public void setOnline(String online) {
		this.online = online;
	}

	
	

}
