package com.example.littlelemon

import android.util.Log
import android.view.MenuItem
import io.ktor.client.* // Import Ktor HTTP client for making network requests
import io.ktor.client.call.body // Extension function to parse the response body
import io.ktor.client.request.* // Provides methods for making HTTP requests (GET, POST, etc.)
import io.ktor.client.engine.cio.* // CIO engine for handling network requests
import io.ktor.client.plugins.contentnegotiation.* // Plugin for handling JSON content negotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.SerialName // Allows custom serialization names
import kotlinx.serialization.Serializable // Enables Kotlin serialization for data classes
import kotlinx.serialization.json.Json // JSON parsing using KotlinX serialization


val httpClient = HttpClient(CIO) {
    install(ContentNegotiation) {
        json(Json {ignoreUnknownKeys = true})
    }}
@Serializable
data class MenuNetwork(
    @SerialName("menu") val menu: List <MenuItemNetwork>
)

@Serializable
data class MenuItemNetwork(
    val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val image: String,
    var category: String
)

suspend fun fetchMenu(): List<MenuItemNetwork> {
    return try {
        val responseString: String = httpClient.get("https://raw.githubusercontent.com/annamarkovame/little-lemon/refs/heads/main/app/src/main/res/menu.json").body()
        Log.d("RAW API Response", responseString) // ✅ Log raw JSON response

        val response: MenuNetwork = Json { ignoreUnknownKeys = true }.decodeFromString(responseString)
        Log.d("Parsed API Response", response.menu.toString()) // ✅ Log parsed data

        response.menu
    } catch (e: Exception) {
        Log.e("API Error", "Failed to fetch menu", e)
        emptyList()
    }
}




