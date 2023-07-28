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

        val dbHandler = DBHandler(this)

        val nomUtilisateur = binding.editUtilisateurUser.text.toString()
        val motDePasse = binding.editUtilisateurMotPasse.text.toString()

        binding.btnEnregistrerUtilisateur.setOnClickListener {
            val utilisateur = Utilisateur(1, nomUtilisateur, motDePasse)
            dbHandler.ajouterUtilisateur(utilisateur)

        }
    }
}


/*import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.brunoleonardo.projet_final_pendu.DBHandler
import com.brunoleonardo.projet_final_pendu.Utilisateur
import com.brunoleonardo.projet_final_pendu.databinding.ActivityEnregistrerUtilisateurBinding

class EnregistrerUtilisateurActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEnregistrerUtilisateurBinding
    private var dbHandler: DBHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnregistrerUtilisateurBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHandler = DBHandler(this)

        binding.btnEnregistrer.setOnClickListener { registerUser() }
    }

    private fun registerUser() {
        val nom = binding.edtNom.text.toString()
        val nomUtilisateur = binding.edtNomUtilisateur.text.toString()
        val motDePasse = binding.edtMotDePasse.text.toString()

        if (nom.isEmpty() || nomUtilisateur.isEmpty() || motDePasse.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
            return
        }

        // Usuários serão registrados como não-administradores por padrão
        val nouveauUtilisateur = Utilisateur(nom = nom, nomUtilisateur = nomUtilisateur, motDePasse = motDePasse)
        dbHandler!!.addUtilisateur(nouveauUtilisateur)


    }
}*/

