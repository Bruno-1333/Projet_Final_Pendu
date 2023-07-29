package com.brunoleonardo.projet_final_pendu

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.brunoleonardo.projet_final_pendu.databinding.ActivityConsulterBinding

class ConsulterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConsulterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConsulterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dbHandler = DBHandler(this)

        val utilisateurs = dbHandler.chercherMots()
        val adapter = ArrayAdapter(this, R.layout.simple_list_item_1, utilisateurs)
        binding.listVIEWConsulter.adapter = adapter


    }
}