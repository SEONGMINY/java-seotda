package 섯다;

public class User {
	String userId;
	String money;
	GameRoom gameRoom;
	
	public User() {
	}
	
	public User(String userId,String money) {
		super();
		this.userId = userId;
		this.money = money;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public GameRoom getGameRoom() {
		return gameRoom;
	}

	public void setGameRoom(GameRoom gameRoom) {
		this.gameRoom = gameRoom;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}
}
