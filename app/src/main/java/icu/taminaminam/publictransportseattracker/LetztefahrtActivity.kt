package icu.taminaminam.publictransportseattracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import icu.taminaminam.publictransportseattracker.databinding.LetztefahrtBinding

class LetztefahrtActivity : AppCompatActivity() {

    private lateinit var binding: LetztefahrtBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LetztefahrtBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val textView = binding.textView
        textView.text = intent.getStringExtra(INTENT_KEY)
    }

    companion object{
        const val INTENT_KEY = "intent_key"
    }
}