/**
 * 功能：播放类，用于播放音乐时调用
 * 日期：2012-12-20
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
	public Player audioPlayer = null;//Player 对象
	private URL url;		//播放器能找到的地址
	private File filelist;  // 媒体播放文件
	private String lists[]; // 媒体播放列表
	

	public void choiceFile(String path) {
		//得到文件所在的位置
		filelist = new File(path);
	}

	public void PlayerWorking(URL url) throws Exception {
		//文件的路径
		this.url = url;
	}

	//得到播放器
	public Player getPlay() {
		return this.audioPlayer;
	}
	
	//File的格式为：
	public void PlayerWorking(String fileAddress) {
		File file = new File(fileAddress);// 歌曲文件夹路径
		System.out.println(fileAddress);
		try {
			
			this.url = file.toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	//开始
	public void playerStart() {
		try {
			//找到播放地址，开始播放
			audioPlayer = Manager.createPlayer(new MediaLocator(url));
			System.out.println("得到的 url: "+url);
			audioPlayer.start();

		} catch (IOException e) {
			System.out.println(e);
			//e.printStackTrace();
		} catch (NoPlayerException e) {
			System.out.println(e);
			JOptionPane.showMessageDialog(null, "没有找到播放器");
			this.playerStop();
		}
		System.out.println("开始播放:" + url.getFile());
	}
	
	//暂停？？？？？
	public void playerPause(){
		try {
			audioPlayer.getMediaTime();
			audioPlayer.getDuration();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("播放暂停···");
	
	}
	
	//停止
	public void playerStop() {//停止播放
		try {
			audioPlayer.stop();
			audioPlayer.close();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("播放完毕···");
	}

	public Vector getFileList() {//添加播放列表
		lists = filelist.list(new Folder());
		Vector vlist = new Vector();
		for (int i = 0; i < lists.length; i++)
			vlist.add(lists[i]);
		return vlist;
	}
}

class Folder implements FilenameFilter {//文件类型过滤
	public boolean accept(File dir, String name) {
		if( name.endsWith(".mp3")||name.endsWith(".avi") || name.endsWith(".mpeg")||name.endsWith(".wav"))
			return true;
		else return false;
	}
}

