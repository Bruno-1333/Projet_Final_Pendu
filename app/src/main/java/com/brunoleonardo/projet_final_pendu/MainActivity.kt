package com.brunoleonardo.projet_final_pendu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.brunoleonardo.projet_final_pendu.databinding.ActivityMainBinding
import com.brunoleonardo.projet_final_pendu.databinding.ActivityMenuJeuBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    // Créer une liste pour stocker les utilisateurs
    private val utilisateurs = mutableListOf<Utilisateur>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Adicionar um administrador por padrão
        utilisateurs.add(Utilisateur(0, "Admin", "admin", "admin", true))

        binding.btnEntrerLogin.setOnClickListener {
            val nomUtilisateur = binding.txtUtilisateurLogin.text.toString()
            val motDePasse = binding.txtMotPasseLogin.text.toString()

            val utilisateur = isValidUtilisateur(nomUtilisateur, motDePasse)
            if (utilisateur != null) {
                val intent = if (utilisateur.isAdministrateur) {
                    Intent(this, AdminActivity::class.java)
                } else {
                    Intent(this, ActivityMenuJeuBinding::class.java)
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, "Nom d'utilisateur ou mot de passe invalide", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnJouerLogin.setOnClickListener {
            val intent = Intent(this, ActivityMenuJeuBinding::class.java)
            startActivity(intent)
        }

        binding.btnEnregistrerUtilisateurLogin.setOnClickListener {
            val intent = Intent(this, EnregistrerUtilisateurActivity::class.java)
            startActivity(intent)
        }
    }

    private fun isValidUtilisateur(nomUtilisateur: String, motDePasse: String): Utilisateur? {
        for (utilisateur in utilisateurs) {
            if (utilisateur.nomUtilisateur == nomUtilisateur && utilisateur.motDePasse == motDePasse) {
                return utilisateur
            }
        }
        return null
    }

    data class Utilisateur(val id: Int, val nom: String, val nomUtilisateur: String, val motDePasse: String, val isAdministrateur: Boolean)
}



