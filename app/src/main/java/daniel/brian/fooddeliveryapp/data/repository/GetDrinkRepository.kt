package daniel.brian.fooddeliveryapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import daniel.brian.fooddeliveryapp.data.dtos.Drink
import daniel.brian.fooddeliveryapp.data.dtos.OrdinaryDrinks
import daniel.brian.fooddeliveryapp.data.local.db.MealDataBase
import daniel.brian.fooddeliveryapp.data.local.db.CartDatabase
import daniel.brian.fooddeliveryapp.data.remote.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class GetDrinkRepository(
    private var mealDataBase: MealDataBase,
    private var cartDB: CartDatabase
) {
    fun getOrdinaryDrink(): LiveData<Result<List<Drink>>> {
        val ordinaryDrinksLiveData = MutableLiveData<Result<List<Drink>>>()
        RetrofitInstance.drinkApi.getDrinkCategories("Ordinary_Drink").enqueue(object :
            Callback<OrdinaryDrinks> {
            override fun onResponse(
                call: Call<OrdinaryDrinks>,
                response: Response<OrdinaryDrinks>,
            ) {
                val drink = response.body()?.drinks
                drink?.let {
                    ordinaryDrinksLiveData.postValue(Result.Success(it))
                }
            }

            override fun onFailure(call: Call<OrdinaryDrinks>, t: Throwable) {
                Timber.tag("MealCategory").d(t.message.toString())
            }
        })

        return ordinaryDrinksLiveData
    }
}
