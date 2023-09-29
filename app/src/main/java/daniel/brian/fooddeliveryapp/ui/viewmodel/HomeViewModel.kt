package daniel.brian.fooddeliveryapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import daniel.brian.fooddeliveryapp.data.dtos.Category
import daniel.brian.fooddeliveryapp.data.dtos.Meal
import daniel.brian.fooddeliveryapp.data.dtos.MealsByCategoryList
import daniel.brian.fooddeliveryapp.data.repository.GetMealsRepository
import daniel.brian.fooddeliveryapp.data.repository.Result
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: GetMealsRepository,
) : ViewModel() {
    private var favoriteMealsLiveData = repository.getFavouriteMeals()
    private var searchMealLiveData = MutableLiveData<Result<List<Meal>>>()
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

    fun searchMeal(search: String): LiveData<Result<List<Meal>>> {
        return repository.searchMeals(search)
    }

    fun observeFavoritesMealsLiveData(): LiveData<List<Meal>> {
        return favoriteMealsLiveData
    }

    fun observeSearchedMealLiveData(): MutableLiveData<Result<List<Meal>>> {
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
