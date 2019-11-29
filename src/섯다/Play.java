package 섯다;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import logon.LogonDBBean;

public class Play extends JFrame implements Runnable,ActionListener {
	JPanel GameImg;
	JTextField myMoney,battingMoney;
	Image bg;
	JButton card1,card2,card3,card4,cardAll,call,half,check,bing,die;
	int x1=380,x2=380,x3=380,x4=380,y1=350,y2=350,y3=350,y4=350,click,first=0,chooseCard=-1,batting=0;
	Thread thread;
	User user;
	Socket socket;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	LogonDBBean db = LogonDBBean.getInstance();
	
	Combine card = new Combine();
	
	public Play(User user, Socket socket) {
		this.user = user;
		this.socket = socket;
		try {
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			
			String request = "isStart::";
			oos.writeObject(request);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setTitle("섯다");
		setSize(900,900);
		getContentPane().setLayout(null);
		
		//쓰레드
		thread = new Thread(this);
		thread.start();
		
		// 배경화면
		GameImg = new JPanel() {
			   protected void paintComponent(Graphics g) {
			      super.paintComponent(g);
			      bg = Toolkit.getDefaultToolkit().getImage("img/GameBackGround.png");			      
			      g.drawImage(bg, 0,0,900,900, this);			      			 
			   }
		};
		GameImg.setBounds(0, 0, 900, 900);
		
		// 콜,하프,다이,체크,삥 버튼
		call = new JButton("콜");
		call.addActionListener(this);
		call.setBounds(600,340,97,40);
		half = new JButton("하프");
		half.addActionListener(this);
		half.setBounds(707,340,97,40);
		check = new JButton("체크");
		check.addActionListener(this);
		check.setBounds(600,390,97,40);
		bing = new JButton("삥");
		bing.addActionListener(this);
		bing.setBounds(707,390,97,40);
		die = new JButton("다이");
		die.addActionListener(this);
		die.setBounds(600,440,204,40);
		
		half.setEnabled(false);
		call.setEnabled(false);
		check.setEnabled(false);
		bing.setEnabled(false);
		
		//카드 패
		cardAll =new JButton(new ImageIcon("img/card.png"));
		cardAll.setBounds(380,350,97,117);
		cardAll.addActionListener(this);
		
		card1 = new JButton(new ImageIcon("img/Card.png"));
		card1.addActionListener(this);
		card2 = new JButton(new ImageIcon("img/Card.png"));
		card2.addActionListener(this);
		card3 = new JButton(new ImageIcon("img/Card.png"));
		card3.addActionListener(this);
		card4 = new JButton(new ImageIcon("img/Card.png"));
		card4.addActionListener(this);
		
		//보유 금액, 배팅 금액
		battingMoney = new JTextField("배팅금액: "+batting);
		battingMoney.setBorder(null);
		battingMoney.setHorizontalAlignment(SwingConstants.RIGHT);
		battingMoney.setBounds(330,490,196,30);
		
		myMoney = new JTextField("보유금액: "+user.getMoney()+" ");
		myMoney.setBorder(null);
		myMoney.setHorizontalAlignment(SwingConstants.RIGHT);
		myMoney.setBounds(600, 490, 204, 30);
		
		getContentPane().add(call);
		getContentPane().add(half);
		getContentPane().add(die);
		getContentPane().add(check);
		getContentPane().add(bing);
		getContentPane().add(myMoney);
		getContentPane().add(battingMoney);		
		getContentPane().add(cardAll);
		getContentPane().add(card1);
		getContentPane().add(card2);
		getContentPane().add(card3);
		getContentPane().add(card4);
		getContentPane().add(GameImg);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	public void winner() {
		JOptionPane.showMessageDialog(null, "승리하셨습니다");
		
	}
	public void loser() {
		JOptionPane.showMessageDialog(null, "패배하셨습니다");
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while(true) {
				try {
					String response = (String) ois.readObject();
					String[] tokens = response.split("::");
					
					if ("start".contentEquals(tokens[0])) {						
						// 카드 움직임
						int ck=0;
						while(true) {					
							thread.sleep(15);
							if(ck==0) {
								card1.setBounds(x1,y1,97,117);
								y1 = y1-5;
								x1 = x1-1;
								if(y1<100) {
									ck++;
								}
							} else if(ck==1) {
								card3.setBounds(x3,y3,97,117);
								y3 = y3+5;
								x3 = x3-1;
								if(y3>600) {
									ck++;
								}
							} else if(ck==2) {
								card2.setBounds(x2,y2,97,117);
								y2 = y2-5;
								x2 = x2+1;
								if(y2<100) {
									ck++;
								}
							} else if (ck==3) {
								card4.setBounds(x4,y4,97,117);
								y4 = y4+5;
								x4 = x4+1;
								if(y4>600) {
									break;
								}
							}
						}
					} else if("isYourTurn".contentEquals(tokens[0])) {						
						if (user.getUserId().contentEquals(tokens[1])) {
							half.setEnabled(true);
							call.setEnabled(true);
							check.setEnabled(true);
							bing.setEnabled(true);
						} else {					
							half.setEnabled(false);
							call.setEnabled(false);
							check.setEnabled(false);
							bing.setEnabled(false);
						}
					} else if("openCard".contentEquals(tokens[0])) {
						if(user.getUserId().contentEquals(tokens[1])) {
							card1.setIcon(new ImageIcon("img/"+tokens[2]+".jpg"));
//							card3.setIcon(new ImageIcon("img/"+tokens[4]+".jpg"));
						} else {
							card1.setIcon(new ImageIcon("img/"+tokens[4]+".jpg"));
//							card3.setIcon(new ImageIcon("img/"+tokens[2]+".jpg"));
						}						
					} else if("firstCard".contentEquals(tokens[0])) {
						if(user.getUserId().contentEquals(tokens[1])) {
							card3.setIcon(new ImageIcon("img/"+tokens[4]+".jpg"));
						} else {
							card3.setIcon(new ImageIcon("img/"+tokens[2]+".jpg"));
						}
					} else if("lastOpen".contentEquals(tokens[0])) {
						if(user.getUserId().contentEquals(tokens[1])) {
							card2.setIcon(new ImageIcon("img/"+tokens[3]+".jpg"));
							card4.setIcon(new ImageIcon("img/"+tokens[5]+".jpg"));
						} else {
							card2.setIcon(new ImageIcon("img/"+tokens[5]+".jpg"));
							card4.setIcon(new ImageIcon("img/"+tokens[3]+".jpg"));
						}
					} else if ("result".contentEquals(tokens[0])) {
						if(user.getUserId().contentEquals(tokens[1])) {
							if(Integer.parseInt(tokens[3])>Integer.parseInt(tokens[2])) {
								winner();
								System.out.println(tokens[1]);
//								db.Winner(tokens[1], batting);
							} else {
								loser();
								System.out.println(tokens[1]);
//								db.Loser(tokens[1], batting);
							}
						} else {
							if(Integer.parseInt(tokens[3])>Integer.parseInt(tokens[2])) {
								loser();
								System.out.println(tokens[1]);
//								db.Loser(tokens[1], batting);
							} else {
								System.out.println(tokens[1]);
								winner();
//								db.Winner(tokens[1], batting);
							}
						}
					} else if ("batting".contentEquals(tokens[0])) {
						battingMoney.setText("배팅금액: "+tokens[1]);
						batting = Integer.parseInt(tokens[1]);
					}
					else {
						break;
					}
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// 쓰레드 실행
		
		// 배팅 - 콜 하프 체크 삥
		if(e.getSource()==call) {
			try {
				String request = "turnEnd::call";
				oos.writeObject(request);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if(e.getSource()==half) {
			try {
				String request = "turnEnd::half";
				oos.writeObject(request);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if(e.getSource()==check) {
			try {
				String request = "turnEnd::check";
				oos.writeObject(request);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
		} else if(e.getSource()==bing) {
			try {
				String request = "turnEnd::bing";
				oos.writeObject(request);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		
		// 배팅 -다이
		if(e.getSource()==die) {
			if(Integer.parseInt(user.money) > 9999) {
//				System.out.println("Play");
//				dispose();
//				new Play();
			} else if(Integer.parseInt(user.money) <= 9999) {
				System.out.println("Room");
				dispose();
//				new WaitingRoom();
			}
		}		
		
	}	
}