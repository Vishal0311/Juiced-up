package com.example.juiced_up.api.service

import com.example.juiced_up.api.model.Category.CategoryResponse
import com.example.juiced_up.api.model.Details.DrinkDetails
import com.example.juiced_up.api.model.FilterByAlcohol.ByAlcoholResponse
import com.example.juiced_up.api.model.FilterByGlass.ByGlassResponse
import com.example.juiced_up.api.model.FilterByIngredients.ByIngredientsResponse
import com.example.juiced_up.api.model.SearchByName.SearchResponse
import com.example.juiced_up.api.model.SubCategory.SubCategoryResponse
import com.example.juiced_up.api.model.YouMayLike.MayLikeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface JuicedUpApi {

    @GET("search.php")
    suspend fun getMayLikeDrink(
        @Query("f") f: String
    ): Response<MayLikeResponse>

    @GET("list.php")
    suspend fun getCategoryDrink(
        @Query("c") c: String
    ): Response<CategoryResponse>

    @GET("list.php")
    suspend fun filterByGlass(
        @Query("g") g: String
    ): Response<ByGlassResponse>

    @GET("list.php")
    suspend fun filterByIngredient(
        @Query("i") i: String
    ): Response<ByIngredientsResponse>

    @GET("list.php")
    suspend fun filterByAlcohol(
        @Query("a") a: String
    ): Response<ByAlcoholResponse>

    @GET("lookup.php")
    suspend fun getDetail(
        @Query("i") i: Int
    ): Response<DrinkDetails>

    @GET("filter.php")
    suspend fun getSubCategoryDrink(
        @Query("c") c:String
    ): Response<SubCategoryResponse>

    @GET("search.php")
    suspend fun searchByName(
        @Query("s") s:String
    ):Response<SearchResponse>

}