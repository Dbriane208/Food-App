package daniel.brian.fooddeliveryapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import daniel.brian.fooddeliveryapp.db.MealDataBase
import daniel.brian.fooddeliveryapp.pojo.Meal
import daniel.brian.fooddeliveryapp.pojo.MealList
import daniel.brian.fooddeliveryapp.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class MealDetailsViewModel(
    // passing the argument here allows : dependency injection i.e allows this viewModel to have outside dependencies
    private val mealDataBase: MealDataBase
) : ViewModel() {
    private var mealDetailsLiveData = MutableLiveData<Meal>()

    fun getMealDetails() {
        RetrofitInstance.mealApi.getMealDetails("idMeal").enqueue(object : Callback<MealList>{
            override fun onResponse(
                call: Call<MealList>,
                response: Response<MealList>
            ) {
                if (response.body() != null){
                    mealDetailsLiveData.value = response.body()!!.meals[0]
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Timber.d("MealDetails",t.message.toString())
            }

        })
    }

    fun observeMealDetailsLiveData(): MutableLiveData<Meal> {
        return mealDetailsLiveData
    }

    //coroutine viewModelScope allows the viewModel to automatically close when not in use
    fun insertMeal(meal : Meal){
        viewModelScope.launch {
            mealDataBase.mealDao().upsert(meal)
        }
    }

    //viewModelScope.launch handles the tasks of deletion or insertion asynchronously
    fun deleteMeal(meal : Meal){
        viewModelScope.launch {
            mealDataBase.mealDao().delete(meal)
        }
    }
}