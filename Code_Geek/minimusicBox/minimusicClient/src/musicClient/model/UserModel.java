/**
 * �û���Ϣ����
 * ���û���¼ʱ�������������Ϣ
 */
package musicClient.model;

public class UserModel {
	
	//�����û����ԣ��û��������룩
	public String userName;
	public String password;
	
	//���û���¼ʱ������Ϊ����ģʽ
	public String online;
	
	//һ��set��get����
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
