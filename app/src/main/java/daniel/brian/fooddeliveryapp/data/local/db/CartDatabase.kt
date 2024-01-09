package daniel.brian.fooddeliveryapp.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import daniel.brian.fooddeliveryapp.data.dtos.Meal
@Database(entities = [Meal::class],version=1)// having a meal class as the database entity
@TypeConverters(MealTypeConverter::class)
abstract class CartDatabase: RoomDatabase() {
    // accessing all the functions in the cartDao
    abstract fun cartDao(): cartDao
    companion object{
        // having a variable to track changes and show them in other threads
        @Volatile
        var INSTANCE: CartDatabase? = null
        @Synchronized // ensures one thread executes at a time
        fun getInstance(context: Context): CartDatabase{
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context,CartDatabase::class.java,"cartDB").fallbackToDestructiveMigration().build()
            }
            return INSTANCE as CartDatabase
        }
    }
}