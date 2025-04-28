package com.aloel.dev.github.core.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Alul on 27/04/2025.
 * Senior Android Developer
 */

open class BaseList<T>(
    @SerializedName("items") var items: ArrayList<T>,
)