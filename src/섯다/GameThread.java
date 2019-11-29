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
	private String[] users = new String[2];
	private Combine card = new Combine();
	private int battingValue = 10000;

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
							break;
							
						}
					}
				} else if ("turnEnd".contentEquals(tokens[0])) {
					gr.setTurn(gr.getTurn() + 1);
					getTurn();
					if ("call".contentEquals(tokens[1])) {
						System.out.println("여긴 콜");
						call();
					} else if ("half".contentEquals(tokens[1])) {
						System.out.println("여긴 하프");
						half();
					} else if ("check".contentEquals(tokens[1])) {
						System.out.println("여긴 체크");
						check();
					} else if ("bing".contentEquals(tokens[1])) {
						System.out.println("여긴 삥");
						bing();
					}
					
				} else if ("endGame".contentEquals(tokens[0])) {
					dao.endGame(gr, user);
					user = db.getUser(user.getUserId());
					oos.writeObject(user);
					break;
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
			}
		}
		return gr.isStart();
	}

	public void getTurn() {
		int turn = gr.getTurn();
		int t_user = turn % 2;
		String response = "";
		if (turn==0) {
			response = "isYourTurn::" + users[t_user];
			dao.broadcast(gr, response);
			firstCard();
		}
		else if (turn == 2) {
			response = "isYourTurn::" + users[t_user];
			dao.broadcast(gr, response);
			openCard();
		}
		else if(turn == 4) {
			response = "isYourTurn::" + users[t_user];
			dao.broadcast(gr, response);
			lastOpen();
		}
		else {
			response = "isYourTurn::" + users[t_user];
			dao.broadcast(gr, response);
		}
	}
	
//	public void getHand() {
//		String
//	}
	public void firstCard() {
		int turn = gr.getTurn();
		int t_user = turn % 2;
		String response = "firstCard::"+users[t_user]+"::"+card.cardValue[0]+"::"+card.cardValue[1]+"::"+card.cardValue[2]+"::"+card.cardValue[3];
		dao.broadcast(gr, response);
	}
	
	public void openCard() {
		int turn = gr.getTurn();
		int t_user = turn % 2;
		String response = "openCard::"+users[t_user]+"::"+card.cardValue[0]+"::"+card.cardValue[1]+"::"+card.cardValue[2]+"::"+card.cardValue[3];
		dao.broadcast(gr, response);
	}
	
	public void lastOpen() {
		int turn = gr.getTurn();
		int t_user = turn % 2;
		String response = "lastOpen::"+users[t_user]+"::"+card.cardValue[0]+"::"+card.cardValue[1]+"::"+card.cardValue[2]+"::"+card.cardValue[3];
		dao.broadcast(gr, response);
		result();
	}
	
	public void result() {
		int turn = gr.getTurn();
		int t_user = turn % 2;
		String response = "result::"+users[t_user]+"::"+card.jockbo1+"::"+card.jockbo2;
		dao.broadcast(gr, response);	
	}
	
	public void call() {
		battingValue += (int)(battingValue * 1.5);
		String response = "batting::" + battingValue;
		dao.broadcast(gr, response);	
	}
	
	public void half() {
		battingValue += battingValue * 2;
		String response = "batting::" + battingValue;
		dao.broadcast(gr, response);	
	}
	
	public void check() {
		String response = "batting::" + battingValue;
		dao.broadcast(gr, response);	
	}
	
	public void bing() {
		battingValue += battingValue + 10000;
		String response = "batting::" + battingValue;
		dao.broadcast(gr, response);		
	}
	
}
