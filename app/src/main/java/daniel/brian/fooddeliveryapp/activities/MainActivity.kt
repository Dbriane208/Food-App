@file:Suppress("DEPRECATION")

package daniel.brian.fooddeliveryapp.activities

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import daniel.brian.fooddeliveryapp.R
import daniel.brian.fooddeliveryapp.databinding.ActivityMainBinding
import daniel.brian.fooddeliveryapp.db.MealDataBase
import daniel.brian.fooddeliveryapp.viewmodel.HomeViewModel
import daniel.brian.fooddeliveryapp.viewmodel.HomeViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    // We instantiate it from the main activity so that we can avoid having three instances of our HomeViewModel
    // By lazy ensures that it will be created the first time is initialized and cached subsequently

    val viewModel: HomeViewModel by lazy {
        val mealDataBase = MealDataBase.getInstance(this)
        val homeViewModelFactory = HomeViewModelFactory(mealDataBase)
        ViewModelProvider(this, homeViewModelFactory)[HomeViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val bottomNavigationView = binding.bottomNavigation
        val navController = Navigation.findNavController(this, R.id.navHostFragment)

        NavigationUI.setupWithNavController(bottomNavigationView, navController)
    }
}
