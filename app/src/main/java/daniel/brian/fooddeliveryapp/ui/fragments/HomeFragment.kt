package daniel.brian.fooddeliveryapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import daniel.brian.fooddeliveryapp.R
import daniel.brian.fooddeliveryapp.data.dtos.Category
import daniel.brian.fooddeliveryapp.data.dtos.Meal
import daniel.brian.fooddeliveryapp.data.dtos.MealsByCategoryList
import daniel.brian.fooddeliveryapp.data.repository.Result
import daniel.brian.fooddeliveryapp.databinding.FragmentHomeBinding
import daniel.brian.fooddeliveryapp.ui.activities.CategoryViewsActivity
import daniel.brian.fooddeliveryapp.ui.activities.MainActivity
import daniel.brian.fooddeliveryapp.ui.activities.MealCategory
import daniel.brian.fooddeliveryapp.ui.adapters.CategoryMealsAdapter
import daniel.brian.fooddeliveryapp.ui.adapters.PopularMealsAdapter
import daniel.brian.fooddeliveryapp.ui.viewmodel.HomeViewModel

class HomeFragment : Fragment() {
    private lateinit var popularItemsAdapter: PopularMealsAdapter
    private lateinit var categoryMealsAdapter: CategoryMealsAdapter
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeMvvm: HomeViewModel
    private lateinit var randomMeal: Meal

    companion object {
        const val MEAL_ID = "daniel.brian.fooddeliveryapp.fragments.idMeal"
        const val MEAL_NAME = "daniel.brian.fooddeliveryapp.fragments.nameMeal"
        const val MEAL_THUMB = "daniel.brian.fooddeliveryapp.fragments.thumbNail"
        const val CATEGORY_NAME = "daniel.brian.fooddeliveryapp.fragments.categoryName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // casting the viewModel from our main activity
        homeMvvm = (activity as MainActivity).viewModel

        popularItemsAdapter = PopularMealsAdapter()
        categoryMealsAdapter = CategoryMealsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareCategoryMealsRecyclerView()
        preparePopularItemsRecyclerView()

        getMeals()
        onClickRandomMeal()

        getPopularMeals()
        onPopularItemClick()

        getMealsByCategory()
        onCategoryClick()

        onSearchClick()
    }

    private fun onSearchClick() {
        binding.searchHome.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

    private fun onCategoryClick() {
        categoryMealsAdapter.onItemClick = { category ->
            val intent = Intent(activity, CategoryViewsActivity::class.java)
            intent.putExtra(CATEGORY_NAME, category.strCategory)
            startActivity(intent)
        }
    }

    private fun prepareCategoryMealsRecyclerView() {
        binding.categoryView.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryMealsAdapter
        }
    }

    private fun onPopularItemClick() {
        popularItemsAdapter.onItemClick = { meal ->
            val intent = Intent(activity, MealCategory::class.java)
            intent.putExtra(MEAL_ID, meal.idMeal)
            intent.putExtra(MEAL_NAME, meal.strMeal)
            intent.putExtra(MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun preparePopularItemsRecyclerView() {
        binding.popularView.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularItemsAdapter
        }
    }

    private fun onClickRandomMeal() {
        binding.cardPromotion.setOnClickListener {
            val intent = Intent(activity, MealCategory::class.java)
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun getMeals() {
        homeMvvm.getRandomMeal().observe(
            viewLifecycleOwner,
        ) { result ->
            if (homeMvvm.randomSavedState != null) {
                binding.randomMealProgressBar.visibility = ProgressBar.GONE
                Glide.with(this@HomeFragment)
                    .load(homeMvvm.randomSavedState!!.strMealThumb)
                    .into(binding.promotionMeal)
            } else {
                when (result) {
                    is Result.Error -> {
                        // TODO: Show an error
                    }

                    is Result.Loading -> {
                        binding.randomMealProgressBar.visibility = ProgressBar.VISIBLE
                    }

                    is Result.Success -> {
                        result.data?.let { homeMvvm.storeRandomMeal(it) }
                        binding.randomMealProgressBar.visibility = ProgressBar.GONE
                        Glide.with(this@HomeFragment)
                            .load(result.data?.strMealThumb)
                            .into(binding.promotionMeal)

                        result.data?.let {
                            this.randomMeal = result.data
                        }
                    }
                }
            }
        }
    }

    private fun getPopularMeals() {
        homeMvvm.getPopularItems().observe(
            viewLifecycleOwner,
        ) { result ->
            when (result) {
                is Result.Error -> Unit
                is Result.Loading -> Unit
                is Result.Success -> {
                    popularItemsAdapter.setMeals(mealsList = result.data as ArrayList<MealsByCategoryList>)
                }
            }
        }
    }

    private fun getMealsByCategory() {
        homeMvvm.getMealsByCategory().observe(
            viewLifecycleOwner,
        ) { result ->
            when (result) {
                is Result.Error -> Unit
                is Result.Loading -> {
                    binding.randomMealProgressBar.visibility = ProgressBar.VISIBLE
                }

                is Result.Success -> {
                    categoryMealsAdapter.setCategoryMeals(categoryMealsList = result.data as ArrayList<Category>)
                }
            }
        }
    }
}
