package daniel.brian.fooddeliveryapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import daniel.brian.fooddeliveryapp.activities.MainActivity
import daniel.brian.fooddeliveryapp.adapters.FavoriteMealsAdapter
import daniel.brian.fooddeliveryapp.databinding.FragmentFavoritesBinding
import daniel.brian.fooddeliveryapp.viewmodel.HomeViewModel

class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var homeMvvm: HomeViewModel
    private lateinit var favoriteMealsAdapter: FavoriteMealsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm = (activity as MainActivity).viewModel
        favoriteMealsAdapter = FavoriteMealsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeFavoritesMeals()
        prepareRecyclerView()

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT,
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

                homeMvvm.deleteMeal(favoriteMealsAdapter.differ.currentList[position])
                Snackbar.make(requireView(), "Meal deleted", Snackbar.LENGTH_LONG).show()
            }
        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.mealFavoritesRV)
    }

    private fun prepareRecyclerView() {
        binding.mealFavoritesRV.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = favoriteMealsAdapter
        }
    }

    private fun observeFavoritesMeals() {
        homeMvvm.observeFavoritesMealsLiveData().observe(viewLifecycleOwner) { meals ->
            favoriteMealsAdapter.differ.submitList(meals)
        }
    }
}
