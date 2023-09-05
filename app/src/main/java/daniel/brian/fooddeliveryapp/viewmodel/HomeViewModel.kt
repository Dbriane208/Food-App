package daniel.brian.fooddeliveryapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import daniel.brian.fooddeliveryapp.pojo.Category
import daniel.brian.fooddeliveryapp.pojo.CategoryList
import daniel.brian.fooddeliveryapp.pojo.MealsByCategory
import daniel.brian.fooddeliveryapp.pojo.MealsByCategoryList
import daniel.brian.fooddeliveryapp.pojo.Meal
import daniel.brian.fooddeliveryapp.pojo.MealList
import daniel.brian.fooddeliveryapp.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel: ViewModel() {
    private var randomMealLiveData = MutableLiveData<Meal>()
    private var popularItemsLiveData = MutableLiveData<List<MealsByCategoryList>>()
    private var categoryMealsLiveData = MutableLiveData<List<Category>>()

    fun getRandomMeal (){
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null){
                    val randomMeal : Meal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal

                }else{
                    return
                }
            }
            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("HomeFragment",t.message.toString())
            }
        })
    }

    fun getPopularItems(){
        RetrofitInstance.api.getPopularItems("Seafood").enqueue(object : Callback<MealsByCategory>{
            override fun onResponse(call: Call<MealsByCategory>, response: Response<MealsByCategory>) {
                if(response.body() != null){
                   popularItemsLiveData.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<MealsByCategory>, t: Throwable) {
                Log.d("HomeFragment",t.message.toString())
            }

        })
    }

    fun getMealsByCategory(){
        RetrofitInstance.api.getMealsByCategory().enqueue(object : Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                if (response.body() != null){
                   categoryMealsLiveData.value = response.body()!!.categories
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
               Log.e("HomeFragment",t.message.toString())
            }

        })
    }
    fun observeRandomMealLivedata() : LiveData<Meal>{
        return randomMealLiveData
    }
    fun observePopularItemsLiveData(): LiveData<List<MealsByCategoryList>>{
        return popularItemsLiveData
    }
     fun observeCategoryMealsLiveData(): MutableLiveData<List<Category>> {
         return categoryMealsLiveData
     }
}