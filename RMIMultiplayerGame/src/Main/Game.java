package Main;

import java.awt.BorderLayout;
import java.net.URL;
import javafx.embed.swing.JFXPanel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Deze main klasse zorgt voor het opstarten van het spel en de inleidingsvideo
 * 
 * @param args
 * 
 * @author Seppe Van Hees & Stijn Van Crombrugge
 */


public class Game {
	
	public static void main(String[] args) {
		
		JFrame.setDefaultLookAndFeelDecorated(false);
		JFrame window = new JFrame("versie 1.3");
		URL imgURL = Game.class.getResource("/Backgrounds/Icon.png");
		window.setIconImage(new ImageIcon(imgURL).getImage());
		
		
		final JPanel managerPanel = new JPanel();
		managerPanel.setLayout(new BorderLayout(0, 0));
		managerPanel.add(new GamePanel(),BorderLayout.CENTER);
		MoviePanel moviePanel = new MoviePanel();
		JFXPanel movieJPanel = moviePanel.initiate();
		managerPanel.add(movieJPanel,BorderLayout.NORTH);
		movieJPanel.setVisible(true);

		
		
		window.getContentPane().add(managerPanel);
		window.setSize(700,390);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setVisible(true);
		
				
	}
	
}
