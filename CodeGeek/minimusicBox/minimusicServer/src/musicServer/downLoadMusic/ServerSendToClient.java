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
   //设置端口号
	int port = 34566;
    ServerSocket ss ;
    //要下载的文件
    String filePath;
    public ServerSendToClient(){
    	//开启端口
    	try {
			ss= new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

  public void stop(){
	  try {
		  //关闭socket
		  if(ss!=null)
			  ss.close();
		  System.out.println("3456端口关闭");
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
                // 选择进行传输的文件
                //String filePath = "F:/KuGou/五月天 - 天使.mp3";
                File fi = new File(filePath);

                System.out.println("文件长度:" + (int) fi.length());

                // public Socket accept() throws
                // IOException侦听并接受到此套接字的连接。此方法在进行连接之前一直阻塞。

                s = ss.accept();
                System.out.println("建立socket链接");
                
                System.out.println(s.getInputStream());
                
                DataInputStream dis = new DataInputStream(new BufferedInputStream(s.getInputStream()));
                dis.readByte();
                System.out.println( "sdfdfgtgt"+dis.readByte());

                DataInputStream fis = new DataInputStream(new BufferedInputStream(new FileInputStream(filePath)));
                DataOutputStream ps = new DataOutputStream(s.getOutputStream());
                //将文件名及长度传给客户端。这里要真正适用所有平台，例如中文名的处理，还需要加工，具体可以参见Think In Java 4th里有现成的代码。
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
                // 注意关闭socket链接哦，不然客户端会等待server的数据过来，
                // 直到socket超时，导致数据不完整。                
                fis.close();
                s.close();                
                System.out.println("文件传输完成");
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
				System.out.println("等待连接。。。");
				sc=ss.accept();
				System.out.println("连接。。。");
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
//					System.out.println("等待连接。。。");
//					sc=ss.accept();
//					System.out.println("连接。。。");
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
//              // 选择进行传输的文件,通过客户端传来要下载的文件
//		      //filePath="F:/KuGou/五月天 - 天使.mp3";
//		      File fi = new File(filePath);
//              System.out.println("文件长度:" + (int) fi.length());
//
//              // public Socket accept() throws
//              // IOException侦听并接受到此套接字的连接。此方法在进行连接之前一直阻塞。
//              System.out.println("astere");
//              
//              s = ss.accept();
//              DataInputStream dis = new DataInputStream(new BufferedInputStream(s.getInputStream()));
//	          dis.readByte();         
//	         
//             System.out.println(  dis.readByte());
//            
//              //根据文件名找到资源，发送到客户端
//              DataInputStream fis = new DataInputStream(new BufferedInputStream(new FileInputStream(filePath)));
//              DataOutputStream ps = new DataOutputStream(s.getOutputStream());
//              //将文件名及长度传给客户端。这里要真正适用所有平台，例如中文名的处理，还需要加工，具体可以参见Think In Java 4th里有现成的代码。
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
//            	  //循环读取文件
//                  int read = 0;
//                  if (fis != null) {
//                      read = fis.read(buf);
//                  }if (read == -1) {
//                      break;
//                  }
//                 //通过字节流给客户端
//                  ps.write(buf, 0, read);
//              }
//              ps.flush();
//              // 注意关闭socket链接，不然客户端会等待server的数据过来，
//              // 直到socket超时，导致数据不完整。                
//              fis.close();
//              s.close();                
//              System.out.println("文件传输完成");
//	          }catch (Exception e) {
//	          e.printStackTrace();
//	          }
//			}
//		  }
	}
}



