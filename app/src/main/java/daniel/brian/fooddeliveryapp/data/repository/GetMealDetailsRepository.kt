package daniel.brian.fooddeliveryapp.data.repository

import androidx.lifecycle.MutableLiveData
import daniel.brian.fooddeliveryapp.data.dtos.Meal
import daniel.brian.fooddeliveryapp.data.dtos.MealList
import daniel.brian.fooddeliveryapp.data.local.db.MealDataBase
import daniel.brian.fooddeliveryapp.data.local.db.CartDatabase
import daniel.brian.fooddeliveryapp.data.remote.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class GetMealDetailsRepository(
    private var mealDataBase: MealDataBase,
    private var cartDB: CartDatabase
) {
    fun getMealDetails(id: String): MutableLiveData<Result<Meal>> {
        val mealDetailsLiveData = MutableLiveData<Result<Meal>>()
        RetrofitInstance.mealApi.getMealDetails(id).enqueue(object : Callback<MealList> {
            override fun onResponse(
                call: Call<MealList>,
                response: Response<MealList>,
            ) {
                val mealDetails = response.body()!!.meals[0]
                mealDetailsLiveData.postValue(Result.Success(mealDetails))
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Timber.d("MealDetails", t.message.toString())
            }
        })
        return mealDetailsLiveData
    }
}
