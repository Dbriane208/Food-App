package daniel.brian.fooddeliveryapp.data.remote.retrofit

import daniel.brian.fooddeliveryapp.data.dtos.CategoryList
import daniel.brian.fooddeliveryapp.data.dtos.MealList
import daniel.brian.fooddeliveryapp.data.dtos.MealsByCategory
import daniel.brian.fooddeliveryapp.data.dtos.OrdinaryDrinks
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {
    @GET("random.php")
    fun getRandomMeal(): Call<MealList>

    @GET("filter.php")
    fun getPopularItems(@Query("c") CategoryName: String): Call<MealsByCategory>

    @GET("categories.php")
    fun getCategories(): Call<CategoryList>

    @GET("filter.php")
    fun getMealsByCategory(@Query("c") categoryName: String): Call<MealsByCategory>

    @GET("filter.php")
    fun getDrinkCategories(@Query("c") categoryName: String): Call<OrdinaryDrinks>

    @GET("lookup.php")
    fun getMealDetails(@Query("i") id: String): Call<MealList>

    @GET("search.php")
    fun searchMeals(@Query("s") mealName: String): Call<MealList>
}
