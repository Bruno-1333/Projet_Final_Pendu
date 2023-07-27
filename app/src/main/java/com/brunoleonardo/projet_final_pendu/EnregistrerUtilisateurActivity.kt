package com.brunoleonardo.projet_final_pendu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class EnregistrerUtilisateurActivity : AppCompatActivity() {

    // Crier une activité pour enregistrer un utilisateur
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enregistrer_utilisateur)
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

