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

	/** ��������ť */
	private JButton btnStart = new JButton("����������");
	/** ֹͣ����ť */
	private JButton btnStop = new JButton("ֹͣ������");
	/**  */
	private ServecieProcessBar bar = new ServecieProcessBar(300, 30);
	/** ��ʾ������־ */
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
			areaLog.append("�����쳣������ȷ��" + path + "�ļ���д!ԭ������:" + e.getMessage());
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
	 * ������ť��ֹͣ��ť���¼���
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
						+ ",��Ƶ�����������������ʱ��������ԭ������:" + e1.getMessage());
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
						+ ",��Ƶ���������ֹͣ����ʱ��������ԭ������:" + e1.getMessage());
			}
		}
	}

	/**
	 * ��дϵͳ��־��
	 * 
	 * @param log
	 *            ��־��
	 */
	public void writeSysLog(String log) {
		areaLog.append(log + "\n");
		// �������Զ��¹�
		areaLog.setCaretPosition(areaLog.getDocument().getLength());
		raf.println(log);

		raf.flush();
	}

	/**
	 * ������������
	 * 
	 * @throws IOException
	 *             IO�쳣��
	 */
	public void startServer() throws IOException {
	        System.out.println("��������ʼ����");
	        System.out.println("����˿�:" + port);

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
		
		writeSysLog(DateDeal.getCurrentTime() + ",��Ƶ������������������ɹ�!�ȴ���Ƶ�����û�����...");
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

                //1��ʾ����UDP
                //2��ʾ��ƵUDP
                //3��ʾ��ƵRTCP UDP
                //4��ʾ��ƵUDP 
                //5��ʾ��ƵRTCP UDP
                //6��ʾ��Ϣ�Ự
                //7��ʾ�뿪
                //8��ʾ��������
                
                switch(Integer.parseInt(Num)){
                    case 1 : {
                        line = "һ���µ��û���¼: " + msg + "  NAT��ַ: " + natAddress + " ����˿�: " + natPort;
                        sendAddressList = sendAddressList + natAddress + " " + natPort + " ";
                        newAddress = msg + ":" + natAddress + ":" + natPort;
                        addresses.add(newAddress);
                        printInfo(line);
                        writeSysLog(DateDeal.getCurrentTime() + ","+line);
                        new UDPTransmit(datagramSocket,"0#��ӭ"+msg+"��½��",natAddress,natPort,false).start();
                        break;
                    }

                    case 2 : {
                        line = "��Ƶ�˿�: " + natPort;
                        newAddress = newAddress + ":" + natPort;
                        printInfo(line);
                        writeSysLog(DateDeal.getCurrentTime() + ","+line);
                        break;
                    }

                    case 3 : {
                        line = "��ƵRTCP�˿�: " + natPort;
                        newAddress = newAddress + ":" + natPort;
                        printInfo(line);
                        writeSysLog(DateDeal.getCurrentTime() + ","+line);
                        break;

                    }

                    case 4 : {
                        line = "��Ƶ�˿�: " + natPort;
                        newAddress = newAddress + ":" + natPort;

                        printInfo(line);
                        writeSysLog(DateDeal.getCurrentTime() + ","+line);
                        break;
                    }

                    case 5 : {
                        line = "��ƵRTCP�˿�: " + natPort;
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
                        line = "�û�������Ϣ: " + msg + "  NAT��ַ: " + natAddress + " ��Ϣ�˿�: " + natPort;
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
                        line = "һ���ͻ��뿪: " + msg + "  NAT��ַ: " + natAddress + " ����˿�: " + natPort;
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
	 * ֹͣ��������
	 * 
	 * @throws IOException
	 *             IO�쳣��
	 */
	public void stopServer() throws IOException {
		addresses.clear();
		isServerRun =false;
		datagramSocket.close();

		writeSysLog(DateDeal.getCurrentTime() + ",��Ƶ�������������ֹͣ�ɹ�!");
	}

	/**
	 * ��ȡ�ͻ���IP
	 * 
	 * @return �����ַ�����IP��
	 */
	private String getClientIP() {
		return datagramPacket == null ? "[�ͻ����ѹر�,���ܻ�ȡ��Ϣ]" : "["
				+ datagramPacket.getAddress().toString() + ":"
				+ datagramPacket.getPort() + "]";
	}
	/**
	 * �����ڷ�������̨�����ʾ��Ϣ
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
