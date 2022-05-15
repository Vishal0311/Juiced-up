package com.example.juiced_up.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.juiced_up.api.model.Details.Drink
import com.example.juiced_up.utils.Constants.Companion.INSTRUCTIONS
import com.example.juiced_up.utils.Constants.Companion.SHARED_PREF_NAME

object SharedPref {

    fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveDrinkDetails(context: Context, drinks: List<Drink>) {
        val pref = getSharedPreferences(context)
        val editor = pref.edit()
        editor.putString(INSTRUCTIONS,drinks.get(0).strInstructions)
        editor.putString("ING1",drinks.get(0).strIngredient1)
        editor.putString("ING2",drinks.get(0).strIngredient2)
        editor.putString("ING3",drinks.get(0).strIngredient3)
        editor.putString("ING4",drinks.get(0).strIngredient4)
        editor.putString("ING5",drinks.get(0).strIngredient5)
        editor.putString("ING6",drinks.get(0).strIngredient6)
        editor.putString("ING7",drinks.get(0).strIngredient7)
        editor.putString("ING8",drinks.get(0).strIngredient8)
        editor.putString("ING9",drinks.get(0).strIngredient9)
        editor.putString("ING10",drinks.get(0).strIngredient10)
        editor.putString("ING11",drinks.get(0).strIngredient11)
        editor.putString("ING12",drinks.get(0).strIngredient12)
        editor.putString("ING13",drinks.get(0).strIngredient13)
        editor.putString("ING14",drinks.get(0).strIngredient14)
        editor.putString("ING15",drinks.get(0).strIngredient15)
        editor.apply()
    }


    fun getDrinkDetails(context: Context){

    }


}