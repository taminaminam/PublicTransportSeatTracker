package icu.taminaminam.publictransportseattracker.localdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import icu.taminaminam.publictransportseattracker.localdatabase.daos.MainDao
import icu.taminaminam.publictransportseattracker.localdatabase.entities.Fahrt
import icu.taminaminam.publictransportseattracker.localdatabase.entities.Fahrzeug
import icu.taminaminam.publictransportseattracker.localdatabase.relations.FahrtenMitFahrzeug
import icu.taminaminam.publictransportseattracker.localdatabase.typeconverters.DateConverter

@Database(entities = [Fahrt::class, Fahrzeug::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase(){
    abstract fun mainDao(): MainDao
}