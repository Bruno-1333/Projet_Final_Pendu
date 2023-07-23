package com.brunoleonardo.projet_final_pendu

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.brunoleonardo.projet_final_pendu.databinding.ActivityJeuBinding
import java.util.Locale


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

        Log.d("JeuActivity", "Theme: $theme, Difficulty: $difficulte")


        // Baseado na dificuldade escolhida, escolha uma palavra para adivinhar
        val mot: String = when (difficulte?.trim()?.toLowerCase(Locale.ROOT)) {
            "facile" -> /* realizar query para palavra de 5 letras baseada no tema */
                "LAPIN"  // substituir por um comando de busca no banco de dados
            "moyen" -> /* realizar query para palavra de 6 letras baseada no tema */
                "GIRAFE"  // substituir por um comando de busca no banco de dados
            "difficile" -> /* realizar query para palavra de 7 letras baseada no tema */
                "POISSON"  // substituir por um comando de busca no banco de dados
            else -> "ANIMAUX" // padrão caso nada seja passado
        }

        val description = "Ceci est une description de la parole." // substituir por um comando de busca no banco de dados
        binding.txtDescription.text = description

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
        binding.btnEssayer.setOnClickListener {
            val lettre = binding.txtSaissirLettre.text.toString().firstOrNull()
            lettre?.let {
                if (mot.contains(it, true)) {
                    devinerLettre(it, mot)
                } else {
                    devinerIncorrect(it)
                }
            }
        }

        // Reinitialiser le jeu
        binding.btnRejouer.setOnClickListener {
            reinitialiserJeu()
        }

        // Desactiver le bouton de reinitialisation
        binding.btnRejouer.isEnabled = false
    }

    // Quando uma letra é adivinhada corretamente, substitua o "_" correspondente
    fun devinerLettre(lettre: Char, mot: String) {
        val indices = mot.indices.filter { mot[it].toLowerCase() == lettre.toLowerCase() }  // Isso retorna os índices de 'lettre' em 'mot'
        indices.forEach { index ->
            vuesLettres[index].text = lettre.toString().toUpperCase()
        }

        // Aqui você pode verificar se a palavra foi completamente adivinhada
        // Se sim, você pode querer gravar no banco de dados que o usuário ganhou e levar o usuário para a próxima tela
        if(!vuesLettres.any { it.text == "_" }) {
            // l'utilisateur a gagné le jeu, enregistrez dans la base de données et faites une action
            gameOver(true)
        }

        // Por fim, limpe o campo de entrada após cada tentativa
        binding.txtSaissirLettre.text.clear()
    }

    // Quando uma letra é adivinhada incorretamente
    fun devinerIncorrect(lettre: Char) {
        if(lettresIncorrectes.contains(lettre)) {
            // l'utilisateur a déjà deviné cette lettre, ne faites rien
            Toast.makeText(this, "Cette lettre a deja utilise", Toast.LENGTH_SHORT).show()
            return
        }

        lettresIncorrectes.add(lettre)
        binding.txtMauvaisMot.text = lettresIncorrectes.joinToString(" ")
        binding.txtMauvaisMot.setTextColor(ContextCompat.getColor(this, R.color.red))

        // Mis à jour de l'image
        miseAJourImage(lettresIncorrectes.size)

        // effacer le champ de saisie
        binding.txtSaissirLettre.text.clear()

        // Verifie si l'utilisateur a perdu
        if (lettresIncorrectes.size >= 10) {
            // l'utilisateur a perdu le jeu, enregistrez dans la base de données et faites une action
            gameOver(false)
        }
    }

    // Quando o jogo termina, você pode querer salvar o resultado no banco de dados e levar o usuário para a próxima tela
    fun gameOver(victoire: Boolean) {
        if(victoire) {
            Toast.makeText(this, "Vous avez gagné!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Vous avez perdu!", Toast.LENGTH_SHORT).show()
            binding.btnEssayer.isEnabled = false
            binding.txtSaissirLettre.isEnabled = false

            // Habilita o botão 'btnRejouer'
            binding.btnRejouer.isEnabled = true
        }
    }

    fun reinitialiserJeu() {
        // Redémarrer l'activité

        // Reinitializer les lettres incorrectes
        binding.txtSaissirLettre.text.clear()
        binding.txtMauvaisMot.text = ""

        // Reinitializer les lettres correctes
        vuesLettres.forEach { it.text = "_" }

        // Reinitializer les lettres incorrectes
        lettresIncorrectes.clear()

        // Reinitializer l'image
        binding.zoneImgPendu.setImageResource(R.drawable.img_default)

        // Reinitializer les entrees des boutons
        binding.btnEssayer.isEnabled = true
        binding.txtSaissirLettre.isEnabled = true

        // Desactiver le bouton 'btnRejouer'
        binding.btnRejouer.isEnabled = false
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
        binding.zoneImgPendu.setImageResource(ressourceImage)
    }
}


