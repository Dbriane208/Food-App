package daniel.brian.fooddeliveryapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import daniel.brian.fooddeliveryapp.data.dtos.Category
import daniel.brian.fooddeliveryapp.data.dtos.Meal
import daniel.brian.fooddeliveryapp.data.dtos.MealList
import daniel.brian.fooddeliveryapp.data.dtos.MealsByCategoryList
import daniel.brian.fooddeliveryapp.data.remote.retrofit.RetrofitInstance
import daniel.brian.fooddeliveryapp.data.repository.GetMealsRepository
import daniel.brian.fooddeliveryapp.data.repository.Result
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class HomeViewModel(
    private val repository: GetMealsRepository,
) : ViewModel() {
    private var favoriteMealsLiveData = repository.getFavouriteMeals()
    private var searchMealLiveData = MutableLiveData<List<Meal>>()
    var randomSavedState: Meal? = null
        private set

    // makes asynchronous network request to retrieve a random meal using retrofit
    fun getRandomMeal(): LiveData<Result<Meal>> {
        return repository.getRandomMeal()
    }

    fun storeRandomMeal(meal: Meal) {
        randomSavedState = meal
    }

    fun getPopularItems(): LiveData<Result<List<MealsByCategoryList>>> {
        return repository.getPopularMeals()
    }

    fun getMealsByCategory(): LiveData<Result<List<Category>>> {
        return repository.getMealsByCategory()
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
                Timber.tag("HomeFragment").e(t.message.toString())
            }
        })
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
            repository.insertMeal(meal)
        }
    }

    // This will help us on deleting the meal when this function is called
    fun deleteMeal(meal: Meal) {
        viewModelScope.launch {
            repository.deleteMeal(meal)
        }
    }
}
