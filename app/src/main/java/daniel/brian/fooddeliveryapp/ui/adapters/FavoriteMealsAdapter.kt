package daniel.brian.fooddeliveryapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import daniel.brian.fooddeliveryapp.data.dtos.Meal
import daniel.brian.fooddeliveryapp.databinding.FavoritesCategoryViewsBinding
import daniel.brian.fooddeliveryapp.util.shortenName

class FavoriteMealsAdapter :
    RecyclerView.Adapter<FavoriteMealsAdapter.FavoriteMealsAdapterViewHolder>() {
    lateinit var onFavoriteClick: ((Meal) -> Unit)

    inner class FavoriteMealsAdapterViewHolder(val binding: FavoritesCategoryViewsBinding) :
        RecyclerView.ViewHolder(binding.root)

    // DiffUtil is an instance of an anonymous object
    // DiffUtil.ItemCallback is used for calculating the difference between two non-null items
    private val diffUtil = object : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }
    }

    // Used to compute differences between lists on a background thread
    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): FavoriteMealsAdapterViewHolder {
        return FavoriteMealsAdapterViewHolder(
            FavoritesCategoryViewsBinding.inflate(
                LayoutInflater.from(
                    parent.context,
                ),
                parent,
                false,
            ),
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: FavoriteMealsAdapterViewHolder, position: Int) {
        val meal = differ.currentList[position]
        Glide.with(holder.itemView)
            .load(meal.strMealThumb)
            .into(holder.binding.CategoryMeals)
        holder.binding.tvCategoryMeal.text = meal.strMeal?.shortenName()
        holder.itemView.setOnClickListener {
            onFavoriteClick.invoke(meal)
        }
    }
}
