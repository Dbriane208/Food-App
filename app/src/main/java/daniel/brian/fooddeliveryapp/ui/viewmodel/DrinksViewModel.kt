package daniel.brian.fooddeliveryapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import daniel.brian.fooddeliveryapp.data.dtos.Drink
import daniel.brian.fooddeliveryapp.data.dtos.OrdinaryDrinks
import daniel.brian.fooddeliveryapp.data.remote.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class DrinksViewModel : ViewModel() {
    private var ordinaryDrinksLiveData = MutableLiveData<List<Drink>>()

    fun getDrinksCategories() {
        RetrofitInstance.drinkApi.getDrinkCategories("Ordinary_Drink").enqueue(object :
            Callback<OrdinaryDrinks> {
            override fun onResponse(
                call: Call<OrdinaryDrinks>,
                response: Response<OrdinaryDrinks>,
            ) {
                if (response.body() != null) {
                    ordinaryDrinksLiveData.value = response.body()!!.drinks
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<OrdinaryDrinks>, t: Throwable) {
                Timber.tag("MealCategory").d(t.message.toString())
            }
        })
    }

    fun observeOrdinaryDrinksLiveData(): MutableLiveData<List<Drink>> {
        return ordinaryDrinksLiveData
    }
}
