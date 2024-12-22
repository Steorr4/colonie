# Simulation de partage de ressource au sein d'une colonie

##  À propos
Ce projet est organisé dans le cadre d'une evolution du module de Programmation Avancée & Application de L3 du parcours Informatique de l'Université de Paris-Cité.\
L'objectif est de représenter un partage de ressource au sein d'une colonie spacial en fonction des préférences
de chacun et de leurs relations entre eux.

## Installation & Lancement

### Prérequis
* Avoir une version récente de JAVA

### Lancement
Télécharger la dernière version du fichier `.jar`.\
Deux options sont possible :
* Soit lancer le programme sans arguments.
```sh
java -jar chemin/vers/SpaceColony.jar
```
* Ou bien le lancer avec un fichier de colonie pré-enregistré en argument.
```sh
java -jar chemin/vers/SpaceColony.jar chemin/vers/colonie.txt
```
Si aucun argument n'est spécifié, le programma vous demandera de créer la colonie a la main.

## Architecture du code source

```text
SpaceColony/
├── src/fr/upc/mi/paa/ 
|   ├── affectation/
|   |   ├── AffectationAmelioree.java
|   |   ├── AffectationBruteForce.java
|   |   ├── AffectationLineaire.java
|   |   └── AffectationStrategy.java
|   ├── colonie/
|   |   ├── Colonie.java
|   |   ├── ColonieSetup.java
|   |   ├── Crewmate.java
|   |   └── Ressource.java
|   └── ui/
|       └── MenuPrincipal.java
├── tests/
|   ├── fichiersTests/
|   ├── ColonieSetupTest.java
|   ├── ColonieTest.java
|   ├── CrewmateTest.java
|   └── RessourceTest.java
├── LICENSE
├── README.md
└── SpaceColony.jar
```

## Algorithme de répartition des ressources

### Affectation Linéaire
Une méthode simple qui consiste juste a donner a chaque colon, sa ressource
préférée encore disponible.\
Cet algorithme est peu complexe ($O(n)$) mais ne permet pas d'avoir un nombre de colon satisfait
minimal.

### Affectation Naive Améliorée
Cette méthode inspirée d'un algorithme d'affectation naïve consiste a, dans un premier temps, trier les colons par leurs degré de connection afin de s’occuper des plus problématiques d'abord.
Ensuite de prendre au hasard 2 colons et d’échanger leurs ressources assignés.\
Si le nombre de jaloux diminue suite a cela alors on laisse ce changement, sinon on retourne a l’état precedent et ça pendant un nombre d'itération pré-défini.\
De plus, si l'on constate qu'au bout d'un certain nombre d’échange il n'y a pas d'amelioration, alors on re-mélange la liste des colons en y réappliquant 
une affectation linéaire afin de ne pas rester bloqué dans des minimas locaux. 

### Affectation Brute Force
Méthode qui consiste a "forcer" le problème en effectuant un certain nombre de mélanges des colons et de re-répartir les ressources 
de manière linéaire dans l'ordre du mélange et de garder la meilleure configuration possible (celle qui engendre le moins de jaloux).

