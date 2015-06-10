package cn.edu.nwsuaf.videoCapture.main;

import javax.swing.UIManager;

import org.jvnet.substance.SubstanceButtonUI;
import org.jvnet.substance.button.StandardButtonShaper;
import org.jvnet.substance.skin.SubstanceFieldOfWheatLookAndFeel;
import org.jvnet.substance.skin.SubstanceOfficeBlue2007LookAndFeel;

import cn.edu.nwsuaf.videoCapture.client.VideoCaptureClientGUI;
import cn.edu.nwsuaf.videoCapture.client.VideoCaptureClientGUI_2;

/**
 * ϵͳ����ģ��
 * @author Qzhong
 *
 */
public class StartGUI_2 implements Runnable {
	

	@Override
	public void run() {
		System.out.println("ϵͳ�������������Ժ򡭡�����");
		VideoCaptureClientGUI.setAppearance(true);
		try {   
//			SubstanceLookAndFeel sla = new SubstanceLookAndFeel();
			SubstanceButtonUI sbu= new SubstanceButtonUI();
			
			SubstanceOfficeBlue2007LookAndFeel.setCurrentButtonShaper(new StandardButtonShaper());
//			sla.setCurrentTitlePainter(new ArcHeaderPainter());
			
			SubstanceOfficeBlue2007LookAndFeel  so= new SubstanceOfficeBlue2007LookAndFeel();
			SubstanceFieldOfWheatLookAndFeel so2 = new SubstanceFieldOfWheatLookAndFeel();
            UIManager.setLookAndFeel(so); 
           
        } catch (Exception e1) {    
            e1.printStackTrace();   
            System.out.println("Substance Raven Graphite failed to initialize");      
        }  
		VideoCaptureClientGUI_2 VCC=new VideoCaptureClientGUI_2();
		
			
		
	}

}
