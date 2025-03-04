package com.example.littlelemon

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.graphics.Color

@Composable
fun ProfileScreen(navController: NavController) {
    val context = LocalContext.current

    val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    val firstName = sharedPreferences.getString("first_name", "")
    val lastName = sharedPreferences.getString("last_name","")
    val email = sharedPreferences.getString("email", "")


    Column(
    modifier = Modifier
        .fillMaxSize()
        .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Header(title ="Home", onBackClick = {navController.popBackStack()})

        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text="Personal Information",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(24.dp))

        Text("First Name", fontSize = 14.sp, fontWeight = FontWeight.Medium)
        OutlinedTextField(value = firstName ?:"", onValueChange = {},
            modifier = Modifier.fillMaxWidth(), readOnly = true)
        Spacer(modifier = Modifier.height(24.dp))

        Text("Last Name", fontSize = 14.sp, fontWeight = FontWeight.Medium)
        OutlinedTextField(value = lastName ?:"", onValueChange = {},
            modifier = Modifier.fillMaxWidth(), readOnly = true)
        Spacer(modifier = Modifier.height(24.dp))

        Text("Email", fontSize = 14.sp, fontWeight = FontWeight.Medium)
        OutlinedTextField(value = email ?:"", onValueChange = {},
            modifier = Modifier.fillMaxWidth(), readOnly = true)

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                // Clear stored user data from SharedPreferences when logging out
                sharedPreferences.edit().clear().apply()

                // Navigate back to the onboarding screen and clear back stack
                navController.navigate("onboarding") {
                    popUpTo("onboarding") { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth(), // Makes button take full width
            colors = ButtonDefaults.buttonColors(containerColor = Yellow) // Uses predefined Yellow color
        ) {
            Text("Log out", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        }
    }

}
@Preview
@Composable
fun ProfilePreview() {
    ProfileScreen(navController = rememberNavController())
}



