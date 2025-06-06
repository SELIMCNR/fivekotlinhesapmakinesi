package com.example.hesapmakinsi

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hesapmakinsi.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlin.math.cos
import kotlin.math.ln
import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan

class MainActivity : AppCompatActivity() {



    private  lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        setSupportActionBar(binding.toolbar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }/*

     binding.materialButtonArt.setOnClickListener {
         animasyonBaslat(it)
         topla(it)
     }
     binding.materialButtonCarp.setOnClickListener {
         animasyonBaslat(it)
         carp(it)
     }
     binding.materialButtonEksi.setOnClickListener {
         animasyonBaslat(it)
         cikar(it)
     }
     binding.materialButtonBolme.setOnClickListener {
         animasyonBaslat(it)
         bol(it)
     }

    }
    fun animasyonBaslat(view: View) {
        view.animate().apply {
            duration = 100
            scaleX(1.1f)
            scaleY(1.1f)
            withEndAction {
                view.scaleX = 1f
                view.scaleY = 1f
            }
        }
    } */
    /*
    fun hesapla(view: View, islem: (Double, Double) -> Double) {
        try {
            val sayi1 = binding.textInputEditTextSay1.text.toString().toDoubleOrNull()
            val sayi2 = binding.textInputEditTextSay2.text.toString().toDoubleOrNull()

            if (sayi1 == null || (sayi2 == null && islem != ::karekok)) {
                Snackbar.make(binding.root, "Lütfen geçerli sayı(lar) girin!", Snackbar.LENGTH_LONG).show()
                return
            }

            val sonuc = if (islem == ::karekok) islem(sayi1, 0.0) else islem(sayi1, sayi2!!)

            binding.materialTextView.text = "Sonuç: $sonuc"
            Snackbar.make(binding.root, "Sonucunuz: $sonuc", Snackbar.LENGTH_LONG).show()
        } catch (e: Exception) {
            Snackbar.make(binding.root, "Bir hata oluştu: ${e.localizedMessage}", Snackbar.LENGTH_LONG).show()
        }
    }
    fun hesaplaTekli(view: View, islem: (Double) -> Double) {
        try {
            val sayi = binding.textInputEditTextSay1.text.toString().toDoubleOrNull()

            if (sayi == null) {
                Snackbar.make(binding.root, "Lütfen geçerli bir sayı girin!", Snackbar.LENGTH_LONG).show()
                return
            }

            val sonuc = islem(sayi)
            binding.materialTextView.text = "Sonuç: $sonuc"
            Snackbar.make(binding.root, "Sonucunuz: $sonuc", Snackbar.LENGTH_LONG).show()

        } catch (e: Exception) {
            Snackbar.make(binding.root, "Hata oluştu: ${e.localizedMessage}", Snackbar.LENGTH_LONG).show()
        }
    }
    fun topla(view: View) = hesapla(view) { a, b -> a + b }
    fun cikar(view: View) = hesapla(view) { a, b -> a - b }
    fun carp(view: View) = hesapla(view) { a, b -> a * b }
    fun bol(view: View) = hesapla(view) { a, b -> if (b != 0.0) a / b else 0.0 }
    fun modAl(view: View) = hesapla(view) { a, b -> a % b }
    fun usAl(view: View) = hesapla(view) { a, b -> a.pow(b) }
    fun karekok(view: View) = hesapla(view) { a, _ -> sqrt(a) }
    fun yuvarla(view: View) = hesapla(view) { a, _ -> a.roundToInt().toDouble() }

    fun sinAl(view: View) = hesaplaTekli(view) { sin(Math.toRadians(it)) }
    fun cosAl(view: View) = hesaplaTekli(view) { cos(Math.toRadians(it)) }
    fun tanAl(view: View) = hesaplaTekli(view) { tan(Math.toRadians(it)) }

    fun logAl(view: View) = hesaplaTekli(view) { log10(it) }
    fun lnAl(view: View) = hesaplaTekli(view) { ln(it) }
*/
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_hesapMakinesi -> {
                Snackbar.make(binding.root, "Hesap makinesi seçildi!", Snackbar.LENGTH_LONG).show()
                return true
            }
            R.id.menu_Dovizkuru -> {
                Snackbar.make(binding.root, "Döviz kuru seçildi", Snackbar.LENGTH_LONG).show()
                return true
            }
            R.id.menu_BirimDonusturucu -> {
                Snackbar.make(binding.root, "Birim dönüştürücü seçildi", Snackbar.LENGTH_LONG).show()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun ekleKarakter(view: View) {
        val eklenenKarakter = (view as Button).text.toString()
        binding.textInputEditTextIslem.append(eklenenKarakter)
    }
    fun silKarakter(view: View) {
        val mevcutText = binding.textInputEditTextIslem.text.toString()
        if (mevcutText.isNotEmpty()) {
            binding.textInputEditTextIslem.setText(mevcutText.dropLast(1))
        }
    }
    fun temizleIslem(view: View) {
        binding.textInputEditTextIslem.setText("")
        binding.textInputEditTextIslem.requestFocus()
    }

    fun hesaplaMetin(islem: String): Double? {
        try {
            val temizIslem = islem.replace(",", ".") // Virgül yerine nokta kullanımı
            val ifade = temizIslem.split("(?=[+\\-*/%^])|(?<=[+\\-*/%^])".toRegex()) // İşlem operatörlerini koruyarak ayırma

            if (ifade.isEmpty()) return null

            var sonuc = ifade[0].toDoubleOrNull() ?: return null // İlk sayıyı al

            for (i in 1 until ifade.size step 2) {
                val operator = ifade[i]
                val sayi = ifade[i + 1].toDoubleOrNull() ?: return null

                sonuc = when (operator) {
                    "+" -> sonuc + sayi
                    "-" -> sonuc - sayi
                    "*" -> sonuc * sayi
                    "/" -> if (sayi != 0.0) sonuc / sayi else return null
                    "%" -> sonuc % sayi
                    "^" -> sonuc.pow(sayi)
                    else -> return null
                }
            }
            return sonuc
        } catch (e: Exception) {
            return null
        }
    }
    fun hesaplaIslem(view: View) {
        try {
            val girilenIslem = binding.textInputEditTextIslem.text.toString()

            if (girilenIslem.isEmpty()) {
                Snackbar.make(binding.root, "Lütfen bir işlem girin!", Snackbar.LENGTH_LONG).show()
                return
            }

            val sonuc = hesaplaMetin(girilenIslem)

            if (sonuc == null) {
                Snackbar.make(binding.root, "Geçerli bir işlem girin! (örn: 15+10-50%5)", Snackbar.LENGTH_LONG).show()
                return
            }

            binding.textInputEditTextIslem.setText(sonuc.toString()) // Sonucu doğrudan girişe yaz
            binding.textInputEditTextIslem.setSelection(binding.textInputEditTextIslem.text?.length ?: 0) // İmleci sona taşı
            Snackbar.make(binding.root, "Sonucunuz: $sonuc", Snackbar.LENGTH_LONG).show()

        } catch (e: Exception) {
            Snackbar.make(binding.root, "Bir hata oluştu: ${e.localizedMessage}", Snackbar.LENGTH_LONG).show()
        }
    }
}
