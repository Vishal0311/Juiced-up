package com.example.juiced_up.repo

import com.example.juiced_up.api.service.MyClient

class JuicedUpRepository() {


    suspend fun getMayLikeDrink(filter: String) = MyClient.api.getMayLikeDrink(filter)

    suspend fun getCategoryDrink(filter: String) = MyClient.api.getCategoryDrink(filter)

    suspend fun filterByGlass(filter: String) = MyClient.api.filterByGlass(filter)

    suspend fun filterByIngredients(filter: String) = MyClient.api.filterByIngredient(filter)

    suspend fun filterByAlcohol(filter: String) = MyClient.api.filterByAlcohol(filter)

    suspend fun getDetails(id: Int) = MyClient.api.getDetail(id)

    suspend fun getSubCategoryDrink(filter: String) = MyClient.api.getSubCategoryDrink(filter)

    suspend fun searchByName(filter: String) = MyClient.api.searchByName(filter)

}