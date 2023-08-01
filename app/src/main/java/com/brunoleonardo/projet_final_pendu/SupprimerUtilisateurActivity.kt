package com.brunoleonardo.projet_final_pendu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.brunoleonardo.projet_final_pendu.databinding.ActivitySupprimerUtilisateurBinding

class SupprimerUtilisateurActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySupprimerUtilisateurBinding
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySupprimerUtilisateurBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Definir o OnClickListener para o botão Voltar
        binding.menuBack.setOnClickListener {
            finish()
        }

        val dbHandler = DBHandler(this)

        // Função de atualização do ArrayAdapter
        fun updateAdapter() {
            val utilisateurs = dbHandler.rechercheUtilisateurs()
            val formattedData = utilisateurs.map { "${it.id} - ${it.nomUtilisateur}" }
            adapter.clear()
            adapter.addAll(formattedData)
            adapter.notifyDataSetChanged()
        }

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf())
        binding.ListViewSupprimerAdmUtilisateur.adapter = adapter

        // Carregar todos os 'utilisateurs' na lista ao abrir a atividade
        updateAdapter()

        binding.btnSupprimerAdmUtilisateur.setOnClickListener {
            val idText = binding.editTxtSupprimerAdmUtilisateur.text.toString()
            val utilisateur = dbHandler.chercherUtilisateurParId(idText.toInt())
            if (utilisateur != null) {
                dbHandler.supprimerUtilisateur(utilisateur)
                updateAdapter()
                Toast.makeText(this, getString(R.string.msg_utilisateur_supprime), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, getString(R.string.utilisateur_non_trouve), Toast.LENGTH_SHORT).show()
            }
            binding.editTxtSupprimerAdmUtilisateur.setText("")
        }

        // Botão para supprimer todos os utilizadores
        binding.btnSupprimerAdmTouteUtilisateur.setOnClickListener {
            dbHandler.supprimmerToutesLesUtilisateurs()
            updateAdapter()
        }
    }
}

