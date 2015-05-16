package netUtil.wang;

import java.util.Date;
import javax.swing.JOptionPane;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;

public class FBUtil {

	public static void post(String msg) {
	    FacebookClient facebookClient = FBUtil.getClient();	    
	    FacebookType publishMessageResponse =
	        facebookClient.publish("me/feed", FacebookType.class,
	          Parameter.with("message", msg));
	    System.out.println("Published message ID: " + publishMessageResponse.getId());	
	    JOptionPane.showMessageDialog(null, "The message has been post to your Facebook timeline. Thanks for your play!");
	  }
	
	public static void createEvent() {
		FacebookClient facebookClient = FBUtil.getClient();
		Date tomorrow = new Date(System.currentTimeMillis() + 1000L * 60L * 60L * 24L);
		Date twoDaysFromNow = new Date(System.currentTimeMillis() + 1000L * 60L * 60L * 48L);

		FacebookType publishEventResponse = facebookClient.publish("me/events",
				FacebookType.class, Parameter.with("name", "FaceBall Game"),
				Parameter.with("start_time", tomorrow),
				Parameter.with("end_time", twoDaysFromNow));

		System.out.println("Published event ID: " + publishEventResponse.getId());
	    JOptionPane.showMessageDialog(null, 
	    	"A FaceBall Game event has been published, you can invite your friend to play it now. Thanks for your play!");
	}

	  public static FacebookClient getClient() {
	    return new DefaultFacebookClient(
	      "CAAGlGgD0wr8BANRAXI0QGAe0umqW1IOfHwXLsWJztk03U" +
	    "jfNiRqIbuWgA3t0Wic4fWXXim93tSIZAnRgH8vGjh7Ru4J9mFyudb53L9680VN" +
	    "ceB9eZB8TjBgKzxrHUGXMHckVDY2oSrBGdnG01S03EZCbneUIyOd6uQXQyEwXjQzW0DglQLwfhzEM3nIytcZD");
	  }

}
