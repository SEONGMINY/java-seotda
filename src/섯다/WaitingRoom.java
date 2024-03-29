package 섯다;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.ScrollPane;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;

public class WaitingRoom extends JFrame implements ActionListener{
	JPanel waitLogo,userForm;
	JLabel userName,money;
	JButton make,in,AI;
	JTable waitRoom;
	JList<String> list;
	DefaultListModel<String> model;
	User user;
	Socket socket;
	String roomName;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	ArrayList<GameRoom> rooms;
	
	public WaitingRoom(User user, Socket socket) {
		try {
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.socket = socket;
		this.user = user;
		
		setTitle("섯다");
		setSize(1100,850);
		setResizable(false);

		// 전체 화면(패널) 생성
		waitLogo = new JPanel() {
			Image logo,room,userform;
			   @Override
			   protected void paintComponent(Graphics g) {
			      super.paintComponent(g);
			      logo = Toolkit.getDefaultToolkit().getImage("img/logo.png");
			      room = Toolkit.getDefaultToolkit().getImage("img/waitroom.jpg");
			      userform = Toolkit.getDefaultToolkit().getImage("img/userform2.png");
			      g.drawImage(room,0,0,1720,872,this);
			      g.drawImage(logo,0,-15,222,192, this);
			      g.drawImage(userform,570,400,491,390, this);
			   }
		};
		// 앱솔루트
		waitLogo.setLayout(null);
		
		add(waitLogo);		
		
		// 유저 정보 패널 생성
		userForm = new JPanel();
		userForm.setBounds(590, 541, 407, 222);
		userForm.setOpaque(false);
		userForm.setBorder(null);
		waitLogo.add(userForm);
		userForm.setLayout(null);
		
		// 방 생성,입장,AI 버튼
		make = new JButton(new ImageIcon("img/make.png"));
		make.setBounds(650,723,110,29);
		make.addActionListener(this);
		waitLogo.add(make);
		
//		in = new JButton(new ImageIcon("img/in.png"));
//		in.setBounds(760,723,110,29);
//		waitLogo.add(in);
		
		AI = new JButton(new ImageIcon("img/AI.png"));
		AI.setBounds(870,723,110,29);
		AI.addActionListener(this);
		waitLogo.add(AI);
		
		//보유 금액
		money = new JLabel(user.getMoney());
		money.setFont(new Font("맑은 고딕", Font.PLAIN, 24));
		money.setBounds(12, 129, 130, 21);
		userForm.add(money);
		
		//유저 이름
		userName = new JLabel(user.getUserId()+" 님");
		userName.setFont(new Font("맑은 고딕", Font.PLAIN, 24));
		userName.setHorizontalAlignment(SwingConstants.LEFT);
		userName.setBounds(10, 41, 312, 35);
		userForm.add(userName);
		
		// 대기방 리스트
		rooms = new ArrayList<GameRoom>();
		
		model = new DefaultListModel<>();
		ScrollPane scroll = new ScrollPane();
		scroll.setBounds(570, 15, 491, 390);
		refresh();
		list = new JList<String>(model);
		list.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if(e.getClickCount() == 2) {
					int index = list.getSelectedIndex();
					int seq = rooms.get(index).getSeq();
					enterRoom(seq);
				}
			}
		});
		
		scroll.add(list);
		waitLogo.add(scroll);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
		
	}
	
	public void refresh() {
		try {
			String request = "getlist::";
			oos.writeObject(request);
			rooms = (ArrayList<GameRoom>) ois.readObject();
			model.clear();
			
			for(GameRoom r : rooms) {
				model.addElement(r.getrName());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void createRoom(String rName) {
		try {
			String request = "create::" + rName;
			oos.writeObject(request);
			new Play(user,socket);
			dispose();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void enterRoom(int seq) {
		int result = 0;
		try {
			String request = "enter::"+seq;
			oos.writeObject(request);
			result = (Integer) ois.readObject();
			dispose();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(result == 0) {
			new Play(user,socket);
		} else if(result == 1) {
			JOptionPane.showMessageDialog(null, "인원수가 초과 했습니다.");
			refresh();
		} else {
			JOptionPane.showMessageDialog(null, "방이 존재하지 않습니다.");
			refresh();
		}
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==AI) {
			refresh();
		} else if (e.getSource()==make) {
			roomName = JOptionPane.showInputDialog(null, "생성할 방 제목을 입력하세요 ","방 생성",JOptionPane.DEFAULT_OPTION);
			createRoom(roomName);
		}
	}
	

}