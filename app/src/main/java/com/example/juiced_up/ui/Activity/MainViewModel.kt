package com.example.juiced_up.ui.Activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.juiced_up.api.model.Category.CategoryResponse
import com.example.juiced_up.api.model.FilterByAlcohol.ByAlcoholResponse
import com.example.juiced_up.api.model.FilterByGlass.ByGlassResponse
import com.example.juiced_up.api.model.FilterByIngredients.ByIngredientsResponse
import com.example.juiced_up.api.model.YouMayLike.MayLikeResponse
import com.example.juiced_up.repo.JuicedUpRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import androidx.lifecycle.LiveData
import com.example.juiced_up.api.model.Details.DrinkDetails
import com.example.juiced_up.api.model.SearchByName.SearchResponse
import com.example.juiced_up.api.model.SubCategory.SubCategoryResponse


class MainViewModel(private val repository: JuicedUpRepository) : ViewModel() {

    val mayLikeResponse: MutableLiveData<Response<MayLikeResponse>> = MutableLiveData()
    val categoryResponse: MutableLiveData<Response<CategoryResponse>> = MutableLiveData()
    val filterByGlassResponse: MutableLiveData<Response<ByGlassResponse>> = MutableLiveData()
    val filterByIngredientsResponse: MutableLiveData<Response<ByIngredientsResponse>> =
        MutableLiveData()
    val filterByAlcoholResponse: MutableLiveData<Response<ByAlcoholResponse>> = MutableLiveData()
    val detailResponse: MutableLiveData<Response<DrinkDetails>> = MutableLiveData()
    val subCategoryResponse:MutableLiveData<Response<SubCategoryResponse>> = MutableLiveData()
    val searchResponse:MutableLiveData<Response<SearchResponse>> = MutableLiveData()






    fun getMayLike(filter: String) {
        viewModelScope.launch {
            val response = repository.getMayLikeDrink(filter)
            mayLikeResponse.value = response
        }
    }

    fun getCategoryDrink(filter: String) {
        viewModelScope.launch {
            val response = repository.getCategoryDrink(filter)
            categoryResponse.value = response
        }
    }

    fun filterByGlass(filter: String) {
        viewModelScope.launch {
            val response = repository.filterByGlass(filter)
            filterByGlassResponse.value = response
        }
    }

    fun filterByIngredient(filter: String) {
        viewModelScope.launch {
            val response = repository.filterByIngredients(filter)
            filterByIngredientsResponse.value = response
        }
    }

    fun filterByAlcohol(filter: String) {
        viewModelScope.launch {
            val response = repository.filterByAlcohol(filter)
            filterByAlcoholResponse.value = response
        }
    }

    fun getDetail(id:Int){
        viewModelScope.launch {
            val response = repository.getDetails(id)
            detailResponse.value = response
        }
    }

    fun getSubCategory(filter: String){
        viewModelScope.launch {
            val response = repository.getSubCategoryDrink(filter)
            subCategoryResponse.value = response
        }
    }

    fun searchByName(filter: String){
        viewModelScope.launch {
            val response = repository.searchByName(filter)
            searchResponse.value = response
        }
    }

}