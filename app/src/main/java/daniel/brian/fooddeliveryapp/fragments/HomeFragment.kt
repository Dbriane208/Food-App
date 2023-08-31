package daniel.brian.fooddeliveryapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import daniel.brian.fooddeliveryapp.activities.MealCategory
import daniel.brian.fooddeliveryapp.databinding.FragmentHomeBinding
import daniel.brian.fooddeliveryapp.pojo.Meal

import daniel.brian.fooddeliveryapp.viewmodel.HomeViewModel


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeMvvm : HomeViewModel
    private lateinit var randomMeal : Meal

    companion object{
     const val MEAL_ID = "daniel.brian.fooddeliveryapp.fragments.idMeal"
     const val MEAL_NAME = "daniel.brian.fooddeliveryapp.fragments.nameMeal"
     const val MEAL_THUMB = "daniel.brian.fooddeliveryapp.fragments.thumbNail"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      homeMvvm = ViewModelProvider(this)[HomeViewModel::class.java]
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

        homeMvvm.getRandomMeal()
        observerRandomMeal()
        onClickRandomMeal()
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