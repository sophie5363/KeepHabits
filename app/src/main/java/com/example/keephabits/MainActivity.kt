package com.example.keephabits

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.keephabits.ui.introscreen.IntroActivity

class MainActivity : AppCompatActivity() {

    private var userFirstTime = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Vérifie que c'est la première fois que l'utilisateur utilise l'appli
        loadData()

        //Si c'est la première fois, enregistre que l'utilisateur a vu l'écran d'intro,
        // il ne le verra pas à la prochaine utilisation
        if(userFirstTime){
            userFirstTime = false
            saveData()

            val i = Intent(this, IntroActivity::class.java)
            startActivity(i)
            finish()
        }

        setupActionBarWithNavController(findNavController(R.id.navHostFragment))
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.navHostFragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun saveData(){
        val sp = getSharedPreferences("SHARED_PREFS", MODE_PRIVATE)
        sp.edit().apply{
            putBoolean("BOOLEAN_FIRST_TIME", userFirstTime)
            apply()
        }
    }

    private fun loadData(){
        val sp = getSharedPreferences("SHARED_PREFS", MODE_PRIVATE)
        userFirstTime = sp.getBoolean("BOOLEAN_FIRST_TIME", true)
    }
}