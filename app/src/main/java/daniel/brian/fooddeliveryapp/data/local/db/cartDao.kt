package daniel.brian.fooddeliveryapp.data.local.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import daniel.brian.fooddeliveryapp.data.dtos.Meal

// it's designed to separate the business logic from the data access logic
@Dao
interface cartDao {
    // function to add item and update it from cart
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(meal : Meal)

    // function to delete item from cart
    @Delete
    fun delete(meal : Meal)

    // function to read all the items in the cart database
    @Query("Select * from mealInformation")
    fun getCartItems(): LiveData<List<Meal>>

}