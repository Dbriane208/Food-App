package daniel.brian.fooddeliveryapp.application

import android.app.Application
import timber.log.Timber

class FoodDeliveryApplication:Application(){

    override fun onCreate() {
        super.onCreate()
        initTimber()
    }

    private fun initTimber(){
        Timber.plant(Timber.DebugTree())
    }
}