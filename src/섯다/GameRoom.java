package 섯다;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GameRoom implements Serializable{
	private int seq;
	private String rName;
	private ArrayList<User> users= new ArrayList<>();
	private boolean isStart = false;
	private Map<String, int[]> batting = Collections.synchronizedMap(new HashMap<String, int[]>());
	private int[] roll = new int[5];
	private int turn;
	
	public GameRoom(User user, int seq, String rName) {
		users.add(user);
		this.seq=seq;
		this.rName=rName;
	}
	public void gameStart() {
		turn = 0;
		batting.put(users.get(0).getUserId(), new int[13]);
		batting.put(users.get(1).getUserId(), new int[13]);
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getrName() {
		return rName;
	}
	public void setrName(String rName) {
		this.rName = rName;
	}
	public ArrayList<User> getUsers() {
		return users;
	}
	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}
	public void addUser(User user) {
		this.users.add(user);
	}
	public void removeUser(User user) {
		this.users.remove(user);
	}
	public boolean isStart() {
		return isStart;
	}
	public void setStart(boolean isStart) {
		this.isStart = isStart;
	}
	public Map<String, int[]> getBatting() {
		return batting;
	}
	public void setBatting(Map<String, int[]> batting) {
		this.batting = batting;
	}
	public int getTurn() {
		return turn;
	}
	public void setTurn(int turn) {
		this.turn = turn;
	}
	public int[] getRoll() {
		return roll;
	}
	public void setRoll(int[] roll) {
		this.roll = roll;
	}
//	public int getUserScore(int index) {
//		int sum = 0;
//		int[] userscore = batting.get(users.get(index).getUserId());
//		for (int i = 0; i < userscore.length; i++) {
//			sum += userscore[i];
//		}
//		return sum;
//	}
	public int getWinner() {
		int[] scores = new int[2];
		for(int i = 0; i<2; i++) {
			int[] userscore = batting.get(users.get(i).getUserId());
			for(int j=0;j<userscore.length;j++) {
				scores[i] += userscore[j];
			}
		}
		
		if(scores[0]>=scores[1]) {
			return 0;
		} else if(scores[0]>=scores[1]) {
			return 1;
		} else {
			return -1;
		}
	}
	
}
