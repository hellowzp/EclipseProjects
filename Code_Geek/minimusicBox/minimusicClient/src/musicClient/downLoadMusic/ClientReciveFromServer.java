package musicClient.downLoadMusic;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientReciveFromServer implements Runnable{
	 private ClientSocket cs = null;
	    //��������IP
	    private String ip = "localhost";// ���óɷ�����IP
	   //�˿�
	    private int port = 34566;
	    private String sendMessage = "Windwos";
	    Socket  s1=null;
	    
 	    public ClientReciveFromServer(String downloadMusic) {

 	    	try {
				s1=new Socket(ip,port);
				//��Ҫ���صĸ������͵�������
				ObjectOutputStream oos=new ObjectOutputStream(s1.getOutputStream());
				oos.writeObject(downloadMusic);
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	//�ڷ��������ļ��Ķ�д��ʽ
	    	try {
	            if (createConnection()) {
	                sendMessage();      
	            }

	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }

	    private boolean createConnection() {
	        cs = new ClientSocket(ip, port);
	        try {
	            cs.CreateConnection();
	            System.out.print("���ӷ������ɹ�!" + "\n");
	            return true;
	        } catch (Exception e) {
	            System.out.print("���ӷ�����ʧ��!" + "\n");
	            return false;
	        }

	    }

	    private void sendMessage() {
	        if (cs == null)
	            return;
	        try {
	            cs.sendMessage(sendMessage);
	        } catch (Exception e) {
	            System.out.print("������Ϣʧ��!" + "\n");
	        }
	    }

	    private void getMessage() {
	        if (cs == null)
	            return;
	        DataInputStream inputStream = null;
	        try {
	            inputStream = cs.getMessageStream();
	        } catch (Exception e) {
	            System.out.print("������Ϣ�������\n");
	            return;
	        }

	        try {
	            //���ر���·�����ļ������Զ��ӷ������˼̳ж�����
	            String savePath = "E:\\";
	            int bufferSize = 8192;
	            byte[] buf = new byte[bufferSize];
	            int passedlen = 0;
	            long len=0;
	            
	            savePath += inputStream.readUTF();
	            DataOutputStream fileOut = new DataOutputStream(new BufferedOutputStream(new BufferedOutputStream(new FileOutputStream(savePath))));
	            len = inputStream.readLong();
	            
	            System.out.println("�ļ��ĳ���Ϊ:" + len + "\n");
	            System.out.println("��ʼ�����ļ�!" + "\n");
	                    
	            while (true) {
	                int read = 0;
	                if (inputStream != null) {
	                    read = inputStream.read(buf);
	                }
	                passedlen += read;
	                if (read == -1) {
	                    break;
	                }
	                //�����������Ϊͼ�ν����prograssBar���ģ���������Ǵ��ļ������ܻ��ظ���ӡ��һЩ��ͬ�İٷֱ�
	                System.out.println("�ļ�������" +  (passedlen * 100/ len) + "%\n");
	                fileOut.write(buf, 0, read);
	            }
	            System.out.println("������ɣ��ļ���Ϊ" + savePath + "\n");

	            fileOut.close();
	        } catch (Exception e) {
	            System.out.println("������Ϣ����" + "\n");
	            return;
	        }
	    }

//	    public static void main(String arg[]) {
//	    	 ClientReciveFromServer crfs=new ClientReciveFromServer("F:/KuGou/������ - ��ʹ.mp3");
//	    	 Thread t=new Thread(crfs);
//	    	 t.start();
//	    }

		@Override
		public void run() {
			// TODO Auto-generated method stub
			 getMessage();
		}
	}