package com.brunoleonardo.projet_final_pendu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.brunoleonardo.projet_final_pendu.databinding.ActivityEnregistrerUtilisateurBinding

class EnregistrerUtilisateurActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEnregistrerUtilisateurBinding

    // Creer l'activite
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnregistrerUtilisateurBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Definit le bouton "Retour"
        binding.menuBack.setOnClickListener {
            finish()
        }

        // DBHandler pour accéder à la base de données
        val dbHandler = DBHandler(this)


        // Enregistrer un utilisateur
        binding.btnEnregistrerUtilisateur.setOnClickListener {

            val nomUtilisateur = binding.editUtilisateurUser.text.toString()
            val motDePasse = binding.editUtilisateurMotPasse.text.toString()

            val utilisateur = Utilisateur(1, nomUtilisateur, motDePasse)
            dbHandler.ajouterUtilisateur(utilisateur)

        }
    }
}


