package com.example.littlelemon

import android.content.Context
import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Entity(tableName = "menu_items")
data class MenuItemEntity(
    @PrimaryKey val id:Int,
    val title: String,
    val description: String,
    val price: String,
    val image: String
)
@Dao
interface MenuDao {
    @Query("SELECT * FROM menu_items")
    fun getAllMenuItems(): Flow<List<MenuItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMenuItems(menu:List<MenuItemEntity>)

    @Query("DELETE FROM menu_items")
    suspend fun clearMenu()
}




@Database(entities = [MenuItemEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase(){
    abstract fun menuDao(): MenuDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context:Context): AppDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "little_lemon_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

