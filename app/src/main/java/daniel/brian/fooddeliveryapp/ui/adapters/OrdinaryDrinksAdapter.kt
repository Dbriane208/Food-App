package daniel.brian.fooddeliveryapp.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import daniel.brian.fooddeliveryapp.databinding.OrdinaryDrinksBinding
import daniel.brian.fooddeliveryapp.data.dtos.Drink

class OrdinaryDrinksAdapter :
    RecyclerView.Adapter<OrdinaryDrinksAdapter.OrdinaryDrinksViewHolder>() {
    private var drinksList: ArrayList<Drink> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    fun setOrdinaryDrinks(drinksList: ArrayList<Drink>) {
        this.drinksList = drinksList
        notifyDataSetChanged()
    }

    inner class OrdinaryDrinksViewHolder(val binding: OrdinaryDrinksBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdinaryDrinksViewHolder {
        return OrdinaryDrinksViewHolder(
            OrdinaryDrinksBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )
    }

    override fun getItemCount(): Int {
        return drinksList.size
    }

    override fun onBindViewHolder(holder: OrdinaryDrinksViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(drinksList[position].strDrinkThumb)
            .into(holder.binding.ordinaryDrinks)
    }
}
