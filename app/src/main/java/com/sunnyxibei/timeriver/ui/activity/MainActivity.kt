package com.sunnyxibei.timeriver.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.sunnyxibei.timeriver.R
import com.sunnyxibei.timeriver.extension.findNavControllerOfContainer

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = findNavControllerOfContainer(R.id.nav_host_fragment)
    }

    override fun onSupportNavigateUp(): Boolean = navController.navigateUp()
}