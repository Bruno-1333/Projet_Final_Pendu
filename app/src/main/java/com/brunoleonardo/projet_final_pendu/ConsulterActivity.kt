package com.brunoleonardo.projet_final_pendu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.brunoleonardo.projet_final_pendu.databinding.ActivityConsulterBinding

class ConsulterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConsulterBinding
    private lateinit var adapter: ArrayAdapter<String>

    // Creer l'activite
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConsulterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Deffinir onClickListener pour le bouton "Retour"
        binding.menuBack.setOnClickListener {
            finish()
        }

        // DBHandler pour accéder à la base de données
        val dbHandler = DBHandler(this)

        // Function pour mettre à jour l'adapter
        fun updateAdapter(mots: List<Mot>) {
            val formattedData = mots.map { "${it.mot}\n${it.theme}\n${it.niveauDifficulte}" }
            adapter.clear()
            adapter.addAll(formattedData)
            adapter.notifyDataSetChanged()
        }

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf())
        binding.listVIEWConsulter.adapter = adapter

        // Charger tous les mots
        val allMots = dbHandler.chercherMots()
        updateAdapter(allMots)

        // Chercher les mots
        binding.btnChercherParMot.setOnClickListener {
            val motText = binding.txtChercherParMot.text.toString()
            val mot = dbHandler.chercherParMot(motText)
            mot?.let { updateAdapter(listOf(it)) }
            binding.txtChercherParMot.setText("")
        }

        // Chercher les mots par id
        binding.btnChercherParId.setOnClickListener {
            val idText = binding.txtChercherParId.text.toString()
            val mot = dbHandler.chercherMotParId(idText.toInt())
            mot?.let { updateAdapter(listOf(it)) }
            binding.txtChercherParId.setText("")
        }
    }
}
