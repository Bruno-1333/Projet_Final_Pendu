package com.brunoleonardo.projet_final_pendu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.brunoleonardo.projet_final_pendu.databinding.ActivityJeuBinding

class JeuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityJeuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJeuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Récupération des données de l'activité précédente (PanneauJeuActivity)
        val theme = intent.getStringExtra("theme")
        val difficulty = intent.getStringExtra("difficulte")

    }
}