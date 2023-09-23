package daniel.brian.fooddeliveryapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import daniel.brian.fooddeliveryapp.pojo.Meal

@Dao // it's a design pattern used to separate data access logic from business logic
interface MealDao {
    // This function updates and inserts into the database incase there is a conflict
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(meal: Meal)

    @Delete
    suspend fun delete(meal: Meal)

    // The livedata returns a list of all the meal objects
    @Query("SELECT * FROM mealInformation")
    fun getAllMeals(): LiveData<List<Meal>>
}
