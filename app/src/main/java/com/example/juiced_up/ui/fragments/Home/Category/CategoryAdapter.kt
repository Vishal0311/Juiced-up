package com.example.juiced_up.ui.fragments.Home.Category

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.juiced_up.R
import com.example.juiced_up.api.model.Category.Drink
import com.example.juiced_up.utils.Constants.Companion.TITLE
import java.util.*

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    private var myList = emptyList<Drink>()
    private lateinit var context: Context

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val preview: ImageView = itemView.findViewById(R.id.preview_iv)
        val title: TextView? = itemView.findViewById(R.id.title_tv)

        init {
            itemView.setOnClickListener{
                val bundle = Bundle()
                bundle.apply {
                    putString(TITLE, myList[adapterPosition].strCategory)
                }
                it.findNavController().navigate(R.id.action_homeFragment_to_categoryFragment,bundle)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        setAnimation(holder.itemView, position)

        holder.title?.text = myList[position].strCategory

        val options: RequestOptions = RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.loader)
            .error(R.drawable.sample_small_image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH)
            .dontAnimate()
            .dontTransform()

        if (myList[position].strCategory.contains("Ordinary Drink")) {
            Glide.with(context)
                .load(R.drawable.ordinary_drink_preview)
                .apply(options)
                .into(holder.preview)
        } else if (myList[position].strCategory.contains("Cocktail")) {
            Glide.with(context)
                .load(R.drawable.cocktail_preview)
                .apply(options)
                .into(holder.preview)
        } else if (myList[position].strCategory.contains("Milk / Float / Shake")) {
            Glide.with(context)
                .load(R.drawable.milk_float_preview)
                .apply(options)
                .into(holder.preview)
        } else if (myList[position].strCategory.contains("Other/Unknown")) {
            Glide.with(context)
                .load(R.drawable.other_preview)
                .apply(options)
                .into(holder.preview)
        } else if (myList[position].strCategory.contains("Cocoa")) {
            Glide.with(context)
                .load(R.drawable.cocoa_preview)
                .apply(options)
                .into(holder.preview)
        } else if (myList[position].strCategory.contains("Shot")) {
            Glide.with(context)
                .load(R.drawable.shot_preview)
                .apply(options)
                .into(holder.preview)
        } else if (myList[position].strCategory.contains("Coffee / Tea")) {
            Glide.with(context)
                .load(R.drawable.coffee_tea_preview)
                .apply(options)
                .into(holder.preview)
        } else if (myList[position].strCategory.contains("Homemade Liqueur")) {
            Glide.with(context)
                .load(R.drawable.homemade_preview)
                .apply(options)
                .into(holder.preview)
        } else if (myList[position].strCategory.contains("Punch / Party Drink")) {
            Glide.with(context)
                .load(R.drawable.party_drink_preview)
                .apply(options)
                .into(holder.preview)
        } else if (myList[position].strCategory.contains("Beer")) {
            Glide.with(context)
                .load(R.drawable.beer_preview)
                .apply(options)
                .into(holder.preview)
        } else if (myList[position].strCategory.contains("Soft Drink / Soda")) {
            Glide.with(context)
                .load(R.drawable.coke_preview)
                .apply(options)
                .into(holder.preview)
        }


    }

    override fun getItemCount(): Int {
        return myList.size
    }

    fun setData(newList: List<Drink>) {
        myList = newList
        notifyDataSetChanged()
    }


    private var lastPosition = -1
    private fun setAnimation(viewToAnimate: View, position: Int) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            val anim = ScaleAnimation(
                0.0f,
                1.0f,
                0.0f,
                1.0f,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f
            )
            anim.duration =
                Random().nextInt(501).toLong() //to make duration random number between [0,501)
            viewToAnimate.startAnimation(anim)
            lastPosition = position
        }
    }
}