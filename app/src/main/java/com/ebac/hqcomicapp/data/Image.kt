package com.ebac.hqcomicapp.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Image(
    val path: String?,
    val extension: String?
) {
    fun getFullImagePath(): String{
      val pathHttps = path?.replace("http","https")
      return "$pathHttps.$extension"
    }
}
