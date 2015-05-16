package data;

public class Header {
	private String type;
	private String host;
	private int length;
	private String agent;

	public Header(String type, String host, int length, String agent) {
		this.type = type;
		this.host = host;
		this.length = length;
		this.agent = agent;
	}

	public Header() {
		this("application/json", "mototelematics.bitnamiapp.com", 0,
				"EventMachine HttpClient");
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}
}
