package com.brunoleonardo.projet_final_pendu

import java.io.Serializable

public class Jeu (
    var utilisateur: Utilisateur,
    theme: String,
    var mot: String = "",
    var description: String = "",
    niveauDifficulte: String,
    var resultat: Boolean = false,
    var vuesLettres: MutableList<Char> = mutableListOf(),
    var lettresIncorrectes: MutableList<Char> = mutableListOf()
): Serializable {
    var theme: String = if (theme == "defaultTheme") {
        "fallbackTheme"
    } else {
        theme
    }

    var niveauDifficulte: String = if (niveauDifficulte == "defaultDifficulte") {
        "fallbackDifficulte"
    } else {
        niveauDifficulte
    }

    init {
        mot.forEach { _ ->
            vuesLettres.add('_')
        }
    }

    override fun toString(): String {
        return "Jeu(utilisateur=$utilisateur, theme='$theme', mot='$mot', description='$description', niveauDifficulte='$niveauDifficulte', resultat=$resultat, vuesLettres=$vuesLettres, lettresIncorrectes=$lettresIncorrectes)"
    }

    fun guessLetter(letter: Char): Boolean {
        // Se a letra já foi adivinhada ou inserida incorretamente
        if (vuesLettres.contains(letter) || lettresIncorrectes.contains(letter)) {
            return false
        }

        if (mot.contains(letter, ignoreCase = true)) {
            mot.forEachIndexed { index, motLetter ->
                if (motLetter.equals(letter, ignoreCase = true)) {
                    vuesLettres[index] = letter.toLowerCase()
                }
            }
            return true
        } else {
            lettresIncorrectes.add(letter)
            return false
        }
    }


    fun isGameOver(): Boolean {
        return !vuesLettres.any { it == '_' }
    }

    fun resetGame() {
        lettresIncorrectes.clear()
        vuesLettres.fill('_')
    }
}



