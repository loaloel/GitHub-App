package com.aloel.dev.github.features.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aloel.dev.github.core.models.BaseList
import com.aloel.dev.github.core.models.User
import com.aloel.dev.github.core.networks.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Alul on 27/04/2025.
 * Senior Android Developer
 */

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {

    private val _search = MutableStateFlow<Resource<BaseList<User>>>(Resource.Loading())
    val search: StateFlow<Resource<BaseList<User>>> = _search

    fun doSearch(
        query: String,
        page: Int = 1,
    ) {
        viewModelScope.launch {
            _search.value = Resource.Loading()
            _search.value = repository.searchUser(query, page)
        }
    }
}