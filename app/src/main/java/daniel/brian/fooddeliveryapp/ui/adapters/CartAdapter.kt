package daniel.brian.fooddeliveryapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import daniel.brian.fooddeliveryapp.data.dtos.Meal
import daniel.brian.fooddeliveryapp.databinding.CartLayoutBinding
import daniel.brian.fooddeliveryapp.util.shortenName

class CartAdapter : RecyclerView.Adapter<CartAdapter.CartAdapterViewHolder>() {

    // connects the adapter to the fragment add to cart to access the views
    inner class CartAdapterViewHolder(val binding: CartLayoutBinding): RecyclerView.ViewHolder(binding.root)

    // updating the lists by initializing an anonymous object that implements the DiffUtil.ItemCallback<Meal>
    // The object checks if the items are the same("areItemsTheSame") and their contents are the same("areContentsTheSame") for efficient list update
    private val diffUtil = object : DiffUtil.ItemCallback<Meal>(){
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }
    }
    // the differ is used to compute the differences in the list asynchronously
    // it's used to update the recyclerview efficiently when the item changes
    val differ = AsyncListDiffer(this,diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapterViewHolder {
        return CartAdapterViewHolder(CartLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CartAdapterViewHolder, position: Int) {
        val meal = differ.currentList[position]
        Glide.with(holder.itemView)
            .load(meal.strMealThumb)
            .into(holder.binding.foodImage)
        holder.binding.foodName.text = meal.strMeal?.shortenName()
    }
}