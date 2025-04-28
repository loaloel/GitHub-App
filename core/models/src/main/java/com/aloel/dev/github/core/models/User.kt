package com.aloel.dev.github.core.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Created by Alul on 27/04/2025.
 * Senior Android Developer
 */

@Parcelize
data class User(
    @SerializedName("id") var id: Int,
    @SerializedName("login") var login: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("avatar_url") var avatar_url: String? = null,
    @SerializedName("bio") var bio: String? = null,
    @SerializedName("followers") var followers: Int? = 0,
    @SerializedName("following") var following: Int? = 0,
): Parcelable