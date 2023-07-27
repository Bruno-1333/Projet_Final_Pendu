package com.brunoleonardo.projet_final_pendu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.brunoleonardo.projet_final_pendu.databinding.ActivityMainBinding
import java.io.Serializable

// crier une activité pour accéder au jeu
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    // Crier une liste d'utilisateurs
    private val utilisateurs = mutableListOf<Utilisateur>()

    // Vérifie si l'utilisateur existe et si le mot de passe est correct
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ajouter un utilisateur par défaut
        utilisateurs.add(Utilisateur(0, "Admin", "admin", "admin", true))

        // bouton pour accéder à l'activité d'enregistrement d'un utilisateur
        binding.btnEntrerLogin.setOnClickListener {
            val nomUtilisateur = binding.txtUtilisateurLogin.text.toString()
            val motDePasse = binding.txtMotPasseLogin.text.toString()

            val utilisateur = isValidUtilisateur(nomUtilisateur, motDePasse)
            if (utilisateur != null) {
                val intent = if (utilisateur.isAdministrateur) {
                    Intent(this, AdminActivity::class.java).apply {
                        putExtra("utilisateur", utilisateur)
                    }
                } else {
                    Intent(this, PanneauJeuActivity::class.java).apply {
                        putExtra("utilisateur", utilisateur)
                    }
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, "Nom d'utilisateur ou mot de passe invalide", Toast.LENGTH_SHORT).show()
            }
        }

        // bouton pour accéder à l'activité de jeu sans se enregistrer
        binding.btnJouerLogin.setOnClickListener {
            // Creer un utilisateur par défaut
            val utilisateur = Utilisateur(1, "Anonyme", "anonyme", "123456", false)

            val intent = Intent(this, PanneauJeuActivity::class.java).apply {
                putExtra("utilisateur", utilisateur)
            }
            startActivity(intent)
        }

        // bouton pour accéder à l'activité d'enregistrement d'un utilisateur
        binding.btnEnregistrerUtilisateurLogin.setOnClickListener {
            val intent = Intent(this, EnregistrerUtilisateurActivity::class.java)
            startActivity(intent)
        }
    }

    // Vérifie si l'utilisateur existe et si le mot de passe est correct
    private fun isValidUtilisateur(nomUtilisateur: String, motDePasse: String): Utilisateur? {
        for (utilisateur in utilisateurs) {
            if (utilisateur.nomUtilisateur == nomUtilisateur && utilisateur.motDePasse == motDePasse) {
                return utilisateur
            }
        }
        return null
    }
}







