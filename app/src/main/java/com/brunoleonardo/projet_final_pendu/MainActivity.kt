package com.brunoleonardo.projet_final_pendu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.brunoleonardo.projet_final_pendu.databinding.ActivityMainBinding
import java.io.Serializable

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    // Create a list of users
    private val utilisateurs = mutableListOf<Utilisateur>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        // button to access the user registration activity
        binding.btnEntrerLogin.setOnClickListener {
            val nomUtilisateur = binding.txtUtilisateurLogin.text.toString()
            val motDePasse = binding.txtMotPasseLogin.text.toString()
            val utilisateur = Utilisateur(1, "user", "123" )

            if(nomUtilisateur== Constantes.ATTRIBUT_ADMINISTRATEUR_UserNeme && motDePasse== Constantes.ATTRIBUT_ADMINISTRATEUR_MotDePasse){

                val intent = Intent(this, AdminActivity::class.java)
                    startActivity(intent)


            }else{

                if (isValidUtilisateur(nomUtilisateur, motDePasse)) {
                    val intent = Intent(this, PanneauJeuActivity::class.java).apply {
                        putExtra("utilisateur", utilisateur)
                    }
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
     fun isValidUtilisateur(nomUtilisateur: String, motDePasse: String): Boolean {
        val utilisateur = Utilisateur(1, "user", "123" )
        return utilisateur.nomUtilisateur == nomUtilisateur && utilisateur.motDePasse == motDePasse
    }
}










