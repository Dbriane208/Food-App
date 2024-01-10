package daniel.brian.fooddeliveryapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import daniel.brian.fooddeliveryapp.data.dtos.Category
import daniel.brian.fooddeliveryapp.data.dtos.CategoryList
import daniel.brian.fooddeliveryapp.data.dtos.Meal
import daniel.brian.fooddeliveryapp.data.dtos.MealList
import daniel.brian.fooddeliveryapp.data.dtos.MealsByCategory
import daniel.brian.fooddeliveryapp.data.dtos.MealsByCategoryList
import daniel.brian.fooddeliveryapp.data.local.db.MealDataBase
import daniel.brian.fooddeliveryapp.data.remote.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class GetMealsRepository(
    private val database: MealDataBase,
) {

    fun getRandomMeal(): LiveData<Result<Meal>> {
        val randomMealLiveData = MutableLiveData<Result<Meal>>()
        randomMealLiveData.postValue(Result.Loading)
        RetrofitInstance.mealApi.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                // retrieves the first meal of the meal list and assigns it to randomLiveData.value
                if (response.body() != null) {
                    val mealsResponse = response.body()!!.meals[0]
                    randomMealLiveData.postValue(Result.Success(mealsResponse))
                } else {
                    randomMealLiveData.postValue(Result.Error("Response body is null"))
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Timber.d(t.message.toString())
                randomMealLiveData.postValue(Result.Error("Response body is null"))
            }
        })
        return randomMealLiveData
    }

    fun getPopularMeals(): LiveData<Result<List<MealsByCategoryList>>> {
        val popularMeals = MutableLiveData<Result<List<MealsByCategoryList>>>()
        popularMeals.postValue(Result.Loading)
        RetrofitInstance.mealApi.getPopularItems("Seafood")
            .enqueue(object : Callback<MealsByCategory> {
                override fun onResponse(
                    call: Call<MealsByCategory>,
                    response: Response<MealsByCategory>,
                ) {
                    if (response.body() != null) {
                        val meals = response.body()!!.meals
                        popularMeals.postValue(Result.Success(meals))
                    } else {
                        popularMeals.postValue(Result.Error("Response is null"))
                        return
                    }
                }

                override fun onFailure(call: Call<MealsByCategory>, t: Throwable) {
                    Timber.d(t.message.toString())
                    popularMeals.postValue(Result.Error(t.message))
                }
            })
        return popularMeals
    }

    fun getMealsByCategory(): LiveData<Result<List<Category>>> {
        val mealsCategory = MutableLiveData<Result<List<Category>>>()
        mealsCategory.postValue(Result.Loading)
        RetrofitInstance.mealApi.getCategories().enqueue(object : Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                val categoryMeal = response.body()?.categories
                mealsCategory.postValue(Result.Success(categoryMeal))
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Timber.tag("HomeFragment").e(t.message.toString())
                mealsCategory.postValue(Result.Error(t.message.toString()))
            }
        })
        return mealsCategory
    }

    fun searchMeals(search: String): MutableLiveData<Result<List<Meal>>> {
        val searchMealLiveData = MutableLiveData<Result<List<Meal>>>()
        RetrofitInstance.mealApi.searchMeals(search).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                val meal = response.body()?.meals
                searchMealLiveData.postValue(Result.Success(meal))
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                searchMealLiveData.postValue(Result.Error(t.message.toString()))
            }
        })
        return searchMealLiveData
    }

    // functions in the mealDatabase
    fun getFavouriteMeals(): LiveData<List<Meal>> {
        return database.mealDao().getAllMeals()
    }

    suspend fun insertMeal(meal: Meal) {
        database.mealDao().upsert(meal)
    }

    suspend fun deleteMeal(meal: Meal) {
        database.mealDao().delete(meal)
    }

}

// This is a wrapper class
sealed class Result<out T>(val data: T? = null, val message: String? = null) {
    object Loading : Result<Nothing>()
    class Success<T>(data: T?) : Result<T>(data)
    class Error(message: String?) : Result<Nothing>(message = message)
}
