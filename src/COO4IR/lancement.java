
package COO4IR;

import java.io.IOException;
///L'utilisateur lance ceci pour demarrer
public class lancement {
		
	public static void main(String[] args) throws IOException {
		
		fenetreconnexion fenconnexion= new fenetreconnexion();//On ouvre une fenetre où l'utilisateur entre login / MDP / pseudo
		while(!fenconnexion.isEtablissementconnexion()) {//On attend qu'il est fini, on verifie frequemment si c'est bon
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//On cree un utilisateur en fonction de ce qui a été rentré dans la fenetre de connexion
		utilisateur user=new utilisateur(fenconnexion.getTlogin(),fenconnexion.getTpseudo());
		
		System.out.println("demarrertcpudp");
		
/*		
A decommenter pour lancer le logiciel en mode réel, c'est a dire avec plusieurs ordinateurs sous la main, situation reelle
Si on decommente on peut simuler des conversations sur un meme pc.  

		
		//user.demarrerserveurudptcp();

*/		
		
		System.out.println("finirtcpudp");
		
		
		//On signale notre connexion aux autres et on recupere la liste des autres utilisateurs connectes
		
		user.connexion(user.getLogin());
		int i=0;
		while((user.getListeadressesconnectes().isEmpty())&&(i!=100))
		{
			System.out.println(i);
			try {
				Thread.sleep(250);
				i=i+1;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		System.out.println(user.getListeloginconnectes());
		System.out.println(user.getListeadressesconnectes());
		
		//On affiche une fenetre avec la liste des utilisateurs connectés où l'on peut choisir à qui parler
		
		fenetrelisteusers fenetreliste=new fenetrelisteusers(user,user.getListeloginconnectes(),user.getListeadressesconnectes());
		
		
			
			
		}
	}

