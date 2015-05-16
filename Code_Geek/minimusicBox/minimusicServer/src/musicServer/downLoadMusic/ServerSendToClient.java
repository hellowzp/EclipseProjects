package musicServer.downLoadMusic;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerSendToClient implements Runnable{
   //���ö˿ں�
	int port = 34566;
    ServerSocket ss ;
    //Ҫ���ص��ļ�
    String filePath;
    public ServerSendToClient(){
    	//�����˿�
    	try {
			ss= new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

  public void stop(){
	  try {
		  //�ر�socket
		  if(ss!=null)
			  ss.close();
		  System.out.println("3456�˿ڹر�");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
  }
//    public static void main(String arg[]) {
//    	Thread t=new Thread ( new ServerSendToClient());
//    	t.start();
//    }

    public  void start() {
        Socket s = null;
        try {
           
            while (true) {
                // ѡ����д�����ļ�
                //String filePath = "F:/KuGou/������ - ��ʹ.mp3";
                File fi = new File(filePath);

                System.out.println("�ļ�����:" + (int) fi.length());

                // public Socket accept() throws
                // IOException���������ܵ����׽��ֵ����ӡ��˷����ڽ�������֮ǰһֱ������

                s = ss.accept();
                System.out.println("����socket����");
                
                System.out.println(s.getInputStream());
                
                DataInputStream dis = new DataInputStream(new BufferedInputStream(s.getInputStream()));
                dis.readByte();
                System.out.println( "sdfdfgtgt"+dis.readByte());

                DataInputStream fis = new DataInputStream(new BufferedInputStream(new FileInputStream(filePath)));
                DataOutputStream ps = new DataOutputStream(s.getOutputStream());
                //���ļ��������ȴ����ͻ��ˡ�����Ҫ������������ƽ̨�������������Ĵ�������Ҫ�ӹ���������Բμ�Think In Java 4th�����ֳɵĴ��롣
                ps.writeUTF(fi.getName());
                
                System.out.println(fi.getName());
                ps.flush();
                ps.writeLong((long) fi.length());
                ps.flush();

                int bufferSize = 8192;
                byte[] buf = new byte[bufferSize];

                while (true) {
                    int read = 0;
                    if (fis != null) {
                        read = fis.read(buf);
                    }

                    if (read == -1) {
                        break;
                    }
                    ps.write(buf, 0, read);
                }
                ps.flush();
                // ע��ر�socket����Ŷ����Ȼ�ͻ��˻�ȴ�server�����ݹ�����
                // ֱ��socket��ʱ���������ݲ�������                
                fis.close();
                s.close();                
                System.out.println("�ļ��������");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
	@Override
	public void run() {
		// TODO Auto-generated method stub 
		 try {
				
				Socket sc=null; 
				System.out.println("�ȴ����ӡ�����");
				sc=ss.accept();
				System.out.println("���ӡ�����");
				ObjectInputStream ois=new ObjectInputStream(sc.getInputStream());
				 filePath=(String)(ois.readObject());
				 System.out.println(filePath);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
			start();
			
	//	  while (true) {
			  
//			  try {
//					
//					Socket sc=null; 
//					System.out.println("�ȴ����ӡ�����");
//					sc=ss.accept();
//					System.out.println("���ӡ�����");
//					ObjectInputStream ois=new ObjectInputStream(sc.getInputStream());
//					 filePath=(String)(ois.readObject());
//					 System.out.println(filePath);
//				} catch (Exception e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
			  
//			// Socket s1 = null;
//				if(filePath!=null){
//					
//				
//			 Socket s=null;
//		     try {
//              // ѡ����д�����ļ�,ͨ���ͻ��˴���Ҫ���ص��ļ�
//		      //filePath="F:/KuGou/������ - ��ʹ.mp3";
//		      File fi = new File(filePath);
//              System.out.println("�ļ�����:" + (int) fi.length());
//
//              // public Socket accept() throws
//              // IOException���������ܵ����׽��ֵ����ӡ��˷����ڽ�������֮ǰһֱ������
//              System.out.println("astere");
//              
//              s = ss.accept();
//              DataInputStream dis = new DataInputStream(new BufferedInputStream(s.getInputStream()));
//	          dis.readByte();         
//	         
//             System.out.println(  dis.readByte());
//            
//              //�����ļ����ҵ���Դ�����͵��ͻ���
//              DataInputStream fis = new DataInputStream(new BufferedInputStream(new FileInputStream(filePath)));
//              DataOutputStream ps = new DataOutputStream(s.getOutputStream());
//              //���ļ��������ȴ����ͻ��ˡ�����Ҫ������������ƽ̨�������������Ĵ�������Ҫ�ӹ���������Բμ�Think In Java 4th�����ֳɵĴ��롣
//              ps.writeUTF(fi.getName());
//              ps.flush();
//           
//              ps.writeLong((long) fi.length());
//              ps.flush();
//
//              int bufferSize = 8192;
//              byte[] buf = new byte[bufferSize];
//
//              while (true) {
//            	  //ѭ����ȡ�ļ�
//                  int read = 0;
//                  if (fis != null) {
//                      read = fis.read(buf);
//                  }if (read == -1) {
//                      break;
//                  }
//                 //ͨ���ֽ������ͻ���
//                  ps.write(buf, 0, read);
//              }
//              ps.flush();
//              // ע��ر�socket���ӣ���Ȼ�ͻ��˻�ȴ�server�����ݹ�����
//              // ֱ��socket��ʱ���������ݲ�������                
//              fis.close();
//              s.close();                
//              System.out.println("�ļ��������");
//	          }catch (Exception e) {
//	          e.printStackTrace();
//	          }
//			}
//		  }
	}
}



