package com.brunoleonardo.projet_final_pendu

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.brunoleonardo.projet_final_pendu.databinding.ActivityAdminBinding

class AdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Definir o OnClickListener para o botão Voltar
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
            val intent = Intent(this, EnregistrerUtilisateurAdminActivity::class.java) // Aqui você deve substituir pelo nome da sua Activity de adicionar usuários
            startActivity(intent)
        }

        // Consulter les utilisateurs
        binding.btnConsulterUtilisateur.setOnClickListener {
            val intent = Intent(this, ConsulterUtilisateurActivity::class.java) // Aqui você deve substituir pelo nome da sua Activity de consultar usuários
            startActivity(intent)
        }

       // Supprimer les utilisateurs
        binding.btnSupprimerUtilisateur.setOnClickListener {
            val intent = Intent(this, SupprimerUtilisateurActivity::class.java) // Aqui você deve substituir pelo nome da sua Activity de suprimir usuários
            startActivity(intent)
        }
    }

    // Capturar o evento de clique do item de menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            // Identificar o item de menu pelo ID
            R.id.menu_back -> {
                // Implementar o código para retornar à Activity_Admin
                onBackPressed() // Essa chamada irá executar a lógica de retorno padrão (como pressionar o botão "Voltar")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}


