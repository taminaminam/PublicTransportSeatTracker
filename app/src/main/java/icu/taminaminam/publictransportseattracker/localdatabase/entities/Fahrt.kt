package icu.taminaminam.publictransportseattracker.localdatabase.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Fahrt(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    val fahrzeug_uid: Int,
    @ColumnInfo(name="linie") val linie: String?,
    @ColumnInfo(name="datum") val datum: Date,
    @ColumnInfo(name="sitzplatz") val sitzplatz: String,
    @ColumnInfo(name="starthaltestelle") val starthaltestelle: String?,
    @ColumnInfo(name="endhaltestelle") val endhaltestelle: String?
    )
