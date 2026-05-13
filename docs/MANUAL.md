<h1 align="center">
  <img src="images/logo.png" alt="Pangol1 Logo" width="64"/>
  <br/>
  Manuel d'Utilisation - Pangol1
</h1>

## **Pangol1**

_Guide complet : importation, analyse, visualisation et exportation_

**Pangol1** est une solution logicielle open source de haute performance spécialisée la création de rapports d'analyse de données sur de très gros volumes de données.

## Table des matières

1. Interface du logiciel
2. Configuration de l'application
3. Tableau de données
4. Carnet de notes et exportation
5. Support technique

# 1. Interface du logiciel

<img src="images/main_view.png" alt="Interface du logiciel" style="float: left; margin-right: 20px; width: 50%;"/>

Voici la vue principale de **Pangol1**. Elle se compose de plusieurs sections clés:

- **Barre de menu**: Accès aux fonctionnalités principales (Fichier, Affichage, Aide).
  Elle se situe en haut de l'interface et permet d'accéder à toutes les fonctionnalités du logiciel.
- **Barre d'information**: Affiche des informations sur les données importées et les analyses en cours.
  Elle se trouve tout en bas de l'interface et fournit des informations contextuelles sur les données et les analyses.
- **Carnet de notes**: Permet de créé de graphiques, ajouter des commentaires et même ajouter des images pour enrichir les rapports d'analyse.
  Il est situé à gauche de l'interface et offre un espace pour documenter les analyses et les résultats.
- **Tableau de données**: Affiche les données cellule par cellule, permettant une manipulation directe.
  Il est situé sur la partie droite de l'interface.

## Barre de menu

### Menu Fichier

Le menu "Fichier" permet d'ouvrir de nouvelles tables de données, ou des tables récemment ouvertes ou encore d'accéder aux paramètres de configuration de l'application.

<img src="images/topbar_menu_file.png" alt="Menu Fichier" style="width: 80%; display: block; margin: 0 auto;"/>

### Menu Affichage

Le menu "Affichage" permet de personnaliser la disposition des éléments de l'interface, d'afficher ou de masquer le carnet de notes, la table de données, etc.

<img src="images/topbar_menu_display_panes.png" alt="Menu Affichage - Panes" style="width: 80%; display: block; margin: 0 auto;"/>

Il permet aussi de changer l'ordre des panneaux:

<img src="images/topbar_display_rotate.png" alt="Menu Affichage - Rotation des panneaux" style="width: 80%; display: block; margin: 0 auto;"/>

Et voici les une des dispositions possibles de l'interface:

<img src="images/main_display_rotate.png" alt="Interface avec une disposition différente" style="width: 80%; display: block; margin: 0 auto;"/>

### Menu Aide

Le menu "Aide" permet d'accéder à la documentation, de voir les informations sur la version du logiciel et aussi de changer rapidement la langue de l'interface:

<img src="images/topbar_menu_help.png" alt="Menu Aide" style="width: 80%; display: block; margin: 0 auto;"/>

Information sur le logiciel:

<img src="images/main_about.png" alt="Informations sur le logiciel" style="width: 80%; display: block; margin: 0 auto;"/>

## Barre d'information

La barre d'information permet d'afficher le statut de l'application et les tâches en cours:

<img src="images/bottombar_status.png" alt="Barre d'information - Statut de l'application" style="width: 80%; display: block; margin: 0 auto;"/>

Vous pouvez cliquer sur la barre de chargement pour afficher les détails de la tâche en cours:

<img src="images/bottombar_task.png" alt="Barre d'information - Tâche en cours" style="width: 80%; display: block; margin: 0 auto;"/>

Ici on observe que l'application est en cours de chargement d'une table qui proviens d'une URL:

<img src="images/bottombar_task_open.png" alt="Barre d'information - Tâche en cours" style="width: 80%; display: block; margin: 0 auto;"/>

# 2. Configuration de l'application

L'application propose plusieurs options de configuration pour personnaliser l'expérience utilisateur et optimiser les performances. Vous pouvez accéder aux paramètres de configuration en cliquant sur le menu "Fichier" dans la barre de menu, puis en sélectionnant "Paramètres".

<img src="images/topbar_menu_file.png" alt="Menu Fichier" style="width: 80%; display: block; margin: 0 auto;"/>

## 1. Paramètres généraux

Voici le résumé des options disponibles dans les paramètres généraux :

- **Langue**: Permet de choisir la langue de l'interface utilisateur.
- **Démarrage**: Configuration du comportement de l'application au démarrage (ouvrir le dernier projet, afficher un écran de bienvenue, etc.).
- **Fermeture**: Options pour la fermeture de l'application (confirmation avant de quitter, etc.).

<img src="images/settings_general.png" alt="Paramètres généraux" style="width: 80%; display: block; margin: 0 auto;"/>

### Langue

Vous pouvez séléctionner la langue de l'interface utilisateur parmi les options disponibles:

- AR: Arabe
- EN: Anglais
- FR: Français
- ES: Espagnol
- ID: Indonésien
- PT: Portugais
- RU: Russe
- ZH: Chinois
- JA: Japonais
- TR: Turc

### Démarrage

Vous pouvez configurer le comportement de l'application au démarrage :

- Affichez ou non l'écran de chargement de Pangol1.
- Vérifiez les mises à jour au démarrage de l'application.
- Réouvrir les dernier tableaux de données.
- Ouvrir ou non un tableau de données par défaut:
  - Depuis un fichier
  - Depuis une URL

### Fermeture

Vous pouvez configurer les options de fermeture de l'application :

- Confirmer avant de quitter l'application.
- Confirmer avant de fermer un tableau de données.

## 2. Paramètres du tableau de données

Voici le résumé des options disponibles dans les paramètres du tableau de données :

- **Mode**: Permet d'editer les données directement dans le fichier source.
- **Affichage**: Configuration de l'affichage du tableau de données.
- **Colonnes**: Options pour la gestion des colonnes (ajout, suppression, réorganisation).
- **Statistiques**: Paramètres pour les calculs statistiques appliqués aux données.

<img src="images/settings_table.png" alt="Paramètres du tableau de données" style="width: 80%; display: block; margin: 0 auto;"/>

### Mode

- Ouvrir les données en mode lecture seule ou en mode édition.
  Le mode édition permet d'écrire directement dans les cellules de la table.
- Autoriser la saisie manuelle de type
  Le fichier source est typé se qui signifie que les données sont automatiquement converties dans le type de données approprié (numérique, texte, date, etc.). En activant cette option, vous pouvez saisir manuellement des données dans les cellules sans que le logiciel tente de les convertir automatiquement.
  Attention, cela peut entraîner des pertes de données ou des erreurs d'analyse si les données saisies ne sont pas conformes au type attendu.
- Sensibilité à la convertion de type
  Lorsque que la table n'est pas typé **Pangol1** tente de déviner le type de données et si un type est anbïgu, il
  prend le type le plus présent dans la colonne. Par défault cette option est réglée sur 95%, ce qui signifie que si 95% des données d'une colonne sont de type numérique, la colonne sera considérée comme numérique et donc que le reste des données (par exemple des chaînes de caractères) seront converties en numérique (par exemple en NaN). En ajustant ce seuil, vous pouvez contrôler la rigueur avec laquelle le logiciel détermine le type de données d'une colonne.

### Affichage

- Afficher ou masquer les numéros de ligne.
- Afficher les valeurs nulles
  L'affichage des valeurs nulles peut être utile pour identifier les données manquantes ou les erreurs dans le jeu de données. Cependant, cela peut également rendre le tableau plus difficile à lire si de nombreuses valeurs nulles sont présentes. En activant cette option, vous pouvez choisir d'afficher ou de masquer les valeurs nulles dans le tableau de données. Attention, cette option peut impacter les performances.

### Colonnes

- Masquer les colonnes vides par défaut
  Lorsque cette option est activée, les colonnes qui ne contiennent aucune donnée (c'est-à-dire des colonnes entièrement vides) seront automatiquement masquées dans le tableau de données. Cela peut aider à réduire l'encombrement visuel et à se concentrer sur les données pertinentes. Cependant, si vous avez besoin de voir toutes les colonnes, y compris celles qui sont vides, vous pouvez désactiver cette option.
- Afficher les types de données dans les en-têtes de colonnes
  En activant cette option, les types de données (par exemple, numérique, texte, date) seront affichés dans les en-têtes de colonnes du tableau de données. Cela peut être utile pour identifier rapidement le type de données contenu dans chaque colonne, ce qui peut faciliter l'analyse et la manipulation des données.
- Ajuster automatiquement la largeur des colonnes
  Lorsque cette option est activée, la largeur des colonnes du tableau de données sera automatiquement ajustée en fonction du contenu de chaque colonne. Cela signifie que les colonnes s'élargiront ou se rétréciront pour s'adapter au texte ou aux valeurs qu'elles contiennent, ce qui peut améliorer la lisibilité du tableau.

### Statistiques

- Calculer les statistiques lorsqu'une colonne est sélectionnée
  En activant cette option, les statistiques de base (telles que la moyenne, la médiane, l'écart-type, etc.) seront automatiquement calculées et affichées lorsque vous sélectionnez une colonne dans le tableau de données. Cela peut être utile pour obtenir rapidement des informations sur la distribution des données dans cette colonne sans avoir à effectuer des calculs manuels. Attention, cette option peut impacter les performances.

## 3. Paramètres d'apparance

Voici les résumé des options disponibles dans les paramètres d'apparance :

- **Thème**: Choix entre différents thèmes d'apparence pour l'interface utilisateur (clair, sombre, etc.).
- **Interface**: Configuration de l'interface utilisateur (taille des polices, couleurs, etc.).

<img src="images/settings_appearance.png" alt="Paramètres du tableau de données" style="width: 80%; display: block; margin: 0 auto;"/>

### Thème

- Choix entre le thème clair et le thème sombre.
  Le mode "Automatique" permet de choisir avec les curseurs votre plage horaire de préférence pour le thème sombre. Par défaut, le thème sombre est activé de 18h à 6h du matin.
  Le mode "Système" permet d'adapter automatiquement le thème de l'application en fonction du thème de votre système d'exploitation.

### Interface

- Utiliser le thème FlatLaf
  FlatLaf est un thème d'apparence moderne et épuré pour les applications Java. En activant cette option, vous pouvez appliquer le thème FlatLaf à l'interface de Pangol1, ce qui peut améliorer l'esthétique et la convivialité de l'application. Attention, cette option peut impacter les performances sur les machines plus anciennes ou moins puissantes.
- Taille de la police
  Vous pouvez ajuster la taille de la police utilisée dans l'interface de Pangol1 pour améliorer la lisibilité en fonction de vos préférences.
- Famille de police
  Vous pouvez choisir la famille de police utilisée dans l'interface de Pangol1 pour personnaliser l'apparence du texte selon vos préférences. La liste des familles dépend exclusivement de celles installées sur votre système.

## 4. Raccourcis clavier

Pangol1 ne permet pas encore de personnaliser les raccourcis clavier, mais voici la liste des raccourcis disponibles par défaut :

| Action                                      | Raccourci clavier |
| ------------------------------------------- | ----------------- |
| Ouvrir une table                            | Ctrl + O          |
| Fermer la table                             | Ctrl + W          |
| Ouvrir le créateur de filtres               | Ctrl + F          |
| Ouvrir les paramètres                       | Ctrl + ,          |
| Trie par ordre croissant                    | Ctrl + Shift + A  |
| Trie par ordre décroissant                  | Ctrl + Shift + D  |
| Annuler une action dans le carnet de notes  | Ctrl + Z          |
| Rétablir une action dans le carnet de notes | Ctrl + Y          |

<img src="images/settings_shortcuts.png" alt="Paramètres des raccourcis clavier" style="width: 80%; display: block; margin: 0 auto;"/>

## 5. Paramètres avancés

Les paramètres avancés permettent de configurer des options plus techniques et spécifiques pour les utilisateurs expérimentés. Voici un résumé des options disponibles dans les paramètres avancés :

- **Activer le journal de débogage**: Permet d'activer ou de désactiver le journal de débogage pour aider à diagnostiquer les problèmes.
- **Activer le mode développeur**: Permet d'accéder à des fonctionnalités supplémentaires destinées aux développeurs.

<img src="images/settings_advanced.png" alt="Paramètres avancés" style="width: 80%; display: block; margin: 0 auto;"/>

# 3.Tableau de données

Le panneau de droite de l'interface affiche les données importées dans un format de tableau. Chaque cellule du tableau correspond à une valeur spécifique dans le jeu de données, et les utilisateurs peuvent interagir directement avec ces cellules pour manipuler les données.
Ce panneau contient plusieur fonctionnalités pour faciliter la manipulation et l'analyse des données, notamment :

- **Barre d'outils de la table**: Permet d'accéder à des fonctionnalités spécifiques pour manipuler les données du tableau, telles que le tri, le filtrage, l'ajout ou la suppression de colonnes, etc.
- **Menu contextuel**: En cliquant avec le bouton droit sur l'en-tête d'une colonne.
- **Séléection de colonnes**: Permet de sélectionner une colonne et d'afficher ses statisitques de base (moyenne, médiane, écart-type, etc.) dans la barre d'information en bas de l'interface.

## 1. Barre d'outils de la table

La barre d'outils contient 4 boutons principaux pour manipuler les données du tableau :

- **Filtres**: Permet d'ouvrir le créateur de filtres pour appliquer des filtres avancés sur les données du tableau.
- **Sous-table**: Permet de créer une sous-table à partir des données sélectionnées dans le tableau.
- **Afficher/Masquer les colonnes vides**: Permet d'afficher ou de masquer les colonnes qui ne contiennent aucune donnée.
- **Fermer la table**: Permet de fermer le tableau de données actuellement ouvert.

### Créateur de filtres

Le créateur de filtres permet d'appliquer des filtres avancés sur le tableau ouvert. Vous pouvez créer des filtres basés sur des conditions spécifiques pour affiner les données affichées dans le tableau. Par exemple, vous pouvez filtrer les données pour n'afficher que les lignes où une certaine colonne contient une valeur spécifique, ou pour n'afficher que les lignes où une colonne numérique est supérieure à un certain seuil.

<img src="images/table_toolbar_filter.png" alt="Créateur de filtres" style="width: 80%; display: block; margin: 0 auto;"/>

Voici un exemple de filtrage avancé sur les données du tableau:

<img src="images/filter_all.png" alt="Exemple de filtrage avancé" style="width: 80%; display: block; margin: 0 auto;"/>

Sélection du filtre à appliquer, par exemple entre une **Intervale**:
<img src="images/filter_choose.png" alt="Exemple de filtrage avancé" style="width: 80%; display: block; margin: 0 auto;"/>

Comme les colonnes sont typées le créateur de filtres est intelligent et ne propose que des opérateurs et des champs pertinent en fonction du type de la colonne sélectionnée.

Par exemple pour une colonne de type Date:
<img src="images/filter_date.png" alt="Créateur de filtres pour une colonne de type Date" style="width: 80%; display: block; margin: 0 auto;"/>

Pour une colonne de type Numérique:
<img src="images/filter_number.png" alt="Créateur de filtres pour une colonne de type Numérique" style="width: 80%; display: block; margin: 0 auto;"/>

Pour une colonne qui contient des chaînes de caractères (ou une **Catégorie**):
<img src="images/filter_category.png" alt="Créateur de filtres pour une colonne de type Chaîne de caractères" style="width: 80%; display: block; margin: 0 auto;"/>

Les catégorie dans **Pangol1** sont des colonnes de type chaîne de caractères qui contiennent un nombre limité de valeurs uniques. Par exemple, une colonne "Pays" qui contient les valeurs "France", "Espagne", "Italie", etc. Les catégories sont traitées de manière spéciale dans le créateur de filtres pour permettre des filtrages plus efficaces et pertinents.

### Sous-table

Le concept de sous-table permet de créer une nouvelle table sur la base de la table ouverte. Il suffit de sélectionner la colonne à partir de laquelle vous souhaitez regrouper les données puis des choisir l'aggrégation de votre choix sur le reste des colonnes.

<img src="images/table_toolbar_subtable.png" alt="Créateur de sous-table" style="width: 80%; display: block; margin: 0 auto;"/>

Ici on sélectionne une colonne qui contient des catégories. Les colonnes sans aggrégation **(Aucune)** sont simplement ignorées lors de la création de la sous-table. Ici on choisit l'aggrégation de comptage distinct des éléments:

<img src="images/subtable_create.png" alt="Créateur de sous-table" style="width: 80%; display: block; margin: 0 auto;"/>

Ensuite notre sous-table s'affiche comme un nouvelle table classique et elle est automatiquement sauvegardée dans le même dossier que la table d'origine:

<img src="images/subtable_newtable.png" alt="Sous-table créée" style="width: 80%; display: block; margin: 0 auto;"/>

### Afficher/Masquer les colonnes vides

Si votre table contient de nombreuses colonnes vides, cela peut rendre la navigation et l'analyse des données plus difficile. En activant cette option, vous pouvez choisir d'afficher toutes les colonnes ou bien de masquer les colonnes qui ne contiennent aucune donnée.

<img src="images/table_toolbar_hide_empty.png" alt="Afficher/Masquer les colonnes vides" style="width: 80%; display: block; margin: 0 auto;"/>

### Fermer la table

Lorque vous souhaitez change de table ou simplement fermer la table de données. Vous pouvez revenir à la liste de vos tables ouvertes:

<img src="images/table_close.png" alt="Fermer la table" style="width: 80%; display: block; margin: 0 auto;"/>

Liste des tables ouvertes:

<img src="images/table_list_menu.png" alt="Liste des tables ouvertes" style="width: 80%; display: block; margin: 0 auto;"/>
Vous trouverez également un petit menu contextuel en cliquant avec le bouton droit sur une table de la liste.

## 2. Menu contextuel

En cliquant avec le bouton droit sur l'en-tête d'une colonne, vous pouvez accéder à un menu contextuel qui offre plusieurs options pour manipuler les données de cette colonne. Voici les options disponibles dans le menu contextuel :

<img src="images/table_ctxmenu.png" alt="Menu contextuel de la table" style="width: 80%; display: block; margin: 0 auto;"/>

- **Trier**: Permet de trier les données de la colonne par ordre croissant ou décroissant.
- **Appliquer un filtre simple**: Permet d'appliquer un filtre simple sur la colonne, en sélectionnant une valeur spécifique à afficher.
- **Afficher les statistiques**: Permet d'afficher les statistiques de base pour la colonne sélectionnée, telles que la moyenne, la médiane, l'écart-type, etc.
- **Changer le type de données**: Permet de changer le type de données de la colonne (numérique, texte, date, etc.).
- **Renommer la colonne**: Permet de renommer la colonne pour une meilleure organisation des données.
- **Masquer la colonne**: Permet de masquer la colonne du tableau de données.

### Trie par ordre croissant ou décroissant

Attention si le trie est appliqué sur plusieurs colonnes, le trie ne sera effectif uniquement sur la dernière colonne sur laquelle le trie a été appliqué. Par exemple, si vous appliquez un trie croissant sur la colonne "Pays" puis un trie décroissant sur la colonne "Population", les données seront triées par ordre décroissant de population, et en cas d'égalité de population, elles seront triées par ordre croissant de pays.

<img src="images/table_ctxmenu_sort.png" alt="Trier les données de la colonne" style="width: 80%; display: block; margin: 0 auto;"/>

### Appliquer un filtre simple

Le filtre simple permet de filtrer les données uniquement sur une colonne. Ce menu contextuel est intelligent et s'adapte au type de données de la colonne sélectionnée. Par exemple, pour une colonne de type catégorie, le menu contextuel affichera la liste des catégories uniques présentes dans la colonne, et vous pourrez sélectionner une ou plusieurs catégories pour filtrer les données.

<img src="images/table_ctxmenu_filter.png" alt="Appliquer un filtre simple" style="width: 80%; display: block; margin: 0 auto;"/>

Ici on applique un filtre par **Catégorie** sur une colonne qui a été détectée comme contenant des catégories. Le menu contextuel affiche la liste des catégories uniques présentes dans la colonne, et vous pouvez sélectionner une ou plusieurs catégories pour filtrer les données.

<img src="images/table_ctxmenu_filter_category.png" alt="Appliquer un filtre simple sur une colonne de type catégorie" style="width: 80%; display: block; margin: 0 auto;"/>

Le boutton "Supprimer les filtres" permet de supprimer tous les filtres appliqués sur la **colonne** sélectionnée et non sur l'ensemble du tableau.

### Afficher les statistiques

Le menu de statistiques est lui aussi intelligent et s'adapte en fonction du type de données de la colonne sélectionnée.

<img src="images/table_ctxmenu_stats.png" alt="Afficher les statistiques de la colonne" style="width: 80%; display: block; margin: 0 auto;"/>

Par exemple pour le type date:

<img src="images/table_ctxmenu_stats_date.png" alt="Afficher les statistiques pour une colonne de type date" style="width: 80%; display: block; margin: 0 auto;"/>

Pour le type numérique les options sont bien plus nombreuses:

<img src="images/table_ctxmenu_stats_number.png" alt="Afficher les statistiques pour une colonne de type numérique" style="width: 80%; display: block; margin: 0 auto;"/>

Vous remarquerez qu'il existe des options de corrélations pour les colonnes numériques. Cette option permet de calculer la corrélation entre la colonne sélectionnée et une autre colonne numérique de votre choix.

Lorsque vous sélectionnez par exemple l'option **Résumer**, une nouvelle fenêtre s'ouvre pour vous permettre de visualiser les statistiques.

<img src="images/stats_summary.png" alt="Résumé des statistiques pour une colonne de type numérique" style="width: 80%; display: block; margin: 0 auto;"/>

Vous pouvez ensuite les ajouter au carnet de notes en cliquant sur le bouton "Ajouter au carnet de notes" en bas de la fenêtre.

<img src="images/stats_summary_add.png" alt="Ajouter les statistiques au carnet de notes" style="width: 80%; display: block; margin: 0 auto;"/>

### Autres options du menu contextuel

- **Changer le type de données**: Cette option n'est visible que pour les utilisateurs qui on activé dans les paramètres le mode édition: "Table>Mode>Autoriser la saisie manuelle de type". Elle permet de changer le type de données d'une colonne, par exemple de numérique à texte ou de date à texte, etc. Attention, cela peut entraîner des pertes de données ou des erreurs d'analyse si les données ne sont pas conformes au type attendu.
- **Renommer la colonne**: Permet de renommer la colonne pour une meilleure organisation des données. Par exemple, vous pouvez renommer une colonne "Population" en "Population totale". Attention, le renommage ne s'applique que visuellement dans le tableau de données et n'affecte pas le nom de la colonne dans le fichier source.
- **Masquer la colonne**: Permet de masquer la colonne du tableau de données.

## 3. Séléection de colonnes

Si vous avez activé dans les paramètres l'option "Table>Statistiques>Calculer les statistiques lorsqu'une colonne est sélectionnée", vous pouvez simplement cliquer sur une colonne du tableau de données pour afficher ses statistiques de base (moyenne, médiane, écart-type, etc.) dans la barre d'information en bas de l'interface.

<img src="images/bottombar_stats.png" alt="Sélection de colonnes et affichage des statistiques" style="width: 80%; display: block; margin: 0 auto;"/>

# 4. Carnet de notes

Le carnet de notes vous permet de documenter votre analyse sur les données en ajoutant des graphiques, des commentaires et même des images. Vous pouvez créer plusieurs pages dans le carnet de notes pour organiser votre analyse de manière structurée.

Les blocs du carnet de notes sont déplaçables et modifiables:

<img src="images/report_ctxmenu.png" alt="Menu contextuel du carnet de notes" style="width: 80%; display: block; margin: 0 auto;"/>

Vous pouvez également les déplacer avec le "glisser-déposer" pour organiser votre page comme vous le souhaitez:

<img src="images/report_dragdrop.png" alt="Glisser-déposer dans le carnet de notes" style="width: 80%; display: block; margin: 0 auto;"/>

### Ajouter un texte

Cliquer sur le button "Ajouter un bloc de texte":

<img src="images/report_select_text.png" alt="Ajouter un texte au carnet de notes" style="width: 80%; display: block; margin: 0 auto;"/>

Puis saisissez votre texte dans la section de paramétrage du bloc de texte:

<img src="images/report_text_setting.png" alt="Paramètres du bloc de texte" style="width: 80%; display: block; margin: 0 auto;"/>

Et voici le résultat dans le carnet de notes:

<img src="images/report_text.png" alt="Bloc de texte ajouté au carnet de notes" style="width: 80%; display: block; margin: 0 auto;"/>

### Ajouter une image

Cliquer sur le button "Ajouter une image":
<img src="images/report_select_image.png" alt="Ajouter une image au carnet de notes" style="width: 80%; display: block; margin: 0 auto;"/>

Puis sélectionnez votre image parmis vos fichiers:
<img src="images/report_image_setting.png" alt="Paramètres du bloc dimage" style="width: 80%; display: block; margin: 0 auto;"/>

Et voici le résultat dans le carnet de notes:

<img src="images/report_image.png" alt="Image ajoutée au carnet de notes" style="width: 80%; display: block; margin: 0 auto;"/>

### Ajouter un graphique

Cliquer sur le button "Ajouter un graphique":
<img src="images/report_select_chart.png" alt="Ajouter un graphique au carnet de notes" style="width: 80%; display: block; margin: 0 auto;"/>

Ensuite une menu de paramétrage souvre et vous offre plusieurs sections pour configurer votre graphique:

<img src="images/report_chart_setting.png" alt="Paramètres du bloc de graphique" style="width: 80%; display: block; margin: 0 auto;"/>

Vous pouvez choisir la source de vos données et même travailler sans avoir de table ouverte:

<img src="images/report_select_table.png" alt="Sélection de la source de données pour le graphique" style="width: 80%; display: block; margin: 0 auto;"/>

Ensuite ajouter votre graphique dans le carnet de notes:

<img src="images/report_chart.png" alt="Graphique ajouté au carnet de notes" style="width: 80%; display: block; margin: 0 auto;"/>

Si vous aviez envie de modifier votre graphique, double-cliquez simplement dessus pour rouvrir le menu de paramétrage qui cette fois ci contiendra des options supplémentaires pour modifier votre graphique:

<img src="images/report_chart_edit.png" alt="Modifier un graphique dans le carnet de notes" style="width: 80%; display: block; margin: 0 auto;"/>

Une fois terminer cliquer simplement sur "Modifier" pour appliquer les modifications à votre graphique.
Voici les différents graphiques que **Pangol1** propose:

<img src="images/chart_bar.png" alt="Graphique en barres" style="width: 30%; display: inline-block; margin: 0 auto;"/>
<img src="images/chart_cols.png" alt="Graphique en colonnes" style="width: 30%; display: inline-block; margin: 0 auto;"/>
<img src="images/chart_line.png" alt="Graphique en lignes" style="width: 30%; display: inline-block; margin: 0 auto;"/>
<img src="images/chart_pie.png" alt="Graphique en camembert" style="width: 30%; display: inline-block; margin: 0 auto;"/>
<img src="images/chart_radar.png" alt="Graphique en nuage de points" style="width : 30%; display: inline-block; margin: 0 auto;"/>
<img src="images/chart_dots.png" alt="Graphique en radar" style="width: 30%; display: inline-block; margin: 0 auto;"/>
<img src="images/chart_area.png" alt="Graphique en aires" style="width: 30%; display: inline-block; margin: 0 auto;"/>

### Exporter le carnet de notes

Une fois votre analyse terminée, vous pouvez exporter votre carnet de notes dans plusieurs formats et différents styles:
<img src="images/report_export.png" alt="Exporter le carnet de notes" style="width: 80%; display: block; margin: 0 auto;"/>

<img src="images/export_main.png" alt="Menu d'exportation du carnet de notes" style="width: 80%; display: block; margin: 0 auto;"/>

Sélection du format d'exportation:
<img src="images/export_format.png" alt="Sélection du format d'exportation" style="width: 80%; display: block; margin: 0 auto;"/>

Sélection du style d'exportation:
<img src="images/export_theme.png" alt="Sélection du style d'exportation" style="width: 80%; display: block; margin: 0 auto;"/>

Ensuite vous pourrez choisir le dossier de destination de votre rapport et trouver un fichier ZIP contenant:

- Un fichier au format de votre choix (HTML, PDF, Markdown, etc.) avec votre rapport d'analyse.
- Un dossier "charts" contenant les images en SVG et en PNG de touts les graphiques que vous avez créés dans votre carnet de notes.

# 7. Support Technique

Pour tout problème :

- **Jules Garcia** (Scrum Master)  
  jules.garcia.1@etudiant.univ-rennes1.fr

- **Kerem Eylem** (Product Owner)  
  kerem.eylem@etudiant.univ-rennes1.fr

- **Briac Boitel** (Developer)  
  briac.boitel@etudiant.univ-rennes.fr

- **Noé Berthelier** (Developer)  
  noe.berthelier@etudiant.univ-rennes1.fr

- **Elouan Barbier** (Developer)  
  elouan.barbier.1@etudiant.univ-rennes1.fr

- **Paul Gallon** (Developer)  
  paul.gallon@etudiant.univ-rennes.fr

- **Basile Guemene** (Developer)  
  basile.guemene@etudiant.univ-rennes.fr
