package daniel.brian.fooddeliveryapp.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import daniel.brian.fooddeliveryapp.databinding.ActivityCategoryViewsBinding
import daniel.brian.fooddeliveryapp.ui.adapters.CategoryListsAdapter
import daniel.brian.fooddeliveryapp.ui.fragments.HomeFragment
import daniel.brian.fooddeliveryapp.ui.viewmodel.CategoriesListViewModel

class CategoryViewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryViewsBinding
    private lateinit var categoriesListViewModel: CategoriesListViewModel
    private lateinit var categoryListsAdapter: CategoryListsAdapter

    // this will help in passing the meal id and name of the meal that we will click to the next activity
    companion object {
        const val MEAL_ID = "daniel.brian.fooddeliveryapp.fragments.idMeal"
        const val MEAL_NAME = "daniel.brian.fooddeliveryapp.fragments.nameMeal"
        const val MEAL_THUMB = "daniel.brian.fooddeliveryapp.fragments.thumbNail"
        const val CATEGORY_NAME = "daniel.brian.fooddeliveryapp.fragments.categoryName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryViewsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        prepareRecyclerView()
        categoryListItemOnclick()

        categoriesListViewModel = ViewModelProvider(this)[CategoriesListViewModel::class.java]

        categoriesListViewModel.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)

        categoriesListViewModel.observeMealsLiveData().observe(this) { mealsList ->
            categoryListsAdapter.setMealsList(mealsList)
        }
    }

    private fun categoryListItemOnclick() {
        categoryListsAdapter.onItemClick = { meal ->
            val intent = Intent(this, MealCategory::class.java)
            intent.putExtra(MEAL_ID, meal.idMeal)
            intent.putExtra(MEAL_NAME, meal.strMeal)
            intent.putExtra(MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun prepareRecyclerView() {
        categoryListsAdapter = CategoryListsAdapter()

        binding.CategoryViewsHolder.apply {
            layoutManager = GridLayoutManager(context, 4, GridLayoutManager.VERTICAL, false)
            adapter = categoryListsAdapter
        }
    }
}
