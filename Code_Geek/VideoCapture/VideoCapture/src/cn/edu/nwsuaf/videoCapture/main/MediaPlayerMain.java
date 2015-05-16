package cn.edu.nwsuaf.videoCapture.main;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import org.jvnet.substance.skin.SubstanceChallengerDeepLookAndFeel;

import cn.edu.nwsuaf.videoCapture.mediaPlayer.MediaPlayer;

/**
 * ²¥·ÅÆ÷Ö÷³ÌÐò
 * @author Qzhong
 *
 */
public class MediaPlayerMain {

	public static void main(String[] args){
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
        try {      
               UIManager.setLookAndFeel(new SubstanceChallengerDeepLookAndFeel());     
           } catch (Exception e1) {    
               e1.printStackTrace();   
               System.out.println("Substance Raven Graphite failed to initialize");      
           }
		new MediaPlayer();
		
	}
}
