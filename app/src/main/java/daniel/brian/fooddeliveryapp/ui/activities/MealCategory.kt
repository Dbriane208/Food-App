@file:Suppress("DEPRECATION")

package daniel.brian.fooddeliveryapp.ui.activities

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import daniel.brian.fooddeliveryapp.data.dtos.Drink
import daniel.brian.fooddeliveryapp.data.dtos.Meal
import daniel.brian.fooddeliveryapp.data.local.db.MealDataBase
import daniel.brian.fooddeliveryapp.data.local.db.CartDatabase
import daniel.brian.fooddeliveryapp.data.repository.GetDrinkRepository
import daniel.brian.fooddeliveryapp.data.repository.GetMealDetailsRepository
import daniel.brian.fooddeliveryapp.data.repository.GetMealsRepository
import daniel.brian.fooddeliveryapp.data.repository.Result
import daniel.brian.fooddeliveryapp.databinding.ActivityMealCategoryBinding
import daniel.brian.fooddeliveryapp.ui.adapters.OrdinaryDrinksAdapter
import daniel.brian.fooddeliveryapp.ui.fragments.HomeFragment
import daniel.brian.fooddeliveryapp.ui.viewmodel.DrinksViewModel
import daniel.brian.fooddeliveryapp.ui.viewmodel.DrinksViewModelFactory
import daniel.brian.fooddeliveryapp.ui.viewmodel.MealDetailsViewModel
import daniel.brian.fooddeliveryapp.ui.viewmodel.MealDetailsViewModelFactory
import daniel.brian.fooddeliveryapp.util.shortenName

class MealCategory : AppCompatActivity() {
    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var drinkThumb: String
    private lateinit var binding: ActivityMealCategoryBinding
    private lateinit var drinksMvvm: DrinksViewModel
    private lateinit var mealMvvm: MealDetailsViewModel
    private var mealToSave: Meal? = null
    private lateinit var ordinaryDrinksAdapter: OrdinaryDrinksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT
        binding = ActivityMealCategoryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val mealDataBase = MealDataBase.getInstance(this)
        val cartDatabase = CartDatabase.getInstance(this)
        val repository = GetMealsRepository(mealDataBase,cartDatabase)
        val detailsRepo = GetMealDetailsRepository(mealDataBase,cartDatabase)
        val mealDetailsViewModelFactory = MealDetailsViewModelFactory(repository, detailsRepo)

        getMealInformation()

        mealMvvm =
            ViewModelProvider(this, mealDetailsViewModelFactory)[MealDetailsViewModel::class.java]

        getMealDetails()
        onClickFavoriteMeal()
        addItemsToCart()

        val drinksRepository = GetDrinkRepository(mealDataBase,cartDatabase)
        val drinkViewModelFactory = DrinksViewModelFactory(drinksRepository)

        drinksMvvm = ViewModelProvider(this, drinkViewModelFactory)[DrinksViewModel::class.java]
        ordinaryDrinksAdapter = OrdinaryDrinksAdapter()
        getOrdinaryDrink()

        prepareAddsOnRecyclerView()

        setInformationInViews()

        binding.backButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun addItemsToCart() {
        binding.addToCart.setOnClickListener{
            mealToSave?.let {
                mealMvvm.addItemToCart(it)
                Toast.makeText(this,"Meal Added to Cart Successfully",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getMealDetails() {
        mealMvvm.getMealDetails(mealId).observe(this) { mealDetails ->
            when (mealDetails) {
                is Result.Error -> Unit
                is Result.Loading -> Unit
                is Result.Success -> {
                    mealToSave = mealDetails.data
                    val image = mealToSave!!.strMealThumb
                    Glide.with(this)
                        .load(image)
                        .into(binding.imageMeal)
                }
            }
        }
    }

    private fun getOrdinaryDrink() {
        drinksMvvm.getDrinksCategories().observe(
            this,
        ) { drink ->
            when (drink) {
                is Result.Error -> Unit
                is Result.Loading -> Unit
                is Result.Success -> {
                    ordinaryDrinksAdapter.setOrdinaryDrinks(drinksList = drink.data as ArrayList<Drink>)
                }
            }
        }
    }

    private fun onClickFavoriteMeal() {
        binding.favoriteMeal.setOnClickListener {
            binding.favoriteMeal.setColorFilter(Color.parseColor("#df3079"))
            mealToSave?.let {
                mealMvvm.insertMeal(it)
                Toast.makeText(this, "Meal saved to favorites", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun prepareAddsOnRecyclerView() {
        binding.addsOnRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(this@MealCategory, LinearLayoutManager.HORIZONTAL, false)
            adapter = ordinaryDrinksAdapter
        }
    }

    private fun setInformationInViews() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imageMeal)

        binding.mealName.text = mealName.shortenName()
    }

    private fun getMealInformation() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!.shortenName()
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }
}
