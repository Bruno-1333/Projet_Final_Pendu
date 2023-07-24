package com.brunoleonardo.projet_final_pendu

import java.io.Serializable

data class Utilisateur(val id: Int, val nom: String, val nomUtilisateur: String, val motDePasse: String, val isAdministrateur: Boolean): Serializable
 {
    override fun toString(): String {
        return "Utilisateur(" +
                "id=$id \n" +
                " nomUtilisateur='$nomUtilisateur'\n" +
                " motDePasse='$motDePasse'\n" +
                " isAdministrateur=$isAdministrateur)"
    }
}


