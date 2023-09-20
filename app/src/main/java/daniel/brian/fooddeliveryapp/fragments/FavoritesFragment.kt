package daniel.brian.fooddeliveryapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import daniel.brian.fooddeliveryapp.activities.MainActivity
import daniel.brian.fooddeliveryapp.adapters.FavoriteMealsAdapter
import daniel.brian.fooddeliveryapp.databinding.FragmentFavoritesBinding
import daniel.brian.fooddeliveryapp.viewmodel.HomeViewModel


class FavoritesFragment : Fragment() {
  private lateinit var binding: FragmentFavoritesBinding
  private lateinit var homeMvvm : HomeViewModel
  private lateinit var favoriteMealsAdapter : FavoriteMealsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

         homeMvvm = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeFavoritesMeals()
        prepareRecyclerView()
    }

    private fun prepareRecyclerView() {
        favoriteMealsAdapter = FavoriteMealsAdapter()
        binding.mealFavoritesRV.apply {
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter = favoriteMealsAdapter
        }
    }

    private fun observeFavoritesMeals() {
        homeMvvm.observeFavoritesMealsLiveData().observe(viewLifecycleOwner) { meals ->
            favoriteMealsAdapter.differ.submitList(meals)
        }
    }

}