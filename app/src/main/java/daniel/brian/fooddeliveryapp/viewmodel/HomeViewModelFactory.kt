package daniel.brian.fooddeliveryapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import daniel.brian.fooddeliveryapp.db.MealDataBase

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(
    private val mealDataBase: MealDataBase
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(mealDataBase) as T
    }
}