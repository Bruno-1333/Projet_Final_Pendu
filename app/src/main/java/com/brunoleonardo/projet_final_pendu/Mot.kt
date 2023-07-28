package com.brunoleonardo.projet_final_pendu

import java.io.Serializable

// creer un class Mot
 data class Mot(
    var id: Int,
    var mot: String,
    var description: String,
    var theme: String,

){

    // Crer un constructeur secondaire pour le mot

    constructor(
        mot: String,
        description: String,
        theme: String,
    ) : this(
        0,
        mot,
        description,
        theme,
    )
    override fun toString(): String {
        return "Mot(id=$id, mot='$mot', description='$description', theme='$theme')"
    }
}


