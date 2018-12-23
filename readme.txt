
I)Utilisation du code

1) 

Pour executer le programme sur une seule machine et donc pouvoir simuler des conversations comme si plusieurs utilisateurs
utilisaient le programme, mais sur un seul PC avec des ports differents :

Dans le constructeur de la classe "utilisateur", vérifier que l'adresse IP rentré est 127.0.0.1, faire la même chose dans la fonction connexion() de a classe "utilisateur".

Lancer "serveurtest.java" puis "lancement.java"

Ici, serveurtest.java représente un 2eme utilisateur qui lui s'est déjà connecté et qui a donc déjà un socket UDP ouvert
pour écouter l'arrivée des nouveaux utilisateurs, un socket TCP en accept() pour accepter les éventuels demandes de connexion
et creer alors un nouveau thread qui va créer une conversation et la gérer. Ce socket TCP repasse ensuite en accept() et ainsi de suite.

lancement.java représente vraiment ce que l'utilisateur verra quand il lancera le programme.

2)

Pour executer le programme en version réelle, c'est à dire comme il serait implémenté en entreprise:

Changez les adresses utilisées dans le constructeur de la classe utilisateur et dans la fonction connexion par l'adresse IP public du PC et celle de Broadcast respectivement. Nous avons implémenté une fonction permettant de récupérer ses adresses quand on est sur un PC de l'INSA mais elle n'est pas encore au point pour d'autres cas.

Décommentez la ligne : user.demarrerserveurudptcp(); dans utilisateur
Décommentez la fonction dans "utilisateur" : demarrerserveurudptcp();

La différence avec le programme précédent sera que évidemment il n'y aura pas d'utilisateurs affichés "connectés" sur le réseau si vous lancez ce programme uniquement sur un PC.
Cependant si vous le lancez depuis plusieurs PC il devrait correctement fonctionner. ( nous n'avons pas pu encore le tester sur plusieurs machines, les salles de TP étant pleinement utilisées)


II) Ce que fait et ne fait pas encore le code


Quand on lance le programme, une fenetre s'affiche où l'on peut rentrer son login, son MDP et son pseudo puis valider.
//////A FAIRE ///Les login et mdp entrées sont vérifiés avec la BDD du PC admin et la connexion est validée ou non : pour le moment elle est toujours validée/////
La fenetre se ferme et on crée un utilisateur A ayant les informations entrées dans la fenetre précédente.
On ouvre un socket UDP et un socket TCP puis on envoie un paquet UDP en Broadcast sur le réseau en indiquant notre login avec le socket UDP crée puis
on attend que l'on nous envoie la liste des utilisateurs connectés ( c'est a dire la liste des "adressesIPconnectes" + la liste des loginconnectes")
Ceux qui ont  recu le message de l'utilisateur A enregistre le login et l'adresse de l'user A et regarde chacun si ils sont l'avant dernier
utilisateur connecté. Si c'est le cas alors l'utilisateur concerné ouvre un socket TCP, se connecte à celui de l'user A et lui envoie la liste des users connectés.
L'utilisateur A reçoit la liste et la met à jour.

Une fenetre s'ouvre alors affichant les utilisateurs disponibles sur le réseau.
En cliquant sur un utilisateur on ouvre une conversation avec lui et on peut lui envoyer des messages et en recevoir, correctement dialoguer avec lui.

A Faire : Gérer la fermeture d'une fenetre de conversation = Fermer les sockets associés à la conversation
			Gérer les id des messages, les enregistrer dans une "BDD" local au PC
			Verifier si tout fonctionne bien avec + que 2 utilisateurs sur des PC's différents

			Changement de pseudo pas encore tout a fait au point
			


