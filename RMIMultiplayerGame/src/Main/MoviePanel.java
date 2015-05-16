package Main;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;



/**
 * Deze klasse verzorgt de moviepanel voor het aanmaken en onderhouden van de inleidingsvideo
 * 
 * @author Seppe Van Hees & Stijn Van Crombrugge
 */


public class MoviePanel extends JFXPanel {
	
	
	private static final long serialVersionUID = 1L;
	private MediaPlayer player;
	private Button skip;
	private JFXPanel fxPanel;
	
	
	public MoviePanel()
	{
	}
	
	
	public JFXPanel initiate(){
	
		fxPanel= new JFXPanel();
		fxPanel.setSize(800,500);
	    Platform.runLater(new Runnable() {
	          public void run() {
	              initFX(fxPanel);
	            }
	        });
	    return fxPanel;
	}

	    private void initFX(JFXPanel fxPanel) {
	    	
	    	Media media = new Media("file:///C:/Users/Stijn/Documents/GroepT3ejaar/Softwaredevelopment/workspace/SpelTest1/Resources/Movie/OldFilmCountdown.mp4");
			player = new MediaPlayer(media);
			skip = makeButton();
	        Scene scene = initScene();
	        fxPanel.setScene(scene);
	        fxPanel.setVisible(true);
	        player.play();
	        
	        
	     // make an action at the end of the movie
			player.setOnEndOfMedia(new Runnable() {
				public void run() {
					   actOnEnd();
					  }
					});
			

			skip.setOnAction(new EventHandler<ActionEvent>() {
		    	public void handle(ActionEvent actionEvent) {
		    			actOnEnd();
		    		}
		    		});
	    }

	    
	    
	// Het aanmaken van de scene voor de movieplayer
	  private Scene initScene(){
	     
		  Group  root  =  new  Group();
		  MediaView view = new MediaView(player);
	      root.getChildren().add(view);
	      root.getChildren().add(skip);
	      Scene scene  =  new  Scene(root);
	      return (scene);
	    }

	// Het aanmaken van de knop om de movie over te slaan
	public Button makeButton(){
	
		final Button button = new Button("skip");
		button.setLayoutX(336);
		button.setLayoutY(325);
		button.setMinSize(100, 35);
		button.setVisible(true);
		return button;
	}
	
	// methode die de movieplayer doet verdwijnen op het einde van de movie of na een skip
	public void actOnEnd()
	{
		player.stop();
		fxPanel.setVisible(false);
		JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(fxPanel);
		topFrame.setSize(650,500);
	}

}
