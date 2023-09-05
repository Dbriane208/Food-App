package daniel.brian.fooddeliveryapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import daniel.brian.fooddeliveryapp.databinding.CategoryrecyclervieiwBinding
import daniel.brian.fooddeliveryapp.pojo.Category

class CategoryMealsAdapter : RecyclerView.Adapter<CategoryMealsAdapter.CategoryMealsViewHolder>() {
     private var categoryMealsList : ArrayList<Category> = ArrayList()

     @SuppressLint("NotifyDataSetChanged")
     fun setCategoryMeals(categoryMealsList : ArrayList<Category>){
         this.categoryMealsList = categoryMealsList
         notifyDataSetChanged()
     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealsViewHolder {
        return CategoryMealsViewHolder(CategoryrecyclervieiwBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return categoryMealsList.size
    }

    override fun onBindViewHolder(holder: CategoryMealsViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(categoryMealsList[position].strCategoryThumb)
            .into(holder.binding.imgCategoryMeal)

        val meal = categoryMealsList[position]
        holder.binding.tvCategoryMeal.text = meal.strCategory
    }
    inner class CategoryMealsViewHolder(val binding: CategoryrecyclervieiwBinding) : RecyclerView.ViewHolder(binding.root)
}