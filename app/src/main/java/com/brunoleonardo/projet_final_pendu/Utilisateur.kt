package com.brunoleonardo.projet_final_pendu

import java.io.Serializable

// Créer une classe Utilisateur
class Utilisateur(
    val id: Int,
    val nomUtilisateur: String,
    val motDePasse: String,

) : Serializable
{
    override fun toString(): String {
        return "Utilisateur(" +
                "id=$id \n" +
                " nomUtilisateur='$nomUtilisateur'\n" +
                " motDePasse='$motDePasse'\n)"
    }
}




