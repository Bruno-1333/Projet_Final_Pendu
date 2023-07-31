package com.brunoleonardo.projet_final_pendu

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.brunoleonardo.projet_final_pendu.Constantes
import com.brunoleonardo.projet_final_pendu.DBHandler
import com.brunoleonardo.projet_final_pendu.Mot
import com.brunoleonardo.projet_final_pendu.databinding.ActivityAjouterBinding

class AjouterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAjouterBinding
    private lateinit var dbHandler: DBHandler // Declaração da instância dbHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAjouterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.menuBack.setOnClickListener {
            finish()
        }

        dbHandler = DBHandler(this) // Inicialização de dbHandler

        binding.btnEnregistrer.setOnClickListener {
            val mot = binding.edtMot.text.toString().trim()
            val description = binding.edtDescriptionMot.text.toString().trim()

            val themeId = binding.radioGroup.checkedRadioButtonId
            val niveauId = binding.radioGroupNiveau.checkedRadioButtonId

            if (mot.isNotEmpty() && description.isNotEmpty() && themeId != -1 && niveauId != -1) {
                val theme = when (themeId) {
                    R.id.radioBtnAnimaux -> "Animaux"
                    R.id.radioBtnPays -> "Pays"
                    R.id.radioBtnMusique -> "Musique"
                    R.id.radioBtnVoitures -> "Voitures"
                    else -> ""
                }

                val niveau = when (niveauId) {
                    R.id.radioBtnFacile -> "Facile"
                    R.id.radioBtnMoyen -> "Moyen"
                    R.id.radioBtnDifficile -> "Difficile"
                    else -> ""
                }

                val motObj = Mot(0, mot, description, theme, niveau)
                dbHandler.ajouterMot(motObj)

                binding.edtMot.text.clear()
                binding.edtDescriptionMot.text.clear()
                binding.radioGroup.clearCheck()
                binding.radioGroupNiveau.clearCheck()

                Toast.makeText(this, "Mot adicionado com sucesso!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dbHandler.close() // Feche o banco de dados quando a atividade for destruída
    }
}

