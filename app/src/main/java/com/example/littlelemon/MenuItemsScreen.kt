package com.example.littlelemon

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.littlelemon.AppDatabase

@Composable
fun MenuItemsScreen(navController: NavController, searchQuery: String, selectedCategory: String?) {
    val context = LocalContext.current
    val database = remember { AppDatabase.getDatabase(context) }
    val menuDao = remember { database.menuDao() }
    val menuItems by menuDao.getAllMenuItems().collectAsStateWithLifecycle(initialValue = emptyList())

    // **Filter Menu Items Based on Search Query and Category**
    val filteredMenuItems = menuItems.filter { item ->
        (searchQuery.isBlank() ||
                item.title.contains(searchQuery, ignoreCase = true) ||
                item.description.contains(searchQuery, ignoreCase = true)) &&
                (selectedCategory == null || item.category.equals(selectedCategory, ignoreCase = true))
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Little Lemon Menu",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (filteredMenuItems.isEmpty()) {
            Text(text = "No menu items found.", fontSize = 16.sp, fontWeight = FontWeight.Medium)
        } else {
            filteredMenuItems.forEach { item ->
                MenuItemComposable(item)
            }
        }
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItemComposable(item: MenuItemEntity) {
    LaunchedEffect(item.image) {
        Log.d("GlideDebug", "Image URL: ${item.image}")
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = item.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(text = item.description, fontSize = 14.sp)
            Text(text = "Price: $${item.price}", fontSize = 14.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(8.dp))

            GlideImage(
                model = item.image?.takeIf { it.isNotBlank() } ?: "https://via.placeholder.com/150",
                contentDescription = item.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f), // ✅ Ensures uniform image size
                requestBuilderTransform = { requestBuilder ->
                    requestBuilder.apply(
                        RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .centerCrop() // ✅ Crops the image to maintain proper aspect ratio
                    )
                }
            )
        }
    }
}
