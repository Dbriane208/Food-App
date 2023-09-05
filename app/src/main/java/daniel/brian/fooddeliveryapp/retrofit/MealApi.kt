package daniel.brian.fooddeliveryapp.retrofit

import daniel.brian.fooddeliveryapp.pojo.Category
import daniel.brian.fooddeliveryapp.pojo.CategoryList
import daniel.brian.fooddeliveryapp.pojo.MealsByCategory
import daniel.brian.fooddeliveryapp.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {
    @GET("random.php")
    fun getRandomMeal():Call<MealList>
    @GET("filter.php?")
    fun getPopularItems(@Query("c")CategoryName : String) : Call<MealsByCategory>

    @GET("categories.php")
    fun getMealsByCategory(): Call<CategoryList>

}