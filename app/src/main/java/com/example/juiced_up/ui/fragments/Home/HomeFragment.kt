package com.example.juiced_up.ui.fragments.Home

import android.content.Context
import android.net.ConnectivityDiagnosticsManager
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.juiced_up.MainViewModelFactory
import com.example.juiced_up.R
import com.example.juiced_up.api.model.SearchByName.Drink
import com.example.juiced_up.databinding.FragmentHomeBinding
import com.example.juiced_up.repo.JuicedUpRepository
import com.example.juiced_up.ui.Activity.MainViewModel
import com.example.juiced_up.ui.fragments.Filter.BottomSheetFragment
import com.example.juiced_up.ui.fragments.Home.Category.CategoryAdapter
import com.example.juiced_up.ui.fragments.Home.Search.SearchAdapter
import com.example.juiced_up.ui.fragments.Home.YouMayLike.MayLikeAdapter
import com.example.juiced_up.utils.Constants
import com.example.juiced_up.utils.Constants.Companion.ALCOHOL
import com.example.juiced_up.utils.Constants.Companion.CHIP_ADDED
import com.example.juiced_up.utils.Constants.Companion.COUNT
import com.example.juiced_up.utils.Constants.Companion.GLASS
import com.example.juiced_up.utils.Constants.Companion.INGREDIENT
import com.example.juiced_up.utils.Constants.Companion.allList
import com.example.juiced_up.utils.Constants.Companion.outputStrLength
import com.example.juiced_up.utils.Constants.Companion.source
import com.example.juiced_up.utils.Constants.Companion.sourceLength
import com.example.juiced_up.utils.Constants.Companion.spanCount
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import java.io.Serializable
import kotlin.streams.asSequence
import androidx.core.content.ContextCompat.getSystemService





class HomeFragment : Fragment(), BottomSheetFragment.ChipsSelectedListener {

    private lateinit var viewModel: MainViewModel


    private var binding: FragmentHomeBinding? = null
    private val mayLikeAdapter by lazy { MayLikeAdapter() }
    private val categoryAdapter by lazy { CategoryAdapter() }
    private val searchAdapter by lazy { SearchAdapter() }
    private var alcohol: String = ""
    private var ingredient: String = ""
    private var glass: String = ""
    private var total:Int=0
    private var chipAdded = HashMap<String,ArrayList<String>>()
    private var totalTagsCount = 0
    private var aCtr=0
    private var gCtr=0
    private var iCtr=0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       

        val repository = JuicedUpRepository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel =
            ViewModelProvider(requireActivity(), viewModelFactory).get(MainViewModel::class.java)

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding?.progressBar?.isVisible = true




        return binding?.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRVMayLike()
        setUpRVCategory()




        binding?.apply {

            // FILTER BUTTON
            filterIv.setOnClickListener {
                Toast.makeText(requireContext(), "please wait...", Toast.LENGTH_SHORT).show()
                progressBar.isVisible = true
                val bundle = Bundle()
                bundle.apply {
                    putString(ALCOHOL, alcohol)
                    putString(INGREDIENT,ingredient)
                    putString(GLASS,glass)
                    putInt(COUNT,totalTagsCount)
                    putInt(Constants.aCtr,aCtr)
                    putInt(Constants.gCtr,gCtr)
                    putInt(Constants.iCtr,iCtr)
                    putSerializable(CHIP_ADDED,chipAdded as Serializable)
                }

                val tags=BottomSheetFragment(this@HomeFragment)
                tags.arguments=bundle
                tags.show(requireActivity().supportFragmentManager, "customTags")

                progressBar.isVisible = false

            }


            //TOTAL FILTER NUMBER
            if(totalTagsCount>0){
                Category.text = resources.getText(R.string.filtered_text)
                totalFilterNumber.isVisible = true
                totalFilterNumber.text = totalTagsCount.toString()
                setUpFilter()
            }
            else{
                totalFilterNumber.isVisible = false
                Category.text = resources.getText(R.string.category)
            }

            // SEARCH FOR
            searchTv.addTextChangedListener(object :TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    setUpSearch()
                    progressBar.isVisible = true
                    val searchText = "$s"
                    if(searchText.isNotEmpty()){
                        Category.text = resources.getText(R.string.searched_text)
                        viewModel.searchByName(searchText)
                        viewModel.searchResponse.observe(requireActivity(), Observer { response ->
                            if(response.isSuccessful){
                                binding?.progressBar?.isVisible = false
                                response.body()?.let {
                                    if(it.drinks!=null)
                                        searchAdapter.setData(it.drinks)
                                    else{
                                        Toast.makeText(requireContext(), "No drink matched", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                            else{
                                Toast.makeText(activity, "${response.code()} error", Toast.LENGTH_SHORT).show()
                                Log.i("HomeFragment", "Category-> onViewCreated: ${response.code().toString()}")
                            }
                        })
                    }
                    else{
                        Category.text = resources.getText(R.string.category)
                        setUpRVCategory()
                        viewModel.getCategoryDrink(allList)
                        viewModel.categoryResponse.observe(requireActivity(), Observer { response ->
                            if (response.isSuccessful) {
                                binding?.progressBar?.isVisible = false
                                response.body()?.let { categoryAdapter.setData(it.drinks) }
                            } else {

                                Toast.makeText(activity, "${response.code()} error", Toast.LENGTH_SHORT).show()
                                Log.i("HomeFragment", "Category-> onViewCreated: ${response.code().toString()}")
                            }
                        })
                    }

                }

                override fun afterTextChanged(s: Editable?) {

                }

            })

        }


        //generate character randomly
        val output = java.util.Random().ints(outputStrLength, 0, sourceLength)
            .asSequence()
            .map(source::get)
            .joinToString("")

        Log.i("Random alphabet", "onViewCreated: $output")

        viewModel.getMayLike(output)
        viewModel.mayLikeResponse.observe(requireActivity(), Observer { response ->
            if (response.isSuccessful) {
                binding?.progressBar?.isVisible = false
                response.body()?.let { mayLikeAdapter.setData(it.drinks) }
            } else {
                Toast.makeText(activity, "${response.code()} error", Toast.LENGTH_SHORT).show()
                Log.i("HomeFragment", "YouMayLike-> onViewCreated: ${response.code().toString()}")
            }
        })

        viewModel.getCategoryDrink(allList)
        viewModel.categoryResponse.observe(requireActivity(), Observer { response ->
            if (response.isSuccessful) {
                binding?.progressBar?.isVisible = false
                response.body()?.let { categoryAdapter.setData(it.drinks) }
            } else {

                Toast.makeText(activity, "${response.code()} error", Toast.LENGTH_SHORT).show()
                Log.i("HomeFragment", "Category-> onViewCreated: ${response.code().toString()}")
            }
        })

        viewModel.filterByAlcohol(allList)
        viewModel.filterByAlcoholResponse.observe(requireActivity(), Observer { response ->
            if (response.isSuccessful) {
                if (isAdded) {
                    val res = response.body()!!.drinks
                    alcohol = Gson().toJson(res)
                }

            } else {
                Snackbar.make(view,  "Something went Wrong", Snackbar.LENGTH_LONG).apply {
                    setAction("Retry") {
                        viewModel.filterByAlcohol(allList)
                    }
                }.show()
            }
        })

        viewModel.filterByIngredient(allList)
        viewModel.filterByIngredientsResponse.observe(requireActivity(), Observer { response ->
            if (response.isSuccessful) {
                if (isAdded) {
                    val res = response.body()!!.drinks
                    ingredient = Gson().toJson(res)
                }

            } else {
                // show snackbar
                Snackbar.make(view,  "Something went Wrong", Snackbar.LENGTH_LONG).apply {
                    setAction("Retry") {
                        viewModel.filterByIngredient(allList)
                    }
                }.show()
            }
        })

        viewModel.filterByGlass(allList)
        viewModel.filterByGlassResponse.observe(requireActivity(), Observer { response ->
            if (response.isSuccessful) {
                if (isAdded) {
                    val res=response.body()!!.drinks
                    glass=Gson().toJson(res)
                }

            } else {
                Snackbar.make(view,  "Something went Wrong", Snackbar.LENGTH_LONG).apply {
                    setAction("Retry") {
                        viewModel.filterByGlass(allList)
                    }
                }.show()
            }
        })


    }




    private fun setUpSearch() {
        binding?.apply {
            categoryRV.adapter = searchAdapter
            categoryRV.layoutManager = GridLayoutManager(requireContext(), spanCount)
        }
    }

    private fun setUpFilter(){

    }


    private fun setUpRVMayLike() {
        binding?.apply {
            youMayLikeRV.adapter = mayLikeAdapter
            youMayLikeRV.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL, false
            )

        }
    }

    private fun setUpRVCategory() {
        binding?.apply {
            categoryRV.adapter = categoryAdapter
            categoryRV.layoutManager = GridLayoutManager(requireContext(), spanCount)

        }
    }


    override fun onChipChange(totalTagsCount: Int?) {
        if (totalTagsCount != null) {
            this.totalTagsCount=totalTagsCount
        }
        binding?.apply {
            if (totalTagsCount != null) {
                if(totalTagsCount>0){
                    Category.text = resources.getText(R.string.filtered_text)
                    totalFilterNumber.isVisible = true
                    totalFilterNumber.text = totalTagsCount.toString()
                }
                else{
                    Category.text = resources.getText(R.string.category)
                    totalFilterNumber.isVisible = false
                }
            }
            else{
                Category.text = resources.getText(R.string.category)
                totalFilterNumber.isVisible = false
            }
        }
        Log.i("total_filter", "onChipChange: $totalTagsCount")
    }

    override fun onChipAdded(chipAdded: HashMap<String,ArrayList<String>>) {
        Log.i("total_filter", "OnChipAdded: $chipAdded")
        this.chipAdded = chipAdded
    }

    override fun individualChipGroupCount(alcohol: Int, glass: Int, ingredient: Int) {
        Log.i("individual_count", "individualChipGroupCount: $alcohol , $glass , $ingredient")
        this.aCtr = alcohol
        this.gCtr = glass
        this.iCtr = ingredient
    }


}