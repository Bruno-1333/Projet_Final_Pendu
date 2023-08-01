package com.brunoleonardo.projet_final_pendu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import com.brunoleonardo.projet_final_pendu.databinding.ActivityPanneauJeuBinding

// Classe qui gère l'activité du panneau de jeu
class PanneauJeuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPanneauJeuBinding

    // Reference au bouton de theme actuellement selectionne et son image
    private var currentThemeButton: Pair<ImageButton, Int>? = null
    private var themeChoisi: String? = null
    private var difficulteChoisi: String? = null

    // pour afficher le panneau de jeu et choisir le theme et la difficulte du jeu
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPanneauJeuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dbHandler = DBHandler(this) // Ouverture de la base de données
        val utlisateurId = intent.getIntExtra("utilisateurId", -1) // Récupérer l'ID de l'utilisateur
        val utilisateur = dbHandler.chercherUtilisateurParId(utlisateurId) // Récupérer l'utilisateur

        val themeButtons = mapOf(
            binding.imgBtnAnimaux to Triple("Animal", R.drawable.img_animal_normal, R.drawable.img_animal_choisi),
            binding.imgBtnPays to Triple("Pays", R.drawable.img_pays_normal, R.drawable.img_pays_choisi),
            binding.imgBtnInstumentsMusique to Triple("Musique", R.drawable.img_musique_normal, R.drawable.img_musique_choisi),
            binding.imgBtnVoitures to Triple("Voitures", R.drawable.img_voitures_normal, R.drawable.img_voitures_choisi)
        )

        for ((button, info) in themeButtons) {
            val (theme, imgNormal, imgChoisi) = info

            button.setImageResource(imgNormal) // Definir l'image normale du bouton

            button.setOnClickListener {
                // desactive le bouton de theme actuellement selectionne
                currentThemeButton?.let { (currentButton, currentImgNormal) ->
                    currentButton.setImageResource(currentImgNormal)
                }

                // selectionne le bouton de theme actuellement clique
                button.setImageResource(imgChoisi)

                // metrre a jour le bouton de theme actuellement selectionne
                currentThemeButton = button to imgNormal

                // metrre a jour le theme choisi
                themeChoisi = theme
            }
        }

        // Gestion de la difficulté
        binding.groupeRadioDifficulte.setOnCheckedChangeListener { group, checkedId ->
            difficulteChoisi = when (checkedId) { // Récupérer la difficulté choisie
                R.id.radioBtnFacille -> "Facile"
                R.id.radioBtnMoyen -> "Moyen"
                R.id.radioBtnDifficile -> "Difficile"
                else -> null
            }
        }

        // Gestion du bouton jouer
        binding.btnJouer.setOnClickListener {
            if (themeChoisi != null && difficulteChoisi != null) {
                val selectedTheme = themeChoisi!!
                val selectedDifficulty = difficulteChoisi!!

                val mot = dbHandler.chercherMotsParThemeDifficulte(selectedTheme, selectedDifficulty)

                if(mot != null) {
                    val intent = Intent(this, JeuActivity::class.java)
                    intent.putExtra("motid", mot.id)
                    intent.putExtra("utilisateurId2", utlisateurId)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, getString(R.string.aucun_mot_par_Theme_choisi), Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, getString(R.string.selectionar_tehem_et_niveau), Toast.LENGTH_SHORT).show()
            }
        }
    }
}

