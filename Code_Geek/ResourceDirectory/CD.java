import java.io.File;
import java.io.IOException;
import java.net.URL;

public class CD {
	public static void main(String[] args) {
		System.out.println(System.getProperty("user.dir"));
		
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL url = classLoader.getResource("res/resource.text"); //"/res/resource.text" also works
		
		File file = new File(url.getPath()) ;
		System.out.println("Absolute path: " + file.getAbsolutePath());
		try {
			System.out.println("Canonical path: " + file.getCanonicalPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}