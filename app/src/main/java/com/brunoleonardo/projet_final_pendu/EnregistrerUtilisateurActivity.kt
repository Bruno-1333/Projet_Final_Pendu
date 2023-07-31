package com.brunoleonardo.projet_final_pendu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.brunoleonardo.projet_final_pendu.databinding.ActivityEnregistrerUtilisateurBinding

class EnregistrerUtilisateurActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEnregistrerUtilisateurBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnregistrerUtilisateurBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Definir o OnClickListener para o botão Voltar
        binding.menuBack.setOnClickListener {
            finish()
        }

        val dbHandler = DBHandler(this)



        binding.btnEnregistrerUtilisateur.setOnClickListener {

            val nomUtilisateur = binding.editUtilisateurUser.text.toString()
            val motDePasse = binding.editUtilisateurMotPasse.text.toString()

            val utilisateur = Utilisateur(1, nomUtilisateur, motDePasse)
            dbHandler.ajouterUtilisateur(utilisateur)

        }
    }
}


