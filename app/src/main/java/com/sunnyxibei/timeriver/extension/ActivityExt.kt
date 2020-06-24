package com.sunnyxibei.timeriver.extension

import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController

fun FragmentActivity.findNavControllerOfContainer(viewId: Int): NavController {
    return (supportFragmentManager.findFragmentById(viewId) as NavHostFragment).findNavController()
}