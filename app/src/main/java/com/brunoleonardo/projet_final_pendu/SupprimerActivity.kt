package com.brunoleonardo.projet_final_pendu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.brunoleonardo.projet_final_pendu.databinding.ActivitySupprimerBinding

class SupprimerActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySupprimerBinding
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySupprimerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dbHandler = DBHandler(this)

        // Função de atualização do ArrayAdapter
        fun updateAdapter(mots: List<Mot>) {
            val formattedData = mots.map { "${it.id}\n${it.mot}\n${it.theme}\n${it.niveauDifficulte}" }
            adapter.clear()
            adapter.addAll(formattedData)
            adapter.notifyDataSetChanged()
        }

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf())
        binding.ListViewSupprimerMot.adapter = adapter

        // Carregando todos os mots na abertura da Activity
        val mots = dbHandler.chercherMots()
        updateAdapter(mots)

        binding.btnSupprimerMot.setOnClickListener {
            val idText = binding.editTxtSupprimerMot.text.toString()
            dbHandler.supprimerMot(idText.toInt())

            // Atualizando a lista após a remoção
            val updatedMots = dbHandler.chercherMots()
            updateAdapter(updatedMots)

            binding.editTxtSupprimerMot.setText("")
        }

        binding.btnSupprimerToutMot.setOnClickListener {
            dbHandler.supprimerToutMot()

            // Atualizando a lista após a remoção
            val updatedMots = dbHandler.chercherMots()
            updateAdapter(updatedMots)
        }

        binding.menuBack.setOnClickListener {
            finish()
        }
    }
}





