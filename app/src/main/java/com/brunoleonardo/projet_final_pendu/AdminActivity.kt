package com.brunoleonardo.projet_final_pendu

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.brunoleonardo.projet_final_pendu.databinding.ActivityAdminBinding

class AdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminBinding

    // Creer l'activite
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Deffinir la barre de menu
        binding.menuBack.setOnClickListener {
            finish()
        }

        // Ajouter les mots
        binding.btnAjouter.setOnClickListener {
            val intent = Intent(this, AjouterActivity::class.java)
            startActivity(intent)
        }

        // Consulter les mots
        binding.btnConsulter.setOnClickListener {
            val intent = Intent(this, ConsulterActivity::class.java)
            startActivity(intent)
        }

        // Supprimer les mots
        binding.btnSupprimer.setOnClickListener {
            val intent = Intent(this, SupprimerActivity::class.java)
            startActivity(intent)
        }

        // Ajouter les utilisateurs
        binding.btnEnregistrerUtilisateur.setOnClickListener {
            val intent = Intent(this, EnregistrerUtilisateurAdminActivity::class.java)
            startActivity(intent)
        }

        // Consulter les utilisateurs
        binding.btnConsulterUtilisateur.setOnClickListener {
            val intent = Intent(this, ConsulterUtilisateurActivity::class.java)
            startActivity(intent)
        }

       // Supprimer les utilisateurs
        binding.btnSupprimerUtilisateur.setOnClickListener {
            val intent = Intent(this, SupprimerUtilisateurActivity::class.java)
            startActivity(intent)
        }
    }

    // Capturer le clic sur le bouton "Retour"
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            // Identifier le bouton "Retour"
            R.id.menu_back -> {
                // Implementer le code de retour
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}


