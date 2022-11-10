package com.example.missingcats.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.missingcats.repository.AuthAppRepository
import com.google.firebase.auth.FirebaseUser

class UserViewModel: ViewModel() {
    private val repository = AuthAppRepository()
    val userLiveData = repository.userLiveData
    val errorMessageLiveData:LiveData<String> = repository.errorMessageLiveData

    fun login(email: String, password: String){
        repository.login(email,password)
    }
    fun logOut(){
        repository.logOut()
    }
    fun signUp(email: String, password: String) {
        repository.signUP(email, password)
    }

}

