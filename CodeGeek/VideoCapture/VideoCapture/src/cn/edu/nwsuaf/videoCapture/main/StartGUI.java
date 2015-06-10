package cn.edu.nwsuaf.videoCapture.main;

import javax.swing.UIManager;

import org.jvnet.substance.SubstanceButtonUI;
import org.jvnet.substance.button.StandardButtonShaper;
import org.jvnet.substance.skin.SubstanceFieldOfWheatLookAndFeel;
import org.jvnet.substance.skin.SubstanceGreenMagicLookAndFeel;
import org.jvnet.substance.skin.SubstanceOfficeBlue2007LookAndFeel;

import cn.edu.nwsuaf.videoCapture.client.VideoCaptureClientGUI;

/**
 * 系统启动模块
 * @author Qzhong
 *
 */
public class StartGUI implements Runnable {
	

	@Override
	public void run() {
		System.out.println("系统正在启动，请稍候…………");
		VideoCaptureClientGUI.setAppearance(true);
		try {   
//			SubstanceLookAndFeel sla = new SubstanceLookAndFeel();
			SubstanceButtonUI sbu= new SubstanceButtonUI();
			
			SubstanceOfficeBlue2007LookAndFeel.setCurrentButtonShaper(new StandardButtonShaper());
//			sla.setCurrentTitlePainter(new ArcHeaderPainter());
			
			SubstanceOfficeBlue2007LookAndFeel  so= new SubstanceOfficeBlue2007LookAndFeel();
			SubstanceFieldOfWheatLookAndFeel so2 = new SubstanceFieldOfWheatLookAndFeel();
            UIManager.setLookAndFeel(new SubstanceGreenMagicLookAndFeel()); 
           
        } catch (Exception e1) {    
            e1.printStackTrace();   
            System.out.println("Substance Raven Graphite failed to initialize");      
        }  
		VideoCaptureClientGUI VCC=new VideoCaptureClientGUI();
		
			
		
	}

}
