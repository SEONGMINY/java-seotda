package 섯다;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
	public static final int PORT = 7777;
	
	public static void main(String[] args) {
		ServerSocket server = null;
		try {
			server = new ServerSocket();
//			String hostAddress = InetAddress.getLocalHost().getHostAddress();
			server.bind(new InetSocketAddress("39.127.8.189", PORT));
//			System.out.println(hostAddress);
			System.out.println("waiting...");
			
			while(true) {
				Socket socket = server.accept();
				System.out.println("connected");
				new LoginThread(socket).start();
			}
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(server!=null&&!server.isClosed()) {
					server.close();
					System.out.println("서버 닫힘");
				}
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}
