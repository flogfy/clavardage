package COO4IR;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class MyThread extends Thread {
	
	private Socket socket;
	private utilisateur user;
	
	public MyThread(Socket socketlocal,utilisateur user2) {
		this.socket=socketlocal;
		this.user=user2;
	}
	public void run() {
		//On envoie tout d'abord un message pour confirmer au client que l'on a ouvert une conversation
		//et comme Ã§a le client sait sur quel socket il peut parler au serveur
		message messageaenvoyer=new message(6,13,"",null);//type texte
		user.envoyermessage(messageaenvoyer,socket.getLocalAddress(),socket.getInetAddress(),socket);
		
		//On creer une fenetre de conversation
		
		fenetre fenetreconv=new fenetre(user,socket.getLocalAddress(),socket.getInetAddress(),socket,"Nouveau");
		
		// On recupere tout les messages envoyes a ce socket tant qu'il est connecte
		ObjectInputStream entree;
			while(!socket.isClosed()) {
			System.out.println("Convouverte");
			try {
				if(!socket.isClosed())
				{
					//On recupere directement les messages sous forme d'objets messages
					entree = new ObjectInputStream(socket.getInputStream());
					Object input2=entree.readObject();
					message input=(message)input2;
					if(input.getType()==0)
					{
						//Si le type est message texte (normal)
						fenetreconv.affichermessage(input);
					}
				}
					
				}
			 catch (IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("Conversation fermée");
				try {
					socket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					System.out.println("socketdejafermee");
					e1.printStackTrace();
				}
				//e.printStackTrace();
			 }				
		}
		
		
	}
}
