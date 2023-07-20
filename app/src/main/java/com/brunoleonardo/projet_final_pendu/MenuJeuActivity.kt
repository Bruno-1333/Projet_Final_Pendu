package com.brunoleonardo.projet_final_pendu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.brunoleonardo.projet_final_pendu.databinding.ActivityMenuJeuBinding

class SelectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuJeuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuJeuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var themeChoisi = ""
        var difficulteChoisi = ""

        binding.imgBtnAnimaux.setOnClickListener {
            themeChoisi = "Animal"
            // Você pode querer alterar a aparência do ImageButton para indicar que ele foi selecionado

        }

        binding.imgBtnPays.setOnClickListener {
            themeChoisi = "Pays"
            // Alterar a aparência do ImageButton, se necessário
        }

        binding.imgBtnInstumentsMusique.setOnClickListener {
            themeChoisi = "Pays"
            // Alterar a aparência do ImageButton, se necessário
        }

        binding.imgBtnVoitures.setOnClickListener {
            themeChoisi = "Pays"
            // Alterar a aparência do ImageButton, se necessário
        }

        // Faça o mesmo para os outros botões de tema

        binding.groupeRadioDifficulte.setOnCheckedChangeListener { group, checkedId ->
            // Fait la même chose pour les niveaux de difficulté
            difficulteChoisi = when (checkedId) {
                R.id.radioBtnFacille -> "Facile"
                R.id.radioBtnMoyen -> "Moyen"
                R.id.radioBtnDifficile -> "Difficile"
                else -> ""
            }
        }

        binding.btnJouer.setOnClickListener {
            // Vérifiez si un thème et un niveau de difficulté ont été sélectionnés
            if (themeChoisi.isNotEmpty() && difficulteChoisi.isNotEmpty()) {
                // Si oui, commencez JeuActivity avec un Intent, en passant les détails de la sélection comme extras
                val intent = Intent(this, JeuActivity::class.java).apply {
                    putExtra("theme", themeChoisi)
                    putExtra("difficulty", difficulteChoisi)
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, "Veuillez sélectionner un thème et un niveau de difficulté.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}