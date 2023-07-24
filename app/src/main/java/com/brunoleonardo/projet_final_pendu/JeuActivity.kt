package com.brunoleonardo.projet_final_pendu

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.brunoleonardo.projet_final_pendu.databinding.ActivityJeuBinding
import java.util.Locale


class JeuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityJeuBinding
    private lateinit var jeu: Jeu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJeuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Récupération des données de l'activité précédente (PanneauJeuActivity)
        val theme = intent.getStringExtra("theme")
        val difficulte = intent.getStringExtra("difficulte")

        // Créer un nouveau jeu
        val utilisateur = intent.getSerializableExtra("utilisateur") as Utilisateur

        // Aqui você precisa definir a palavra e a descrição baseadas no tema e dificuldade
        val mot = choisirMot(theme, difficulte) // buscar ou gerar a palavra baseada no tema e dificuldade
        val description = "descripcion_exemple" // buscar ou gerar a descrição baseada no tema e dificuldade

        jeu = Jeu(utilisateur, theme ?: "defaultTheme", mot, description, difficulte ?: "defaultDifficulte")

        binding.txtDescription.text = jeu.description

        creerVuesLettres()

        binding.btnEssayer.setOnClickListener {
            val lettre = binding.txtSaissirLettre.text.toString().firstOrNull()
            lettre?.let {
                if (!jeu.vuesLettres.contains(it) && !jeu.lettresIncorrectes.contains(it)) {
                    if (jeu.guessLetter(it)) {
                        miseAJourLettresDevinees()
                    } else {
                        miseAJourLettresIncorrectes()
                    }
                    binding.txtSaissirLettre.setText("") // apaga o valor de txtSaissirLettre
                } else {
                    Toast.makeText(this, "Vous deja utilise cette lettre!", Toast.LENGTH_SHORT).show()
                }
            }
            verifierFinDePartie()
            miseAJourLettresDevinees() // Atualiza as letras corretamente adivinhadas a cada tentativa
        }



        binding.btnRejouer.setOnClickListener {
            jeu.resetGame()
            miseAJourLettresDevinees()
            miseAJourLettresIncorrectes()
            binding.zoneImgPendu.setImageResource(R.drawable.img_default)
        }

        // Definir a imagem inicial
        miseAJourImage(0)
    }

    // Escolher uma palavra com base no tema e na dificuldade
    private fun choisirMot(theme: String?, difficulte: String?): String {
        val mots = mapOf(
            "Animaux" to mapOf(
                "Facile" to listOf("lion"), // 4 letras
                "Moyen" to listOf("panthère"), // 8 letras
                "Difficile" to listOf("rhinocéros") // 10 letras
            ),
            "Pays" to mapOf(
                "Facile" to listOf("Peru"), // 4 letras
                "Moyen" to listOf("Portugal"), // 8 letras
                "Difficile" to listOf("Afghanistan") // 11 letras
            ),
            "Instruments Musique" to mapOf(
                "Facile" to listOf("harp"), // 4 letras
                "Moyen" to listOf("trombone"), // 8 letras
                "Difficile" to listOf("clavicémbalo") // 12 letras
            ),
            "Voitures" to mapOf(
                "Facile" to listOf("Audi"), // 4 letras
                "Moyen" to listOf("Mercedes"), // 8 letras
                "Difficile" to listOf("Lamborghini") // 11 letras
            ),
        )

        val motParDefaut = "girafe"

        val motsThemeDifficulte = mots[theme]?.get(difficulte)
        return if (motsThemeDifficulte != null && motsThemeDifficulte.isNotEmpty()) {
            motsThemeDifficulte.random() // escolhe uma palavra aleatória da lista
        } else {
            motParDefaut
        }
    }

    private fun creerVuesLettres() {
        binding.motLayout.removeAllViews()

        jeu.vuesLettres.forEach { letter ->
            TextView(this).apply {
                text = if (letter == '_') "_" else letter.toString().toUpperCase()
                textSize = 24f
                setPadding(8, 0, 8, 0)
                binding.motLayout.addView(this)
            }
        }
    }

    private fun miseAJourLettresDevinees() {
        for (i in jeu.vuesLettres.indices) {
            (binding.motLayout.getChildAt(i) as TextView).text =
                if (jeu.vuesLettres[i] == '_') "_" else jeu.vuesLettres[i].toString().toUpperCase()
        }
    }

    private fun miseAJourLettresIncorrectes() {
        binding.txtMauvaisMot.text = jeu.lettresIncorrectes.joinToString(" ") { it.toString().toUpperCase() }
        binding.txtMauvaisMot.setTextColor(ContextCompat.getColor(this, R.color.red))
        miseAJourImage(jeu.lettresIncorrectes.size)
    }


    private fun verifierFinDePartie() {
        if (jeu.isGameOver()) {
            jeu.resultat = true
            Toast.makeText(this, "Vous avez gagné!", Toast.LENGTH_SHORT).show()
        } else if (jeu.lettresIncorrectes.size == 7) {
            jeu.resultat = false
            Toast.makeText(this, "Vous avez perdu! Le mot était ${jeu.mot}.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun miseAJourImage(nbrMauvaisesLettres: Int) {
        val image = when (nbrMauvaisesLettres) {
            0 -> R.drawable.img_default
            1 -> R.drawable.img_01
            2 -> R.drawable.img_02
            3 -> R.drawable.img_03
            4 -> R.drawable.img_04
            5 -> R.drawable.img_05
            6 -> R.drawable.img_06
            7 -> R.drawable.img_07
            else -> R.drawable.img_default
        }
        binding.zoneImgPendu.setImageResource(image)
    }
}








