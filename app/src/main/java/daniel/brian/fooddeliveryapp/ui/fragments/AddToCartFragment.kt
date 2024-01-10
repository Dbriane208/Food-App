package daniel.brian.fooddeliveryapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import daniel.brian.fooddeliveryapp.databinding.FragmentAddToCartBinding
import daniel.brian.fooddeliveryapp.ui.activities.MainActivity
import daniel.brian.fooddeliveryapp.ui.adapters.CartAdapter
import daniel.brian.fooddeliveryapp.ui.viewmodel.HomeViewModel

class AddToCartFragment : Fragment() {
     private lateinit var addToCartBinding : FragmentAddToCartBinding
     private lateinit var homeMvvm: HomeViewModel
     private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm = (activity as MainActivity).viewModel
        cartAdapter = CartAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        addToCartBinding = FragmentAddToCartBinding.inflate(inflater)
        return addToCartBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // preparing the recyclerview
        prepareRecyclerView()
        // observing the cart products item
        observeCartProductsLiveData()

        // implementing on move to delete item from the database
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

                homeMvvm.deleteMeal(cartAdapter.differ.currentList[position])
                Snackbar.make(requireView(), "Meal deleted", Snackbar.LENGTH_LONG).show()
            }
        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(addToCartBinding.cartItemsRV)
    }

    private fun observeCartProductsLiveData() {
        homeMvvm.observeFavoritesMealsLiveData().observe(viewLifecycleOwner){ cart ->
            cartAdapter.differ.submitList(cart)
        }
    }

    private fun prepareRecyclerView() {
        addToCartBinding.cartItemsRV.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            adapter = cartAdapter
        }
    }

}