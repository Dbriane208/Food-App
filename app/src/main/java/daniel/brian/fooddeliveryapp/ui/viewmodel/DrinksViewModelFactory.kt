package daniel.brian.fooddeliveryapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import daniel.brian.fooddeliveryapp.data.repository.GetDrinkRepository

@Suppress("UNCHECKED_CAST")
class DrinksViewModelFactory(
    private val repository: GetDrinkRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DrinksViewModel(repository) as T
    }
}
