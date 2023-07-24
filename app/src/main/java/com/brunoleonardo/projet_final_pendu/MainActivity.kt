package com.brunoleonardo.projet_final_pendu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.brunoleonardo.projet_final_pendu.databinding.ActivityMainBinding
import java.io.Serializable


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    // Criação da lista para armazenar os utilizadores
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

        binding.btnJouerLogin.setOnClickListener {
            // Criamos um novo usuário padrão quando nenhum usuário está logado.
            val utilisateur = Utilisateur(1, "Guest", "guest", "123456", false)

            val intent = Intent(this, PanneauJeuActivity::class.java).apply {
                putExtra("utilisateur", utilisateur)
            }
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
}






