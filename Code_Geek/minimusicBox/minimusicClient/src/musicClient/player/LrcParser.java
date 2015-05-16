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
 * 此类用来解析LRC文件 将解析完整的LRC文件放入一个LrcInfo对象
 *  并且返回这个LrcInfo对象s 
 *  注意：采用本方法得到的歌词信息保存在hashmap中时并没有正确的顺序
 */
public class LrcParser {
	//定义迭代器，用于遍历hashMap
//	public Set set = this.lrcinfo.infos.entrySet() ;
//	public java.util.Iterator iterator = this.lrcinfo.infos.entrySet().iterator();
//	public java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
	
	public LrcInfo lrcinfo = new LrcInfo();

	/**
	 * 用来封装歌词信息的类
	 * @author Administrator
	 *
	 */
	 public class LrcInfo {
		 
		public String title;//歌曲名
		public String singer;//演唱者
		public String album;//专辑	
		public HashMap<Long,String> infos;//保存歌词信息和时间点一一对应的Map
	   //以下为getter()  setter()
		
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

	
	
	public long currentTime = 0;//存放临时时间
	public String currentContent = null;//存放临时歌词
	//用户保存所有的歌词和时间点信息间的映射关系的Map
	public HashMap<Long, String> maps = new HashMap<Long, String>();

	//建两个hashMap，一个用来保存时间，一个用来保存歌词
	public HashMap<Integer,Long> hashMap1=new HashMap<Integer, Long>();
	public HashMap<Integer,String>hashMap2=new HashMap<Integer, String>();
	public int length=0;
	int a=0;
	
	/**
	 * 根据文件路径，读取文件，返回一个输入流
	 * 
	 * @param path
	 *            路径
	 * @return 输入流
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
	 * 将输入流中的信息解析，返回一个LrcInfo对象
	 * 
	 * @param inputStream
	 *            输入流
	 * @return 解析好的LrcInfo对象
	 * @throws IOException
	 */
	public LrcInfo parser(InputStream inputStream) throws IOException {
		// 三层包装
		InputStreamReader inr = new InputStreamReader(inputStream);
		BufferedReader reader = new BufferedReader(inr);
		// 一行一行的读，每读一行，解析一行
		String line = null;
		while ((line = reader.readLine()) != null) {
			parserLine(line);
		}
		// 全部解析完后，设置info
		lrcinfo.setInfos(maps);
		return lrcinfo;
	}

	/**
	 * 利用正则表达式解析每行具体语句
	 * 并在解析完该语句后，将解析出来的信息设置在LrcInfo对象中
	 * 
	 * @param str
	 */
	public void parserLine(String str) {
		// 取得歌曲名信息
		if (str.startsWith("[ti:")) {
			String title = str.substring(4, str.length() - 1);
			System.out.println("歌曲--->" + title);
			lrcinfo.setTitle(title);

		}// 取得歌手信息
		else if (str.startsWith("[ar:")) {
			String singer = str.substring(4, str.length() - 1);
			System.out.println("歌手--->" + singer);
			lrcinfo.setSinger(singer);

		}// 取得专辑信息
		else if (str.startsWith("[al:")) {
			String album = str.substring(4, str.length() - 1);
			System.out.println("专辑--->" + album);
			lrcinfo.setAlbum(album);

		}// 通过正则取得每句歌词信息
		else {
			// 设置正则规则
			String reg = "\\[(\\d{2}:\\d{2}\\.\\d{2})\\]";
			// 编译
			Pattern pattern = Pattern.compile(reg);
			Matcher matcher = pattern.matcher(str);
			
			// 如果存在匹配项，则执行以下操作
			
			while (matcher.find()) {
				// 得到匹配的所有内容
				String msg = matcher.group();
				// 得到这个匹配项开始的索引
				int start = matcher.start();
				// 得到这个匹配项结束的索引
				int end = matcher.end();

				// 得到这个匹配项中的组数
				int groupCount = matcher.groupCount();
				// 得到每个组中内容
				for (int i = 0; i <= groupCount; i++) {
					String timeStr = matcher.group(i);
					if (i == 1) {
						// 将第二组中的内容设置为当前的一个时间点
						currentTime = strToLong(timeStr);
					}
				}

				// 得到时间点后的内容
				String[] content = pattern.split(str);
				// 输出数组内容
				for (int i = 0; i < content.length; i++) {
					if (i == content.length - 1) {
						// 将内容设置为当前内容
						currentContent = content[i];
					}
				}
				// 设置时间点和内容的映射
				maps.put(currentTime, currentContent);
				hashMap1.put(a,currentTime );
				hashMap2.put(a, currentContent);
				a++;
				System.out.println("put--time-->" + currentTime
						+ "---content--->" + currentContent);

			}//while循环
			
			//hashmap中有0~length-1个值
			length=a;
		
		}//if判断语句
		
		
		
	}

	/**
	 * 将解析得到的表示时间的字符转化为Long型
	 * 
	 * @param group
	 *            字符形式的时间点
	 * @return Long形式的时间
	 */
	public long strToLong(String timeStr) {
		// 因为给如的字符串的时间格式为XX:XX.XX,返回的long要求是以毫秒为单位
		// 1:使用：分割 2：使用.分割
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
//			lp.parser("F:/KuGou/方大同/M0151239010.lrc");
//			 for(Iterator it = lp.lrcinfo.getInfos().entrySet().iterator();it.hasNext();){  
//		         Entry<Long, String> entry = (Entry<Long, String>)it.next();  
//		         if(!"".equals(entry.getValue())){  
//		         	//jta_lrc.append("\r\t"+entry.getKey()+entry.getValue()+"\r\n");
//		            // System.out.println("渣渣："+entry.getKey() + "\t" + entry.getValue());   
//		         }  
//		     }  
//		} catch (Exception e) {
//       System.out.println("parser erro");
//			e.printStackTrace();
//		}
//	
//	}
}
