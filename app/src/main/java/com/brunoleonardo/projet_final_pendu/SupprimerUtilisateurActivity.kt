package com.brunoleonardo.projet_final_pendu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.brunoleonardo.projet_final_pendu.databinding.ActivitySupprimerUtilisateurBinding

class SupprimerUtilisateurActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySupprimerUtilisateurBinding
    private lateinit var adapter: ArrayAdapter<String>

    // Creer l'activite
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySupprimerUtilisateurBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Deffinir la barre de menu
        binding.menuBack.setOnClickListener {
            finish()
        }

        // DBHandler pour accéder à la base de données
        val dbHandler = DBHandler(this)

        // Function pour mettre à jour la liste des utilisateurs
        fun updateAdapter() {
            val utilisateurs = dbHandler.rechercheUtilisateurs()
            val formattedData = utilisateurs.map { "${it.id} - ${it.nomUtilisateur}" }
            adapter.clear()
            adapter.addAll(formattedData)
            adapter.notifyDataSetChanged()
        }

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf())
        binding.ListViewSupprimerAdmUtilisateur.adapter = adapter

        // Charger la liste des utilisateurs
        updateAdapter()

        // botton pour supprimer un utilisateur
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

        // Botton pour supprimer tous les utilisateurs
        binding.btnSupprimerAdmTouteUtilisateur.setOnClickListener {
            dbHandler.supprimmerToutesLesUtilisateurs()
            updateAdapter()
        }
    }
}

