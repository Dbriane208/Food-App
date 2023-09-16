package daniel.brian.fooddeliveryapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import daniel.brian.fooddeliveryapp.pojo.Drink
import daniel.brian.fooddeliveryapp.pojo.OrdinaryDrinks
import daniel.brian.fooddeliveryapp.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DrinksViewModel : ViewModel() {
    private var ordinaryDrinksLiveData = MutableLiveData<List<Drink>>()

    fun getDrinksCategories(){
        RetrofitInstance.api.getDrinkCategories("Ordinary_Drink").enqueue(object :
            Callback<OrdinaryDrinks>{
            override fun onResponse(call: Call<OrdinaryDrinks>, response: Response<OrdinaryDrinks>) {
                if(response.body() != null){
                    ordinaryDrinksLiveData.value = response.body()!!.drinks
                }else{
                    return
                }
            }

            override fun onFailure(call: Call<OrdinaryDrinks>, t: Throwable) {
                Log.d("MealCategory",t.message.toString())
            }

        })
    }

    fun observeOrdinaryDrinksLiveData (): MutableLiveData<List<Drink>> {
        return ordinaryDrinksLiveData
    }
}