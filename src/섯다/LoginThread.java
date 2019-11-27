package 섯다;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;

import logon.LogonDBBean;

public class LoginThread extends Thread {
	private Socket socket=null;
	LogonDBBean db = LogonDBBean.getInstance();
	User user;
	public LoginThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

			while (true) {
				String request = (String)ois.readObject();
				if (request == null) {
					System.out.println("lost connect");
					break;
				}
				String[] tokens = request.split("::");
				if ("login".contentEquals(tokens[0])) {
					int result = db.Login(tokens[1], tokens[2]);
					if (result == 1) {
						user = db.getUser(tokens[1]);
						oos.writeObject(result);
						System.out.println("아이디:"+user.getUserId());
						oos.writeObject(user);
						new RoomThread(user,socket).start();
						break;
					} else {
						oos.writeObject(result);
					}

				} 
			}
		} catch(SocketException e) {
			System.out.println("[LoginThread]lost connection");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
