package com.example.hesapmakinsi

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hesapmakinsi.MainActivity

import com.example.hesapmakinsi.databinding.ActivityBirimDonusuturcuBinding
import com.google.android.material.snackbar.Snackbar

class birimDonusturucu : AppCompatActivity() {
    private lateinit var binding: ActivityBirimDonusuturcuBinding
    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        private const val PREFS_APP_SETTINGS = "AppSettings"
        private const val THEME_COLOR_KEY = "theme_color"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityBirimDonusuturcuBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        sharedPreferences = getSharedPreferences(PREFS_APP_SETTINGS, MODE_PRIVATE)

        // 📌 Kaydedilmiş temayı uygula
        applySavedTheme()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_hesapMakinesi -> {
                Snackbar.make(binding.root, "Hesap makinesi seçildi!", Snackbar.LENGTH_LONG).show()
                val intent = Intent(this@birimDonusturucu, MainActivity::class.java)
                startActivity(intent)
                overridePendingTransition(
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right
                )
                return true
            }
            R.id.menu_themaSec -> {
                Snackbar.make(binding.root, "tema kuru seçildi", Snackbar.LENGTH_LONG).show()
                val intent = Intent(this@birimDonusturucu, themaSec::class.java)
                startActivity(intent)
                overridePendingTransition(
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right
                )
                return true
            }
            R.id.menu_BirimDonusturucu -> {
                Snackbar.make(binding.root, "Birim dönüştürücü seçildi", Snackbar.LENGTH_LONG).show()
                val intent = Intent(this@birimDonusturucu, birimDonusturucu::class.java)
                startActivity(intent)
                overridePendingTransition(
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right
                )
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    /**
     * 📌 Kaydedilmiş tema rengini uygula
     */
    fun applySavedTheme() {
        val savedColor = sharedPreferences.getInt(THEME_COLOR_KEY, -1)
        if (savedColor != -1) {
            window.decorView.setBackgroundColor(ContextCompat.getColor(this, savedColor))
        }
    }

    /**
     * 📌 Tema rengini sıfırla
     */
    fun resetTheme() {
        sharedPreferences.edit().remove(THEME_COLOR_KEY).apply()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.slide_in_left)
    }
}