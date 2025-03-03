package com.example.littlelemon

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.compose.composable
import androidx.navigation.createGraph

@Composable
fun navGraph(navController: NavController, isLoggedIn: Boolean = false): NavGraph {
    return navController.createGraph(startDestination = if (isLoggedIn) {
        "home"
    }else{
        "onboarding"
    }
    ){
        composable("onboarding") { OnboardingScreen(navController)}
            composable("home") { HomeScreen(navController)  }
            composable("profile") { ProfileScreen(navController)  }


    }
}