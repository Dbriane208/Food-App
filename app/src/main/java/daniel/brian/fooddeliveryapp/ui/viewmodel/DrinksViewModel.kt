package daniel.brian.fooddeliveryapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import daniel.brian.fooddeliveryapp.data.dtos.Drink
import daniel.brian.fooddeliveryapp.data.repository.GetDrinkRepository
import daniel.brian.fooddeliveryapp.data.repository.Result

class DrinksViewModel(
    private val repository: GetDrinkRepository,
) : ViewModel() {

    fun getDrinksCategories(): LiveData<Result<List<Drink>>> {
        return repository.getOrdinaryDrink()
    }
}
