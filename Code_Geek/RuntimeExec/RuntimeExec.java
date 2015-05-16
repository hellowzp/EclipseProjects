//http://www.programcreek.com/2011/07/java-design-pattern-singleton/
//http://adnjavainterview.blogspot.in/2014/06/singleton-design-pattern-in-java-with.html
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class RuntimeExec {

	public RuntimeExec() { }
	
	public static void main(String[] arg) throws IOException {
		Process p = null;
		try {
			p = Runtime.getRuntime().exec(
					"C:/windows/system32/ping.exe programcreek.com");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//get process input stream and put it to buffered reader
		BufferedReader input = new BufferedReader(new InputStreamReader(
				p.getInputStream()));
		 
		String line;
		try {
			while ((line = input.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		input.close();
	}
}
