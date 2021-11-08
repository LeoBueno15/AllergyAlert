package com.example.allergyalert

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.view.Menu
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import com.example.allergyalert.databinding.ActivityMainBinding
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_profiles, R.id.nav_check_product
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val animationBackground = findViewById<DrawerLayout>(R.id.drawer_layout).background as AnimationDrawable
        animationBackground.setEnterFadeDuration(4000)
        animationBackground.setExitFadeDuration(4000)
        animationBackground.start()


//        val fileName = "tech.txt"
//        val fileObject = File(fileName)
//
//        println("file path " + fileObject.canonicalPath)
//        // create a new file
//        fileObject.writeText("This is some text for file writing operations")


        val inputMan: InputStream = this.resources.openRawResource(R.raw.manufacturers_text)
        val lineListMan = mutableListOf<String>()
        inputMan.bufferedReader().forEachLine { lineListMan.add(it) }

        val inputProd: InputStream = this.resources.openRawResource(R.raw.products_text)
        val lineListProd = mutableListOf<String>()
        inputProd.bufferedReader().forEachLine { lineListProd.add(it) }

        val inputIngred: InputStream = this.resources.openRawResource(R.raw.ingredients_text)
        val lineListIngred = mutableListOf<String>()
        inputIngred.bufferedReader().forEachLine { lineListIngred.add(it) }

        println(lineListMan.size)
        println(lineListProd.size)
        println(lineListIngred.size)
        for (i in 0..10000) {
            println("$i. " + lineListMan[i] + " | " + lineListProd[i] + " | " + lineListIngred[i] + "\n")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}