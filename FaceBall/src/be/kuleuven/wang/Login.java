package be.kuleuven.wang;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.swing.JTextField;
import javax.swing.SwingConstants;

import netUtil.wang.MailUtil;

import java.awt.Color;

public class Login extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JButton log;
	private JButton sign;
	private JTextField userField;
	private JTextField mailField;
	private JLabel userLabel;
	private JLabel mailLabel;
	private JLabel response;
	private JButton findUser;
	private JPanel btPanel;
	private Map<String, String> rs;

	private static int nrOfUsers;
	protected static String user;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Login();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Login() {
		rs = AccessDB.getAllUsers();
		nrOfUsers = rs.size();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setSize(400, 260);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);

		userLabel = new JLabel("User name");
		userLabel.setHorizontalAlignment(SwingConstants.CENTER);
		userLabel.setBounds(66, 64, 94, 23);
		contentPane.add(userLabel);

		mailLabel = new JLabel("Email");
		mailLabel.setHorizontalAlignment(SwingConstants.CENTER);
		mailLabel.setBounds(53, 97, 94, 23);
		contentPane.add(mailLabel);

		userField = new JTextField(50);
		userField.setBounds(166, 64, 150, 23);
		contentPane.add(userField);
		userField.setColumns(10);

		mailField = new JTextField(50);
		mailField.setBounds(166, 97, 150, 23);
		contentPane.add(mailField);
		mailField.setColumns(10);

		response = new JLabel("");
		response.setForeground(Color.RED);
		response.setBounds(83, 43, 287, 15);  //140
		contentPane.add(response);

		log = new JButton("login");
		log.addActionListener(this);

		sign = new JButton("sign up");
		sign.addActionListener(this);

		findUser = new JButton();
		ImageIcon icon = new ImageIcon("images/mail_send.png");
		findUser.setIcon(icon);
		findUser.addActionListener(this);
		findUser.setToolTipText("Click me to find your username, but first please fill the right mail address!");

		btPanel = new JPanel();
		btPanel.setBounds(73, 135, 250, 45);
		btPanel.add(log);
		btPanel.add(sign);
		// btPanel.add(findUser);
		contentPane.add(btPanel);

	}

	private boolean validateMail(String mail) {
		//System.out.println(""+mail.indexOf("@")+mail.lastIndexOf("."));
		return 1 < mail.indexOf("@")
				&& mail.indexOf("@") < mail.lastIndexOf(".")
				&& mail.lastIndexOf(".") < mail.length() - 2;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String user = userField.getText().trim();
		String mail = mailField.getText().trim();
		//System.out.println(mail);
		if (validateMail(mail)) {			
			boolean logAvailable = false;
			boolean signAvailable = true;
			if (e.getSource() == log) {
				for (Map.Entry<String, String> entry : rs.entrySet()) {
					if (user.equals(entry.getKey()) && mail.equals(entry.getValue())) {
						logAvailable = true;
						break;
					}
				}
				if (logAvailable) {
					System.out.println("login");
					Login.user = user;
					System.out.println(Login.user);
					new GameFrame();
					setVisible(false);
				} else {
					response.setText("Can't login with this user name, please check it!");
					btPanel.add(findUser);
				}
			}

			if (e.getSource() == sign) {
				for (Map.Entry<String, String> entry : rs.entrySet()) {
					if (entry.getKey().equals(user)) {
						response.setText("User already exists!");
						signAvailable = false;
						break;
					}
					if (entry.getValue().equals(mail)) {
						response.setText("Email already exists!");
						signAvailable = false;
						break;
					}
				}

				if (signAvailable) {
					Login.user = user;
					nrOfUsers++;
					System.out.println(Login.user);
					AccessDB.addUser(user, mail);
					new GameFrame();
					setVisible(false);
				}
			}

			if (e.getSource() == findUser) {
				int it = 0;
				setCursor(new Cursor(Cursor.WAIT_CURSOR));				
				for (Map.Entry<String, String> entry : rs.entrySet()) {
					it++;
					if (entry.getValue().equals(mail)) {
						String message = "Thanks for your register to the FaceBall game, "
								+ "your user name is "
								+ entry.getKey()
								+ ". Enjoy your game!";
						MailUtil.sendMail(message, mail);
						break;
					}
				}
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));				
				if (it >= nrOfUsers) {
					JOptionPane.showMessageDialog(null,
							"This mail address doesn't exist in the database. Please check it!");
				}else{
					JOptionPane.showMessageDialog(null, 
							"A mail address has been sent to your mailbox, please check it!");
				}
			}
		} else {
			response.setText("Invalid mail address!");
		}
	}
}
