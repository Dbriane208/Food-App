package daniel.brian.fooddeliveryapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import daniel.brian.fooddeliveryapp.data.dtos.Meal
import daniel.brian.fooddeliveryapp.data.repository.GetMealDetailsRepository
import daniel.brian.fooddeliveryapp.data.repository.GetMealsRepository
import daniel.brian.fooddeliveryapp.data.repository.Result
import kotlinx.coroutines.launch

class MealDetailsViewModel(
    // passing the argument here allows : dependency injection i.e allows this viewModel to have outside dependencies
    private val repository: GetMealsRepository,
    private val detailsRepo: GetMealDetailsRepository,
) : ViewModel() {
    private var mealDetailsLiveData = MutableLiveData<Meal>()

    fun getMealDetails(id: String): MutableLiveData<Result<Meal>> {
        return detailsRepo.getMealDetails(id)
    }

    fun insertMeal(meal: Meal) {
        viewModelScope.launch {
            repository.insertMeal(meal)
        }
    }
}
