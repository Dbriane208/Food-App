package daniel.brian.fooddeliveryapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import daniel.brian.fooddeliveryapp.activities.MainActivity
import daniel.brian.fooddeliveryapp.activities.MealCategory
import daniel.brian.fooddeliveryapp.adapters.FavoriteMealsAdapter
import daniel.brian.fooddeliveryapp.databinding.FragmentSearchBinding
import daniel.brian.fooddeliveryapp.viewmodel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var homeMvvm: HomeViewModel
    private lateinit var searchMealAdapter: FavoriteMealsAdapter

    companion object {
        const val MEAL_ID = "daniel.brian.fooddeliveryapp.fragments.idMeal"
        const val MEAL_NAME = "daniel.brian.fooddeliveryapp.fragments.nameMeal"
        const val MEAL_THUMB = "daniel.brian.fooddeliveryapp.fragments.thumbNail"
        const val CATEGORY_NAME = "daniel.brian.fooddeliveryapp.fragments.categoryName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        onFavoriteClick()

        binding.btnSearchFrag.setOnClickListener {
            searchMeals()
        }

        observeSearchMealsLiveData()

        var searchJobs: Job? = null
        binding.editSearchViewFrag.addTextChangedListener { searchQuery ->
            searchJobs?.cancel()
            searchJobs = lifecycleScope.launch {
                delay(500)
                homeMvvm.searchMeal(searchQuery.toString())
            }
        }
    }

    private fun onFavoriteClick() {
        searchMealAdapter.onFavoriteClick = { meal ->
            val intent = Intent(activity, MealCategory::class.java)
            intent.putExtra(FavoritesFragment.MEAL_ID, meal.idMeal)
            intent.putExtra(FavoritesFragment.MEAL_NAME, meal.strMeal)
            intent.putExtra(FavoritesFragment.MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observeSearchMealsLiveData() {
        homeMvvm.observeSearchedMealLiveData().observe(
            viewLifecycleOwner,
        ) { mealList ->
            searchMealAdapter.differ.submitList(mealList)
        }
    }

    private fun searchMeals() {
        val search = binding.editSearchViewFrag.text.toString()
        if (search.isNotEmpty()) {
            homeMvvm.searchMeal(search)
        }
    }

    private fun prepareRecyclerView() {
        searchMealAdapter = FavoriteMealsAdapter()
        binding.searchRV.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = searchMealAdapter
        }
    }
}
