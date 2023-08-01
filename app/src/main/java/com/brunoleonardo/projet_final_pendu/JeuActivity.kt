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
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
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
        val dbHandler = DBHandler(this) // Ouverture de la base de données
        val motid = intent.getIntExtra("motid", -1) // Récupérer l'ID du mot
        val mot = dbHandler.chercherMotParId(motid) // Récupérer le mot
        val utilisateurid = intent.getIntExtra("utilisateurId2", -1) // Récupérer l'ID de l'utilisateur
        val utilisateur = dbHandler.chercherUtilisateurParId(utilisateurid) // Récupérer l'utilisateur

        // Créer un nouveau jeu
        jeu = Jeu(1,utilisateur!!.id, mot!!, false,0 )
        binding.txtDescription.text = jeu.mot.description // Adicione esta linha

        if (utilisateur == null) {
            Toast.makeText(this, getString(R.string.utilisateur_non_trouve), Toast.LENGTH_SHORT).show()
            finish()
            return
        }

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
                    binding.txtSaissirLettre.setText("") // Effacer le texte de la zone de texte
                } else {
                    Toast.makeText(this, getString(R.string.deja_utilise_lettre), Toast.LENGTH_SHORT).show() // Afficher un message d'erreur
                }
            }
            verifierFinDePartie() // vérifier si la partie est terminée
            miseAJourLettresDevinees() // Mettre à jour les lettres devinées
        }

        // bouton pour rejouer le jeu avec le même utilisateur, thème et difficulté
        binding.btnRejouer.setOnClickListener {
            val newWord = dbHandler.chercherNouveauMot(jeu.mot.id, jeu.mot.niveauDifficulte)
            newWord?.let {
                jeu = Jeu(1, jeu.utilisateurId, it, false, jeu.victories)
                creerVuesLettres() // Créer les vues des lettres
                miseAJourLettresDevinees() // Mettre à jour les lettres devinées
                binding.txtMauvaisMot.text = ""
                miseAJourImage(0)
                binding.btnRejouer.visibility = View.INVISIBLE
                binding.txtSaissirLettre.isEnabled = true
                binding.btnEssayer.isEnabled = true
            }
        }

        // Mettre à jour l'image du pendu
        miseAJourImage(0)

        // Começa a contagem regressiva de 1 minutos (60000 ms)
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

            // Se o jogador é anônimo (não logado)
            if (jeu.utilisateurId == -1) {
                Toast.makeText(this, getString(R.string.bravo_gagne), Toast.LENGTH_SHORT).show()
                binding.btnRejouer.visibility = View.VISIBLE // O botão "Rejouer" fica visível
            }
            // Se o jogador está logado
            else {
                Toast.makeText(this, getString(R.string.bravo_vous_gagne), Toast.LENGTH_SHORT).show()
                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(this, ResultatActivity::class.java)
                    intent.putExtra("resultat", "victoire")
                    intent.putExtra("victorie", jeu.victories) // envoyer le nombre de victoires à l'activité de résultat
                    intent.putExtra("mot", jeu.mot.mot)
                    startActivity(intent)
                    finish()
                }, 2000)
            }

        } else if (jeu.lettresIncorrectes.size == 10) {
            jeu.resultat = false
            Toast.makeText(this, getString(R.string.vous_perdu), Toast.LENGTH_SHORT).show()
            finirJeu()
            return
        }
    }


    private fun finirJeu() {
        // Cancela o timer para evitar que ele continue a funcionar após o jogo ter terminado
        timer?.cancel()
        binding.btnRejouer.visibility = View.VISIBLE
        binding.txtSaissirLettre.isEnabled = false
        binding.btnEssayer.isEnabled = false
    }

    // Mettre à jour l'image du pendu
    private fun miseAJourImage(nbrEssaies: Int) {
        when (nbrEssaies) {
            0 -> binding.zoneImgPendu.setImageResource(R.drawable.img_default)
            1 -> binding.zoneImgPendu.setImageResource(R.drawable.img_01)
            2 -> binding.zoneImgPendu.setImageResource(R.drawable.img_02)
            3 -> binding.zoneImgPendu.setImageResource(R.drawable.img_03)
            4 -> binding.zoneImgPendu.setImageResource(R.drawable.img_04)
            5 -> binding.zoneImgPendu.setImageResource(R.drawable.img_05)
            6 -> binding.zoneImgPendu.setImageResource(R.drawable.img_06)
            7 -> binding.zoneImgPendu.setImageResource(R.drawable.img_07)
            8 -> binding.zoneImgPendu.setImageResource(R.drawable.img_08)
            9 -> binding.zoneImgPendu.setImageResource(R.drawable.img_09)
            10 -> binding.zoneImgPendu.setImageResource(R.drawable.img_10)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_jeu, menu)
        return true
    }

    // A função onOptionsItemSelected deve estar aqui, dentro da classe
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



    // A função onDestroy deve estar aqui, dentro da classe
    override fun onDestroy() {
        super.onDestroy()
        // Cancela o timer para evitar que ele continue a funcionar após a atividade ter sido destruída
        timer?.cancel()
    }
}











