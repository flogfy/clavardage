
I)Utilisation du code

1)

Pour executer le programme sur une seule machine et donc pouvoir simuler des conversations comme si plusieurs utilisateurs
utilisaient le programme mais sur un seul PC avec des ports differents :

Lancer "serveurtest.java" puis "lancement.java"

Ici, serveurtest.java représente un 2eme utilisateur qui lui s'est déjà connecté et qui a donc déjà un socket UDP ouvert
pour écouter l'arrivée des nouveaux utilisateurs, un socket TCP en accept() pour accepter les éventuels demandes de connexion
et creer alors un nouveau thread qui va créer une conversation et la gérer. Ce socket TCP repasse ensuite en accept() et ainsi de suite.

lancement.java représente vraiment ce que l'utilisateur verra quand il lancera le programme.

2)

Pour executer le programme en version réel, c'est à dire comme il serait implémenté en entreprise:

Décommentez la ligne : user.demarrerserveurudptcp(); dans utilisateur
Décommentez la fonction dans "utilisateur" : demarrerserveurudptcp();

La différence avec le programme précédent sera que évidemment il n'y aura pas d'utilisateurs affichés connectés sur le réseau si vous lancez ce programme uniquement sur un PC.
Cependant si vous le lancez depuis plusieurs PC il devrait correctement fonctionner. ( nous n'avons pas pu encore le tester sur plusieurs machines )


II) Ce que fait et ne fait pas encore le code

	En rouge ce qui n'a pas encore été implémenté mais qui le sera bientôt.

Quand on lance le programme, une fenetre s'affiche où l'on peut rentrer son login, son MDP et son pseudo puis valider.



