package 섯다;



import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

class Title extends JFrame implements KeyListener,ActionListener {
	JPanel title,loginForm;
	JTextField login;
	JPasswordField pw;
	JButton btlogin,btjoin;
	boolean idck=true ,pwck = true;
	int ck;
//	String money;
	User user;
	Socket socket;	
	ObjectOutputStream oos;
	ObjectInputStream ois;
	
	private static final String SERVER_IP = "39.127.8.189";
	private static final int SERVER_PORT = 7777;
	
	public Title() {
		try {
			socket = new Socket();
			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		setTitle("섯다");
		setSize(1000,1000);
		setResizable(false);

		// 타이틀 전체 화면(패널) 생성
		title = new JPanel() {
			Image bg,logo,loginForm;
			   @Override
			   protected void paintComponent(Graphics g) {
			      super.paintComponent(g);
			      bg = Toolkit.getDefaultToolkit().getImage("img/background.png");
			      logo = Toolkit.getDefaultToolkit().getImage("img/logo.png");
			      loginForm = Toolkit.getDefaultToolkit().getImage("img/loginForm.png");
			      g.drawImage(bg, 0,0,1000,1000, this);
			      g.drawImage(logo,250,-20, this);
			      g.drawImage(loginForm,250,600, this);
			   }
		};
		 
		title.setLayout(null); 
		
		// 로그인 패널 생성
		loginForm = new JPanel();
		loginForm.setSize(900,900);
		loginForm.setLayout(null);
		loginForm.setOpaque(false);
		
		// 로그인, 회원가입 텍스트 생성
		login = new JTextField("아이디를 입력하세요.");
		login.setFont(new Font(null,Font.PLAIN,20));
		login.setBounds(300,612,380,100);
		login.setColumns(30);
		login.setOpaque(false);
		login.setBorder(null);
		login.addKeyListener(this);		
		
		pw = new JPasswordField();
		pw.setBounds(300,690,380,100);
		pw.setColumns(30);
		pw.setOpaque(false);
		pw.setBorder(null);

		btlogin = new JButton(new ImageIcon("img/loginButton.png"));
		btlogin.setBounds(717,632,135,140);
		btlogin.setBorder(null);
		btlogin.addActionListener(this);
		
		btjoin = new JButton(new ImageIcon("img/joinbt.png"));
		btjoin.setBounds(580,780,110,31);
		btjoin.setBorder(null);
		btjoin.addActionListener(this);
			
		loginForm.add(login);
		loginForm.add(pw);
		loginForm.add(btlogin);
		loginForm.add(btjoin);
		
		add(title);
		title.add(loginForm);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		if(idck) {
			login.setText("");
			idck = false;
		}		
	}
	
	public void login() {
//		LogonDBBean dblogin = LogonDBBean.getInstance();
//		dblogin.getConn();
//		dblogin.Login(id, password);
//		ck = dblogin.ck;
//		money = dblogin.money;
		
        try {
        	// id 값 들고오기
    		String id = login.getText();
    		// pw 값 들고오기
    		char[] pass=pw.getPassword();
            String password = new String(pass,0,pass.length);
            
            String request = "login::" + id+"::"+password;
            oos.writeObject(request);
            
			int result = (int)ois.readObject();
			
			if(result == 1) {
				user = (User)ois.readObject();
				System.out.println("아이디:"+user.getUserId());
				dispose();
				new WaitingRoom(user, socket);
			}
			
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
//		if(ck==1) {
//			User user = new User();
//			user.setUserid(id);
//			user.setMoney(money);
//			try {
//				//재람이형 꺼
//				String request = "login::" + id + "::" + password;
//				oos.writeObject(request);		
//				
//				user = (User) ois.readObject();
//				new WaitingRoom(user,socket);
//				dispose();
//				
//			} catch (ClassNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}	
	}
	
	public void Join() {
		new Join();
	}

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==btlogin) {
			login();
		}
		
		if(e.getSource()==btjoin) {
			Join();
		}
		
		
		
	}

	
}
public class Sutda {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Title();
	}

}