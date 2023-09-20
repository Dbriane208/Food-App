package daniel.brian.fooddeliveryapp.retrofit

import daniel.brian.fooddeliveryapp.util.Constants.DRINKS_BASE_URL
import daniel.brian.fooddeliveryapp.util.Constants.MEAL_BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val mealApi : MealApi by lazy {
        Retrofit.Builder()
            .baseUrl(MEAL_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MealApi::class.java)
    }

    val drinkApi : MealApi by lazy {
        Retrofit.Builder()
            .baseUrl(DRINKS_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MealApi::class.java)
    }


}