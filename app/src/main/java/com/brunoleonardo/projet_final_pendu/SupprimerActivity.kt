package com.brunoleonardo.projet_final_pendu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.brunoleonardo.projet_final_pendu.databinding.ActivitySupprimerBinding

class SupprimerActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySupprimerBinding
    private lateinit var adapter: ArrayAdapter<String>

    // Creer l'activite
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySupprimerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dbHandler = DBHandler(this)

        // Function pour mettre à jour l'adapter
        fun updateAdapter(mots: List<Mot>) {
            val formattedData = mots.map { "${it.id}\n${it.mot}\n${it.theme}\n${it.niveauDifficulte}" }
            adapter.clear()
            adapter.addAll(formattedData)
            adapter.notifyDataSetChanged()
        }

        // Adapter pour afficher les mots
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf())
        binding.ListViewSupprimerMot.adapter = adapter

        // DBHandler pour accéder à la base de données
        val mots = dbHandler.chercherMots()
        updateAdapter(mots)

        // Supprimer un mot
        binding.btnSupprimerMot.setOnClickListener {
            val idText = binding.editTxtSupprimerMot.text.toString()
            dbHandler.supprimerMot(idText.toInt())

            val updatedMots = dbHandler.chercherMots()
            updateAdapter(updatedMots)

            binding.editTxtSupprimerMot.setText("")
        }

        // Supprimer tous les mots
        binding.btnSupprimerToutMot.setOnClickListener {
            dbHandler.supprimerToutMot()

            val updatedMots = dbHandler.chercherMots()
            updateAdapter(updatedMots)
        }

        // Definit le bouton "Retour"
        binding.menuBack.setOnClickListener {
            finish()
        }
    }
}





