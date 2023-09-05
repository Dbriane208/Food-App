package daniel.brian.fooddeliveryapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import daniel.brian.fooddeliveryapp.databinding.ActivityMealCategoryBinding

class CategoryMealsAdapter() : RecyclerView.Adapter<CategoryMealsAdapter.CategoryMealsViewHolder>() {


    inner class CategoryMealsViewHolder(val binding: ActivityMealCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealsViewHolder {
        return CategoryMealsViewHolder(ActivityMealCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: CategoryMealsViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}