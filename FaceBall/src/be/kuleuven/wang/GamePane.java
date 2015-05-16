package be.kuleuven.wang;

import netUtil.wang.FBUtil;

import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.*;
import java.util.*;

public class GamePane extends JPanel {
	private static final int FPS = 30;
	private static final int CANVAS_WIDTH = 500;
	private static final int CANVAS_HEIGHT= 400;
	
	private CanvasPanel  canvasPanel;
	private ControlPanel controlPanel;
	private DisplayPanel displayPanel;	
	
	private String userName;		
	private ArrayList<Ball> balls;
	private double areaRatio;
	private int score;
	private int nrOfInitialBalls,minute, second,life;
	private boolean paused; // Flag for pause/resume control
	private boolean win,isStarted, isSetted;

	private Timer timing = new Timer(1000, new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (second > 0)
				second--;
			else {
				second = 59;
				minute--;
			}
			if (minute == 0 && second == 0) {
				gameOver();
			}
		}
	});

	public GamePane() {
		setCursor(new Cursor(Cursor.WAIT_CURSOR));
		userName = Login.user;
		//System.out.println(userName);
		balls = new ArrayList<Ball>();
		reset();		

		canvasPanel = new CanvasPanel();
		controlPanel = new ControlPanel();
		displayPanel = new DisplayPanel();		
		
		this.setLayout(new BorderLayout());
		this.add(displayPanel, BorderLayout.NORTH);
		this.add(controlPanel, BorderLayout.SOUTH);
		this.add(canvasPanel, BorderLayout.CENTER);

		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		//gameStart();		
	}

	private void reset() {
		if(!isSetted)  ModelSetting.setDefault();					
		nrOfInitialBalls = ModelSetting.nrOfInitialBalls;
		minute=ModelSetting.minute;
		second=ModelSetting.second;
		System.out.println("setting: "+nrOfInitialBalls+minute+second+ModelSetting.velocity);
		
		paused=true;
		life=5;
		score=0;
		areaRatio=0;
		win=false;

		while (balls.size() < nrOfInitialBalls) {
			Ball ball = new Ball(CANVAS_WIDTH, CANVAS_HEIGHT,ModelSetting.velocity);
			if (!ball.ballCollisionCheck(balls)) {
				balls.add(ball);
				System.out.println(balls.size()+" "+ball.getXVelocity()+" "+ball.getYVelocity());
			} else
				Ball.decrementColor();			
		}		
	}

	public void gameStart() {
		Thread gameThread = new Thread() {
			public void run() {
				while (true) { // Execute one update step
					if (!paused) {
						long beginTimeMillis, timeTakenMillis, timeLeftMillis;
						beginTimeMillis = System.currentTimeMillis();

						goMoving();
						repaint(); // Callback paintComponent()

						// Provide the necessary delay to meet the target rate
						timeTakenMillis = System.currentTimeMillis() - beginTimeMillis;
						timeLeftMillis = 1000L / FPS - timeTakenMillis;
						if (timeLeftMillis < 5)
							timeLeftMillis = 5; // Set a minimum
						try {
							Thread.sleep(timeLeftMillis); // milliseconds
						} catch (InterruptedException ex) {
						}
					}
				}
			}
		};
		gameThread.start();
	}

	public boolean findBall(int x, int y) {
		for (Ball ball : balls) {
			int dx = ball.getXOrigin() - x;
			int dy = ball.getYOrigin() - y;
			double ds = Math.pow(dx, 2) + Math.pow(dy, 2);
			if (ds <= Math.pow(ball.getRadius(), 2))
				return true;
		}
		return false;
	}

	public void goMoving() {
		for (Ball ball : balls) {
			ball.moveAround();
		}
		Ball.ballCollisionResponse(balls);
	}	

	public String getTime() {
		String m, s;
		if (second < 10)
			s = "0" + second;
		else
			s = "" + second;
		if (minute < 10)
			m = "0" + minute;
		else
			m = "" + minute;
		return m + ":" + s;
	}

	private void calculeteScore() {
		if(!isSetted)  ModelSetting.setDefault();
		int time = ( ModelSetting.minute - minute ) * 60 + ( ModelSetting.second - second );
		System.out.println( score +" "+ ModelSetting.scoreBonus);

		if(win) {
			float factor;
			if(time<30) factor = 2f;
			else if(time<45) factor = 1.5f;
			else if(time<60) factor = 1.3f;
			else factor = 1.1f;	
			score = (int) (score * factor + ModelSetting.scoreBonus + 100 * life ) ;
		}else{
			if(balls.size()>this.nrOfInitialBalls) 
				score += 0.7 * ModelSetting.scoreBonus;
			else score=0;	
		}	
	}
	
	public void updateRatio() {
        double threshArea = 0.5 * CANVAS_WIDTH * CANVAS_HEIGHT - Math.PI * nrOfInitialBalls * 625;
		areaRatio += Math.pow(balls.get(balls.size()-1).getRadius(),2) * Math.PI / threshArea;
				
		if (areaRatio >= 0.65) { 			
			win=true;
			gameOver();
		}
	}

	public String getRatio() {
		int r = (int) (areaRatio * 100 / 0.65 );
		if(r>100) r=100;		
		return r + "%";
	}
	
	public void gameOver() {
		repaint();
		paused = true; // stop animation
		timing.stop(); // stop counting
		calculeteScore();
		
	    if(win) {			
			int option = JOptionPane.showConfirmDialog(null, "Congratulations! You get " + score +
					". Do you want to save your score? The previous score will be overriden.",
					"Winner", JOptionPane.YES_NO_OPTION);
			if(option==0) {
				AccessDB.setScore(userName, score);
				JOptionPane.showMessageDialog(null, "The score has been saved");
			}
			System.out.print(""+option);
	    }else{	    	
			//JOptionPane.showMessageDialog(null, "Game Over! You get " + score);
			int option = JOptionPane.showConfirmDialog(null, "Game Over! You get " + score + 
					". Do you want to save your score? The previous score will be overriden.",
					"Game Over", JOptionPane.YES_NO_OPTION);
			if(option==0) {
				AccessDB.setScore(userName, score);
				JOptionPane.showMessageDialog(null, "The score has been saved");
			}
			System.out.print(""+option);
	    }	
	    
	    isStarted=false;  //reset the control bit al last because the previous calculation will need them!!
		isSetted=false;
	    controlPanel.start.setEnabled(false);
	    controlPanel.restart.setEnabled(true);
	}
	

	/** The custom drawing panel for the bouncing ball (inner class). */
	class CanvasPanel extends JPanel {

		private static final long serialVersionUID = 1L;
		private int radius = 20; // initial radius of new ball
		private boolean isCollided;
		int a;

		private Timer ballTimer = new Timer(30, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				radius++;
				Ball lastBall = balls.get(balls.size() - 1);
				lastBall.setRadius(radius);
				if (lastBall.borderCollisonCheck()) {
					ballTimer.stop();
					balls.get(balls.size()-1).setVelocity(5, 5);
				}
				for (Ball ball : balls) {
					if (lastBall.ballCollisionCheck(ball)) {
						ballTimer.stop();
						if (ball.getColor().equals(Color.red)) {
							balls.get(balls.size() - 1).setVelocity(5, 5);
						}else{
							balls.remove(lastBall);
							java.awt.Toolkit.getDefaultToolkit().beep();							
							life--;
							isCollided = true;
							if (life == 0) {
								gameOver();
							}
						}						
						break;
					}
				}
				repaint();
			}
		});

		public CanvasPanel() {
			addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					if(isStarted) {
						balls.add(new Ball(e.getX(), e.getY()));
						isCollided = false;
						ballTimer.start();
					}else{
						JOptionPane.showMessageDialog(null, "Please click the Start button to start the game. Enjoy!");
					}
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					if(isStarted) {
						ballTimer.stop();
						radius = 20; // reset initial radius
						if (!isCollided) {
							balls.get(balls.size() - 1).setVelocity(5, 5);
							score += balls.get(balls.size()-1).getRadius()-20;
							updateRatio();
						}
					}				
				}
			});
			addMouseMotionListener(new MouseMotionListener() {
				public void mouseDragged(MouseEvent e) {}				
				public void mouseMoved(MouseEvent e) {
					if (findBall(e.getX(), e.getY()))
						setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					else
						setCursor(new Cursor(Cursor.HAND_CURSOR));
				}
			});
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(Color.black);
			g2d.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
			for (int i = 0; i < balls.size(); i++) {
				balls.get(i).draw(g2d);
			}
		}

		/** Called back to get the preferred size of the component. */
		@Override
		public Dimension getPreferredSize() {
			return (new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
		}
	}

	/** The control panel (inner class). */
	class ControlPanel extends JPanel {

		private static final long serialVersionUID = 1L;
		private boolean isPlaying;
		
		private int currentClip;
		private JButton rank = new JButton("User Rank");
		private JButton start = new JButton("Start");
		private JButton restart = new JButton("Set Up");
		private ArrayList<Clip> clips = new ArrayList<>();	

		public ControlPanel() {	
			final JButton btnSound2 = new JButton();
			add(new BlankArea(CANVAS_WIDTH,10));
			
			start.setFont(new Font("SimSun", Font.PLAIN, 15));
			start.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
                    if(!isStarted) {
                    	gameStart();
                    	if(ModelSetting.sound)
                    		clips.get(0).loop(Clip.LOOP_CONTINUOUSLY);
                    }
            		    
                    isStarted=true;
                    paused=!paused;
                    restart.setText("Restart");
                    restart.setEnabled(false);
                    if(paused) {
                    	start.setText("Start");
                    	timing.stop();
                    }else{
                    	start.setText("Pause");
                    	timing.start();
                    }                                     
				}				
			});
			this.add(start);
			
			restart.setFont(new Font("SimSun", Font.PLAIN, 15));
			restart.addActionListener(new ActionListener() {				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(restart.isEnabled()) {
						isSetted=true;
	                    restart.setText("Restart");
						start.setEnabled(false);
						
						new ModelSetting();
	                    balls.clear();
	                    reset();
	            		System.out.println("set up: "+nrOfInitialBalls+minute+second);
	                    
	            		start.setEnabled(true);
	            		start.setText("Start");
						restart.setEnabled(false);
					}					
				}
				
			});
			this.add(restart);
			
			rank.setFont(new Font("SimSun", Font.PLAIN, 15));
			rank.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					Thread t = new Thread() {						
						public void run() {
							System.out.println("Thread is running");
							AccessDB.createTable();	
						}
					};
					System.out.println("Run thread: "+t);
					t.run();
				}				
			});
			this.add(rank);
			
			SoundEffect.init();
			SoundEffect.volume = SoundEffect.Volume.LOW; // un-mute

			clips.add(SoundEffect.CHRISTMAS.getClip());
			clips.add(SoundEffect.LOST_WON.getClip());
			clips.add(SoundEffect.BRIGHT_ROAD.getClip());

			// Set up UI components
			JButton btnSound1 = new JButton("Last");
			btnSound1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(isStarted) {
						Clip lastClip = clips.get(currentClip);
						if (lastClip.isRunning())
							lastClip.stop();
						currentClip--;
						if (currentClip < 0)
							currentClip = 2;
						clips.get(currentClip).loop(Clip.LOOP_CONTINUOUSLY);
					}					
				}
			});
			this.add(btnSound1);
						
			btnSound2.setText(ModelSetting.sound?"Stop":"Play");
			btnSound2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(isStarted) {
						isPlaying = !isPlaying;
						Clip c = clips.get(currentClip);
						if (isPlaying)
							c.loop(Clip.LOOP_CONTINUOUSLY); // repeat forever
						else {
							if (c.isRunning())
								c.stop();
						}
						btnSound2.setText(isPlaying ? "Stop" : "Play");
					}					
				}
			});
			this.add(btnSound2);
			JButton btnSound3 = new JButton("Next");
			btnSound3.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (isStarted) {
						Clip lastClip = clips.get(currentClip);
						if (lastClip.isRunning())
							lastClip.stop();
						currentClip++;
						if (currentClip > 2)
							currentClip = 0;
						clips.get(currentClip).loop(Clip.LOOP_CONTINUOUSLY);
					}					
				}
			});
			this.add(btnSound3);	
			
			ImageIcon icon = new ImageIcon("images/Facebook.png");
			JLabel fb = new JLabel(icon);
			final JTextField fbField = new JTextField(30);
			fbField.setFont(new Font("SimSun", Font.ITALIC, 16));
			fbField.setBackground(Color.LIGHT_GRAY);
			fbField.setText("post something to Facebook...");
			fbField.addFocusListener(new FocusListener() {
				@Override
				public void focusGained(FocusEvent e) {
					fbField.selectAll();
				}

				@Override
				public void focusLost(FocusEvent e) {
					
				}
			});
			fbField.addKeyListener(new KeyAdapter(){
			    public void keyTyped(KeyEvent e) {
			        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			        	Thread postFB = new Thread() {
			    			public void run() {
			    				FBUtil.post(fbField.getText());
			    				System.out.println("2");
			    			}
			        	};
			        	System.out.println("1");
			        	postFB.start();
			        }
			    }
			});
			
			JButton submit = new JButton("Post");	
			submit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					Thread postFB = new Thread() {
		    			public void run() {
		    				FBUtil.post(fbField.getText());
		    				System.out.println("2");
		    			}
		        	};
		        	System.out.println("1");
		        	postFB.start();
				}				
			});
			JButton publish = new JButton("Create Event");
			publish.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					Thread postFB = new Thread() {
		    			public void run() {
		    				FBUtil.createEvent();
		    				System.out.println("2");
		    			}
		        	};
		        	System.out.println("1");
		        	postFB.start();
				}				
			});
			this.add(fb);
			this.add(fbField);
			this.add(submit);
			this.add(publish);
		}		
		
		@Override
		public Dimension getPreferredSize() {
			return (new Dimension(CANVAS_WIDTH, 100));
		}
	}

	class DisplayPanel extends JPanel {

		private static final long serialVersionUID = 1L;

		public void paintComponent(Graphics g) {
			super.paintComponent(g); // Paint background
			Graphics2D g2d = (Graphics2D) g;

			g2d.setFont(new Font("SimSun", Font.ITALIC, 30));
			g2d.setColor(Color.cyan);
			g2d.drawString(getTime(), 10, 40);
			g2d.setColor(Color.red);
			g2d.drawString(getRatio(), 200, 40);
			
			// draw stars
			Star s = new Star();
			AffineTransform saveTransform = g2d.getTransform();
			GeneralPath starPath = new GeneralPath();
			starPath.moveTo(s.getX()[0], s.getY()[0]);
			for (int i = 1; i < s.getX().length; i++) {
				starPath.lineTo(s.getX()[i], s.getY()[i]);
			}
			starPath.closePath();

			g2d.translate(270, 30);
			for (int i = 1; i <= life; i++) {
				g2d.translate(40, 0);
				g2d.setColor(Color.cyan);
				g2d.fill(starPath);
			}
			g2d.setTransform(saveTransform);
		}

		@Override
		public Dimension getPreferredSize() {
			return (new Dimension(CANVAS_WIDTH, 50));
		}
	}
}
