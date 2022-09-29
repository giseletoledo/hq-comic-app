package com.ebac.hqcomicapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ebac.hqcomicapp.HQDetails.HQDetails
import com.ebac.hqcomicapp.placeholder.PlaceholderContent
import com.ebac.hqcomicapp.placeholder.PlaceholderContent.PlaceholderItem

class HQViewModel : ViewModel() {
    //primeiro pega como tipo LiveData que não é modificável, só de leitura
    val hqDetailsLiveData : LiveData<HQDetails>
        get() = _hqDetailsLiveData
    //transforma em dados mutáveis
    private val _hqDetailsLiveData = MutableLiveData<HQDetails>()

    val hqListLiveData: LiveData<MutableList<PlaceholderItem>>
        get() = _hqListLiveData

    private val _hqListLiveData = MutableLiveData<MutableList<PlaceholderItem>>()

    val navigationToDetailLiveData
        get() = _navigationToDetailLiveData

    private val _navigationToDetailLiveData = MutableLiveData<Unit>()


    init {
        _hqListLiveData.postValue(PlaceholderContent.ITEMS)
    }

    fun onHQSelected(position: Int){
        val hqDetails = HQDetails("Minha HQ", "Este é um conteúdo de texto extenso")
        _hqDetailsLiveData.postValue(hqDetails)
        _navigationToDetailLiveData.postValue(Unit)
    }
}