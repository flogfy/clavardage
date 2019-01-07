package COO4IR;
import java.io.*;
import java.net.*;

public class serveurtest{
	
	public serveurtest() {
	}
	public static void main(String[]  args) throws InterruptedException, ClassNotFoundException {
	
			utilisateur user2=new utilisateur("pepita","lulu");
			System.out.println(user2.getListeadressesconnectes());
		//	user2.getListepseudoconnectes().add(user2.getPseudo());
			Thread udp=new MyThreadudp(user2);
			udp.start();
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
				Thread t = new MyThread(lien,user2,"pseudo");
				t.start();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		
		//Faire un tableau de thread et un tableau de socket pour ne pas risque d'ecraser les donnees precedentes apres un tour de boucle
		
	}
}

