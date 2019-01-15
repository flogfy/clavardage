package COO4IR;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;

public class MyThread extends Thread {
	
	private Socket socket;
	private utilisateur user;
	private String pseudodest;
	
	public MyThread(Socket socketlocal,utilisateur user2,String pseudodest) {
		this.socket=socketlocal;
		this.user=user2;
		this.pseudodest=pseudodest;
	}
	public void run() {
		
		//On envoie tout d'abord un message pour confirmer au client que l'on a ouvert une conversation
		//et comme ça le client sait sur quel socket il peut parler au serveur
		message messageaenvoyer=new message(3,13,user.getPseudo(),null);//type texte
		user.envoyermessage(messageaenvoyer,socket.getLocalAddress(),socket.getInetAddress(),socket);
		
		//On creer une fenetre de conversation
		
		fenetre fenetreconv=new fenetre(user,socket.getLocalAddress(),socket.getInetAddress(),socket,this.pseudodest);
		InetAddress adressedestinataire=null;
		// On recupere tout les messages envoyes a ce socket tant qu'il est connecte
		ObjectInputStream entree;
			while(!socket.isClosed()) {
			try {
				if(!socket.isClosed())
				{
			
					//On recupere directement les messages sous forme d'objets messages
					entree = new ObjectInputStream(socket.getInputStream());
					Object input2=entree.readObject();
					message input=(message)input2;
					if(input.getType()==3) {
						System.out.println("premieremiseajourpseudo");
						fenetreconv.setPseudodestinataire(input.getContenu());
						fenetreconv.converterFrame.setTitle(input.getContenu());
						this.pseudodest=input.getContenu();
						int index=user.getListepseudoconnectes().indexOf(this.pseudodest);
						adressedestinataire=user.getListeadressesconnectes().get(index);
					}
					if(input.getType()==0)
					{
						
						//Si le type est message texte (normal)
						user.getBdd().addMessage(input.getContenu(), socket.getLocalAddress(), socket.getInetAddress(), input.getDate().toString());
						fenetreconv.affichermessage(input);
					}
					int index=user.getListeadressesconnectes().indexOf(adressedestinataire);
					if(this.pseudodest!=user.getListepseudoconnectes().get(index)) {
						System.out.println("Deuxiememiseajourpseudo");
						this.pseudodest=user.getListepseudoconnectes().get(index);
						fenetreconv.setPseudodestinataire(this.pseudodest);
						fenetreconv.converterFrame.setTitle(this.pseudodest);
					}
				}
					
				}
			 catch (IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
	
				try {
					socket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block

					e1.printStackTrace();
				}
				//e.printStackTrace();
			 }				
		}
		
		
	}
}
