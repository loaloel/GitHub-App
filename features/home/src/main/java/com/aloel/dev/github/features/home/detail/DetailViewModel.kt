package com.aloel.dev.github.features.home.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aloel.dev.github.core.models.BaseList
import com.aloel.dev.github.core.models.Repository
import com.aloel.dev.github.core.models.User
import com.aloel.dev.github.core.networks.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Alul on 28/04/2025.
 * Senior Android Developer
 */

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: DetailRepository) : ViewModel() {

    private val _user = MutableStateFlow<Resource<User>>(Resource.Loading())
    val user: StateFlow<Resource<User>> = _user

    private val _repo = MutableStateFlow<Resource<ArrayList<Repository>>>(Resource.Loading())
    val repo: StateFlow<Resource<ArrayList<Repository>>> = _repo

    fun getUserDetail(id: Int? = null) {
        viewModelScope.launch {
            _user.value = repository.getUserDetail(id)
        }
    }

    fun getRepoList(userId: Int? = null, page: Int = 1) {
        viewModelScope.launch {
            _repo.value = Resource.Loading()
            _repo.value = repository.getRepoList(userId, page)
        }
    }
}