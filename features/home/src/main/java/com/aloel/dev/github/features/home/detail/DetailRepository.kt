package com.aloel.dev.github.features.home.detail

import com.aloel.dev.github.core.models.Repository
import com.aloel.dev.github.core.models.User
import com.aloel.dev.github.core.networks.Resource
import com.aloel.dev.github.core.networks.Services
import com.aloel.dev.github.core.networks.safeApiCall
import javax.inject.Inject

/**
 * Created by Alul on 28/04/2025.
 * Senior Android Developer
 */

class DetailRepository @Inject constructor(private val services: Services) {

    suspend fun getRepoList(
        userId: Int? = null,
        page: Int = 1,
    ): Resource<ArrayList<Repository>> {
        return safeApiCall { services.getRepositoryList(userId, page) }
    }

    suspend fun getUserDetail(
        id: Int? = null,
    ): Resource<User> {
        return safeApiCall { services.getUserDetail(id) }
    }
}