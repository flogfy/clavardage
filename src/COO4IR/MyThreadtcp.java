package COO4IR;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/*
 * SocketTCP qui va tourner continuellement et accepter toutes les connexions en créeant à chaque fois
 * Un nouveau socket pour chaque nouvelle connexion puis en repassant en accept() 
 *
 */

public class MyThreadtcp extends Thread {
	
	private utilisateur user;
	
	public MyThreadtcp(utilisateur user2) {
		this.user=user2;
	}
	
	public void run() {
	ServerSocket socket = null;
	try {
		socket = new ServerSocket(8200);
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	while(true) {
	try {
	
		Socket lien=socket.accept();
		Thread t = new MyThread(lien,user,"pseudotest");//On cree un Thread, c'est a dire ici une conversation
		t.start();
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
}
}	
