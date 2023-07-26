package com.brunoleonardo.projet_final_pendu

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.brunoleonardo.projet_final_pendu.databinding.ActivityJeuBinding
import java.util.Locale

// Créer une activité pour le jeu
class JeuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityJeuBinding
    private lateinit var jeu: Jeu
    private var timer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJeuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Récupération des données de l'activité précédente (PanneauJeuActivity)
        val theme = intent.getStringExtra("theme") // récupérer le thème
        val difficulte = intent.getStringExtra("difficulte") // récupérer la difficulté

        // Créer un nouveau jeu
        val utilisateur = intent.getSerializableExtra("utilisateur") as? Utilisateur // récupérer l'utilisateur
        if (utilisateur == null) {
            Toast.makeText(this, "Utilisateur non trouvé", Toast.LENGTH_SHORT).show()
            finish()
            return
        }// récupérer l'utilisateur

        // ici on va chercher ou générer le mot et la description
        val mot = choisirMot(theme, difficulte) // chercher ou générer le mot baseado no tema e dificuldade
        val description = "descripcion_exemple" // chercher ou générer la description baseado no tema e dificuldade

        jeu = Jeu(utilisateur, theme ?: "defaultTheme", mot, description, difficulte ?: "defaultDifficulte") // créer un nouveau jeu

        binding.txtDescription.text = jeu.description // afficher la description

        creerVuesLettres() // créer les vues des lettres

        // Créer les vues des lettres incorrectes
        binding.btnEssayer.setOnClickListener {
            val lettre = binding.txtSaissirLettre.text.toString().firstOrNull()
            lettre?.let {
                if (!jeu.vuesLettres.contains(it) && !jeu.lettresIncorrectes.contains(it)) {
                    if (jeu.devinerLettre(it)) {
                        miseAJourLettresDevinees()
                    } else {
                        miseAJourLettresIncorrectes()
                    }
                    binding.txtSaissirLettre.setText("") // effacer le texte de la zone de texte
                } else {
                    Toast.makeText(this, "Vous deja utilise cette lettre!", Toast.LENGTH_SHORT).show() // afficher un message d'erreur
                }
            }
            verifierFinDePartie() // vérifier si la partie est terminée
            miseAJourLettresDevinees() // Mettre à jour les lettres devinées
        }

        // bouton pour rejouer le jeu avec le même utilisateur, thème et difficulté
        binding.btnRejouer.setOnClickListener {
            val intent = Intent(this, JeuActivity::class.java)
            intent.putExtra("utilisateur", jeu.utilisateur)
            intent.putExtra("theme", jeu.theme)
            intent.putExtra("difficulte", jeu.niveauDifficulte)
            startActivity(intent)
            finish()
        }

        // Mettre à jour l'image du pendu
        miseAJourImage(0)

        // Começa a contagem regressiva de 1 minutos (120000 ms)
        timer = object: CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val remainingSeconds = millisUntilFinished / 1000
                val minutes = remainingSeconds / 60
                val seconds = remainingSeconds % 60

                // Formata o tempo restante em "MM:SS" e atualiza o texto do TextView
                binding.txtChronometre.text = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                // A contagem regressiva acabou. Desabilita a tela e mostra a imagem
                binding.txtChronometre.text = "00:00"
                binding.zoneImgPendu.setImageResource(R.drawable.img_10)
                finirJeu()
            }
        }

        // Começa a contagem regressiva
        timer?.start()
    }

    // Choisir un mot selon le thème et la difficulté
    private fun choisirMot(theme: String?, difficulte: String?): String {
        val mots = mapOf(
            "Animaux" to mapOf( // Le map est utilisé pour associer le thème à la difficulté et à la liste de mots
                "Facile" to listOf("lion"), // 4 letras
                "Moyen" to listOf("panthère"), // 8 letras
                "Difficile" to listOf("rhinocéros") // 10 letras
            ),
            "Pays" to mapOf(
                "Facile" to listOf("Peru"), // 4 letras
                "Moyen" to listOf("Portugal"), // 8 letras
                "Difficile" to listOf("Afghanistan") // 11 letras
            ),
            "Instruments Musique" to mapOf(
                "Facile" to listOf("harp"), // 4 letras
                "Moyen" to listOf("trombone"), // 8 letras
                "Difficile" to listOf("clavicémbalo") // 12 letras
            ),
            "Voitures" to mapOf(
                "Facile" to listOf("Audi","Ford" ), // 4 letras
                "Moyen" to listOf("Mercedes"), // 8 letras
                "Difficile" to listOf("Lamborghini") // 11 letras
            ),
        )

        // mot par défaut si le thème ou la difficulté n'est pas trouvé
        val motParDefaut = "girafe"
        val motsThemeDifficulte = mots[theme]?.get(difficulte)
        return if (motsThemeDifficulte != null && motsThemeDifficulte.isNotEmpty()) {
            motsThemeDifficulte.random() // choisir un mot aléatoire
        } else {
            motParDefaut
        }
    }

    // Créer les vues des lettres
    private fun creerVuesLettres() {
        binding.motLayout.removeAllViews()

        jeu.vuesLettres.forEach { letter ->
            TextView(this).apply {
                text = if (letter == '_') "_" else letter.toString().toUpperCase()
                textSize = 24f
                setPadding(8, 0, 8, 0)
                binding.motLayout.addView(this)
            }
        }
    }

    // Mettre à jour les lettres devinées
    private fun miseAJourLettresDevinees() {
        for (i in jeu.vuesLettres.indices) {
            (binding.motLayout.getChildAt(i) as TextView).text =
                if (jeu.vuesLettres[i] == '_') "_" else jeu.vuesLettres[i].toString().toUpperCase()
        }
    }

    // Mettre à jour les lettres incorrectes
    private fun miseAJourLettresIncorrectes() {
        binding.txtMauvaisMot.text = jeu.lettresIncorrectes.joinToString(" ") { it.toString().toUpperCase() }
        binding.txtMauvaisMot.setTextColor(ContextCompat.getColor(this, R.color.red))
        miseAJourImage(jeu.lettresIncorrectes.size)
    }


    // Vérifier si la partie est terminée

    private fun verifierFinDePartie() {
        if (jeu.isGameOver()) {
            jeu.resultat = true
            jeu.incrementVictories() // Incrementer le nombre de victoires
            Toast.makeText(this, "Vous avez gagne!", Toast.LENGTH_SHORT).show()
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this, ResultatActivity::class.java)
                intent.putExtra("resultat", "victoire")
                intent.putExtra("victories", jeu.victories) // envoyer le nombre de victoires à l'activité de résultat
                intent.putExtra("mot", jeu.mot)
                startActivity(intent)
                finish()
            }, 2000)

        } else if (jeu.lettresIncorrectes.size == 10) {
            jeu.resultat = false
            Toast.makeText(this, "Tu as perdu! le mot était ${jeu.mot}.", Toast.LENGTH_SHORT).show()
            finirJeu()
        }
    }


    private fun finirJeu() {
        // Cancela o timer para evitar que ele continue a funcionar após o jogo ter terminado
        timer?.cancel()
        binding.btnRejouer.visibility = View.VISIBLE
        binding.btnEssayer.isEnabled = false
        binding.txtSaissirLettre.isEnabled = false
    }



    // Mettre à jour l'image du pendu selon le nombre de lettres incorrectes
    private fun miseAJourImage(nbrMauvaisesLettres: Int) {
        val image = when (nbrMauvaisesLettres) {
            0 -> R.drawable.img_default
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
            else -> R.drawable.img_default
        }
        binding.zoneImgPendu.setImageResource(image)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflar o menu; isso adiciona itens à barra de ação, se estiver presente.
        menuInflater.inflate(R.menu.menu_jeu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_retour -> {
                val intent = Intent(this, PanneauJeuActivity::class.java)
                startActivity(intent)
                finish()
                true
            }
            R.id.action_sortir -> {
                finishAffinity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Cancela o timer para evitar que ele continue a funcionar após a atividade ter sido destruída
        timer?.cancel()
    }

}








