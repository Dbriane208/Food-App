package daniel.brian.fooddeliveryapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import daniel.brian.fooddeliveryapp.db.MealDataBase
import daniel.brian.fooddeliveryapp.pojo.Category
import daniel.brian.fooddeliveryapp.pojo.CategoryList
import daniel.brian.fooddeliveryapp.pojo.Meal
import daniel.brian.fooddeliveryapp.pojo.MealList
import daniel.brian.fooddeliveryapp.pojo.MealsByCategory
import daniel.brian.fooddeliveryapp.pojo.MealsByCategoryList
import daniel.brian.fooddeliveryapp.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class HomeViewModel(
    private val mealDataBase: MealDataBase,
) : ViewModel() {
    private var randomMealLiveData = MutableLiveData<Meal>()
    private var popularItemsLiveData = MutableLiveData<List<MealsByCategoryList>>()
    private var categoryMealsLiveData = MutableLiveData<List<Category>>()
    private var favoriteMealsLiveData = mealDataBase.mealDao().getAllMeals()
    private var searchMealLiveData = MutableLiveData<List<Meal>>()
    private var randomSavedState: Meal? = null

    // makes asynchronous network request to retrieve a random meal using retrofit
    fun getRandomMeal() {
        randomSavedState?.let { randomMeal ->
            randomMealLiveData.postValue(randomMeal)
            return
        }
        // Callback is used to asynchronously handle requests after a network call
        RetrofitInstance.mealApi.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                // retrieves the first meal of the meal list and assigns it to randomLiveData.value
                if (response.body() != null) {
                    val randomMeal: Meal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal
                    randomSavedState = randomMeal
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Timber.tag("HomeFragment").d(t.message.toString())
            }
        })
    }

    fun getPopularItems() {
        RetrofitInstance.mealApi.getPopularItems("Seafood")
            .enqueue(object : Callback<MealsByCategory> {
                override fun onResponse(
                    call: Call<MealsByCategory>,
                    response: Response<MealsByCategory>,
                ) {
                    if (response.body() != null) {
                        popularItemsLiveData.value = response.body()!!.meals
                    } else {
                        return
                    }
                }

                override fun onFailure(call: Call<MealsByCategory>, t: Throwable) {
                    Timber.tag("HomeFragment").d(t.message.toString())
                }
            })
    }

    fun getMealsByCategory() {
        RetrofitInstance.mealApi.getCategories().enqueue(object : Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                val categoryMeal = response.body()?.categories
                categoryMeal?.let {
                    categoryMealsLiveData.postValue(it)
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Timber.tag("HomeFragment").e(t.message.toString())
            }
        })
    }

    fun searchMeal(search: String) {
        RetrofitInstance.mealApi.searchMeals(search).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                val meal = response.body()?.meals
                meal?.let {
                    searchMealLiveData.postValue(it)
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Timber.e("HomeFragment", t.message.toString())
            }
        })
    }

    // allows the external UI controllers to access the randomMealLiveData
    fun observeRandomMealLivedata(): LiveData<Meal> {
        return randomMealLiveData
    }

    fun observePopularItemsLiveData(): LiveData<List<MealsByCategoryList>> {
        return popularItemsLiveData
    }

    fun observeCategoryMealsLiveData(): MutableLiveData<List<Category>> {
        return categoryMealsLiveData
    }

    fun observeFavoritesMealsLiveData(): LiveData<List<Meal>> {
        return favoriteMealsLiveData
    }

    fun observeSearchedMealLiveData(): MutableLiveData<List<Meal>> {
        return searchMealLiveData
    }

    // Coroutine viewModelScope allows the viewModel to automatically close when not in use
    fun insertMeal(meal: Meal) {
        viewModelScope.launch {
            mealDataBase.mealDao().upsert(meal)
        }
    }

    // This will help us on deleting the meal when this function is called
    fun deleteMeal(meal: Meal) {
        viewModelScope.launch {
            mealDataBase.mealDao().delete(meal)
        }
    }
}
