package com.brunoleonardo.projet_final_pendu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import com.brunoleonardo.projet_final_pendu.databinding.ActivityPanneauJeuBinding

class PanneauJeuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPanneauJeuBinding

    // Referência ao botão de tema atualmente selecionado e suas imagens correspondentes
    private var currentThemeButton: Pair<ImageButton, Int>? = null
    private var themeChoisi: String? = null
    private var difficulteChoisi: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPanneauJeuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val utilisateur = intent.getSerializableExtra("utilisateur") as Utilisateur

        val themeButtons = mapOf(
            binding.imgBtnAnimaux to Triple("Animal", R.drawable.img_animal_normal, R.drawable.img_animal_choisi),
            binding.imgBtnPays to Triple("Pays", R.drawable.img_pays_normal, R.drawable.img_pays_choisi),
            binding.imgBtnInstumentsMusique to Triple("Musique", R.drawable.img_musique_normal, R.drawable.img_musique_choisi),
            binding.imgBtnVoitures to Triple("Voitures", R.drawable.img_voitures_normal, R.drawable.img_voitures_choisi)
        )

        for ((button, info) in themeButtons) {
            val (theme, imgNormal, imgChoisi) = info

            button.setImageResource(imgNormal) // Definir a imagem inicial do botão

            button.setOnClickListener {
                // Desmarque o botão de tema atualmente selecionado, se houver um
                currentThemeButton?.let { (currentButton, currentImgNormal) ->
                    currentButton.setImageResource(currentImgNormal)
                }

                // Marque o novo botão como selecionado
                button.setImageResource(imgChoisi)

                // Atualize a referência ao botão de tema atualmente selecionado e sua imagem normal
                currentThemeButton = button to imgNormal

                // Atualize o tema escolhido
                themeChoisi = theme
            }
        }

        binding.groupeRadioDifficulte.setOnCheckedChangeListener { group, checkedId ->
            difficulteChoisi = when (checkedId) {
                R.id.radioBtnFacille -> "Facile"
                R.id.radioBtnMoyen -> "Moyen"
                R.id.radioBtnDifficile -> "Difficile"
                else -> null
            }
        }

        binding.btnJouer.setOnClickListener {
            if (themeChoisi != null && difficulteChoisi != null) {
                val selectedTheme = themeChoisi!! // Récupérer le thème choisi
                val selectedDifficulty = difficulteChoisi!! // Récupérer la difficulté choisie

                val intent = Intent(this, JeuActivity::class.java).apply {
                    putExtra("theme", selectedTheme)
                    putExtra("difficulte", selectedDifficulty)
                    putExtra("utilisateur", utilisateur)
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, "Veuillez sélectionner un thème et un niveau de difficulté.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

