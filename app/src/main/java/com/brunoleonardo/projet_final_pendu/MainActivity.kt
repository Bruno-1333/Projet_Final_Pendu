package com.brunoleonardo.projet_final_pendu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.brunoleonardo.projet_final_pendu.databinding.ActivityMainBinding
import java.io.Serializable

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ouverture de la base de données
        val dbHandler = DBHandler(this)

        // button to access the user registration activity
        binding.btnEntrerLogin.setOnClickListener {
            val nomUtilisateur = binding.txtUtilisateurLogin.text.toString()
            val motDePasse = binding.txtMotPasseLogin.text.toString()
            val utilisateur = dbHandler.chercherParUtilisateur(nomUtilisateur)



            if(nomUtilisateur== Constantes.ATTRIBUT_ADMINISTRATEUR_UserNeme && motDePasse== Constantes.ATTRIBUT_ADMINISTRATEUR_MotDePasse){

                val intent = Intent(this, AdminActivity::class.java)
                startActivity(intent)

            }else{

                if (isValidUtilisateur(utilisateur, motDePasse)) {
                    val intent = Intent(this, PanneauJeuActivity::class.java)
                    intent.putExtra("utilisateurId", utilisateur!!.id)
                    startActivity(intent)

                } else {
                    Toast.makeText(this, "Utilisateur ou mot de passe invalide", Toast.LENGTH_SHORT).show()
                }
            }
        }



        // button to access the game activity without registering
        binding.btnJouerLogin.setOnClickListener {
            // Create a default user
            val utilisateur = Utilisateur(1, "Anonyme", "anonyme" )

            val intent = Intent(this, PanneauJeuActivity::class.java).apply {
                putExtra("utilisateur", utilisateur)
            }
            startActivity(intent)
        }

        // button to access the user registration activity
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










