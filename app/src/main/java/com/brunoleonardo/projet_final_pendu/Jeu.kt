package com.brunoleonardo.projet_final_pendu

import java.io.Serializable

// Créer une classe Jeu

class Jeu(
    var id: Int,
    var utilisateurId: Int,
    var mot: Mot,
    var niveauDifficulte: String,
    var resultat: Boolean = false,
    var vuesLettresString: String = "",
    var lettresIncorrectesString: String = "",
    var victories: Int = 0,
) : Serializable {

    // Creesr un constructeur secondaire pour le jeu
    constructor(
        id: Int,
        utilisateurId: Int,
        mot: Mot,
        niveauDifficulte: String,
        resultat: Boolean = false,
        victories: Int = 0,
    ) : this(
        id,
        utilisateurId,
        mot,
        niveauDifficulte,
        resultat,
        "",
        "",
        victories,
    )

    val vuesLettres: MutableList<Char>
        get() = vuesLettresString.toMutableList()

    val lettresIncorrectes: MutableList<Char>
        get() = lettresIncorrectesString.toMutableList()

    override fun toString(): String {
        return "Jeu(utilisateurId=$utilisateurId\n" +
                " mot='$mot'\n" +
                " niveauDifficulte='$niveauDifficulte'\n" +
                " resultat=$resultat\n" +
                " vuesLettres=$vuesLettres\n" +
                " lettresIncorrectes=$lettresIncorrectes)"+
                " victories=$victories)"
    }

    // Fonction pour deviner une lettre et retourner un boolean si la lettre est dans le mot ou pas et mettre à jour les variables du jeu.
    fun devinerLettre(letter: Char): Boolean {
        // Si la lettre a déjà été entrée, on ne fait rien
        if (vuesLettres.contains(letter) || lettresIncorrectes.contains(letter)) {
            return false
        }

        if (mot.mot.contains(letter, ignoreCase = true)) { // si la lettre est dans le mot on remplace les _ par la lettre
            mot.mot.forEachIndexed { index, motLetter ->
                if (motLetter.equals(letter, ignoreCase = true)) {
                    vuesLettres[index] = letter.toLowerCase()
                }
            }
            return true
        } else { // si la lettre n'est pas dans le mot on l'ajoute dans lettresIncorrectes
            lettresIncorrectes.add(letter)
            return false
        }
    }


    // Fonction pour vérifier si le jeu est fini et mettre à jour les variables du jeu.
    fun isGameOver(): Boolean { // si le nombre de lettres incorrectes est supérieur à 10, le jeu est fini
        return !vuesLettres.any { it == '_' }
    }

    // Fonction pour vérifier si le jeu est gagné et mettre à jour les variables du jeu.
    fun reJouer() { // remettre les variables du jeu à leur valeur initiale
        lettresIncorrectes.clear()
        vuesLettres.fill('_')
    }

    // Fonction pour varifier la quantité de victoires
    fun incrementVictories() {
        victories++
    }
}






































// Crier une classe pour le jeu

/*public class Jeu (
    var utilisateur: Utilisateur,
    theme: String,
    var mot: String = "",
    var description: String = "",
    niveauDifficulte: String,
    var resultat: Boolean = false,
    var vuesLettres: MutableList<Char> = mutableListOf(),
    var lettresIncorrectes: MutableList<Char> = mutableListOf(),
    var victories: Int = 0,
): Serializable { // Serializable pour pouvoir passer l'objet entre les activités
    // Crier les variables pour le jeu et les initialiser
    var theme: String = if (theme == "defaultTheme") {
        "themeAlternatif"
    } else {
        theme
    }

    var niveauDifficulte: String = if (niveauDifficulte == "defaultDifficulte") {
        "themeAlternatif"
    } else {
        niveauDifficulte
    }

    // Init pour add les _ dans vuesLettres
    init {
        mot.forEach { _ ->
            vuesLettres.add('_')
        }
    }

    // toString pour afficher les données du jeu
    override fun toString(): String {
        return "Jeu(utilisateur=$utilisateur\n" +
                " theme='$theme'\n" +
                " mot='$mot'\n" +
                " description='$description'\n" +
                " niveauDifficulte='$niveauDifficulte'\n" +
                " resultat=$resultat\n" +
                " vuesLettres=$vuesLettres\n" +
                " lettresIncorrectes=$lettresIncorrectes)"+
                " victories=$victories)"
    }

    // Fonction pour deviner une lettre et retourner un boolean si la lettre est dans le mot ou pas et mettre à jour les variables du jeu.
    fun devinerLettre(letter: Char): Boolean {
        // Si la lettre a déjà été entrée, on ne fait rien
        if (vuesLettres.contains(letter) || lettresIncorrectes.contains(letter)) {
            return false
        }

        if (mot.contains(letter, ignoreCase = true)) { // si la lettre est dans le mot on remplace les _ par la lettre
            mot.forEachIndexed { index, motLetter ->
                if (motLetter.equals(letter, ignoreCase = true)) {
                    vuesLettres[index] = letter.toLowerCase()
                }
            }
            return true
        } else { // si la lettre n'est pas dans le mot on l'ajoute dans lettresIncorrectes
            lettresIncorrectes.add(letter)
            return false
        }
    }


    // Fonction pour vérifier si le jeu est fini et mettre à jour les variables du jeu.
    fun isGameOver(): Boolean { // si le nombre de lettres incorrectes est supérieur à 10, le jeu est fini
        return !vuesLettres.any { it == '_' }
    }

    // Fonction pour vérifier si le jeu est gagné et mettre à jour les variables du jeu.
    fun reJouer() { // remettre les variables du jeu à leur valeur initiale
        lettresIncorrectes.clear()
        vuesLettres.fill('_')
    }

    // Fonction pour varifier la quantité de victoires
    fun incrementVictories() {
        victories++
    }
}*/



