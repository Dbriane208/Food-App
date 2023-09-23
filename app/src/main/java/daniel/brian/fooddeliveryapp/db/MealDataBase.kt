package daniel.brian.fooddeliveryapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import daniel.brian.fooddeliveryapp.pojo.Meal

// Indicates that the database will have Meal class as it's table
@Database(entities = [Meal::class], version = 2)
@TypeConverters(MealTypeConverter::class)
abstract class MealDataBase : RoomDatabase() {
    // This function obtains all the functions of the MealDao
    abstract fun mealDao(): MealDao

    companion object {
        @Volatile // ensures changes made to this variable are visible to other threads
        var INSTANCE: MealDataBase ? = null

        @Synchronized // ensures that only one thread executes at a time
        fun getInstance(context: Context): MealDataBase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    MealDataBase::class.java,
                    "mealDB",
                ).fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as MealDataBase
        }
    }
}
