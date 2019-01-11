
package COO4IR;

import java.io.IOException;
///L'utilisateur lance ceci pour demarrer
public class lancement {
		
	public static void main(String[] args) throws IOException, InterruptedException {
		
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
	
		
/*		
A decommenter pour lancer le logiciel en mode réel, c'est a dire avec plusieurs ordinateurs sous la main, situation reelle
Si on decommente on peut simuler des conversations sur un meme pc.  
*/
		
		user.demarrerserveurudptcp();

		
		
		//On signale notre connexion aux autres et on recupere la liste des autres utilisateurs connectes
		
		user.connexion(user.getPseudo());
		int i=0;	
	  while((user.getListeadressesconnectes().isEmpty())&&(i!=100))
	 
		{

			try {
				Thread.sleep(250);
				i=i+1;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		System.out.println(user.getListepseudoconnectes());
		System.out.println(user.getListeadressesconnectes());
		
		//On affiche une fenetre avec la liste des utilisateurs connectés où l'on peut choisir à qui parler
		
		fenetrelisteusers fenetreliste=new fenetrelisteusers(user,user.getListepseudoconnectes(),user.getListeadressesconnectes());
		user.setFenetreliste(fenetreliste);
		
	}
}

