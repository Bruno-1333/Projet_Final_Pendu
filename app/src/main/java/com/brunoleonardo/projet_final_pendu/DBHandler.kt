package com.brunoleonardo.projet_final_pendu

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast

class DBHandler(context: Context) :
    SQLiteOpenHelper(context, Constantes.NOM_BASE, null, Constantes.VERSION_BD) {

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_UTILISATEUR_TABLE = ("CREATE TABLE ${Constantes.TABLE_UTILISATEUR}(" +
                "${Constantes.ATTRIBUT_UTILISATEUR_ID} INTEGER PRIMARY KEY," +
                "${Constantes.ATTRIBUT_UTILISATEUR_NOM_UTILISATEUR} TEXT," +
                "${Constantes.ATTRIBUT_UTILISATEUR_MOT_DE_PASSE} TEXT)")

        val CREATE_JEU_TABLE = ("CREATE TABLE ${Constantes.TABLE_JEU}(" +
                "${Constantes.ATTRIBUT_JEU_ID} INTEGER PRIMARY KEY," +
                "${Constantes.ATTRIBUT_JEU_UTILISATEUR_ID} INTEGER, " +
                "${Constantes.ATTRIBUT_JEU_MOT_ID} INTEGER," +
                "${Constantes.ATTRIBUT_JEU_RESULTAT} TEXT," +
                "${Constantes.ATTRIBUT_JEU_VICTOIRES} INTEGER," +
                "FOREIGN KEY(${Constantes.ATTRIBUT_JEU_UTILISATEUR_ID}) REFERENCES ${Constantes.TABLE_UTILISATEUR}(${Constantes.ATTRIBUT_UTILISATEUR_ID}), " +
                "FOREIGN KEY(${Constantes.ATTRIBUT_JEU_MOT_ID}) REFERENCES ${Constantes.TABLE_MOT}(${Constantes.ATTRIBUT_MOT_ID}))")

        val CREATE_MOT_TABLE = ("CREATE TABLE ${Constantes.TABLE_MOT}(" +
                "${Constantes.ATTRIBUT_MOT_ID} INTEGER PRIMARY KEY," +
                "${Constantes.ATTRIBUT_MOT_MOT} TEXT, " +
                "${Constantes.ATTRIBUT_MOT_DESCRIPTION} TEXT," +
                "${Constantes.ATTRIBUT_MOT_THEME} TEXT, " +
                "${Constantes.ATTRIBUT_MOT_NIVEAU_DIFFICULTE} TEXT)")




        db.execSQL(CREATE_UTILISATEUR_TABLE)
        db.execSQL(CREATE_JEU_TABLE)
        db.execSQL(CREATE_MOT_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${Constantes.TABLE_UTILISATEUR}")
        db.execSQL("DROP TABLE IF EXISTS ${Constantes.TABLE_JEU}")
        db.execSQL("DROP TABLE IF EXISTS ${Constantes.TABLE_MOT}")
        onCreate(db)
    }

    // Fonctions CRUD pour la table Utilisateur

    fun rechercheUtilisateurs (): ArrayList<Utilisateur> {
        val utilisateurList: ArrayList<Utilisateur> = ArrayList()
        val db = this.readableDatabase


        val selectQuery = "SELECT * FROM ${Constantes.TABLE_UTILISATEUR}"
        val cursor = db.rawQuery(selectQuery, null)

       val res = cursor.moveToFirst()

        if(res){
            do {
                val id = cursor.getInt(0)
                val nomUtilisateur = cursor.getString(1)
                val motDePasse = cursor.getString(2)

                val utilisateur = Utilisateur(id, nomUtilisateur, motDePasse)
                utilisateurList.add(utilisateur)
            } while (cursor.moveToNext())

        }

        cursor.close()
        db.close()
        return utilisateurList
    }

    fun chercherUtilisateurParId (Id : Int): Utilisateur? {
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM ${Constantes.TABLE_UTILISATEUR} WHERE ${Constantes.ATTRIBUT_UTILISATEUR_ID} = $Id"
        val cursor = db.rawQuery(selectQuery, null)
        val res = cursor.moveToFirst()
        var utilisateur : Utilisateur? = null
        if(res){
            val id = cursor.getInt(0)
            val nomUtilisateur = cursor.getString(1)
            val motDePasse = cursor.getString(2)
            utilisateur = Utilisateur(id, nomUtilisateur, motDePasse)
        }
        cursor.close()
        db.close()
        return utilisateur
    }

    fun chercherParUtilisateur(userName : String) : Utilisateur? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM ${Constantes.TABLE_UTILISATEUR} WHERE ${Constantes.ATTRIBUT_UTILISATEUR_NOM_UTILISATEUR} = ?", arrayOf(userName))
        val res = cursor.moveToFirst()
        var utilisateur : Utilisateur? = null
        if(res){
            val id = cursor.getInt(0)
            val nomUtilisateur = cursor.getString(1)
            val motDePasse = cursor.getString(2)
            utilisateur = Utilisateur(id, nomUtilisateur, motDePasse)

            cursor.close()
            db.close()
            return utilisateur
        }
        cursor.close()
        db.close()
        return utilisateur
    }

    fun ajouterUtilisateur(Utilisateur: Utilisateur) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Constantes.ATTRIBUT_UTILISATEUR_NOM_UTILISATEUR, Utilisateur.nomUtilisateur)
        contentValues.put(Constantes.ATTRIBUT_UTILISATEUR_MOT_DE_PASSE, Utilisateur.motDePasse)

        db.insert(Constantes.TABLE_UTILISATEUR, null, contentValues)

        db.close()

       Log.v("test", "ajouté")
    }

    fun modifierUtilisateur(Utilisateur: Utilisateur) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Constantes.ATTRIBUT_UTILISATEUR_NOM_UTILISATEUR, Utilisateur.nomUtilisateur)
        contentValues.put(Constantes.ATTRIBUT_UTILISATEUR_MOT_DE_PASSE, Utilisateur.motDePasse)

        db.update(Constantes.TABLE_UTILISATEUR, contentValues, "${Constantes.ATTRIBUT_UTILISATEUR_ID} = ?", arrayOf(Utilisateur.id.toString()))

        db.close()
    }

    fun supprimerUtilisateur(Utilisateur: Utilisateur) {
        val db = this.writableDatabase
        db.delete(Constantes.TABLE_UTILISATEUR, "${Constantes.ATTRIBUT_UTILISATEUR_ID} = ?", arrayOf(Utilisateur.id.toString()))
        db.close()
    }

    // Functions CRUD pour la table Jeu

    fun chercherMots (): ArrayList<Mot> {
        val motList: ArrayList<Mot> = ArrayList()
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM ${Constantes.TABLE_MOT}"
        val cursor = db.rawQuery(selectQuery, null)
        val res = cursor.moveToFirst()
        if(res){
            do {
                val id = cursor.getInt(0)
                val motJeu = cursor.getString(1)
                val description = cursor.getString(2)
                val theme = cursor.getString(3)
                val niveauDifficulte = cursor.getString(4)
                val mot = Mot(id, motJeu, description, theme, niveauDifficulte)
                motList.add(mot)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return motList
    }

    fun chercherParMot(mot: String): Mot? {
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM ${Constantes.TABLE_JEU} WHERE ${Constantes.ATTRIBUT_MOT_MOT} = $mot"
        val cursor = db.rawQuery(selectQuery, null)
        val res = cursor.moveToFirst()
        var mot : Mot? = null
        if(res){
            val id = cursor.getInt(0)
            val motJeu = cursor.getString(1)
            val description = cursor.getString(2)
            val theme = cursor.getString(3)
            val niveauDifficulte = cursor.getString(4)
            mot = Mot(id, motJeu, description, theme, niveauDifficulte)
        }
        cursor.close()
        db.close()
        return mot
    }

    fun chercherMotParTheme(Theme : String) : ArrayList<Mot> {
        val motList: ArrayList<Mot> = ArrayList()
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM ${Constantes.TABLE_MOT} WHERE ${Constantes.ATTRIBUT_MOT_THEME} = $Theme"
        val cursor = db.rawQuery(selectQuery, null)
        val res = cursor.moveToFirst()
        if(res){
            do {
                val id = cursor.getInt(0)
                val motJeu = cursor.getString(1)
                val description = cursor.getString(2)
                val theme = cursor.getString(3)
                val niveauDifficulte = cursor.getString(4)
                val mot = Mot(id, motJeu, description, theme, niveauDifficulte)
                motList.add(mot)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return motList
    }

    fun chercherMotParId(Id : Int): Mot? {
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM ${Constantes.TABLE_MOT} WHERE ${Constantes.ATTRIBUT_MOT_ID} = $Id"
        val cursor = db.rawQuery(selectQuery, null)
        val res = cursor.moveToFirst()
        var mot : Mot? = null
        if(res){
            val id = cursor.getInt(0)
            val motJeu = cursor.getString(1)
            val description = cursor.getString(2)
            val theme = cursor.getString(3)
            val niveauDifficulte = cursor.getString(4)
            mot = Mot(id, motJeu, description, theme, niveauDifficulte)
        }
        cursor.close()
        db.close()
        return mot
    }

    // Dans votre classe DBHandler ou équivalent
    fun chercherMotsParThemeDifficulte(theme: String, difficulte: String): Mot? {
        val db = this.readableDatabase
        var mot : Mot? = null

        val cursor = db.rawQuery("SELECT * FROM ${Constantes.TABLE_MOT} WHERE ${Constantes.ATTRIBUT_MOT_THEME} = ? AND ${Constantes.ATTRIBUT_MOT_NIVEAU_DIFFICULTE} = ?", arrayOf(theme, difficulte))

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val motJeu = cursor.getString(1)
                val description = cursor.getString(2)
                val theme = cursor.getString(3)
                val niveauDifficulte = cursor.getString(4)
                mot = Mot(id, motJeu, description, theme, niveauDifficulte)

            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return mot
    }


    fun ajouterMot(mot: Mot) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Constantes.ATTRIBUT_MOT_MOT, mot.mot)
        contentValues.put(Constantes.ATTRIBUT_MOT_DESCRIPTION, mot.description)
        contentValues.put(Constantes.ATTRIBUT_MOT_THEME, mot.theme)
        contentValues.put(Constantes.ATTRIBUT_MOT_NIVEAU_DIFFICULTE, mot.niveauDifficulte)

        db.insert(Constantes.TABLE_MOT, null, contentValues)

        db.close()
    }

    fun modifierMot(mot: Mot) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Constantes.ATTRIBUT_MOT_MOT, mot.mot)
        contentValues.put(Constantes.ATTRIBUT_MOT_DESCRIPTION, mot.description)
        contentValues.put(Constantes.ATTRIBUT_MOT_THEME, mot.theme)
        contentValues.put(Constantes.ATTRIBUT_MOT_NIVEAU_DIFFICULTE, mot.niveauDifficulte)

        db.update(Constantes.TABLE_MOT, contentValues, "${Constantes.ATTRIBUT_MOT_ID} = ?", arrayOf(mot.id.toString()))

        db.close()
    }

    fun supprimerMot(mot: Mot) {
        val db = this.writableDatabase
        db.delete(Constantes.TABLE_MOT, "${Constantes.ATTRIBUT_MOT_ID} = ?", arrayOf(mot.id.toString()))
        db.close()
    }

    fun supprimerToutMot() {
        val db = this.writableDatabase
        db.delete(Constantes.TABLE_MOT, null, null)
        db.close()
    }

    // Functions CRUD pour la table Jeu

    fun chercherJeu (): ArrayList<Jeu> {
        val jeuList: ArrayList<Jeu> = ArrayList()
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM ${Constantes.TABLE_JEU}"
        val cursor = db.rawQuery(selectQuery, null)
        val res = cursor.moveToFirst()
        if(res){
            do {
                val id = cursor.getInt(0)
                val utilisateurId = cursor.getInt(1)
                val motId = cursor.getInt(2)
                val resultat = cursor.getString(3).toBoolean()
                val victories = cursor.getInt(4)
                val mot = chercherMotParId(motId)
                val jeu = Jeu(id, utilisateurId, mot!!, resultat, victories)
                jeuList.add(jeu)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return jeuList
    }

    fun ajouterJeu(jeu: Jeu) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Constantes.ATTRIBUT_JEU_UTILISATEUR_ID, jeu.utilisateurId)
        contentValues.put(Constantes.ATTRIBUT_JEU_MOT_ID, jeu.mot.id)
        contentValues.put(Constantes.ATTRIBUT_JEU_RESULTAT, jeu.resultat)
        contentValues.put(Constantes.ATTRIBUT_JEU_VICTOIRES, jeu.victories)

        db.insert(Constantes.TABLE_JEU, null, contentValues)

        db.close()
    }

    fun modifierJue(jeu: Jeu) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Constantes.ATTRIBUT_JEU_UTILISATEUR_ID, jeu.utilisateurId)
        contentValues.put(Constantes.ATTRIBUT_JEU_MOT_ID, jeu.mot.id)
        contentValues.put(Constantes.ATTRIBUT_JEU_RESULTAT, jeu.resultat)
        contentValues.put(Constantes.ATTRIBUT_JEU_VICTOIRES, jeu.victories)

        db.update(Constantes.TABLE_JEU, contentValues, "${Constantes.ATTRIBUT_JEU_ID} = ?", arrayOf(jeu.id.toString()))

        db.close()
    }

    fun supprimerJeu(jeu: Jeu) {
        val db = this.writableDatabase
        db.delete(Constantes.TABLE_JEU, "${Constantes.ATTRIBUT_JEU_ID} = ?", arrayOf(jeu.id.toString()))
        db.close()
    }

}




