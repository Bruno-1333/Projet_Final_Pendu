
/*package com.brunoleonardo.projet_final_pendu

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.brunoleonardo.projet_final_pendu.Constantes
import com.brunoleonardo.projet_final_pendu.entities.Utilisateur
import com.brunoleonardo.projet_final_pendu.entities.Jeu

class DBHandler(context: Context) : SQLiteOpenHelper(context, Constantes.NOM_BASE, null, Constantes.VERSION_BD) {
    companion object {
        const val NOM_TABLE_UTILISATEUR = "Utilisateurs"
        const val COLONNE_ID = "id"
        const val COLONNE_NOM = "nom"
        const val COLONNE_NOM_UTILISATEUR = "nomUtilisateur"
        const val COLONNE_MOT_DE_PASSE = "motDePasse"

        const val NOM_TABLE_JEU = "Jeux"
        const val COLONNE_THEME = "theme"
        const val COLONNE_MOT = "mot"
        const val COLONNE_DESCRIPTION = "description"
        const val COLONNE_NIVEAU_DIFFICULTE = "niveauDifficulte"
        const val COLONNE_RESULTAT = "resultat"
        const val COLONNE_VICTOIRES = "victoires"
        const val COLONNE_UTILISATEUR_ID = "utilisateurId"

        const val SQL_CREER_TABLE_UTILISATEUR =
            "CREATE TABLE $NOM_TABLE_UTILISATEUR (" +
                    "$COLONNE_ID INTEGER PRIMARY KEY," +
                    "$COLONNE_NOM TEXT," +
                    "$COLONNE_NOM_UTILISATEUR TEXT," +
                    "$COLONNE_MOT_DE_PASSE TEXT)"

        const val SQL_CREER_TABLE_JEU =
            "CREATE TABLE $NOM_TABLE_JEU (" +
                    "$COLONNE_ID INTEGER PRIMARY KEY," +
                    "$COLONNE_UTILISATEUR_ID INTEGER," +
                    "$COLONNE_THEME TEXT," +
                    "$COLONNE_MOT TEXT," +
                    "$COLONNE_DESCRIPTION TEXT," +
                    "$COLONNE_NIVEAU_DIFFICULTE TEXT," +
                    "$COLONNE_RESULTAT INTEGER," +
                    "$COLONNE_VICTOIRES INTEGER," +
                    "FOREIGN KEY($COLONNE_UTILISATEUR_ID) REFERENCES $NOM_TABLE_UTILISATEUR($COLONNE_ID))"

        const val SQL_SUPPRIMER_TABLE_UTILISATEUR = "DROP TABLE IF EXISTS $NOM_TABLE_UTILISATEUR"
        const val SQL_SUPPRIMER_TABLE_JEU = "DROP TABLE IF EXISTS $NOM_TABLE_JEU"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREER_TABLE_UTILISATEUR)
        db.execSQL(SQL_CREER_TABLE_JEU)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_SUPPRIMER_TABLE_UTILISATEUR)
        db.execSQL(SQL_SUPPRIMER_TABLE_JEU)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    // Métodos CRUD para Utilisateur

    fun addUtilisateur(utilisateur: Utilisateur) {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(COLONNE_NOM, utilisateur.nom)
        values.put(COLONNE_NOM_UTILISATEUR, utilisateur.nomUtilisateur)
        values.put(COLONNE_MOT_DE_PASSE, utilisateur.motDePasse)

        db.insert(NOM_TABLE_UTILISATEUR, null, values)
        db.close()
    }

    fun getUtilisateur(nomUtilisateur: String): Utilisateur? {
        val db = this.readableDatabase
        val cursor = db.query(
            NOM_TABLE_UTILISATEUR,
            arrayOf(COLONNE_ID, COLONNE_NOM, COLONNE_NOM_UTILISATEUR, COLONNE_MOT_DE_PASSE),
            "$COLONNE_NOM_UTILISATEUR=?",
            arrayOf(nomUtilisateur),
            null, null, null, null
        )

        if (cursor != null && cursor.moveToFirst()) {
            val utilisateur = Utilisateur(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3)
            )
            cursor.close()
            return utilisateur
        } else {
            cursor?.close()
            return null
        }
    }

    fun updateUtilisateur(utilisateur: Utilisateur) {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(COLONNE_NOM, utilisateur.nom)
        values.put(COLONNE_NOM_UTILISATEUR, utilisateur.nomUtilisateur)
        values.put(COLONNE_MOT_DE_PASSE, utilisateur.motDePasse)

        db.update(NOM_TABLE_UTILISATEUR, values, "$COLONNE_ID=?", arrayOf(utilisateur.id.toString()))
        db.close()
    }

    fun deleteUtilisateur(utilisateur: Utilisateur) {
        val db = this.writableDatabase

        db.delete(NOM_TABLE_UTILISATEUR, "$COLONNE_ID=?", arrayOf(utilisateur.id.toString()))
        db.close()
    }

    // Métodos CRUD para Jeu

    fun addJeu(jeu: Jeu) {
        val db = this.writableDatabase

        val values = ContentValues().apply {
            put(COLONNE_UTILISATEUR_ID, jeu.utilisateurId)
            put(COLONNE_THEME, jeu.theme)
            put(COLONNE_MOT, jeu.mot)
            put(COLONNE_DESCRIPTION, jeu.description)
            put(COLONNE_NIVEAU_DIFFICULTE, jeu.niveauDifficulte)
            put(COLONNE_RESULTAT, if (jeu.resultat) 1 else 0)
            put(COLONNE_VICTOIRES, jeu.victoires)
        }

        db.insert(NOM_TABLE_JEU, null, values)
        db.close()
    }

    fun getJeu(id: Int): Jeu? {
        val db = this.readableDatabase
        val cursor = db.query(
            NOM_TABLE_JEU,
            arrayOf(COLONNE_ID, COLONNE_UTILISATEUR_ID, COLONNE_THEME, COLONNE_MOT, COLONNE_DESCRIPTION, COLONNE_NIVEAU_DIFFICULTE, COLONNE_RESULTAT, COLONNE_VICTOIRES),
            "$COLONNE_ID=?",
            arrayOf(id.toString()),
            null, null, null, null
        )

        cursor?.use {
            if (it.moveToFirst()) {
                return Jeu(
                    it.getInt(it.getColumnIndex(COLONNE_ID)),
                    it.getInt(it.getColumnIndex(COLONNE_UTILISATEUR_ID)),
                    it.getString(it.getColumnIndex(COLONNE_THEME)),
                    it.getString(it.getColumnIndex(COLONNE_MOT)),
                    it.getString(it.getColumnIndex(COLONNE_DESCRIPTION)),
                    it.getString(it.getColumnIndex(COLONNE_NIVEAU_DIFFICULTE)),
                    it.getInt(it.getColumnIndex(COLONNE_RESULTAT)) == 1,
                    mutableListOf(),
                    it.getInt(it.getColumnIndex(COLONNE_VICTOIRES))
                )
            }
        }

        return null
    }

    fun updateJeu(jeu: Jeu) {
        val db = this.writableDatabase

        val values = ContentValues().apply {
            put(COLONNE_UTILISATEUR_ID, jeu.utilisateurId)
            put(COLONNE_THEME, jeu.theme)
            put(COLONNE_MOT, jeu.mot)
            put(COLONNE_DESCRIPTION, jeu.description)
            put(COLONNE_NIVEAU_DIFFICULTE, jeu.niveauDifficulte)
            put(COLONNE_RESULTAT, if (jeu.resultat) 1 else 0)
            put(COLONNE_VICTOIRES, jeu.victoires)
        }

        db.update(NOM_TABLE_JEU, values, "$COLONNE_ID=?", arrayOf(jeu.id.toString()))
        db.close()
    }

    fun deleteJeu(id: Int) {
        val db = this.writableDatabase
        db.delete(NOM_TABLE_JEU, "$COLONNE_ID=?", arrayOf(id.toString()))
        db.close()
    }

}*/




