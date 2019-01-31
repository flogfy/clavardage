Utilisation du programme

Pour utiliser le programme il faut tout d'abord installer les fichiers nécessaire à la BDD.
Pour cela, vous devez les télécharger sur : https://db.apache.org/derby/releases/release-10.14.2.0.cgi
Télécharger le premier fichier .zip et extrayez les fichiers sur votre ordinateur.

Ouvrez Eclipse, récupérez nos fichiers issus de notre git et ouvrer les dans Eclipse.
Vous devez après cela dire à Eclipse d'utiliser les fichiers pour la BDD.
Pour cela, cliquez une fois sur le nom du projet dans le package explorer dans Eclipse.Faites ensuite clic droit dessus et ouvrez properties. Selectionnez ensuite l'onglet Java Build Path et supprimer l'ensemble des fichiers du Build Path. Puis cliquez sur "Add externazl JARs..." et allez récuperer les fichiers que vous avez téléchargé, plus précisément les fichiers dans le dossier .lib de ce que vous avez téléchargé puis ajoutez les. Cliquez ensuite sur "Apply and Close"

Pour lancer le programme il ne vous reste plus qu'a exécuter le fichier : lancement.java

A la première utilisation du programme, la BDD ne sera pas créée donc il n'y aura pas de login et MDP particulier a entrer pour se connecter. Les login et MDP qui seront nécessaires pour se connecter aux exécutions suivantes seront ceux que vous aurez entrés lors de la première exécution. Veillez donc bien à vous souvenir des identifiants que vous entrez à votre première execution du programme.

Le reste du programme est simple d'utilisation.

Ne prêtez pas attention aux différentes exceptions pouvant être levées durant l'exécution du programme, elles sont normales et volontaires.
