

package com.example.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.* // For spacing, alignment, and layout
import androidx.compose.foundation.background // For setting background color
import androidx.compose.foundation.shape.RoundedCornerShape // For rounded corners (if needed)
import androidx.compose.material3.* // For Material Design 3 components
import androidx.compose.runtime.* // For state management in Compose
import androidx.compose.ui.Alignment // For aligning elements inside a Column
import androidx.compose.ui.Modifier // For modifying UI components
import androidx.compose.ui.graphics.Color // For color properties
import androidx.compose.ui.res.painterResource // To load images from resources
import androidx.compose.ui.text.font.FontWeight // For font weight styling
import androidx.compose.ui.unit.dp // For defining dimensions
import androidx.compose.ui.unit.sp // For font size
import androidx.navigation.NavController // For handling navigation




@Composable
fun HomeScreen(navController: NavController) {
    val darkGreen = Color(0xFF495E57)
    val yellow = Color(0xFFF4CE14)

    Column(modifier = Modifier.fillMaxSize()) {
        Header(title = "Little Lemon", onBackClick = {})

        Column(modifier = Modifier
            .fillMaxWidth()
            .background(darkGreen)
            .padding(16.dp)) {
            Text(
                "Little Lemon",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(text = "Chicago", fontSize = 20.sp, color = Color.White)
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "We are a family-owned Mediterranean restaurant, focused on traditional recipes served with a modern twist.",
                fontSize = 16.sp,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

       MenuItemsScreen(navController)
        }
    }
