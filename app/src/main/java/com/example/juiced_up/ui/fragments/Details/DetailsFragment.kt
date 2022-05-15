package com.example.juiced_up.ui.fragments.Details

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.juiced_up.MainViewModelFactory
import com.example.juiced_up.R
import com.example.juiced_up.api.model.Details.DrinkDetails
import com.example.juiced_up.databinding.FragmentDetailsBinding
import com.example.juiced_up.repo.JuicedUpRepository
import com.example.juiced_up.ui.Activity.MainActivity
import com.example.juiced_up.ui.Activity.MainViewModel
import com.example.juiced_up.utils.Constants
import com.example.juiced_up.utils.Constants.Companion.ID
import com.example.juiced_up.utils.Constants.Companion.INGREDIENTS
import com.example.juiced_up.utils.Constants.Companion.INSTRUCTIONS
import com.example.juiced_up.utils.Constants.Companion.NAME
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Response
import androidx.appcompat.app.AppCompatActivity





class DetailsFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private var binding: FragmentDetailsBinding? = null
    private var drinkId: String = ""
    private var drinkName: String = ""
    public var instruction: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val repository = JuicedUpRepository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel =
            ViewModelProvider(requireActivity(), viewModelFactory).get(MainViewModel::class.java)
        arguments?.apply {
            drinkId = getString(ID).toString()
            drinkName = getString(NAME).toString()
        }
        Log.i("DrinkName", "onCreate: $drinkName")
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = drinkName
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getDetail(drinkId.toInt())
        viewModel.detailResponse.observe(requireActivity(), Observer { response ->
            if (response.isSuccessful) {
                if (isAdded) {
                    saveDataForTabLayouts(response)
                    val url = response.body()?.drinks?.get(0)?.strDrinkThumb
                    loadImage(url)
                    binding?.apply {
                        titleTv.text = response.body()?.drinks?.get(0)?.strDrink
                        alcoholTv.text = response.body()?.drinks?.get(0)?.strAlcoholic
                        glassTv.text = response.body()?.drinks?.get(0)?.strGlass
                    }
                }
            } else {
                Toast.makeText(activity, "${response.code()} error", Toast.LENGTH_SHORT).show()
                Log.i("HomeFragment", "YouMayLike-> onViewCreated: ${response.code().toString()}")
            }
        })

        binding?.apply {
            viewPager.adapter = FragmentAdapter(requireParentFragment())
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                when (position) {
                    0 -> tab.text = INGREDIENTS
                    1 -> tab.text = INSTRUCTIONS

                }
            }.attach()
        }
    }

    private fun saveDataForTabLayouts(response: Response<DrinkDetails>?) {
        val pref =
            requireContext().getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString(INSTRUCTIONS, response?.body()?.drinks?.get(0)?.strInstructions)
        editor.putString("ING1", response?.body()?.drinks?.get(0)?.strIngredient1)
        editor.putString("ING2", response?.body()?.drinks?.get(0)?.strIngredient2)
        editor.putString("ING3", response?.body()?.drinks?.get(0)?.strIngredient3)
        editor.putString("ING4", response?.body()?.drinks?.get(0)?.strIngredient4)
        editor.putString("ING5", response?.body()?.drinks?.get(0)?.strIngredient5)
        editor.putString("ING6", response?.body()?.drinks?.get(0)?.strIngredient6)
        editor.putString("ING7", response?.body()?.drinks?.get(0)?.strIngredient7)
        editor.putString("ING8", response?.body()?.drinks?.get(0)?.strIngredient8)
        editor.putString("ING9", response?.body()?.drinks?.get(0)?.strIngredient9)
        editor.putString("ING10", response?.body()?.drinks?.get(0)?.strIngredient10)
        editor.putString("ING11", response?.body()?.drinks?.get(0)?.strIngredient11)
        editor.putString("ING12", response?.body()?.drinks?.get(0)?.strIngredient12)
        editor.putString("ING13", response?.body()?.drinks?.get(0)?.strIngredient13)
        editor.putString("ING14", response?.body()?.drinks?.get(0)?.strIngredient14)
        editor.putString("ING15", response?.body()?.drinks?.get(0)?.strIngredient15)
        editor.apply()

        Log.i("INGS", "saveDataForTabLayouts: ${ response?.body()?.drinks?.get(0)?.strIngredient1}" +
                "${ response?.body()?.drinks?.get(0)?.strIngredient2}")
    }


    private fun loadImage(url: String?) {
        val options: RequestOptions = RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.loader)
            .error(R.drawable.sample_small_image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH)
            .dontAnimate()
            .dontTransform()

        binding?.previewIv?.let {
            Glide.with(requireActivity())
                .load(url)
                .apply(options)
                .into(it)
        }
    }

    class FragmentAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> IngredientsFragment.newInstance()
                1 -> InstructionsFragment.newInstance()
                else -> IngredientsFragment.newInstance()
            }
        }

    }
}