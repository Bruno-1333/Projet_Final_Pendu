package com.brunoleonardo.projet_final_pendu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import com.brunoleonardo.projet_final_pendu.databinding.ActivityJeuBinding

class JeuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityJeuBinding
    private lateinit var letterViews: List<TextView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJeuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Récupération des données de l'activité précédente (PanneauJeuActivity)
        val theme = intent.getStringExtra("theme")
        val difficulty = intent.getStringExtra("difficulte")

        // Aqui você deve consultar o banco de dados e buscar uma palavra baseada no tema e na dificuldade
        // Suponha que essa seja a palavra a ser adivinhada
        val mot = "ANIMAUX" // substituir por um comando de busca no banco de dados

        // Mantenha uma lista das TextViews
        letterViews = mot.map { letter ->
            TextView(this).apply {
                text = "_"
                textSize = 24f
                setPadding(8, 0, 8, 0)  // Adicione algum padding para espaçar as letras
            }
        }

        // Adicione as TextViews ao LinearLayout
        val motLayout = findViewById<LinearLayout>(R.id.motLayout)
        letterViews.forEach { textView ->
            motLayout.addView(textView)
        }

        // Quando o botão Adivinhar for clicado
        binding.btnGuess.setOnClickListener {
            val letter = binding.txtSaissir.text.firstOrNull()
            letter?.let { guessLetter(it, mot) }
        }
    }

    // Quando uma letra é adivinhada corretamente, substitua o "_" correspondente
    fun guessLetter(letter: Char, mot: String) {
        val indices = mot.indices.filter { mot[it] == letter }  // Isso retorna os índices de 'letter' em 'word'
        indices.forEach { index ->
            letterViews[index].text = letter.toString()
        }

        // Aqui você pode verificar se a palavra foi completamente adivinhada
        // Se sim, você pode querer gravar no banco de dados que o usuário ganhou e levar o usuário para a próxima tela

        // Por fim, limpe o campo de entrada após cada tentativa
        binding.txtSaissir.text.clear()
    }

    // Você também precisa implementar uma função para lidar com palpites errados
    // Nessa função, você pode adicionar a letra errada a uma lista de letras erradas,
    // incrementar o contador de tentativas e atualizar a imagem correspondente.
    // Se o usuário atingir o número máximo de tentativas, você deve gravar no banco de dados que o usuário perdeu
}
