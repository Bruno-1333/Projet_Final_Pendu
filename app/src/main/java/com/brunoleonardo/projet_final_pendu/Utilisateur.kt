package com.brunoleonardo.projet_final_pendu

data class Utilisateur (
    val id: Int,
    val nom: String,
    val nomUtilisateur: String,
    val motDePasse: String,
    val isAdministrateur: Boolean
) {
    constructor(nom: String,
                nomUtilisateur: String,
                motDePasse: String,
                isAdministrateur: Boolean)
            : this(0, nom, nomUtilisateur, motDePasse, isAdministrateur)
}