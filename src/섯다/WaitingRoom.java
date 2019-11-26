package 섯다;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

public class WaitingRoom extends JFrame implements ActionListener{
	JPanel waitLogo,userForm;
	JLabel userName,money;
	JButton make,in,AI;
	JTable waitRoom;
	JList<String> list;
	JScrollPane scroll;
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
//		String col[] = {"방이름","인원"};
//		String row[][] = {{roomName}};
//		System.out.println(roomName);
//		
//		model=new DefaultTableModel(row,col);
//		waitRoom = new JTable(model);
//		scroll = new JScrollPane(waitRoom);
//		scroll.setBounds(570,15,491,390);
//		waitLogo.add(scroll);
		
		rooms = new ArrayList<GameRoom>();
		
		
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==AI) {
			new Play();
			dispose();
		} else if (e.getSource()==make) {
			roomName = JOptionPane.showInputDialog(null, "생성할 방 제목을 입력하세요 ","방 생성",JOptionPane.DEFAULT_OPTION);
		}
	}
	

}