package icu.taminaminam.publictransportseattracker.localdatabase.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Fahrzeug(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name="fahrzeugnummer") val fahrzeugnummer: String,
    @ColumnInfo(name="fahrzeugtyp") val fahrzeugtyp: String?,
    @ColumnInfo(name="modell") val modell: String?
)
