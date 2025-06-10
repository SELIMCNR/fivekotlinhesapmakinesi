package com.example.hesapmakinsi

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import com.example.hesapmakinsi.databinding.ActivityThemaSecBinding
import com.google.android.material.snackbar.Snackbar

class themaSec : AppCompatActivity() {
    private lateinit var binding : ActivityThemaSecBinding
    private lateinit var sharedPreferences: SharedPreferences
    companion object {
        private const val PREFS_APP_SETTINGS = "AppSettings"
        private const val THEME_COLOR_KEY = "theme_color"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThemaSecBinding.inflate(layoutInflater)
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
    fun applySavedTheme() {
        val savedColor = sharedPreferences.getInt(THEME_COLOR_KEY, -1)
        if (savedColor != -1) {
            window.decorView.setBackgroundColor(ContextCompat.getColor(this, savedColor))
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_hesapMakinesi -> {
                Snackbar.make(binding.root, "Hesap makinesi seçildi!", Snackbar.LENGTH_LONG).show()
                val intent = Intent(this, MainActivity ::class.java)
                startActivity(intent)
                overridePendingTransition(
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right
                )
                return true
            }

            R.id.menu_themaSec -> {
                Snackbar.make(binding.root, "Them seç açıldı!", Snackbar.LENGTH_LONG).show()
                val intent = Intent(this, themaSec::class.java)
                startActivity(intent)
                overridePendingTransition(
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right
                )
                return true

            }



            R.id.menu_BirimDonusturucu -> {
                Snackbar.make(binding.root, "Birim dönüştürücü seçildi", Snackbar.LENGTH_LONG)
                    .show()
                val intent = Intent(this, birimDonusturucu ::class.java)
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

    fun showColorPicker(view:View) {
        val renkSecenekleri = arrayOf("Kırmızı", "Mavi", "Yeşil", "Mor", "Turuncu","Pembe","teal","lime")
        val renkKodlari = arrayOf(R.color.red, R.color.blue, R.color.green, R.color.purple, R.color.orange,R.color.pink,R.color.teal,R.color.lime )

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Tema Rengini Seç")
        builder.setItems(renkSecenekleri) { _, which ->
            changeTheme(renkKodlari[which]) // 📌 Kullanıcının seçtiği renkle temayı değiştir
            saveThemeColor(which)
        }
        builder.show()
    }

    fun changeTheme(color: Int) {
        window.decorView.setBackgroundColor(ContextCompat.getColor(this, color))
    }

    fun saveThemeColor(color: Int) {
        val prefs = getSharedPreferences("AppSettings", MODE_PRIVATE)
        prefs.edit().putInt("theme_color", color).apply()
    }
    fun resetTheme() {
        val prefs = getSharedPreferences("AppSettings", MODE_PRIVATE)
        prefs.edit().remove("theme_color").apply() // 📌 Kaydedilmiş rengi kaldır

    }
    override fun onDestroy() {
        super.onDestroy()
        resetTheme()
    }
    override fun finish() {
        super.finish()
        overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.slide_in_left)
    }
}