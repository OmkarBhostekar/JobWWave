package com.omkarcodes.hackathonstarter.ui.auth

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omkarcodes.hackathonstarter.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val pref: SharedPreferences
) : ViewModel() {

    private val _authState = MutableLiveData<Resource<String>>()
    val authState: LiveData<Resource<String>> = _authState

    fun login(email: String, pass: String) = viewModelScope.launch(Dispatchers.IO) {
        _authState.postValue(Resource.Success(""))
    }

    fun signup(username: String, email: String, pass: String) = viewModelScope.launch(Dispatchers.IO) {

    }

    fun logout() = viewModelScope.launch(Dispatchers.IO) {

    }
}