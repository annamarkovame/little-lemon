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
import kotlinx.coroutines.flow.Flow

@Composable
fun MenuItemsScreen(navController: NavController) {
    val context = LocalContext.current
    val database = remember { AppDatabase.getDatabase(context) } // Prevents unnecessary recompositions
    val menuDao = remember { database.menuDao() } // Ensures stable reference
    val menuItems by menuDao.getAllMenuItems().collectAsStateWithLifecycle(initialValue = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Little Lemon Menu",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (menuItems.isEmpty()) {
            Text(text = "No menu items found.")
        } else {
            menuItems.forEach { item ->
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

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
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
                .size(150.dp) // Ensure proper size
                .fillMaxWidth(),
            requestBuilderTransform = { requestBuilder ->
                requestBuilder.apply(
                    RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
            }
        )
    }
}


