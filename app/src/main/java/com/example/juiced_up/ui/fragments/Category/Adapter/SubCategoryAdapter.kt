package com.example.juiced_up.ui.fragments.Category.Adapter

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
import com.example.juiced_up.api.model.SubCategory.Drink
import com.example.juiced_up.utils.Constants
import com.example.juiced_up.utils.Constants.Companion.preview
import java.util.*


class SubCategoryAdapter: RecyclerView.Adapter<SubCategoryAdapter.ViewHolder>() {
    private var myList = emptyList<Drink>()
    private lateinit var context: Context

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val preview: ImageView = itemView.findViewById(R.id.preview_iv)
        val title: TextView? = itemView.findViewById(R.id.title_tv)

        init {
            itemView.setOnClickListener {
                val bundle = Bundle()
                bundle.apply {
                    putString(Constants.ID,myList[adapterPosition].idDrink)
                    putString(Constants.NAME,myList[adapterPosition].strDrink) // call this to details fragment
                }
                it.findNavController().navigate(R.id.action_categoryFragment_to_detailsFragment,bundle)
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

        holder.title?.text = myList[position].strDrink

        val url = myList[position].strDrinkThumb+preview

        val options: RequestOptions = RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.loader)
            .error(R.drawable.sample_small_image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH)
            .dontAnimate()
            .dontTransform()

        Glide.with(context)
            .load(url)
            .apply(options)
            .into(holder.preview)
    }

    override fun getItemCount(): Int = myList.size

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