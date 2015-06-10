package cn.edu.nwsuaf.videoCaptureServer;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jvnet.substance.skin.SubstanceFieldOfWheatLookAndFeel;
import org.jvnet.substance.skin.SubstanceGreenMagicLookAndFeel;
import org.jvnet.substance.skin.SubstanceOfficeBlue2007LookAndFeel;

/**
 * 服务器端入口程序
 * @author Qzhong
 *
 */
public class RunServer {

	
	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		SubstanceOfficeBlue2007LookAndFeel  so= new SubstanceOfficeBlue2007LookAndFeel();
		SubstanceGreenMagicLookAndFeel so2 = new SubstanceGreenMagicLookAndFeel();
        try {
			UIManager.setLookAndFeel(so2);
		} catch (UnsupportedLookAndFeelException e) {
		} 
	    new Server();

	}

}
