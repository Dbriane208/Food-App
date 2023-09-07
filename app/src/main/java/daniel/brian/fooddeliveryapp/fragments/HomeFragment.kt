package daniel.brian.fooddeliveryapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import daniel.brian.fooddeliveryapp.activities.CategoryViewsActivity
import daniel.brian.fooddeliveryapp.activities.MealCategory
import daniel.brian.fooddeliveryapp.adapters.CategoryMealsAdapter
import daniel.brian.fooddeliveryapp.adapters.PopularMealsAdapter
import daniel.brian.fooddeliveryapp.databinding.FragmentHomeBinding
import daniel.brian.fooddeliveryapp.pojo.Category
import daniel.brian.fooddeliveryapp.pojo.MealsByCategoryList
import daniel.brian.fooddeliveryapp.pojo.Meal
import daniel.brian.fooddeliveryapp.viewmodel.HomeViewModel


class HomeFragment : Fragment() {
    private lateinit var popularItemsAdapter : PopularMealsAdapter
    private lateinit var categoryMealsAdapter : CategoryMealsAdapter
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeMvvm : HomeViewModel
    private lateinit var randomMeal : Meal

    companion object{
     const val MEAL_ID = "daniel.brian.fooddeliveryapp.fragments.idMeal"
     const val MEAL_NAME = "daniel.brian.fooddeliveryapp.fragments.nameMeal"
     const val MEAL_THUMB = "daniel.brian.fooddeliveryapp.fragments.thumbNail"
     const val CATEGORY_NAME = "daniel.brian.fooddeliveryapp.fragments.categoryName"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      homeMvvm = ViewModelProvider(this)[HomeViewModel::class.java]

      popularItemsAdapter = PopularMealsAdapter()
      categoryMealsAdapter = CategoryMealsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareCategoryMealsRecyclerView()
        preparePopularItemsRecyclerView()

        homeMvvm.getRandomMeal()
        observerRandomMeal()
        onClickRandomMeal()

        homeMvvm.getPopularItems()
        observePopularItemsLiveData()
        onPopularItemClick()

        homeMvvm.getMealsByCategory()
        observeCategoryMealsLiveData()
        onCategoryClick()
    }

    private fun onCategoryClick() {
        categoryMealsAdapter.onItemClick = { category ->
            val intent = Intent(activity,CategoryViewsActivity::class.java)
            intent.putExtra(CATEGORY_NAME,category.strCategory)
            startActivity(intent)
        }
    }

    private fun prepareCategoryMealsRecyclerView() {
        binding.categoryView.apply {
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter = categoryMealsAdapter
        }
    }

    private fun observeCategoryMealsLiveData() {
        homeMvvm.observeCategoryMealsLiveData().observe(viewLifecycleOwner){
            categoryMealsList ->
            categoryMealsAdapter.setCategoryMeals(categoryMealsList = categoryMealsList as ArrayList<Category>)
        }
    }

    private fun onPopularItemClick() {
        popularItemsAdapter.onItemClick = { meal ->
            val intent = Intent(activity,MealCategory::class.java)
            intent.putExtra(MEAL_ID,meal.idMeal)
            intent.putExtra(MEAL_NAME,meal.strMeal)
            intent.putExtra(MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun preparePopularItemsRecyclerView() {
      binding.popularView.apply {
          layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
          adapter = popularItemsAdapter
      }
    }

    private fun observePopularItemsLiveData() {
        homeMvvm.observePopularItemsLiveData().observe(viewLifecycleOwner
        ) { mealList ->
            popularItemsAdapter.setMeals(mealsList = mealList as ArrayList<MealsByCategoryList>)
        }
    }

    private fun onClickRandomMeal() {
        binding.cardPromotion.setOnClickListener{
            val intent = Intent(activity,MealCategory::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observerRandomMeal() {
        homeMvvm.observeRandomMealLivedata().observe(viewLifecycleOwner
        ) { value ->
            Glide.with(this@HomeFragment)
                .load(value.strMealThumb)
                .into(binding.promotionMeal)

            this.randomMeal = value
        }
    }

}