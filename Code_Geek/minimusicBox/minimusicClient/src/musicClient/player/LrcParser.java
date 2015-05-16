package musicClient.player;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashMap;

import musicClient.view.MainView;
/**
 * ������������LRC�ļ� ������������LRC�ļ�����һ��LrcInfo����
 *  ���ҷ������LrcInfo����s 
 *  ע�⣺���ñ������õ��ĸ����Ϣ������hashmap��ʱ��û����ȷ��˳��
 */
public class LrcParser {
	//��������������ڱ���hashMap
//	public Set set = this.lrcinfo.infos.entrySet() ;
//	public java.util.Iterator iterator = this.lrcinfo.infos.entrySet().iterator();
//	public java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
	
	public LrcInfo lrcinfo = new LrcInfo();

	/**
	 * ������װ�����Ϣ����
	 * @author Administrator
	 *
	 */
	 public class LrcInfo {
		 
		public String title;//������
		public String singer;//�ݳ���
		public String album;//ר��	
		public HashMap<Long,String> infos;//��������Ϣ��ʱ���һһ��Ӧ��Map
	   //����Ϊgetter()  setter()
		
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getSinger() {
			return singer;
		}
		public void setSinger(String singer) {
			this.singer = singer;
		}
		public String getAlbum() {
			return album;
		}
		public void setAlbum(String album) {
			this.album = album;
		}
		public HashMap<Long, String> getInfos() {
			return infos;
		}
		public void setInfos(HashMap<Long, String> infos) {
			this.infos = infos;
		}
		
		
		
		
	}

	
	
	public long currentTime = 0;//�����ʱʱ��
	public String currentContent = null;//�����ʱ���
	//�û��������еĸ�ʺ�ʱ�����Ϣ���ӳ���ϵ��Map
	public HashMap<Long, String> maps = new HashMap<Long, String>();

	//������hashMap��һ����������ʱ�䣬һ������������
	public HashMap<Integer,Long> hashMap1=new HashMap<Integer, Long>();
	public HashMap<Integer,String>hashMap2=new HashMap<Integer, String>();
	public int length=0;
	int a=0;
	
	/**
	 * �����ļ�·������ȡ�ļ�������һ��������
	 * 
	 * @param path
	 *            ·��
	 * @return ������
	 * @throws FileNotFoundException
	 */
	public InputStream readLrcFile(String path) throws FileNotFoundException {
		//System.out.println("1"+path);
		File f = new File(path);
		//System.out.println("4"+f);
		InputStream ins = new FileInputStream(f);
		//System.out.println("2"+ins);
		return ins;
		
		
		
	}

	public LrcInfo parser(String path) throws Exception {
		//System.out.println("3"+path);
		InputStream in = readLrcFile(path);
		lrcinfo = parser(in);
		//System.out.println("5"+in);
		return lrcinfo;

	}
	
	/**
	 * ���������е���Ϣ����������һ��LrcInfo����
	 * 
	 * @param inputStream
	 *            ������
	 * @return �����õ�LrcInfo����
	 * @throws IOException
	 */
	public LrcInfo parser(InputStream inputStream) throws IOException {
		// �����װ
		InputStreamReader inr = new InputStreamReader(inputStream);
		BufferedReader reader = new BufferedReader(inr);
		// һ��һ�еĶ���ÿ��һ�У�����һ��
		String line = null;
		while ((line = reader.readLine()) != null) {
			parserLine(line);
		}
		// ȫ�������������info
		lrcinfo.setInfos(maps);
		return lrcinfo;
	}

	/**
	 * ����������ʽ����ÿ�о������
	 * ���ڽ���������󣬽�������������Ϣ������LrcInfo������
	 * 
	 * @param str
	 */
	public void parserLine(String str) {
		// ȡ�ø�������Ϣ
		if (str.startsWith("[ti:")) {
			String title = str.substring(4, str.length() - 1);
			System.out.println("����--->" + title);
			lrcinfo.setTitle(title);

		}// ȡ�ø�����Ϣ
		else if (str.startsWith("[ar:")) {
			String singer = str.substring(4, str.length() - 1);
			System.out.println("����--->" + singer);
			lrcinfo.setSinger(singer);

		}// ȡ��ר����Ϣ
		else if (str.startsWith("[al:")) {
			String album = str.substring(4, str.length() - 1);
			System.out.println("ר��--->" + album);
			lrcinfo.setAlbum(album);

		}// ͨ������ȡ��ÿ������Ϣ
		else {
			// �����������
			String reg = "\\[(\\d{2}:\\d{2}\\.\\d{2})\\]";
			// ����
			Pattern pattern = Pattern.compile(reg);
			Matcher matcher = pattern.matcher(str);
			
			// �������ƥ�����ִ�����²���
			
			while (matcher.find()) {
				// �õ�ƥ�����������
				String msg = matcher.group();
				// �õ����ƥ���ʼ������
				int start = matcher.start();
				// �õ����ƥ�������������
				int end = matcher.end();

				// �õ����ƥ�����е�����
				int groupCount = matcher.groupCount();
				// �õ�ÿ����������
				for (int i = 0; i <= groupCount; i++) {
					String timeStr = matcher.group(i);
					if (i == 1) {
						// ���ڶ����е���������Ϊ��ǰ��һ��ʱ���
						currentTime = strToLong(timeStr);
					}
				}

				// �õ�ʱ���������
				String[] content = pattern.split(str);
				// �����������
				for (int i = 0; i < content.length; i++) {
					if (i == content.length - 1) {
						// ����������Ϊ��ǰ����
						currentContent = content[i];
					}
				}
				// ����ʱ�������ݵ�ӳ��
				maps.put(currentTime, currentContent);
				hashMap1.put(a,currentTime );
				hashMap2.put(a, currentContent);
				a++;
				System.out.println("put--time-->" + currentTime
						+ "---content--->" + currentContent);

			}//whileѭ��
			
			//hashmap����0~length-1��ֵ
			length=a;
		
		}//if�ж����
		
		
		
	}

	/**
	 * �������õ��ı�ʾʱ����ַ�ת��ΪLong��
	 * 
	 * @param group
	 *            �ַ���ʽ��ʱ���
	 * @return Long��ʽ��ʱ��
	 */
	public long strToLong(String timeStr) {
		// ��Ϊ������ַ�����ʱ���ʽΪXX:XX.XX,���ص�longҪ�����Ժ���Ϊ��λ
		// 1:ʹ�ã��ָ� 2��ʹ��.�ָ�
		String[] s = timeStr.split(":");
		int min = Integer.parseInt(s[0]);
		String[] ss = s[1].split("\\.");
		int sec = Integer.parseInt(ss[0]);
		int mill = Integer.parseInt(ss[1]);
		return min * 60 * 1000 + sec * 1000 + mill * 10;
	}

	
//	public static void main(String[] args) {
//		LrcParser lp = new LrcParser();
//         try {
//			lp.parser("F:/KuGou/����ͬ/M0151239010.lrc");
//			 for(Iterator it = lp.lrcinfo.getInfos().entrySet().iterator();it.hasNext();){  
//		         Entry<Long, String> entry = (Entry<Long, String>)it.next();  
//		         if(!"".equals(entry.getValue())){  
//		         	//jta_lrc.append("\r\t"+entry.getKey()+entry.getValue()+"\r\n");
//		            // System.out.println("������"+entry.getKey() + "\t" + entry.getValue());   
//		         }  
//		     }  
//		} catch (Exception e) {
//       System.out.println("parser erro");
//			e.printStackTrace();
//		}
//	
//	}
}
