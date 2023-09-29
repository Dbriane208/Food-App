package daniel.brian.fooddeliveryapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import daniel.brian.fooddeliveryapp.data.local.db.MealDataBase
import daniel.brian.fooddeliveryapp.data.repository.GetMealsRepository

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(
    private val repository: GetMealsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(repository) as T
    }
}

// fun <VM : ViewModel> viewModelFactory(initializer: () -> VM): ViewModelProvider.Factory {
//    return object : ViewModelProvider.Factory {
//        override fun <T : ViewModel> create(modelClass: Class<T>): T {
//            return initializer as T
//        }
//    }
// }
