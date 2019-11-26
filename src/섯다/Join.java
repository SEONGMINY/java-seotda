package 섯다;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import logon.LogonDBBean;

public class Join extends JFrame implements ActionListener {
	JPanel Image;
	JTextField id;
	JPasswordField pw;
	JButton joinbt;
	
	public Join() {
		 setTitle("회원가입");
		 setSize(472,550);
		 setResizable(false);
		 setLocation(280,250);
		 
		 Image = new JPanel() {
				Image JoinForm;
				   @Override
				   protected void paintComponent(Graphics g) {
				      super.paintComponent(g);
				      JoinForm = Toolkit.getDefaultToolkit().getImage("img/joinForm.png");
				      g.drawImage(JoinForm, 0,0,450,459, this);
				   }
		 };
		 
		 Image.setLayout(null); 
		 
		 id = new JTextField();
		 id.setFont(new Font(null,Font.PLAIN,12));
		 id.setBounds(60,200,300,50);
		 id.setColumns(20);
		 id.setOpaque(false);
		 id.setBorder(null);	
		 Image.add(id);
			
		 pw = new JPasswordField();
		 pw.setBounds(60,285,300,50);
		 pw.setColumns(20);
		 pw.setOpaque(false);
		 pw.setBorder(null);
		 Image.add(pw);
		 
		 joinbt = new JButton(new ImageIcon("img/joinok.png"));
		 joinbt.setBounds(309, 333, 110, 33);
		 joinbt.setBorder(null);
		 joinbt.addActionListener(this);
		 
		 Image.add(joinbt);
		 
		 
		 add(Image);
		 
		 
		 setVisible(true);
	}
	
	public void Ok() {
		// id 값 들고오기
		String uid = id.getText();
		// pw 값 들고오기
		char[] pass=pw.getPassword();
		String password = new String(pass,0,pass.length);
		
		LogonDBBean dblogin = LogonDBBean.getInstance();
		dblogin.getConn();
		int usercheck = dblogin.UserCheck(uid);
		System.out.println(usercheck);
		if(usercheck==-1) {
			dblogin.Join(uid, password);
			JOptionPane.showMessageDialog(null, "회원가입이 완료 되었습니다.");
		} else {
			JOptionPane.showMessageDialog(null, "존재하는 아이디 입니다.");
		}
		
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==joinbt) {
			Ok();
		}
	}
}