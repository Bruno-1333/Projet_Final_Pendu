package com.brunoleonardo.projet_final_pendu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.brunoleonardo.projet_final_pendu.databinding.ActivityEnregistrerUtilisateurBinding

/*class EnregistrerUtilisateurActivity : AppCompatActivity() {
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
            val nomUtilisateur = binding.editTxtNomUtilisateur.text.toString()
            val motDePasse = binding.editTxtMotDePasse.text.toString()

            if(nomUtilisateur.isNotEmpty() && motDePasse.isNotEmpty()){
                val utilisateur = Utilisateur(0, nomUtilisateur, motDePasse)
                dbHandler.enregistrerUtilisateur(utilisateur)
                Toast.makeText(this, "Utilisateur enregistré", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Veuillez entrer tous les champs", Toast.LENGTH_SHORT).show()
            }

            // Limpar os campos de entrada após o registro
            binding.editTxtNomUtilisateur.setText("")
            binding.editTxtMotDePasse.setText("")
        }
    }
}*/
