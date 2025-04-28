package com.aloel.dev.github.core.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Created by Alul on 27/04/2025.
 * Senior Android Developer
 */

@Parcelize
data class Repository(
    @SerializedName("id") var id: Int,
    @SerializedName("full_name") var full_name: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("html_url") var html_url: String? = null,
    @SerializedName("language") var language: String? = null,
    @SerializedName("watchers_count") var watchers_count: Int? = 0,
    @SerializedName("fork") var fork: Boolean? = null,
) : Parcelable