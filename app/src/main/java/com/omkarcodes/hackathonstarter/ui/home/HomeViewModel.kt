package com.omkarcodes.hackathonstarter.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omkarcodes.hackathonstarter.common.Resource
import com.omkarcodes.hackathonstarter.common.RetrofitUtils
import com.omkarcodes.hackathonstarter.data.model.RecResult
import com.omkarcodes.hackathonstarter.data.model.ReferralModel
import com.omkarcodes.hackathonstarter.data.model.User
import com.omkarcodes.hackathonstarter.data.model.search.SearchResult
import com.omkarcodes.hackathonstarter.data.network.MainApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val api: MainApi
) : ViewModel() {

    private val recommendations = MutableLiveData<Resource<List<RecResult>>>()
    private val refs = MutableLiveData<Resource<List<ReferralModel>>>()
    private val profile = MutableLiveData<Resource<User>>()
    fun getRecom(userId: String) = viewModelScope.launch {
        recommendations.postValue(Resource.Loading())
        try {
            val res = api.getRecom(userId)
            if(res.isSuccessful){
                res.body()?.let {
                    recommendations.postValue(Resource.Success(it))
                }
            }
        }catch (_: Throwable) {
            recommendations.postValue(Resource.Error(""))
        }
    }
    fun getApplications(userId: String) = viewModelScope.launch {
        recommendations.postValue(Resource.Loading())
        try {
            val res = api.getRecom(userId)
            if(res.isSuccessful){
                res.body()?.let {
                    recommendations.postValue(Resource.Success(it))
                }
            }
        }catch (_: Throwable) {
            recommendations.postValue(Resource.Error(""))
        }
    }
    fun getRefList(userId: String) = viewModelScope.launch {
        refs.postValue(Resource.Loading())
        try {
            val res = api.getRefList(userId)
            if(res.isSuccessful){
                res.body()?.let {
                    refs.postValue(Resource.Success(it))
                }
            }
        }catch (_: Throwable) {
            refs.postValue(Resource.Error(""))
        }
    }
    fun getProfile(userId: String) = viewModelScope.launch {
        profile.postValue(Resource.Loading())
        try {
            val res = api.getProfile(userId)
            if(res.isSuccessful){
                res.body()?.let {
                    profile.postValue(Resource.Success(it))
                }
            }
        }catch (_: Throwable) {
            profile.postValue(Resource.Error(""))
        }
    }

    fun pushToken(userId: String, token: String) = viewModelScope.launch {
        try {
            val body = RetrofitUtils.createJsonRequestBody(
                "id" to userId,
                "token" to token
            )
            val res = api.pushToken(body)
        }catch (_: Throwable) {
        }
    }

    fun onRecommendations() = recommendations
    fun onReferralList() = refs

    fun onProfile() = profile

}