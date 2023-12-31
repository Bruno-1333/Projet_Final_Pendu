package com.brunoleonardo.projet_final_pendu

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.brunoleonardo.projet_final_pendu.databinding.ActivityMainBinding
import java.io.Serializable


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    // Creer l'activite
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*val listMot = arrayListOf<Mot>()
        listMot.add(Mot("lion", "C'est un animal qui vit dans la forêt", "Animaux", "Facile"))
        listMot.add(Mot("panthère", "C'est un animal qui vit dans la forêt", "Animaux", "Moyen"))
        listMot.add(Mot("rhinocéros", "C'est un animal qui vit dans la forêt", "Animaux", "Difficile"))
        listMot.add(Mot("Peru", "C'est un pays qui est situé en Amérique du Sud", "Pays", "Facile"))
        listMot.add(Mot("Portugal", "C'est un pays qui est situé en Europe", "Pays", "Moyen"))
        listMot.add(Mot("Afghanistan", "C'est un pays qui est situé en Asie", "Pays", "Difficile"))
        listMot.add(Mot("harp", "C'est un instrument de musique", "Instruments Musique", "Facile"))
        listMot.add(Mot("trombone", "C'est un instrument de musique", "Instruments Musique", "Moyen"))
        listMot.add(Mot("clavicémbalo", "C'est un instrument de musique", "Instruments Musique", "Difficile"))
        listMot.add(Mot("Audi", "C'est une voiture de luxe", "Voitures", "Facile"))
        listMot.add(Mot("Ford", "C'est une voiture de luxe", "Voitures", "Facile"))
        listMot.add(Mot("Mercedes", "C'est une voiture de luxe", "Voitures", "Moyen"))
        listMot.add(Mot("Lamborghini", "C'est une voiture de luxe", "Voitures", "Difficile"))*/


        // Ouverture de la base de données
        val dbHandler = DBHandler(this)



        // Ajouter un utilisateur par défaut
        /*val utilisateur = Utilisateur(1, "anonyme", "anonyme")
       dbHandler.ajouterUtilisateur(utilisateur)*/

        // supprimer tous les mots
       /* dbHandler.supprimerToutMot()
        for (mot in listMot) {
            dbHandler.ajouterMot(mot)
        }*/

        // Button pour accéder à l'activité de connexion
        binding.btnEntrerLogin.setOnClickListener {
            val nomUtilisateur = binding.txtUtilisateurLogin.text.toString()
            val motDePasse = binding.txtMotPasseLogin.text.toString()

            if(nomUtilisateur == Constantes.ATTRIBUT_ADMINISTRATEUR_UserNeme && motDePasse == Constantes.ATTRIBUT_ADMINISTRATEUR_MotDePasse) {
                val intent = Intent(this, AdminActivity::class.java)
                binding.txtUtilisateurLogin.text.clear()
                binding.txtMotPasseLogin.text.clear()
                startActivity(intent)
            } else {
                if (dbHandler.loginUtilisateur(nomUtilisateur, motDePasse)) {
                    val intent = Intent(this, PanneauJeuActivity::class.java)
                    intent.putExtra("utilisateurId", dbHandler.obtenirIdUtilisateur(nomUtilisateur))
                    binding.txtUtilisateurLogin.text.clear()
                    binding.txtMotPasseLogin.text.clear()
                    startActivity(intent)
                } else {
                    Toast.makeText(this, getString(R.string.ToastUtilisateurNeExistePas), Toast.LENGTH_SHORT).show()
                }
            }
        }



        // button pour accéder à l'activité de jeu
        binding.btnJouerLogin.setOnClickListener {
            val intent = Intent(this, PanneauJeuActivity::class.java)
            intent.putExtra("utilisateurId", 1)
            startActivity(intent)
        }

        // button pour accéder à l'activité d'enregistrement d'un utilisateur
        binding.btnEnregistrerUtilisateurLogin.setOnClickListener {
            val intent = Intent(this, EnregistrerUtilisateurActivity::class.java)
            startActivity(intent)
        }
    }

    // verifier si l'utilisateur et le mot de passe sont valides
     fun isValidUtilisateur(utilisateur: Utilisateur?, motDePasse: String): Boolean {
        if(utilisateur != null){
            if(utilisateur.motDePasse == motDePasse)
                return true

        }
        return false
    }

}










