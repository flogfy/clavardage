package COO4IR;

import java.net.InetAddress;
import java.net.Socket;

//ArrayList<MyObject> result = new ArrayList<MyObject>();

public class userconnectes {
	private Socket sock;
	private String pseudo;
	private InetAddress ip;

	public userconnectes(Socket sock, String pseudo, InetAddress selfip) {
		this.sock=sock;
		this.pseudo=pseudo;
		this.ip=selfip;
	}

	public Socket getSock() {
		return sock;
	}

	public void setSock(Socket sock) {
		this.sock = sock;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public InetAddress getIp() {
		return ip;
	}

	public void setIp(InetAddress ip) {
		this.ip = ip;
	}

	
	
}
