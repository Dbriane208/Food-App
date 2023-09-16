package daniel.brian.fooddeliveryapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import daniel.brian.fooddeliveryapp.adapters.CategoryListsAdapter
import daniel.brian.fooddeliveryapp.databinding.ActivityCategoryViewsBinding
import daniel.brian.fooddeliveryapp.fragments.HomeFragment
import daniel.brian.fooddeliveryapp.viewmodel.CategoriesListViewModel

class CategoryViewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryViewsBinding
    private lateinit var categoriesListViewModel: CategoriesListViewModel
    private lateinit var categoryListsAdapter: CategoryListsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryViewsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        prepareRecyclerView()

        categoriesListViewModel = ViewModelProvider(this)[CategoriesListViewModel::class.java]

        categoriesListViewModel.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)

        categoriesListViewModel.observeMealsLiveData().observe(this) { mealsList ->
                categoryListsAdapter.setMealsList(mealsList)
        }
    }

    private fun prepareRecyclerView() {
        categoryListsAdapter = CategoryListsAdapter()

        binding.CategoryViewsHolder.apply {
            layoutManager = GridLayoutManager(context,4,GridLayoutManager.VERTICAL,false)
            adapter = categoryListsAdapter
        }
    }
}