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
	
	Combine card = new Combine();
	
	public Play(User user, Socket socket) {
		this.user = user;
		this.socket = socket;
		try {
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
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
		battingMoney = new JTextField("배팅금액: "+user.getMoney());
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

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while(true) {
				try {
					String response = (String) ois.readObject();
					String[] tokens = response.split("::");
					
					if ("start".contentEquals(tokens[0])) {
//						JOptionPane.showMessageDialog(null, "게임 시작.");
					
						
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
//									JOptionPane.showMessageDialog(null, "배팅 해주세요.");
//									String request = "firstBatting";
//									oos.writeObject(request);
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
					} else if ("first".contentEquals(tokens[0])) {
						bing.setEnabled(false);
						check.setEnabled(false);
					} else if("isYourTurn".contentEquals(tokens[0])) {
						System.out.println(user.getUserId()+"/"+tokens[1]);
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
					} else {
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
			
			// 오픈할 카드 선택 
			ImageIcon[] choosecards = {new ImageIcon("img/"+card.cardValue[2]+".jpg"), new ImageIcon("img/"+card.cardValue[3]+".jpg")};
			chooseCard = JOptionPane.showOptionDialog(null, "오픈할를 카드선택 해주세요", "카드 선택", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, choosecards, "");
			
			if(chooseCard==0) {
				card3.setIcon(new ImageIcon("img/"+card.cardValue[2]+".jpg"));
//				card1.setIcon(new ImageIcon("img/"+card.cardValue[0]+".jpg"));
			} else if(chooseCard==1) {
				card4.setIcon(new ImageIcon("img/"+card.cardValue[3]+".jpg"));
//				card1.setIcon(new ImageIcon("img/"+card.cardValue[0]+".jpg"));
			}
			
			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// 쓰레드 실행
		if(e.getSource()==cardAll) {
			try {
				String request = "isStart::";
				oos.writeObject(request);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		// 배팅 - 콜 하프 체크 삥
		if(e.getSource()==call) {
			try {
				click = 1;
				String request = "turnEnd::"+batting;
				oos.writeObject(request);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if(e.getSource()==half) {
			try {
				click = 1;
				batting = batting + (batting/2);
				String request = "turnEnd::"+batting;
				oos.writeObject(request);
				battingMoney.setText("배팅금액: "+batting);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if(e.getSource()==check) {
			try {
				click = 1;
				String request = "turnEnd::"+batting+"::"+"check";
				oos.writeObject(request);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
		} else if(e.getSource()==bing) {
			try {
				click = 1;
				batting = batting+10000;
				battingMoney.setText("배팅금액: "+batting);
				String request = "turnEnd::"+batting+"::"+"bing";
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
		
		// 카드 오픈
//		if (e.getSource()==card1) {
//			card1.setIcon(new ImageIcon("img/"+card.cardValue[0]+".jpg"));
//		} else if (e.getSource()==card2) {
//			card2.setIcon(new ImageIcon("img/"+card.cardValue[1]+".jpg"));
//		} else if(e.getSource()==card3) {
//			card3.setIcon(new ImageIcon("img/"+card.cardValue[2]+".jpg"));
//		} else if(e.getSource()==card4) {
//			card4.setIcon(new ImageIcon("img/"+card.cardValue[3]+".jpg"));
//		}
		
		if(e.getSource()==card3) {
			card3.setIcon(new ImageIcon("img/"+card.cardValue[2]+".jpg"));
		} else if(e.getSource()==card4) {
			card4.setIcon(new ImageIcon("img/"+card.cardValue[3]+".jpg"));
		}
		
		
	}	
}