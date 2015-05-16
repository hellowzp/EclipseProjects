package data;

public class Data {
	private Meta meta;
	private Payload pl;
	private final static int NULL = -1;
	
	public Data() {
		meta = new Meta();
		pl = new Payload();
	}
	
	
	public Meta getMeta() {
		return meta;
	}

	public Payload getPayload() {
		return pl;
	}


	private class Payload {
		private int id;
		private int thread_id;
		private int parent_id;
		private String type; //"message"
		private String channel; //com.mdi.dynamic_channel_configuration",
		private String sender;
		private String recipient;
		private String asset;
		private String payload;
		private String rcdAt;
		private String rcvAt;
		
		public Payload(int id, int thread_id, int parent_id, String type,
				String channel, String sender, String recipient, String asset,
				String payload, String rcdAt, String rcvAt) {
			this.id = id;
			this.thread_id = thread_id;
			this.parent_id = parent_id;
			this.type = type;
			this.channel = channel;
			this.sender = sender;
			this.recipient = recipient;
			this.asset = asset;
			this.payload = payload;
			this.rcdAt = rcdAt;
			this.rcvAt = rcvAt;
		}
		
		public Payload() {
			this(NULL,NULL,NULL,null,null,null,
					null,null,null,null,null);
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getThread_id() {
			return thread_id;
		}

		public void setThread_id(int thread_id) {
			this.thread_id = thread_id;
		}

		public int getParent_id() {
			return parent_id;
		}

		public void setParent_id(int parent_id) {
			this.parent_id = parent_id;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getChannel() {
			return channel;
		}

		public void setChannel(String channel) {
			this.channel = channel;
		}

		public String getSender() {
			return sender;
		}

		public void setSender(String sender) {
			this.sender = sender;
		}

		public String getRecipient() {
			return recipient;
		}

		public void setRecipient(String recipient) {
			this.recipient = recipient;
		}

		public String getAsset() {
			return asset;
		}

		public void setAsset(String asset) {
			this.asset = asset;
		}

		public String getPayload() {
			return payload;
		}

		public void setPayload(String payload) {
			this.payload = payload;
		}

		public String getRcdAt() {
			return rcdAt;
		}

		public void setRcdAt(String rcdAt) {
			this.rcdAt = rcdAt;
		}

		public String getRcvAt() {
			return rcvAt;
		}

		public void setRcvAt(String rcvAt) {
			this.rcvAt = rcvAt;
		}
	}
	
	private class Meta {
		private String account;
		private String event;
		
		
		
		public Meta(String account, String event) {
			this.account = account;
			this.event = event;
		}
		
		public Meta() {
			this("municio","event");
		}
		
		public String getAccount() {
			return account;
		}
		public void setAccount(String account) {
			this.account = account;
		}
		public String getEvent() {
			return event;
		}
		public void setEvent(String event) {
			this.event = event;
		}		
	}
}
