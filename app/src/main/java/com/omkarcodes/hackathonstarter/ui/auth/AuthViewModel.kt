package com.omkarcodes.hackathonstarter.ui.auth

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omkarcodes.hackathonstarter.common.Resource
import com.omkarcodes.hackathonstarter.common.RetrofitUtils
import com.omkarcodes.hackathonstarter.data.network.MainApi
import com.pluto.plugins.logger.PlutoLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val pref: SharedPreferences,
    private val mainApi: MainApi
) : ViewModel() {

    private val _authState = MutableLiveData<Resource<String>>()
    val authState: LiveData<Resource<String>> = _authState

    fun login(email: String, pass: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            _authState.postValue(Resource.Loading())
            PlutoLog.d("","before api call")
            val res = mainApi.login(RetrofitUtils.createJsonRequestBody(
                "email" to email,
                "password" to pass
            ))
            if(res.isSuccessful){
                res.body()?.let {
                    pref.edit()
                        .putString("token",it.token)
                        .putString("name",it.name)
                        .putString("userId", it.userId)
                        .apply()
                    _authState.postValue(Resource.Success("Logged in successfully"))
                }
            }else{
                _authState.postValue(Resource.Error("An error occurred"))
            }
        }catch (t: Throwable){
            PlutoLog.d("",t.message.toString())
        }
    }

    fun signup(username: String, email: String, pass: String) = viewModelScope.launch(Dispatchers.IO) {

    }

    fun logout() = viewModelScope.launch(Dispatchers.IO) {

    }
}