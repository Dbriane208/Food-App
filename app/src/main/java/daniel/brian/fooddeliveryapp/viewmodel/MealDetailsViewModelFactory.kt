package daniel.brian.fooddeliveryapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import daniel.brian.fooddeliveryapp.db.MealDataBase

//ViewModelFactory helps in creating instances of the viewModels which enable dependency injection
@Suppress("UNCHECKED_CAST")
class MealDetailsViewModelFactory(
    private val mealDataBase: MealDataBase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MealDetailsViewModel(mealDataBase) as T
    }
}