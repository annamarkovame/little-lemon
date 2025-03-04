package com.example.littlelemon

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.flow.Flow


@Entity(tableName = "menu_items")
data class MenuItemEntity(
    @PrimaryKey val id:Int,
    val title: String,
    val description: String,
    val price: String,
    val image: String,
    val category: String
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




@Database(entities = [MenuItemEntity::class], version = 2)
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
                ).addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE menu_items ADD COLUMN category TEXT NOT NULL DEFAULT ''")
    }
}


