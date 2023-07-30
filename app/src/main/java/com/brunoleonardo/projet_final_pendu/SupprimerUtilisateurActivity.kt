package com.brunoleonardo.projet_final_pendu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.brunoleonardo.projet_final_pendu.databinding.ActivitySupprimerUtilisateurBinding

/*class SupprimerUtilisateurActivity : AppCompatActivity() {
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
        fun updateAdapter(utilisateurs: List<Utilisateur>) {
            val formattedData = utilisateurs.map { "${it.id} - ${it.nomUtilisateur}" }
            adapter.clear()
            adapter.addAll(formattedData)
            adapter.notifyDataSetChanged()
        }

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf())
        binding.listVIEWSupprimer.adapter = adapter

        // Carregar todos os 'utilisateurs' na lista ao abrir a atividade
        val allUtilisateurs = dbHandler.chercherTousUtilisateurs()
        updateAdapter(allUtilisateurs)

        binding.btnSupprimerParId.setOnClickListener {
            val idText = binding.editTxtSupprimerUtilisateur.text.toString()
            val utilisateur = dbHandler.chercherUtilisateurParId(idText.toInt())
            if (utilisateur != null) {
                dbHandler.supprimerUtilisateur(utilisateur)
                val updatedUtilisateurs = dbHandler.chercherTousUtilisateurs()
                updateAdapter(updatedUtilisateurs)
                Toast.makeText(this, "Utilisateur supprimé", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Utilisateur non trouvé", Toast.LENGTH_SHORT).show()
            }
            binding.editTxtSupprimerUtilisateur.setText("")
        }
    }
}*/
