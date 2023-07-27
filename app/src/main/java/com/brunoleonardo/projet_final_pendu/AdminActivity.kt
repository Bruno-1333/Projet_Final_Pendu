package com.brunoleonardo.projet_final_pendu

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.brunoleonardo.projet_final_pendu.databinding.ActivityAdminBinding

class AdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAjouter.setOnClickListener {
            val intent = Intent(this, AjouterActivity::class.java)
            startActivity(intent)
        }

        binding.btnConsulter.setOnClickListener {
            val intent = Intent(this, ConsulterActivity::class.java)
            startActivity(intent)
        }

        binding.btnModifier.setOnClickListener {
            val intent = Intent(this, ModifierActivity::class.java)
            startActivity(intent)
        }

        binding.btnSupprimer.setOnClickListener {
            val intent = Intent(this, SupprimerActivity::class.java)
            startActivity(intent)
        }
    }
}
