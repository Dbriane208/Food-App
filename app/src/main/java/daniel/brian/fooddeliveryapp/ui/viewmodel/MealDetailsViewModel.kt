package daniel.brian.fooddeliveryapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import daniel.brian.fooddeliveryapp.data.dtos.Meal
import daniel.brian.fooddeliveryapp.data.dtos.MealList
import daniel.brian.fooddeliveryapp.data.remote.retrofit.RetrofitInstance
import daniel.brian.fooddeliveryapp.data.repository.GetMealsRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class MealDetailsViewModel(
    // passing the argument here allows : dependency injection i.e allows this viewModel to have outside dependencies
    private val repository: GetMealsRepository,
) : ViewModel() {
    private var mealDetailsLiveData = MutableLiveData<Meal>()

    fun getMealDetails(id: String) {
        RetrofitInstance.mealApi.getMealDetails(id).enqueue(object : Callback<MealList> {
            override fun onResponse(
                call: Call<MealList>,
                response: Response<MealList>,
            ) {
                if (response.body() != null) {
                    mealDetailsLiveData.value = response.body()!!.meals[0]
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Timber.d("MealDetails", t.message.toString())
            }
        })
    }

    fun observeMealDetailsLiveData(): MutableLiveData<Meal> {
        return mealDetailsLiveData
    }

    fun insertMeal(meal: Meal) {
        viewModelScope.launch {
            repository.insertMeal(meal)
        }
    }
}
