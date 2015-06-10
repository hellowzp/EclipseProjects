/**
 * ���ܣ������࣬���ڲ�������ʱ����
 * ���ڣ�2012-12-20
 */

package musicClient.player;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.swing.JOptionPane;

public class PlayerWork {
	public Player audioPlayer = null;//Player ����
	private URL url;		//���������ҵ��ĵ�ַ
	private File filelist;  // ý�岥���ļ�
	private String lists[]; // ý�岥���б�
	

	public void choiceFile(String path) {
		//�õ��ļ����ڵ�λ��
		filelist = new File(path);
	}

	public void PlayerWorking(URL url) throws Exception {
		//�ļ���·��
		this.url = url;
	}

	//�õ�������
	public Player getPlay() {
		return this.audioPlayer;
	}
	
	//File�ĸ�ʽΪ��
	public void PlayerWorking(String fileAddress) {
		File file = new File(fileAddress);// �����ļ���·��
		System.out.println(fileAddress);
		try {
			
			this.url = file.toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	//��ʼ
	public void playerStart() {
		try {
			//�ҵ����ŵ�ַ����ʼ����
			audioPlayer = Manager.createPlayer(new MediaLocator(url));
			System.out.println("�õ��� url: "+url);
			audioPlayer.start();

		} catch (IOException e) {
			System.out.println(e);
			//e.printStackTrace();
		} catch (NoPlayerException e) {
			System.out.println(e);
			JOptionPane.showMessageDialog(null, "û���ҵ�������");
			this.playerStop();
		}
		System.out.println("��ʼ����:" + url.getFile());
	}
	
	//��ͣ����������
	public void playerPause(){
		try {
			audioPlayer.getMediaTime();
			audioPlayer.getDuration();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("������ͣ������");
	
	}
	
	//ֹͣ
	public void playerStop() {//ֹͣ����
		try {
			audioPlayer.stop();
			audioPlayer.close();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("������ϡ�����");
	}

	public Vector getFileList() {//��Ӳ����б�
		lists = filelist.list(new Folder());
		Vector vlist = new Vector();
		for (int i = 0; i < lists.length; i++)
			vlist.add(lists[i]);
		return vlist;
	}
}

class Folder implements FilenameFilter {//�ļ����͹���
	public boolean accept(File dir, String name) {
		if( name.endsWith(".mp3")||name.endsWith(".avi") || name.endsWith(".mpeg")||name.endsWith(".wav"))
			return true;
		else return false;
	}
}

