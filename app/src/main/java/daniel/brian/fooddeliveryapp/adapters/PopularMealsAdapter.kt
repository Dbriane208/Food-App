@file:Suppress("ClassName")

package daniel.brian.fooddeliveryapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import daniel.brian.fooddeliveryapp.databinding.PopularMealsViewBinding
import daniel.brian.fooddeliveryapp.pojo.MealsByCategoryList


class PopularMealsAdapter : RecyclerView.Adapter<PopularMealsAdapter.popularMealViewHolder>() {
    private var mealsList : ArrayList<MealsByCategoryList> = ArrayList()
    lateinit var onItemClick : ((MealsByCategoryList)-> Unit)

    @SuppressLint("NotifyDataSetChanged")
    fun setMeals(mealsList:ArrayList<MealsByCategoryList>){
        this.mealsList = mealsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): popularMealViewHolder {
        return popularMealViewHolder(PopularMealsViewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return mealsList.size

    }

    override fun onBindViewHolder(holder: popularMealViewHolder, position: Int) {
         Glide.with(holder.itemView)
             .load(mealsList[position].strMealThumb)
             .into(holder.binding.popularMeal)

         val meal = mealsList[position]
         holder.binding.popularMealName.text = meal.strMeal

         holder.itemView.setOnClickListener{
           onItemClick.invoke(mealsList[position])
         }
    }

    class popularMealViewHolder( var binding : PopularMealsViewBinding) : RecyclerView.ViewHolder(binding.root)
}