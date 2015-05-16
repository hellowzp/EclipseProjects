/**
 * ���ܣ���Ӹ���
 */
package musicClient.operateToDb;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import musicClient.model.MusicModel;
public class AddMusic extends JFrame implements ActionListener{

	//��������Ҫ�����
	JPanel jp_add,jp1;
	JLabel jlb_title,jlb_songname,jlb_songaddress,jlb_singer,jlb_songlist,jlb_songlrc;
	public JTextField jtf_songname,jtf_songaddress, jtf_singername, jtf_songlrc;
	public JComboBox jcb_songList;
	JButton jb_scane,jb_brower,jb_sure,jb_quit;
	//���帴ѡ��õ��Ĳ����б�
	String listName;
	
	public AddMusic()
	{
		//�����������
		//jlb_pic=new JLabel(new ImageIcon("images/welcome.jpg"));
		jlb_title=new JLabel("������Ϣ");
		jlb_title.setForeground(Color.orange);
		jlb_title.setFont(new Font("��������",Font.BOLD,24));
		jlb_title.setBounds(170, 30, 200, 50);
		jlb_songname=new JLabel("��   ��:",JLabel.CENTER);
		jlb_songaddress=new JLabel("λ   ��:",JLabel.CENTER);
		jlb_singer=new JLabel("��   ��:",JLabel.CENTER);
		jlb_songlist=new JLabel("��   ��:",JLabel.CENTER);
		jlb_songlrc=new JLabel("��  ��:",JLabel.CENTER);
		jlb_songname.setBounds(100, 100, 60, 25);
		jlb_songaddress.setBounds(100, 140, 60, 25);
		jlb_singer.setBounds(100, 180, 60, 25);
		jlb_songlist.setBounds(100, 220, 60, 25);
		jlb_songlrc.setBounds(100, 260, 60, 25);
		
		jtf_songname=new JTextField(40);
		jtf_songname.setText("ѡ�����");
		jtf_songname.setForeground(Color.magenta);
		jtf_songname.getFocusTraversalKeysEnabled();
		jtf_songaddress=new JTextField(100);
		jtf_singername=new JTextField(40);
		jtf_singername.setText("������~");
		jtf_singername.setForeground(Color.darkGray);
		jtf_singername.getFocusListeners();
		String chooseList[]={"Ĭ���б�","�����ĸ�","ϲ���ĸ�"}; 
		jcb_songList=new JComboBox(chooseList);
		jcb_songList.addActionListener(this);
		jcb_songList.setActionCommand("list");
		jtf_songlrc=new JTextField(100);
		//�������λ��
		jtf_songname.setBounds( 200, 100, 150, 28);
		jtf_songaddress.setBounds( 200, 140, 150, 28);
		jtf_singername.setBounds( 200, 180, 150, 28);
		jcb_songList.setBounds( 200, 220, 150, 28);
		jtf_songlrc.setBounds(200, 260, 150, 28);
		
		
		jb_scane=new JButton("���");
		jb_scane.addActionListener(this);
		jb_brower=new JButton("ѡ��");
		jb_brower.addActionListener(this);
		jb_sure=new JButton("ȷ��");
		jb_sure.setForeground(Color.blue);
		jb_sure.addActionListener(this);
		jb_quit=new JButton("ȡ��");
		jb_quit.setForeground(Color.gray);
		jb_quit.addActionListener(this);
		//�������λ��
		jb_scane.setBounds( 355, 100, 60, 25);
		jb_brower.setBounds(355, 260, 60, 25);
		jb_sure.setBounds(180, 300, 60, 30);
		jb_quit.setBounds( 280, 300, 60, 30);
		
	
		
		//�����������
		jp_add=new JPanel(null);
		jp_add.setOpaque(false);
		
		jp_add.setBounds(0, 0, 500,400);
		jp_add.add(jlb_title);
		jp_add.add(jlb_songname);
		jp_add.add(jtf_songname);
		jp_add.add(jb_scane);
		jp_add.add(jlb_songaddress);
		jp_add.add(jtf_songaddress);
		jp_add.add(jlb_singer);
		jp_add.add(jtf_singername);
		jp_add.add(jlb_songlist);
		jp_add.add(jcb_songList);
		jp_add.add(jlb_songlrc);
		jp_add.add(jtf_songlrc);
		jp_add.add(jb_brower);
		jp_add.add(jb_sure);
		jp_add.add(jb_quit);
		
			
		this.add(jp_add);
	
		//���ñ���ͼƬ
		JPanel imagePanel;
		ImageIcon background=new ImageIcon("images/background7.jpg");
		JLabel label=new JLabel(background);
		label.setBounds(0, 0, background.getIconWidth(), background.getIconHeight());
		imagePanel=(JPanel)this.getContentPane();
		//ʹ���ݴ���͸����
		imagePanel.setOpaque(false);
		this.getLayeredPane().setLayout(null);
		this.getLayeredPane().add(label,new Integer(Integer.MIN_VALUE));
		
		 try {
	            // ��LookAndFeel���ó�Windows��ʽ
	            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
	        } catch (Exception ex) {	
	            ex.printStackTrace();
	        }
	        
		//���ô�������
	    this.setTitle("��Ӹ���...");
		this.setSize(500,400);
		this.setLocation(300, 150);
		this.setResizable(false);
		this.setVisible(true);
		//AddMusic���Ӵ��ڣ��رմ���ʱ�����˳�����
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//�Ը�ѡ���¼�����
		if(e.getActionCommand().equals("list")){
			JComboBox jcb=(JComboBox)e.getSource();
			setListName((String)jcb.getSelectedItem());
			
		}
		//�����¼�
		if(e.getSource()==jb_scane)
		{
			//����ļ�
			//�ļ�ѡ�����JFileChooser
			JFileChooser jfc=new JFileChooser();
			//��������
			jfc.setDialogTitle("��ѡ�������ļ�... ");
			jfc.setFileFilter(new JAVAFiler(
			"*.mp3,*.AU��*.AVI��*.MIDI��*.MPEG��*.QuickTime,*.wav"));
			//Ĭ�Ϸ�ʽ
			jfc.showOpenDialog(null);
			//��ʾ����
			jfc.setVisible(true);
			
			//�õ��û�ѡ���ļ���ȫ·��
			String filepath=jfc.getSelectedFile().getAbsolutePath();
			String filename=jfc.getSelectedFile().getName();
			jtf_songname.setText("");
			jtf_songname.setText(filename);
			System.out.println(filepath);
			jtf_songaddress.setText("");
			jtf_songaddress.setText(filepath);	
		}
		if(e.getSource()==jb_brower)
		{
			//����ļ�
			//�ļ�ѡ�����JFileChooser
			JFileChooser jfc=new JFileChooser();
			//��������
			jfc.setDialogTitle("��ѡ�����ļ�... ");
			//Ĭ�Ϸ�ʽ
			jfc.showOpenDialog(null);
			//��ʾ����
			jfc.setVisible(true);
			
			//�õ��û�ѡ���ļ���ȫ·��
			String lrcpath=jfc.getSelectedFile().getAbsolutePath();
			jtf_songlrc.setText("");
			jtf_songlrc.setText(lrcpath);	
		}
		if(e.getSource()==jb_sure)
		{
			//�������ݿ���в���
			/*
			 * ȷ���ύ,��Ӹ�����Ϣ ��ûỰ�����ı����е���Ϣ
			 */
			String songname = this.getJtf_songname().getText();
			String songaddress = this.getJtf_songaddress().getText();
			String singername= this.getJtf_singername().getText();
			String songlist=listName;
			String songlrc = this.getJtf_songlrc().getText();
			String mess = null;// �����洢�������Ϣ
			if (songname == null || " ".equals(songname)) {
				mess = "������Ϊ�գ�";
				JOptionPane.showMessageDialog(this, mess);
				
			}else {
				//	�������ݿ⣬���õ��ĸ�����Ϣ���͵����ݿⲢ����
				MusicModel mm=new MusicModel();
				mm.AddM(this);
				
			}
			
		}
		if(e.getSource()==jb_quit)
			{
				this.dispose();
			}
	}
	
	//һ��set��get�ķ��������ڵõ���ӵĸ�����Ϣ
	public JTextField getJtf_songlrc() {
		return jtf_songlrc;
	}
	public void setJtf_songlrc(JTextField jtf_songlrc) {
		this.jtf_songlrc = jtf_songlrc;
	}
	public JTextField getJtf_songname() {
		return jtf_songname;
	}
	public void setJtf_songname(JTextField jtf_songname) {
		this.jtf_songname = jtf_songname;
	}
	public JTextField getJtf_songaddress() {
		return jtf_songaddress;
	}
	public void setJtf_songaddress(JTextField jtf_songaddress) {
		this.jtf_songaddress = jtf_songaddress;
	}
	public JTextField getJtf_singername() {
		return jtf_singername;
	}
	public void setJtf_singername(JTextField jtf_singername) {
		this.jtf_singername = jtf_singername;
	}

	public JComboBox getJcb_songList() {
		return jcb_songList;
	}
	public void setJcb_songList(JComboBox jcb_songList) {
		this.jcb_songList = jcb_songList;
	}
	
	public String getListName() {
		return listName;
	}
	public void setListName(String listName) {
		this.listName = listName;
	}
	
}

//�����ļ�ѡ�������
class JAVAFiler extends FileFilter {

	private static final long serialVersionUID = 1L;
	String ext;

	public JAVAFiler(String ext) {
		this.ext = ext;
	}

	public boolean accept(File file) {
		if (file.isDirectory())
			return true;

		String fileName = file.getName();
		int index = fileName.lastIndexOf('.');

		if (index > 0 && index < fileName.length() - 1) {
			String extension = fileName.substring(index + 1).toLowerCase();
			if (ext.toLowerCase().indexOf(extension.toLowerCase()) >= 0)
				return true;
		}
		return false;
	}

	@Override
	public String getDescription() {
		return "ý��(*.mp3,*.AU��*.AVI��*.MIDI��*.MPEG��,*.wav)";
	}
}
	
	
