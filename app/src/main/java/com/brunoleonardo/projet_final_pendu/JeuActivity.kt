package com.brunoleonardo.projet_final_pendu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import com.brunoleonardo.projet_final_pendu.databinding.ActivityJeuBinding

class JeuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityJeuBinding
    private lateinit var vuesLettres: List<TextView>
    private val lettresIncorrectes = mutableListOf<Char>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJeuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Récupération des données de l'activité précédente (PanneauJeuActivity)
        val theme = intent.getStringExtra("theme")
        val difficulte = intent.getStringExtra("difficulte")

        // Aqui você deve consultar o banco de dados e buscar uma palavra baseada no tema e na dificuldade
        // Suponha que essa seja a palavra a ser adivinhada
        val mot = "ANIMAUX" // substituir por um comando de busca no banco de dados

        // Mantenha uma lista das TextViews
        vuesLettres = mot.map { lettre ->
            TextView(this).apply {
                text = "_"
                textSize = 24f
                setPadding(8, 0, 8, 0)  // Adicione algum padding para espaçar as letras
            }
        }

        // Adicione as TextViews ao LinearLayout
        val motLayout = findViewById<LinearLayout>(R.id.motLayout)
        vuesLettres.forEach { textView ->
            motLayout.addView(textView)
        }

        // Quando o botão Adivinhar for clicado
        binding.btnGuess.setOnClickListener {
            val lettre = binding.txtSaissir.text.toString().firstOrNull()
            lettre?.let {
                if (mot.contains(it, true)) {
                    devinerLettre(it, mot)
                } else {
                    devinerIncorrect(it)
                }
            }
        }
    }

    // Quando uma letra é adivinhada corretamente, substitua o "_" correspondente
    fun devinerLettre(lettre: Char, mot: String) {
        val indices = mot.indices.filter { mot[it].toLowerCase() == lettre.toLowerCase() }  // Isso retorna os índices de 'lettre' em 'mot'
        indices.forEach { index ->
            vuesLettres[index].text = lettre.toString()
        }

        // Aqui você pode verificar se a palavra foi completamente adivinhada
        // Se sim, você pode querer gravar no banco de dados que o usuário ganhou e levar o usuário para a próxima tela

        // Por fim, limpe o campo de entrada após cada tentativa
        binding.txtSaissir.text.clear()
    }

    // Quando uma letra é adivinhada incorretamente
    fun devinerIncorrect(lettre: Char) {
        lettresIncorrectes.add(lettre)
        binding.textView3.text = lettresIncorrectes.joinToString(" ")
        miseAJourImage(lettresIncorrectes.size)
        if (lettresIncorrectes.size >= 10) {
            // l'utilisateur a perdu le jeu, enregistrez dans la base de données et faites une action
        }
    }

    fun miseAJourImage(tentatives: Int) {
        // Met à jour l'image en fonction du nombre de tentatives
        val ressourceImage = when (tentatives) {
            1 -> R.drawable.img_01
            2 -> R.drawable.img_02
            3 -> R.drawable.img_03
            4 -> R.drawable.img_04
            5 -> R.drawable.img_05
            6 -> R.drawable.img_06
            7 -> R.drawable.img_07
            8 -> R.drawable.img_08
            9 -> R.drawable.img_09
            10 -> R.drawable.img_10
            // ... ajoutez plus de cas si nécessaire
            else -> R.drawable.img_default
        }
        binding.imageView.setImageResource(ressourceImage)
    }
}


