package com.brunoleonardo.projet_final_pendu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.brunoleonardo.projet_final_pendu.databinding.ActivityEnregistrerUtilisateurAdminBinding

class EnregistrerUtilisateurAdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEnregistrerUtilisateurAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnregistrerUtilisateurAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dbHandler = DBHandler(this)

        binding.btnEnregistrerAdmUtilisateur.setOnClickListener {
            val nomUtilisateur = binding.editTxtNomAdmUtilisateur.text.toString().trim()
            val motDePasse = binding.editTxtMotPasseAdmUtilisateur.text.toString().trim()

            if (nomUtilisateur.isEmpty()) {
                binding.editTxtNomAdmUtilisateur.error = "Please enter a name"
                binding.editTxtNomAdmUtilisateur.requestFocus()
                return@setOnClickListener
            }

            if (motDePasse.isEmpty()) {
                binding.editTxtMotPasseAdmUtilisateur.error = "Please enter a password"
                binding.editTxtMotPasseAdmUtilisateur.requestFocus()
                return@setOnClickListener
            }

            val utilisateur = Utilisateur(0, nomUtilisateur, motDePasse)
            dbHandler.ajouterUtilisateur(utilisateur)
            Toast.makeText(this, "Utilisateur enregistré", Toast.LENGTH_SHORT).show()

            // Limpar os campos de entrada após o registro
            binding.editTxtNomAdmUtilisateur.setText("")
            binding.editTxtMotPasseAdmUtilisateur.setText("")
        }
    }
}


