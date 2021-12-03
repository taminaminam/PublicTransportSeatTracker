package icu.taminaminam.publictransportseattracker.localdatabase.relations

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import icu.taminaminam.publictransportseattracker.localdatabase.entities.Fahrt

data class FahrtenMitFahrzeug(
    @Embedded val fahrt: Fahrt,
    @Relation(
        parentColumn = "uid",
        entityColumn = "fahrzeug_uid"
    )
    val fahrten: List<Fahrt>
)
