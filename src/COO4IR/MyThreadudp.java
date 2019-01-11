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
				//Il est aussi utilisé pour des changements de pseudos et les deconnexions
				
				DatagramSocket socketudp=new DatagramSocket(1500);
				byte[] buffer=new byte[512];
				DatagramPacket paquetudp=new DatagramPacket(buffer,buffer.length);
				int i=0;//Necessaire  pour la simulation sur un seul PC car sinon on rentre quoi qu'il arrive dans le if car
				//il n'y a que l'adesse 127.0.0.1 dans la liste des adresses
				while(true) {
			
					try {
						i=i+1;
						socketudp.receive(paquetudp);
						if(user.getListeadressesconnectes().contains(paquetudp.getAddress())&&(i!=1)&&(!user.getAdresseip().equals(paquetudp.getAddress()))) {
							byte[] pseudobyte=paquetudp.getData();
							int longueur=paquetudp.getLength();
							String newpseudo=new String(pseudobyte,0,longueur,Charset.defaultCharset());
							int index=user.getListeadressesconnectes().indexOf(paquetudp.getAddress());
							if(newpseudo.equals("deconnexion")){
								user.getListepseudoconnectes().remove(index);
								user.getListepseudoconnectes().remove(index);
								user.getFenetreliste().actualiser();
							}
							else {
							
							user.getListepseudoconnectes().add(index,newpseudo);
							user.getListepseudoconnectes().remove(index+1);
							user.getFenetreliste().actualiser();
							
							}
							
						}
						else
						{
							if((!user.getAdresseip().equals(paquetudp.getAddress()))) {
								//POur ne pas traiter les paquets qu'on a soi-même envoyé en broadcast
								
								
								byte[] pseudobyte=paquetudp.getData();
								int longueur=paquetudp.getLength();
								String pseudo=new String(pseudobyte,0,longueur,Charset.defaultCharset());
								user.getListepseudoconnectes().add(pseudo);
								user.getListeadressesconnectes().add(paquetudp.getAddress());
								System.out.println(user.getListepseudoconnectes());
								System.out.println(user.getListeadressesconnectes());
								user.getFenetreliste().actualiser();
								
								if(user.getListepseudoconnectes().size()>=2)
								{
								
									
									if(user.getListepseudoconnectes().get(user.getListepseudoconnectes().size()-2).equals(user.getPseudo())) {
										//Si nous sommes l'avant dernière personne as'être connectée
								//on envoie la liste des login et adresses connectes on ouvre donc un nouveau socket TCP ce coup-ci car 
										//on veut être sûr que le message arrive et on s'adresse qu'a une personne
									Socket socketsource=new Socket();
									InetAddress adressedestination=paquetudp.getAddress();
									Thread.sleep(500);
									socketsource.connect(new InetSocketAddress(adressedestination,1501));
									ObjectOutputStream sortie = new ObjectOutputStream(socketsource.getOutputStream());
									sortie.flush();
									sortie.writeObject(user.getListepseudoconnectes());
									sortie.flush();
									sortie.writeObject(user.getListeadressesconnectes());
									Thread.sleep(500);
									socketsource.close();
									}
								}
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