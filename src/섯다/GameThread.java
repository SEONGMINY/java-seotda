package 섯다;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Map;

import logon.LogonDBBean;

public class GameThread extends Thread {
	private Socket socket = null;
	private RoomDAO dao;
	private LogonDBBean db;
	private User user;
	private GameRoom gr;
	private int[] rolled = new int[5];
	private String[] users = new String[2];
	private int batting;
	private Combine card = new Combine();

	public GameThread(Socket socket, User user) {
		this.socket = socket;
		this.user = user;
		gr = user.getGameRoom();
		dao = RoomDAO.getInstance();
		db = LogonDBBean.getInstance();
	}

	@Override
	public void run() {
		try {
			
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			
			for (GameRoom r : dao.getRoomlist()) {
				if (r.getSeq() == gr.getSeq()) {
					dao.putUser(r, oos);
				}
			}
			
			while (true) {
				String request = (String) ois.readObject();
				String[] tokens = request.split("::");
				if ("isStart".contentEquals(tokens[0])) {
					while (true) {
						if (isStart()) {
							for (int i = 0; i < 2; i++) {
								users[i] = gr.getUsers().get(i).getUserId();
							}
							dao.broadcast(gr, "start::" + users[0] + "::" + users[1]);
							getTurn();
							System.out.println("여기1:"+users[0]);
							break;
//							+card.cardValue[0]+"::"+card.cardValue[1]+"::"+card.cardValue[2]+"::"+card.cardValue[3]
						}
					}
				} else if ("turnEnd".contentEquals(tokens[0])) {
					gr.setTurn(gr.getTurn() + 1);	
					getTurn();
					System.out.println("여기2:"+users[0]);
				} else if ("endGame".contentEquals(tokens[0])) {
					dao.endGame(gr, user);
					user = db.getUser(user.getUserId());
					oos.writeObject(user);
					break;
				} else if ("firstBatting".contentEquals(tokens[0])) {
					dao.broadcast(gr, "first::");
				}
			}
		} catch (SocketException e) {
			System.out.println("[GameThread]lost connection");
			dao.getRoomlist().get(gr.getSeq()).removeUser(user);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public boolean isStart() {
		for (GameRoom r : dao.getRoomlist()) {
			if (r.getSeq() == gr.getSeq()) {
				gr = r;
				System.out.println("돼냐?");
			}
		}
		return gr.isStart();
	}

	public void getTurn() {
		int turn = gr.getTurn();
		int t_user = turn % 2;
		System.out.println("몇번째턴:"+turn+"인덱스가 누구:"+t_user+"이넘 누구:"+users[0]);
		String response = "";
		if (turn == 100) {
			response = "gameSet::" + users[t_user];
			dao.broadcast(gr, response);
		} else {
			response = "isYourTurn::" + users[t_user];
			dao.broadcast(gr, response);
		}
	}
	
//	public void getHand() {
//		String
//	}
	
	public void setBatting(int batting) {
		Map<String, int[]> pre = gr.getBatting();
		gr.setBatting(pre);
	}
	

//	public void setScore(int type, int score) {
//		Map<String, int[]> pre = gr.getScore();
//		pre.get(user.getUserId())[type] = score;
//		gr.setScore(pre);
//	}
}
