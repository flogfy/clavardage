package COO4IR;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;

public class utilisateur {
	private String login;
	private String pseudo;
	private InetAddress adresseip;
	//Les differentes listes avec les infos des users connectes
	private ArrayList<String> listeloginconnectes = new ArrayList<String>(); 
	private ArrayList<InetAddress> listeadressesconnectes = new ArrayList<InetAddress>();
	private ArrayList<String> listepseudoconnectes = new ArrayList<String>();
	//Equivalents des "define" de C pour les differents types de messages
	static int TEXTE=0;
	int DOCUMENT=1;
	int IMAGE=2;
	int PSEUDO=3;
	static int CONNEXION=4;
	int DECONNEXION=5;
	int OUVERTURE=6;
	int id=0;
	
	
	
 public utilisateur(String login, String pseudo) {
	 this.login=login;
	 this.pseudo=pseudo;
	 this.listepseudoconnectes.add(pseudo);
	 
	 /*	On recupere son adresse IP, on suppose ici que celle-ci est sur eth0 */
	 
	 try {
		NetworkInterface interfaces = NetworkInterface.getByName("wlan0");
		Enumeration<InetAddress> iEnum=interfaces.getInetAddresses();
			/* L'adresse IP est le 3eme element */
	      /*  InetAddress inetAddress = iEnum.nextElement(); 
	        inetAddress=iEnum.nextElement();
	        System.out.println(inetAddress.getHostAddress());
	        */
		InetAddress inetAddress=InetAddress.getByName("127.0.0.1");
	        this.adresseip=inetAddress;
		
	} catch (SocketException | UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
 }

 
 
 /* Changement de pseudo, lancé depuis la fenetre des listes userconnectes 
  * On verifie d'abord si le pseudo ne fait pas partie de ceux déjà utilisés
  * On recupère la position de notre pseudo actuelle, on met a sa place le nouveau pseudo voulu, et on supprime l'ancien
  * puis on envoie en UDP à toutes les personnes connectes
  */
 
 public void setPseudo(String pseudochange) {
		id=id+1;
		int i=0;
		int index=listepseudoconnectes.indexOf(pseudo);
			this.pseudo = pseudochange;
			listepseudoconnectes.add(index,pseudo);
			listepseudoconnectes.remove(index+1);
			message pseudochanges = new message(PSEUDO,id,pseudochange,null);
			Socket socketsource=new Socket();
			
			for(i=0;i<listeadressesconnectes.size()-1;i++) {
				try {
					socketsource.connect(new InetSocketAddress(listeadressesconnectes.get(i),8200));
					this.envoyermessage(pseudochanges,this.getAdresseip(),listeadressesconnectes.get(i),socketsource);
					socketsource.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		
}

	public int envoyermessage(message messageaenvoyer,InetAddress adressesource,InetAddress adressedestination,Socket socketsource)
	{
		try {
			
			ObjectOutputStream sortie = new ObjectOutputStream(socketsource.getOutputStream());
			sortie.flush();
			sortie.writeObject(messageaenvoyer);
			return(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return(0);
		}
		
	}
	/////On envoie a tout le monde ( en broadcast via UDP son pseudo pour dire qu'on est connecté )
	//puis on attend la réponse en TCP de l'avant dernier qui s'est connecté qui nous envoie la liste des connectés.

	@SuppressWarnings("unchecked")
	public void connexion(String login2) throws IOException {
		//On supposera que l'administrateur du réseau entrera dans la BDD l'adresse du réseau, l'adresse de Broadcast et le masque du réseau, une fois a l'installation du logiciel
		//car on ne peut pas aisément recuperer ces adresses en java et c'est très rapide de les rentrer une fois en dur
			DatagramSocket dgramconnexion=new DatagramSocket();
			InetAddress adressebroadcast=InetAddress.getByName("127.0.0.1");
			DatagramPacket paquetenvoye=new DatagramPacket(login2.getBytes(),login2.length(),adressebroadcast,1500);
			dgramconnexion.send(paquetenvoye);
			dgramconnexion.close();
			
			
			try {
				ServerSocket socket = new ServerSocket(1501);
				socket.setSoTimeout(1000);
				Socket lien=socket.accept();
				ObjectInputStream entree = new ObjectInputStream(lien.getInputStream());
				Object input=entree.readObject();
				ArrayList<String> logins=(ArrayList<String>) input;
				this.listeloginconnectes=logins;
				input=entree.readObject();
				ArrayList<InetAddress> adressesip=(ArrayList<InetAddress>) input;
				this.listeadressesconnectes=adressesip;
				lien.close();
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				
			}	
	}
	
	//////// A decommenter pour lancer le logiciel en mode réel, c'est a dire avec plusieurs ordinateurs sous la main.
	//Si on commente on peut simuler des conversations sur un meme pc
	
	
	/*
	public void demarrerserveurudptcp() {
		// TODO Auto-generated method stub
		Thread udp=new MyThreadudp(this);
		udp.start();

		System.out.println("terminerdemarrerudp");
		Thread tcp=new MyThreadtcp(this);
		tcp.start();
		System.out.println("terminerdemarrer");
	}
	*/

	
	
	/* GETTERS SETTERS */

public ArrayList<String> getListepseudoconnectes() {
		return listepseudoconnectes;
		}
	
	public ArrayList<InetAddress> getListeadressesconnectes() {
		return listeadressesconnectes;
	}
	
public String getLogin() {
	return login;
}
public ArrayList<String> getListeloginconnectes() {
	return listeloginconnectes;
}

public InetAddress getAdresseip() {
	return adresseip;
}

public String getPseudo() {
	return pseudo;
}











}