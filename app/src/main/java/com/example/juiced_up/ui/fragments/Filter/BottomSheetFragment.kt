package com.example.juiced_up.ui.fragments.Filter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.juiced_up.MainViewModelFactory
import com.example.juiced_up.R
import com.example.juiced_up.api.model.FilterByAlcohol.Drink
import com.example.juiced_up.databinding.FragmentBottomSheetBinding
import com.example.juiced_up.repo.JuicedUpRepository
import com.example.juiced_up.ui.Activity.MainViewModel
import com.example.juiced_up.utils.Constants
import com.example.juiced_up.utils.Constants.Companion.ALCOHOL
import com.example.juiced_up.utils.Constants.Companion.CHIP_ADDED
import com.example.juiced_up.utils.Constants.Companion.COUNT
import com.example.juiced_up.utils.Constants.Companion.GLASS
import com.example.juiced_up.utils.Constants.Companion.INGREDIENT
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class BottomSheetFragment(var listener: ChipsSelectedListener) : BottomSheetDialogFragment() {

    lateinit var totalFilterListener: ChipsSelectedListener


    interface ChipsSelectedListener {
        fun onChipChange(totalTagsCount: Int?)
        fun onChipAdded(chipAdded: HashMap<String, ArrayList<String>>)
        fun individualChipGroupCount(alcohol: Int, glass: Int, ingredient: Int)
    }

    private lateinit var viewModel: MainViewModel

    private var binding: FragmentBottomSheetBinding? = null
    private var alcoholFilterCount = 0
    private var glassFilterCount = 0
    private var ingredientFilterCount = 0
    private var g = 0
    private var i = 0
    private var a = 0
    private var totalCount = 0
    private var alcoholFilterList = mutableListOf<String>()
    private var glassFilterList = mutableListOf<String>()
    private var ingredientFilterList = mutableListOf<String>()
    private var alcohol: String = ""
    private var ingredient: String = ""
    private var glass: String = ""
    private var selectedAlcoholChip = ArrayList<String>()
    private var selectedGlassChip = ArrayList<String>()
    private var selectedIngredientChip = ArrayList<String>()
    private var chipMap = HashMap<String, ArrayList<String>>()
    private var selectedChip: HashMap<String, ArrayList<String>>? = null
    private var totalSelected = 0
    private var aCtr = 0
    private var gCtr = 0
    private var iCtr = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            alcohol = getString(ALCOHOL).toString()
            ingredient = getString(INGREDIENT).toString()
            glass = getString(GLASS).toString()
            totalSelected = getInt(COUNT)
            aCtr = getInt(Constants.aCtr)
            gCtr = getInt(Constants.gCtr)
            iCtr = getInt(Constants.iCtr)
            selectedChip = (getSerializable(CHIP_ADDED) as? HashMap<String, ArrayList<String>>)
        }
        Log.i("selectedChip", "onCreate: $selectedChip")
        alcoholFilterCount += aCtr
        glassFilterCount += gCtr
        ingredientFilterCount += iCtr
        totalCount += totalSelected

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
        totalFilterListener = listener

        binding = FragmentBottomSheetBinding.inflate(inflater, container, false)


        binding?.apply {
            if (totalSelected > 0) {
                if (aCtr > 0) {
                    if (selectedChip?.get("Alcohol")?.size!! > 0) {
                        alcoholicFilterNumber.isVisible = true
                        alcoholicFilterNumber.text = selectedChip!!["Alcohol"]?.size.toString()
                    }
                }

                if (gCtr > 0) {
                    if (selectedChip!!["Glass"]?.size!! > 0) {
                        glassFilterNumber.isVisible = true
                        glassFilterNumber.text = selectedChip!!["Glass"]?.size.toString()
                    }
                }

                if (iCtr > 0) {
                    if (selectedChip!!["Ingredient"]?.size!! > 0) {
                        ingredientFilterNumber.isVisible = true
                        ingredientFilterNumber.text = selectedChip!!["Ingredient"]?.size.toString()
                    }
                }
            }


        }

        val gson = Gson()


        val type1 = object : TypeToken<ArrayList<Drink?>?>() {}.type
        val alcoholList: List<Drink> =
            gson.fromJson(alcohol, type1)
        while (a < alcoholList.size) {
            addChipAlcohol(alcoholList[a].strAlcoholic)
            a++

        }

        val type2 = object :
            TypeToken<ArrayList<com.example.juiced_up.api.model.FilterByIngredients.Drink?>?>() {}.type
        val ingredientList: List<com.example.juiced_up.api.model.FilterByIngredients.Drink> =
            gson.fromJson(ingredient, type2)
        while (i < ingredientList.size) {
            addChipIngredient(ingredientList[i].strIngredient1)
            i++

        }

        val type3 = object :
            TypeToken<ArrayList<com.example.juiced_up.api.model.FilterByGlass.Drink?>?>() {}.type
        val glassList: List<com.example.juiced_up.api.model.FilterByGlass.Drink> =
            gson.fromJson(glass, type3)
        while (g < glassList.size) {
            addChipGlass(glassList[g].strGlass)
            g++

        }







        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {

            chipGroupAlcohol.children.forEach {
                (it as Chip).setOnCheckedChangeListener { buttonView, isChecked ->
                    alcoholicFilterNumber.isVisible = true
                    if (it.isChecked) {
                        totalCount++
                        alcoholFilterCount++
                        selectedAlcoholChip.add((it as Chip).text.toString())
                    }
                    if (!it.isChecked) {
                        totalCount--
                        alcoholFilterCount--
                        selectedAlcoholChip.remove((it as Chip).text.toString())
                    }
                    chipMap["Alcohol"] = selectedAlcoholChip
                    listener.onChipAdded(chipMap)
                    listener.onChipChange(totalCount)
                    listener.individualChipGroupCount(
                        alcoholFilterCount,
                        glassFilterCount,
                        ingredientFilterCount
                    )
                    if (alcoholFilterCount == 0) alcoholicFilterNumber.isVisible = false
                    alcoholicFilterNumber.text = alcoholFilterCount.toString()
                }
            }

            chipGroupGlass.children.forEach {
                (it as Chip).setOnCheckedChangeListener { buttonView, isChecked ->
                    glassFilterNumber.isVisible = true
                    if (it.isChecked) {
                        selectedGlassChip.add((it as Chip).text.toString())
                        glassFilterCount++
                        totalCount++
                    }
                    if (!it.isChecked) {
                        selectedGlassChip.remove((it as Chip).text.toString())
                        glassFilterCount--
                        totalCount--
                    }
                    chipMap["Glass"] = selectedGlassChip
                    listener.onChipAdded(chipMap)
                    listener.onChipChange(totalCount)
                    listener.individualChipGroupCount(
                        alcoholFilterCount,
                        glassFilterCount,
                        ingredientFilterCount
                    )
                    if (glassFilterCount == 0) glassFilterNumber.isVisible = false
                    glassFilterNumber.text = glassFilterCount.toString()
                }
            }

            chipGroupIngredient.children.forEach {
                (it as Chip).setOnCheckedChangeListener { buttonView, isChecked ->
                    ingredientFilterNumber.isVisible = true
                    if (it.isChecked) {
                        selectedIngredientChip.add((it as Chip).text.toString())
                        ingredientFilterCount++
                        totalCount++
                    }
                    if (!it.isChecked) {
                        selectedIngredientChip.remove((it as Chip).text.toString())
                        ingredientFilterCount--
                        totalCount--
                    }
                    chipMap["Ingredient"] = selectedIngredientChip
                    listener.onChipAdded(chipMap)
                    listener.onChipChange(totalCount)
                    listener.individualChipGroupCount(
                        alcoholFilterCount,
                        glassFilterCount,
                        ingredientFilterCount
                    )
                    if (ingredientFilterCount == 0) ingredientFilterNumber.isVisible = false
                    ingredientFilterNumber.text = ingredientFilterCount.toString()
                }
            }

        }


    }

    private fun addChipAlcohol(text: String) {
        val chip = layoutInflater.inflate(
            R.layout.single_chip_layout,
            binding?.chipGroupAlcohol,
            false
        ) as Chip
        chip.text = text

        binding?.chipGroupAlcohol?.addView(chip)


        if (selectedChip?.get("Alcohol")?.contains(text) == true) {
            Log.i("chipMatched", "addChipAlcohol:  $text")
            binding?.chipGroupAlcohol?.check(chip.id)
        }


    }

    private fun addChipGlass(text: String) {
        val chip = layoutInflater.inflate(
            R.layout.single_chip_layout,
            binding?.chipGroupGlass,
            false
        ) as Chip


        chip.text = text

        binding?.chipGroupGlass?.addView(chip)

        if (selectedChip?.get("Glass")?.contains(text) == true) {
            Log.i("chipMatched", "addChipGlass:  $text")
            binding?.chipGroupGlass?.check(chip.id)
        }
    }

    private fun addChipIngredient(text: String) {
        val chip = layoutInflater.inflate(
            R.layout.single_chip_layout,
            binding?.chipGroupIngredient,
            false
        ) as Chip
        chip.text = text

        binding?.chipGroupIngredient?.addView(chip)

        if (selectedChip?.get("Ingredient")?.contains(text) == true) {
            Log.i("chipMatched", "addChipIngredient:  $text")
            binding?.chipGroupIngredient?.check(chip.id)
        }
    }


    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }


}