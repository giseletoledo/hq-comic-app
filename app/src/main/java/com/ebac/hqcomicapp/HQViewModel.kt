package com.ebac.hqcomicapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ebac.hqcomicapp.api.ComicsService
import com.ebac.hqcomicapp.data.ApiCredentials
import com.ebac.hqcomicapp.data.Comic
import com.ebac.hqcomicapp.data.DataState
import com.ebac.hqcomicapp.data.Event
import com.ebac.hqcomicapp.helper.ApiHelper
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class HQViewModel : ViewModel() {

    val hqDetailsLiveData: LiveData<Comic>//primeiro pega como tipo LiveData que não é modificável, só de leitura
        get() = _hqDetailsLiveData
    private val _hqDetailsLiveData = MutableLiveData<Comic>() //transforma em dados mutáveis

    val hqListLiveData: LiveData<List<Comic>?>
        get() = _hqListLiveData
    private val _hqListLiveData = MutableLiveData<List<Comic>?>()

    val appState: LiveData<DataState>
        get() = _appState
    private val _appState = MutableLiveData<DataState>()

    val navigationToDetailLiveData
        get() = _navigationToDetailLiveData
    private val _navigationToDetailLiveData = MutableLiveData<Event<Unit>>()

    private val retrofit = Retrofit.Builder()
        .baseUrl(ApiCredentials().baseUrl)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    private val comicsService = retrofit.create(ComicsService::class.java)

    init {
        _appState.postValue(DataState.Loading)
        getHqData()
    }

    fun onHQSelected(position: Int) {
        val hqDetails = _hqListLiveData.value?.get(position)//pega um elemento da lista
        hqDetails?.let{
            _hqDetailsLiveData.postValue(it)
            _navigationToDetailLiveData.postValue(Event(Unit))
        }

    }

    private fun getHqData() {
        val timestamp = ApiHelper.getCurrentTimeStamp()
        val input = "$timestamp${ApiCredentials().privateKey}${ApiCredentials().publicKey}"
        val hash = ApiHelper.generateMD5Hash(input)

        viewModelScope.launch {
            val response =
                comicsService.getComicsList(timestamp, ApiCredentials().publicKey, hash, 100)
            if (response.isSuccessful) {
                _hqListLiveData.postValue(response.body()?.data?.results)
                _appState.postValue(DataState.Success)
            } else {
                _appState.postValue(DataState.Error)
            }
        }
    }
}