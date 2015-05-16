import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		try {
			FileOutputStream fileStream;

			// create an API client instance
			Client client = new Client("South_North", "bdf20eaa38da2572b6f0de37f0db2afd");

			// convert a web page and save the PDF to a file
			fileStream = new FileOutputStream("qzone.pdf");
			client.convertURI("http://user.qzone.qq.com/1548276723/", fileStream);
			fileStream.close();

			// convert an HTML string and store the PDF into a byte array
//			ByteArrayOutputStream memStream = new ByteArrayOutputStream();
//			String html = "<head></head><body>My HTML Layout</body>";
//			client.convertHtml(html, memStream);
//
//			// convert an HTML file
//			fileStream = new FileOutputStream("file.pdf");
//			client.convertFile("/path/to/local/file.html", fileStream);
//			fileStream.close();

			// retrieve the number of tokens in your account
			System.out.println("Tokens left: " + client.numTokens());
		} catch (PdfcrowdError why) {
			System.err.println(why.getMessage());
		} catch (IOException exc) {
			// handle the exception
		}
	}
}
