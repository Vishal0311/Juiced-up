package com.example.juiced_up.ui.fragments.Category

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.juiced_up.MainViewModelFactory
import com.example.juiced_up.databinding.FragmentCategoryBinding
import com.example.juiced_up.repo.JuicedUpRepository
import com.example.juiced_up.ui.Activity.MainViewModel
import com.example.juiced_up.ui.fragments.Category.Adapter.SubCategoryAdapter
import com.example.juiced_up.utils.Constants

import com.example.juiced_up.utils.Constants.Companion.TITLE
import com.example.juiced_up.utils.Constants.Companion.spanCount


class CategoryFragment : Fragment() {

    private var binding: FragmentCategoryBinding? = null
    private var title: String = ""
    private val categoryAdapter by lazy { SubCategoryAdapter() }
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = JuicedUpRepository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel =
            ViewModelProvider(requireActivity(), viewModelFactory).get(MainViewModel::class.java)

        arguments?.apply {
            title = getString(TITLE).toString()
        }
        Log.i("DrinkName", "onCreate: $title")
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = title
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRVCategory()

        viewModel.getSubCategory(title)
        viewModel.subCategoryResponse.observe(requireActivity(), Observer { response->
            if(response.isSuccessful){
                binding?.progressBar?.isVisible = false
                response.body()?.let { categoryAdapter.setData(it.drinks) }
            }
            else{
                Toast.makeText(activity, "${response.code()} error", Toast.LENGTH_SHORT).show()
                Log.i("HomeFragment", "Category-> onViewCreated: ${response.code().toString()}")
            }
        })
    }

    private fun setUpRVCategory() {
        binding?.apply {
            categoryRV.adapter = categoryAdapter
            categoryRV.layoutManager = GridLayoutManager(requireContext(), spanCount)

        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CategoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CategoryFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}