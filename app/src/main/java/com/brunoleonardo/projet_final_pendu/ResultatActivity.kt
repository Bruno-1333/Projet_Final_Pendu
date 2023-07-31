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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)  // Configure a Toolbar como ActionBar

        // obtenha o número de vitórias do usuário a partir do Intent
        val victories = intent.getIntExtra("victoires", 0)

        // Suponha que os dados do usuário são esses:
        val user = obtenirUtilisateurActuelDeBaseDeDonnees()
        val victoriesText = "$victories vitórias"

        val data = listOf(user, victoriesText)

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
    fun obtenirUtilisateurActuelDeBaseDeDonnees(): String {
        // Retorna o nome do usuário atual
        return "nome_do_usuario" // Substitua isso com a implementação real que busca os dados do banco de dados
    }

    fun obtenirVictoiresPourUtilisateur(user: String): String {
        // Retorna a contagem de vitórias para o usuário
        return "10" // Substitua isso com a implementação real que busca os dados do banco de dados
    }

    // Inflar o menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflar o menu; isso adiciona itens à barra de ação, se estiver presente.
        menuInflater.inflate(R.menu.menu_jeu, menu)
        return true
    }

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




