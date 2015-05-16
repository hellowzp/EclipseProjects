import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;
   
// To play sound using Clip, the process need to be alive.
// Hence, we use a Swing application.
// https://www3.ntu.edu.sg/home/ehchua/programming/java/J8c_PlayingSound.html
public class SoundClipTest extends JFrame {
   
   // Constructor
   public SoundClipTest() {
	  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  this.setTitle("Test Sound Clip");
	  this.setSize(300, 200);
	  this.setVisible(true);
   
	  try {
		 // Open an audio input stream.
		 
		 // from a wave File
        File soundFile = new File("D:/My Documents/My Music/Kugou/Sunny_day.wav");
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
         /*
		 // from a URL
         URL url = new URL("http://www.zzz.com/eatfood.wav");
         AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
		 */
         // can read from a disk file and also a file contained inside a JAR (used for distribution)
         // recommended
         //URL url = this.getClass().getClassLoader().getResource("D:\My Documents\My Music\Kugou\Sunny_day.wav");
         //AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
		 // Get a sound clip resource.
		 Clip clip = AudioSystem.getClip();
		 // Open audio clip and load samples from the audio input stream.
		 clip.open(audioIn);
		 clip.start();
	  } catch (UnsupportedAudioFileException e) {
		 e.printStackTrace();
	  } catch (IOException e) {
		 e.printStackTrace();
	  } catch (LineUnavailableException e) {
		 e.printStackTrace();
	  }
   }
   
   public static void main(String[] args) {
	  new SoundClipTest();
   }
}