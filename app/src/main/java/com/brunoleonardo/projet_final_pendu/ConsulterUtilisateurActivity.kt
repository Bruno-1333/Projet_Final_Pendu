package com.brunoleonardo.projet_final_pendu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.brunoleonardo.projet_final_pendu.databinding.ActivityConsulterUtilisateurBinding

class ConsulterUtilisateurActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConsulterUtilisateurBinding
    private lateinit var adapter: ArrayAdapter<String>

    // Creer l'activite
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConsulterUtilisateurBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Deffinition de la barre de menu
        binding.menuBack.setOnClickListener {
            finish()
        }

        // Deffinition de la base de donnees
        val dbHandler = DBHandler(this)

        // Function pour mettre a jour l'adapteur
        fun updateAdapter(utilisateurs: List<Utilisateur>) {
            val formattedData = utilisateurs.map { "${it.nomUtilisateur}\n${it.motDePasse}" }
            adapter.clear()
            adapter.addAll(formattedData)
            adapter.notifyDataSetChanged()
        }

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf())
        binding.ListViewConsulterUtilisateur.adapter = adapter

        // Charger tous les utilisateurs
        val allUtilisateurs = dbHandler.rechercheUtilisateurs()
        updateAdapter(allUtilisateurs)

        // Chercher les utilisateurs
        binding.btnChercherAdmParUtilisateur.setOnClickListener {
            val utilisateurText = binding.editTxtChercherAdmParUtilisateur.text.toString()
            if (utilisateurText.isBlank()) {
                val allUtilisateurs = dbHandler.rechercheUtilisateurs()
                updateAdapter(allUtilisateurs)
            } else {
                val utilisateur = dbHandler.chercherParUtilisateur(utilisateurText)
                utilisateur?.let { updateAdapter(listOf(it)) }
                binding.editTxtChercherAdmParUtilisateur.setText("")
            }
        }

        // Chercher les utilisateurs par id
        binding.btnChercherAdmParId.setOnClickListener {
            val idText = binding.txtChaercherAdmUitlisateurParId.text.toString()
            val utilisateur = dbHandler.chercherUtilisateurParId(idText.toInt())
            utilisateur?.let { updateAdapter(listOf(it)) }
            binding.txtChaercherAdmUitlisateurParId.setText("")
        }
    }
}


