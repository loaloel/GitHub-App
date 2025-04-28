package com.aloel.dev.github.core.networks

import com.aloel.dev.github.core.models.BaseList
import com.aloel.dev.github.core.models.Repository
import com.aloel.dev.github.core.models.User
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Alul on 27/04/2025.
 * Senior Android Developer
 */

interface Services {

    @GET(NetworkPath.USER_LIST)
    suspend fun getUserList(
        @Query("q") query: String? = null,
        @Query("page") page: Int = 1,
    ): BaseList<User>

    @GET(NetworkPath.USER_DETAIL)
    suspend fun getUserDetail(
        @Path(value = "userId", encoded = true) userId: Int? = null
    ): User

    @GET(NetworkPath.REPOSITORY_LIST)
    suspend fun getRepositoryList(
        @Path(value = "userId", encoded = true) userId: Int? = null,
        @Query("page") page: Int = 1,
    ): ArrayList<Repository>
}