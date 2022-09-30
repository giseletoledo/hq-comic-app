package com.ebac.hqcomicapp.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TextObject(
    val type: String?,
    val language: String?,
    val text: String?
) {

}