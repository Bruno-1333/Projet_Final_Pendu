package com.brunoleonardo.projet_final_pendu

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.SimpleAdapter
import com.brunoleonardo.projet_final_pendu.databinding.ActivityResultatBinding

class ResultatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)  // Configure a Toolbar como ActionBar


        // Suponha que os dados do usuário são esses:
        val user = "Utilisateur 1"
        val victories = "5 victoires"

        val data = listOf(user, victories)

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, data)
        binding.listResultat.adapter = adapter

        // Adicione o ouvinte de clique ao botão Rejouer
        binding.btnRejouer.setOnClickListener {
            val intent = Intent(this, JeuActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    // Substitua isso com os dados reais do usuário do seu banco de dados
    /*
    fun getCurrentUserFromDatabase(): String {
        // Retorna o nome do usuário atual
    }

    fun getVictoriesForUser(user: String): String {
        // Retorna a contagem de vitórias para o usuário
    }
    */

    // Inflar o menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflar o menu; isso adiciona itens à barra de ação, se estiver presente.
        menuInflater.inflate(com.brunoleonardo.projet_final_pendu.R.menu.menu_jeu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            com.brunoleonardo.projet_final_pendu.R.id.action_retour -> {
                val intent = Intent(this, PanneauJeuActivity::class.java)
                startActivity(intent)
                finish()
                true
            }
            com.brunoleonardo.projet_final_pendu.R.id.action_sortir -> {
                finishAffinity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}



