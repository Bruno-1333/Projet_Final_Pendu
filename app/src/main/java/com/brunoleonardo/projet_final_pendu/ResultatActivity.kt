package com.brunoleonardo.projet_final_pendu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.brunoleonardo.projet_final_pendu.databinding.ActivityResultatBinding

class ResultatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val resultat = intent.getBooleanExtra("resultat", false)
        val mot = intent.getStringExtra("mot")
    }
}