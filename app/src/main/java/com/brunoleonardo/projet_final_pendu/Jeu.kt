package com.brunoleonardo.projet_final_pendu

class Jeu (
    val id: Int,
    val utilisateur: Utilisateur,
    val tema: String,
    val palavra: String,
    val descricao: String,
    val nivelDificuldade: String,
    var resultado: Boolean
)
{
    constructor(utilisateur: Utilisateur,
                tema: String,
                palavra: String,
                descricao: String,
                nivelDificuldade: String,
                resultado: Boolean)
            : this(0, utilisateur, tema, palavra, descricao, nivelDificuldade, resultado)
}