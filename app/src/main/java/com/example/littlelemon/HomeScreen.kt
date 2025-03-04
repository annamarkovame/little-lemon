package com.example.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    val darkGreen = Color(0xFF495E57)
    val yellow = Color(0xFFF4CE14)

    var searchQuery by remember { mutableStateOf("") } // 🔍 Search state
    var selectedCategory by remember { mutableStateOf<String?>(null) } // 🍽️ Category filter

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // ✅ Whole screen is scrollable
        verticalArrangement = Arrangement.spacedBy(16.dp) // Adds spacing between sections
    ) {
        // **Header Section**
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Little Lemon Logo",
                )
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "Profile Picture",
                    modifier = Modifier.size(100.dp).clickable { navController.navigate("profile") }
                )
            }
        }

        // **Hero Section**
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(darkGreen)
                    .padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Little Lemon",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = yellow
                        )
                        Text(
                            text = "Chicago",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "We are a family-owned Mediterranean restaurant, focused on traditional recipes served with a modern twist.",
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Image(
                        painter = painterResource(id = R.drawable.hero_image),
                        contentDescription = "Restaurant Image",
                        modifier = Modifier
                            .size(120.dp) // ✅ Larger hero image
                            .background(Color.Gray, RoundedCornerShape(8.dp))
                    )
                }
            }
        }

        // **Search Bar**
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray, RoundedCornerShape(8.dp))
                    .padding(12.dp)
            ) {
                BasicTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(fontSize = 16.sp)
                )
                if (searchQuery.isEmpty()) {
                    Text("Enter search phrase", color = Color.Gray)
                }
            }
        }

        // **Category Filters - Now Scrollable!**
        item {
            val categories = listOf("All", "Starters", "Mains", "Desserts", "Drinks")

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { category ->
                    Button(
                        onClick = {
                            selectedCategory = if (category == "All") null else category
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedCategory == category) yellow else Color.LightGray
                        )
                    ) {
                        Text(
                            category,
                            color = Color.Black,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        // **Menu Items - Filtered by Search and Category**
        item {
            MenuItemsScreen(
                navController = navController,
                searchQuery = searchQuery,
                selectedCategory = selectedCategory
            )
        }
    }
}
