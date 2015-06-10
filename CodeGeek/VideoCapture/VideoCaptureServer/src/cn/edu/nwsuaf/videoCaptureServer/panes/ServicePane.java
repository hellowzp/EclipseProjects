package cn.edu.nwsuaf.videoCaptureServer.panes;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import cn.edu.nwsuaf.videoCaptureServer.UDPTransmit;
import cn.edu.nwsuaf.videoCaptureServer.components.ServecieProcessBar;
import cn.edu.nwsuaf.videoCaptureServer.utils.DateDeal;
import cn.edu.nwsuaf.videoCaptureServer.utils.FillWidth;


public class ServicePane extends JPanel implements ActionListener {

	/** 启动服务按钮 */
	private JButton btnStart = new JButton("启动服务器");
	/** 停止服务按钮 */
	private JButton btnStop = new JButton("停止服务器");
	/**  */
	private ServecieProcessBar bar = new ServecieProcessBar(300, 30);
	/** 显示连接日志 */
	private JTextArea areaLog = new JTextArea();

	private String path = "logs/"+DateDeal.getCurrentDate()+".log";
	private PrintWriter raf = null;
	DatagramPacket datagramPacket = null;
	DatagramSocket datagramSocket = null;
	/**
	 * 
	 */
	private byte buf[] = new byte[1024];
	public boolean isServerRun = true;
	private int port = 2010;
	String newAddress = "";
	String sendAddressList = "";
	String addressList="";
	public static List<String> addresses = new ArrayList<String>();

	public ServicePane() {
		try {
			raf = new PrintWriter(new BufferedOutputStream(
					new FileOutputStream(new File(path), true)));
		} catch (FileNotFoundException e) {
			areaLog.append("发生异常错误，请确保" + path + "文件可写!原因如下:" + e.getMessage());
			btnStart.setEnabled(false);
		}

		setLayout(new FlowLayout(FlowLayout.CENTER));

		areaLog.setEditable(false);
		areaLog.setLineWrap(true);
		bar.setPreferredSize(new Dimension(580, 27));
		btnStart.addActionListener(this);
		btnStop.addActionListener(this);
		btnStop.setEnabled(false);

		JPanel pane = new JPanel();
		pane.setPreferredSize(new Dimension(600, 70));
		pane.setLayout(new FlowLayout(FlowLayout.CENTER));
		pane.add(btnStart);
		pane.add(btnStop);
		pane.add(bar);

		setLayout(new BorderLayout());
		add(pane, BorderLayout.NORTH);
		add(new JScrollPane(areaLog), BorderLayout.CENTER);
		add(new FillWidth(4, 4), BorderLayout.WEST);
		add(new FillWidth(4, 4), BorderLayout.EAST);

	}

	/**
	 * 启动按钮、停止按钮的事件。
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnStart) {

			try {
				btnStart.setEnabled(false);
				btnStop.setEnabled(true);
				bar.startRoll();
				startServer();
			} catch (IOException e1) {
				writeSysLog(DateDeal.getCurrentTime()
						+ ",视频捕获服务器启动服务时发生错误，原因如下:" + e1.getMessage());
			}
		}
		if (e.getSource() == btnStop) {
			btnStart.setEnabled(true);
			btnStop.setEnabled(false);
			bar.stopRoll();
			try {
				stopServer();
			} catch (IOException e1) {
				writeSysLog(DateDeal.getCurrentTime()
						+ ",视频捕获服务器停止服务时发生错误，原因如下:" + e1.getMessage());
			}
		}
	}

	/**
	 * 书写系统日志。
	 * 
	 * @param log
	 *            日志。
	 */
	public void writeSysLog(String log) {
		areaLog.append(log + "\n");
		// 滚动条自动下滚
		areaLog.setCaretPosition(areaLog.getDocument().getLength());
		raf.println(log);

		raf.flush();
	}

	/**
	 * 启动服务器。
	 * 
	 * @throws IOException
	 *             IO异常。
	 */
	public void startServer() throws IOException {
	        System.out.println("服务器开始运行");
	        System.out.println("服务端口:" + port);

	        datagramPacket = new DatagramPacket(buf, buf.length);
			datagramSocket = new DatagramSocket(port);
		
		new Thread() {
			@Override
			public void run() {
//				while (isServerRun) {
					//						datagramSocket.receive(datagramPacket);
//						ByteArrayInputStream bais = new ByteArrayInputStream(buf);
//						DataInputStream dis = new DataInputStream(bais);
//						System.out.println(dis.readUTF());
//						
//						ByteArrayOutputStream baos = new ByteArrayOutputStream();
//						DataOutputStream dos = new DataOutputStream(baos);
//						dos.writeUTF("HelloClient!!");
//						
//						buf= baos.toByteArray();
//
//						datagramSocket.send(datagramPacket);
					receive();
//				}
			}
		}.start();
		
		writeSysLog(DateDeal.getCurrentTime() + ",视频捕获服务器服务启动成功!等待视频捕获用户上线...");
	}

	
    public void receive() {
    	while (isServerRun){
            try {
            	try {
					datagramSocket.receive(datagramPacket);
				} catch (Exception e) {
					printInfo(e.getMessage());
					 writeSysLog(DateDeal.getCurrentTime() + ","+e.getMessage());
				}

                String msg = new String(datagramPacket.getData(),datagramPacket.getOffset(),datagramPacket.getLength());
                String natAddress = datagramPacket.getAddress().toString().substring(1);
                int natPort = datagramPacket.getPort();

               
                String Num = msg.substring(0,1);
                msg = msg.substring(2);
                String line;

                //1表示管理UDP
                //2表示音频UDP
                //3表示音频RTCP UDP
                //4表示视频UDP 
                //5表示视频RTCP UDP
                //6表示消息会话
                //7表示离开
                //8表示保持连接
                
                switch(Integer.parseInt(Num)){
                    case 1 : {
                        line = "一个新的用户登录: " + msg + "  NAT地址: " + natAddress + " 管理端口: " + natPort;
                        sendAddressList = sendAddressList + natAddress + " " + natPort + " ";
                        newAddress = msg + ":" + natAddress + ":" + natPort;
                        addresses.add(newAddress);
                        printInfo(line);
                        writeSysLog(DateDeal.getCurrentTime() + ","+line);
                        new UDPTransmit(datagramSocket,"0#欢迎"+msg+"登陆！",natAddress,natPort,false).start();
                        break;
                    }

                    case 2 : {
                        line = "音频端口: " + natPort;
                        newAddress = newAddress + ":" + natPort;
                        printInfo(line);
                        writeSysLog(DateDeal.getCurrentTime() + ","+line);
                        break;
                    }

                    case 3 : {
                        line = "音频RTCP端口: " + natPort;
                        newAddress = newAddress + ":" + natPort;
                        printInfo(line);
                        writeSysLog(DateDeal.getCurrentTime() + ","+line);
                        break;

                    }

                    case 4 : {
                        line = "视频端口: " + natPort;
                        newAddress = newAddress + ":" + natPort;

                        printInfo(line);
                        writeSysLog(DateDeal.getCurrentTime() + ","+line);
                        break;
                    }

                    case 5 : {
                        line = "视频RTCP端口: " + natPort;
                        newAddress = newAddress + ":" + natPort;
                        printInfo(line);
                        writeSysLog(DateDeal.getCurrentTime() + ","+line);
                        if (!sendAddressList.equals(""))
                            doSend("1 " + newAddress, sendAddressList);
                        if(!addressList.equals("")){
                            String address[] = newAddress.split(":");
                            doSend("7 " + addressList,
                                   address[1] + " " + address[2]);
                        }
                        addressList = addressList + newAddress + " ";
                        break;
                    }
                    
                    case 6 : {
                        line = "用户发送消息: " + msg + "  NAT地址: " + natAddress + " 消息端口: " + natPort;
                        sendAddressList = sendAddressList + natAddress + " " + natPort + " ";
                        newAddress = msg + ":" + natAddress + ":" + natPort;
                        for(String str:addresses){
                        	String[] str2 = str.split(":");
                        	if(str2!=null){
                        		
                        			 new UDPTransmit(datagramSocket,"6#"+msg,str2[1],Integer.parseInt(str2[2].trim()),false).start();	
                        		
                        	}
                        }
                        
                       
                        printInfo(line);
                        writeSysLog(DateDeal.getCurrentTime() + ","+line);
                        break;
                    }

                    case 7 : {
                        line = "一个客户离开: " + msg + "  NAT地址: " + natAddress + " 管理端口: " + natPort;
                        printInfo(line);
                        writeSysLog(DateDeal.getCurrentTime() + ","+line);
                        delAddress(msg, natAddress, natPort);
                        if(!sendAddressList.equals(""))
                            doSend("6 " + msg + ":" + natAddress, sendAddressList);
                        break;
                    }

                    case 8 : break;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
	
	
	/**
	 * 停止服务器。
	 * 
	 * @throws IOException
	 *             IO异常。
	 */
	public void stopServer() throws IOException {
		addresses.clear();
		isServerRun =false;
		datagramSocket.close();

		writeSysLog(DateDeal.getCurrentTime() + ",视频捕获服务器服务停止成功!");
	}

	/**
	 * 获取客户端IP
	 * 
	 * @return 返回字符串的IP。
	 */
	private String getClientIP() {
		return datagramPacket == null ? "[客户端已关闭,不能获取信息]" : "["
				+ datagramPacket.getAddress().toString() + ":"
				+ datagramPacket.getPort() + "]";
	}
	/**
	 * 用于在服务器后台打出提示信息
	 * @param message
	 */
	public void printInfo(String message) {
        Date nowTime = new Date();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa", Locale.US);
        System.out.println(fmt.format(nowTime) + "----" + message);
        
    }
	
	  public void doSend(String msgSend, String sendAddressList) throws Exception {
	        String[] s = sendAddressList.split(" ");
	        byte[] data = msgSend.getBytes();
	        for(int i =0; i < s.length; i++){
	            DatagramPacket pack = new DatagramPacket(data, data.length, InetAddress.getByName(s[i]), Integer.parseInt(s[++i]));
	            datagramSocket.send(pack);
	        }
	    }

	    public void delAddress(String msg, String natAddress, int ctrlPort){

	        String regEx = msg + ":" + natAddress + ":" + ctrlPort + ":.{1,5}:.{1,5}:.{1,5}:.{1,5} ";
	        Pattern p = Pattern.compile(regEx);
	        Matcher m=p.matcher(addressList);

	        String s = m.replaceAll("");
	        addressList = s;

	        regEx = natAddress + " " + ctrlPort + " ";
	        p = Pattern.compile(regEx);
	        m = p.matcher(sendAddressList);
	        s = m.replaceAll("");
	        sendAddressList = s;
	    }

}
