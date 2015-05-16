package be.kuleuven.wang;

import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class GameFrame extends JFrame{

	public GameFrame() {
		System.out.println("Loading...");
		setTitle("BallGame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setDefaultLookAndFeelDecorated(true);		
		setSize(500,580);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		System.out.println("Frame loaded, waiting to load contentPane..");
		Container contentPane=getContentPane();
		contentPane.add(new GamePane());		
		System.out.println("ContentPane loaded!");		
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
	         public void run() {
	        	 new GameFrame();  // Let the constructor do the job
	         }
	      });
	   }
	}


