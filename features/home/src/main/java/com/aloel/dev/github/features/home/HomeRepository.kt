package com.aloel.dev.github.features.home

import com.aloel.dev.github.core.models.BaseList
import com.aloel.dev.github.core.models.User
import com.aloel.dev.github.core.networks.Resource
import com.aloel.dev.github.core.networks.Services
import com.aloel.dev.github.core.networks.safeApiCall
import javax.inject.Inject

/**
 * Created by Alul on 27/04/2025.
 * Senior Android Developer
 */

class HomeRepository @Inject constructor(private val services: Services) {

    suspend fun searchUser(
        query: String,
        page: Int = 1,
    ): Resource<BaseList<User>> {
        return safeApiCall { services.getUserList(query, page) }
    }
}