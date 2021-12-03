package icu.taminaminam.publictransportseattracker

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.room.Room
import icu.taminaminam.publictransportseattracker.databinding.ActivityMainBinding
import icu.taminaminam.publictransportseattracker.localdatabase.AppDatabase
import icu.taminaminam.publictransportseattracker.localdatabase.daos.MainDao
import icu.taminaminam.publictransportseattracker.localdatabase.entities.Fahrt
import icu.taminaminam.publictransportseattracker.localdatabase.entities.Fahrzeug
import icu.taminaminam.publictransportseattracker.localdatabase.relations.FahrtenMitFahrzeug
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val sdf = SimpleDateFormat("dd.MM.yyyy")
    private lateinit var database: AppDatabase
    private lateinit var mainDao: MainDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.editTextDatum.hint = sdf.toPattern()

        binding.editTextDatum.setOnClickListener(View.OnClickListener {
            val cldr = Calendar.getInstance()
            val day = cldr[Calendar.DAY_OF_MONTH]
            val month = cldr[Calendar.MONTH]
            val year = cldr[Calendar.YEAR]
            // date picker dialog
            val picker = DatePickerDialog(this@MainActivity,
                { view, year, monthOfYear, dayOfMonth -> binding.editTextDatum.setText(String.format("%02d.%02d.%04d", dayOfMonth, (monthOfYear + 1), year)) },   //TODO: adjust to local date formatting
                year,
                month,
                day
            )
            picker.show()
        })

        binding.addButton.setOnClickListener {
            val vehicleNo = binding.editTextFahrzeugnummer.text.toString()
            val seat = binding.editTextSitzplatz.text.toString()
            val dateStr = binding.editTextDatum.text.toString()

            //TODO: adjust to local date formatting
            /*
            val parser =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm")
            val formattedDate = formatter.format(parser.parse("2018-12-14T09:55:00"))
             */


            val date = if (dateStr.isNotBlank()) sdf.parse(dateStr) else java.util.Date()

            //clearing text fields
            binding.editTextFahrzeugnummer.text.clear()
            binding.editTextSitzplatz.text.clear()
            binding.editTextDatum.text.clear()

            addEntry(vehicleNo, seat, date)
            Log.d(MainActivity::class.qualifiedName, "added entry")
            val letzteFahrtMitFahrzeug = getLastFahrt(vehicleNo)

            Log.d(this::class.qualifiedName, "Letzte Fahrt: "+letzteFahrtMitFahrzeug.toString())

            var message: String
            val fahrzeug_from_db = mainDao.getFahrzeugByFahrzeugnummer(vehicleNo)[0]

            if (letzteFahrtMitFahrzeug == null){
                message = String.format(getString(R.string.noch_nie_gefahren), fahrzeug_from_db.fahrzeugnummer)
            } else {
                message = String.format(getString(R.string.letzte_fahrt), fahrzeug_from_db.fahrzeugnummer, sdf.format(letzteFahrtMitFahrzeug.datum),letzteFahrtMitFahrzeug.sitzplatz)
            }

            val explicitIntent = Intent(this, LetztefahrtActivity::class.java).apply {
                putExtra(LetztefahrtActivity.INTENT_KEY, message)
            }
            startActivity(explicitIntent)
        }

        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "AppDatabase"
        ).allowMainThreadQueries()
            .build()

        mainDao = database.mainDao()
    }

    private fun getLastFahrt(vehicleNo: String): Fahrt? {
        val fahrzeug_uid = mainDao.getFahrzeugByFahrzeugnummer(vehicleNo)[0].uid
        Log.d(MainActivity::class.qualifiedName, "fahrzeug_uid: $fahrzeug_uid")

        val result = mainDao.getLetzteFahrtMitFahrzeug(fahrzeug_uid)
        Log.d(MainActivity::class.qualifiedName, "result: $result")

        val letzteFahrt = result.getOrNull(1)
        return letzteFahrt
    }

    fun addEntry(vehicleNo: String, seat: String, date: java.util.Date?){

        try {
            var fahrzeug_from_db = mainDao.getFahrzeugByFahrzeugnummer(vehicleNo).getOrNull(0)
            if (fahrzeug_from_db == null) {
                val fahrzeug = Fahrzeug(0, vehicleNo, null, null)
                val fahrzeug_rowId = mainDao.insertFahrzeug(fahrzeug)

                fahrzeug_from_db = mainDao.getFahrzeugByRowId(fahrzeug_rowId)[0]
            }

            val fahrt = Fahrt(0, fahrzeug_from_db.uid, null, date ?: Date(), seat, null, null)

            mainDao.insertFahrt(fahrt)

            Toast.makeText(
                applicationContext,
                "Added entry for $vehicleNo in seat $seat at ${date.toString()}",
                Toast.LENGTH_LONG
            ).show()
            Log.d("MainAct", "addentry success")
        } catch (e: Exception){
            Toast.makeText(
                applicationContext,
                "Failed to add entry for $vehicleNo in seat $seat at ${date.toString()}",
                Toast.LENGTH_LONG
            ).show()
            Log.d("MainAct", "addentry fail")
        }

    }
}