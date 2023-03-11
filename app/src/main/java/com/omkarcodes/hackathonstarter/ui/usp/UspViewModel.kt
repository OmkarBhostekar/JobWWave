package com.omkarcodes.hackathonstarter.ui.usp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omkarcodes.hackathonstarter.common.Resource
import com.omkarcodes.hackathonstarter.common.RetrofitUtils
import com.omkarcodes.hackathonstarter.data.body.SmartSearchBody
import com.omkarcodes.hackathonstarter.data.model.search.SearchResult
import com.omkarcodes.hackathonstarter.data.network.MainApi
import com.omkarcodes.hackathonstarter.data.network.UspApi
import com.pluto.plugins.logger.PlutoLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UspViewModel @Inject constructor(
    private val api: UspApi
) : ViewModel() {

    private val searchResult = MutableLiveData<Resource<List<SearchResult>>>()

    fun getSmartSearch(companies: List<String>, skills: List<String>, title: List<String>) = viewModelScope.launch {
        val body = RetrofitUtils.createJsonRequestBody(
            "companies" to companies,
            "skills" to skills,
            "title" to title,
        )
        searchResult.postValue(Resource.Loading())
        try{
            val response = api.getSmartSearch(body)
            if(response.isSuccessful){
                response.body()?.let {
                    PlutoLog.d("",it.toString())
                    searchResult.postValue(Resource.Success(it))
                }
            }
        }catch (_: Throwable){
            searchResult.postValue(Resource.Error(""))
        }
    }

    fun onSearchResult() = searchResult

}