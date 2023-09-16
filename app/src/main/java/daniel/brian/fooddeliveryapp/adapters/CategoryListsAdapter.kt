package daniel.brian.fooddeliveryapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import daniel.brian.fooddeliveryapp.databinding.CategoryViewsBinding
import daniel.brian.fooddeliveryapp.pojo.MealsByCategoryList

class CategoryListsAdapter : RecyclerView.Adapter<CategoryListsAdapter.CategoryListViewHolder>() {
     private var mealList = ArrayList<MealsByCategoryList>()

    @SuppressLint("NotifyDataSetChanged")
    fun setMealsList(mealList : List<MealsByCategoryList>){
        this.mealList = mealList as ArrayList<MealsByCategoryList>
        notifyDataSetChanged()
    }

    inner class CategoryListViewHolder(val binding : CategoryViewsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryListViewHolder {
        return CategoryListViewHolder(CategoryViewsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
       return mealList.size
    }

    override fun onBindViewHolder(holder: CategoryListViewHolder, position: Int) {
        Glide.with(holder.itemView).load(mealList[position].strMealThumb).into(holder.binding.CategoryMeals)
        holder.binding.tvCategoryMeal.text = mealList[position].strMeal
    }


}