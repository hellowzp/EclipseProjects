//http://www.wigwag.com/devblog/send-email-through-gmail-smtp-server-using-javamail/

// Create properties, get Session
 Properties props = new Properties();

 props.put("mail.smtp.ssl.enable", "true");
 props.put("mail.transport.protocol", "smtps");
 props.put("mail.debug", "true");

 // If using static Transport.send(),
 // Need to specify which host to send it to
 String host = "smtp.gmail.com"; //gmail SMTP server name
 props.put("mail.smtps.host", host);

 //Specify which port to connect to
 int port = 465; //port for SSL connection
 props.put("mail.smtps.port", port);

 //Enable SSL
 props.put("mail.smtps.socketFactory.port", Integer.toString(port));
 props.put("mail.smtps.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
 props.put("mail.smtps.socketFactory.fallback", "false");
 props.put("mail.smtps.auth", "true");

 //Enable authentication by providing your Gmail account and password
 final String senderAccount = "InsertAccountHere@gmail.com";
 final String senderAccountPassword = "GmailAccountPasswordHere";
 Session session = Session.getInstance(props);
 session = Session.getInstance(props, new javax.mail.Authenticator() {
 protected PasswordAuthentication getPasswordAuthentication() {
 return new PasswordAuthentication(senderAccount, senderAccountPassword);
 }
 });

 Transport transport = session.getTransport("smtps");
 //Connect to the host with the specified sender
 transport.connect(host, senderAccount);