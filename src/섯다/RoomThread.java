package 섯다;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class RoomThread extends Thread {
	private Socket socket;
	private User user;
	RoomDAO dao = RoomDAO.getInstance();
	
	public RoomThread(User user, Socket socket) {
		this.socket = socket;
		this.user = user;
	}

	@Override
	public void run() {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

			while (true) {
				System.out.println("listen...");
				String request = (String)ois.readObject();
				
				String[] tokens = request.split("::");
				if ("getlist".contentEquals(tokens[0])) {
					ArrayList<GameRoom> rl = dao.getaRoomList();
					System.out.println(request);
					oos.writeObject(rl);
					System.out.println("loading room list");
				} else if ("create".contentEquals(tokens[0])) {
					user.setGameRoom(dao.createRoom(user, tokens[1], socket));
					System.out.println("room has been created");
					System.out.println("number of rooms : " + dao.getRoomlist().size());
					new GameThread(socket,user).start();
					break;
				} else if ("enter".contentEquals(tokens[0])) {
					int result = dao.enterRoom(user, Integer.parseInt(tokens[1]), socket);
					if (result == 0) {
						for (GameRoom gr : dao.getRoomlist()) {
							if (gr.getSeq() == Integer.parseInt(tokens[1])) {
								user.setGameRoom(gr);
								if (gr.getUsers().size() > 1)
									gr.setStart(true);
								new GameThread(socket,user).start();
							}
						}
					}
					oos.writeObject(result);
					break;
				}
			}
		} catch (SocketException e) {
			System.out.println("[RoomThread]lost connection");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
