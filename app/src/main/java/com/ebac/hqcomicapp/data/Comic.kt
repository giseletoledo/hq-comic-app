package com.ebac.hqcomicapp.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Comic(
    val id: Int?,
    val title: String?,
    val description: String?,
    val textObjects: List<TextObject>?,
    val thumbnail: Image?
){
    fun getContent():String{
        return when {
            description?.isNotEmpty() == true -> description
                textObjects?.isNotEmpty() == true -> textObjects[0].text ?: "Conteúdo não disponível"
            else -> "Conteúdo não disponível"
        }
    }

    fun getIdString(): String{
        return id?.toString() ?: ""
    }
}
