package daniel.brian.fooddeliveryapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import daniel.brian.fooddeliveryapp.data.repository.GetMealDetailsRepository
import daniel.brian.fooddeliveryapp.data.repository.GetMealsRepository

// ViewModelFactory helps in creating instances of the viewModels which enable dependency injection
@Suppress("UNCHECKED_CAST")
class MealDetailsViewModelFactory(
    private val repository: GetMealsRepository,
    private val detailsRepo: GetMealDetailsRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MealDetailsViewModel(repository, detailsRepo) as T
    }
}
