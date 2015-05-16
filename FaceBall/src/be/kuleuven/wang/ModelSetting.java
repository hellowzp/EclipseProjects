package be.kuleuven.wang;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class ModelSetting extends JDialog{
	protected JPanel contentPane;
	protected static int minute, second, nrOfInitialBalls;
	protected static int velocity, scoreBonus;
	protected static boolean sound;

	public ModelSetting() {
		setCursor(new Cursor(Cursor.WAIT_CURSOR));
		setTitle("Game Settings");
		setSize(400,250);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		this.setAlwaysOnTop(true);
		this.setAutoRequestFocus(true);
		this.setModalityType(DEFAULT_MODALITY_TYPE);
		
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel settingPanel = new JPanel();
		//settingPanel.setPreferredSize(new Dimension(400,200));
		settingPanel.setBounds(30, 10, 320, 145);
		settingPanel.setLayout(null);
		settingPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLoweredBevelBorder(),
                BorderFactory.createEmptyBorder(10,10,10,10)));
		
		JLabel lblVelocity = new JLabel("Velocity");
		lblVelocity.setBounds(10, 53, 72, 23);
		lblVelocity.setForeground(Color.BLUE);
		lblVelocity.setFont(new Font("SimSun", Font.ITALIC, 16));
		settingPanel.add(lblVelocity);
		
		final JSlider slider = new JSlider(10,20,15);
		slider.setBounds(90, 45, 200, 46);
		slider.setMajorTickSpacing(1);
		//slider.setPaintLabels(true);
		//slider.setPaintTicks(true);
		settingPanel.add(slider);
		
		JLabel lblInitialBalls = new JLabel("Initial balls");
		lblInitialBalls.setFont(new Font("SimSun", Font.ITALIC, 16));
		lblInitialBalls.setForeground(Color.BLUE);
		lblInitialBalls.setBounds(10, 12, 116, 23);
		settingPanel.add(lblInitialBalls);
		
		Integer[] items = {6,7,8,9,10}; 
		final JComboBox comBox = new JComboBox(items);
//		comBox.addItemListener(new ItemListener() {
//			@Override
//			public void itemStateChanged(ItemEvent e) {
//				JComboBox cb = (JComboBox)e.getSource();
//				nrOfInitialBalls = (int) cb.getSelectedItem();
//				scoreBonus += nrOfInitialBalls * 100 - 600;
//				System.out.println(""+nrOfInitialBalls);				
//			}
//		});
		comBox.setSelectedIndex(1);
		comBox.setBounds(124, 14, 54, 21);
		settingPanel.add(comBox);
		
		final JCheckBox chckbxSound = new JCheckBox("Sound",true);
		chckbxSound.setForeground(Color.blue);
		chckbxSound.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				JCheckBox cb = (JCheckBox) e.getSource();
				sound = cb.isSelected();
			}
		});
		chckbxSound.setForeground(Color.BLUE);
		chckbxSound.setFont(new Font("SimSun", Font.PLAIN, 16));
		chckbxSound.setBounds(226, 11, 72, 23);
		chckbxSound.setHorizontalTextPosition(SwingConstants.LEFT);
		settingPanel.add(chckbxSound);	
		
		JLabel lblTime = new JLabel("Time");
		lblTime.setForeground(Color.BLUE);
		lblTime.setFont(new Font("SimSun", Font.ITALIC, 16));
		lblTime.setBounds(10, 99, 54, 15);
		settingPanel.add(lblTime);
		
		JLabel lblMinute = new JLabel("Minute");
		lblMinute.setBounds(39, 124, 43, 15);
		settingPanel.add(lblMinute);
		
		final JTextField minField;
		minField = new JTextField();
		minField.setText("1");
		minField.setBounds(86, 121, 66, 21);
		settingPanel.add(minField);
		minField.setColumns(10);
		
		JLabel lblSecond = new JLabel("Second");
		lblSecond.setBounds(189, 124, 54, 15);
		settingPanel.add(lblSecond);
		
		final JTextField secField;
		secField = new JTextField();
		secField.setText("30");
		secField.setBounds(236, 121, 66, 21);
		settingPanel.add(secField);
		secField.setColumns(10);				
		
		JButton btnOk = new JButton("OK");
		btnOk.setBounds(93, 168, 75, 23);
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				velocity = (int) slider.getValue();
				nrOfInitialBalls = (int) comBox.getSelectedItem();
				minute = Integer.parseInt(minField.getText());
				second = Integer.parseInt(secField.getText());
				sound = chckbxSound.isSelected();
				scoreBonus += Math.max(0, nrOfInitialBalls * 50 - 400 )  + 10 * velocity - 100;				
				System.out.println(""+scoreBonus+" "+nrOfInitialBalls+velocity+minute+second);
				setVisible(false);
				dispose();
			}
		});
		
		JButton btnDefault = new JButton("Default");
		btnDefault.setBounds(195, 168, 75, 23);
		btnDefault.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(""+velocity);
				setDefault();
				setVisible(false);
				dispose();
			}
		});
				
		contentPane.add(settingPanel);
		contentPane.add(btnOk);
		contentPane.add(btnDefault);
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));  //must restore default cursor before set to visible
		setVisible(true);
		
	}
	
	public static void setDefault() {
		minute=1;
		second=30;
		nrOfInitialBalls=7;
		velocity=15;
		scoreBonus=0;
		sound=true;
		System.out.println("default setting: "+nrOfInitialBalls+minute+second+velocity);

	}
	
	public static void main(String[] args) {
        new ModelSetting();
	}

}
