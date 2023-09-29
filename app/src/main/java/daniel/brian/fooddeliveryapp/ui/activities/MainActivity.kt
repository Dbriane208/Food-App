package daniel.brian.fooddeliveryapp.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import daniel.brian.fooddeliveryapp.R
import daniel.brian.fooddeliveryapp.data.local.db.MealDataBase
import daniel.brian.fooddeliveryapp.data.repository.GetMealsRepository
import daniel.brian.fooddeliveryapp.databinding.ActivityMainBinding
import daniel.brian.fooddeliveryapp.ui.viewmodel.HomeViewModel
import daniel.brian.fooddeliveryapp.ui.viewmodel.HomeViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    // We instantiate it from the main activity so that we can avoid having three instances of our HomeViewModel
    // By lazy ensures that it will be created the first time is initialized and cached subsequently

    val viewModel: HomeViewModel by lazy {
        val mealDataBase = MealDataBase.getInstance(this)
        val repository = GetMealsRepository(mealDataBase)
        val homeViewModelFactory = HomeViewModelFactory(repository)
        ViewModelProvider(this, homeViewModelFactory)[HomeViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val bottomNavigationView = binding.bottomNavigation
        val navController = Navigation.findNavController(this, R.id.navHostFragment)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
    }
}
