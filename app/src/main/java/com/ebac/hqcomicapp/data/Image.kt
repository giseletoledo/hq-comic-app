package com.ebac.hqcomicapp.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Image(
    val path: String?,
    val extension: String?
) {
    fun getFullImagePath() = "$path.$extension"
}
