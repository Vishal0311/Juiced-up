package com.example.juiced_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.juiced_up.repo.JuicedUpRepository
import com.example.juiced_up.ui.Activity.MainViewModel

class MainViewModelFactory(private val repository: JuicedUpRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}