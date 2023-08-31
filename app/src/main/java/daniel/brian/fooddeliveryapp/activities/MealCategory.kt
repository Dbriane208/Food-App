@file:Suppress("DEPRECATION")

package daniel.brian.fooddeliveryapp.activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import daniel.brian.fooddeliveryapp.databinding.ActivityMealCategoryBinding
import daniel.brian.fooddeliveryapp.fragments.HomeFragment

class MealCategory : AppCompatActivity() {
    private lateinit var mealId : String
    private lateinit var mealName : String
    private lateinit var mealThumb : String
    private lateinit var binding : ActivityMealCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT
        binding = ActivityMealCategoryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        getMealInformation()
        setInformationInViews()
    }

    private fun setInformationInViews() {
         Glide.with(applicationContext)
             .load(mealThumb)
             .into(binding.imageMeal)

         binding.mealName.text = mealName

    }

    private fun getMealInformation() {
         val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

    fun onClickBack(view: View) {
        binding.backButton.setOnClickListener {
            val intent = Intent(this,HomeFragment::class.java)
            startActivity(intent)
        }
    }

}