package daniel.brian.fooddeliveryapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import daniel.brian.fooddeliveryapp.data.dtos.MealsByCategory
import daniel.brian.fooddeliveryapp.data.dtos.MealsByCategoryList
import daniel.brian.fooddeliveryapp.data.remote.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class CategoriesListViewModel : ViewModel() {
    val mealsLiveData = MutableLiveData<List<MealsByCategoryList>>()

    fun getMealsByCategory(categoryName: String) {
        RetrofitInstance.mealApi.getMealsByCategory(categoryName)
            .enqueue(object : Callback<MealsByCategory> {
                override fun onResponse(
                    call: Call<MealsByCategory>,
                    response: Response<MealsByCategory>,
                ) {
                    response.body()?.let { mealsByCategory ->
                        mealsLiveData.postValue(mealsByCategory.meals)
                    }
                }

                override fun onFailure(call: Call<MealsByCategory>, t: Throwable) {
                    Timber.tag("CategoriesLiveViewModel").e(t.message.toString())
                }
            })
    }

    fun observeMealsLiveData(): MutableLiveData<List<MealsByCategoryList>> {
        return mealsLiveData
    }
}
