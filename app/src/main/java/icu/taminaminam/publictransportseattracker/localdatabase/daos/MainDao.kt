package icu.taminaminam.publictransportseattracker.localdatabase.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import icu.taminaminam.publictransportseattracker.localdatabase.entities.Fahrt
import icu.taminaminam.publictransportseattracker.localdatabase.entities.Fahrzeug
import icu.taminaminam.publictransportseattracker.localdatabase.relations.FahrtenMitFahrzeug

@Dao
interface MainDao {
    @Insert(onConflict = REPLACE)
    fun insertFahrt(fahrt: Fahrt): Long

    @Insert
    fun insertFahrzeug(fahrzeug: Fahrzeug): Long

    @Transaction
    @Query("SELECT fahrt.uid, fahrt.fahrzeug_uid, fahrt.linie, fahrt.datum, fahrt.sitzplatz, fahrt.starthaltestelle, fahrt.endhaltestelle FROM fahrt JOIN fahrzeug ON fahrt.fahrzeug_uid = fahrzeug.uid WHERE 1=1")
    fun getAllFahrtenMitFahrzeug(): List<Fahrt>

    @Transaction
    @Query("SELECT fahrt.uid, fahrt.fahrzeug_uid, fahrt.linie, fahrt.datum, fahrt.sitzplatz, fahrt.starthaltestelle, fahrt.endhaltestelle FROM fahrt JOIN fahrzeug ON fahrt.fahrzeug_uid = fahrzeug.uid WHERE fahrzeug.uid = :fahrzeug_uid")
    fun getAllFahrtenMitFahrzeugForfahrzeuguid(fahrzeug_uid: Int): List<Fahrt>

    @Transaction
    @Query("SELECT fahrt.uid, fahrt.fahrzeug_uid, fahrt.linie, fahrt.datum, fahrt.sitzplatz, fahrt.starthaltestelle, fahrt.endhaltestelle FROM fahrt JOIN fahrzeug ON fahrt.fahrzeug_uid = fahrzeug.uid WHERE fahrzeug.uid = :fahrzeug_uid ORDER BY fahrt.datum desc LIMIT 2")
    fun getLetzteFahrtMitFahrzeug(fahrzeug_uid: Int): List<Fahrt>

    @Query("SELECT * FROM fahrzeug WHERE rowid = :rowId")
    fun getFahrzeugByRowId(rowId: Long): List<Fahrzeug>

    @Query("SELECT * FROM fahrzeug WHERE fahrzeugnummer = :fahrzeugnummer")
    fun getFahrzeugByFahrzeugnummer(fahrzeugnummer: String): List<Fahrzeug>

    /*
    @Transaction
    @Query("SELECT * FROM User")
    fun getUsersWithPlaylists(): List<UserWithPlaylists>

     */
}