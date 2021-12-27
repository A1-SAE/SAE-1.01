# SAE 1.01 : scrabble

## Présentation du projet
bla bla bla java orienté objet, SAÉ, travail de groupe, entrainement
Ce projet s'inscrit au sein d'un SAÉ, c'est-à-dire un projet de groupe évalué faisant partie du programme de 
BUT informatique. Durant ce projet nous étions 2 à travailler en parallèle, pour ce faire nous avons utilisé 
l'outil <a href="https://github.com/Rafiki13/SAE-1.01">Github</a>.<br/>
Ce projet est pour nous un moyen de nous entrainer en java, en programmation objet et en travail d'équipe.<br/>
Le projet en lui-même consiste en un scrabble, avec des règles plus ou moins simplifiées suivant des 
<a href="https://github.com/Rafiki13/SAE-1.01/blob/main/sujet-scrabble-3.pdf">consignes données</a>.

## Extensions

### 3.1 : Prise en compte d'un dictionnaire de référence

#### classes modifiées et/ou créées
- Dico
- Branches
- Plateau
- Scrabble

#### Explications
Le dictionnaire se crée en même temps que le Plateau. Celui-ci utilise un arbre 
lexicographique.<br/>
Sa structure est faite de manière à pouvoir connaitre la répartition des mots le 
long de l'arbre, ce qui nous est utile par la suite pour l'IA.

### 3.2 : Repérage des cases du plateau

#### classes modifiées et/ou créées
- a

#### Explications
a

### 3.3 : Ajout des jetons joker

#### classes modifiées et/ou créées
- Plateau
- Scrabble
- Case

#### Explications
Le jeton joker est reconnu dans une case grâce à une variable qui indique s'il est joker ou non. Graphiquement 
on reconnait ces jetons par une étoile. Pour pouvoir calculer le score, on différencie une lettre normale en majuscule 
et une lettre joker en minuscule.

### 3.4 : Détermination du joueur qui commence

#### classes modifiées et/ou créées
- a

#### Explications
a

### 3.5 :Prise en compte des jetons qui "touchent le mot transversalement"

#### classes modifiées et/ou créées
- Plateau
- Scrabble

#### Explications
On vérifie à l'aide du Dico si les mots trasversaux formés sont correctes, et on calcul ensuite les points rapportés 
par ceux-ci.

### 3.6 : Initialisation du plateau

#### classes modifiées et/ou créées
- Plateau

#### Explications
On crée une liste de cases spéciales, on et ajoute ces cases petit à petit au plateau en transformant les cases 
spéciales par symétrie.

### 3.7 : Version IA

#### classes modifiées et/ou créées
- a

#### Explications
a

### 3.8 : Interface graphique

#### classes modifiées et/ou créées
- a

#### Explications
a