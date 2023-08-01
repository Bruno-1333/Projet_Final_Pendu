package com.brunoleonardo.projet_final_pendu

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.brunoleonardo.projet_final_pendu.R
import com.brunoleonardo.projet_final_pendu.databinding.ActivityResultatBinding

class ResultatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultatBinding

    // Crée l'activité
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)  // Definit la barre d'outils

        // obtenir les données de l'intent
        val victories = intent.getIntExtra("victoires", 0)

        // obtenir le nom d'utilisateur actuel de la base de données
        val user = obtenirUtilisateurActuelDeBaseDeDonnees()
        val victoriesText = "$victories victoires"

        val data = listOf(user, victoriesText)

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, data)
        binding.listResultat.adapter = adapter

        // button "Rejouer"
        binding.btnRejouer.setOnClickListener {
            val intent = Intent(this, JeuActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    // Obtient le nom d'utilisateur actuel de la base de données
    fun obtenirUtilisateurActuelDeBaseDeDonnees(): String {
        // Retorna o nome do usuário atual
        return "nome_do_usuario" // Substitua isso com a implementação real que busca os dados do banco de dados
    }

    // Obtient le nombre de victoires pour l'utilisateur
    fun obtenirVictoiresPourUtilisateur(user: String): String {
        // Retorna a contagem de vitórias para o usuário
        return "10" // Substitua isso com a implementação real que busca os dados do banco de dados
    }

    // Inflar le menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_jeu, menu)
        return true
    }

    // Gérer les clics sur les éléments du menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_retour -> {
                val intent = Intent(this, PanneauJeuActivity::class.java)
                startActivity(intent)
                finish()
                true
            }
            R.id.action_sortir -> {
                finishAffinity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}




