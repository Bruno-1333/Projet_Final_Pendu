
package com.brunoleonardo.projet_final_pendu

object Constantes {
    // Utilisateur
    const val ATTRIBUT_UTILISATEUR_ID = "id"
    const val ATTRIBUT_UTILISATEUR_NOM_UTILISATEUR = "nomUtilisateur"
    const val ATTRIBUT_UTILISATEUR_MOT_DE_PASSE = "motDePasse"

    // Administrateur
    const val ATTRIBUT_ADMINISTRATEUR_UserNeme = "admin"
    const val ATTRIBUT_ADMINISTRATEUR_MotDePasse = "admin"

    // Jeu
    const val ATTRIBUT_JEU_ID = "id"
    const val ATTRIBUT_JEU_MOT_ID = "mot"

    const val ATTRIBUT_JEU_NIVEAU_DIFFICULTE = "niveauDifficulte"
    const val ATTRIBUT_JEU_RESULTAT = "resultat"
    const val ATTRIBUT_JEU_VICTOIRES = "victoires"
    const val ATTRIBUT_JEU_UTILISATEUR_ID = "utilisateurId"
    const val ATTRIBUT_JEU_VUESLETTRES = "vues_lettres"

    // Mot
    const val ATTRIBUT_MOT_ID = "id"
    const val ATTRIBUT_MOT_MOT = "mot"
    const val ATTRIBUT_MOT_DESCRIPTION = "description"
    const val ATTRIBUT_MOT_THEME = "theme"



    // Nom de la base de données et version
    const val NOM_BASE = "pendu.db"
    const val VERSION_BD = 1

    // Tables
    const val TABLE_UTILISATEUR = "utilisateur"
    const val TABLE_JEU = "jeu"
    const val TABLE_MOT = "mot"


}

