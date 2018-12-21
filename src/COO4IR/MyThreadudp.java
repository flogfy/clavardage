package COO4IR;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.Charset;

public class MyThreadudp extends Thread {

		private utilisateur user;
	
		public MyThreadudp(utilisateur user2) {
			this.user=user2;
		}
		public void run() {
			try {
				//Le socket UDP utilisé pour recevoir des trames UDP envoyé par des utilisateurs qui viennent de se connecter
				//et rajouter leur infos et éventuellement leur renvoyer les infos que l'on connait si on est l'avant
				//derniere personne connectee
				
				DatagramSocket socketudp=new DatagramSocket(1500);
				byte[] buffer=new byte[512];
				DatagramPacket paquetudp=new DatagramPacket(buffer,buffer.length);
				while(true) {
					
					try {
						socketudp.receive(paquetudp);
						byte[] loginbyte=paquetudp.getData();
						int longueur=paquetudp.getLength();
						String login=new String(loginbyte,0,longueur,Charset.defaultCharset());
						user.getListeloginconnectes().add(login);
						user.getListeadressesconnectes().add(paquetudp.getAddress());
						System.out.println(user.getListeloginconnectes());
						System.out.println(user.getListeadressesconnectes());
						
						
						if(user.getListeloginconnectes().size()>=2)
						{
						
							
							if(user.getListeloginconnectes().get(user.getListeloginconnectes().size()-2).equals(user.getLogin())) {//Si nous sommes l'avant dernière personne as'être connectée
						//on envoie la liste des login et adresses connectes on ouvre donc un nouveau socket TCP ce coup-ci car 
								//on veut être sûr que le message arrive et on s'adresse qu'a une personne
							Socket socketsource=new Socket();
							InetAddress adressedestination=paquetudp.getAddress();
							Thread.sleep(500);
							socketsource.connect(new InetSocketAddress(adressedestination,1501));
							ObjectOutputStream sortie = new ObjectOutputStream(socketsource.getOutputStream());
							sortie.flush();
							sortie.writeObject(user.getListeloginconnectes());
							sortie.flush();
							sortie.writeObject(user.getListeadressesconnectes());
							Thread.sleep(500);
							socketsource.close();
							}
						}
						} catch (IOException | InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
			}
		}catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
}
}