package com.ebac.hqcomicapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ebac.hqcomicapp.HQDetails.HQDetails
import com.ebac.hqcomicapp.data.Comic
import com.ebac.hqcomicapp.data.ComicResponse
import com.ebac.hqcomicapp.data.DataState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class HQViewModel : ViewModel() {
    //primeiro pega como tipo LiveData que não é modificável, só de leitura
    val hqDetailsLiveData: LiveData<HQDetails>
        get() = _hqDetailsLiveData
    //transforma em dados mutáveis
    private val _hqDetailsLiveData = MutableLiveData<HQDetails>()

    val hqListLiveData: LiveData<List<Comic>?>
        get() = _hqListLiveData
    private val _hqListLiveData = MutableLiveData<List<Comic>?>()

    val appState: LiveData<DataState>
        get() = _appState
    private val _appState = MutableLiveData<DataState>()

    val navigationToDetailLiveData
        get() = _navigationToDetailLiveData
    private val _navigationToDetailLiveData = MutableLiveData<Unit>()

    private val retrofit = Retrofit.Builder()
        .baseUrl(ApiCredentials().baseUrl)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    private val comicsService = retrofit.create(ComicsService::class.java)

    init {
        _appState.postValue(DataState.Loading)
        getHqData()
    }

    fun onHQSelected(position: Int){
        val hqDetails = HQDetails("Minha HQ", "Este é um conteúdo de texto extenso")
        _hqDetailsLiveData.postValue(hqDetails)
        _navigationToDetailLiveData.postValue(Unit)
    }

    private fun getHqData(){
        val timestamp = ApiHelper.getCurrentTimeStamp()
        val input = "$timestamp${ApiCredentials().privateKey}${ApiCredentials().publicKey}"

        val hash = ApiHelper.generateMD5Hash(input)

        comicsService.getComicsList(timestamp, ApiCredentials().publicKey, hash, 100).enqueue(object:
            Callback<ComicResponse> {
            override fun onResponse(call: Call<ComicResponse>, response: Response<ComicResponse>) {
                if(response.isSuccessful){
                    _hqListLiveData.postValue(response.body()?.data?.results)
                    _appState.postValue(DataState.Success)
                } else {
                    _appState.postValue(DataState.Error)
                }
            }

            override fun onFailure(call: Call<ComicResponse>, t: Throwable) {
               /* t?.message?.let {
                    Log.e("onFailure error", t?.message!!)
                }*/
                _appState.postValue(DataState.Error)
            }

        })
    }
}