package com.example.hesapmakinsi

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
import android.view.animation.ScaleAnimation
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hesapmakinsi.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import java.util.Stack
import kotlin.math.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    companion object {
        private const val PREFS_APP_SETTINGS = "AppSettings"
        private const val THEME_COLOR_KEY = "theme_color"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
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
    fun applySavedTheme() {
        val savedColor = sharedPreferences.getInt(THEME_COLOR_KEY, -1)
        if (savedColor != -1) {
            window.decorView.setBackgroundColor(ContextCompat.getColor(this, savedColor))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_hesapMakinesi -> {
                Snackbar.make(binding.root, "Hesap makinesi seçildi!", Snackbar.LENGTH_LONG).show()
                val intent = Intent(this@MainActivity, MainActivity::class.java)
                startActivity(intent)
                overridePendingTransition(
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right
                )
                return true
            }
            R.id.menu_themaSec -> {
                Snackbar.make(binding.root, "tema kuru seçildi", Snackbar.LENGTH_LONG).show()
                val intent = Intent(this@MainActivity, themaSec::class.java)
                startActivity(intent)
                overridePendingTransition(
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right
                )
                return true
            }
            R.id.menu_BirimDonusturucu -> {
                Snackbar.make(binding.root, "Birim dönüştürücü seçildi", Snackbar.LENGTH_LONG).show()
                val intent = Intent(this@MainActivity, birimDonusturucu::class.java)
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
    fun fadeIn(view: View) {
        view.visibility = View.VISIBLE
        val fadeIn = AlphaAnimation(0f, 1f).apply {
            duration = 300
            interpolator = AccelerateInterpolator()
        }
        view.startAnimation(fadeIn)
    }

    fun fadeOut(view: View) {
        val fadeOut = AlphaAnimation(1f, 0f).apply {
            duration = 300
            interpolator = DecelerateInterpolator()
        }
        view.startAnimation(fadeOut)
        view.visibility = View.GONE
    }
    fun scaleUp(view: View){
        val scaleUp = ScaleAnimation(
            0.95f, 1f, 0.95f, 1f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = 150
            interpolator = OvershootInterpolator()
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
    fun buttonSinCos(view: View) {
        if (binding.scientificButtons.visibility == View.VISIBLE) {
            fadeOut(binding.scientificButtons)
            binding.scientificButtons.postDelayed({
                binding.scientificButtons.visibility = View.GONE
            }, 300) // Animasyon süresi kadar beklet

            fadeIn(binding.buttonHistory)
            binding.buttonHistory.visibility = View.VISIBLE
        } else {
            binding.scientificButtons.visibility = View.VISIBLE
            fadeIn(binding.scientificButtons)

            fadeOut(binding.buttonHistory)
            binding.buttonHistory.postDelayed({
                binding.buttonHistory.visibility = View.GONE
            }, 300)
        }
    }
    fun toggleDrawer(view: View) {
        try {
            if (binding.drawerLayout.visibility == View.GONE) {
                binding.drawerLayout.openDrawer(GravityCompat.START) // 📌 Önce açma işlemi
                fadeIn(binding.drawerLayout)
                binding.drawerLayout.visibility = View.VISIBLE

                fadeOut(binding.numericPad)
                binding.numericPad.postDelayed({ binding.numericPad.visibility = View.GONE }, 300)

                fadeOut(binding.buttonSinCos)
                binding.buttonSinCos.postDelayed({ binding.buttonSinCos.visibility = View.GONE }, 300)

                fadeIn(binding.buttonHistory)
                binding.buttonHistory.visibility = View.VISIBLE

                fadeOut(binding.textInputEditTextIslem)
                binding.textInputEditTextIslem.postDelayed({ binding.textInputEditTextIslem.visibility = View.GONE }, 300)

            } else {
                fadeOut(binding.drawerLayout)
                binding.drawerLayout.postDelayed({
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    binding.drawerLayout.visibility = View.GONE

                    // 📌 toggleDrawer kapanınca buttonSinCos geri gelsin
                    fadeIn(binding.buttonSinCos)
                    binding.buttonSinCos.visibility = View.VISIBLE
                }, 300)

                fadeIn(binding.numericPad)
                binding.numericPad.visibility = View.VISIBLE

                fadeIn(binding.buttonHistory)
                binding.buttonHistory.visibility = View.VISIBLE

                fadeIn(binding.textInputEditTextIslem)
                binding.textInputEditTextIslem.visibility = View.VISIBLE
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Snackbar.make(binding.root, "Bir hata oluştu: ${e.localizedMessage}", Snackbar.LENGTH_LONG).show()
        }
    }

    fun hesaplaIslem(view: View) {
        val girilenIslem = binding.textInputEditTextIslem.text.toString()
        if (girilenIslem.isEmpty()) {
            Snackbar.make(binding.root, "Lütfen bir işlem girin!", Snackbar.LENGTH_LONG).show()
            return
        }
        try {
            val sonuc = evaluateExpression(girilenIslem)
            binding.textInputEditTextIslem.setText(sonuc.toString())
            binding.textInputEditTextIslem.setSelection(binding.textInputEditTextIslem.text?.length ?: 0)
            kaydetIslemGecmisi("$girilenIslem = $sonuc")
            gosterIslemGecmisi()
            Snackbar.make(binding.root, "Sonucunuz: $sonuc", Snackbar.LENGTH_LONG).show()
        } catch (e: Exception) {
            Snackbar.make(binding.root, "Geçerli bir işlem girin!", Snackbar.LENGTH_LONG).show()
        }
    }
    fun preprocessExpression(expr: String): String {
        var processed = expr

        // Aşağıdaki ifade: 50 + 10% → 50 + (50 * 10 / 100)
        val patternAddSub = Regex("""(\d+(?:\.\d+)?)([\+\-])(\d+(?:\.\d+)?)%""")
        processed = patternAddSub.replace(processed) {
            val left = it.groupValues[1]
            val op = it.groupValues[2]
            val percent = it.groupValues[3]
            "$left$op($left*$percent/100)"
        }

        // Diğer işlemler için (örneğin çarpma ve bölme), yüzdeyi sadece değeriyle değiştir (10% → 0.10)
        val patternPercentOnly = Regex("""(\d+(?:\.\d+)?)%""")
        processed = patternPercentOnly.replace(processed) {
            val value = it.groupValues[1].toDouble() / 100.0
            value.toString()
        }

        return processed
    }

    fun evaluateExpression(expression: String): Double {




        // Önce ifadeyi bağlamsal yüzde oranlarını destekleyecek şekilde dönüştür
        val preprocessed = preprocessExpression(expression)
        val replaced = preprocessed
            .replace("π", Math.PI.toString())
            .replace("e", Math.E.toString())
            .replace("√", "sqrt")
            .replace("sin", "sin")
            .replace("cos", "cos")
            .replace("tan", "tan")
            .replace("log", "log")
            .replace("ln", "ln")
            .replace("\\s+".toRegex(), "")

        val parser = ExpressionParser(replaced)
        return try {
            parser.parse()
        } catch (e: Exception) {
            Double.NaN
        }
    }

    fun kaydetIslemGecmisi(yeniKayit: String) {
        val eski = sharedPreferences.getString("islem_gecmisi", "") ?: ""
        val yeni = if (eski.isEmpty()) yeniKayit else "$eski\n$yeniKayit"
        sharedPreferences.edit().putString("islem_gecmisi", yeni).apply()
    }

    fun gosterIslemGecmisi() {
        val gecmis = sharedPreferences.getString("islem_gecmisi", "Henüz işlem geçmişi yok...")
        binding.historyContent.text = gecmis
    }

    fun temizleIslemGecmisi() {
        sharedPreferences.edit().remove("islem_gecmisi").apply()
    }
    fun temizleIslem(view: View) {
        binding.textInputEditTextIslem.setText("")
        binding.textInputEditTextIslem.requestFocus()
    } fun resetTheme() {
        val prefs = getSharedPreferences("AppSettings", MODE_PRIVATE)
        prefs.edit().remove("theme_color").apply()

    }

    override fun onDestroy() {
        super.onDestroy()
        temizleIslemGecmisi()
        resetTheme()
    }


    override fun onStop() {
        super.onStop()
        temizleIslemGecmisi()
    }
    override fun finish() {
        super.finish()
        overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.slide_in_left)
    }
}

class ExpressionParser(private val input: String) {
    private var pos = 0
    private val length = input.length
    private val currentChar: Char?
        get() = if (pos < length) input[pos] else null

    private fun advance() {
        pos++
    }

    private fun skipWhitespace() {
        while (currentChar?.isWhitespace() == true) advance()
    }

    private fun parseNumber(): Double {
        val start = pos
        while (currentChar?.isDigit() == true || currentChar == '.') advance()
        return input.substring(start, pos).toDouble()
    }

    private fun parseIdentifier(): String {
        val start = pos
        while (currentChar?.isLetter() == true || currentChar == 'π') advance()
        return input.substring(start, pos)
    }

    fun parse(): Double {
        val result = parseExpression()
        skipWhitespace()
        if (pos < length) throw IllegalArgumentException("Unexpected character: $currentChar")
        return result
    }

    private fun parseExpression(): Double {
        var result = parseTerm()
        while (true) {
            skipWhitespace()
            result = when (currentChar) {
                '+' -> {
                    advance()
                    result + parseTerm()
                }
                '-' -> {
                    advance()
                    result - parseTerm()
                }
                else -> return result
            }
        }
    }

    private fun parseTerm(): Double {
        var result = parseFactor()
        while (true) {
            skipWhitespace()
            result = when (currentChar) {
                '*' -> {
                    advance()
                    result * parseFactor()
                }
                '/' -> {
                    advance()
                    result / parseFactor()
                }
                else -> return result
            }
        }
    }

    private fun parseFactor(): Double {
        skipWhitespace()
        var result = parsePrimary()
        skipWhitespace()
        while (currentChar == '^') {
            advance()
            result = result.pow(parsePrimary())
        }
        return result
    }

    // Ana sayı/fonksiyon/() ayrıştırıcısı ve postfix işleyicisi
    private fun parsePrimary(): Double {
        skipWhitespace()
        var result: Double = when {
            currentChar == '(' -> {
                advance()
                val res = parseExpression()
                skipWhitespace()
                if (currentChar != ')') throw IllegalArgumentException("Expected ')'")
                advance()
                res
            }
            currentChar?.isDigit() == true || currentChar == '.' -> parseNumber()
            currentChar?.isLetter() == true || currentChar == 'π' -> {
                val name = parseIdentifier()
                skipWhitespace()
                val argument = if (currentChar == '(') {
                    advance()
                    val arg = parseExpression()
                    skipWhitespace()
                    if (currentChar != ')') throw IllegalArgumentException("Expected ')'")
                    advance()
                    arg
                } else {
                    parsePrimary()
                }

                when (name) {
                    "π" -> Math.PI
                    "e" -> Math.E
                    "sin" -> sin(Math.toRadians(argument))
                    "cos" -> cos(Math.toRadians(argument))
                    "tan" -> tan(Math.toRadians(argument))
                    "log" -> log10(argument)
                    "ln" -> ln(argument)
                    "sqrt" -> sqrt(argument)
                    "inv" -> 1.0 / argument
                    "rad" -> Math.toRadians(argument)
                    "deg" -> Math.toDegrees(argument)
                    else -> throw IllegalArgumentException("Unknown function: $name")
                }
            }
            currentChar == '-' -> {
                advance()
                -parsePrimary()
            }
            else -> throw IllegalArgumentException("Unexpected character: $currentChar")
        }

        skipWhitespace()
        // Postfix işlemler
        while (currentChar == '%' || currentChar == '!') {
            when (currentChar) {
                '%' -> {
                    advance()
                    result /= 100.0
                }
                '!' -> {
                    advance()
                    result = factorial(result)
                }
            }
            skipWhitespace()
        }

        return result
    }

    private fun factorial(n: Double): Double {
        if (n < 0 || n != floor(n)) throw IllegalArgumentException("Faktöriyel sadece pozitif tam sayılar için tanımlıdır.")
        var result = 1.0
        for (i in 1..n.toInt()) result *= i
        return result
    }
}

