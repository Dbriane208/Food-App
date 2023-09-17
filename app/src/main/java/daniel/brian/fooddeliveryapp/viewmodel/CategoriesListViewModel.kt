package daniel.brian.fooddeliveryapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import daniel.brian.fooddeliveryapp.pojo.MealsByCategory
import daniel.brian.fooddeliveryapp.pojo.MealsByCategoryList
import daniel.brian.fooddeliveryapp.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesListViewModel : ViewModel() {
    val mealsLiveData = MutableLiveData<List<MealsByCategoryList>>()

    fun getMealsByCategory(categoryName : String){
        RetrofitInstance.mealApi.getMealsByCategory(categoryName).enqueue(object : Callback<MealsByCategory>{
            override fun onResponse(
                call: Call<MealsByCategory>,
                response: Response<MealsByCategory>
            ) {
                response.body()?.let { mealsByCategory ->
                    mealsLiveData.postValue(mealsByCategory.meals)
                }
            }

            override fun onFailure(call: Call<MealsByCategory>, t: Throwable) {
                Log.e("CategoriesLiveViewModel",t.message.toString())
            }

        })
    }

    fun observeMealsLiveData (): MutableLiveData<List<MealsByCategoryList>> {
        return mealsLiveData
    }
}