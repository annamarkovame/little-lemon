package com.example.littlelemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph

class MainActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContent {
            LittleLemonApp()
        }

    }

}

@Composable
fun LittleLemonApp(){
    val navController = rememberNavController()
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("LittleLemon", 0)
    val isLoggedIn = sharedPreferences.getString("firstName", null) != null

    Surface(modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
        ) {
        NavHost(navController = navController, graph = navGraph(navController)  )

    }
}

@Preview
@Composable
fun LittleLemonPreview(){
    LittleLemonApp()
}



