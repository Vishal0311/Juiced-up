package com.example.juiced_up.ui.fragments.Details

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.juiced_up.databinding.FragmentIngredientsBinding
import com.example.juiced_up.ui.fragments.Details.Adapter.IngredientAdapter
import com.example.juiced_up.ui.fragments.Details.Adapter.IngredientDataClass
import com.example.juiced_up.utils.Constants
import com.example.juiced_up.utils.Constants.Companion.spanCount


class IngredientsFragment : Fragment() {


    private val mainList = ArrayList<IngredientDataClass>()
    private var binding: FragmentIngredientsBinding? = null
    private lateinit var adapter: IngredientAdapter
    var flag = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pref =
            requireContext().getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val inst1 = pref.getString("ING1", null)
        val inst2 = pref.getString("ING2", null)
        val inst3 = pref.getString("ING3", null)
        val inst4 = pref.getString("ING4", null)
        val inst5 = pref.getString("ING5", null)
        val inst6 = pref.getString("ING6", null)
        val inst7 = pref.getString("ING7", null)
        val inst8 = pref.getString("ING8", null)
        val inst9 = pref.getString("ING9", null)
        val inst10 = pref.getString("ING10", null)
        val inst11 = pref.getString("ING11", null)
        val inst12 = pref.getString("ING12", null)
        val inst13 = pref.getString("ING13", null)
        val inst14 = pref.getString("ING14", null)
        val inst15 = pref.getString("ING15", null)

        Log.i("INGS", "onCreate: $inst1 $inst2 $inst3")

        if (inst1 != null) {
            flag = 1
            val item = IngredientDataClass(inst1)
            mainList += item
        }
        if (inst2 != null) {
            flag = 1
            val item = IngredientDataClass(inst2)
            mainList += item
        }
        if (inst3 != null) {
            flag = 1
            val item = IngredientDataClass(inst3)
            mainList += item
        }
        if (inst4 != null) {
            flag = 1
            val item = IngredientDataClass(inst4)
            mainList += item
        }
        if (inst5 != null) {
            flag = 1
            val item = IngredientDataClass(inst5)
            mainList += item
        }
        if (inst6 != null) {
            flag = 1
            val item = IngredientDataClass(inst6)
            mainList += item
        }
        if (inst7 != null) {
            flag = 1
            val item = IngredientDataClass(inst7)
            mainList += item
        }
        if (inst8 != null) {
            flag = 1
            val item = IngredientDataClass(inst8)
            mainList += item
        }
        if (inst9 != null) {
            flag = 1
            val item = IngredientDataClass(inst9)
            mainList += item
        }
        if (inst10 != null) {
            flag = 1
            val item = IngredientDataClass(inst10)
            mainList += item
        }
        if (inst11 != null) {
            flag = 1
            val item = IngredientDataClass(inst11)
            mainList += item
        }
        if (inst12 != null) {
            flag = 1
            val item = IngredientDataClass(inst12)
            mainList += item
        }
        if (inst13 != null) {
            flag = 1
            val item = IngredientDataClass(inst13)
            mainList += item
        }
        if (inst14 != null) {
            flag = 1
            val item = IngredientDataClass(inst14)
            mainList += item
        }
        if (inst15 != null) {
            flag = 1
            val item = IngredientDataClass(inst15)
            mainList += item
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentIngredientsBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            if(flag==1){
                nothingToShow.isVisible = false
                ingredientRV.isVisible=true
                ingredientRV?.apply {
                    adapter = IngredientAdapter(mainList)
                    layoutManager = GridLayoutManager(requireContext(), spanCount)
                    setHasFixedSize(true)
                }
            }else if(flag==0){
                ingredientRV.isVisible=false
                nothingToShow.isVisible = true
            }
        }



    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment IngredientsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            IngredientsFragment().apply {
                arguments = Bundle().apply {

                }
            }

        fun newInstance() = IngredientsFragment().apply {

        }
    }
}